package com.cg.ibs.spmgmt.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

//  import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.ibs.spmgmt.bean.BankAdmin;
import com.cg.ibs.spmgmt.bean.SPCustomerData;
import com.cg.ibs.spmgmt.bean.ServiceProvider;
import com.cg.ibs.spmgmt.dao.ServiceProviderDao;
import com.cg.ibs.spmgmt.dao.ServiceProviderDaoImpl;
import com.cg.ibs.spmgmt.exception.IBSException;
import com.cg.ibs.spmgmt.exception.IBSExceptionInterface;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {
//	public static final Logger LOGGER = Logger.getLogger(ServiceProviderServiceImpl.class);
	private BigInteger spi = BigInteger.valueOf(-1);

	ServiceProvider serviceProvider;

	private ServiceProviderDao serviceProviderDao;
	private EntityTransaction entityTransaction;

	@Autowired
	public ServiceProviderServiceImpl(ServiceProviderDao serviceProviderDao) {
		super();
		this.serviceProviderDao = serviceProviderDao;
	}

	public ServiceProviderDao getServiceProviderDao() {
		return serviceProviderDao;
	}

	public void setServiceProviderDao(ServiceProviderDao serviceProviderDao) {
		this.serviceProviderDao = serviceProviderDao;
	}

	@Transactional
	public boolean isRunnable() {
		boolean runnable = true;
		try {
			spi = serviceProviderDao.getLastSPI();
		} catch (ExceptionInInitializerError exception) {
			runnable = false;
//		LOGGER.fatal("DATABASE NOT FOUND/CONNECTION FAILURE");
		} catch (IBSException exception) {
			runnable=false;
		}
		return runnable;
	}

	// Function to generate User ID and Password of Service Provider
	@Transactional
	public ServiceProvider generateIdPassword(ServiceProvider serviceProvider) throws IBSException {
		if (serviceProvider == null) {
			throw new IBSException(IBSExceptionInterface.REGISTRATION_FAILED_MESSAGE);
		}
		String temp = serviceProvider.getNameOfCompany();
		String userId = temp.substring(0, Math.min(temp.length(), 5));
		boolean userIdExists = false;
		long tail = 1;
		if (userIdExists = ((serviceProviderDao.checkUserId(userId)).equals(userId))) {
			String tempuser = userId;
//				LOGGER.debug("User exsists");
			do {
				userId = tempuser + "_" + Long.toString(tail);
				userIdExists = ((serviceProviderDao.checkUserId(userId)).equals(userId));

				tail++;
			} while (userIdExists);
		}
		String passGeneratorSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"
				+ "!@#$%^&*_=+-/.?<>)";
		StringBuilder userPass = new StringBuilder(8);

		for (int i = 0; i < 8; i++) {
			int index = (int) (passGeneratorSet.length() * Math.random());
			userPass.append(passGeneratorSet.charAt(index));
		}

		String result = userPass.toString();
		serviceProvider.setUserId(userId);
		serviceProvider.setPassword(result);
		return serviceProvider;
	}

	// Function to store details of Service Provider
	@Transactional
	public boolean storeSPDetails(ServiceProvider sp) throws IBSException {
		boolean result = false;
		if (sp == null) {
			throw new IBSException(IBSExceptionInterface.REGISTRATION_FAILED_MESSAGE);
		}
		sp.setRequestDate(LocalDateTime.now());
		ServiceProvider storeResult = serviceProviderDao.addServiceProvider(sp); // used to store the SP details after
		if (storeResult != null) {
//			LOGGER.debug("successfully added");}
			if (storeResult != null)
				result = true;
		}
		return result;
	}

	// Function to validate Login of Service Provider
	@Transactional
	public boolean validateLogin(String username, String password) throws IBSException {
		boolean result = false;
		serviceProvider = serviceProviderDao.getServiceProviderById(username);
		if (serviceProvider != null) {
			if (serviceProvider.getPassword() != null) {
				if (password.equals(serviceProvider.getPassword())) {
					result = true;
				} else {
					throw new IBSException(IBSExceptionInterface.INCORRECT_LOGIN_MESSAGE);
				}
			} else {
				throw new IBSException(IBSExceptionInterface.INCORRECT_LOGIN_MESSAGE);
			}
		} else {
			throw new IBSException(IBSExceptionInterface.INCORRECT_LOGIN_MESSAGE);
		}
		return result;
	}

	// Function to get the details of Service Provider from DAO Layer where the
	// details are stored
	@Transactional
	public ServiceProvider getServiceProvider(String userid) throws IBSException {
		serviceProvider = serviceProviderDao.getServiceProviderById(userid);
		return serviceProvider;
	}

	// Function to show all the Pending Request of Service Providers to the Bank
	// Administrative
	public List<ServiceProvider> showPending() throws IBSException {
		return serviceProviderDao.getPendingServiceProviders();
	}

	// Function to approve or disapprove the request of Service Provider by the Bank
	// Administrative
	@Transactional
	public void approveSP(ServiceProvider sp, String decision) throws IBSException {
		if (sp == null) {
			throw new IBSException(IBSExceptionInterface.REGISTRATION_FAILED_MESSAGE);
		}
		if (decision.equalsIgnoreCase("approved")) {
			sp.setStatus("Approved");
			generateSpi();
			sp.setSpi(spi);
		} else if (decision.equalsIgnoreCase("disapproved")) {
			spi = BigInteger.valueOf(-1);
			sp.setSpi(spi);
			sp.setStatus("Disapproved");
		} else {
			throw new IBSException(IBSExceptionInterface.REGISTRATION_FAILED_MESSAGE);
		}
		serviceProviderDao.updateServiceProvider(sp);
	}

	// Function to generate Unique SPI ID after the Service Provider gets approved
	private BigInteger generateSpi() throws IBSException {
		spi = serviceProviderDao.getLastSPI();
		if (spi.compareTo(BigInteger.valueOf(100000)) < 0) {
			spi = BigInteger.valueOf(100000);
		}
		spi = spi.add(BigInteger.valueOf(1));
		return spi;
	}

	// Function to validate the login of bank Administrative
	@Transactional
	public boolean validateAdminLogin(String adminID, String adminPassword) throws IBSException {
		boolean validity = false;
		BankAdmin admin;
		admin = serviceProviderDao.getBankAdmin(adminID);
		if (admin.getAdminPassword() != null) {
			if (adminPassword.equals(admin.getAdminPassword())) {
				validity = true;
			} else {
				throw new IBSException(IBSExceptionInterface.INCORRECT_LOGIN_MESSAGE);
			}
		} else {
			throw new IBSException(IBSExceptionInterface.INCORRECT_LOGIN_MESSAGE);
		}
		return validity;
	}

	// Default empty data function
	@Transactional
	public boolean emptyData() throws IBSException {
		boolean result = (serviceProviderDao.getPendingServiceProviders().isEmpty());
		return result;
	}

	@Transactional
	public List<ServiceProvider> showHistory() throws IBSException {
		List<ServiceProvider> historyList = null;
			historyList = serviceProviderDao.getApprovedDisapprovedServiceProviders();
			Collections.sort(historyList, (serviceProvider1, serviceProvider2) -> serviceProvider2.getRequestDate()
					.compareTo(serviceProvider1.getRequestDate()));
		return historyList;
	}

	@Transactional
	public void storeSPCustomerData(SPCustomerData customerData) {
		serviceProviderDao.addSPCustomerData(customerData);
	}

	public List<SPCustomerData> getSPCustomerData(ServiceProvider serviceProvider) throws IBSException {
		List<SPCustomerData> data = serviceProviderDao.getSPData(serviceProvider);
		return data;
	}

	@Override
	public boolean checkUnique(ServiceProvider serviceProvider) {
		boolean check = serviceProviderDao.checkUniqueConstarints(serviceProvider);
		return check;
	}
}
