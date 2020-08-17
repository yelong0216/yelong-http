/**
 * 
 */
package org.yelong.http.request;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.yelong.http.Constants;
import org.yelong.http.utils.WebUtils;

/**
 * 默认的http请求实现
 * 
 * @since 1.0
 */
public class DefaultHttpRequest extends AbstractHttpReqeust {

	private String boundary;

	public DefaultHttpRequest(String url, String method) {
		super(url, method);
	}

	@Override
	public String getUrl() {
		if (method.equalsIgnoreCase("POST") && !existContent()) {
			return this.url;
		} else {
			if (this.params.isEmpty()) {
				return this.url;
			}
			try {
				if (this.url.contains("?")) {
					return this.url + "&" + WebUtils.buildQueryParam(params, charset);
				} else {
					return this.url + "?" + WebUtils.buildQueryParam(params, charset);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public String getContentType() {
		String contentType = super.getContentType();
		if (this.method.equals("GET")) {
			return null;
		}
		if (StringUtils.isEmpty(contentType)) {
			if (existFile()) {// 如果存在文件
				contentType = "multipart/form-data;charset=" + charset + ";boundary=" + getBoundary();
			} else {
				contentType = "application/x-www-form-urlencoded;charset=" + charset;
			}
			this.contentType = contentType;
		}
		return contentType;
	}

	@Override
	public byte[] getContent() throws IOException {
		if (existContent()) {
			return super.content;
		}
		byte[] content = Constants.EMPTY_BYTE_ARRAY;
		if (method.equalsIgnoreCase("POST") || existFile()) {
			if (existFile()) {// 存在文件则消息体不一样
				content = WebUtils.buildFileContent(params, fileItems, charset, getBoundary());
			} else if (!params.isEmpty()) {
				String queryParam = WebUtils.buildQueryParam(params, charset);
				if (StringUtils.isNotBlank(queryParam)) {
					content = queryParam.getBytes();
				}
			}
		}
		return content;
	}

	private boolean existFile() {
		return !this.fileItems.isEmpty();
	}

	private String getBoundary() {
		if (null == boundary) {
			this.boundary = String.valueOf(System.nanoTime());
		}
		return this.boundary;
	}

}
