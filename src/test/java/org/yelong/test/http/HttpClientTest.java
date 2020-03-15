/**
 * 
 */
package org.yelong.test.http;

import java.io.File;
import java.io.IOException;

import org.yelong.http.client.DefaultHttpClient;
import org.yelong.http.client.HttpClient;
import org.yelong.http.exception.HttpException;
import org.yelong.http.request.HttpRequest;
import org.yelong.http.request.HttpRequestFactory;
import org.yelong.http.request.file.FileItemFactory;
import org.yelong.http.response.HttpResponse;
import org.yelong.http.utils.WebUtils;

/**
 * @author PengFei
 * @date 2020年3月3日下午12:16:34
 * @since 1.0
 */
public class HttpClientTest {
	
	public static void main(String[] args) throws HttpException, IOException  {
		test2();
	}
	
	public static void test1() throws HttpException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpRequest request = HttpRequestFactory.create("http://www.baidu.com", "GET");
		request.addParam("page", "1");
		request.addParam("limit", "20");
		HttpResponse response = httpClient.execute(request);
		System.out.println(response.getRequest().getUrl());
		System.out.println(response.getContentStr());
		System.out.println(response.getResponseCode());
	}
	
	public static void test2() throws HttpException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpRequest request = HttpRequestFactory.create("http://localhost:12510/upload", "POST");
		request.addFileItem("file", FileItemFactory.create(new File("C:\\Users\\14308\\Desktop\\zigui\\彭飞.txt")));
		request.addParam("abc", "彭飞fafdsafsd");
		//request.setContentType("multipart/form-data; boundary=----WebKitFormBoundaryari0emH33oMihIU4");
		System.out.println(new String(request.getContent()));
		System.out.println(request.getContentType());
		HttpResponse response = httpClient.execute(request);
		System.out.println(response.getRequest().getUrl());
		System.out.println(response.getContentStr());
		System.out.println(response.getResponseCode());
	}
	
	public static void test3() throws HttpException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpRequest request = HttpRequestFactory.create("http://localhost:12510/download", "GET");
		//request.setContentType("multipart/form-data; boundary=----WebKitFormBoundaryari0emH33oMihIU4");
		HttpResponse response = httpClient.execute(request);
		WebUtils.getResponseFile(response, "D:\\");
	}
	
}
