/**
 * 
 */
package org.yelong.http.net;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * HttpURLConnection 解析器
 * 
 * @since 1.1
 */
public interface HttpURLConnectionResolver {

	/**
	 * 获取请求响应的内容
	 * 
	 * @param connection url connection
	 * @return 响应的内容
	 * @throws IOException io exception
	 */
	byte[] getResponseContent(HttpURLConnection connection) throws IOException;

	/**
	 * 获取请求或者响应的编码
	 * 
	 * @param connection url connection
	 * @return 编码
	 * @throws IOException io exception
	 */
	String getCharset(HttpURLConnection connection) throws IOException;

}
