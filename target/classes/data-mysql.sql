-- MySQL sample data

-- Only insert if the items don't already exist (to avoid duplicate data on restart)
INSERT INTO item (name, description) 
SELECT 'Item 1', 'Description for Item 1' FROM dual 
WHERE NOT EXISTS (SELECT 1 FROM item WHERE name = 'Item 1');

INSERT INTO item (name, description) 
SELECT 'Item 2', 'Description for Item 2' FROM dual 
WHERE NOT EXISTS (SELECT 1 FROM item WHERE name = 'Item 2');

INSERT INTO item (name, description) 
SELECT 'Item 3', 'Description for Item 3' FROM dual 
WHERE NOT EXISTS (SELECT 1 FROM item WHERE name = 'Item 3'); 