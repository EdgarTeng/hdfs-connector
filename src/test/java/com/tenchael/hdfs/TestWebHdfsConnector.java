package com.tenchael.hdfs;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.tenchael.hdfs.HdfsConnector;
import com.tenchael.hdfs.WebHdfsConnector;

import junit.framework.TestCase;

public class TestWebHdfsConnector extends TestCase {

	private HdfsConnector connector = new WebHdfsConnector("master", 50070,
			"root");

	public void testGetStatus() {
		String remotePath = "/user/upload";
		String msg = connector.getStatus(remotePath);
		System.out.println(msg);
	}

	public void testMkdir() {
		String remotePath = "/user/upload";
		String msg = connector.mkdir(remotePath);
		System.out.println(msg);
	}

	public void testRename() {
		String oldName = "/user/upload/test.java";
		String newName = "/user/upload/test2.java";
		String msg = connector.rename(oldName, newName);
		System.out.println(msg);
	}

	public void testDelete() {
		String remotePath = "/user/upload/test2.java";
		String msg = connector.delete(remotePath);
		System.out.println(msg);
	}

	public void testList() {
		String remoteDirectory = "/user/upload";
		String msg = connector.list(remoteDirectory);
		System.out.println(msg);
	}

	public void testDownload() throws IOException {
		String remoteFilePath = "/user/upload/test.java";
		String localFilePath = "d:/test2.java";
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(new File(localFilePath))));
		InputStream in = connector.download(remoteFilePath);

		int bufferSize = 8192;
		byte[] buf = new byte[bufferSize];

		while (true) {
			int read = 0;
			if (in != null) {
				read = in.read(buf);
			}
			if (read == -1) {
				break;
			}
			out.write(buf, 0, read);
		}
		out.flush();
		out.close();
	}

	public void testUpload() throws FileNotFoundException {
		String localFilePath = "d:/test.java";
		String remoteDirectory = "/user/upload/";
		connector.upload(localFilePath, remoteDirectory);
	}

}
