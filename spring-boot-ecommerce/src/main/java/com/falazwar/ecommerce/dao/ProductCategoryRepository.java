package com.falazwar.ecommerce.dao;

import com.falazwar.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

// this will expose rest api http://localhost:8080/api/product-category{?page,size,sort}
// Spring Data REST will expose ALL HTTP method (POST, GET, GET(plural), PUT, DELETE)
@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "productCategory", path = "product-category")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
