package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;
    
    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("activeProfile", activeProfile);
        model.addAttribute("items", itemRepository.findAll());
        // Get list of tables
        List<String> tables = jdbcTemplate.queryForList(getTablesQuery(), String.class);
        model.addAttribute("tables", tables);
        return "admin/index";
    }
    
    @GetMapping("/table/{tableName}")
    public String viewTable(@PathVariable String tableName, Model model) {
        // Validate table name to prevent SQL injection
        if (!isValidTableName(tableName)) {
            return "redirect:/admin";
        }
        
        List<String> columns = jdbcTemplate.queryForList(
                getColumnsQuery(tableName), String.class);
        
        // Get table data
        String sql = "SELECT * FROM " + tableName;
        List<Map<String, Object>> tableData = jdbcTemplate.queryForList(sql);
        
        model.addAttribute("tableName", tableName);
        model.addAttribute("columns", columns);
        model.addAttribute("tableData", tableData);
        return "admin/table";
    }
    
    @GetMapping("/query")
    public String executeQuery(@RequestParam(required = false) String sql, Model model) {
        model.addAttribute("sql", sql);
        
        if (sql != null && !sql.trim().isEmpty()) {
            try {
                // For safety, limit to SELECT queries only
                if (sql.trim().toLowerCase().startsWith("select")) {
                    Query query = entityManager.createNativeQuery(sql);
                    List<?> result = query.getResultList();
                    model.addAttribute("queryResult", result);
                    model.addAttribute("success", true);
                } else {
                    model.addAttribute("error", "Only SELECT queries are allowed for security reasons");
                }
            } catch (Exception e) {
                model.addAttribute("error", "Error executing query: " + e.getMessage());
            }
        }
        
        return "admin/query";
    }
    
    private String getTablesQuery() {
        if ("prod".equals(activeProfile)) {
            // MySQL query to get tables
            return "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = database()";
        } else {
            // H2 query to get tables
            return "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";
        }
    }
    
    private String getColumnsQuery(String tableName) {
        if ("prod".equals(activeProfile)) {
            // MySQL query to get columns
            return "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = database() AND TABLE_NAME = '" + tableName + "'";
        } else {
            // H2 query to get columns
            return "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_NAME = '" + tableName + "'";
        }
    }
    
    private boolean isValidTableName(String tableName) {
        // Simple validation to prevent SQL injection
        return tableName.matches("[a-zA-Z0-9_]+");
    }
} 