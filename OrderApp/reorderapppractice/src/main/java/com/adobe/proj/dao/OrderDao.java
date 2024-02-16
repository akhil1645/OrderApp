package com.adobe.proj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adobe.proj.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {

}
