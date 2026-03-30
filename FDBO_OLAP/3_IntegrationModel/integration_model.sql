--nivel consolidare date
CREATE OR REPLACE VIEW v_consolidated_sales AS
SELECT
    o.order_id,
    o.order_date,
    o.total_amount,
    o.payment_method,
    o.shipping_country,
    c.name          AS customer_name,
    c.country       AS customer_country,
    c.gender        AS customer_gender,
    oi.product_id,
    oi.quantity,
    oi.unit_price,
    p.product_name,
    p.category,
    p.brand
FROM orders o
JOIN order_items          oi ON o.order_id    = oi.order_id
LEFT JOIN v_customers_pg   c ON o.customer_id = c.customer_id
LEFT JOIN v_products_mongo p ON oi.product_id = p.product_id;

--Schema analitica ROLAP- model dimensional complet
--fact table
CREATE OR REPLACE VIEW fact_sales AS
SELECT
    o.order_id,
    o.customer_id,
    oi.product_id,
    o.order_date,
    oi.quantity,
    oi.unit_price,
    oi.quantity * oi.unit_price  AS revenue,
    o.payment_method,
    o.shipping_country
FROM orders o
JOIN order_items oi ON o.order_id = oi.order_id;

--tabele dimensionale OPAL-- dimensiune timp
CREATE OR REPLACE VIEW dim_time AS
SELECT DISTINCT
    order_date                          AS date_key,
    EXTRACT(YEAR  FROM order_date)      AS year,
    EXTRACT(MONTH FROM order_date)      AS month,
    EXTRACT(DAY   FROM order_date)      AS day,
    TO_CHAR(order_date,'Q')             AS quarter
FROM orders;

--dimensiune product
CREATE OR REPLACE VIEW dim_product AS
SELECT
    product_id,
    product_name,
    category,
    brand,
    price
FROM v_products_mongo;

--dimensiune client
CREATE OR REPLACE VIEW dim_customer AS
SELECT
    customer_id,
    name,
    country,
    gender
FROM v_customers_pg;

--functii analitice
--ROLLUP pentru agregarea ierarhica a vanzarilor in timp
CREATE OR REPLACE VIEW v_sales_rollup_time AS
SELECT
    EXTRACT(YEAR FROM order_date)  AS an,
    EXTRACT(MONTH FROM order_date) AS luna,
    SUM(revenue)                   AS total_vanzari,
    COUNT(DISTINCT order_id)       AS nr_comenzi
FROM fact_sales
GROUP BY ROLLUP(EXTRACT(YEAR FROM order_date), EXTRACT(MONTH FROM order_date));

--CUBE pentru agregarea vânzărilor pe metoda de plată și țara de livrare

CREATE OR REPLACE VIEW v_sales_cube AS
SELECT
    DECODE(GROUPING(payment_method), 1, 'ALL_METHODS', payment_method)   AS payment_method,
    DECODE(GROUPING(shipping_country), 1, 'ALL_COUNTRIES', shipping_country) AS shipping_country,
    GROUPING_ID(payment_method, shipping_country) AS grouping_level,
    SUM(revenue) AS total_revenue,
    COUNT(DISTINCT order_id) AS nr_orders
FROM fact_sales
GROUP BY CUBE(payment_method, shipping_country);

--Functii analitice: rank vanzari per comanda

SELECT
    order_id,
    SUM(revenue) AS total_comanda,
    RANK() OVER (ORDER BY SUM(revenue) DESC) AS rank_comanda
FROM fact_sales
GROUP BY order_id
ORDER BY rank_comanda
FETCH FIRST 10 ROWS ONLY;










