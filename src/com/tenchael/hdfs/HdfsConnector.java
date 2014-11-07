package com.tenchael.hdfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface HdfsConnector {
	/**
	 * Obtain the information of a file or directory
	 * 
	 * @param remotePath
	 *            The path of a file or directory in remote
	 * @return The result of operation with the format of json
	 */
	public String getStatus(String remotePath);

	/**
	 * Create a directory of remote hdfs
	 * 
	 * @param remotePath
	 *            The path of the directory in remote hdfs
	 * @return The result of operation with the format of json
	 */
	public String mkdir(String remotePath);

	/**
	 * Rename a file or directory in remote hdfs
	 * 
	 * @param oldName
	 *            The old name
	 * @param newName
	 *            The new name
	 * @return The result of operation with the format of json
	 */
	public String rename(String oldName, String newName);

	/**
	 * Delete a file or directory in remote hdfs
	 * 
	 * @param remotePath
	 *            The path of a file or directory
	 * @return The result of operation with the format of json
	 */
	public String delete(String remotePath);

	/**
	 * List all items under a directory
	 * 
	 * @param remoteDirectory
	 *            The directory in remote hdfs
	 * @return The result of operation with the format of json
	 */
	public String list(String remoteDirectory);

	/**
	 * Download a file from remote hdfs
	 * 
	 * @param remoteFilePath
	 *            The path of a file in remote hdfs
	 * @param out
	 *            The outputStream for output
	 * @throws IOException
	 */
	public InputStream download(String remoteFilePath) throws IOException;

	/**
	 * Upload a file to remote hdfs
	 * 
	 * @param localFilePath
	 *            The path a local file
	 * @param remoteDirectory
	 *            The path a remote directory in hdfs
	 * @return The result of operation with the format of json
	 * @throws FileNotFoundException
	 */
	public String upload(String localFilePath, String remoteDirectory)
			throws FileNotFoundException;

}
