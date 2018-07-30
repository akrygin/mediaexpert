package com.mediaexpert;

import com.mediaexpert.entity.bean.Item;
import com.mediaexpert.entity.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.mediaexpert.entity, com.mediaexpert.controller")
public class ApplicationStarter {
    private static final Logger log = LoggerFactory.getLogger(ApplicationStarter.class);

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class);
    }

    @Bean
    public CommandLineRunner loadData(ItemRepository repository) {
        return (args) -> {

            repository.save(new Item("Apple X", "Very good Smartphone, but expensive", "80000"));
            repository.save(new Item("Samsung S9", "Not bad, but I do not know", "45000"));

            // fetch all items
            log.info("Items found with findAll():");
            log.info("-------------------------------");
            for (Item item : repository.findAll()) {
                log.info(item.toString());
            }
            log.info("");

            // fetch an individual item by ID
            Item item = repository.findById(1L).get();
            log.info("Item found with findOne(1L):");
            log.info("--------------------------------");
            log.info(item.toString());
            log.info("");

            // fetch items by last name
            log.info("Item found with findByNameStartsWithIgnoreCase('Samsung'):");
            log.info("--------------------------------------------");
            for (Item samsung : repository
                    .findByNameStartsWithIgnoreCase("Samsung")) {
                log.info(samsung.toString());
            }
            log.info("");
        };
    }
}