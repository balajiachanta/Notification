package com.bala.drive.db.bean;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bala.drive.db.Views;
import com.fasterxml.jackson.annotation.JsonView;

@Document(collection="eligible_collections")
public class EligibilityFile {

	@Id
	private int id;
	@JsonView(Views.Response.class)
	private String name;
	private String fieldName;
	private int count;
	private LocalDate targetDttm;
	private LocalDate createDttm;
	private String couponCode;
	private String createdFor;
	@JsonView(Views.Response.class)
	private String dirPath;
	@JsonView(Views.Response.class)
	private int seq;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public LocalDate getTargetDttm() {
		return targetDttm;
	}
	public void setTargetDttm(LocalDate targetDttm) {
		this.targetDttm = targetDttm;
	}
	public LocalDate getCreateDttm() {
		return createDttm;
	}
	public void setCreateDttm(LocalDate createDttm) {
		this.createDttm = createDttm;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getCreatedFor() {
		return createdFor;
	}
	public void setCreatedFor(String createdFor) {
		this.createdFor = createdFor;
	}
	public String getDirPath() {
		return dirPath;
	}
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
}
