package io.yiwenlong.leveldb.format;

public enum ValueType {

	TypeDeletion((byte) 0x0), TypeValue((byte) 0x1);

	private final byte raw;

	ValueType(byte r) {
		this.raw = r;
	}

	public byte getRawValue() {
		return raw;
	}
}
