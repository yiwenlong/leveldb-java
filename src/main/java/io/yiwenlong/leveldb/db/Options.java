package io.yiwenlong.leveldb.db;

public class Options {

	// Leveldb will write up to this amount of bytes to a file before
	// switching to a new one.
	// Most clients should leave this parameter alone.  However if your
	// filesystem is more efficient with larger files, you could
	// consider increasing the value.  The downside will be longer
	// compactions and hence longer latency/performance hiccups.
	// Another reason to increase this parameter might be when you are
	// initially populating a large database.
	public int maxFileSize = 2 * 1024 * 1024;
}
