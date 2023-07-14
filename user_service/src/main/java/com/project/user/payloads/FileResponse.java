package com.project.user.payloads;

public class FileResponse<T> {

	T content;
	boolean isSuccessful;
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
	public boolean isSuccessful() {
		return isSuccessful;
	}
	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
	public FileResponse(T content, boolean isSuccessful) {
		super();
		this.content = content;
		this.isSuccessful = isSuccessful;
	}
	public FileResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FileResponse [content=" + content + ", isSuccessful=" + isSuccessful + "]";
	}
	
	
}

