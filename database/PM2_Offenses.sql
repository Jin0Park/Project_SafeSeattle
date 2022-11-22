DROP SCHEMA IF EXISTS PM2;
CREATE SCHEMA PM2;
USE PM2;

DROP TABLE IF EXISTS Offenses;
DROP TABLE IF EXISTS Reports;

CREATE TABLE Reports(
	ReportId VARCHAR(255),
    ReportTime DATETIME,
    OffenseId BIGINT,
    CONSTRAINT pk_Report_ReportId PRIMARY KEY (ReportId),
    CONSTRAINT fk_Report_OffenseId FOREIGN KEY (OffenseId)
		REFERENCES PM2.Offenses (OffenseId)
		
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


LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/SPD_Crime_Data__2018-2022.csv' IGNORE INTO TABLE Reports
	FIELDS TERMINATED BY ',' 
    ENCLOSED BY '"'
	LINES TERMINATED BY '\n'
    IGNORE 1 LINES
	(ReportId, OffenseId, @dummy, @dummy, @reporttime, @dummy, @dummy, @dummy, 
    @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy)
    SET ReportTime = STR_TO_DATE(@reporttime, '%m/%d/%Y %h:%i:%s %p');
    
    
LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/Offenses.csv' INTO TABLE Offenses
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (OffenseId, ReportId, @offenseDate, OffenseCode, BlockAddress, MCPP, Longitude, Latitude)
    SET OffenseDate = STR_TO_DATE(@offenseDate, '%m/%d/%Y %H:%i:%s %p');
    
SELECT count(*) FROM Reports;
SELECT count(*) FROM Offenses;
SELECT * FROM Reports;
SELECT * FROM Offenses;
