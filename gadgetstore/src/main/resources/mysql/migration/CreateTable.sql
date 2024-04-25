CREATE DATABASE electronic_store;
USE electronic_store;
CREATE TABLE user (
    user_id int PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    password VARCHAR(500),
    gender VARCHAR(10),
    about TEXT,
    email VARCHAR(255) UNIQUE,
    image_name varchar(255) DEFAULT NULL
);

CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    cover_image VARCHAR(255)
);

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price INT NOT NULL,
    quantity INT NOT NULL,
    added_date DATE,
    live BOOLEAN NOT NULL,
    stock BOOLEAN NOT NULL,
    category_id INT,
    product_image varchar(50) DEFAULT NULL,
    CONSTRAINT fk_category_id
        FOREIGN KEY (category_id)
        REFERENCES category(category_id) on delete cascade
);


CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    created_at DATE,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

CREATE TABLE cart_item (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    quantity INT,
    product_price INT,
    cart_id INT,
    FOREIGN KEY (product_id) REFERENCES product(id) on delete cascade,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id) on delete cascade
);


