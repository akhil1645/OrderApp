package com.adobe.proj.entity;

import java.io.Serializable;

//import org.hibernate.validator.constraints.NotBlank;  // don't take org hibernate one then it will be directly coupled
// if we take org then it will be tighly coupled and in future if we change from hibernate to other like(toplink) then issue. so take from javax.persistence

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="products")
@Data // for getter,setters,default constructors
@NoArgsConstructor
@AllArgsConstructor
@Builder // so that you can get object using build
public class Product implements Serializable { // so basically implements Serializable this is the marker interface which will allow me to write data to the stream  here 
	private static final long serialVersionUID = -1958187467740696203L; // this ensures that writer and reader have the same version of the product
	// good to have serial version id (not compulsion)
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // auto increment  other oprions are sequence or any other table having list of primary keys
	private int id;
	
	@NotBlank(message="Name is required ") //  don't take hibernate one then it will be directly coupled
	private String name;
	
	@Min(value=10, message="Price ${validatedValue} should be more than {value} ") // validatedValue this will be what is the actual value coming from the payload, {value} =10 min valuw written before
	private double price;
	
	@Min(value=0, message="Quantity ${validatedValue} should be more than {value} ")
	@Column(name="qty")
	private int quantity;
	
	
	@Version
	private int version;
	
}
