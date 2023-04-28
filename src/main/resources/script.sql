DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
                          id BIGINT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          cart_id BIGINT,
                          creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cart (
                      id BIGINT PRIMARY KEY,
                      creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product (
                         id BIGINT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price DECIMAL(10,2) NOT NULL,
                         creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cart_item (
                           id BIGINT PRIMARY KEY,
                           quantity INT NOT NULL,
                           product_id BIGINT,
                           cart_id BIGINT,
                           creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id) REFERENCES product(id),
                           CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart(id)
);

ALTER TABLE customer ADD CONSTRAINT fk_customer_cart FOREIGN KEY (cart_id) REFERENCES cart(id);
