DROP SCHEMA IF EXISTS PM4;
CREATE SCHEMA PM4;
USE PM4;

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

CREATE TABLE Users(
	UserId INT,
    UserType ENUM ('basic', 'premium'),
    CONSTRAINT pk_Users_UserId PRIMARY KEY (UserId),
	CONSTRAINT fk_Users_UserId FOREIGN KEY (UserId)
		REFERENCES Persons(UserId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE LoginInformation (
	LoginId INT,
    UserId INT,
    SessionStart TIMESTAMP NOT NULL  DEFAULT current_timestamp,
    UserOrAdmin ENUM ('admin', 'user'),
    CONSTRAINT pk_LoginInformation_LoginId PRIMARY KEY (LoginId),
    CONSTRAINT fk_LoginInformation_UserId FOREIGN KEY (UserId) 
		REFERENCES Persons(UserId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE UserPreferences (
	UserId INT,
    Neighborhood VARCHAR(255),
    Notifications BOOL,
    CONSTRAINT pk_UserPreferences_UserId PRIMARY KEY (UserId),
    CONSTRAINT fk_UserPreferences_UserId FOREIGN KEY (UserId) 
		REFERENCES Users(UserId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Admins(
	UserId INT,
    EditAccess ENUM ('all', 'limited'),
    CONSTRAINT pk_Admins_UserId PRIMARY KEY (UserId),
	CONSTRAINT fk_Admins_UserId FOREIGN KEY (UserId)
		REFERENCES Persons(UserId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Reports(
	ReportId VARCHAR(255),
    ReportTime DATETIME,
    CONSTRAINT pk_Report_ReportId PRIMARY KEY (ReportId)
);

CREATE TABLE Posts(
	PostId INTEGER,
	UserId INTEGER,
	ReportId VARCHAR(255),
	Title VARCHAR(255),
	Content TEXT,
	Created TIMESTAMP,
	CONSTRAINT pk_Posts_PostId PRIMARY KEY (PostId),
	CONSTRAINT fk_Posts_UserId FOREIGN KEY (UserId)
		REFERENCES Users (UserId) ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT fk_Posts_ReportId FOREIGN KEY (ReportId)
		REFERENCES Reports (ReportId) ON UPDATE CASCADE ON DELETE SET NULL
);

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
		REFERENCES Users (UserId) ON UPDATE CASCADE ON DELETE SET NULL
);

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
		REFERENCES Reports (ReportId)
		ON UPDATE CASCADE ON DELETE CASCADE
);

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

CREATE TABLE OffenseCodeDetails(
	OffenseCode Varchar(255),
    OffenseDescription Varchar(255),
    OffenseParentGroup Varchar(255),
    CrimeAgainstCategory Enum('SOCIETY', 'PROPERTY', 'PERSON', 'NOT_A_CRIME'),
    Constraint pk_OffenseCodeDetails_OffenseCode PRIMARY KEY (OffenseCode)
);



