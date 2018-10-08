package com.capgemini.productapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.productapp.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{

}
