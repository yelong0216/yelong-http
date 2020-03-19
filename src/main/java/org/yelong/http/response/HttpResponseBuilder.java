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

import org.yelong.http.request.HttpRequest;
import org.yelong.http.utils.WebUtils;

/**
 * @author PengFei
 */
public class HttpResponseBuilder {

	public static HttpResponse build(HttpURLConnection conn) throws IOException {
		return build(conn, null);
	}

	public static HttpResponse build(HttpURLConnection conn,HttpRequest request) throws IOException {
		//响应消息体头
		Map<String,String> headers = new HashMap<>();
		Map<String, List<String>> headerFields = conn.getHeaderFields();
		for (Entry<String, List<String>> entry : headerFields.entrySet()) {
			String value = entry.getValue().stream().collect(Collectors.joining(","));
			headers.put(entry.getKey(), value);
		}
		byte [] content = WebUtils.getResponseContent(conn);
		String charset = WebUtils.getCharset(conn);
		int responseCode = conn.getResponseCode();
		return new DefaultHttpResponse(request, headers, content, charset, responseCode);
	}

}
