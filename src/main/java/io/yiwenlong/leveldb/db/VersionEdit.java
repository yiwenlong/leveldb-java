package io.yiwenlong.leveldb.db;

import io.yiwenlong.leveldb.format.InternalKey;
import io.yiwenlong.leveldb.format.Slice;

public class VersionEdit {

	private String comparator_;
	private long logNumber_;
	private long prevLogNumber_;
	private long nextFileNumber_;
	private long lastSequence_;

	boolean hasComparator_;
	boolean hasLogNumber_;
	boolean hasPrevLogNumber_;
	boolean hasNextFileNumber_;
	boolean hasLastSequence_;

	// compact_pointers_
	// deleted_files_
	// new_files_

	public void clear() {

	}

	public void setComparatorName(final Slice name) {
		hasComparator_ = true;
		comparator_ = name.toString();
	}

	public void setLogNumber(long num) {
		hasLogNumber_ = true;
		logNumber_ = num;
	}

	public void setPrevLogNumber(long num) {
		hasPrevLogNumber_ = true;
		prevLogNumber_ = num;
	}

	public void setNextFile(long num) {
		hasNextFileNumber_ = true;
		nextFileNumber_ = num;
	}
	public void setLastSequence(long seq) {
		hasLastSequence_ = true;
		lastSequence_ = seq;
	}
	public void setCompactPointer(int level, final InternalKey key) {
//		compact_pointers_.push_back(std::make_pair(level, key));
	}

	// Add the specified file at the specified number.
	// REQUIRES: This version has not been saved (see VersionSet::SaveTo)
	// REQUIRES: "smallest" and "largest" are smallest and largest keys in file
	public void AddFile(int level, long file, long file_size,
               final InternalKey smallest, final InternalKey largest) {
		FileMetaData f = new FileMetaData();
		f.number = file;
		f.fileSize = file_size;
		f.smallest = smallest;
		f.largest = largest;
//		new_files_.push_back(std::make_pair(level, f));
	}

	// Delete the specified "file" from the specified "level".
	public void removeFile(int level, long file) {
//		deleted_files_.insert(std::make_pair(level, file));
	}
}
