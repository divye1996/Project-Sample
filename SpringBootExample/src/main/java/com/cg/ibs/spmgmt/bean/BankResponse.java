package com.cg.ibs.spmgmt.bean;

import java.io.Serializable;

import javax.persistence.Entity;


public class BankResponse implements Serializable{

	private String userId;
	private String remarks;
	private String decision;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	@Override
	public String toString() {
		return "BankResponse [userId=" + userId + ", remarks=" + remarks + ", decision=" + decision + "]";
	}
	
	
}
