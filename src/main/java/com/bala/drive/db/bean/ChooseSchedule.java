package com.bala.drive.db.bean;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="choose_schedule")
public class ChooseSchedule {

	@Id
	private int id;
	private String driveName;
	private String fileName; 
	private int fileSeq;
	private int driveSeq;
	private Date scheduledDttm;
	

	private ChooseSchedule.schedule status;
	private int lineCount;
	private int os;
	
	
	public enum choose{IOS, GCM, BOTH}
	
	public enum schedule{SCHEDULED,DONE,INVALID}
	
	public int getLineCount() {
		return lineCount;
	}
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
	public int getOs() {
		return os;
	}
	public void setOs(int os) {
		this.os = os;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDriveName() {
		return driveName;
	}
	public void setDriveName(String driveName) {
		this.driveName = driveName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}
	public Date getScheduledDttm() {
		return scheduledDttm;
	}
	public void setScheduledDttm(Date scheduledDttm) {
		this.scheduledDttm = scheduledDttm;
	}
	
	public int getDriveSeq() {
		return driveSeq;
	}
	public void setDriveSeq(int driveSeq) {
		this.driveSeq = driveSeq;
	}
	public ChooseSchedule.schedule getStatus() {
		return status;
	}
	public void setStatus(ChooseSchedule.schedule status) {
		this.status = status;
	}
	
	
}
