-- Database Schema for the application
-- This will create the 'item' table if it doesn't exist

CREATE TABLE IF NOT EXISTS item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
); 