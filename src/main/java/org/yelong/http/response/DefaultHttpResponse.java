/**
 * 
 */
package org.yelong.http.response;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.yelong.http.request.HttpRequest;

/**
 * 默认的http响应
 * 
 * @since 1.0
 */
public class DefaultHttpResponse implements HttpResponse {

	private final HttpRequest request;

	private final Map<String, String> headers;

	private final byte[] content;

	private final String charset;

	private final int responseCode;

	public DefaultHttpResponse(HttpRequest request, Map<String, String> headers, byte[] content, String charset,
			int responseCode) {
		this.request = request;
		this.headers = headers;
		this.content = content;
		this.charset = charset;
		this.responseCode = responseCode;
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
	public String getContentDisposition() {
		return this.headers.get("Content-Disposition");
	}

	@Override
	public String getContentType() {
		return this.headers.get("Content-Type");
	}

	@Override
	public byte[] getContent() {
		return this.content;
	}

	@Override
	public String getContentStr() {
		try {
			return new String(this.content, charset);
		} catch (UnsupportedEncodingException e) {
			return new String(this.content);
		}
	}

	@Override
	public int getResponseCode() {
		return this.responseCode;
	}

	@Override
	public HttpRequest getRequest() {
		return this.request;
	}

	@Override
	public String getCharset() {
		return this.charset;
	}

}
