package com.cg.ibs.spmgmt.bean;

import java.time.LocalDate;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class SPCustomerData {
	private Double billAmount;
	private LocalDate billDate;
	private LocalDate dueDate;
	private String status="Pending";
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	@ManyToOne(targetEntity = ServiceProvider.class)
	private ServiceProvider serviceProvider;
	private String spcId;

	public Double getBillAmount() {
		return billAmount;
	}

	public LocalDate getBillDate() {
		return billDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public Integer getId() {
		return id;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public String getSpcId() {
		return spcId;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public void setSpcId(String spcId) {
		this.spcId = spcId;
	}

	@Override
	public String toString() {
		return "SPCustomerData [id=" + id + ", serviceProvider=" + serviceProvider + ", dueDate=" + dueDate
				+ ", billDate=" + billDate + ", billAmount=" + billAmount + ", spcId=" + spcId + "]";
	}

}
