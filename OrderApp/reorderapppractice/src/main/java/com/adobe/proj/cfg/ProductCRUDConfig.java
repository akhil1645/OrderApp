package com.adobe.proj.cfg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.adobe.proj.entity.Product;
import com.adobe.proj.service.OrderService;

import jakarta.transaction.Transactional;

@Configuration
public class ProductCRUDConfig implements CommandLineRunner{

	@Autowired
	private OrderService orderService;
	
	@Override
	public void run(String... args) throws Exception { // this method is automatically going to get recalled as soon as the spring container is created. No need to explicitly call it. n number of classes can implement command line runner all will be called 
		System.out.println("AKHIL _PRODUCT_RUN");
//		addProducts();
//		listProducts();
//		orderService.modifyProduct();
	}
	
	
//	


	private void listProducts() {
		List<Product> products= orderService.getProducts();
		for(Product p: products) {
			System.out.println(p +"AkhilProducts");
		}
		
	}

	private void addProducts() {
		Product p1= Product.builder()
				.name("e14")
				.price(5900.00)
				.quantity(100)
				.build();
		
		Product p2= Product.builder()
				.name("OMouseus12T")
				.price(2900.00)
				.quantity(98)
				.build();
		orderService.insertProduct(p1);
		orderService.insertProduct(p2);
		
	}

}
