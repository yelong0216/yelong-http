/**
 * 
 */
package org.yelong.http.request.file;

/**
 * 文件项 包装请求上传的文件
 * 
 * @since 1.0
 */
public interface FileItem {

	/**
	 * 获取文件名称
	 * 
	 * @return file name
	 */
	String getFileName();

	/**
	 * 获取文件的大小（长度）（单位：b）
	 * 
	 * @return file length
	 */
	long getFileLength();

	/**
	 * 获取文件的内容
	 * 
	 * @return 文件内容
	 */
	byte[] getContent();

	/**
	 * 文件识别类型
	 * 
	 * @return 文件识别类型
	 */
	String getMimeType();

}
