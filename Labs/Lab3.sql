/*
customer_id is a surrogate key that just gets sequentially assigned. We don't
care what the values are, just as long as they never change, and they are unique.
Which means that a DBMS constructed sequence is just about the perfect solution.
The syntax for the customer_id column is peculiar to Derby.To do the same thing in 
MySQL, the syntax would be: "customer_id int not null auto_increment,"Note that 
we still have to declare ths to be the primary key. Since it's the only attribute 
in the primary key, I can declare the primary key constraint "inline" rather than 
out of line. This is a handy shortcut that you will want to use if you have only 
one attribute (usually as surrogate) in the PK. Note also that we create the customers_uk01
uniqueness constraint to implement the candidate key over (cFirstname, cLastname, and cPhone).
*/
create table customer (
	customer_id integer not null generated always as 
		identity (start with 1, increment by 1)
		constraint customers_pk primary key,
	cFirstname      varchar(20) not null,
	cLastname       varchar(20) not null,
	cPhone          varchar(20) not null,
	cstreet         varchar(50),
	czipcode        varchar(5),
	constraint      customers_uk01 unique(cFirstname, cLastname, cPhone));

-- Order is a keyword in Derby.  Remember that the order by clause in the 
-- select statement allows us to change the order in which the rows come out.
-- I could have just changed the table name to orderS, but that would have 
-- been too easy.  Note that the Order table name is now case sensitive.
create table "Order" (
	customer_id     integer     not null,
	orderdate       date,
	soldby          varchar(20),
		constraint      orders_customers_fk01 foreign key
			(customer_id) references customer(customer_id),
		constraint      orders_pk primary key (customer_id, orderdate));

create table product (
	upc             integer     not null primary key,
	prodName        varchar(40) not null,
	mfgr            varchar(40) not null,
	model           varchar(20) not null,
	unitListPrice	double      not null,
	unitsInStock    integer     not null);

-- I forgot to create a candidate key for prodName, mfgr and model, so I’m 
-- adding it in after creating the table.  Not my preferred approach.
alter table product add constraint product_uk01 unique (prodName, mfgr, model);

/*
Note that I elected to use the full name UniversalProductCode for the UPC 
in the OrderLine table.  This now means that I've role named the UPC column
in the Product table to be UniversalProductCode in the OrderLine table.
That means that we're not going to be able to get away with a natural join
from OrderLine to Product. Note also that OrderLine has not one, but two 
foreign key constraints: one to Order, and the other up to Product.
*/
create table OrderLine (
	customer_id             integer     not null,
	orderdate               date        not null,
	UniversalProductCode    integer     not null,
	quantity                integer     not null,
	unitSalePrice           double,
	constraint 		orderline_pk primary key(customer_id, orderdate, UniversalProductCode),
	constraint 		OrderLine_Order_fk01 foreign key (customer_id, orderdate)
		references 		"Order" (customer_id, orderdate),
	constraint OrderLine_product foreign key (UniversalProductCode)
		references product (upc));

-- Note that we have to start at the "top" of the model with the inserts,
-- otherwise the referrential integrity constraints will fail.
-- Note also that I'm using the syntax for inserting multiple rows with one
-- insert statement.  That saves you having to put int the list of attributes
-- over and over again with each set of values.
insert into customer (cFi
rstname, cLastname, cPhone, cstreet, czipcode) values 
('Dave',    'Brown',    '562-982-8696', '123 Lakewood Blvd. Long Beach',    '90080'),
('Rachel',  'Burris',   '213-543-2211', '54 218th St. Torrance',            '90210'),
('Tom',     'Jewett',   '714-555-1212', '10200 Slater',                     '92708'),
('Alvero',  'Monge',    '562-111-1234', '314159 Pi St. Long Beach',         '90814');

insert into product (UPC, prodName, mfgr, model, unitListPrice, unitsInStock) values
(135798642, 'Framing hammer, 20oz.',	'Stanley',		'Frame01',		18.95,20),
(123456789, 'Framer''s level 9 ft.',	'Stanley',		'Frame09',		28.57,10),
(777999111, 'Blade scredriver #2',		'Proto',		'Blad02',		8.53,15),
(123123123, 'Cold Chisel 1"',			'Challenger',	'One inch',		12.04,30),
(321321321, 'Jackhammer, electric',		'Bosche',		'Sml Elec',		128.95,5),
(111222333, 'Arc Welder',				'Lincoln',		'Industrial',	5298.65,1);

insert into "Order" (customer_ID, orderDate, soldBy) values 
(1,     '2015-12-24',   'Patrick'),
(1,     '2015-11-25',   'Sally Forth'),
(2,     '2016-05-05',   'Mack'),
(3,     '2012-05-05',   'Phillip'),
(3,     '2014-04-04',   'Patrick');

/*
The unitsaleprice is an override to the unitListPrice in Product.  If the 
customer got the list price in this transaction, then the just set the 
unitSaleProce to null and the assumption is that they got the unitListPrice.
This starts to unravel when prices change and you only have the most current 
list price and you want to go back a few years to figure out how much was 
paid for an order based on the then-current prices.
*/
insert into orderLine (customer_id, 
orderdate, universalproductcode, 
quantity, unitsaleprice) values
(1,     '2015-12-24',   135798642, 3, NULL),
(1,     '2015-12-24',   123456789, 1, NULL),
(1,     '2015-11-25',   777999111, 3, NULL),
(2,     '2016-05-05',   321321321, 2, 120.00),
(3,     '2012-05-05',   123123123, 1, NULL),
(3,     '2012-05-05',   777999111, 3, NULL),
(3,     '2012-05-05',   123456789, 2, NULL),
(3,     '2014-04-04',   135798642, 1, NULL),
(3,     '2014-04-04',   123123123, 2, NULL);