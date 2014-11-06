package com.tenchael.hdfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.tenchael.hdfs.util.NameRules;

public class WebHdfsConnector extends BaseService implements HdfsConnector {
	private String host;
	private int port;
	private String user;
	private String uri;

	/**
	 * Constructor method
	 * @param host ip adress or hostname
	 * @param port port number
	 * @param user user name
	 */
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
		Map<String, String> params = new HashMap<String, String>();
		params.put(NameRules.KEY_OP, NameRules.OP_GETFILESTATUS);
		return super.doGetAsString(getUri() + remotePath, params);
	}

	public String mkdir(String remotePath) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(NameRules.KEY_OP, NameRules.OP_MKDIRS);
		params.put(NameRules.KEY_USERNAME, getUser());
		return super.doPut(getUri() + remotePath, params);
	}

	public String rename(String oldName, String newName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(NameRules.KEY_OP, NameRules.OP_RENAME);
		params.put(NameRules.KEY_USERNAME, getUser());
		params.put(NameRules.KEY_DESTINATION, newName);
		return super.doPut(getUri() + oldName, params);
	}

	public String delete(String remotePath) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(NameRules.KEY_OP, NameRules.OP_DELETE);
		params.put(NameRules.KEY_USERNAME, getUser());
		return super.doDelete(getUri() + remotePath, params);
	}

	public String list(String remoteDirectory) {
		Map<String, String> params = new HashMap<String, String>();
		params.put(NameRules.KEY_OP, NameRules.OP_LISTSTATUS);
		return super.doGetAsString(getUri() + remoteDirectory, params);
	}

	public InputStream download(String remoteFilePath) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put(NameRules.KEY_OP, NameRules.OP_OPEN);
		return super.doGetAsStream(getUri() + remoteFilePath, params);
	}

	public String upload(String localFilePath, String remoteDirectory)
			throws FileNotFoundException {
		Map<String, String> params = new HashMap<String, String>();
		params.put(NameRules.KEY_USERNAME, getUser());
		params.put(NameRules.KEY_OP, NameRules.OP_CREATE);
		return super.create(new File(localFilePath),
				getUri() + remoteDirectory, params);
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
