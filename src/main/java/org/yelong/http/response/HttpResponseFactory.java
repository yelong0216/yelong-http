/**
 * 
 */
package org.yelong.http.response;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.yelong.http.net.HttpURLConnectionResolver;
import org.yelong.http.request.HttpRequest;

/**
 * 响应工厂
 * 
 * @since 1.1
 */
public interface HttpResponseFactory {

	/**
	 * 创建 http response
	 * 
	 * @param connection url connection
	 * @return http response
	 * @throws IOException io exception
	 */
	HttpResponse create(HttpURLConnection connection) throws IOException;

	/**
	 * 创建 http response 
	 * 
	 * @param connection url connection
	 * @param request request
	 * @return http response
	 * @throws IOException io exception
	 */
	HttpResponse create(HttpURLConnection connection, HttpRequest request) throws IOException;

	/**
	 * @return HttpURLConnection 解析器
	 */
	HttpURLConnectionResolver getHttpURLConnectionResolver();

}
