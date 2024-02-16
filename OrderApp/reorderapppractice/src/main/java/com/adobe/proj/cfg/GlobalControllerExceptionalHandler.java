package com.adobe.proj.cfg;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adobe.proj.service.NotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalControllerExceptionalHandler {
	@ExceptionHandler(NotFoundException.class) // make sure this is importing com.adobe.proj.service.NotFoundException; and not import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) { // for RestController changing return type to ResponseEntity, If an exception is not handled by try catch and internally thrown then this will resolve those types of exception
		// example my ProductController is internally throwing exception without handling by try/catch
		Map<String, Object> body= new LinkedHashMap<String,Object>();
		body.put("message",ex.getMessage() );
		body.put("timestamp", new Date());
		
		return new ResponseEntity <Object>(body,HttpStatus.NOT_FOUND); // now we converted to 404-Resource not Found
		
		
	}
	
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) { 
//		Map<String, Object> body= new LinkedHashMap<String,Object>();
//		// here i need to traverse through the list of exception which are occurring
//		
//		List<String> errorMessages = new ArrayList<>();
//		System.out.println("Exception Handle");
//        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
//            errorMessages.add(violation.getMessage());
//            System.out.println(violation.getMessage());
//        }
//
//			
//        
//		// if worked on controller knowing this
//		body.put("errors",errorMessages );
//		body.put("timestamp", new Date());
//		
//		return new ResponseEntity <Object>(body,HttpStatus.BAD_REQUEST); // now we converted to 404-Resource not Found
//		
//		
//	}
	
	
	// since getting Argument nor valid esception so above exception is failing
	//[org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.adobe.proj.entity.Product> com.adobe.proj.api.ProductController.addProduct(com.adobe.proj.entity.Product) with 3 errors: [Field error in object 'product' on field 'name': rejected value []; codes [NotBlank.product.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.name,name]; arguments []; default message [name]]; default message [Name is required ]] [Field error in object 'product' on field 'quantity': rejected value [-20]; codes [Min.product.quantity,Min.quantity,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.quantity,quantity]; arguments []; default message [quantity],0]; default message [Quantity -20 should be more than 0 ]] [Field error in object 'product' on field 'price': rejected value [7.0]; codes [Min.product.price,Min.price,Min.double,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.price,price]; arguments []; default message [price],10]; default message [Price 7.0 should be more than 10 ]] ]
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) { 
		Map<String, Object> body= new LinkedHashMap<String,Object>();
		// here i need to traverse through the list of exception which are occurring
		
		List<String> errors=ex.getBindingResult().getFieldErrors().stream()
				.map(exception -> exception.getDefaultMessage())
				.collect(Collectors.toList());
		// if worked on controller you would know this that any validation connected with the input in  the exception. it will be stored as a binding result here
		// .getFieldErrors() will give me these things
		// [Field error in object 'product' on field 'name': rejected value []; codes [NotBlank.product.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.name,name]; arguments []; default message [name]]; default message [Name is required ]] 
		//[Field error in object 'product' on field 'quantity': rejected value [-20]; codes [Min.product.quantity,Min.quantity,Min.int,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.quantity,quantity]; arguments []; default message [quantity],0]; default message [Quantity -20 should be more than 0 ]] 
		//[Field error in object 'product' on field 'price': rejected value [7.0]; codes [Min.product.price,Min.price,Min.double,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.price,price]; arguments []; default message [price],10]; default message [Price 7.0 should be more than 10 ]] ]
		// so 3 field errors are there.
		// stream()-> java 8 stream traverse through them
		// then for each exception you get me map to transform data 
		// map(exception -> exception.getDefaultMessage()) - it means i/p is these 3 exception traverse them and form them go on getting default message for all three
		// .collect(Collectors.toList()) we collected all the 3 errors with default messages coming from the exceptions.
		
		body.put("errors",errors );
		body.put("timestamp", new Date());
		return new ResponseEntity <Object>(body,HttpStatus.BAD_REQUEST); // now we converted to 404-Resource not Found
		
		
		
	}
	
}
