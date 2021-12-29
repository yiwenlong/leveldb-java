package io.yiwenlong.leveldb.format;

public final class ParsedInternalKey {

	private Slice userKey;
	private long sequence; 			// SequenceNumber
	private ValueType type;

	// Intentionally left uninitialized (for speed)
	public ParsedInternalKey() {}

	public ParsedInternalKey(final Slice u, final long seq, final ValueType t) {
		this.userKey  = u;
		this.sequence = seq;
		this.type = t;
	}

	public int internalKeyEncodingLength() {
		return userKey.size() + 8;
	}
}
