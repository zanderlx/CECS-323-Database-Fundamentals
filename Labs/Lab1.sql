CREATE TABLE state (
	stateName 		VARCHAR(20) NOT NULL, 
	stateCode		VARCHAR(2)  NOT NULL,
	dateJoinedUS    DATE        NOT NULL,
	governor		VARCHAR(20) NOT NULL,
	CONSTRAINT state_pk
		PRIMARY KEY (stateName, stateCode));

INSERT INTO state (stateName, stateCode, dateJoinedUS, governor)
	VALUES('California', 'CA', '1850-09-09', 'Jerry Brown'); 

CREATE TABLE city (
	stateName		VARCHAR(20) NOT NULL,
	stateCode		VARCHAR(2)  NOT NULL,
	cityName		VARCHAR(20) NOT NULL,
	population		VARCHAR(10) NOT NULL,
	lastCensusDate  DATE        NOT NULL,
	mayor			VARCHAR(20) NOT NULL,
	CONSTRAINT city_pk
		PRIMARY KEY (stateName, stateCode, cityName),
	CONSTRAINT city_county_fk
		FOREIGN KEY (countyName)
		REFERENCES county (countyName));

INSERT INTO city (stateName, stateCode, cityName, population, lastCensusDate, mayor)
	VALUES('California', 'CA', 'Los Angeles', '3,976,000', '2017-07-01', 'Eric Garcetti');

-- Pennsylvania
INSERT INTO state (stateName, stateCode, dateJoinedUS, governor)
	VALUES('Pennsylvania', 'PA', '1787-12-12', 'Tom Wolf');

INSERT INTO city (stateName, stateCode, cityName, population, lastCensusDate, mayor)
	VALUES('Pennsylvania', 'PA', 'Scranton', '77,291', '2017-07-01', 'William Courtright');

-- Nevada
INSERT INTO state (stateName, stateCode, dateJoinedUS, governor)
	VALUES('Nevada', 'NV', '1864-10-31', 'Brian Sandoval');

INSERT INTO city (stateName, stateCode, cityName, population, lastCensusDate, mayor)
	VALUES('Nevada', 'NV', 'Las Vegas', '632,912', '2017-07-01', 'Carolyn Goodman');

-- Step 2A
-- INSERT INTO city (stateName, stateCode, cityName, population, lastCensusDate, mayor)
--	VALUES('New York', 'NY', 'New York City', '8,538,000', '2017-07-01', 'Bill de Blasio');
-- Above causes -> INSERT on table 'CITY' caused a violation of foreign key constraint 'CITY_STATE_FK' for key (New York,NY).  The statement has been rolled back.

-- Step 2B
-- INSERT INTO city (stateName, stateCode, cityName, population, lastCensusDate, mayor)
--	VALUES('Connecticut', 'CT', 'Long Beach', '8,538,000', '2017-07-01', 'Bill de Blasio');
-- Above causes -> INSERT on table 'CITY' caused a violation of foreign key constraint 'CITY_STATE_FK' for key (Connecticut,CT).  The statement has been rolled back.

-- Step 3A
-- Done

-- Step 3B
-- Done

-- Step 4
SELECT * FROM state NATURAL JOIN city;

-- Step 5
-- Did not do...

--------------------------------------------------------------------------------------------------

-- Updated database with county and constraints
CREATE TABLE county (
	countyName	VARCHAR(20) NOT NULL,
	population	VARCHAR(10) NOT NULL,
	countySeat	VARCHAR(10) NOT NULL,
	CONSTRAINT county_pk
		PRIMARY KEY (countyName),
	CONSTRAINT county_state_fk
		FOREIGN KEY (stateName, stateCode)
		REFERENCES state (stateName, stateCode));

INSERT INTO county (countyName, population, countySeat)
	VALUES('Orange County', '3,017l,000', 'Santa Ana');

--------------------------------------------------------------------------------------------------

CREATE TABLE state (
	stateName 	VARCHAR(20) NOT NULL, 
	stateCode	VARCHAR(2)  NOT NULL,
	dateJoinedUS    DATE        NOT NULL,
	governor	VARCHAR(20) NOT NULL,
	CONSTRAINT state_pk
            PRIMARY KEY (stateCode));

CREATE TABLE county (
        stateCode       VARCHAR(2)  NOT NULL,
	countyName	VARCHAR(20) NOT NULL,
	population	VARCHAR(10) NOT NULL,
	countySeat	VARCHAR(10) NOT NULL,
	CONSTRAINT county_pk
		PRIMARY KEY (stateCode, countyName),
	CONSTRAINT county_state_fk
		FOREIGN KEY (stateCode)
		REFERENCES state (stateCode));

CREATE TABLE city (
	stateCode	VARCHAR(2)  NOT NULL,
        countyName      VARCHAR(20) NOT NULL,        
	cityName	VARCHAR(20) NOT NULL,
	population	VARCHAR(10) NOT NULL,
	lastCensusDate  DATE        NOT NULL,
	mayor		VARCHAR(20) NOT NULL,
	CONSTRAINT city_pk
		PRIMARY KEY (stateCode, countyName, cityName),
	CONSTRAINT city_county_fk
		FOREIGN KEY (stateCode, countyName)
		REFERENCES county (stateCode, countyName));

-- Anaheim, Orange County, CA
INSERT INTO state (stateName, stateCode, dateJoinedUS, governor)
	VALUES('California', 'CA', '1850-09-09', 'Jerry Brown'); 

INSERT INTO county (stateCode, countyName, population, countySeat)
	VALUES('CA', 'Orange County', '3,017,000', 'Santa Ana');

INSERT INTO city (stateCode, countyName, cityName, population, lastCensusDate, mayor)
	VALUES('CA', 'Orange County', 'Anaheim', '351,043', '2017-07-01', 'Tom Tait');

-- Scranton, Lackawanna County, PA
INSERT INTO state (stateName, stateCode, dateJoinedUS, governor)
	VALUES('Pennsylvania', 'PA', '1787-12-12', 'Tom Wolf');
 
INSERT INTO county (stateCode, countyName, population, countySeat)
	VALUES('PA', 'Lackawanna County', '211,917', 'Scranton');

INSERT INTO city (stateCode, countyName, cityName, population, lastCensusDate, mayor)
	VALUES('PA', 'Lackawanna County', 'Scranton', '77,291', '2017-07-01', 'William Courtright');


