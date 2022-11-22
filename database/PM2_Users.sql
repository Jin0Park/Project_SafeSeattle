DROP SCHEMA IF EXISTS PM2;
CREATE SCHEMA PM2;
USE PM2;

DROP TABLE IF EXISTS Persons;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Admins;

CREATE TABLE Persons(
	PersonId INT AUTO_INCREMENT,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    PhoneNumber VARCHAR(30),
    Email VARCHAR(255),
    Address VARCHAR(255),
    Neighborhood VARCHAR(255),
    CONSTRAINT pk_Persons_PersonId PRIMARY KEY (PersonId)
);

CREATE TABLE Users(
	UserId INT,
    Username VARCHAR(255),
    Password VARCHAR(255),
    UserType ENUM ('basic', 'premium'),
    PersonId INTEGER,
    CONSTRAINT pk_Users_UserId PRIMARY KEY (UserId),
	CONSTRAINT fk_Users_PersonId FOREIGN KEY (PersonId)
		REFERENCES Persons(PersonId)
        ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Admins(
	AdminId INT,
    UserId INTEGER,
    Username VARCHAR(255),
    Password VARCHAR(255),
    EditAccess ENUM ('all', 'limited'),
    CONSTRAINT pk_Admins_AdminId PRIMARY KEY (AdminId),
	CONSTRAINT fk_Admins_UserId FOREIGN KEY (UserId)
		REFERENCES Users(UserId)
        ON UPDATE CASCADE ON DELETE CASCADE
);


LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/Persons.csv' INTO TABLE Persons
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\r\n'
   IGNORE 1 LINES
  (PersonId, FirstName, LastName, PhoneNumber, Email, Address, Neighborhood);
  SET foreign_key_checks = 0;
  LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/Users.csv' INTO TABLE Users
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\r\n'
   IGNORE 1 LINES
  (UserId, Username, Password, UserType);
  SET foreign_key_checks = 1;
  

SET foreign_key_checks = 0;
 LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/Admins.csv' INTO TABLE Admins
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\r\n'
   IGNORE 1 LINES
  (AdminId, Username, Password, EditAccess);
SET foreign_key_checks = 0;


  
  SELECT * FROM Persons;
  SELECT * FROM Users;
  SELECT * FROM Admins;
  
