package com.sheryians.major.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheryians.major.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<List<Product>> findAllByCategory_Id(int id);

}
