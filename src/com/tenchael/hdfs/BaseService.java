package com.tenchael.hdfs;

import static com.tenchael.hdfs.util.Constants.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public abstract class BaseService {

	public String doGetAsString(String uri, Map<String, String> params) {
		JSONObject json = new JSONObject();
		try {
			// create HttpClient instance
			HttpClient httpclient = new DefaultHttpClient();
			// create Get Method instance
			HttpGet httpgets = new HttpGet(uri + jointParams(params));
			HttpResponse response = httpclient.execute(httpgets);
			Header[] headers = response.getAllHeaders();
			JSONObject headersJson = new JSONObject();

			for (int i = 0; i < headers.length; i++) {
				// buf.append(headers[i].toString() + "\n");
				String strHead = headers[i].toString();
				int index = strHead.indexOf(":");
				String attr = strHead.substring(0, index).trim();
				String value = strHead.substring(index + 1, strHead.length())
						.trim();
				headersJson.put(attr, value);
			}
			json.put(ATTR_HEADER, headersJson);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String content = convertStreamToString(instreams).trim();
				if (content.startsWith("{") && content.endsWith("}")
						&& content.contains(":")) {
					JSONObject bodyJson = JSONObject.fromObject(content);
					json.put(ATTR_BODY, bodyJson);
				} else {
					json.put(ATTR_CONTENT, content);
				}
				// Do not need the rest
				httpgets.abort();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public InputStream doGetAsStream(String uri, Map<String, String> params) {
		InputStream instreams = null;
		try { // create HttpClient instance
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(uri + jointParams(params));
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				instreams = entity.getContent(); // httpgets.abort();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instreams;
	}

	public String doPut(String uri, Map<String, String> params) {
		JSONObject json = new JSONObject();
		try {
			// create HttpClient instance
			HttpClient httpclient = new DefaultHttpClient();
			HttpPut httpput = new HttpPut(uri + jointParams(params));
			HttpResponse response = httpclient.execute(httpput);

			Header[] headers = response.getAllHeaders();
			JSONObject headersJson = new JSONObject();

			for (int i = 0; i < headers.length; i++) {
				// buf.append(headers[i].toString() + "\n");
				String strHead = headers[i].toString();
				int index = strHead.indexOf(":");
				String attr = strHead.substring(0, index).trim();
				String value = strHead.substring(index + 1, strHead.length())
						.trim();
				headersJson.put(attr, value);
			}
			json.put("header", headersJson);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String content = convertStreamToString(instreams);
				if (null != content && !("".equals(content.trim()))) {
					JSONObject contentJson = JSONObject.fromObject(content);
					json.put("content", contentJson);
				}

				// Do not need the rest
				httpput.abort();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public String doPost(String uri, Map<String, String> params) {
		// not implement this method
		return null;
	}

	public String doDelete(String uri, Map<String, String> params) {
		JSONObject json = new JSONObject();
		try {
			// create HttpClient instance
			HttpClient httpclient = new DefaultHttpClient();
			HttpDelete httpdel = new HttpDelete(uri + jointParams(params));
			HttpResponse response = httpclient.execute(httpdel);

			Header[] headers = response.getAllHeaders();
			JSONObject headersJson = new JSONObject();

			for (int i = 0; i < headers.length; i++) {
				// buf.append(headers[i].toString() + "\n");
				String strHead = headers[i].toString();
				int index = strHead.indexOf(":");
				String attr = strHead.substring(0, index).trim();
				String value = strHead.substring(index + 1, strHead.length())
						.trim();
				headersJson.put(attr, value);
			}
			json.put("header", headersJson);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				JSONObject contentJson = JSONObject
						.fromObject(convertStreamToString(instreams));
				json.put("content", contentJson);
				// Do not need the rest
				httpdel.abort();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	private String jointParams(Map<String, String> params) {
		StringBuffer buf = new StringBuffer("");
		if (params != null && params.size() > 0) {
			Iterator<String> iter = params.keySet().iterator();
			buf.append("?");
			while (iter.hasNext()) {
				String key = iter.next();
				buf.append(key);
				buf.append("=");
				buf.append(params.get(key));
				buf.append("&");
			}
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/*protected String requestNameNode(File file, String uri,
			Map<String, String> params) {
		uri = uri.trim();
		if (uri.endsWith("/")) {
			uri += file.getName();
		} else {
			uri += ("/" + file.getName());
		}

		String json = doPut(uri, params);
		JSONObject bean = JSONObject.fromObject(json);
		Object obj = bean.get(ATTR_HEADER);
		JSONObject header = JSONObject.fromObject(obj);
		return header.getString("Location");
	}*/

	protected String requestNameNode(String uri, Map<String, String> params) {
		uri = uri.trim();
		String json = doPut(uri, params);
		JSONObject bean = JSONObject.fromObject(json);
		Object obj = bean.get(ATTR_HEADER);
		JSONObject header = JSONObject.fromObject(obj);
		return header.getString("Location");
	}

	protected void requestDataNode(String uri, File file) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPut httpput = new HttpPut(uri); // HttpPost httpput = new
												// HttpPost(uri);

			System.out.println(file.length());

			HttpEntity reqEntity = EntityBuilder.create().setFile(file).build();

			System.out.println(reqEntity.getContentLength());

			httpput.setEntity(reqEntity);

			System.out.println("executing request " + httpput.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpput);

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				System.out.println("Response content length: "
						+ resEntity.getContentLength());
			}
			String responseContent = EntityUtils.toString(resEntity);
			System.out.println(responseContent);
			EntityUtils.consume(resEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}