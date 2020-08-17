/**
 * 
 */
package org.yelong.http.response;

import java.io.IOException;

import org.yelong.http.exception.HttpException;

/**
 * 响应拦截器
 * 
 * @since 1.0
 */
public interface HttpResponseInterceptor {

	/**
	 * 在请求执行后执行
	 * 
	 * @param response 响应结果
	 */
	void process(HttpResponse response) throws HttpException, IOException;

}
