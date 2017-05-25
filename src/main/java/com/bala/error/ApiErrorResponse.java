package com.bala.error;

public class ApiErrorResponse extends Exception {


	private static final long serialVersionUID = 1L;
	private int status;
	private int code;
	private String message;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ApiErrorResponse [status=" + status + ", code=" + code + ", message=" + message + "]";
	}
	public ApiErrorResponse(int status, int code, String message) {
		super();
		this.status = status;
		this.code = code;
		this.message = message;
	}

}
