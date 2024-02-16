package com.adobe.proj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.proj.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, String> {
	// writing custom queries no need to write code
	
	// select * from customers where lastName=?
	List<Customer> findByLastName(String lastName);// this is the custom configured
	// here start with getBy or findBy  then a field present in customer like LastName only thing here is converted to camel case here
	// so a simple way of writing custom query
	
	// select * from customers where firstName=? AND lastName=?
	List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
	
//	// select * from customers where firstName=? OR lastName=?
	List<Customer> findByFirstNameOrLastName(String firstName, String lastName);
	
	@Modifying  // anything other than select statement in query because behind the scene it is in jdbc of type exwcuteUpdate since other than select
//	@Query("from Customer set lastName= :lname  where email= :email")
	@Query("UPDATE Customer c SET c.lastName = :lname WHERE c.email = :email")
//	@Query("set last_name = lname from customers where email=email")
	void updateCustomer(@Param("email")String email, @Param("lname")String lastName);
	
}
