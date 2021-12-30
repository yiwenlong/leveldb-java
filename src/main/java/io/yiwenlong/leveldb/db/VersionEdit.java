package io.yiwenlong.leveldb.db;

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
}
