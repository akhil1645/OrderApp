package com.adobe.proj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.adobe.proj.entity.Product;

@Configuration // here not component as need to create a bean
public class RestTemplateExample implements CommandLineRunner {
	
	@Autowired
	RestTemplate restTemplate;
	
//	@Bean
//	public RestTemplate getRestTemplate(RestTemplateBuilder builder)
//	{
//		return builder.build();
//	} // if present here giving the circular dependency present error.so moved to main
	// so need to move this to some other config class
	// we are creating a bean here and wiring with autowired just above at same place so leading to circular dependency
	//  (maybe not sure of because) because this class is having dependency on itself 

	// any specific other thing you want to add you can use it into the rest template builder like security acces token to be added when performing cRUD operations then add across origin etc, 
	// or any other tags can be added with this filter.
	
	// now specifying how to consume your rest in the spring
	

	@Override
	public void run(String... args) throws Exception {
//		getProducts();	
//		getProduct();
//		addProduct();
	} 

	private void getProducts() {
		String str=restTemplate.getForObject("http://localhost:8080/api/products", String.class);
		// whatever is the json coming from this 'http://localhost:8080/api/products' convert it to string and give it back to me.
		// here http -> this thing can be any server to which making the call
		// if you want can use objectMApper to convert it to mapper and get object or any other things also.
		System.out.println(str);
	}
	
	
	private void getProduct() {
		ResponseEntity<Product> responseEntity=restTemplate.getForEntity("http://localhost:8080/api/products/2", Product.class);
		// since single product so instead of getting as a string get an Entity of type Product. second Argumet is type of entity class(like Product)
		// so here return type will be of responseEntity<Product>
		// responseEntity will have headers as well as the payload.
		System.out.println(responseEntity.getBody());
//		System.out.println(responseEntity.getHeaders());
	}
	
	private void addProduct() {
		Product p=Product.builder().name("Some LAtest Item").price(105.00).quantity(500).build(); // this is payload
		// this is product i want to post
		
		ResponseEntity<Product> responseEntity=restTemplate.postForEntity("http://localhost:8080/api/products", p,Product.class ); // 3rd argument is type of entity
		
//		System.out.println(responseEntity.getStatusCodeValue()); // 201 code ( in video this)
		
		System.out.println(responseEntity.getStatusCode());// by myself
	}
	
	
}
