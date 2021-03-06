/**
 * 
 */
package org.yelong.http.client;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.yelong.http.Constants;
import org.yelong.http.exception.HttpException;
import org.yelong.http.request.HttpRequest;
import org.yelong.http.request.HttpRequestInterceptor;
import org.yelong.http.response.DefaultHttpResponseFactory;
import org.yelong.http.response.HttpResponse;
import org.yelong.http.response.HttpResponseFactory;
import org.yelong.http.response.HttpResponseInterceptor;

/**
 * 默认的 http 客户端实现
 * 
 * @since 1.0
 */
public class DefaultHttpClient extends AbstractHttpClient implements HttpClient {

	public static final String DEFAULT_CHARSET = Constants.DEFAULT_CHARSET;

	private HttpResponseFactory httpResponseFactory;
	
	private static boolean ignoreSSLCheck = true;

	private static boolean ignoreHostCheck = true;

	public DefaultHttpClient() {
		this(DefaultHttpResponseFactory.INSTANCE);
	}
	
	public DefaultHttpClient(HttpResponseFactory httpResponseFactory) {
		this.httpResponseFactory = httpResponseFactory;
	}
	
	@Override
	public HttpResponse execute(HttpRequest request) throws HttpException, IOException {
		for (HttpRequestInterceptor httpRequestInterceptor : getHttpRequestInterceptors()) {
			httpRequestInterceptor.process(request);
		}
		HttpURLConnection conn = null;
		OutputStream out = null;
		try {
			conn = getConnection(new URL(request.getUrl()), request.getMethod(), request.getContentType(),
					request.getHeaders(), null);
			conn.setConnectTimeout(request.getConnectTimeout());
			conn.setReadTimeout(request.getReadTimeout());
			byte[] content = request.getContent();
			if (content != null && content != Constants.EMPTY_BYTE_ARRAY) {
				out = conn.getOutputStream();
				out.write(content);
			}
			conn.connect();
			HttpResponse httpResponse = buildHttpResponse(conn, request);
			for (HttpResponseInterceptor httpResponseInterceptor : getHttpResponseInterceptors()) {
				httpResponseInterceptor.process(httpResponse);
			}
			return httpResponse;
		} finally {
			close(out);
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * 构建响应结果
	 * 
	 * @param conn http connection
	 * @param request request
	 * @return 响应结果
	 * @throws IOException 构建响应结果失败
	 */
	protected HttpResponse buildHttpResponse(HttpURLConnection conn ,HttpRequest request) throws IOException {
		return httpResponseFactory.create(conn, request);
	}
	
	/**
	 * 获取连接对象
	 */
	protected static HttpURLConnection getConnection(URL url, String method, String contentType,
			Map<String, String> headerMap, Proxy proxy) throws IOException {
		HttpURLConnection conn = null;
		if (proxy == null) {
			conn = (HttpURLConnection) url.openConnection();
		} else {
			conn = (HttpURLConnection) url.openConnection(proxy);
		}
		if ((conn instanceof HttpsURLConnection)) {
			HttpsURLConnection connHttps = (HttpsURLConnection) conn;
			if (ignoreSSLCheck) {
				try {
					SSLContext ctx = SSLContext.getInstance("TLS");
					ctx.init(null, new TrustManager[] { new TrustAllTrustManager() }, new SecureRandom());
					connHttps.setSSLSocketFactory(ctx.getSocketFactory());
					connHttps.setHostnameVerifier(new HostnameVerifier() {
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					});
				} catch (Exception e) {
					throw new IOException(e.toString());
				}
			} else if (ignoreHostCheck) {
				connHttps.setHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
			}
			conn = connHttps;
		}
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		// conn.setUseCaches(false);
		conn.setRequestProperty("Accept", "text/xml,text/javascript");
		conn.setRequestProperty("User-Agent", "top-sdk-java");
		if (null != contentType) {
			conn.setRequestProperty("Content-Type", contentType);
		}
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			conn.setRequestProperty(entry.getKey(), entry.getValue());
		}
		// if ((headerMap != null) && (headerMap.get("TOP_HTTP_DNS_HOST") != null)) {
		// conn.setRequestProperty("Host", (String)headerMap.get("TOP_HTTP_DNS_HOST"));
		// } else {
		// conn.setRequestProperty("Host", url.getHost());
		// }
		// conn.setRequestProperty("Accept", "text/xml,text/javascript");
		// conn.setRequestProperty("User-Agent", "top-sdk-java");
		// if (headerMap != null) {
		// for (Map.Entry<String, String> entry : headerMap.entrySet()) {
		// if (!"TOP_HTTP_DNS_HOST".equals(entry.getKey())) {
		// conn.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
		// }
		// }
		// }
		return conn;
	}

	/**
	 * 关闭
	 */
	public void close(Closeable closeable) throws HttpException {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
				throw new HttpException(e);
			}
		}
	}

	public static boolean isIgnoreSSLCheck() {
		return ignoreSSLCheck;
	}

	public static void setIgnoreSSLCheck(boolean ignoreSSLCheck) {
		DefaultHttpClient.ignoreSSLCheck = ignoreSSLCheck;
	}

	public static boolean isIgnoreHostCheck() {
		return ignoreHostCheck;
	}

	public static void setIgnoreHostCheck(boolean ignoreHostCheck) {
		DefaultHttpClient.ignoreHostCheck = ignoreHostCheck;
	}

	public static class TrustAllTrustManager implements X509TrustManager {

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}

	}

}
