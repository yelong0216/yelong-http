/**
 * 
 */
package org.yelong.http.client;

import java.io.IOException;
import java.util.List;

import org.yelong.http.exception.HttpException;
import org.yelong.http.request.HttpRequest;
import org.yelong.http.request.HttpRequestInterceptor;
import org.yelong.http.response.HttpResponse;
import org.yelong.http.response.HttpResponseInterceptor;

/**
 * http 客户端
 * 
 * 执行请求并返回请求的相应结果
 * 
 * @since 1.0
 */
public interface HttpClient {

	/**
	 * 执行request的请求，并返回请求的相应结果 <br/>
	 * 
	 * 常用地方：发送http请求
	 * 
	 * @param request 请求
	 * @return 响应
	 * @throws HttpException 请求发送失败或者request是一个错误的请求
	 */
	HttpResponse execute(HttpRequest request) throws HttpException, IOException;

	/**
	 * 添加请求拦截器
	 * 
	 * @param httpRequestInterceptor
	 */
	void addHttpRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor);

	/**
	 * 移除请求拦截器
	 * 
	 * @param httpRequestInterceptor
	 */
	void removeHttpRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor);

	/**
	 * 获取所有请求拦截器
	 * 
	 * @return
	 */
	List<HttpRequestInterceptor> getHttpRequestInterceptors();

	/**
	 * 添加响应拦截器
	 * 
	 * @param HttpResponseInterceptor
	 */
	void addHttpResponseInterceptor(HttpResponseInterceptor HttpResponseInterceptor);

	/**
	 * 移除响应拦截器
	 * 
	 * @param HttpResponseInterceptor
	 */
	void removeHttpResponseInterceptor(HttpResponseInterceptor HttpResponseInterceptor);

	/**
	 * 获取所有响应拦截器
	 * 
	 * @return
	 */
	List<HttpResponseInterceptor> getHttpResponseInterceptors();

}
