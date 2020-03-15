/**
 * 
 */
package org.yelong.http.request.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 
 * 文件项工厂
 * 
 * @author PengFei
 * @date 2020年3月3日下午12:49:45
 * @since 1.0
 */
public class FileItemFactory {

	/**
	 * 默认的文件识别类型
	 */
	private static final String MIME_TYPE = "application/octet-stream";

	/**
	 * 通过文件创建文件项
	 * @param file 文件
	 * @return 文件项
	 * @throws IOException
	 */
	public static FileItem create(File file) throws IOException {
		return create(file, MIME_TYPE);
	}

	/**
	 * 通过文件和文件识别类型创建文件项
	 * @param file 文件
	 * @param mimeType 文件识别类型
	 * @return 文件项
	 * @throws IOException
	 */
	public static FileItem create(File file,String mimeType) throws IOException {
		byte [] content = FileUtils.readFileToByteArray(file);
		return new DefaultFileItem(file.getName(), file.length(), content, mimeType);
	}

	/**
	 * @param fileName 文件名称
	 * @param content 文件内容
	 * @return 文件项
	 */
	public static FileItem create(String fileName, byte[] content) {
		return create(fileName, content, MIME_TYPE);
	}

	/**
	 * @param fileName 文件名称
	 * @param content 文件内容
	 * @param mimeType 文件识别类型
	 * @return 文件项
	 */
	public static FileItem create(String fileName, byte[] content, String mimeType) {
		return new DefaultFileItem(fileName, content.length, content, mimeType);
	}

	/**
	 * @param fileName 文件名称
	 * @param stream 文件流
	 * @return 文件项
	 * @throws IOException
	 */
	public static FileItem create(String fileName, InputStream stream) throws IOException {
		return create(fileName, stream, MIME_TYPE);
	}

	/**
	 * @param fileName 文件名称
	 * @param stream 文件流
	 * @param mimeType 文件识别类型
	 * @return 文件项
	 * @throws IOException
	 */
	public static FileItem create(String fileName, InputStream stream, String mimeType) throws IOException{
		byte [] content = IOUtils.toByteArray(stream);
		return new DefaultFileItem(fileName, content.length, content, mimeType);
	}
}
