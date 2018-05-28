DROP DATABASE heartbeat;

CREATE DATABASE heartbeat;

USE heartbeat;

CREATE TABLE user
(
	ID INT NOT NULL,
	user VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    firstname VARCHAR(16),
    familyname VARCHAR(20) NOT NULL,
    gender VARCHAR(10),
    date VARCHAR(20) NOT NULL,
    type VARCHAR(15) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (ID)
);

CREATE TABLE address
(
	ID INT NOT NULL,
	postalcode VARCHAR(10) NOT NULL,
    streetname VARCHAR(40) NOT NULL,
	housenr INT NOT NULL,
    cityname VARCHAR(40) NOT NULL,
    CONSTRAINT pk_address PRIMARY KEY (postalcode),
    CONSTRAINT fk_user FOREIGN KEY (ID) REFERENCES client (ID)
);

CREATE TABLE data
(
    ID INT NOT NULL,
	BPM FLOAT NOT NULL,
    timestamp VARCHAR(20) NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (ID) REFERENCES client (ID)
);
SHOW TABLES;    
    