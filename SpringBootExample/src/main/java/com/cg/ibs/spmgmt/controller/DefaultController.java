package com.cg.ibs.spmgmt.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;

import com.cg.ibs.spmgmt.bean.BankResponse;
import com.cg.ibs.spmgmt.bean.SPCustomerData;
import com.cg.ibs.spmgmt.bean.ServiceProvider;
import com.cg.ibs.spmgmt.exception.IBSException;
import com.cg.ibs.spmgmt.service.ServiceProviderService;

@RestController
@Scope("session")
public class DefaultController {

	@Autowired
	private ServiceProviderService service;
	private ServiceProvider serviceProvider;

	// Get bills data for given SP
	@GetMapping("/down/{uid}")
	public String displayBills(@PathVariable String uid) throws IBSException {
		List<SPCustomerData> data = null;
		ServiceProvider serviceProvider = service.getServiceProvider(uid);
		data = service.getSPCustomerData(serviceProvider);
		System.out.println(data.toString());
		return (data.toString() + uid);
	}

	//format list
	public List<String> formatResponse(List<ServiceProvider> serviceProviders){
		List<String> formatResponse = new ArrayList<String>();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		int index = 1;
		formatResponse.add(String.format("%-25s", "Name of Company") + String.format("%-25s", "Category")
				+ String.format("%-25s", "User_ID") + String.format("%-25s", "Request Time"));
		for (ServiceProvider serviceProvider : serviceProviders) {
			formatResponse.add(String.format("%-25s", serviceProvider.getNameOfCompany())
					+ String.format("%-25s", serviceProvider.getCategory())
					+ String.format("%-25s", serviceProvider.getUserId())
					+ String.format("%-25s", serviceProvider.getRequestDate().format(format)));
			index++;
		}
		return formatResponse;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	// Show Banker non-pending SPs
	@GetMapping("/approvedDisapprovedHistory")
	public ResponseEntity<?> showApprovedDisapprovedList() {
		ResponseEntity<?> result = new ResponseEntity<String>("Internal Error", HttpStatus.BAD_REQUEST);
		List<ServiceProvider> approvedDisapprovedList = null;
		try {
			approvedDisapprovedList = service.showHistory();
			result = new ResponseEntity<List<String>>(formatResponse(approvedDisapprovedList), HttpStatus.OK);
		} catch (IBSException e) {
			String msg = e.getMessage();
			System.out.println(msg);
		}
		return result;
	}

	// Pre-existing IBS Customer Registration
	@PostMapping("/registeredDetailsAlready")
	public ResponseEntity<ServiceProvider> showDetailsAlreadyExisting(@RequestBody ServiceProvider serviceProvider) {
		ResponseEntity<ServiceProvider> result;
		serviceProvider.setPanNumber("LUUPSA56907");
		serviceProvider.setAccountNumber(new BigInteger("10000010000"));
		serviceProvider.setIFSC("IBS0000789");
		serviceProvider.setBankName("IBS limited");
		try {
			serviceProvider = service.generateIdPassword(serviceProvider);
			service.storeSPDetails(serviceProvider);
		} catch (IBSException e) {
			e.printStackTrace();
		}
		if (serviceProvider == null) {
			result = new ResponseEntity<ServiceProvider>(HttpStatus.BAD_REQUEST);
		} else {
			result = new ResponseEntity<ServiceProvider>(serviceProvider, HttpStatus.OK);
		}
		return result;
	}

	// Fresh Registration - Other Bank
	@PostMapping("/registeredDetails")
	public ResponseEntity<?> showDetailsOtherBank(@RequestBody ServiceProvider serviceProvider) {
		ResponseEntity<?> result;
		try {
			serviceProvider = service.generateIdPassword(serviceProvider);
			service.storeSPDetails(serviceProvider);
		} catch (IBSException e) {
			System.out.println(e.getMessage());
		}

		if (serviceProvider == null) {
			result = new ResponseEntity<String>("Internal Error", HttpStatus.BAD_REQUEST);
		} else {
			result = new ResponseEntity<ServiceProvider>(serviceProvider, HttpStatus.OK);
		}
		return result;
	}

	// New IBS Bank Registration
	@PostMapping("/registeredDetailsOurBank")
	public ResponseEntity<ServiceProvider> showDetailsOurBank(@RequestBody ServiceProvider serviceProvider) {

		ResponseEntity<ServiceProvider> result;
		serviceProvider.setPanNumber("LUUPSA56907");
		serviceProvider.setAccountNumber(new BigInteger("10000010000"));
		serviceProvider.setIFSC("IBS0000789");
		serviceProvider.setBankName("IBS limited");
		try {
			serviceProvider = service.generateIdPassword(serviceProvider);
			service.storeSPDetails(serviceProvider);
		} catch (IBSException e) {
			e.printStackTrace();
		}
		if (serviceProvider == null) {
			result = new ResponseEntity<ServiceProvider>(HttpStatus.BAD_REQUEST);
		} else {

			result = new ResponseEntity<ServiceProvider>(serviceProvider, HttpStatus.OK);
		}
		return result;
	}

	// Login
	@GetMapping("/loginDetails/{userId}")
	public ResponseEntity<ServiceProvider> showLoginDetails(@PathVariable String userId) throws IBSException {
		ResponseEntity<ServiceProvider> result;
		ServiceProvider serviceProvider = null;
		serviceProvider = service.getServiceProvider(userId);
		System.out.println(userId);
		System.out.println(serviceProvider.toString());
		if (serviceProvider == null) {
			result = new ResponseEntity<ServiceProvider>(HttpStatus.BAD_REQUEST);
		} else {

			result = new ResponseEntity<ServiceProvider>(serviceProvider, HttpStatus.OK);
		}
		return result;
	}

	// Show Banker pending SPs
	@GetMapping("/pendingList")
	public ResponseEntity<?> showPendingList() {
		ResponseEntity<?> result = new ResponseEntity<String>("Internal Error", HttpStatus.BAD_REQUEST);
		;
		List<ServiceProvider> pendingList = null;
		try {

			pendingList = service.showPending();
			result = new ResponseEntity<List<String>>(formatResponse(pendingList), HttpStatus.OK);
		} catch (IBSException e) {
			String msg = e.getMessage();
			System.out.println(msg);
		}

		return result;
	}

	// Update Status of chosen SP with remarks
	@PostMapping("/updateSP")
	public ResponseEntity<?> updateSP(@RequestBody BankResponse response) {
		ResponseEntity<?> result = new ResponseEntity<String>("Internal Error", HttpStatus.BAD_REQUEST);
		System.out.println(response.toString());
		try {
			ServiceProvider provider = service.getServiceProvider(response.getUserId());
			System.out.println(provider);
			provider.setRemarks(response.getRemarks());
			service.approveSP(provider, response.getDecision());
			result = new ResponseEntity<String>("Status Updated Succesfully", HttpStatus.OK);
		} catch (IBSException e) {
			String msg = e.getMessage();
			System.out.println(msg);
		}
		return result;
	}

	// Show details of chosen SP
	@GetMapping("/showDetailsToApprove/{userId}")
	public ResponseEntity<?> updateStatusMethod(@PathVariable String userId) {
		ResponseEntity<?> result = new ResponseEntity<String>("Internal Error", HttpStatus.BAD_REQUEST);
		;
		ServiceProvider serviceProvider = null;
		try {
			serviceProvider = service.getServiceProvider(userId);
			result = new ResponseEntity<ServiceProvider>(serviceProvider, HttpStatus.OK);
		} catch (IBSException e) {
			String msg = e.getMessage();
			System.out.println(msg);
		}
		return result;
	}

	// Upload bills from CSV file
	@PostMapping("/up/{uid}")
	public String uploadBills(@RequestBody MultipartFile bills, @PathVariable String uid)
			throws IOException, IBSException {
		byte[] bytes = bills.getBytes();
		String s = new String(bytes);
		System.out.println(s);
		BufferedReader csvReader = new BufferedReader(new StringReader(s));
		String row;
		ServiceProvider serviceProvider = service.getServiceProvider(uid);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			if (data.length == 0)
				break;
			SPCustomerData spCustomerData = new SPCustomerData();
			spCustomerData.setServiceProvider(serviceProvider);
			spCustomerData.setSpcId(data[0]);
			spCustomerData.setBillDate(LocalDate.parse(data[1], formatter));
			spCustomerData.setDueDate(LocalDate.parse(data[2], formatter));
			spCustomerData.setBillAmount(Double.valueOf(data[3]));
			service.storeSPCustomerData(spCustomerData);
			System.out.println(spCustomerData.toString());
		}
		csvReader.close();
		System.out.println("Upload Succesfull. Bills Updated");
		return s;
	}
	
//	@RequestMapping(value = "/downloadAdd", method = RequestMethod.GET)
//	public ResponseEntity<ByteArrayResource> downloadAdd(@RequestParam("uid") String uid)
//			throws IOException, IBSException {
//		ServiceProvider provider = service.getServiceProvider(uid);
//		byte[] data = provider.getAddressProofUpload();
//		ByteArrayResource resource = new ByteArrayResource(data);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Address_Doc_" + provider.getUserId())
//				.contentType(MediaType.APPLICATION_PDF).contentLength(data.length).body(resource);
//	}
//	
//	@RequestMapping(value = "/downloadPan", method = RequestMethod.GET)
//	public ResponseEntity<ByteArrayResource> downloadPan(@RequestParam("uid") String uid)
//			throws IOException, IBSException {
//		ServiceProvider provider = service.getServiceProvider(uid);
//		byte[] data = provider.getPanCardUpload();
//		ByteArrayResource resource = new ByteArrayResource(data);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=PAN_Doc_" + provider.getUserId())
//				.contentType(MediaType.APPLICATION_PDF).contentLength(data.length).body(resource);
//	}
//
//	@ExceptionHandler(IBSException.class)
//	public ModelAndView handleUserIdNotFoundException(HttpServletRequest request, IBSException message) {
//		return new ModelAndView("errorPage", "message", message.getMessage());
//
//	}
}
