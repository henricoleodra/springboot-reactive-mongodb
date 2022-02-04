package com.henricoleodra.springreactivemongodb.handler;

import com.henricoleodra.springreactivemongodb.controller.ProductController;
import com.henricoleodra.springreactivemongodb.dto.ProductDto;
import com.henricoleodra.springreactivemongodb.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    ProductService service;

    public Mono<ServerResponse> loadProducts(ServerRequest serverRequest) {
        Flux<ProductDto> productList = service.getProducts();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productList, ProductDto.class);
    }

    public Mono<ServerResponse> loadProduct(ServerRequest serverRequest) {
        Mono<ProductDto> product = service.getProduct(serverRequest.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(product, ProductDto.class);
    }

    public Mono<ServerResponse> loadRangeProduct(ServerRequest serverRequest) {
        Flux<ProductDto> productList = service.getProductInRange(Double.parseDouble(serverRequest.queryParam("min").get()), Double.parseDouble(serverRequest.queryParam("max").get()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productList, ProductDto.class);
    }

    public Mono<ServerResponse> saveProduct(ServerRequest serverRequest) {
        Mono<ProductDto> productMono = serverRequest.bodyToMono(ProductDto.class);
        Mono<ProductDto> newProduct = service.saveProduct(productMono);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(newProduct, ProductDto.class);
    }

    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<ProductDto> productMono = serverRequest.bodyToMono(ProductDto.class);
        Mono<ProductDto> updatedProduct = service.updateProduct(productMono, id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(updatedProduct, ProductDto.class);
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        service.deleteProduct(id);
        return ServerResponse.ok().body(BodyInserters.fromValue("Successfully deleted product with id : " + id));
    }

}
