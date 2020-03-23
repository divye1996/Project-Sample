//package com.cg.ibs.spmgmt.ui;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.math.BigInteger;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Scanner;
//import java.util.regex.Pattern;
//
////import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import com.cg.ibs.spmgmt.bean.SPCustomerData;
//import com.cg.ibs.spmgmt.bean.ServiceProvider;
//import com.cg.ibs.spmgmt.exception.IBSException;
//import com.cg.ibs.spmgmt.exception.RegisterException;
//import com.cg.ibs.spmgmt.service.ServiceProviderService;
//import com.cg.ibs.spmgmt.service.ServiceProviderServiceImpl;
//
//import sun.print.resources.serviceui;
//
//@Component
//public class Application {
//
//	static String DIGIT_MESSAGE = "Enter a number!";
////	public static final Logger LOGGER = Logger.getLogger(Application.class);
//
//	
//	private ServiceProviderService service;
//	
//	
//	@Autowired
//	public Application(ServiceProviderService service) {
//		super();
//		this.service = service;
//	}
//
//	// Main-------------------------------------------------------
//	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		Application application = (Application) context.getBean("application");
//		Scanner scanner = new Scanner(System.in);
//		int switchInput = 0;
//		boolean exitTrigger = true;
////		LOGGER.info("PROGRAMM STARTED");
//
//		// Keep Showing the menu until exit button pressed
//		do {
//			if (!application.service.isRunnable()) {
//				System.out.println("CONNECTION PROBLEM");
//				break;
//			}
//			ServiceProvider serviceProvider = new ServiceProvider();
//			switchInput = application.menu(scanner);
//			switch (switchInput) {
//			case 1:
//				// Takes User Input for all details-->performs basic input
//				// checks
//				application.registerServiceProvider(scanner, application.service, serviceProvider);
//				 application.returnToMainMenu(scanner);
//				 application.clearScreen();
//				break;
//			case 2:
//				// Takes login ID and password--> checks validity-->shows
//				// status/SPI if approved
//				try {
//					application.loginMethod(scanner, application.service, serviceProvider);
//				} catch (IBSException e1) {
//					System.out.println(e1.getMessage());
////					LOGGER.error("Login Problem");
//				}
//				// application.returnToMainMenu(scanner);
//				break;
//			case 3:
//				// BankAdmin Login
//				boolean exitToMain = false;
//				exitToMain = application.bankAdmin(scanner, application.service);
//				if (!exitToMain) {					
//					// Getting the list of pending Service Providers in Database
//					// ArrayList<ServiceProvider> serviceProviders = new ArrayList<>();
//					// ArrayList<ServiceProvider> serviceProviders2 = new ArrayList<>();
//					try {
//						application.bankMethod(application.service, scanner);
//					} catch (IBSException exception) {
////						LOGGER.info("The user gave an invalid input while logging as a bank admin");
//						System.out.println(exception.getMessage());
//					}
//				}
//				break;
//			case 4:
//				// Exit from Application
//				exitTrigger = false;
//				break;
//			default:
//				System.out.println("Invalid Input");
//			}
//		} while (exitTrigger);
//		application.clearScreen();
//		System.out.println("EXITED");
//	}
//
//	// BankAdmin Login Inputs
//	private boolean bankAdmin(Scanner scanner, ServiceProviderService service) {
//		boolean validity = false;
//		boolean exitToMain = false;
//		// Taking Inputs until login succeeds
//		do {
//			System.out.println("Enter Admin ID: ");
//			String inputId = scanner.next();
//			scanner.nextLine();
//			System.out.println("Enter Admin Password: ");
//			String inputPassword = scanner.next();
//			scanner.nextLine();
//			int response;
//			try {
//				validity = service.validateAdminLogin(inputId, inputPassword);
//			} catch (IBSException exception) {
//				System.out.println(exception.getMessage());
////				LOGGER.info("The Bank Admin gave an invalid input while logging in");
//				System.out.println("Enter 1 to return back to main menu or enter any other number to Try Again");
//				integerLoop(scanner);
//				response = scanner.nextInt();
//				if (response == 1) {
//					exitToMain = true;
//					break;
//				}
//			}
//		} while (!validity);
//		return exitToMain;
//	}
//
//	// Shows all Pending Service Providers and allows to approve/disapprove
//	private void bankMethod(ServiceProviderService service, Scanner scanner) throws IBSException {
//		List<ServiceProvider> serviceProviders = new ArrayList<>();
//		List<ServiceProvider> serviceProviders2 = new ArrayList<>();
//		int index = 0;
//		boolean exitTrigger2 = true;
//		clearScreen();
//		System.out.println("---------------Login Sucessful-----------------");
//
//		do {
//			System.out.println(" Press 1 for pending List  Press 2 to view Request History 3 to exit ");
//			integerLoop(scanner);
//			switch (scanner.nextInt()) {
//			// Show List of Pending Service Providers -> Allow to select from list and show
//			// details -> Approve/Disapprove the selected one
//			case 1:
//				// Getting Pending list from database
//				serviceProviders = service.showPending();
//				try {
//					if (service.emptyData()) {
//						System.out.println("*******No pending accounts*********" + " \n RETURNING TO MAIN MENU!");
//						break;
//					}
//				} catch (IBSException exception) {
////					LOGGER.error("Get Pending list from Database Failed!");
//					System.out.println(exception.getMessage());
//				}
//				clearScreen();
//				System.out.println("List of Pending Accounts: ");
//				System.out.println("Select an Account from the list below or Press 0 to return back to menu");
//				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//				index = 1;
//				System.out.format("%-20s", "Name of company");
//				System.out.format("%-25s", "Category");
//				System.out.format("%-20s", "USER_ID ");
//				System.out.format("%-20s\n", " Request Date and Time");
//				for (ServiceProvider serviceProvider : serviceProviders) {
//					System.out.format("%-20s", index + "." + serviceProvider.getNameOfCompany());
//					System.out.format("%-25s", serviceProvider.getCategory());
//					System.out.format("%-20s", serviceProvider.getUserId());
//					System.out.format("%-20s\n", serviceProvider.getRequestDate().format(format));
//					index++;
//				}
//				// Show selected Service Providers details -> Approve/Disapprove and update in
//				// database
//				updateServiceProvider(scanner, serviceProviders, service);
//				break;
//			// Show the past list of Service Providers with status either
//			// Approved/Disapproved
//			case 2:
//				serviceProviders2 = service.showHistory();
//				showHistory(serviceProviders2, scanner, service);
//				break;
//			// Exit to Main Menu
//			case 3:
//				exitTrigger2 = false;
//				break;
//			default:
//				System.out.println("Invalid Input");
//				break;
//			}
//			clearScreen();
//		} while (exitTrigger2);
//	}
//
//	// Taking KYC details and doing basic input checks to match standard
//	// patterns
//	private ServiceProvider getKYC(Scanner scanner, ServiceProvider serviceProvider) {
//
//		System.out.println("\n------Enter the Necessary Details----- ");
//		System.out.println("Select a category of Service Providers as applicable (1/2/3/4/5):");
//		System.out.println("1. Telecom Services");
//		System.out.println("2. Financial Services");
//		System.out.println("3. Electricity");
//		System.out.println("4. Dining Services");
//		System.out.println("5. Travel Services");
//
//		int categoryInput;
//		do {
//			inputLoop(scanner);
//			categoryInput = scanner.nextInt();
//			scanner.nextLine();
//			switch (categoryInput) {
//			case 1:
//				serviceProvider.setCategory("Telecom Services");
//				break;
//			case 2:
//				serviceProvider.setCategory("Financial Services");
//				break;
//			case 3:
//				serviceProvider.setCategory("Electricity");
//				break;
//			case 4:
//				serviceProvider.setCategory("Dining Services");
//				break;
//			case 5:
//				serviceProvider.setCategory("Travel Services");
//				break;
//			default:
//				System.out.println("Invalid Input! Enter 1/2/3/4/5");
//			}
//		} while (categoryInput > 5 || categoryInput < 1);
//
//		return inputLoop2(scanner, serviceProvider);
//
//	}
//
//	private void inputLoop(Scanner scanner) {
//		while (!scanner.hasNextInt()) {
//			scanner.next();
//			scanner.nextLine();
//			System.out.println("Invalid Input! Please Enter a Number: 1 2 3 4 5");
//		}
//	}
//
//	private ServiceProvider inputLoop2(Scanner scanner, ServiceProvider serviceProvider) {
//		// Setting Input REGX patterns
//		String string = "";
//		String gstinPattern = "[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z][0-9][A-Z][0-9,A-Z]";
//		String panPattern = "[A-Z]{5}[0-9]{4}[A-Z]";
//		String acNumberPattern = "[1-9][0-9]{9,14}";
//		String phoneNumberPattern = "[1-9][0-9]{9}";
//		String IFSCPattern="[A-Z]{4}[0-9]{7}";
//		// Input taking loops
//		// 1.GST
//		boolean bn = false;
//		System.out.println("Enter GST Number(16 Characters digit or uppercase alphabets): \nEx. 35AABCD1429B1ZX");
//		do {
//			string = scanner.next();
//			scanner.nextLine();
//			bn = !Pattern.matches(gstinPattern, string);
//			if (bn) {
//				System.out.println("Enter valid number(16 Characters-digits/uppercase letters)");
//			}
//		} while (bn);
//		serviceProvider.setGstin(string);
//		// 2.PAN
//		if (serviceProvider.getPanNumber() == null) {
//			System.out.println("Enter PAN(10 Characters-digits/uppercase letters): \nEx: AAAPL1234C ");
//			do {
//				string = scanner.next();
//				scanner.nextLine();
//				bn = !Pattern.matches(panPattern, string);
//				if (bn) {
//					System.out.println("Enter valid PAN(10 Characters-digits/uppercase letters)");
//				}
//			} while (bn);
//			serviceProvider.setPanNumber(string);
//			// 3.PAN Doc upload
//			boolean check;
//			do {
//				check = false;
//				try {
//					System.out.println("Enter file path for pancard to be uploaded");
//					String string2 = scanner.nextLine();
//					File file = new File(string2);
//					FileInputStream fileInputStream = new FileInputStream(file);
//					byte[] buffer = new byte[8192];
//					ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//					int i;
//					while ((i = fileInputStream.read(buffer)) != -1) {
//						arrayOutputStream.write(buffer, 0, i);
//					}
//					byte[] byteArray = arrayOutputStream.toByteArray();
//					serviceProvider.setPanCardUpload(byteArray);
//				} catch (Exception exception) {
//					System.out.println("Problem Uploading/finding file");
//					System.out.println("Check the path properly try again...");
//					check = true;
//				}
//			} while (check);
//			// 4.Bank Account Input
//			System.out.println("Enter Bank Account Number(10-14 digits): ");
//			do {
//				string = scanner.next();
//				scanner.nextLine();
//				bn = !Pattern.matches(acNumberPattern, string);
//				if (bn) {
//					System.out.println("Enter a valid Account number(10-14 digits)");
//				}
//			} while (bn);
//			serviceProvider.setAccountNumber((new BigInteger(string)));
//			// 4.IFSC
//						System.out.println("Enter IFSC \nEx.SBIN0000789");
//						do {
//							string = scanner.next();
//							scanner.nextLine();
//							bn = !Pattern.matches(IFSCPattern, string);
//							if (bn) {
//								System.out.println("Enter a valid IFSC");
//							}
//						} while (bn);
//						serviceProvider.setIFSC(string);
//			// 5.Name of Bank Input
//			System.out.println("Enter Name of the Bank: ");
//			serviceProvider.setBankName(scanner.nextLine());
//			// 6.Phone Number Input
//			System.out.println("Enter Contact Number (10 digits): ");
//			do {
//				string = scanner.next();
//				scanner.nextLine();
//				bn = !Pattern.matches(phoneNumberPattern, string);
//				if (bn) {
//					System.out.println(
//							"Enter 10 digit phone number that doesn't start with 0(No special characters like + or -)");
//				}
//
//			} while (bn);
//			serviceProvider.setMobileNumber(new BigInteger(string));
//		}
//		// 7.Company address input
//		System.out.println("Enter Company Address: ");
//		serviceProvider.setCompanyAddress(scanner.nextLine());
//		boolean check2;
//		do {
//			check2 = false;
//			try {
//				System.out.println("Enter file path for address proof to be uploaded");
//				String string2 = scanner.nextLine();
//				File file = new File(string2);
//				FileInputStream fileInputStream = new FileInputStream(file);
//				byte[] buffer = new byte[8192];
//				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//				int i;
//				while ((i = fileInputStream.read(buffer)) != -1) {
//					arrayOutputStream.write(buffer, 0, i);
//				}
//				byte[] byteArray = arrayOutputStream.toByteArray();
//				serviceProvider.setAddressProofUpload(byteArray);
//			} catch (Exception exception) {
//				System.out.println("Problem Uploading/finding file");
//				System.out.println("Try again...");
//				check2 = true;
//			}
//		} while (check2);
//		// 8. Name of company Input
//		serviceProvider.setNameOfCompany(takeNameInput(scanner));
//		// Print All Details
//		clearScreen();
//		printDetails(serviceProvider);
//
//		return serviceProvider;
//	}
//
//	private void integerLoop(Scanner scanner) {
//		while (!scanner.hasNextInt()) {
//			scanner.next();
//			scanner.nextLine();
//			System.out.println(DIGIT_MESSAGE);
//		}
//	}
//
//	// Displays details and approval status
//	private void loginMethod(Scanner scanner, ServiceProviderService service, ServiceProvider serviceProvider)
//			throws IBSException {
//		boolean exitTrig = true;
//		serviceProvider = null;
//		boolean exitToMain = false;
//		do {
//			clearScreen();
//			System.out.println("Enter User ID: ");
//			String inputId = scanner.next();
//			scanner.nextLine();
//			System.out.println("Enter Password: ");
//			String inputPassword = scanner.next();
//			scanner.nextLine();
//			int response;
//			boolean check = false;
//			try {
//				check = service.validateLogin(inputId, inputPassword);
//			} catch (IBSException exception) {
//				System.out.println(exception.getMessage());
////				LOGGER.error("The user gave an invalid input while logging as a service provider");
//				System.out.println("Enter 1 to return back to main menu or enter any other number to Try Again");
//
//				while (!scanner.hasNextInt()) {
//					System.out.println(DIGIT_MESSAGE);
//					scanner.next();
//					scanner.nextLine();
//				}
//				response = scanner.nextInt();
//				if (response == 1) {
//					exitToMain = true;
//					break;
//				}
//			}
//			if (check) {
//				try {
//					serviceProvider = service.getServiceProvider(inputId);
//				} catch (IBSException exception) {
////					LOGGER.error("invalid input is being given to fetch details");
//					System.out.println(exception.getMessage());
//				} finally {
//					exitTrig = (serviceProvider == null);
//				}
//			}
//		} while (exitTrig);
//		if (!exitToMain) {
//			clearScreen();
//			System.out.println("-------------Login Succesful------------------");
//			printDetails(serviceProvider);
//			byte[] byteArray = serviceProvider.getPanCardUpload();
//			try {
//				System.out.print("PAN document downloaded at location:");
//				System.out.println("PANdoc" + serviceProvider.getUserId() + ".txt");
//
//				FileWriter writer = new FileWriter("PANdoc" + serviceProvider.getUserId() + ".txt");
//				BufferedWriter out = new BufferedWriter(writer);
//				for (Byte b : byteArray) {
//					out.write(b);
//				}
//				out.close();
//			} catch (Exception e) {
////				LOGGER.error("PANdoc download failed");
//			}
//			byte[] byteArray2 = serviceProvider.getAddressProofUpload();
//			try {
//				System.out.print("Address proof document downloaded at location:");
//				System.out.println("adressproofdoc" + serviceProvider.getUserId() + ".txt");
//
//				FileWriter writer = new FileWriter("adressproofdoc" + serviceProvider.getUserId() + ".txt");
//				BufferedWriter out = new BufferedWriter(writer);
//				for (Byte b : byteArray2) {
//					out.write(b);
//				}
//				out.close();
//			} catch (Exception e) {
//	//			LOGGER.error("Address proof doc download failed");
//			}
//			System.out.println("Approval Status: " + serviceProvider.getStatus());
//			if (serviceProvider.getStatus().equals("Approved")) {
//				System.out.println("Unique SPI: " + serviceProvider.getSpi());
//				boolean exitTrigger1;
//				do {
//					exitTrigger1 = true;
//					System.out.println(
//							"Press 1 to upload new bills file \nPress 2 to see past bills status \nPress 3 to return");
//					integerLoop(scanner);
//					int k = scanner.nextInt();
//					scanner.nextLine();
//					switch (k) {
//					// Upload New File
//					case 1:
//						boolean exitTrigger;
//						do {
//							exitTrigger = false;
//							try {
//								System.out.println("Enter file path to get customer data ");
//								String string2 = scanner.next();
//								scanner.nextLine();
//								BufferedReader csvReader = new BufferedReader(new FileReader(string2));
//								String row;
//
//								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//								while ((row = csvReader.readLine()) != null) {
//									String[] data = row.split(",");
//									if (data.length == 0)
//										break;
//									SPCustomerData spCustomerData = new SPCustomerData();
//									spCustomerData.setServiceProvider(serviceProvider);
//									spCustomerData.setSpcId(data[0]);
//									spCustomerData.setBillDate(LocalDate.parse(data[1], formatter));
//									spCustomerData.setDueDate(LocalDate.parse(data[2], formatter));
//									spCustomerData.setBillAmount(Double.valueOf(data[3]));
//									service.storeSPCustomerData(spCustomerData);
//								}
//								csvReader.close();
//								System.out.println("Upload Succesfull Bills Updated");
//							} catch (Exception exception) {
////								LOGGER.debug(exception.getMessage());
//								System.out.println("Problem uploading the file check the pathname and try again");
//								exitTrigger = true;
//							}
//						} while (exitTrigger);
//						break;
//					case 2: {
//						List<SPCustomerData> data = service.getSPCustomerData(serviceProvider);
//						if (data.isEmpty()) {
//							System.out.println("No bills added");
//							break;
//						}
//						clearScreen();
//						System.out.format("%20s%20s%20s%20s%20s\n", "Customer ID", "Bill Date", "Due Date", "Amount",
//								"Status");
//						int i = 1;
//						for (Iterator iterator = data.iterator(); iterator.hasNext();) {
//							SPCustomerData spCustomerData = (SPCustomerData) iterator.next();
//							System.out.format("%20s%20s%20s%20s%20s\n", spCustomerData.getSpcId(),
//									spCustomerData.getBillDate(), spCustomerData.getDueDate(),
//									spCustomerData.getBillAmount(), spCustomerData.getStatus());
//						}
//						break;
//					}
//
//					case 3:
//						exitTrigger1 = false;
//						break;
//					default:
//						System.out.println("Invalid Input");
//						break;
//					}
//
//				} while (exitTrigger1);
//			} else if (serviceProvider.getStatus().equals("Disapproved")) {
//				System.out.println("Remarks for Disapproval: ");
//				System.out.println(serviceProvider.getRemarks());
//				System.out.println(" \n Enter a number to return to main menu");
//				while (!scanner.hasNextInt()) {
//					System.out.println(DIGIT_MESSAGE);
//					scanner.next();
//					scanner.nextLine();
//				}
//				scanner.nextInt();
//				scanner.nextLine();
//			} else {
//				System.out.println(" \n Enter a number to return to main menu");
//				while (!scanner.hasNextInt()) {
//					System.out.println(DIGIT_MESSAGE);
//					scanner.next();
//					scanner.nextLine();
//				}
//				scanner.nextInt();
//				scanner.nextLine();
//			}
//
//		}
//	}
//
//	// Methods
//	// Display Main Menu
//	private int menu(Scanner scanner) {
//		System.out.println("Welcome to Service Provider Portal");
//		System.out.println("Select an option below (1/2/3/4):");
//		System.out.println("1. New Registration");
//		System.out.println("2. Login");
//		System.out.println("3. Bank Administrator Login");
//		System.out.println("4. Exit");
//		// Ensuring input matches the menu
//		while (!scanner.hasNextInt()) {
//			System.out.println("Invalid Input! Please Enter a Number: 1 2 3 4");
//			scanner.next();
//			scanner.nextLine();
//		}
//		int returnInput = scanner.nextInt();
//
//		scanner.nextLine();
//
//		return returnInput;
//	}
//
//	// Prints Details of a Service Provider
//	private void printDetails(ServiceProvider serviceProvider) {
//		System.out.println("Name of Company: " + serviceProvider.getNameOfCompany());
//		System.out.println("GST Number: " + serviceProvider.getGstin());
//		System.out.println("PAN: " + serviceProvider.getPanNumber());
//		System.out.println("Account Number: " + serviceProvider.getAccountNumber());
//		System.out.println("IFSC Code: " + serviceProvider.getIFSC());
//		System.out.println("Bank Name:" + serviceProvider.getBankName());
//		System.out.println("Company Address: " + serviceProvider.getCompanyAddress());
//		System.out.println("Mobile Number: " + serviceProvider.getMobileNumber());
//
//	}
//
//	// Register a user
//	private void registerServiceProvider(Scanner scanner, ServiceProviderService service,
//			ServiceProvider serviceProvider) {
//
//		System.out.println(
//				"Press 1 if you wish to continue with pre-existing IBS bank account \nPress 2 to Register with IBS for new bank account \nPress 3 to continue with other bank account \nPress 4 to return");
//		boolean exitTrig = false;
//		do {
//			integerLoop(scanner);
//			int j=scanner.nextInt();
//			scanner.nextLine();
//			clearScreen();
//			switch (j) {
//			case 1: {
//				// to be redirected to IBS login
//				System.out.println("Enter IBS UserID and password");
//				System.out.println("UserID: ");
//				scanner.next();
//				scanner.nextLine();
//				System.out.println("Password: ");
//				scanner.next();
//				scanner.nextLine();
//				serviceProvider.setMobileNumber(new BigInteger("9800000000"));
//				serviceProvider.setPanNumber("LUUPSA5690D");
//				serviceProvider.setAccountNumber(new BigInteger("10000010000"));
//				serviceProvider.setIFSC("SBI0000789");
//				serviceProvider.setBankName("IBS limited");
//				serviceProvider = getKYC(scanner, serviceProvider);
//				exitTrig = false;
//				break;
//			}
//			case 2: {
//				// to be redirected to IBS Registration
//				serviceProvider.setMobileNumber(new BigInteger("9800000000"));
//				serviceProvider.setPanNumber("LUUPSA56907");
//				serviceProvider.setAccountNumber(new BigInteger("10000010000"));
//				serviceProvider.setIFSC("SBI0000789");
//				serviceProvider.setBankName("IBS limited");
//				serviceProvider = getKYC(scanner, serviceProvider);
//				exitTrig = false;
//				break;
//			}
//			case 3: {
//				// Fresh Registration
//				serviceProvider = getKYC(scanner, serviceProvider);
//				exitTrig = false;
//				break;
//			}
//			case 4: {
//				exitTrig = false;
//				break;
//			}
//			default:
//				System.out.println("Invalid Input! Enter 1/2/3");
//				exitTrig = true;
//			}
//		} while (exitTrig);
//		System.out.println("Press 1 to Submit Press any other number to abort and go back ");
//		integerLoop(scanner);
//		int j = scanner.nextInt();
//		scanner.nextLine();
//		if (j != 1) {
//			return;
//		}
//		try {
//			// Generates Unique ID & Password
//			serviceProvider = service.generateIdPassword(serviceProvider);
//		} catch (Exception exception) {
////			LOGGER.error("Error in generating Unique ID & Password");
//			System.out.println(exception.getMessage());
//		}
//		clearScreen();
//		
//		try {
//			// store the object into database
//			service.storeSPDetails(serviceProvider);
//			System.out.println("\nREGISTRATION COMPLETE. APPROVAL REQUEST SENT TO BANK! "
//					+ "\nLOGIN FROM MAIN MENU TO CHECK STATUS OF REQUEST \n");
//			System.out.println("Note Down the ID & Password for future logins:");
//			System.out.println("User ID:" + serviceProvider.getUserId());
//			System.out.println("Password: " + serviceProvider.getPassword());
//		} catch (RegisterException exception) {
//			System.out.println("------------------------------------------------------ \n" + exception.getMessage()
//					+ "\n------------------------------------------------------");
////			LOGGER.error("Error occured during registration");
//		} catch (IBSException e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//	// Taking 1 as input
//	private void returnToMainMenu(Scanner scanner) {
//		System.out.println("Enter a number to return to main menu");
//		integerLoop(scanner);
//		scanner.nextInt();
//		scanner.nextLine();
//	}
//
//	private void showHistory(List<ServiceProvider> serviceProviders2, Scanner scanner, ServiceProviderService service) {
//		int idx = 1;
//		DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//		if (serviceProviders2.isEmpty()) {
//			clearScreen();
//			System.out.println("*******No history Found*********" + "\n RETURNING TO MAIN MENU! \n");
//			return;
//		} else {
//			clearScreen();
//			System.out.println("-----------REGISTRATION RECORDS--------------");
//			System.out.println("Select from the list to view details or Press 0 to return back to main menu");
//			System.out.format("%-20s", "Name of company");
//			System.out.format("%-20s", "USER_ID ");
//			System.out.format("%-25s", "Status");
//			System.out.format("%-25s", " Request Date and Time");
//			System.out.format("%-20s", "Unique SPI");
//			System.out.format("%-20s\n", "Remarks");
//			for (ServiceProvider serviceProvider : serviceProviders2) {
//				System.out.format("%-20s", idx + "." + serviceProvider.getNameOfCompany());
//				System.out.format("%-20s", serviceProvider.getUserId());
//				System.out.format("%-25s", serviceProvider.getStatus());
//				System.out.format("%-25s", serviceProvider.getRequestDate().format(format1));
//				if (serviceProvider.getStatus().equals("Approved")) {
//					System.out.format("%-20s", serviceProvider.getSpi());
//				} else {
//					System.out.format("%-20s", "NA");
//				}
//				System.out.format("%-20s\n", serviceProvider.getRemarks());
//				idx++;
//			}
//		}
//
//		ServiceProvider provider = showFromList(scanner, serviceProviders2, service);
//		if (provider == null) {
//			return;
//		}
//		if (provider.getStatus().equals("Approved")) {
//			System.out.println("Press 1 to disapprove Press any other number to proceed");
//			integerLoop(scanner);
//			int j = scanner.nextInt();
//			scanner.nextLine();
//			if (j == 1) {
//				System.out.println("Provide Remarks for disapproval");
//				provider.setRemarks(scanner.nextLine());
//				provider.setStatus("Disapproved");
//				provider.setSpi(BigInteger.valueOf(-1));
//				try {
//					service.approveSP(provider, "false");
//				} catch (IBSException exception) {
//					System.out.println(exception.getMessage());
////					LOGGER.info("Error while disapproving preapproved Service Provider");
//				}
//			}
//		}
//
//	}
//
//	// Input character length check
//	private String takeNameInput(Scanner scanner) {
//		String string = "";
//		String namePattern = "[A-Z,a-z]{1}[ ,A-Z,a-z,0-9,&,@,#,*,(,),+,-,!]{0,}";
//		// Keep Taking User Input until it matches the pattern
//		do {
//			System.out.println("Enter Name of the Company");
//			string = scanner.nextLine();
//		} while (!string.matches(namePattern));
//		return string;
//	}
//
//	private ServiceProvider showFromList(Scanner scanner, List<ServiceProvider> serviceProviders,
//			ServiceProviderService service) {
//		integerLoop(scanner);
//		int k = scanner.nextInt();
//		int index;
//		ServiceProvider value = null;
//		if (k == 0) {
//			return null;
//		}
//		while (k > serviceProviders.size() || k < 1) {
//			System.out.println(
//					"Invalid input. Choose the correct number from menu to display company details! or press 0 to return");
//			integerLoop(scanner);
//			k = scanner.nextInt();
//			scanner.nextLine();
//			if (k == 0) {
//				return null;
//			}
//		}
//
//		for (index = 0; index <= serviceProviders.size(); index++) {
//			if ((index + 1) == k) {
//				value = serviceProviders.get(index);
//				printDetails(value);
//				byte[] byteArray = value.getPanCardUpload();
//				try {
//					System.out.print("PAN document downloaded at location:");
//					System.out.println("PANdoc" + value.getUserId() + ".txt");
//
//					FileWriter writer = new FileWriter("PANdoc" + value.getUserId() + ".txt");
//					BufferedWriter out = new BufferedWriter(writer);
//					for (Byte b : byteArray) {
//						out.write(b);
//					}
//					out.close();
//				} catch (Exception e) {
////					LOGGER.error("PANdoc download error");
//				}
//				byte[] byteArray2 = value.getAddressProofUpload();
//				try {
//					System.out.print("Address proof document downloaded at location:");
//					System.out.println("adressproofdoc" + value.getUserId() + ".txt");
//
//					FileWriter writer = new FileWriter("adressproofdoc" + value.getUserId() + ".txt");
//					BufferedWriter out = new BufferedWriter(writer);
//					for (Byte b : byteArray2) {
//						out.write(b);
//					}
//					out.close();
//				} catch (Exception e) {
////					LOGGER.error("Address proof doc download error");
//				}
//
//			}
//		}
//		return value;
//
//	}
//	
//	private void clearScreen() {
//		for (int i = 0; i < 75; i++) {
//			System.out.println("");
//		}
//		System.out.println("--------------------------------------------------------------------------------");
//	}
//
//	private void updateServiceProvider(Scanner scanner, List<ServiceProvider> serviceProviders,
//			ServiceProviderService service) {
//		ServiceProvider value = showFromList(scanner, serviceProviders, service);
//		if (value == null) {
//			return;
//		}
//		System.out.println("Press 1 to keep in pending and return Press any other number to proceed");
//		integerLoop(scanner);
//		int j = scanner.nextInt();
//		scanner.nextLine();
//		if (j == 1) {
//			return;
//		}
//		System.out.println("Enter true to Approve, false to Disapprove");
//		try {
//			while (!scanner.hasNextBoolean()) {
//				System.out.println("Enter true or false!");
//				scanner.next();
//				scanner.nextLine();
//			}
//			String approval = scanner.nextBoolean();
//			scanner.nextLine();
//			System.out.println("Provide remarks");
//			String remarks = scanner.nextLine();
//			value.setRemarks(remarks);
//			service.approveSP(value, approval);
//		} catch (IBSException exception) {
////			LOGGER.error("boolean is not being entered by bank admin");
//			System.out.println(exception.getMessage());
//		}
//		// serviceProviders.remove((index));
//	}
//}
