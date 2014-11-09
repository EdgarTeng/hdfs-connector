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
	public String getStatus(final String remotePath);

	/**
	 * Create a directory of remote hdfs
	 * 
	 * @param remotePath
	 *            The path of the directory in remote hdfs
	 * @return The result of operation with the format of json
	 */
	public String mkdir(final String remotePath);

	/**
	 * Rename a file or directory in remote hdfs
	 * 
	 * @param oldName
	 *            The old name
	 * @param newName
	 *            The new name
	 * @return The result of operation with the format of json
	 */
	public String rename(final String oldName, final String newName);

	/**
	 * Delete a file or directory in remote hdfs
	 * 
	 * @param remotePath
	 *            The path of a file or directory
	 * @return The result of operation with the format of json
	 */
	public String delete(final String remotePath);

	/**
	 * List all items under a directory
	 * 
	 * @param remoteDirectory
	 *            The directory in remote hdfs
	 * @return The result of operation with the format of json
	 */
	public String list(final String remoteDirectory);

	/**
	 * Download a file from remote hdfs
	 * 
	 * @param remoteFilePath
	 *            The path of a file in remote hdfs
	 * @param out
	 *            The outputStream for output
	 * @throws IOException
	 */
	public InputStream download(final String remoteFilePath) throws IOException;

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
	public void upload(final String localFilePath, final String remoteDirectory)
			throws FileNotFoundException;

	/**
	 * Create a file at remote hdfs
	 * 
	 * @param localFilePath
	 *            The path of local file
	 * @param remoteFilePath
	 *            The path of remote file in hdfs
	 * @throws FileNotFoundException
	 */
	public void create(final String localFilePath, final String remoteFilePath)
			throws FileNotFoundException;

}
