package com.adobe.proj.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="items")
@Data // for getter,setters,default constructors
@NoArgsConstructor
@AllArgsConstructor
@Builder // so that you can get object using build
public class Item {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // auto increment  other options are sequence or any other table having list of primary keys
	@Column(name="item_id")
	private int itemId;
	
	// order_fk already present in order
	
	@ManyToOne
	@JoinColumn(name="product_fk")
	private Product product;
	
	private int quantity;
	
	private double amount; // this also accounts for discounts ore other things son not simply product price * quantity
	
	
	
}
