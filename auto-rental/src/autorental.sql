CREATE TABLE orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        order_date DATE,
                        amount DECIMAL(10, 2)
);
CREATE TABLE order_details (
                               odid INT  AUTO_INCREMENT PRIMARY KEY,
                               oid INT,
                               vid INT,
                               qty INT,
                               price DECIMAL(10, 2),
                               FOREIGN KEY (oid) REFERENCES orders(id),
                               FOREIGN KEY (vid) REFERENCES vehicle(id)
);