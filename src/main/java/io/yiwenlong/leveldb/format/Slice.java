package io.yiwenlong.leveldb.format;

public final class Slice implements Comparable<Slice>{

	private final byte[] data;

	private int size;
	private int offset;

	// Create an empty slice.
	public Slice() {
		this(new byte[0], 0);
	}

	// Create a slice that refers to d[0,n-1].
	public Slice(final byte[] d, int n) {
		this.data = d;
		this.size = n;
		this.offset = 0;
	}

	// Create a slice that refers to the contents of "s"
	public Slice(final String s) {
		this(s.getBytes(), s.getBytes().length);
	}

	public Slice(final byte[] d) {
		this(d, d.length);
	}

	public byte[] data() {
		byte[] tmp = new byte[size];
		System.arraycopy(data, offset, tmp, 0, size);
		return tmp;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	// Change this slice to refer to an empty array
	public void clear() {
//		this.data = new byte[0];
		this.offset = this.data.length;
		this.size = 0;
	}

	// Drop the first "n" bytes from this slice.
	public void removePrefix(int n) {
		assert(n <= size());
		this.offset += n;
		this.size -= n;
	}

	// Return true iff "x" is a prefix of "*this"
	public boolean startWith(Slice x) {
		return ((size >= x.size) && (this.memcmp(x, x.size) == 0));
	}

	public int memcmp(final Slice x, int length) {
		for (int i = 0; i < length; i++) {
			if (this.data[offset + i] != x.data[x.offset + i]) {
				return (this.data[offset + i] & 0xFF) - (x.data[x.offset + i] & 0xFF);
			}
		}
		return 0;
	}

	// Returns the user key portion of an internal key.
	public Slice extractUserKey() {
		assert this.size() > 8;
		return new Slice(this.data(), this.size() - 8);
	}


	@Override
	public String toString() {
		return new String(data, offset, size);
	}

	@Override
	public int compareTo(final Slice b) {
		if (this == b) {
			return 0;
		}
		final int minLen = Math.min(size, b.size);
		for (int i = 0; i < minLen; i++) {
			if (this.data[this.offset + i] != b.data[b.offset + i]) {
				return (this.data[this.offset + i] & 0xFF) - (b.data[b.offset + i] & 0xFF);
			}
		}
		return this.size - b.size;
	}
}
