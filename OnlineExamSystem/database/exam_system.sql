-- Create Database
CREATE DATABASE IF NOT EXISTS exam_system;
USE exam_system;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20) NOT NULL DEFAULT 'student',
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Exams Table
CREATE TABLE IF NOT EXISTS exams (
    exam_id INT PRIMARY KEY AUTO_INCREMENT,
    exam_name VARCHAR(200) NOT NULL,
    description TEXT,
    duration_minutes INT DEFAULT 60,
    total_questions INT,
    passing_percentage INT DEFAULT 50,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Questions Table
CREATE TABLE IF NOT EXISTS questions (
    question_id INT PRIMARY KEY AUTO_INCREMENT,
    exam_id INT NOT NULL,
    question_text TEXT NOT NULL,
    option_a VARCHAR(200) NOT NULL,
    option_b VARCHAR(200) NOT NULL,
    option_c VARCHAR(200) NOT NULL,
    option_d VARCHAR(200) NOT NULL,
    correct_answer CHAR(1) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (exam_id) REFERENCES exams(exam_id)
);

-- Results Table
CREATE TABLE IF NOT EXISTS results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    exam_id INT NOT NULL,
    total_questions INT,
    correct_answers INT,
    percentage DOUBLE,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (exam_id) REFERENCES exams(exam_id)
);

-- Insert Sample Users
INSERT INTO users (username, password, email, role, active) VALUES
('student1', 'password123', 'student1@example.com', 'student', TRUE),
('student2', 'password123', 'student2@example.com', 'student', TRUE),
('admin1', 'admin123', 'admin1@example.com', 'admin', TRUE),
('admin2', 'admin123', 'admin2@example.com', 'admin', TRUE);

-- Insert Sample Exams
INSERT INTO exams (exam_name, description, duration_minutes, total_questions, passing_percentage) VALUES
('Java Programming Exam', 'Test your Java programming knowledge', 60, 50, 50),
('Web Development Exam', 'Test your web development skills', 45, 40, 50),
('Database Management Exam', 'Test your database management knowledge', 50, 45, 50);

-- Insert Sample Questions for Exam 1 (Java Programming)
INSERT INTO questions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES
(1, 'What is the correct way to declare a variable in Java?', 'var x = 5;', 'int x = 5;', 'Integer x = 5;', 'let x = 5;', 'B'),
(1, 'Which of the following is NOT a primitive data type in Java?', 'int', 'String', 'double', 'boolean', 'B'),
(1, 'What is the output of System.out.println(5 + 5 + "5");?', '"1010"', '"55"', '"510"', '"105"', 'C'),
(1, 'Which keyword is used to create a constant in Java?', 'const', 'final', 'static', 'readonly', 'B'),
(1, 'What is the size of int in Java?', '2 bytes', '4 bytes', '8 bytes', '16 bytes', 'B');

-- Insert Sample Questions for Exam 2 (Web Development)
INSERT INTO questions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES
(2, 'Which HTML tag is used to define the main heading?', '<header>', '<h1>', '<heading>', '<head>', 'B'),
(2, 'What does CSS stand for?', 'Cascading Style Sheets', 'Computer Style Sheets', 'Creative Style Sheets', 'Colorful Style Sheets', 'A'),
(2, 'Which JavaScript method is used to select an HTML element by id?', 'getElementById()', 'selectId()', 'findElement()', 'getElement()', 'A'),
(2, 'What is the correct syntax for linking an external CSS file?', '<link href="style.css">', '<css href="style.css">', '<link rel="stylesheet" href="style.css">', '<style src="style.css">', 'C'),
(2, 'Which tag is used to specify metadata about an HTML document?', '<head>', '<meta>', '<metadata>', '<info>', 'B');

-- Insert Sample Questions for Exam 3 (Database Management)
INSERT INTO questions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES
(3, 'What does SQL stand for?', 'Structured Query Language', 'System Query Language', 'Simple Query Language', 'Standard Query Language', 'A'),
(3, 'Which SQL command is used to retrieve data?', 'RETRIEVE', 'GET', 'SELECT', 'FETCH', 'C'),
(3, 'What does PRIMARY KEY do?', 'Ensures data is indexed', 'Ensures uniqueness and non-null', 'Makes data read-only', 'Encrypts the data', 'B'),
(3, 'Which keyword is used to sort data in SQL?', 'SORT', 'ORDER BY', 'ARRANGE', 'RANK', 'B'),
(3, 'What is a foreign key?', 'A key that is unique in a table', 'A key that references primary key of another table', 'A key used for encryption', 'A key that is always numeric', 'B');

-- Create Index for better performance
CREATE INDEX idx_user_exam ON results(user_id, exam_id);
CREATE INDEX idx_exam_questions ON questions(exam_id);

-- Display the database structure
SELECT 'Database setup completed successfully!' as Status;
