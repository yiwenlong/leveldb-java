package io.yiwenlong.leveldb.format;

public class State {
	public final byte K_OK = 0;
	public final byte K_NOT_FOUND = 1;
	public final byte K_CORRUPTION = 2;
	public final byte K_NOT_SUPPORTED = 3;
	public final byte K_INVALID_ARGUMENT = 4;
	public final byte K_IO_ERROR = 5;

	// OK status has a null state_.  Otherwise, state_ is a new[] array
	// of the following form:
	//    state_[0..3] == length of message
	//    state_[4]    == code
	//    state_[5..]  == message
	private byte[] state_;

	public byte code() {
		return state_ == null ? K_OK : state_[4];
	}

	// Returns true iff the status indicates success.
	public boolean isOk() { return state_ == null; }

	// Returns true iff the status indicates a NotFound error.
	public boolean isNotFound() { return this.code() == K_NOT_FOUND; }

	// Returns true iff the status indicates a Corruption error.
	public boolean isCorruption() { return this.code() == K_CORRUPTION; }

	// Returns true iff the status indicates an IOError.
	public boolean IsIOError() { return this.code() == K_IO_ERROR; }

	// Returns true iff the status indicates a NotSupportedError.
	public boolean IsNotSupportedError() { return this.code() == K_NOT_SUPPORTED; }

	// Returns true iff the status indicates an InvalidArgument.
	public boolean IsInvalidArgument() { return this.code() == K_INVALID_ARGUMENT; }

	public static State ok() { return new State(); }

}
