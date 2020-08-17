/**
 * 
 */
package org.yelong.http.exception;

/**
 * http请求异常
 * 
 * @since 1.0
 */
public class HttpException extends Exception {

	private static final long serialVersionUID = -3361088021953817845L;

	public HttpException() {

	}

	public HttpException(String message) {
		super(message);
	}

	public HttpException(Throwable t) {
		super(t);
	}

	public HttpException(String message, Throwable cause) {
		super(message, cause);
	}

}
