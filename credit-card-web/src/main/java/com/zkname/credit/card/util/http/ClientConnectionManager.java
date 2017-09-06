package com.zkname.credit.card.util.http;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.zkname.core.util.exception.ExceptionUtils;

import lombok.Getter;

public class ClientConnectionManager {

	private static ClientConnectionManager clientConnectionManager;

	/**
	 * http客户端
	 */
	@Getter
	private HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

	private ClientConnectionManager() {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
		httpClientBuilder.setDefaultRequestConfig(config);
	}

	public static ClientConnectionManager getInstance() {
		if (clientConnectionManager == null) {
			synchronized (ClientConnectionManager.class) {
				if (clientConnectionManager == null) {
					clientConnectionManager = new ClientConnectionManager();
				}
			}
		}
		return clientConnectionManager;
	}

	public String postEntityJson(String url, String json) {
		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			HttpPost requestPost = new HttpPost(URI.create(url));
			requestPost.setEntity(new StringEntity(json, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(requestPost);
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (200 == statusCode) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toString(httpEntity, "UTF-8");
				}
			}
		} catch (Exception e) {
			throw ExceptionUtils.unchecked(e);
		}
		return null;
	}

	public String post(String url, Map<String, String> map) {
		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			HttpPost requestPost = new HttpPost(URI.create(url));
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> set = map.entrySet();
			for (Entry<String, String> key : set) {
				nvps.add(new BasicNameValuePair(key.getKey(), key.getValue()));
			}
			requestPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse httpResponse = httpClient.execute(requestPost);
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (200 == statusCode) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toString(httpEntity, "UTF-8");
				}
			}
		} catch (Exception e) {
			throw ExceptionUtils.unchecked(e);
		}
		return null;
	}

	public String get(String url) {
		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			HttpResponse httpResponse = httpClient.execute(new HttpGet(URI.create(url)));
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (200 == statusCode) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toString(httpEntity, "UTF-8");
				}
			}
		} catch (Exception e) {
			throw ExceptionUtils.unchecked(e);
		}
		return null;
	}

	/**
	 * 获取http 状态
	 * 
	 * @param url
	 * @return
	 */
	public int getStatusCode(String url) {
		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			HttpResponse httpResponse = httpClient.execute(new HttpGet(URI.create(url)));
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			return statusCode;
		} catch (Exception e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	public byte[] getByte(String url) {
		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			HttpResponse httpResponse = httpClient.execute(new HttpGet(URI.create(url)));
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (200 == statusCode) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return EntityUtils.toByteArray(httpEntity);
				}
			}
		} catch (Exception e) {
			throw ExceptionUtils.unchecked(e);
		}
		return null;
	}

	public InputStream getInputStream(String url) {
		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			HttpResponse httpResponse = httpClient.execute(new HttpGet(URI.create(url)));
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (200 == statusCode) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					return httpEntity.getContent();
				}
			}
		} catch (Exception e) {
			throw ExceptionUtils.unchecked(e);
		}
		return null;
	}
}
