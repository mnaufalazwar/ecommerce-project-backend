package com.falazwar.ecommerce.config;

import com.falazwar.ecommerce.entity.Country;
import com.falazwar.ecommerce.entity.Product;
import com.falazwar.ecommerce.entity.ProductCategory;
import com.falazwar.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

  @Value("${allowed.origins}")
  private String[] theAllowedOrigins;
  private EntityManager entityManager;

  @Autowired
  public MyDataRestConfig(EntityManager theEntityManager) {
    entityManager = theEntityManager;
  }

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
//    RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

    HttpMethod[] theUnsupportedActions = {
        HttpMethod.PUT,
        HttpMethod.POST,
        HttpMethod.DELETE,
        HttpMethod.PATCH
    };

    // disable HTTP method for product
    disableHttpMethods(Product.class, config, theUnsupportedActions);

    // disable HTTP method for product category
    disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

    // disable HTTP method for country
    disableHttpMethods(Country.class, config, theUnsupportedActions);

    // disable HTTP method for state
    disableHttpMethods(State.class, config, theUnsupportedActions);

    //// these code below is added to expose the entity id
    // call an internal helper method
    exposeIds(config);

    // configure the cors mapping
    cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);

  }

  private static void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
    config.getExposureConfiguration()
        .forDomainType(theClass)
        .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)))
        .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));
  }

  //// these code below is added to expose the entity id
  private void exposeIds(RepositoryRestConfiguration config) {
    // expose entity ids
    //

    // - get a list of all entity classes from the entity manager
    Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

    // - create an array of the entity types
    List<Class> entityClasses = new ArrayList<>();

    // - get the entity types for the entities
    for (EntityType tempEntityType : entities) {
      entityClasses.add(tempEntityType.getJavaType());
    }

    // - expose the entity ids for the array of entity/domain types
    Class[] domainTypes = entityClasses.toArray(new Class[0]);
    config.exposeIdsFor(domainTypes);
  }
}

/*
notes :
This class is used as a configuration class for configuring REST Data API
*/
