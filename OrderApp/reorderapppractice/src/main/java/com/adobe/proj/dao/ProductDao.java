package com.adobe.proj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.proj.entity.Product;

// no need to create @Repository class spring data JPA Generates it
public interface ProductDao extends JpaRepository<Product, Integer> {
	
	List<Product> findByIdIn(List<Integer>id);
	
	// this one is sql exapmle
	
	// @Query(value="select * from products where price>= :l and price<= :h",nativeQuery=true) 
	
	// by default here it is JPQL
	@Query("from Product where price>= :l and price<= :h")
	List<Product> getByRange(@Param("l")double low, @Param("h") double high);// so her in some casese like these we may have to put our own queries
	
	@Modifying  // anything other than select statement in query because behind the scene it is in jdbc of type exwcuteUpdate since other than select
//	@Query("from Product set price= :pr where id= :id") // in training this was written
	@Query("UPDATE Product p SET p.price = :pr Where p.id = :id ")
	void updateProduct(@Param("id")int id, @Param("pr")double price);
}
