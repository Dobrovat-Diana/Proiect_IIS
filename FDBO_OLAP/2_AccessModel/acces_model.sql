--sales src
	CREATE TABLE orders (
    order_id NUMBER PRIMARY KEY,
    customer_id NUMBER NOT NULL,
    order_date DATE NOT NULL,
    total_amount NUMBER(12,2),
    payment_method VARCHAR2(50),
    shipping_country VARCHAR2(100)
);

	CREATE TABLE order_items (
    order_item_id NUMBER PRIMARY KEY,
    order_id NUMBER NOT NULL,
    product_id NUMBER NOT NULL,
    quantity NUMBER NOT NULL,
    unit_price NUMBER(12,2) NOT NULL,
    CONSTRAINT fk_order_items_orders
        FOREIGN KEY (order_id) REFERENCES orders(order_id)
);
	SELECT COUNT(*) FROM orders;

--create DB LINK
CREATE DATABASE LINK fdbo_link
CONNECT TO fdbo IDENTIFIED BY fdbo123
USING '//localhost:1521/XEPDB1';

--testare acces

SELECT * FROM vw_orders_oracle@fdbo_link WHERE ROWNUM <= 5;
SELECT * FROM vw_order_items_oracle@fdbo_link WHERE ROWNUM <= 5;


--creare view local in sales_src

CREATE OR REPLACE VIEW v_orders_fdbo AS
SELECT * FROM vw_orders_oracle@fdbo_link;

CREATE OR REPLACE VIEW v_order_items_fdbo AS
SELECT * FROM vw_order_items_oracle@fdbo_link;


--verificare functionalitate db link
SELECT * FROM v_orders_fdbo WHERE ROWNUM <= 5;
SELECT * FROM v_order_items_fdbo WHERE ROWNUM <= 5;

--interogare demonstrativa oracle-oracle prin DB link

SELECT
    o.order_id,
    o.customer_id,
    oi.product_id,
    oi.quantity,
    oi.unit_price
FROM v_orders_fdbo o
JOIN v_order_items_fdbo oi
  ON o.order_id = oi.order_id
WHERE ROWNUM <= 10;

--acces oracle la postgrest prin postgREST
CREATE OR REPLACE VIEW v_customers_pg AS
SELECT *
FROM JSON_TABLE(
  UTL_HTTP.REQUEST('http://host.docker.internal:3000/customers?limit=10000'),
  '$[*]' COLUMNS (
    customer_id  NUMBER        PATH '$.customer_id',
    name         VARCHAR2(200) PATH '$.name',
    email        VARCHAR2(200) PATH '$.email',
    gender       VARCHAR2(20)  PATH '$.gender',
    signup_date  VARCHAR2(30)  PATH '$.signup_date',
    country      VARCHAR2(100) PATH '$.country'
  )
);


--validare flux de date prin API clienti

SELECT * FROM v_customers_pg WHERE ROWNUM <= 5;

--Crearea unei vederi virtuale pentru recenziile produselor stocate extern
CREATE OR REPLACE VIEW v_reviews_pg AS
SELECT *
FROM JSON_TABLE(
  UTL_HTTP.REQUEST('http://host.docker.internal:3000/product_reviews?limit=10000'),
  '$[*]' COLUMNS (
    review_id    NUMBER         PATH '$.review_id',
    product_id   NUMBER         PATH '$.product_id',
    customer_id  NUMBER         PATH '$.customer_id',
    rating       NUMBER         PATH '$.rating',
    review_text  VARCHAR2(4000) PATH '$.review_text',
    review_date  VARCHAR2(30)   PATH '$.review_date'
  )
);


--Verificarea formatului datelor din API-ul de recenzii
SELECT * FROM v_reviews_pg WHERE ROWNUM <= 5;

--Integrarea catalogului de produse dintr-o bază de date NoSQL (MongoDB)
CREATE OR REPLACE VIEW v_products_mongo AS
SELECT *
FROM JSON_TABLE(
  UTL_HTTP.REQUEST('http://host.docker.internal:8081/products?pagesize=1000'),
  '$._embedded[*]' COLUMNS (
    product_id     NUMBER        PATH '$.product_id',
    product_name   VARCHAR2(500) PATH '$.product_name',
    category       VARCHAR2(200) PATH '$.category',
    price          NUMBER        PATH '$.price',
    stock_quantity NUMBER        PATH '$.stock_quantity',
    brand          VARCHAR2(200) PATH '$.brand'
  )
);


--Inspectarea mapării datelor NoSQL în coloane relaționale
SELECT * FROM v_products_mongo WHERE ROWNUM <= 5;



