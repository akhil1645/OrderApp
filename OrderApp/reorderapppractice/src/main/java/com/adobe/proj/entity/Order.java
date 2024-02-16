package com.adobe.proj.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orders")
@Data // for getter,setters,default constructors
@NoArgsConstructor
@AllArgsConstructor
//@Builder // so that you can get object using build // her in date giving error to declare it as final so commenting builder
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // auto increment  other options are sequence or any other table having list of primary keys
	@Column(name="oid")
	private int orederId;
	
	@Temporal(TemporalType.TIMESTAMP) // by default it will store dd-mm-yy type only now seconds time will be also added
	@Column(name="order_date")
	private Date orderDate=new Date();
	
	private double total;
	 
	@ManyToOne  // here automatically eager fetches it will automatically combine orders to the customer
	@JoinColumn(name="customer_fk")
	private Customer customer;
	
	
	@OneToMany(cascade = CascadeType.ALL ,fetch=FetchType.EAGER) // commented to explain Lazy fetching exception
//	@OneToMany(cascade = CascadeType.ALL )// this will give lazy fetching exception as some proxy is used
	@JoinColumn(name="order_fk")
	private List<Item> items=new ArrayList<>();
	
	

}
