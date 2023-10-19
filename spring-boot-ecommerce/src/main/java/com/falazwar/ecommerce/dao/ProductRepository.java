package com.falazwar.ecommerce.dao;

import com.falazwar.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

// this will expose rest api http://localhost:8080/api/products{?page,size,sort}
// Spring Data REST will expose ALL HTTP method (POST, GET, GET(plural), PUT, DELETE)
// Includes :
// - http://localhost:8080/api/products?page=0&size=3 -> for pagination
// - http://localhost:8080/api/products/99 -> for get by id
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {

  // Behind the scenes, Spring will execute a query similar to this :
  // SELECT * FROM product where category_id=?
  // Spring DATA REST automatically exposes endpoint
  // http://localhost:8080/api/products/search/findByCategoryId?id=1
  Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);

  // Behind the scenes, Spring will execute a query similar to this :
  // SELECT * FROM Product p where p.name LIKE CONCAT('%', :name, '%')
  // Spring DATA REST automatically exposes endpoint
  // http://localhost:8080/api/products/search/findByNameContaining?name=
  Page<Product> findByNameContaining(@Param("name") String name, Pageable page);
}

/*
notes :
How if we don't want to expose all HTTP methods ?
1. Don't use Spring Data REST
  - Manually create @RestController
  - Manually define methods for access @GetMapping
  - But, we will lose the Spring Data Rest support for paging, sorting, etc
2. Use Spring Data REST
  - Configure to disable certain HTTP method -> MyDataRestConfig

for @CrossOrigin :
- If we want to specify the url for cross-origin support : @CrossOrigin("http://localhost:4200)
- origin is protocol + hostname + port, e.g. http://localhost:4200
*/
