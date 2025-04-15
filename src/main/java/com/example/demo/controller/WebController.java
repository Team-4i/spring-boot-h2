package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "index";
    }

    @GetMapping("/items")
    public String itemsPage(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("newItem", new Item());
        return "items";
    }

    @PostMapping("/items/add")
    public String addItem(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/items/edit/{id}")
    public String editItemPage(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id).orElseThrow();
        model.addAttribute("item", item);
        return "edit-item";
    }

    @PostMapping("/items/update/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute Item item) {
        item.setId(id);
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return "redirect:/items";
    }
    
    @GetMapping("/db-info")
    public String dbInfoPage(Model model) {
        Map<String, String> dbInfo = new HashMap<>();
        
        // Active profiles
        String[] activeProfiles = environment.getActiveProfiles();
        dbInfo.put("activeProfiles", Arrays.toString(activeProfiles));
        
        // Custom database selector
        String useMysql = environment.getProperty("app.use-mysql", "false");
        dbInfo.put("useMysql", useMysql);
        
        // Database metadata
        String dbProduct = "Unknown";
        String dbUrl = "Unknown";
        
        try {
            dbProduct = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
            dbUrl = environment.getProperty("spring.datasource.url");
        } catch (Exception e) {
            dbProduct = "Error retrieving database info: " + e.getMessage();
        }
        
        dbInfo.put("databaseProduct", dbProduct);
        dbInfo.put("databaseUrl", dbUrl);
        dbInfo.put("databasePlatform", environment.getProperty("spring.jpa.database-platform"));
        
        model.addAttribute("dbInfo", dbInfo);
        return "db-info";
    }
} 