package com.adobe.proj.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.proj.entity.Product;
import com.adobe.proj.service.NotFoundException;
import com.adobe.proj.service.OrderService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("api/products") // this is the uri pattern
public class ProductController {

	@Autowired
	private OrderService orderService;
	
//	@GetMapping() // problem here is that this is not RPC. you can't pass Objects; Only http slangs. Onlu XML/JSON strings can be processed
//	// so mention @ResponseBody to make the http handlers come in. to convet your list of products to json/xml based on accept header.
//	// Accept: application/json
//	// GET http://localhost:8080/api/products
//	public @ResponseBody List<Product> getProducts(){
//		return orderService.getProducts();
//	}
	
	// here using extra path parameter. id is a path variable
	// GET http://localhost:8080/api/products/4
	@ResponseStatus(value=HttpStatus.OK)
	@GetMapping("/{id}")
	@Cacheable(value="productCache", key="#id") // whatever is the argument passed in function will be the key for cache. It invokes this method. Returned value first written to the cache. And will be stored by the particular key. Then returns the response.
	// From the next request onwards. First checks if it is already available in the cache, then won't enter into the above code. It will directly returns back from the cache.
	public @ResponseBody Product getProduct(@PathVariable("id") int id) throws NotFoundException { // spring data conversions (this will convert string to int). So for all built in availabe for primitive types. Anything coming from above url is a string. But all the conversion like string to int or double are already avaialbe. no need to explicit convert it. All the message converters are already available 
		System.out.println("Cache Missed!!! ");
		try {
			Thread.sleep(2000); // to see if not caching then make 2sec extra spend. if cached 2 sec not wasted.
		}catch(Exception ex) {
			
		}
		return orderService.getProductById(id);	
	}
	
	// query parameter: query parameters are retrieved as RequestParams 
	// GET http://localhost:8080/api/products?low=5000&high=50000
	// GET http://localhost:8080/api/products this is the case for default value=0.0
	@GetMapping()
	public @ResponseBody List<Product> getProducts(@RequestParam (name="low", defaultValue="0.0")double low, @RequestParam (name="high", defaultValue="0.0")double high){
		if(low == 0.0 & high==0.0 ) {
			return orderService.getProducts(); // get all products base case
		}
		return orderService.getByRange(low, high);
	}
	
	// Content-type: application/json
	// POST http://localhost:8080/api/products -> this is the uri only type changes to POST
	//  so now payload coming in json will be converted to product now
	//to remeber Not @ResponseEntity just ResponseEntity
	
//	@ResponseStatus(value=HttpStatus.CREATED)
	@PostMapping()
	@Cacheable(value="productCache",key="#p.id" )
//	@Cacheable(value="productCache",key="#p.id" , condition= "#p.price>=5000") //can also mention condition condition based on the arguments (i/p). 
//	@Cacheable(value="productCache",key="#p.id" , result = "#p.price>=5000") // Throwing error unless is ok below Other things result= "#p.price>=5000" then check made at the o/p
//	@Cacheable(value="productCache",key="#id" , unless = "#p.price>=5000") // unless is based on the return type
	public ResponseEntity<Product> addProduct(@RequestBody @Valid Product p){ // Client is sending the payload. That comes in the Request Body. Convert it into your Product now.So based on the content type. So you will be telling that client is sending json (from content type header). That json coming in request body. give it to appropriate message handler. this is for jackson. Jackson converts json to object and passes it to argument as Product p.
		orderService.insertProduct(p);
		// now we need to return back. simply could have returned back the product here. and give @ResponseBody then perfectly fine
		// but just to show difference insted of above
		// going for ResponseEntity of product plus http meassage is returned
		// what it does is if you want to pass some extra headers. especially headers related to caching like when was class created etc.
		// so it allows me to add additional info with this
		
		return new ResponseEntity<Product> (p, HttpStatus.CREATED);
		// for this created could have also simply put the commented annotation @ResponseStatus(value=HttpStatus.CREATED)
		// so response entity is especially useful for adding additional info on caching related.
	}
	
	@GetMapping("/clear-cache")
	@CacheEvict(value="productCache", allEntries= true) // not good way since asking the client to clear the cache. 
	public @ResponseBody String clear() throws Exception {
		return "Cache cleared!!!";
	}
	
	
//	@PutMapping() //this is one way in which id was used from payload but not good generally payloads don't have ids
//	public @ResponseBody Product updateProduct(@RequestBody Product p) {
//		return orderService.updatePrice(p.getId(), p.getPrice());
//	}
	@PutMapping("/{id}") // payload need not have id
	@CachePut(value="productCache",key="#id") // for cachin related
	public @ResponseBody Product updateProduct(@PathVariable("id")int id,@RequestBody @Valid Product p) throws NotFoundException {
		return orderService.updatePrice(p.getId(), p.getPrice());
	}
	
	// this is bad practice always create in GlobalCtrollerExceptionHandler
//	@ExceptionHandler(NotFoundException.class) // make sure this is importing com.adobe.proj.service.NotFoundException; and not import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
//	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) { // for RestController changing return type to ResponseEntity, If an exception is not handled by try catch and internally thrown then this will resolve those types of exception
//		// example my ProductController is internally throwing exception without handling by try/catch
//		Map<String, Object> body= new LinkedHashMap<String,Object>();
//		body.put("message",ex.getMessage() );
//		body.put("timestamp", new Date());
//		
//		return new ResponseEntity <Object>(body,HttpStatus.NOT_FOUND); // now we converted to 404-Resource not Found
//		
//		
//	}
	
	// delete methode to be done
	
	
	// ETAG first call adding the ETAG first time
	// can not test with nrowser as can not have extra header so going with postman
	// for the etag header related to caching
	// bad practice
	@GetMapping("/cache/{id}") // can not send back @ResponseBody wrap it into the response entity
	public ResponseEntity<Product> getProductCache(@PathVariable("id") int id) throws NotFoundException { // spring data conversions (this will convert string to int). So for all built in availabe for primitive types. Anything coming from above url is a string. But all the conversion like string to int or double are already avaialbe. no need to explicit convert it. All the message converters are already available 
		Product p= orderService.getProductById(id);	
		return ResponseEntity.ok().eTag(Long.toString(p.hashCode())).body(p);
		// .etag genearating etag -- can take any way here taking version as it keeps  changing whenever data gets modified. or since we added @Data on Product entity we have hashcodes and equals here.
		//Hashcode-> numerical representation of an object. Whenver any attribute changes hascode will be regenerated.
		// since etag requires string so converting from long to string
	}
	
	
}
