USE PM2;

DROP TABLE IF EXISTS Reports;

CREATE TABLE Reports(
	ReportId VARCHAR(255),
    ReportTime DATETIME,
    OffenseId BIGINT,
    CONSTRAINT pk_Report_ReportId PRIMARY KEY (ReportId),
    CONSTRAINT fk_Report_OffenseId FOREIGN KEY (OffenseId)
		REFERENCES PM2.Offenses (OffenseId)
		
);

LOAD DATA INFILE '/Users/alexisflorence/NEU/cs5200/data/SPD_Crime_Data__2018-2022.csv' IGNORE INTO TABLE Reports
	FIELDS TERMINATED BY ',' 
    ENCLOSED BY '"'
	LINES TERMINATED BY '\n'
    IGNORE 1 LINES
	(ReportId, OffenseId, @dummy, @dummy, @reporttime, @dummy, @dummy, @dummy, 
    @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy)
    SET ReportTime = STR_TO_DATE(@reporttime, '%m/%d/%Y %h:%i:%s %p');

SELECT count(*) from Reports;










