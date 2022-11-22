USE PM2;

-- 1. Which neighborhood(MCPP) has the most/least crime cases? (Insight) 
SELECT MCPP, COUNT(ReportId) AS CNT
FROM Offenses
GROUP BY MCPP
ORDER BY CNT DESC
LIMIT 1;

SELECT MCPP, COUNT(ReportId) AS CNT
FROM Offenses
GROUP BY MCPP
ORDER BY CNT 
LIMIT 1;


-- 2. What is the average number of reports for each day of the week? Which days have the highest average? (Insight)
--    NOTE: The weekday() function will return a number that corresponds with the day of the week 0 - Sunday, 1 - Monday, 2 - Tuesday, etc.
SELECT day, AVG(day_count) AS average_per_day
FROM(
	SELECT WEEKDAY(ReportTime) AS day,  COUNT(*) AS day_count
    FROM Reports
    GROUP BY day
    ) AS countperday
GROUP BY day
ORDER BY average_per_day DESC;

-- select count(*)
-- from Reports;

-- 3. What season/month has the most crime cases? (Insight)
-- per month
SELECT MONTH(OffenseDate) AS mon, OffenseCodeDetails.OffenseDescription AS Offense, Offenses.MCPP AS location, COUNT(*) AS nums
FROM Offenses 
JOIN OffenseCodeDetails ON Offenses.OffenseCode = OffenseCodeDetails.OffenseCode
GROUP BY mon
ORDER BY mon;

-- per season
(SELECT "Spring", OffenseCodeDetails.OffenseDescription AS MostCrimeCase, Offenses.MCPP AS Location, COUNT(*) AS NumberOfCases
FROM Offenses
	JOIN OffenseCodeDetails ON Offenses.OffenseCode = OffenseCodeDetails.OffenseCode
WHERE (MONTH(Offenses.OffenseDate) > 2 AND DAY(Offenses.OffenseDate) > 20) OR
	  (MONTH(Offenses.OffenseDate) < 5 AND Day(Offenses.OffenseDate) < 22)
GROUP BY MostCrimeCase
ORDER BY NumberOfCases DESC
LIMIT 1)
UNION
(SELECT "Summer", OffenseCodeDetails.OffenseDescription AS MostCrimeCase, Offenses.MCPP AS Location, COUNT(*) AS NumberOfCases
FROM Offenses
	JOIN OffenseCodeDetails ON Offenses.OffenseCode = OffenseCodeDetails.OffenseCode
WHERE (MONTH(Offenses.OffenseDate) > 5 AND DAY(Offenses.OffenseDate) > 21) OR
	  (MONTH(Offenses.OffenseDate) < 10 AND Day(Offenses.OffenseDate) < 23)
GROUP BY MostCrimeCase
ORDER BY NumberOfCases DESC
LIMIT 1)
UNION
(SELECT "Autumn", OffenseCodeDetails.OffenseDescription AS MostCrimeCase, Offenses.MCPP AS Location, COUNT(*) AS NumberOfCases
FROM Offenses
	JOIN OffenseCodeDetails ON Offenses.OffenseCode = OffenseCodeDetails.OffenseCode
WHERE (MONTH(Offenses.OffenseDate) > 8 AND DAY(Offenses.OffenseDate) > 22) OR
	  (MONTH(Offenses.OffenseDate) < 13 AND Day(Offenses.OffenseDate) < 21)
GROUP BY MostCrimeCase
ORDER BY NumberOfCases DESC
LIMIT 1)
UNION
(SELECT "Winter", OffenseCodeDetails.OffenseDescription AS MostCrimeCase, Offenses.MCPP AS Location, COUNT(*) AS NumberOfCases
FROM Offenses
	JOIN OffenseCodeDetails ON Offenses.OffenseCode = OffenseCodeDetails.OffenseCode
WHERE (MONTH(Offenses.OffenseDate) = 12 AND DAY(Offenses.OffenseDate) > 20) OR
	  (MONTH(Offenses.OffenseDate) < 4 AND Day(Offenses.OffenseDate) < 21)
GROUP BY MostCrimeCase
ORDER BY NumberOfCases DESC
LIMIT 1);


-- 4. The most frequent crimeAgainstCategory/OffenseParentGroup type per MCPP? (Insight)
SELECT Offenses.MCPP, OffenseCodeDetails.OffenseParentGroup, COUNT(OffenseCodeDetails.OffenseParentGroup) AS CNT
FROM Offenses INNER JOIN OffenseCodeDetails ON Offenses.OffenseCode = OffenseCodeDetails.OffenseCode
GROUP BY MCPP
ORDER BY CNT DESC;

SELECT Offenses.MCPP, OffenseCodeDetails.crimeAgainstCategory, COUNT(OffenseCodeDetails.crimeAgainstCategory) AS CNT
FROM Offenses INNER JOIN OffenseCodeDetails ON Offenses.OffenseCode = OffenseCodeDetails.OffenseCode
GROUP BY MCPP
ORDER BY CNT DESC;


-- 5. The most commented post within the past year (UI Admin analysis):
SELECT Comments.postId,
COUNT(IF(created > '2021-11-7',
	TIMESTAMPDIFF(DAY, '2021-11-7', CURRENT_DATE()),
	TIMESTAMPDIFF(DAY, created, CURRENT_DATE())
) ) AS NumberOfComments
FROM Comments
GROUP BY PostId
ORDER BY NumberOfComments DESC 
LIMIT 1;


-- 6. Top 5 reports with highest offense counts (Insight)
SELECT Offenses.reportId, MCPP, COUNT(*) 
AS numOffenses FROM Offenses 
GROUP BY Offenses.reportId 
ORDER BY numOffenses 
DESC LIMIT 5;

-- 7. Which day do users have the most/least login activities? (Admin analysis)
SELECT WEEKDAY(SessionStart) AS Day, COUNT(*) AS nums
FROM LoginInformation
WHERE UserOrAdmin = 'user'
GROUP BY Day;

-- 8. Which neighborhood do application users have the most preference for? (Admin analysis)
SELECT Neighborhood, COUNT(*) AS neighborhood_count
FROM Persons 
GROUP BY Neighborhood
ORDER BY neighborhood_count DESC;

-- 9. What is the most active crime report? Most active report could be defined as report with most user posts and comments combined.
SELECT Reports.ReportId, Reports.ReportTime, COUNT(Posts.PostId) + COUNT(Comments.CommentId) AS ActivityCt
FROM Reports
INNER JOIN Posts ON Reports.ReportId = Posts.ReportId
LEFT JOIN Comments ON Posts.PostId = Comments.PostId
GROUP BY Reports.ReportId, Reports.ReportTime
ORDER BY ActivityCt DESC LIMIT 1;


-- 10. Is there any significant difference between the average posts and comments between premium and basic users? 
-- What is the split between the user type groups? E.g. 40% basic and 60% premium.
	# Note1: TotalUsers table is being joined without any ‘ON’ statement to get the proportion number for basic vs premium users. 
    #        The AVG(TotalUsers.totalCt) will result in just the totalCt 
    #        because the ‘totalCt’ will get duplicated due to all the joins so the average will result in a right totalCt.
	# Note2: Use Count Distinct to calculate average posts and average comments to make sure that duplications are not counted 
    #        (e.g. after left joining the comments table, each post row might get duplicated)
SELECT Users.UserType,
COUNT(DISTINCT Users.UserId) / AVG(TotalUsers.totalCt) AS Proportion,
COUNT(DISTINCT Posts.PostId) / COUNT(DISTINCT Users.UserId) AS AvgPosts,
COUNT(DISTINCT Comments.CommentId) / COUNT(DISTINCT Users.UserId) AS AvgComments
FROM Users
LEFT JOIN Posts ON Users.UserId = Posts.UserId
LEFT JOIN Comments ON Users.UserId = Comments.UserId
JOIN (SELECT COUNT(*) AS totalCt FROM Users) AS TotalUsers
GROUP BY UserType;






