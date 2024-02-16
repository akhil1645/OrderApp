package com.adobe.proj.cfg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

//import com.adobe.proj.dao.CustomerDao;
import com.adobe.proj.entity.Customer;
import com.adobe.proj.service.CustomerService;

@Configuration
public class CustomerCRUDConfig implements CommandLineRunner{

//	@Autowired
//	private CustomerDao customerDao;
	@Autowired
	private CustomerService customerService;
	
	@Override
	public void run(String... args) throws Exception {
//		 if(customerDao.count()==0) {
//			 customerDao.save(Customer.builder()
//					 .email("a1@adobe.com")
//					 .firstName("akhil")
//					 .lastName("hooda")
//					 .build());
//			 customerDao.save(Customer.builder()
//					 .email("a2@adobe.com")
//					 .firstName("sahil")
//					 .lastName("hooda")
//					 .build());
//		 }
		
//		addCustomers();
//		findCustomers();
//		updateCustomer();
		System.out.println("AKHILCUSTOMERRUN");
		
		
	}
	private void updateCustomer() {
		String email="a4@adobe.com";
		String lastNameChangedTo="gupta";
		Customer c_before=customerService.getCustomerByEmail(email);
		System.out.println(c_before);
		System.out.println("Befor was above now after below");
		Customer c_ex= customerService.updateCustomerAndVerify(email, lastNameChangedTo);
		System.out.println(c_ex);
		System.out.println("Even UPDATE working");
	}
	private void findCustomers() {
		String firstName="ram";
		String lastName="hooda";
//		List<Customer> cl= customerService.getCustomerByFirstAndLastName(firstName, lastName);
//		for(Customer val:cl) {
//			System.out.println(val);
//			System.out.println("HA HA");
//		}
//		
//		System.out.println("Finish Customer printing by firstname and last Name");
//		List<Customer> c_last= customerService.getCustomerByLastName(lastName);
//		for(Customer val:c_last) {
//			System.out.println(val);
//			System.out.println("OO");
//		}
//		System.out.println("Finish Customer printing by  last Name");
		List<Customer> customerOrlist= customerService.getCustomerByFirstOrLastName(firstName, lastName);
		for(Customer val:customerOrlist) {
			System.out.println(val);
			System.out.println("HA HA");
		}
	}
	
	private void addCustomers() {
		 Customer c1=Customer.builder()
				 .email("a3@adobe.com")
				 .firstName("ram")
				 .lastName("sharma")
				 .build();
		 Customer c2=Customer.builder()
				 .email("a4@adobe.com")
				 .firstName("shyam")
				 .lastName("verma")
				 .build();
		 
		 customerService.insertCustomer(c1);
		 customerService.insertCustomer(c2);
		 
	}
	

}
