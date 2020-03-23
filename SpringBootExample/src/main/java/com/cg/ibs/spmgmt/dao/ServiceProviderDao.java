package com.cg.ibs.spmgmt.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.cg.ibs.spmgmt.bean.BankAdmin;
import com.cg.ibs.spmgmt.bean.SPCustomerData;
import com.cg.ibs.spmgmt.bean.ServiceProvider;
import com.cg.ibs.spmgmt.exception.IBSException;

public interface ServiceProviderDao {
	ServiceProvider addServiceProvider(ServiceProvider serviceProvider) throws IBSException;

	ServiceProvider updateServiceProvider(ServiceProvider serviceProvider) throws IBSException;

	ServiceProvider getServiceProviderById(String userId) throws IBSException;

	List<ServiceProvider> getServiceProviders() throws IBSException;

	List<ServiceProvider> getPendingServiceProviders() throws IBSException;

	List<ServiceProvider> getApprovedServiceProviders() throws IBSException;

	List<ServiceProvider> getDisapprovedServiceProviders() throws IBSException;

	List<ServiceProvider> getApprovedDisapprovedServiceProviders() throws IBSException;

	BankAdmin getBankAdmin(String adminId) throws IBSException;

	BigInteger getLastSPI() throws IBSException;
	
	String checkUserId(String userId) throws IBSException;

	void addSPCustomerData(SPCustomerData customerData);

	List<SPCustomerData> getSPData(ServiceProvider serviceProvider) throws IBSException;

	boolean checkUniqueConstarints(ServiceProvider serviceProvider);
	
	

}
