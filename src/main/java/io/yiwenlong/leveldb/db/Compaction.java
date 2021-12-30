package io.yiwenlong.leveldb.db;

import java.util.List;

import io.yiwenlong.leveldb.format.Comparator;
import io.yiwenlong.leveldb.format.Slice;

public class Compaction {

	private int level_;
	private long maxOutputFileSize_;
	private Version inputVersion_;
	private VersionEdit edit_;

	// Each compaction reads inputs from "level_" and "level_+1"
	private final List<FileMetaData>[] inputs_ = new List[2];  // The two sets of inputs

	// State used to check for number of overlapping grandparent files
	// (parent == level_ + 1, grandparent == level_ + 2)
	private List<FileMetaData> grandparents_;
	private int grandparentIndex_;			// Index in grandparent_starts_
	private boolean seenKey_;				// Some output key has been seen
	private long overlappedBytes_;			// Bytes of overlap between current output
											// and grandparent files

	// State for implementing IsBaseLevelForKey

	// level_ptrs_ holds indices into input_version_->levels_: our state
	// is that we are positioned at one of the file ranges for each
	// higher level than the ones involved in this compaction (i.e. for
	// all L >= level_ + 2).
	private final int[] levelPtrs_ = new int[Config.kNumLevels];

	// Return the level that is being compacted.  Inputs from "level"
	// and "level+1" will be merged to produce a set of "level+1" files.
	public int level() { return level_; }

	// Return the object that holds the edits to the descriptor done
	// by this compaction.
	public VersionEdit edit() { return edit_; }

	// "which" must be either 0 or 1
	public int numInputFiles(int which) { return inputs_[which].size(); }

	// Return the ith input file at "level()+which" ("which" must be 0 or 1).
	public FileMetaData input(int which, int i) { return inputs_[which].get(i); }

	// Maximum size of files to build during this compaction.
	public long maxOutputFileSize() { return maxOutputFileSize_; }

	// Is this a trivial compaction that can be implemented by just
	// moving a single input file to the next level (no merging or splitting)
	public boolean isTrivialMove() {
		final VersionSet vset = inputVersion_.vset();
		// Avoid a move if there is lots of overlapping grandparent data.
		// Otherwise, the move could create a parent file that will require
		// a very expensive merge later on.
		return (numInputFiles(0) == 1 && numInputFiles(1) == 0 &&
				TotalFileSize(grandparents_) <=
						MaxGrandParentOverlapBytes(vset.options()));
	}

	// Add all inputs to this compaction as delete operations to *edit.
	public void addInputDeletions(VersionEdit edit) {
		for (int which = 0; which < 2; which++) {
			for (int i = 0; i < inputs_[which].size(); i++) {
				edit.removeFile(level_ + which, inputs_[which].get(i).number);
			}
		}
	}

	// Returns true if the information we have available guarantees that
	// the compaction is producing data in "level+1" for which no data exists
	// in levels greater than "level+1".
	public boolean isBaseLevelForKey(final Slice user_key){
		// Maybe use binary search to find right entry instead of linear search?
  		final Comparator userCmp = inputVersion_.vset().icmp().userComparator();
		for (int lvl = level_ + 2; lvl < Config.kNumLevels; lvl++) {
    		final List<FileMetaData> files = inputVersion_.files()[lvl];
			while (levelPtrs_[lvl] < files.size()) {
				FileMetaData f = files.get(levelPtrs_[lvl]);
				if (userCmp.compare(user_key, f.largest.userKey()) <= 0) {
					// We've advanced far enough
					if (userCmp.compare(user_key, f.smallest.userKey()) >= 0) {
						// Key falls in this file's range, so definitely not base level
						return false;
					}
					break;
				}
				levelPtrs_[lvl]++;
			}
		}
		return true;
	}

	// Returns true iff we should stop building the current output
	// before processing "internal_key".
	public boolean shouldStopBefore(final Slice internal_key) {
		final VersionSet vset = inputVersion_.vset();
		// Scan to find earliest grandparent file that contains key.
  		final InternalKeyComparator icmp = vset.icmp();
		while (grandparentIndex_ < grandparents_.size() &&
				icmp.compare(internal_key, grandparents_.get(grandparentIndex_).largest.encode()) > 0) {
			if (seenKey_) {
				overlappedBytes_ += grandparents_.get(grandparentIndex_).fileSize;
			}
			grandparentIndex_++;
		}
		seenKey_ = true;

		if (overlappedBytes_ > MaxGrandParentOverlapBytes(vset.options())) {
			// Too much overlap for current output; start new output
			overlappedBytes_ = 0;
			return true;
		} else {
			return false;
		}
	}

	// Release the input version for the compaction, once the compaction
	// is successful.
	public void releaseInputs() {
		if (inputVersion_ != null) {
			inputVersion_.unref();
			inputVersion_ = null;
		}
	}

	static long TotalFileSize(final List<FileMetaData> files) {
		long sum = 0;
		for (int i = 0; i < files.size(); i++) {
			sum += files.get(i).fileSize;
		}
		return sum;
	}

	// Maximum bytes of overlaps in grandparent (i.e., level+2) before we
	// stop building a single file in a level->level+1 compaction.
	static long MaxGrandParentOverlapBytes(final Options options) {
		return 10L * TargetFileSize(options);
	}

	static int TargetFileSize(final Options options) {
		return options.maxFileSize;
	}
}
