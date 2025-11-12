CREATE TABLE Contents (
    id SERIAL PRIMARY KEY,
    name varchar(64) UNIQUE NOT NULL,
    description text,
    used_symbols text UNIQUE NOT NULL
);

CREATE TABLE Algorithms (
    id SERIAL PRIMARY KEY,
    name varchar(16) UNIQUE NOT NULL,
    key_size int NOT NULL,
    mode varchar(16) NOT NULL
);

CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    username varchar(255) UNIQUE NOT NULL,
    email varchar(255) UNIQUE NOT NULL,
    role varchar(32) NOT NULL DEFAULT 'USER',
    password_hash varchar(255) NOT NULL
);

CREATE TABLE Tags (
    id SERIAL PRIMARY KEY,
    name varchar(64) UNIQUE NOT NULL,
    description text,
    color_hex varchar(7) NOT NULL DEFAULT '#FFFFFF'
);

CREATE TABLE Passwords (
    id SERIAL PRIMARY KEY,
    user_id int NOT NULL,
    encrypted_password bytea NOT NULL,
    content_id int NOT NULL,
    algorithm_id int NOT NULL,
    salt varchar(255) NOT NULL,
    length int NOT NULL,
    updated_at timestamp NOT NULL
);

CREATE TABLE UserTags (
    user_id int NOT NULL,
    tag_id int NOT NULL,
  PRIMARY KEY (user_id, tag_id)
);

CREATE INDEX ON Passwords (user_id);

ALTER TABLE Passwords ADD FOREIGN KEY (user_id) REFERENCES Users (id);

ALTER TABLE Passwords ADD FOREIGN KEY (content_id) REFERENCES Contents (id);

ALTER TABLE Passwords ADD FOREIGN KEY (algorithm_id) REFERENCES Algorithms (id);

ALTER TABLE UserTags ADD FOREIGN KEY (user_id) REFERENCES Users (id);

ALTER TABLE UserTags ADD FOREIGN KEY (tag_id) REFERENCES Tags (id);


-- Test data
-- Insert values

insert into Contents (name, description, used_symbols) values
('Letters', 'Latin letters only: A-Z, a-z', 'abcdefghijklmnopqrstuvwxyzABCDEFGIHJKLMNOPQRSTUVWXYZ'),
('Digits', 'Digits only: 0-9', '0123456789'),
('Special Chars', 'Special charachters only', '!@#$%^&*()_-+={[]};:''",<.>/?\|~`'),
('Letters, Digits', 'Letin letters and digits', 'abcdefghijklmnopqrstuvwxyzABCDEFGIHJKLMNOPQRSTUVWXYZ0123456789'),
('Letters, Special Chars', 'Latin letters and special characters', 'abcdefghijklmnopqrstuvwxyzABCDEFGIHJKLMNOPQRSTUVWXYZ!@#$%^&*()_-+={[]};:''",<.>/?\|~`'),
('Digits, Special Chars', 'Digits and special characters', '0123456789!@#$%^&*()_-+={[]};:''",<.>/?\|~`'),
('Mix', 'Latin letters, digits and special characters', 'abcdefghijklmnopqrstuvwxyzABCDEFGIHJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-+={[]};:''",<.>/?\|~`');

insert into Algorithms (name, key_size, mode) values
('AES', 128, 'ECB'),
('DES', 56, 'ECB'),
('DESede', 168, 'ECB');

insert into Tags (name, description, color_hex) values
('Study', 'Study services passwords', '#FF0000'),
('Social', 'Social media passwords', '#00FF00'),
('Games', 'Game services passwords', '#0000FF');

insert into Users (username, email, role, password_hash) values
('alex', 'alex@mail.com', 'ADMIN', 'alex_hash'),
('white_cat', 'white@cat.com', 'USER', 'white_cat_hash'),
('water_fox', 'water@fox.com', 'USER', 'water_fox_hash'),
('admin', 'admin@admin.ru', 'ADMIN', '$2a$10$nPyt29KwY4JarZvISsQZsudVx8Lp96Xny0V0648QtGtj0y18z3jP2');

insert into Passwords (user_id, encrypted_password, content_id, algorithm_id, salt, length, updated_at) values
(1, convert_to('123456789012', 'UTF8'), 1, 1, 'salt0', 12, now()),
(1, convert_to('qwertyuiop111', 'UTF8'), 4, 2, 'salt1', 13, now()),
(2, convert_to('1234567890123@', 'UTF8'), 7, 3, 'salt2', 14, now()),
(3, convert_to('@43210987654321', 'UTF8'), 7, 1, 'salt3', 15, now());

-- TODO: add test data for UserTags table, add PasswordTags table