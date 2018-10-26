CREATE TABLE customers (
	cfirstname    VARCHAR(20) NOT NULL,
	clastname     VARCHAR(20) NOT NULL,
	cphone        VARCHAR(20) NOT NULL,
	cstreet       VARCHAR(50),
	czipcode      VARCHAR(5),
	constraint customers_pk
	PrImArY KeY (cfirstname, clastname, cphone));

INSERT INTO customers (cfirstname, clastname, cphone, cstreet, czipcode)
		VALUES ('Tom', 'Jewett', '714-555-1212', '10200 Slater', '92708');

CREATE TABLE orders (
	cfirstname  VARCHAR(20) NOT NULL,
	clastname   VARCHAR(20) NOT NULL,
	cphone      VARCHAR(20) NOT NULL,
	orderdate   DATE NOT NULL,
	soldby      VARCHAR(20),
	CONSTRAINT  orders_pk
		PRIMARY KEY (cfirstname, clastname, cphone, orderdate),
	CONSTRAINT  orders_customers_fk
		FOREIGN KEY (cfirstname, clastname, cphone)
		REFERENCES customers (cfirstname, clastname, cphone));

INSERT INTO orders (cfirstname, clastname, cphone, orderdate, soldby)
	VALUES ('Tom', 'Jewett', '714-555-1212', '2005-09-11', 'Patrick');

SELECT * FROM customers INNER JOIN orders ON
	customers.cfirstname = orders.cfirstname AND
	customers.clastname = orders.clastname AND
	customers.cphone = orders.cphone;