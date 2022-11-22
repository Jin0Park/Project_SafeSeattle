DROP SCHEMA IF EXISTS PM2;
CREATE SCHEMA PM2;
USE PM2;

DROP TABLE IF EXISTS OffenseCodeDetails;

CREATE TABLE OffenseCodeDetails(
	OffenseCode Varchar(255),
    OffenseDescription Varchar(255),
    OffenseParentGroup Varchar(255),
    CrimeAgainstCategory Enum('SOCIETY', 'PROPERTY', 'PERSON', 'NOT_A_CRIME'),
    Constraint pk_OffenseCodeDetails_OffenseCode PRIMARY KEY (OffenseCode)
);

LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/SPD_Crime_Data__2018-2022.csv' IGNORE INTO TABLE OffenseCodeDetails
   FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
   LINES TERMINATED BY '\n'
   IGNORE 1 LINES
    (@dummy, @dummy, @dummy, @dummy, @dummy, @dummy, CrimeAgainstCategory, OffenseParentGroup, OffenseDescription, OffenseCode, 
    @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy);
    
SELECT * FROM OffenseCodeDetails;