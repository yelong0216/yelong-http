/**
 * 
 */
package org.yelong.http.request;

/**
 * http 请求工厂
 * 
 * @since 1.0
 */
public class HttpRequestFactory {

	/**
	 * 创建默认的http请求
	 * 
	 * @param url    url (http://www.baidu.com)
	 * @param method 请求method (GET、POST)
	 * @return request
	 */
	public static HttpRequest create(String url, String method) {
		return new DefaultHttpRequest(url, method);
	}

}
