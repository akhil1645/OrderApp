package com.adobe.proj.cfg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.adobe.proj.entity.Customer;
import com.adobe.proj.entity.Item;
import com.adobe.proj.entity.Order;
import com.adobe.proj.entity.Product;
import com.adobe.proj.service.OrderService;

@Component // here needed at least one of the annotations so going for component simplest
public class OrderCRUD implements CommandLineRunner {

	@Autowired
	private OrderService orderService;
	
	@Override
	public void run(String... args) throws Exception {
		
//		// this below code is to demonstrate the lazy fetching exception
//		List<Order> orders=orderService.getOrders();
//		for(Order o: orders) {
//			System.out.println(o.getOrederId()+ " , "+ o.getCustomer().getEmail());
//			// Lazy initialisztion exception (also see commented order service getorders function)
//			List<Item> items= o.getItems();// this will populate a proxy . items will be a proxy object. it doesn't contains the actual object.
//			// so this Proxy->> trues to make a necction to DB and fetch (what is happening but at that point of time connection is already lost)
//			
//			for(Item i: items) {
//				System.out.println(i.getAmount());
//			}
//		}
		// getting this error with above code
		// org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.adobe.proj.entity.Order.items: could not initialize proxy - no Session
		
		// on commennting above part even for removing the eager type(Order.java) 
		// even after this if we go ahead with webapplication fetching get api/orders code is working
		// but standalone it is not working
		// this is because of Spring Mvc uses OpenSessionInView Design Pattern  
		
//		Order order= new Order();
//		order.setCustomer(Customer.builder().email("a1@adobe.com").build());
//		order.getItems().add(Item.builder()
//				.product(Product.builder().id(1).build())
//				.quantity(3)
//				.build());
//		order.getItems().add(Item.builder()
//				.product(Product.builder().id(3).build())
//				.quantity(2)
//				.build());		
//		orderService.placeOrder(order);  // commented for testing versioning

	}

}
