package com.adobe.proj.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adobe.proj.entity.Product;
import com.adobe.proj.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // it has all the methods like get, post, put, delete etc
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // this has to do with json-path and all. like result to validate.
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*; 
//MockMvc Related Imports;
// for the static imports auto complete won't come so pick these imports from MockMvc definition

// Most important not auto completed below import, learn it
import static org.hamcrest.Matchers.hasSize; // this is for the hson-path matcher learn this
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;



@WebMvcTest(ProductController.class) // And within the test bed we are telling ProductController alone should be loaded to test bed.
public class ProductControllerTest {
	@MockBean
	private OrderService orderService;// created a mockbean. this is not interacting with the db but we will have to tell to print the hard coded products not one from DB
	
	/*
	 * MockMvc mockMvc = webAppContextSetup(wac).build();

 mockMvc.perform(get("/form"))
     .andExpectAll(
         status().isOk(),
         content().contentType("text/html"),
         forwardedUrl("/WEB-INF/layouts/main.jsp")
     );
 
 MockMvc allows me to perform all these opreations like get POST and all
	 */
	
	@Autowired // this is used to perform the crud operations
	private MockMvc mockMvc;
	
	@Test
	public void getProductsTest() throws Exception{
		List<Product> products= new ArrayList<>();
		products.add(Product.builder().id(1).name("abc").price(50000).quantity(50).build());
		products.add(Product.builder().id(3).name("xyz").price(40000).quantity(40).build());
		
		when(orderService.getProducts()).thenReturn(products); // mocking
		// we should tell the mock service how to behave so created the mocking behavior
		
		// remember to put / before api/products
		mockMvc.perform(get("/api/products")) 
			.andExpect(status().isOk())
			.andExpect(jsonPath("$",hasSize(2)))
			.andExpect(jsonPath("$[0].name",is("abc")));
			
//			.andExpect(jsonPath("$[0].name","abc"));  // this is giving error in video no ResultMatcher
		
		
		
		// here "api/products" will resolve Request mapping and get will resolve GetMapping int the ProductController code
		// in jsonpath $ means everything, since 2 products so size=2
		// similarly $0 => first product
		
		verify(orderService, times(1)).getProducts(); // here from the method is called here, even if hardcoded then it would have failed
		// how many times from mockito here. one api call should go to getProducts() how many times.
		// here count is of only this orderService.getProducts() method
		// orderService.getByRange(low, high) this method call doesn't count
		// or other hard coded code doesn't work here
		// as we should get the data by making a call to the service
		// so how we got the data is also important
	}
	
	@Test
	public void addProductTest() throws Exception{
		Product p=Product.builder().name("abc").price(50000).quantity(50).build(); // id not required as while building it is auto incremented so not mention in test also
		
		ObjectMapper mapper= new ObjectMapper();// from jackson
		String json=mapper.writeValueAsString(p); // convet Product to json. now sending this json as payload to the server. similar to Postman. POST call
		
		when(orderService.insertProduct(Mockito.any(Product.class)))
			.thenReturn(Mockito.any(Product.class)); // mocking
		// the insertProduct present on server should take a product at the end. And this product will be created from json. 
		// i don't know the name of object i.e. final Product as it will be created form json and internally http handler methods are going to create it.
		// i don't have reference of internal conversion. so can't give exaclty p here it will fail
		// when(orderService.insertProduct(p)) -> it will fail in this case. becuse this is in client got converted to json then goes to server. that p will not be addressed and on checking issue here 
		// 
		// so in short my service has to takee a product and return back a product that's all
		
		
		// remember to put / before api/products
		mockMvc.perform(post("/api/products")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		// when making post should give content=json string and  content-type=application/json
		
		
		verify(orderService, times(1)).insertProduct(Mockito.any(Product.class)); // verify that this method is called only once
		
	}
	
	@Test
	public void addProductExceptionTest() throws Exception{ // should say that name is not found other errors etc since keeping name as empty
		Product p=Product.builder().name("").price(0.00).quantity(-15).build(); // id not required as while building it is auto incremented so not mention in test also
		
		ObjectMapper mapper= new ObjectMapper();// from jackson
		String json=mapper.writeValueAsString(p); // convet Product to json. now sending this json as payload to the server. similar to Postman. POST call
		
		// no need for mocking because it will not enter the function insertProduct() at all because of @Valid
		
		// remember to put / before api/products
		mockMvc.perform(post("/api/products")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.errors",hasSize(3)))
			.andExpect(jsonPath("$.errors",hasItem("Name is required ")));
//		.andExpect(jsonPath("$",is("abc")));
		// when making post should give content=json string and  content-type=application/json
		
		
		verifyNoInteractions(orderService); 
		
	}
	
	

}
