/**
 * 
 */
package org.yelong.http.request.file;

/**
 * @since 1.0
 */
public class DefaultFileItem implements FileItem {

	private String fileName;

	private long fileLength;

	private byte[] content;

	private String mimeType;

	public DefaultFileItem(String fileName, long fileLength, byte[] content, String mimeType) {
		this.fileName = fileName;
		this.fileLength = fileLength;
		this.content = content;
		this.mimeType = mimeType;
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public long getFileLength() {
		return this.fileLength;
	}

	@Override
	public byte[] getContent() {
		return this.content;
	}

	@Override
	public String getMimeType() {
		return this.mimeType;
	}

}
