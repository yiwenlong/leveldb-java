package io.yiwenlong.leveldb.format;

public interface Comparator {

	// Three-way comparison.  Returns value:
	//   < 0 iff "a" < "b",
	//   == 0 iff "a" == "b",
	//   > 0 iff "a" > "b"
	int compare(final Slice a, final Slice b);

	// The name of the comparator.  Used to check for comparator
	// mismatches (i.e., a DB created with one comparator is
	// accessed using a different comparator.
	//
	// The client of this package should switch to a new name whenever
	// the comparator implementation changes in a way that will cause
	// the relative ordering of any two keys to change.
	//
	// Names starting with "leveldb." are reserved and should not be used
	// by any clients of this package.
	byte[] Name();

	// Advanced functions: these are used to reduce the space requirements
	// for internal data structures like index blocks.

	// If *start < limit, changes *start to a short string in [start,limit).
	// Simple comparator implementations may return with *start unchanged,
	// i.e., an implementation of this method that does nothing is correct.
	void findShortestSeparator(String start, final Slice limit);

	// Changes *key to a short string >= *key.
	// Simple comparator implementations may return with *key unchanged,
	// i.e., an implementation of this method that does nothing is correct.
	void findShortSuccessor(String key);
}
