CREATE TABLE BOOK (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(55) NOT NULL,
    author VARCHAR(155) NOT NULL,
    isbn VARCHAR(30) NOT NULL,
    description VARCHAR(155) NOT NULL,
    price FLOAT NOT NULL,
    quantity_in_stock INTEGER NOT NULL,
    CONSTRAINT id UNIQUE (id)
);

INSERT INTO book (id, title, author, isbn, description, price, quantity_in_stock) VALUES (1,'Clean Code','Robert C. Martin','978-0132350884','description',10.99,100);
INSERT INTO book (id, title, author, isbn, description, price, quantity_in_stock) VALUES (2,'Domain-Driven Design','Erick Evans','978-0321125217','description',30.75,50);