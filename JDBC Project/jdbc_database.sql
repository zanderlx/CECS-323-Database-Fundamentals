-- START TABLE DELETION HERE --
-- This is in case we need to reset our database

drop table books;
drop table writingGroup;
drop table publishers;

-- END TABLE DELETTION HERE --

-- START TABLE CREATIONS HERE --

-- Create Writing Group Table
create table writingGroup (
	groupName	varchar(30) not null,
	headWriter	varchar(20),
	yearFormed	int,
	subject		varchar(20),

	constraint writing_group_pk primary key (groupName)
	);

-- Create Publishers Table
create table publishers (
	publisherName		varchar(30) not null,
	publisherAddress	varchar(50),
	publisherPhone		varchar(20),
	publisherEmail		varchar(50),

	constraint publishers_pk primary key (publisherName)
	);
  
-- Create Books Table
create table books (
	groupName 	varchar(30) not null,
	bookTitle 	varchar(40) not null,
	publisherName   varchar(30) not null,
	yearPublished	int,
	numberPages	int,

	constraint books_pk primary key (groupName, bookTitle),
	constraint writingGroup_books_fk foreign key (groupName)
		references writingGroup (groupName),
	constraint publishers_books_fk foreign key (publisherName)
		references publishers (publisherName)
	);

-- END TABLE CREATIONS HERE --

-- START DATA INSERTS HERE --
  
-- Insert Writing Groups
insert into writingGroup (groupName, headwriter, yearFormed, subject)
	values ('The Inklings', 'J.R.R. Tolkien', 1930, 'Fantasy');
insert into writingGroup (groupName, headwriter, yearFormed, subject)
	values ('Bloomsburg Group', 'Lexzander Saplan', 1907, 'Literature');
insert into writingGroup (groupName, headwriter, yearFormed, subject)
	values ('The Factory', 'Andy Warhol', 1962, 'Sexual Radicals');
insert into writingGroup (groupName, headwriter, yearFormed, subject)
	values ('The Algonquin Roundtable', 'King Arthur', 1919, 'Literature');
insert into writingGroup (groupName, headwriter, yearFormed, subject)
	values ('The Dymock Poets', 'Alphonse Elric', 1930, 'Poetry');
insert into writingGroup (groupName, headwriter, yearFormed, subject)
	values ('Stratford on Odeon', 'Jose Ramirez', 1920, 'Literature');

-- Insert Publishers
insert into publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)
	values('Allen & Unwin', '83 Alexander ST Crows Nest, New South Wales', '612-8425-0100', 'AllenUnwin@yahoo.com');
insert into publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)
	values ('HarperCollins', '195 Broadway New York, NY, 10007', '1-800-242-7737', 'feedback2@harpercollins.com');
insert into publishers (publisherName, publisherAddress, publisherPhone)
	values ('Edward Arnold', '338 Euston Road London, NW1 3BH United Kingdom', '44-17-1873-6336');
insert into publishers (publisherName, publisherAddress, publisherPhone)
	values ('Scholastic', '557 Broadway, New York City, NY, 10012', '1-800-724-6527');
insert into publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)
	values ('Bloomsbury', '1385 Broadway, 5th Floor, New York City, NY, 10018', '212-419-5300', 'academicreviewus@bloomsbury.com');
insert into publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)
	values ('Penguin Random House', 'New York City, NY', '213-520-6411', 'academicfeedback@randomhouse.com');

  
-- Insert Books
insert into books (groupName, bookTitle, publisherName, yearPublished, numberPages)
	values ('The Inklings', 'The Lord of the Rings', 'Allen & Unwin', 1954, 1178);
insert into books (groupName, bookTitle, publisherName, yearPublished, numberPages)
	values ('The Inklings', 'The Chronicles of Narnia', 'HarperCollins', 1950, 208);
insert into books (groupName, bookTitle, publisherName, yearPublished, numberPages)
	values ('Bloomsburg Group', 'A Room with a View', 'Edward Arnold', 1908, 321);
insert into books (groupName, bookTitle, publisherName, yearPublished, numberPages)
	values ('Stratford on Odeon', 'The Adventures of Captain Underpants', 'Scholastic', 1997, 125);
insert into books (groupName, bookTitle, publisherName, yearPublished, numberPages)
	values ('The Algonquin Roundtable', 'Harry Potter and the Philosophers Stone', 'Bloomsbury', 1997, 223);
insert into books (groupName, bookTitle, publisherName, yearPublished, numberPages)
	values ('The Dymock Poets', 'I Know Why the Caged Bird Sings', 'Penguin Random House',1969, 304);