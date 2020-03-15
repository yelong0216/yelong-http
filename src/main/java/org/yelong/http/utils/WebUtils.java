/**
 * 
 */
package org.yelong.http.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.http.Constants;
import org.yelong.http.exception.HttpException;
import org.yelong.http.request.file.FileItem;
import org.yelong.http.response.HttpResponse;

/**
 * @author PengFei
 * @date 2020年3月3日上午11:14:56
 * @since 1.0
 */
public final class WebUtils {

	/**
	 * 建造查询参数
	 * 格式：key1=value1&key2=value2%key3=value3
	 * @param paramMap
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String buildQueryParam(Map<String,String> paramMap,String charset) throws UnsupportedEncodingException {
		if (MapUtils.isEmpty(paramMap)) {
			return null;
		}
		StringBuilder queryParam = new StringBuilder();
		Set<Map.Entry<String, String>> entries = paramMap.entrySet();
		boolean hasParam = false;
		for (Map.Entry<String, String> entry : entries)
		{
			String name = (String)entry.getKey();
			String value = (String)entry.getValue();
			if (!StringUtils.isAnyEmpty( name, value )){
				if (hasParam) {
					queryParam.append("&");
				} else {
					hasParam = true;
				}
				queryParam.append(name).append("=").append(URLEncoder.encode(value, charset));
			}
		}
		return queryParam.toString();
	}

	/**
	 * 获取相应的编码
	 * @param conn
	 * @return
	 */
	public static String getCharset(HttpURLConnection conn){
		String charset = "UTF-8";//默认为UTF-8
		String contentType = conn.getContentType();
		if (!StringUtils.isEmpty(contentType)) {
			String[] params = contentType.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if ((pair.length != 2) || 
							(StringUtils.isEmpty(pair[1]))) {
						break;
					}
					charset = pair[1].trim(); break;
				}
			}
		}
		return charset;
	}

	/**
	 * 获取相应文本
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	public static byte [] getResponseContent(HttpURLConnection conn)
			throws IOException {
		String charset = WebUtils.getCharset(conn);
		if (conn.getResponseCode() < 400) {
			String contentEncoding = conn.getContentEncoding();
			if ("gzip".equalsIgnoreCase(contentEncoding)) {
				return IOUtils.toByteArray(new InputStreamReader(new GZIPInputStream(conn.getInputStream())),charset);
			}
			//return IOUtils.toByteArray(new InputStreamReader(conn.getInputStream()), charset);
			return IOUtils.toByteArray(conn.getInputStream());
		} 
		if (conn.getResponseCode() == 400) {
			InputStream error = conn.getErrorStream();
			if (error != null) {
				return IOUtils.toByteArray(new InputStreamReader(error),charset);
			}
		}
		//throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
		return Constants.EMPTY_BYTE_ARRAY;
	}

	/**
	 * 构建上传文件时的内容体
	 * @return
	 * @throws IOException 
	 */
	public static byte [] buildFileContent(Map<String,String> params,Map<String,FileItem> fileItems,String charset,String boundary) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n").getBytes(charset);
		Set<Map.Entry<String, String>> textEntrySet = params.entrySet();
		for (Map.Entry<String, String> textEntry : textEntrySet) {
			byte[] textBytes = getTextEntry((String)textEntry.getKey(), (String)textEntry.getValue(), charset);
			out.write(entryBoundaryBytes);
			out.write(textBytes);
		}
		Set<Map.Entry<String, FileItem>> fileEntrySet = fileItems.entrySet();
		for (Map.Entry<String, FileItem> fileEntry : fileEntrySet) {
			FileItem fileItem = (FileItem)fileEntry.getValue();
			byte[] fileBytes = getFileEntry((String)fileEntry.getKey(), fileItem.getFileName(), fileItem.getMimeType(), charset);
			out.write(entryBoundaryBytes);
			out.write(fileBytes);
			out.write(fileItem.getContent());
		}
		byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n").getBytes(charset);
		out.write(endBoundaryBytes);
		return out.toByteArray();
	}

	private static byte[] getTextEntry(String fieldName, String fieldValue, String charset)
			throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
		entry.append(fieldValue);
		return entry.toString().getBytes(charset);
	}

	private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset)
			throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\";filename=\"");
		entry.append(fileName);
		entry.append("\"\r\nContent-Type:");
		entry.append(mimeType);
		entry.append("\r\n\r\n");
		return entry.toString().getBytes(charset);
	}

	
	/**
	 * 获取响应中的文件
	 * @author 彭飞
	 * @date 2019年10月28日上午11:19:14
	 * @version 1.2
	 * @param responseEntity 响应结果
	 * @param filePath 存储文件的路径。不包含文件名称
	 * @return
	 * @throws HttpException 响应的不是一个文件
	 * @throws IOException 
	 */
	public static File getResponseFile( HttpResponse httpResponse , String filePath ) throws HttpException, IOException{
		String contentDisposition = httpResponse.getContentDisposition();
		String fileName = "";
		if( contentDisposition.indexOf("fileName") != -1 ) {
			int index = contentDisposition.indexOf("fileName");
			fileName = contentDisposition.substring(index, contentDisposition.indexOf(";", index) == -1 ? contentDisposition.length(): contentDisposition.indexOf(";", index)-1 );
//			Pattern pattern = Pattern.compile("\"(.*?)\"");
//			Matcher matcher = pattern.matcher(fileName);
//			while(matcher.find()) {
//				fileName = matcher.group().trim().replace("\"", "");
//			}
			fileName = fileName.substring("fileName=".length());
		} else{
			throw new RuntimeException("响应中的未发现文件名称！");
		}
		String charset = httpResponse.getCharset();
		File file = new File(filePath,URLDecoder.decode(fileName,charset));
		if(!file.exists()) {
			file.createNewFile();
		}
		FileUtils.writeByteArrayToFile(file, httpResponse.getContent());
		return file;
	}
	
	/**
	 * 获取响应中的文件
	 * @author 彭飞
	 * @date 2019年10月28日上午11:19:14
	 * @version 1.2
	 * @param responseEntity 响应结果
	 * @param filePath 存储文件的路径。不包含文件名称
	 * @return
	 * @throws HttpException 响应的不是一个文件
	 * @throws IOException 
	 */
	public static String getResponseFileName(HttpResponse httpResponse){
		String contentDisposition = httpResponse.getContentDisposition();
		String fileName = "";
		if( contentDisposition.indexOf("fileName") != -1 ) {
			int index = contentDisposition.indexOf("fileName");
			fileName = contentDisposition.substring(index, contentDisposition.indexOf(";", index) == -1 ? contentDisposition.length(): contentDisposition.indexOf(";", index)-1 );
			fileName = fileName.substring("fileName=".length());
		} else{
			throw new RuntimeException("响应中的未发现文件名称！");
		}
		return fileName;
	}
	
	
}
