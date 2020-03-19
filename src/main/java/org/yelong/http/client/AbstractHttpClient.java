package org.yelong.http.client;

import java.util.ArrayList;
import java.util.List;

import org.yelong.http.request.HttpRequestInterceptor;
import org.yelong.http.response.HttpResponseInterceptor;

/**
 * 抽象的http 客户端实现
 * @author PengFei
 */
public abstract class AbstractHttpClient implements HttpClient{

	private final List<HttpRequestInterceptor> httpRequestInterceptors = new ArrayList<>();
	
	private final List<HttpResponseInterceptor> httpResponseInterceptors = new ArrayList<>();
	
	@Override
	public void addHttpRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
		this.httpRequestInterceptors.add(httpRequestInterceptor);
	}

	@Override
	public void removeHttpRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
		this.httpRequestInterceptors.remove(httpRequestInterceptor);
	}

	@Override
	public List<HttpRequestInterceptor> getHttpRequestInterceptors() {
		return this.httpRequestInterceptors;
	}

	@Override
	public void addHttpResponseInterceptor(HttpResponseInterceptor HttpResponseInterceptor) {
		this.httpResponseInterceptors.add(HttpResponseInterceptor);
	}

	@Override
	public void removeHttpResponseInterceptor(HttpResponseInterceptor HttpResponseInterceptor) {
		this.httpResponseInterceptors.remove(HttpResponseInterceptor);
	}

	@Override
	public List<HttpResponseInterceptor> getHttpResponseInterceptors() {
		return this.httpResponseInterceptors;
	}

}
