/**
 * 
 */
package org.yelong.http.request;

import java.io.IOException;

import org.yelong.http.exception.HttpException;

/**
 * @author PengFei
 */
public interface HttpRequestInterceptor {

	/**
	 * 在发送请求之前执行
	 * @param request
	 * @throws HttpException
	 * @throws IOException
	 */
	void process(HttpRequest request) throws HttpException, IOException;
	
}
