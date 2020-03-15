/**
 * 
 */
package org.yelong.http.request;

import java.io.IOException;

import org.yelong.http.exception.HttpException;

/**
 * @author PengFei
 * @date 2020年3月5日上午10:39:30
 * @since 1.0
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
