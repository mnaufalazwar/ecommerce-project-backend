package com.falazwar.ecommerce.dao;

import com.falazwar.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

// this will expose rest api http://localhost:8080/api/products{?page,size,sort}
// Spring Data REST will expose ALL HTTP method (POST, GET, GET(plural), PUT, DELETE)
@CrossOrigin
public interface ProductRepository extends JpaRepository<Product, Long> {
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
