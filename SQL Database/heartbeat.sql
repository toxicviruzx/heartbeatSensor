DROP DATABASE heartbeat;
CREATE DATABASE heartbeat;
USE heartbeat;
DROP USER 'nodered'@'localhost';
CREATE USER 'nodered'@'localhost'
IDENTIFIED BY 'nodered';
GRANT ALL PRIVILEGES ON heartbeat.* TO 'nodered'@'localhost';

CREATE TABLE account
(
	ID INT NOT NULL,
	user VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    firstname VARCHAR(20) NOT NULL,
    familyname VARCHAR(20) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    dateofbirth date NOT NULL,
	note VARCHAR(256),
	status BOOLEAN BY DEFAULT FALSE,
    type VARCHAR BY DEFAULT p,
    CONSTRAINT pk_account PRIMARY KEY (ID)
);
ENGINE = MyISAM DEFAULT charset = utf8
COLLATE = utf8_unicode_ci;

CREATE TABLE data
(
    ID INT NOT NULL,
	BPM INT NOT NULL,
    timestamp VARCHAR(20) NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (ID) REFERENCES account(ID)
);

    