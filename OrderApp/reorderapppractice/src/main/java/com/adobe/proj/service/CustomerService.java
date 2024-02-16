package com.adobe.proj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.proj.dao.CustomerDao;
import com.adobe.proj.entity.Customer;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;
	
	public Customer insertCustomer(Customer c) {
		return  customerDao.save(c);
	}
	public Customer getCustomerByEmail(String email) {
		Optional<Customer> opt=customerDao.findById(email);
		if(opt.isPresent()) {
			return opt.get();
		}
		else {
			return null;
		}
	}
	
	public List<Customer> getCustomers(){
		return customerDao.findAll();
	}
	
	public List<Customer> getCustomerByLastName(String lastName){
		return customerDao.findByLastName(lastName);
	}
	
	public List<Customer> getCustomerByFirstAndLastName (String firstName, String lastName){
		
		return customerDao.findByFirstNameAndLastName(firstName, lastName);
	}
	public List<Customer> getCustomerByFirstOrLastName (String firstName, String lastName){
		
		return customerDao.findByFirstNameOrLastName(firstName, lastName);
	}
	
	@Transactional
	public Customer updateCustomerAndVerify(String email,String lastName) {
		customerDao.updateCustomer(email, lastName);
		Optional<Customer> opt=customerDao.findById(email);
		if(opt.isPresent()) {
			return opt.get();
		}
		else {
			return null;
		}
	}
}
