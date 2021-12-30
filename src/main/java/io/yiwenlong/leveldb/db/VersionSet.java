package io.yiwenlong.leveldb.db;

import io.yiwenlong.leveldb.format.State;
import io.yiwenlong.leveldb.log.Writer;

public class VersionSet {

	private Env env;
	private String dbname;
	private Options options;
	private TableCache tableCache;
	private InternalKeyComparator icmp;

	private long nextFileNumber_;
	private long manifestFileNumber_;
	private long lastSequence_;
	private long logNumber_;
	private long prevLogNumber_;  	// 0 or backing store for memtable being compacted

	// Opened lazily
	private WritableFile descriptorFile;
	private Writer descriptorLog;

	private Version dummyVersions; 	// Head of circular doubly-linked list of versions.
	private Version current_;		// == dummy_versions_.prev_

	// Per-level key at which the next compaction at that level should start.
	// Either an empty string, or a valid InternalKey.
	private final String []compact_pointer = new String[Config.kNumLevels];

	// Apply *edit to the current version to form a new descriptor that
	// is both saved to persistent state and installed as the new
	// current version.  Will release *mu while actually writing to the file.
	// REQUIRES: *mu is held on entry.
	// REQUIRES: no other thread concurrently calls LogAndApply()
	public State LogAndApply(VersionEdit edit) {
		return State.ok();
	}

	// Recover the last saved descriptor from persistent storage.
	public State Recover(boolean saveManifest) {
		return State.ok();
	}

	public Version current() {
		return current_;
	}

	// Return the current manifest file number
	public final long ManifestFileNumber() { return manifestFileNumber_; }

	// Allocate and return a new file number
	public final long NewFileNumber() { return nextFileNumber_++; }

	// Arrange to reuse "file_number" unless a newer file number has
	// already been allocated.
	// REQUIRES: "file_number" was returned by a call to NewFileNumber().
	public void reuseFileNumber(long file_number) {
		if (nextFileNumber_ == file_number + 1) {
			nextFileNumber_ = file_number;
		}
	}

	// Return the number of Table files at the specified level.
	public int numLevelFiles(int level) {
		return 0;
	}
}
