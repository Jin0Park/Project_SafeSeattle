DROP SCHEMA IF EXISTS PM2;
CREATE SCHEMA PM2;
USE PM2;

DROP TABLE IF EXISTS Notifications;

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

LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/Notifications.csv' INTO TABLE Notifications
  FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (NotificationId, UserId, Message, @NotificationTime, ReportId)
  SET NotificationTime = STR_TO_DATE(@NotificationTime, '%m/%d/%Y %h:%i:%s %p');

SELECT * FROM Notifications;
