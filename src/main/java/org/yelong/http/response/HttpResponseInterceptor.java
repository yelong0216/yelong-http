/**
 * 
 */
package org.yelong.http.response;

import java.io.IOException;

import org.yelong.http.exception.HttpException;

/**
 * @author PengFei
 * @date 2020年3月5日上午10:40:26
 * @since 1.0
 */
public interface HttpResponseInterceptor {

	/**
	 * 在请求执行后执行
	 * @param response
	 * @throws HttpException
	 * @throws IOException
	 */
	void process(HttpResponse response) throws HttpException, IOException;

}
