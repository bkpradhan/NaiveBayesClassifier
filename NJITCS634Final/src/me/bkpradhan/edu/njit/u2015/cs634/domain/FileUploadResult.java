package me.bkpradhan.edu.njit.u2015.cs634.domain;

public class FileUploadResult {
	private String sessionId;
	private long recvTime;
	private long uploadFinish;
	private long arffFinish;
	private String filename;
	private long fileSizeBytes;
	private String message;
	private String exception;
	private String prettyMessage;
	private String uploadTypeFlag;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFileSizeBytes() {
		return fileSizeBytes;
	}
	public void setFileSizeBytes(long fileSizeBytes) {
		this.fileSizeBytes = fileSizeBytes;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getPrettyMessage() {
		return this.toString();
	}
	public String getUploadTypeFlag() {
		return uploadTypeFlag;
	}
	public void setUploadTypeFlag(String uploadTypeFlag) {
		this.uploadTypeFlag = uploadTypeFlag;
	}
	
	public long getRecvTime() {
		return recvTime;
	}
	public void setRecvTime(long recvTime) {
		this.recvTime = recvTime;
	}
	public long getUploadFinish() {
		return uploadFinish;
	}
	public void setUploadFinish(long uploadFinish) {
		this.uploadFinish = uploadFinish;
	}
	public long getArffFinish() {
		return arffFinish;
	}
	public void setArffFinish(long arffFinish) {
		this.arffFinish = arffFinish;
	}
	
	@Override
	public String toString() {
		return "FileUploadResult [sessionId=" + sessionId + ",\n filename="
				+ filename + ",\n fileSizeBytes=" + fileSizeBytes + ",\n message="
				+ message + ",\n exception=" + exception + ",\n uploadTypeFlag="
				+ uploadTypeFlag + "\n]";
	}
	
	
}
