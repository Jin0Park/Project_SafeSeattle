DROP SCHEMA IF EXISTS PM2;
CREATE SCHEMA PM2;
USE PM2;

DROP TABLE IF EXISTS Persons;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Admins;
DROP TABLE IF EXISTS LoginInformation;
DROP TABLE IF EXISTS UserPreferences;
DROP TABLE IF EXISTS OffenseCodeDetails;
DROP TABLE IF EXISTS Posts;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS Notification;
DROP TABLE IF EXISTS Report;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Admins;

-- THIS FILE REQUIRES ALL CSV FILES TO BE IN THE PATH /tmp/crimedata/

CREATE TABLE Persons(
	UserId INT AUTO_INCREMENT,
    Username VARCHAR(255),
    Password VARCHAR(255),
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    PhoneNumber VARCHAR(30),
    Email VARCHAR(255),
    Address VARCHAR(255),
    Neighborhood VARCHAR(255),
    CONSTRAINT pk_Persons_UserId PRIMARY KEY (UserId)
);

LOAD DATA INFILE '/tmp/crimedata/Persons.csv' INTO TABLE Persons
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\r\n'
   IGNORE 1 LINES
  (PersonId, FirstName, LastName, PhoneNumber, Email, Address, Neighborhood);
  SET foreign_key_checks = 0;
  
  

CREATE TABLE Users(
	UserId INT,
    UserType ENUM ('basic', 'premium'),
    CONSTRAINT pk_Users_UserId PRIMARY KEY (UserId),
	CONSTRAINT fk_Users_UserId FOREIGN KEY (UserId)
		REFERENCES Persons(UserId)
        ON UPDATE CASCADE ON DELETE CASCADE
);
 

LOAD DATA INFILE '/tmp/crimedata/Users.csv' INTO TABLE Users
	FIELDS TERMINATED BY ','
	ENCLOSED BY '"'
	LINES TERMINATED BY '\r\n'
	IGNORE 1 LINES
	(UserId, Username, Password, UserType, PersonId);
	SET foreign_key_checks = 1;
    


CREATE TABLE LoginInformation (
	LoginId INT,
    UserId INT,
    SessionStart TIMESTAMP NOT NULL  DEFAULT current_timestamp,
    UserOrAdmin ENUM ('admin', 'user'),
    CONSTRAINT pk_LoginInformation_LoginId PRIMARY KEY (LoginId),
    CONSTRAINT fk_LoginInformation_UserId FOREIGN KEY (UserId) 
		REFERENCES Persons(UserId) ON UPDATE CASCADE ON DELETE CASCADE
);

LOAD DATA INFILE '/tmp/crimedata/LoginInformation.csv' INTO TABLE LoginInformation
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\r\n'
   IGNORE 1 LINES
  (LoginId, UserId, SessionStart, UserOrAdmin);


CREATE TABLE UserPreferences (
	UserId INT,
    Neighborhood VARCHAR(255),
    Notifications BOOL,
    CONSTRAINT pk_UserPreferences_UserId PRIMARY KEY (UserId),
    CONSTRAINT fk_UserPreferences_UserId FOREIGN KEY (UserId) 
		REFERENCES Users(UserId) ON UPDATE CASCADE ON DELETE CASCADE
);

LOAD DATA INFILE '/tmp/crimedata/UserPreferences.csv' INTO TABLE UserPreferences
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\n'
   IGNORE 1 LINES
  (UserId, Neighborhood, Notifications);


CREATE TABLE Admins(
	UserId INT,
    EditAccess ENUM ('all', 'limited'),
    CONSTRAINT pk_Admins_UserId PRIMARY KEY (UserId),
	CONSTRAINT fk_Admins_UserId FOREIGN KEY (UserId)
		REFERENCES Persons(UserId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

SET foreign_key_checks = 0;
 LOAD DATA INFILE '/tmp/crimedata/Admins.csv' INTO TABLE Admins
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\r\n'
   IGNORE 1 LINES
  (AdminId, UserId, Username, Password, EditAccess);
SET foreign_key_checks = 0;


CREATE TABLE Posts(
	PostId INTEGER,
	UserId INTEGER,
	ReportId VARCHAR(255),
	Title VARCHAR(255),
	Content TEXT,
	Created TIMESTAMP,
	CONSTRAINT pk_Posts_PostId PRIMARY KEY (PostId),
	CONSTRAINT fk_Posts_UserId FOREIGN KEY (UserId)
		REFERENCES PM2.Users (UserId) ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT fk_Posts_ReportId FOREIGN KEY (ReportId)
		REFERENCES PM2.Reports (ReportId) ON UPDATE CASCADE ON DELETE SET NULL
);

LOAD DATA INFILE '/tmp/crimedata/Posts.csv' IGNORE INTO TABLE Posts
	FIELDS TERMINATED BY ',' 
    ENCLOSED BY '"'
	LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    

CREATE TABLE Comments(
	CommentId INTEGER AUTO_INCREMENT,
    UserId INTEGER,
    PostId INTEGER,
    Content TEXT,
    Created TIMESTAMP,
    CONSTRAINT pk_Comments_CommentId PRIMARY KEY (CommentId),
    CONSTRAINT fk_Comments_PostId FOREIGN KEY (PostId)
		REFERENCES Posts (PostId) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_Comments_UserId FOREIGN KEY (UserId)
		REFERENCES PM2.Users (UserId) ON UPDATE CASCADE ON DELETE SET NULL
);

LOAD DATA INFILE '/tmp/crimedata/Comments.csv' IGNORE INTO TABLE Comments
	FIELDS TERMINATED BY ',' 
    ENCLOSED BY '"'
	LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (UserId, PostId, Content, Created);
    

CREATE TABLE Reports(
	ReportId VARCHAR(255),
    ReportTime DATETIME,
    CONSTRAINT pk_Report_ReportId PRIMARY KEY (ReportId)
);

LOAD DATA INFILE '/tmp/crimedata/SPD_Crime_Data__2018-2022.csv' IGNORE INTO TABLE Reports
	FIELDS TERMINATED BY ',' 
    ENCLOSED BY '"'
	LINES TERMINATED BY '\n'
    IGNORE 1 LINES
	(ReportId, OffenseId, @dummy, @dummy, @reporttime, @dummy, @dummy, @dummy, 
    @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy)
    SET ReportTime = STR_TO_DATE(@reporttime, '%m/%d/%Y %h:%i:%s %p');
    

CREATE TABLE Notifications(
	NotificationId INT AUTO_INCREMENT,
    UserId INT,
    Message VARCHAR(255),
    NotificationTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ReportId VARCHAR(255),
    CONSTRAINT pk_Notifications_NotificationId PRIMARY KEY (NotificationId),
    CONSTRAINT fk_Notifications_UserId FOREIGN KEY (UserId)
		REFERENCES Users (UserId)
		ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_Notifications_ReportId FOREIGN KEY (ReportId)
		REFERENCES Report (ReportId)
		ON UPDATE CASCADE ON DELETE CASCADE    
);

LOAD DATA INFILE '/tmp/crimedata/Notifications.csv' INTO TABLE Notifications
  FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (NotificationId, UserId, Message, @NotificationTime, ReportId)
  SET NotificationTime = STR_TO_DATE(@NotificationTime, '%m/%d/%Y %h:%i:%s %p');


CREATE TABLE Offenses(
	OffenseId BIGINT,
    ReportId VARCHAR(25), # data type should be Varchar instead of Bigint.
    OffenseDate DATETIME,
    OffenseCode VARCHAR(10), # some offenseCode contains characters. 
    BlockAddress VARCHAR(255),
    MCPP VARCHAR(255),
    Longitude DECIMAL(12, 9),
    Latitude DECIMAL(12, 9),
    CONSTRAINT pk_Offenses_OffenseId PRIMARY KEY (OffenseID),
    CONSTRAINT fk_Offenses_ReportId FOREIGN KEY (ReportId)
		REFERENCES Reports(ReportId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

LOAD DATA INFILE '/tmp/crimedata/Offenses.csv' INTO TABLE Offenses
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (OffenseId, ReportId, @offenseDate, OffenseCode, BlockAddress, MCPP, Longitude, Latitude)
    SET OffenseDate = STR_TO_DATE(@offenseDate, '%m/%d/%Y %H:%i:%s %p');
    

CREATE TABLE OffenseCodeDetails(
	OffenseCode Varchar(255),
    OffenseDescription Varchar(255),
    OffenseParentGroup Varchar(255),
    CrimeAgainstCategory Enum('SOCIETY', 'PROPERTY', 'PERSON', 'NOT_A_CRIME'),
    Constraint pk_OffenseCodeDetails_OffenseCode PRIMARY KEY (OffenseCode)
);

LOAD DATA INFILE '/tmp/crimedata/SPD_Crime_Data__2018-2022.csv' IGNORE INTO TABLE OffenseCodeDetails
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\n'
   IGNORE 1 LINES
    (@dummy, @dummy, @dummy, @dummy, @dummy, @dummy, CrimeAgainstCategory, OffenseParentGroup, OffenseDescription, OffenseCode, 
    @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy);
    
    
-- COUNT ALL ROWS FROM ALL TABLES
SELECT SUM(TABLE_ROWS) 
     FROM INFORMATION_SCHEMA.TABLES 
     WHERE TABLE_SCHEMA = 'PM2';

SELECT * FROM Persons;
SELECT * FROM Users;
SELECT * FROM LoginInformation;
SELECT * FROM UserPreferences;
SELECT * FROM Admins;
SELECT * FROM Posts;
SELECT * FROM Comments;
SELECT * FROM Reports;
SELECT * FROM Notifications;
SELECT * FROM Offenses;
SELECT * FROM OffenseCodeDetails;









