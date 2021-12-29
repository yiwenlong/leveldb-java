package io.yiwenlong.leveldb.coding;

public class LittleEndian {

	public static long uint32(byte[] b, int offset) {
		return  ((b[offset++] & 0xFFL)) 		|
				((b[offset++] & 0xFFL) << 8) 	|
				((b[offset++] & 0xFFL) << 16) 	|
				((b[offset] & 0xFFL) << 24);
	}

	public static int uint16(byte[] b, int offset) {
		return ((b[offset++] & 0xFF)) | ((b[offset] & 0xFF) << 8);
	}

	public static void encodeUint64(long val, byte[] out, int offset) {
		out[offset++] = (byte) (0xFF & (val));
		out[offset++] = (byte) (0xFF & (val >> 8));
		out[offset++] = (byte) (0xFF & (val >> 16));
		out[offset++] = (byte) (0xFF & (val >> 24));
		out[offset++] = (byte) (0xFF & (val >> 32));
		out[offset++] = (byte) (0xFF & (val >> 40));
		out[offset++] = (byte) (0xFF & (val >> 48));
		out[offset] = (byte) (0xFF & (val >> 56));
	}

	public static long uint64(byte[] b, int offset) {
		return  ((b[offset++] & 0xFFL)) 		|
				((b[offset++] & 0xFFL) << 8) 	|
				((b[offset++] & 0xFFL) << 16) 	|
				((b[offset++] & 0xFFL) << 24) 	|
				((b[offset++] & 0xFFL) << 32) 	|
				((b[offset++] & 0xFFL) << 40) 	|
				((b[offset++] & 0xFFL) << 48) 	|
				((b[offset] & 0xFFL) << 56) ;
	}
}
