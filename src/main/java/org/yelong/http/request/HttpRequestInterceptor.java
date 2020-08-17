/**
 * 
 */
package org.yelong.http.request;

import java.io.IOException;

import org.yelong.http.exception.HttpException;

/**
 * 请求拦截器
 * 
 * @since 1.0
 */
public interface HttpRequestInterceptor {

	/**
	 * 在发送请求之前执行
	 * 
	 * @param request request
	 */
	void process(HttpRequest request) throws HttpException, IOException;

}
