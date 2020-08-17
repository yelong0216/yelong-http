/**
 * 
 */
package org.yelong.http.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.yelong.http.Constants;
import org.yelong.http.request.file.FileItem;

/**
 * http 请求的抽象实现
 * 
 * @since 1.0
 */
public abstract class AbstractHttpReqeust implements HttpRequest {

	protected String url;

	protected final String method;

	protected String charset = Constants.DEFAULT_CHARSET;

	protected String contentType;

	protected byte[] content = Constants.EMPTY_BYTE_ARRAY;

	protected Map<String, String> headers = new HashMap<>();

	protected Map<String, String> params = new HashMap<>();

	protected Map<String, FileItem> fileItems = new HashMap<>();

	protected int connectTimeout = 15000;

	protected int readTimeout = 30000;

	public AbstractHttpReqeust(String url, String method) {
		this.url = url;
		this.method = method;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getCharset() {
		return this.charset;
	}

	@Override
	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public boolean addHeader(String name, String value) {
		if (null == value) {
			return false;
		}
		this.headers.put(name, value);
		return true;
	}

	@Override
	public void addHeaders(Map<String, String> headers) {
		for (Entry<String, String> entry : headers.entrySet()) {
			addHeader(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public String removeHeader(String name) {
		return this.headers.remove(name);
	}

	@Override
	public String getHeader(String name) {
		return this.headers.get(name);
	}

	@Override
	public Map<String, String> getHeaders() {
		return this.headers;
	}

	@Override
	public void addParam(String name, String value) {
		this.params.put(name, value);
	}

	@Override
	public String removeParam(String name) {
		return this.params.remove(name);
	}

	@Override
	public String getParam(String name) {
		return this.params.get(name);
	}

	@Override
	public Map<String, String> getParams() {
		return this.params;
	}

	@Override
	public void addFileItem(String fieldName, FileItem fileItem) {
		this.fileItems.put(fieldName, fileItem);
	}

	@Override
	public FileItem getFileItem(String fieldName) {
		return this.fileItems.get(fieldName);
	}

	@Override
	public Map<String, FileItem> getFileItems() {
		return this.fileItems;
	}

	@Override
	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public void setContentStr(String contentStr) throws UnsupportedEncodingException {
		this.content = contentStr.getBytes(charset);
	}

	@Override
	public String getContentStr() throws IOException {
		return new String(getContent(), charset);
	}

	@Override
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	@Override
	public int getConnectTimeout() {
		return this.connectTimeout;
	}

	@Override
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	@Override
	public int getReadTimeout() {
		return this.readTimeout;
	}

	public boolean existContent() {
		return this.content != null && this.content != Constants.EMPTY_BYTE_ARRAY;
	}

}
