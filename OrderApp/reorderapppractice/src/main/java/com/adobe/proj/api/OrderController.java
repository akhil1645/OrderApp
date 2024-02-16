package com.adobe.proj.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.proj.entity.Order;
import com.adobe.proj.service.OrderService;

@RestController
@RequestMapping("api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService; // this will be generally one per application or 1 per role
	
	
	@GetMapping
	public @ResponseBody List<Order> getOrders(){
		return orderService.getOrders();
	}
	
	@PostMapping 
	@ResponseStatus(value= HttpStatus.CREATED)
	// here carefully see that responsebody is of type string
	public @ResponseBody String  placeOrder(@RequestBody Order o) {
		orderService.placeOrder(o);
		return "Order placed with Order id "+ o.getOrederId();
	}

}
