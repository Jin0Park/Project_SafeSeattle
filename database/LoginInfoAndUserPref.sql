DROP SCHEMA IF EXISTS PM2;
CREATE SCHEMA PM2;
USE PM2;

DROP TABLE IF EXISTS LoginInformation;
DROP TABLE IF EXISTS UserPreferences;

CREATE TABLE LoginInformation (
	LoginId INT,
    UserId INT,
    SessionStart TIMESTAMP NOT NULL  DEFAULT current_timestamp,
    UserOrAdmin ENUM ('admin', 'user'),
    CONSTRAINT pk_LoginInformation_LoginId PRIMARY KEY (LoginId),
    CONSTRAINT fk__LoginInformation_UserId FOREIGN KEY (UserId) REFERENCES
    Users(UserId) ON UPDATE CASCADE ON DELETE CASCADE
    


);

LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/LoginInformation.csv' INTO TABLE LoginInformation
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\r\n'
   IGNORE 1 LINES
  (LoginId, UserId, SessionStart, UserOrAdmin);

SELECT * FROM LoginInformation;

CREATE TABLE UserPreferences (
	UserId INT,
    Neighborhood VARCHAR(255),
    Notifications BOOL,
    CONSTRAINT pk_UserPreferences_UserId PRIMARY KEY (UserId),
    CONSTRAINT fk__UserPreferences_UserId FOREIGN KEY (UserId) REFERENCES
    LoginInformation(UserId) ON UPDATE CASCADE ON DELETE CASCADE

);

LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/UserPreferences .csv' INTO TABLE UserPreferences
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\n'
   IGNORE 1 LINES
  (UserId, Neighborhood, Notifications);



