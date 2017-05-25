package com.bala.drive.db.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bala.drive.db.Views;
import com.fasterxml.jackson.annotation.JsonView;

@Document(collection="drives")
public class DriveListBean {

	@JsonView(Views.Response.class)
	@Id
	private String id;
	private String startDate;
	
	@JsonView(Views.Response.class)
	private String name;
	
	private String endDate;
	
	private enum status{
		ACTIVE, INACTIVE, HOLD, DEACTIVATED, UPDATED, UPDATEREQ
	}
	
	@JsonView(Views.Response.class)
	private String status;
	
	private String createdBy;
	
	private String goal;
	
	private String desc;
	
	@JsonView(Views.Response.class)
	private boolean isActive;
	
	private String couponCode;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	} 
	
	
}
