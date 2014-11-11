package com.tenchael.test;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.tenchael.hdfs.HdfsConnector;
import com.tenchael.hdfs.impl.WebHdfsConnector;

import junit.framework.TestCase;

public class TestWebHdfsConnector extends TestCase {

	private HdfsConnector connector = new WebHdfsConnector("master", 50070,
			"root");

	public void testGetStatus() {
		String remotePath = "/user/upload/";
		String msg = connector.getStatus(remotePath);
		System.out.println(msg);
	}

	public void testMkdir() {
		String remotePath = "/user/upload/.temp2/";
		String msg = connector.mkdir(remotePath);
		System.out.println(msg);
		//assertEquals(true, false);
		//assertEquals("tzz", "lxy");
		assertEquals(1, 1);
	}

	public void testRename() {
		String oldName = "/user/upload/.temp2";
		String newName = "/user/upload/.temp3";
		String msg = connector.rename(oldName, newName);
		System.out.println(msg);
	}

	public void testDelete() {
		String remotePath = "/user/upload/test.java";
		String msg = connector.delete(remotePath);
		System.out.println(msg);
	}

	public void testList() {
		String remoteDirectory = "/user/upload";
		String msg = connector.list(remoteDirectory);
		System.out.println(msg);
	}

	public void testDownload() throws IOException {
		String remoteFilePath = " /user/upload/test.java";
		String localFilePath = "d:/files/testt.java";

		File file = new File(localFilePath);
		if (!file.exists()) {
			file.createNewFile();
		}

		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(file)));
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
		String localFilePath = "D:/files/bills.txt";
		String remoteDirectory = "/user/upload/";
		connector.upload(localFilePath, remoteDirectory);
	}

	public void testCreate() throws FileNotFoundException {
		String localFilePath = "D:/files/bills.txt";
		String remoteFilePath = "/user/upload/test3.java";
		connector.create(localFilePath, remoteFilePath);
	}

}
