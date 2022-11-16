package com.enspy.dons.customerservice;

import com.enspy.dons.customerservice.entity.Customer;
import com.enspy.dons.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

   @Bean
    CommandLineRunner  start(CustomerRepository customerRepository){
      return args -> {
          customerRepository.saveAll(List.of(
                  Customer.builder().name("Akouna").email("akouna@gmail.com").build(),
                  Customer.builder().name("Akeouna").email("akoun1a@gmail.com").build(),
                  Customer.builder().name("Aksdouna").email("3akouna@gmail.com").build(),
                  Customer.builder().name("Akrfouna").email("dakouna@gmail.com").build(),
                  Customer.builder().name("Akoruna").email("akofuna@gmail.com").build(),
                  Customer.builder().name("Akdouna").email("akosuna@gmail.com").build()
          ));
          customerRepository.findAll().forEach(System.out::println);
      };
    }
}
