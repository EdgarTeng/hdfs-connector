package com.tenchael.hdfs;

import static com.tenchael.hdfs.util.Constants.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.tenchael.hdfs.util.Constants;

public class WebHdfsConnector extends BaseService implements HdfsConnector {
	private String host;
	private int port;
	private String user;
	private String uri;

	public WebHdfsConnector() {
	}

	public WebHdfsConnector(String host, int port, String user) {
		super();
		this.host = host;
		this.port = port;
		this.user = user;
		uri = jointUri();
	}

	private String jointUri() {
		StringBuffer buf = new StringBuffer();
		buf.append("http://");
		buf.append(getHost());
		buf.append(":");
		buf.append(getPort());
		buf.append("/webhdfs/v1");
		return buf.toString();
	}

	public String getStatus(String remotePath) {
		uri = jointUri();
		Map<String, String> params = new HashMap<String, String>();
		params.put(KEY_OP, OP_GETFILESTATUS);
		return super.doGetAsString(getUri() + remotePath, params);
	}

	public String mkdir(String remotePath) {
		uri = jointUri();
		Map<String, String> params = new HashMap<String, String>();
		params.put(KEY_OP, OP_MKDIRS);
		params.put(KEY_USERNAME, getUser());
		return super.doPut(getUri() + remotePath, params);
	}

	public String rename(String oldName, String newName) {
		uri = jointUri();
		Map<String, String> params = new HashMap<String, String>();
		params.put(KEY_OP, OP_RENAME);
		params.put(KEY_USERNAME, getUser());
		params.put(KEY_DESTINATION, newName);
		return super.doPut(getUri() + oldName, params);
	}

	public String delete(String remotePath) {
		uri = jointUri();
		Map<String, String> params = new HashMap<String, String>();
		params.put(KEY_OP, OP_DELETE);
		params.put(KEY_USERNAME, getUser());
		return super.doDelete(getUri() + remotePath, params);
	}

	public String list(String remoteDirectory) {
		uri = jointUri();
		Map<String, String> params = new HashMap<String, String>();
		params.put(KEY_OP, OP_LISTSTATUS);
		return super.doGetAsString(getUri() + remoteDirectory, params);
	}

	public InputStream download(String remoteFilePath) throws IOException {
		uri = jointUri();
		Map<String, String> params = new HashMap<String, String>();
		params.put(KEY_OP, OP_OPEN);
		return super.doGetAsStream(getUri() + remoteFilePath, params);
	}

	public void upload(String localFilePath, String remoteDirectory)
			throws FileNotFoundException {
		uri = jointUri();

		File file = new File(localFilePath);
		String fullRemoteUri = remoteDirectory.trim();
		if (fullRemoteUri.endsWith("/")) {
			fullRemoteUri = (uri + fullRemoteUri + file.getName());
		} else {
			fullRemoteUri = (uri + fullRemoteUri + "/" + file.getName());
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put(KEY_USERNAME, getUser());
		params.put(KEY_OP, OP_CREATE);
		String location = super.requestNameNode(fullRemoteUri, params);
		super.requestDataNode(location, file);
	}

	@Override
	public void create(String localFilePath, String remoteFilePath)
			throws FileNotFoundException {
		uri = jointUri();

		File file = new File(localFilePath);
		String fullRemoteUri = uri + remoteFilePath.trim();
		Map<String, String> params = new HashMap<String, String>();
		params.put(KEY_USERNAME, getUser());
		params.put(KEY_OP, OP_CREATE);
		String location = super.requestNameNode(fullRemoteUri, params);
		super.requestDataNode(location, file);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
