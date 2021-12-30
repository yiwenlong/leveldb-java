package io.yiwenlong.leveldb.db;

import io.yiwenlong.leveldb.format.Comparator;
import io.yiwenlong.leveldb.format.Slice;

public class InternalKeyComparator implements Comparator {

	private Comparator userComparator_;

	public Comparator userComparator() { return userComparator_; }

	@Override
	public int compare(Slice a, Slice b) {
		return 0;
	}

	@Override
	public byte[] Name() {
		return new byte[0];
	}

	@Override
	public void findShortestSeparator(String start, Slice limit) {

	}

	@Override
	public void findShortSuccessor(String key) {

	}
}
