package com.cg.ibs.spmgmt.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Bank_Admins")
public class BankAdmin implements Serializable {

	@Id
	private String adminID;

	@Column(name = "password")
	private String adminPassword;

	public BankAdmin() {
		super();
	}

	public BankAdmin(String adminID, String adminPassword) {
		super();
		this.adminID = adminID;
		this.adminPassword = adminPassword;
	}

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	@Override
	public String toString() {
		return "BankAdminBean [adminID=" + adminID + ", adminPassword=" + adminPassword + "]";
	}

}
