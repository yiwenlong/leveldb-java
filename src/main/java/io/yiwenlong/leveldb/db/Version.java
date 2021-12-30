package io.yiwenlong.leveldb.db;

import java.util.List;

import io.yiwenlong.leveldb.format.InternalKey;
import io.yiwenlong.leveldb.format.Slice;

public class Version {

	private VersionSet vset_;	// VersionSet to which this Version belongs
	private Version next_;		// Next version in linked list
	private Version prev_;		// Previous version in linked list
	private int refs;			// Number of live refs to this version
	// List of files per level
	private final List<FileMetaData>[] files_ = new List[Config.kNumLevels];

	// Next file to compact based on seek stats.
	private FileMetaData fileToCompact;
	private int fileToCompactLevel;

	// Level that should be compacted next and its compaction score.
	// Score < 1 means compaction is not strictly needed.  These fields
	// are initialized by Finalize().
	private double compactionScore;
	private int compactionLevel;

	public static final class GetStats {
		FileMetaData seekFile;
		int seekFileLevel;
	}

	// Record a sample of bytes read at the specified internal key.
	// Samples are taken approximately once every config::kReadBytesPeriod
	// bytes.  Returns true if a new compaction may need to be triggered.
	// REQUIRES: lock is held
	public boolean RecordReadSample(Slice key) {
		return true;
	}

	// Reference count management (so Versions do not disappear out from
	// under live iterators)
	public void ref() {

	}

	public void unref() {

	}

	public VersionSet vset() { return vset_; }

	public List<FileMetaData>[] files() { return files_; }

	public List<FileMetaData> getOverlappingInputs(int level,
			InternalKey begin, 	/* nullptr means before all keys*/
			InternalKey end  	/* nullptr means before all keys*/) {
		return null;
	}

	// Returns true iff some file in the specified level overlaps
	// some part of [*smallest_user_key,*largest_user_key].
	// smallest_user_key==nullptr represents a key smaller than all the DB's keys.
	// largest_user_key==nullptr represents a key largest than all the DB's keys.
	public boolean overlapInLevel(int level, final Slice smallestUserKey, final Slice largestUserKey) {
		return false;
	}

	// Return the level at which we should place a new memtable compaction
	// result that covers the range [smallest_user_key,largest_user_key].
	public int pickLevelForMemTableOutput(final Slice smallest_user_key, final Slice largest_user_key)  {
		return 0;
	}
}
