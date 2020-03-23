package com.cg.ibs.spmgmt.dao;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

//import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import com.cg.ibs.spmgmt.bean.BankAdmin;
import com.cg.ibs.spmgmt.bean.SPCustomerData;
import com.cg.ibs.spmgmt.bean.ServiceProvider;
import com.cg.ibs.spmgmt.exception.IBSException;
import com.cg.ibs.spmgmt.exception.IBSExceptionInterface;
import com.sun.xml.txw2.Document;

@Repository
public class ServiceProviderDaoImpl implements ServiceProviderDao {
	
//  public static final Logger LOGGER = Logger.getLogger(ServiceProviderDaoImpl.class);
	@PersistenceContext
	private EntityManager entityManager;



	
	public ServiceProvider addServiceProvider(ServiceProvider serviceProvider) throws IBSException {
		entityManager.persist(serviceProvider);
		return serviceProvider;
	}

	 
	public ServiceProvider updateServiceProvider(ServiceProvider serviceProvider) throws IBSException {
		return entityManager.merge(serviceProvider);
	}

	 
	public ServiceProvider getServiceProviderById(String userId) throws IBSException {
		return entityManager.find(ServiceProvider.class, userId);
	}

	 
	public List<ServiceProvider> getServiceProviders() throws IBSException {
		List<ServiceProvider> list = null;
		TypedQuery<ServiceProvider> query = entityManager.createQuery("SELECT s from ServiceProvider s",
				ServiceProvider.class);
		try {
			list = query.getResultList();
		} catch (PersistenceException exception) {
			throw new IBSException(IBSExceptionInterface.FETCHING_FAILED_MESSAGE);

		}
		return list;
	}

	 
	public List<ServiceProvider> getPendingServiceProviders() throws IBSException {
		List<ServiceProvider> list = null;
		TypedQuery<ServiceProvider> query = entityManager
				.createQuery("SELECT s from ServiceProvider s where s.status='Pending'", ServiceProvider.class);
		try {
			list = query.getResultList();
		} catch (PersistenceException exception) {
			throw new IBSException(IBSExceptionInterface.FETCHING_FAILED_MESSAGE);
		}
		return list;
	}

	 
	public List<ServiceProvider> getApprovedServiceProviders() throws IBSException {
		List<ServiceProvider> list = null;
		TypedQuery<ServiceProvider> query = entityManager
				.createQuery("SELECT s from ServiceProvider s where s.status='Approved'", ServiceProvider.class);
		try {
			list = query.getResultList();
		} catch (PersistenceException exception) {
			throw new IBSException(IBSExceptionInterface.FETCHING_FAILED_MESSAGE);
		}
		return list;
	}

	 
	public List<ServiceProvider> getDisapprovedServiceProviders() throws IBSException {
		List<ServiceProvider> list = null;
		TypedQuery<ServiceProvider> query = entityManager
				.createQuery("SELECT s from ServiceProvider s where s.status='Disapproved'", ServiceProvider.class);
		try {
			list = query.getResultList();
		} catch (PersistenceException exception) {
			throw new IBSException(IBSExceptionInterface.FETCHING_FAILED_MESSAGE);
		}
		return list;
	}

	 
	public List<ServiceProvider> getApprovedDisapprovedServiceProviders() throws IBSException {
		List<ServiceProvider> list = null;
		TypedQuery<ServiceProvider> query = entityManager.createQuery(
				"SELECT s from ServiceProvider s where s.status='Approved' or s.status ='Disapproved'",
				ServiceProvider.class);
		try {
			list = query.getResultList();
		} catch (PersistenceException exception) {
			throw new IBSException(IBSExceptionInterface.FETCHING_FAILED_MESSAGE);
		}
		return list;
	}

	 
	public BankAdmin getBankAdmin(String adminId) throws IBSException {
		List<BankAdmin> admins = null;
		TypedQuery<BankAdmin> query = entityManager
				.createQuery("SELECT s from BankAdmin s where s.adminID='" + adminId + "'", BankAdmin.class);
		try {
			admins = query.getResultList();
		} catch (PersistenceException exception) {
			throw new IBSException(IBSExceptionInterface.FETCHING_FAILED_MESSAGE);
		}
		BankAdmin admin = new BankAdmin();
		if (admins.isEmpty()) {
			admin.setAdminID("adminId");
			admin.setAdminPassword("false");
		} else
			admin = admins.get(0);
		return admin;
		// return entityManager.find(BankAdmin.class,adminId);
	}

	 
	public BigInteger getLastSPI() throws IBSException {
		BigInteger max_spi = null;
		TypedQuery<BigInteger> query = entityManager.createQuery("SELECT max(s.spi) from ServiceProvider s",
				BigInteger.class);
		try {
			max_spi = query.getSingleResult();
		} catch (PersistenceException exception) {
			throw new IBSException(IBSExceptionInterface.FETCHING_FAILED_MESSAGE);
		}
		if (max_spi == null)
			max_spi = BigInteger.valueOf(-1);
		return max_spi;
	}

	 
	public String checkUserId(String userId) throws IBSException {
		String userIdCheck = null;
		List<ServiceProvider> provider = null;
		TypedQuery<ServiceProvider> query = entityManager
				.createQuery("SELECT s from ServiceProvider s where s.userId='" + userId + "'", ServiceProvider.class);
		// userIdCheck = (String) query.setParameter("userId",
		// userId).getSingleResult();
		try {
			provider = query.getResultList();
			// userIdCheck=entityManager.find(ServiceProvider.class, userId).getUserId();
		} catch (PersistenceException exception) {
			throw new IBSException(IBSExceptionInterface.FETCHING_FAILED_MESSAGE);
		}

		if (provider.isEmpty()) {
			userIdCheck = "empty";
		} else {
			userIdCheck = userId;
		}
		return userIdCheck;
	}

	 
	public void addSPCustomerData(SPCustomerData customerData) {
		entityManager.persist(customerData);
	}

	 
	public List<SPCustomerData> getSPData(ServiceProvider serviceProvider) throws IBSException {
		List<SPCustomerData> data = null;
		List<SPCustomerData> finalData=new ArrayList<SPCustomerData>();
		TypedQuery<SPCustomerData> query = entityManager
				.createQuery("SELECT s from SPCustomerData s", SPCustomerData.class);
		try {
			data = query.getResultList();
		} catch (PersistenceException exception) {
			throw new IBSException(IBSExceptionInterface.FETCHING_FAILED_MESSAGE);
		}
		for (int i = 0; i < data.size(); i++) {
			SPCustomerData customerData=data.get(i);
			if((customerData.getServiceProvider().getUserId()).equals(serviceProvider.getUserId())) {
				finalData.add(customerData);
			}
		}
		return finalData;
	}


	@Override
	public boolean checkUniqueConstarints(ServiceProvider serviceProvider) {
		boolean result =false;
		TypedQuery<ServiceProvider> query = entityManager
				.createQuery("SELECT s from ServiceProvider s where s.nameOfCompany='"+serviceProvider.getNameOfCompany()+"'AND s.category='"+serviceProvider.getCategory()+"'", ServiceProvider.class);
		List<ServiceProvider> provider = null;
		provider=query.getResultList();
		System.out.println(provider.toString());
		result=provider.isEmpty();
	if(provider.isEmpty()) {
		result=true;
	}
		return result;
	}

}
