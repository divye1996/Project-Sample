package com.cg.ibs.spmgmt.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints= @UniqueConstraint(columnNames= {"nameOfCompany","category"}), name = "Service_providers" )
public class ServiceProvider implements Serializable {

	private BigInteger accountNumber;
	private byte[] addressProofUpload;
	private String bankName;
	private String category;
	private String companyAddress;
	private String gstin;
	private String IFSC;
	private BigInteger mobileNumber;
	private String nameOfCompany;
	private byte[] panCardUpload;
	private String panNumber;
	private String password;
	public String getIFSC() {
		return IFSC;
	}

	public void setIFSC(String iFSC) {
		IFSC = iFSC;
	}

	private String remarks = "None";
	private LocalDateTime requestDate;
	private BigInteger spi = BigInteger.valueOf(-1);
	private String status = "Pending";
	@Id
	private String userId;

	public ServiceProvider() {
		super();
	}

	public BigInteger getAccountNumber() {
		return accountNumber;
	}

	public byte[] getAddressProofUpload() {
		return addressProofUpload;
	}

	public String getBankName() {
		return bankName;
	}

	public String getCategory() {
		return category;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public String getGstin() {
		return gstin;
	}

	public BigInteger getMobileNumber() {
		return mobileNumber;
	}

	public String getNameOfCompany() {
		return nameOfCompany;
	}

	public byte[] getPanCardUpload() {
		return panCardUpload;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public String getPassword() {
		return password;
	}

	public String getRemarks() {
		return remarks;
	}

	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public BigInteger getSpi() {
		return spi;
	}

	public String getStatus() {
		return status;
	}

	public String getUserId() {
		return userId;
	}

	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAddressProofUpload(byte[] addressProofUpload) {
		this.addressProofUpload = addressProofUpload;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public void setMobileNumber(BigInteger mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setNameOfCompany(String nameOfCompany) {
		this.nameOfCompany = nameOfCompany;
	}

	public void setPanCardUpload(byte[] panCardUpload) {
		this.panCardUpload = panCardUpload;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setRequestDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

	public void setSpi(BigInteger spi) {
		this.spi = spi;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ServiceProvider [userId=" + userId + ", category=" + category + ", nameOfCompany=" + nameOfCompany
				+ ", gstin=" + gstin + ", panNumber=" + panNumber + ", panCardUpload=" + Arrays.toString(panCardUpload)
				+ ", accountNumber=" + accountNumber + ", bankName=" + bankName + ",  IFSC=" + IFSC + ",addressProofUpload="
				+ Arrays.toString(addressProofUpload) + ", companyAddress=" + companyAddress + ", mobileNumber="
				+ mobileNumber + ", password=" + password + ", spi=" + spi + ", status=" + status + ", requestDate="
				+ requestDate + ", remarks=" + remarks + "]";
	}

}
