package io.yiwenlong.leveldb.db;

import io.yiwenlong.leveldb.format.InternalKey;

public class FileMetaData {

	public FileMetaData() {
		this.refs = 0;
		this.allowedSeeks = 1 << 30;
		this.fileSize = 0;
	}

	public int refs;
	public int allowedSeeks;  		// Seeks allowed until compaction
	public long number;
	public long fileSize;    		// File size in bytes
	public InternalKey smallest;  // Smallest internal key served by table
	public InternalKey largest;   // Largest internal key served by table
}
