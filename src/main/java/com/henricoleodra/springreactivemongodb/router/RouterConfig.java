package com.henricoleodra.springreactivemongodb.router;

import com.henricoleodra.springreactivemongodb.handler.CustomerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class RouterConfig {
    @Autowired
    private CustomerHandler handler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
//      return RouterFunctions.route()
//              .GET("/router/products/range", handler::loadRangeProduct)
//              .GET("/router/products", handler::loadProducts)
//              .GET("/router/products/{id}", handler::loadProduct)
//              .POST("/router/products", handler::saveProduct)
//              .PUT("/router/products/{id}", handler::updateProduct)
//              .DELETE("/router/products/{id}", handler::deleteProduct)
//              .build();
        return RouterFunctions.nest(path("/api/products"),
            RouterFunctions
                    .route(GET("/range"), handler::loadRangeProduct)
                    .andRoute(GET("/{id}"), handler::loadProduct)
                    .andRoute(GET(""), handler::loadProducts)
                    .andRoute(POST(""), handler::saveProduct)
                    .andRoute(PUT("/update/{id}"), handler::updateProduct)
                    .andRoute(DELETE("/delete/{id}"), handler::deleteProduct));



    }
}
