package com.enspy.dons.orderservice;

import com.enspy.dons.orderservice.entities.Order;
import com.enspy.dons.orderservice.entities.ProductItem;
import com.enspy.dons.orderservice.enums.OrderStatus;
import com.enspy.dons.orderservice.model.Customer;
import com.enspy.dons.orderservice.model.Product;
import com.enspy.dons.orderservice.repositories.OrderRepository;
import com.enspy.dons.orderservice.repositories.ProductItemRepository;
import com.enspy.dons.orderservice.services.CustomerRestClientService;
import com.enspy.dons.orderservice.services.InventoryRestClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


import java.util.*;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner start(OrderRepository orderRepository, ProductItemRepository productItemRepository,
                            CustomerRestClientService customerRestClientService, InventoryRestClientService inventoryRestClientService){
        return args -> {
            List<Customer> customers =  customerRestClientService.allCustomers().getContent().stream().toList();
            List<Product> products =  inventoryRestClientService.allProducts().getContent().stream().toList();
            Long customerId = 1L;
            Customer customer = customerRestClientService.customerById(customerId);
            Random random = new Random();
            for (int i =0; i<=10000; i++) {
                Order order = Order.builder()
                        .customerId(customers.get(random.nextInt(customers.size())).getId())
                        .status(Math.random() > 0.5 ? OrderStatus.PENDING : OrderStatus.CREATED)
                        .createdAt(new Date())
                        .build();

                Order savedOrder = orderRepository.save(order);
                if (Math.random() > 0.70) {
                    for (int j = 0; j <= products.size()-1; j++) {
                        ProductItem productItem = ProductItem.builder()
                                .order(savedOrder)
                                .productId(products.get(j).getId())
                                .price(products.get(j).getPrice())
                                .quantity(1 + random.nextInt(21))
                                .discount(Math.random())
                                .build();
                        productItemRepository.save(productItem);
                    }
                }
            }
        };
    }
}
