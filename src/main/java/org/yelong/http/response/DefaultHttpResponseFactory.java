/**
 * 
 */
package org.yelong.http.response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.yelong.http.net.DefaultHttpURLConnectionResolver;
import org.yelong.http.net.HttpURLConnectionResolver;
import org.yelong.http.request.HttpRequest;

/**
 * 响应工厂的默认实现
 * 
 * @since 1.1
 */
public class DefaultHttpResponseFactory implements HttpResponseFactory {

	private HttpURLConnectionResolver httpURLConnectionResolver;

	public static final HttpResponseFactory INSTANCE = new DefaultHttpResponseFactory();

	public DefaultHttpResponseFactory() {
		this(DefaultHttpURLConnectionResolver.INSTANCE);
	}
	
	public DefaultHttpResponseFactory(HttpURLConnectionResolver httpURLConnectionResolver) {
		this.httpURLConnectionResolver = httpURLConnectionResolver;
	}

	@Override
	public HttpResponse create(HttpURLConnection connection) throws IOException {
		return create(connection, null);
	}

	@Override
	public HttpResponse create(HttpURLConnection connection, HttpRequest request) throws IOException {
		// 响应消息体头
		Map<String, String> headers = new HashMap<>();
		Map<String, List<String>> headerFields = connection.getHeaderFields();
		for (Entry<String, List<String>> entry : headerFields.entrySet()) {
			String value = entry.getValue().stream().collect(Collectors.joining(","));
			headers.put(entry.getKey(), value);
		}
		byte[] content = httpURLConnectionResolver.getResponseContent(connection);
		String charset = httpURLConnectionResolver.getCharset(connection);
		int responseCode = connection.getResponseCode();
		return new DefaultHttpResponse(request, headers, content, charset, responseCode);
	}

	@Override
	public HttpURLConnectionResolver getHttpURLConnectionResolver() {
		return httpURLConnectionResolver;
	}
	
	public void setHttpURLConnectionResolver(HttpURLConnectionResolver httpURLConnectionResolver) {
		this.httpURLConnectionResolver = httpURLConnectionResolver;
	}

}
