CREATE DATABASE taskova;
USE taskova;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category ENUM('Work', 'Personal', 'Other'),
    due_date DATE,
    priority ENUM('Important and urgent', 'Important but not urgent', 'Urgent but not important', 'Not urgent not important'),
    is_done ENUM('in progress', 'done') DEFAULT 'in progress',
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    bio TEXT(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE tasks ADD COLUMN user_id INT;
ALTER TABLE tasks ADD FOREIGN KEY (user_id) REFERENCES users(user_id);