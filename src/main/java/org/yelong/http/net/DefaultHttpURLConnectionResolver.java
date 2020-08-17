/**
 * 
 */
package org.yelong.http.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.http.Constants;
import org.yelong.http.utils.WebUtils;

/**
 * 默认实现
 * 
 * @since 1.1
 */
public class DefaultHttpURLConnectionResolver implements HttpURLConnectionResolver {

	public static final HttpURLConnectionResolver INSTANCE = new DefaultHttpURLConnectionResolver();

	@Override
	public byte[] getResponseContent(HttpURLConnection connection) throws IOException {
		String charset = WebUtils.getCharset(connection);
		if (connection.getResponseCode() != 200) {
			InputStream error = connection.getErrorStream();
			if (error != null) {
				return IOUtils.toByteArray(new InputStreamReader(error), charset);
			}
		}
		InputStream inputStream = connection.getInputStream();
		if (null != inputStream) {
			String contentEncoding = connection.getContentEncoding();
			if ("gzip".equalsIgnoreCase(contentEncoding)) {
				return IOUtils.toByteArray(new InputStreamReader(new GZIPInputStream(inputStream)), charset);
			}
			if (null != inputStream) {
				return IOUtils.toByteArray(inputStream);
			}
		}
		return Constants.EMPTY_BYTE_ARRAY;
	}

	@Override
	public String getCharset(HttpURLConnection connection) throws IOException {
		String charset = Constants.DEFAULT_CHARSET;
		String contentType = connection.getContentType();
		if (!StringUtils.isEmpty(contentType)) {
			String[] params = contentType.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if ((pair.length != 2) || (StringUtils.isEmpty(pair[1]))) {
						break;
					}
					charset = pair[1].trim();
					break;
				}
			}
		}
		return charset;
	}

}
