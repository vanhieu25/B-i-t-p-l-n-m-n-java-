-- seed.sql
INSERT INTO Users (username, password, role) VALUES
('admin1', 'hashed_password1', 'ADMIN'),
('admin2', 'hashed_password2', 'ADMIN'),
('admin3', 'hashed_password3', 'ADMIN');
INSERT INTO Products (name, type, price, launchDate, category) VALUES
('Game 1', 'Game', 59.99, '2023-01-01', 'Action'),
('Console 1', 'Console', 299.99, '2022-11-15', 'Gaming'),
('Accessory 1', 'Accessory', 19.99, '2023-02-20', 'Peripheral');