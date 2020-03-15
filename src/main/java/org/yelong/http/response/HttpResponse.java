/**
 * 
 */
package org.yelong.http.response;

import java.util.Map;

import org.yelong.http.request.HttpRequest;

/**
 * http请求后的相应结果
 * 
 * @author PengFei
 * @date 2020年3月3日上午10:31:42
 * @since 1.0
 */
public interface HttpResponse {

	/**
	 * 获取改相应的请求
	 * @return
	 */
	HttpRequest getRequest();

	/**
	 * 获取请求头
	 * @param name header name
	 * @return 请求头信息，如果没有对应name的value则返回null
	 */
	String getHeader(String name);

	/**
	 * 获取所有的请求头
	 * 这里面不包含（"Content-Type"），它们被独立的出来
	 * @return 所有的请求头信息
	 */
	Map<String,String> getHeaders();
	
	/**
	 * 获取内容类型
	 * @return 内容类型
	 */
	String getContentType();
	
	/**
	 * 获取内容处理类型
	 * @return 内容处理类型
	 */
	String getContentDisposition();

	/**
	 * 获取响应的内容
	 * @return 响应的内容 
	 */
	byte [] getContent();

	/**
	 * 获取响应的字符串类型的内容
	 * @return 响应的字符串类型的内容
	 */
	String getContentStr();

	/**
	 * 获取响应状态码
	 * @return 响应状态码
	 */
	int getResponseCode();
	
	/**
	 * 获取编码
	 * @return 编码
	 */
	String getCharset();

}
