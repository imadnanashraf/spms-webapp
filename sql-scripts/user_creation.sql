CREATE USER 'studenttracker'@'localhost' IDENTIFIED WITH mysql_native_password BY 'studenttrackerpassword';
GRANT ALL PRIVILEGES ON *.* TO 'studenttracker'@'localhost';