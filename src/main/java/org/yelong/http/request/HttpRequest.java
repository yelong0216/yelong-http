/**
 * 
 */
package org.yelong.http.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.yelong.http.request.file.FileItem;
import org.yelong.http.request.file.FileItemFactory;

/**
 * http 请求
 * 
 * @author PengFei
 * @date 2020年3月3日上午10:31:36
 * @since 1.0
 */
public interface HttpRequest {
	
	/**
	 * 请求方法
	 * 常用的方法：POST、GET
	 * @return request method
	 */
	String getMethod();
	
	/**
	 * 请求的url
	 * @return url
	 */
	String getUrl();
	
	/**
	 * 设置请求url
	 * @param url
	 */
	void setUrl(String url);
	
	/**
	 * 请求的编码。
	 * 这包含请求的编码，参数的编码等
	 * @return charset
	 */
	String getCharset();
	
	/**
	 * 设置请求的编码。
	 * 这个编码包含：请求的参数、请求等
	 * @param charset 编码
	 */
	void setCharset(String charset);
	
	/**
	 * 添加请求头
	 * 如果value为空header不会进行添加
	 * @param name header name
	 * @param value header value value不允许为空
	 * @return <tt>true</tt> value != null
	 */
	boolean addHeader(String name,String value);
	
	/**
	 * 添加请求头
	 * 循环调用{@link #addHeader(String, String)} 其value为null也不会进行添加
	 * @param headers
	 * @see #addHeader(String, String)
	 */
	void addHeaders(Map<String,String> headers);
	
	/**
	 * 移除请求头
	 * @param name header name
	 * @return 如果移除成功将返回此name对应的value，否则返回 null
	 */
	String removeHeader(String name);
	
	/**
	 * 获取请求头
	 * @param name header name
	 * @return name 对应的值，如果值不存在则返回null
	 */
	String getHeader(String name);
	
	/**
	 * 获取所有的头信息
	 * 这不包含（"Content-Type"）参数，这些参数以独立出来
	 * @return 所有的头信息
	 */
	Map<String,String> getHeaders();
	
	/**
	 * 设置内容类型
	 * 
	 * @param contentType 内容类型
	 */
	void setContentType(String contentType);
	
	/**
	 * @return get contentType
	 */
	String getContentType();
	
	/**
	 * 添加请求参数
	 * @param name param name
	 * @param value param value
	 * @return
	 */
	void addParam(String name,String value);
	
	/**
	 * 移除请求参数
	 * @param name
	 * @return
	 */
	String removeParam(String name);
	
	/**
	 * 获取参数name对应的value
	 * @param name param value
	 * @return name 对应的value，如果没有则返回null
	 */
	String getParam(String name);
	
	/**
	 * 获取所有的请求参数
	 * @return 所有的请求参数
	 */
	Map<String,String> getParams();
	
	/**
	 * 添加上传的文件
	 * @see FileItemFactory
	 * @param fieldName param name
	 * @param fileItem 文件流
	 */
	void addFileItem(String name , FileItem fileItem);
	
	/**
	 * 获取文件流
	 * @param name param name
	 * @return 文件流。如果不存在则返回null
	 */
	FileItem getFileItem(String name);
	
	/**
	 * 获取所有的文件
	 * @return
	 */
	Map<String,FileItem> getFileItems();
	
	/**
	 * 设置内容
	 * 调用此方法设置内容将改变POST请求参数的位置（原放置消息体中，由于设置了消息体则拼接在url上面）
	 * 且调用此方法设置内容后将不支持上传文件（调用{@link #addFileItem(String, FileItem)}不会出现错误，但是文件不会被上传）
	 * @param content 请求内容（也被称为消息体）
	 */
	void setContent(byte [] content);
	
	/**
	 * 获取内容
	 * 如果为GET请求这会返回一个空的字节数组
	 * @return 内容
	 * @throws IOException
	 */
	byte [] getContent() throws IOException;
	
	/**
	 * 设置字符串类型的内容
	 * @see {@link #setContent(byte[])}
	 * @param contentStr 字符串的内容
	 */
	void setContentStr(String contentStr) throws UnsupportedEncodingException;
	
	/**
	 * @see {@link #getContent()}
	 * @return contentStr 字符串的内容
	 */
	String getContentStr() throws IOException;
	
	/**
	 * 设置连接超时时间
	 * @param connectTimeout 连接超时时间(ms)
	 */
	void setConnectTimeout(int connectTimeout);
	
	/**
	 * 获取连接超时时间
	 * @return 连接超时时间(ms)
	 */
	int getConnectTimeout();
	
	/**
	 * 设置读取超时时间
	 * @param readTimeout 读取超时时间(ms)
	 */
	void setReadTimeout(int readTimeout);
	
	/**
	 * @return 读取超时时间(ms)
	 */
	int getReadTimeout();
	
}
