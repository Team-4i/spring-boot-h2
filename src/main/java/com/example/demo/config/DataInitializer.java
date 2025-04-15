package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ItemRepository itemRepository;
    
    @Value("${app.init-db:false}")
    private boolean shouldInitializeDb;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DataInitializer.run called - shouldInitializeDb=" + shouldInitializeDb);
        
        // Check if we already have items
        long count = itemRepository.count();
        System.out.println("Current item count: " + count);
        
        // Only add items if we should initialize and there are no items
        if (shouldInitializeDb && count == 0) {
            System.out.println("Initializing database with sample data via DataInitializer...");
            
            itemRepository.save(new Item("Programmatic Item 1", "Added by DataInitializer - Item 1"));
            itemRepository.save(new Item("Programmatic Item 2", "Added by DataInitializer - Item 2"));
            itemRepository.save(new Item("Programmatic Item 3", "Added by DataInitializer - Item 3"));
            itemRepository.save(new Item("Programmatic Item 4", "Added by DataInitializer - Item 4"));
            itemRepository.save(new Item("Programmatic Item 5", "Added by DataInitializer - Item 5"));
            
            System.out.println("Database initialization completed! New count: " + itemRepository.count());
        } else {
            System.out.println("Skipping database initialization via DataInitializer");
        }
    }
}