/**
 * 
 */
package org.yelong.http.response;

import java.io.IOException;

import org.yelong.http.exception.HttpException;

/**
 * @author PengFei
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
