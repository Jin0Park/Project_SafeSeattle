package safeseattle.tools;

import safeseattle.dal.*;
import safeseattle.model.*;
import safeseattle.model.LoginInformation.userOrAdmin;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;


/**
 * main() runner, used for the app demo.
 * 
 * Instructions:
 * 1. Create a new MySQL schema and then run the CREATE TABLE statements from lecture:
 * http://goo.gl/86a11H.
 * 2. Update ConnectionManager with the correct user, password, and schema.
 */
public class Inserter {

	public static void main(String[] args) throws SQLException {
		// will change these later to unique dates
		int reportDateYear = 2021;
		int reportDateMonth = 5;
		int reportDateDay = 25;
		Timestamp reportTs1 = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 
		                                                reportDateYear, reportDateMonth, reportDateDay));
		
		int reportDateYear2 = 2020;
		int reportDateMonth2 = 7;
		int reportDateDay2 = 1;
		Timestamp reportTs2 = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 
		                                                reportDateYear2, reportDateMonth2, reportDateDay2));
		
		int reportDateYear3 = 2018;
		int reportDateMonth3 = 11;
		int reportDateDay3 = 25;
		Timestamp reportTs3 = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 
		                                                reportDateYear3, reportDateMonth3, reportDateDay3));
	
    
		// DAO instances
		PersonsDao personsDao = PersonsDao.getInstance();
		UsersDao usersDao = UsersDao.getInstance();
		AdminsDao adminsDao = AdminsDao.getInstance();
		ReportsDao reportDao = ReportsDao.getinstance();
		NotificationsDao notiDao = NotificationsDao.getInstance();
		LoginInformationDao loginInfoDao = LoginInformationDao.getInstance();
		PostsDao postsDao = PostsDao.getInstance();
		CommentsDao commentsDao = CommentsDao.getInstance();
		OffensesDao offensesDao = OffensesDao.getInstance();
		
		// CREATE / INSERT
		System.out.println("===== CREATE =====");
    
		// Create Persons
		Persons person1 = new Persons("user1", "12345", "Domenic", "Hopkinson", "1716872247", 
				"Domenic_Hopkinson5109@ubusive.com", "4362 Ryder Avenue, 98101", "Downtown Commercial");
		person1 = personsDao.create(person1);
		Persons person2 = new Persons("user2", "23456", "Benny", "Verdon", "1641853084", 
				"Benny_Verdon5344@extex.org", "9241 13th Ave SW, 98105", "Capitol Hill");
		person2 = personsDao.create(person2);
		Persons person3 = new Persons("user3", "34567", "Vicky", "Kerr", "5862516756", 
				"Vicky_Kerr7361@bulaffy.com", "9668 Rainier Ave S, 98114", "Capitol Hill");
		person3 = personsDao.create(person3);
		Persons person4 = new Persons("admin1", "3467457", "Caitlyn", "Rogers", "2147368271", 
				"Caitlyn_Rogers8793@elnee.tech", "9679 46th Ave SW, 98105", "Northgate");
		person4 = personsDao.create(person4);
		Persons person5 = new Persons("admin2", "34567", "Rosalie", "Carter", "7016726768", 
				"Rosalie_Carter6712@iatim.tech", "9816 51st Ave SW, 98105", "University");
		person5 = personsDao.create(person5);
		
		// Create Users
		Users user1 = new Users(person1, UserType.basic);
		user1 = usersDao.create(user1);
		Users user2 = new Users(person2, UserType.basic);
		user2 = usersDao.create(user2);
		Users user3 = new Users(person3, UserType.premium);
		user3 = usersDao.create(user3);
		
		// Create Admins
		Admins admin1 = new Admins(person4, EditAccess.all);
		admin1 = adminsDao.create(admin1);
		Admins admin2 = new Admins(person5, EditAccess.limited);
		admin2 = adminsDao.create(admin2);
    
		// sql.Date
		Date reportDate = new Date(120, 12, 1);
		Date reportDate2 = new Date(120, 12, 2);
		Date reportDate3 = new Date(120, 12, 3);
		
		// create reports
		Reports report1 = new Reports("2020-044620",reportTs1);
		reportDao.create(report1);
		Reports report2 = new Reports("2020-044452", reportTs2);
		reportDao.create(report2);
		Reports report3 = new Reports("2020-044465", reportTs3);
		reportDao.create(report3);
		
		// timestamp test dates
		int year1 = 2022;
		int month1 = 3;
		int day1 = 24;
		Timestamp ts1 = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 
		                                                year1, month1, day1));
		int year2 = 2020;
		int month2 = 5;
		int day2 = 16;
		Timestamp ts2 = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 
		                                                year2, month2, day2));
		
		int year3 = 2021;
		int month3 = 9;
		int day3 = 10;
		Timestamp ts3 = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00", 
		                                                year3, month3, day3));
		
		// Create Posts
		Posts post1 = new Posts(1, user1, report1, "Car Jacking", "Witnessed card being stolen in Capitol Hill", ts1);
		postsDao.create(post1);
		Posts post2 = new Posts(2, user2, report2, "Robbery", "My neighbors house was robbed in greenlake", ts2);
		postsDao.create(post2);
		Posts post3 = new Posts(3, user3, report3, "Violence Dowtown", "I saw a fight break out at the bus stop Downtown", ts3);
		postsDao.create(post3);
		
		// Create Comments
		Comments comment1 = new Comments(user2, post1, "My car was broken into last month in Cap Hill!", ts1);
		commentsDao.create(comment1);
		Comments comment2 = new Comments(user3, post1, "It's been happening a lot recently!", ts1);
		commentsDao.create(comment2);
		Comments comment3 = new Comments(user1, post3, "I saw this happening too, police were involved quickly", ts1);
		commentsDao.create(comment3);
		
		// Create Offenses
		@SuppressWarnings("deprecation")
		Date date1 = new Date(120, 12, 1);
		Offenses offense1 = new Offenses(37264141730L, "2020-044620", date1, "26A", "96XX BLOCK OF 58TH AVE S", "RAINIER VIEW", -122.2608078, 47.51600039);
		offensesDao.create(offense1);
		Date date2 = new Date(120, 12, 2);
		Offenses offense2 = new Offenses(37263995350L, "2020-044452", date2, "220", "123XX BLOCK OF 15TH AVE NE", "LAKECITY", -122.3126481,	47.71850811);
		offensesDao.create(offense2);
		Date date3 = new Date(120, 12, 3);
		Offenses offense3 = new Offenses(37263912867L, "2020-044465", date3, "220", "123XX BLOCK OF 15TH AVE NE", "LAKECITY", -122.3126481, 47.71850811);
		Offenses offense4 = new Offenses(37263411841L, "2020-044465", date3, "26G",	"20XX BLOCK OF 8TH AVE", "SLU/CASCADE",	-122.3372444, 47.6161057);
		offensesDao.create(offense3);
		offensesDao.create(offense4);
		
		// Create Notifications
		Notifications noti1 = new Notifications(user1, "Don't go to downtown!", ts1, report1);
		notiDao.create(noti1);
		Notifications noti2 = new Notifications(user2, "Shooting reported in Northgate!", ts2, report3);
		notiDao.create(noti2);
		Notifications noti3 = new Notifications(user2, "Vehicle theft in Udistrict!", ts3, report2);
		notiDao.create(noti3);
		Notifications noti4 = new Notifications(user3, "Shooting reported in Northgate!", ts2, report3);
		notiDao.create(noti4);
		
		// Create LoginInformations
		LoginInformation logInfo1 = new LoginInformation(1, person1, ts1, userOrAdmin.user);
		loginInfoDao.create(logInfo1);
		LoginInformation logInfo2 = new LoginInformation(2, person4, ts1, userOrAdmin.admin);
		loginInfoDao.create(logInfo2);
		LoginInformation logInfo3 = new LoginInformation(3, person1, ts2, userOrAdmin.user);
		loginInfoDao.create(logInfo3);
		LoginInformation logInfo4 = new LoginInformation(4, person1, ts3, userOrAdmin.user);
		loginInfoDao.create(logInfo4);
		
		// READ
		System.out.println("===== READ =====");
		Users userResult1 = usersDao.getUserByUserId(1);
		System.out.format("Reading user by id: userId:%s username:%s firstName:%s lastName:%s email:%s phone:%s userType:%s \n",
			userResult1.getUserId(), userResult1.getUsername(), userResult1.getFirstName(), userResult1.getLastName(),
			userResult1.getEmail(), userResult1.getPhoneNumber(), userResult1.getUserType().name());
		
		List<Users> userResultList1 = usersDao.getUsersByNeighborhood("Capitol Hill");
		for (Users u : userResultList1) {
			System.out.format("Reading users by neighborhood: neighborhood:%s userId:%s username:%s firstName:%s lastName:%s email:%s phone:%s userType:%s \n",
					u.getNeighborhood(), u.getUserId(), u.getUsername(), u.getFirstName(), u.getLastName(), 
					u.getEmail(), u.getPhoneNumber(), u.getUserType().name());			
		}
		
		Admins adminResult1 = adminsDao.getAdminByUserId(4);
		System.out.format("Reading admin by id: userId:%s username:%s firstName:%s lastName:%s email:%s phone:%s editAccess:%s \n",
			adminResult1.getUserId(), adminResult1.getUsername(), adminResult1.getFirstName(), adminResult1.getLastName(),
			adminResult1.getEmail(), adminResult1.getPhoneNumber(), adminResult1.getEditAccess().name());
		
		List<Admins> adminResultList1 = adminsDao.getAdminsByEditAccess(EditAccess.all);
		for (Admins a : adminResultList1) {
			System.out.format("Reading admins by edit access: editAccess:%s userId:%s username:%s firstName:%s lastName:%s email:%s phone:%s \n",
					a.getEditAccess().name(), a.getUserId(), a.getUsername(), a.getFirstName(), a.getLastName(), 
					a.getEmail(), a.getPhoneNumber());			
		}
		
		// Posts Read test
		Posts postsResult1 = postsDao.getPostById(1);
		System.out.format("Reading post by id: postId:%s username:%s reportId:%s content:%s created:%s \n",
				postsResult1.getPostId(), postsResult1.getUser().getUsername(), postsResult1.getReport().getReportId(), postsResult1.getContent(),
				postsResult1.getCreated());
		
		// Comments Read test
		Comments commentResult1 = commentsDao.getCommentById(1);
		System.out.format("Reading comment by id: commentId:%s username:%s postId:%s content:%s created:%s \n",
				commentResult1.getCommentId(), commentResult1.getUser().getUsername(), commentResult1.getPost().getPostId(), commentResult1.getContent(),
				commentResult1.getCreated());
		
		// Offenses Read test
		Offenses offenseResult1 = offensesDao.getOffenseById(37264141730L);
		System.out.format("Reading offense by id: offenseId:%s, reportId:%s, offenseDate:%s, offenseCode:%s, blockAddress:%s, mcpp:%s, longitude:%s, latitude:%s \n",
				offenseResult1.getOffenseId(), offenseResult1.getReportId(), offenseResult1.getOffenseDate(), offenseResult1.getOffenseCode(),
				offenseResult1.getBlockAddress(), offenseResult1.getMcpp(), offenseResult1.getLongitude(), offenseResult1.getLatitude());
		List<Offenses> offensesByReportID = offensesDao.getOffensesByReportId("2020-044465");
		for (Offenses o : offensesByReportID) {
			System.out.format("Reading offense by id: offenseId:%s, reportId:%s, offenseDate:%s, offenseCode:%s, blockAddress:%s, mcpp:%s, longitude:%s, latitude:%s \n",
					o.getOffenseId(), o.getReportId(), o.getOffenseDate(), o.getOffenseCode(), o.getBlockAddress(), o.getMcpp(), o.getLongitude(), o.getLatitude());
		}
		
		// Notifications Read test
		Notifications notiResult1 = notiDao.getNotificationByNotificationId(1);
		System.out.format("Reading notification by id: notificationId:%s userId:%s message:%s notificationTime:%s reportId:%s \n",
			notiResult1.getNotificationId(), notiResult1.getUser().getUserId(), notiResult1.getMessage(), notiResult1.getNotificationTime(), 
			notiResult1.getReport().getReportId());
		
		List<Notifications> notiResultList1 = notiDao.getNotificationByReportId(report3.getReportId());
		for (Notifications n : notiResultList1) {
			System.out.format("Looping notifications: notificationId:%s userId:%s message:%s notificationTime:%s reportId:%s \n",
					n.getNotificationId(), n.getUser().getUserId(), n.getMessage(), n.getNotificationTime(), n.getReport().getReportId());	
		}
		
		// LoginInformations Read test
		LoginInformation logInfoResult1 = loginInfoDao.getLoginInformationByLoginInformationId(1);
		System.out.format("Reading login information by id: loginId:%s, userId:%s, sessionStart:%s, userOrAdmin:%s \n",
			logInfoResult1.getLoginId(), logInfoResult1.getPerson().getUserId(), logInfoResult1.getSessionStart(), 
			logInfoResult1.getUserOrAdmin());
		
		List<LoginInformation> logInfoResultList1 = loginInfoDao.getLoginInformationsByUserId(person1.getUserId());
		for (LoginInformation n : logInfoResultList1) {
			System.out.format("Looping login information: loginId:%s, userId:%s, sessionStart:%s, userOrAdmin:%s \n",
					n.getLoginId(), n.getPerson().getUserId(), n.getSessionStart(), n.getUserOrAdmin());	
		}
		System.out.println("\n");


		// UPDATE
		System.out.println("===== UPDATE =====");
		System.out.println("User 2, userType old: " + user2.getUserType().name());
		user2 = usersDao.updateUserType(user2, UserType.premium);
		System.out.println("User 2, userType new: " + user2.getUserType().name());
		
		System.out.println("Person 2, neighborhood old: " + person2.getNeighborhood());
		person2 = personsDao.updateNeighborhood(person2, "Downtown Commercial");
		System.out.println("Person 2, neighborhood new: " + person2.getNeighborhood());
		System.out.println("\n");
		
		// Posts Update test
		System.out.println("Post1 old content: " + post1.getContent());
		post1 = postsDao.updateContent(post1, "updated content for post1");
		System.out.println("Post1 new content: " + post1.getContent());
		
		// Comments Update test
		System.out.println("Comment1 old content: " + comment1.getContent());
		comment1 = commentsDao.updateContent(comment1, "updated content for comment1");
		System.out.println("Comment1 new content: " + comment1.getContent());

		// Notifications Update Test
		System.out.println("noti1 old message: " + noti1.getMessage());
		noti1 = notiDao.updateMessage(noti1, "Just Kiddin");
		System.out.println("noti1 new message: " + noti1.getMessage());
		
		// LoginInformations Update Test
		System.out.println("logInfo1 old session start: " + logInfo1.getSessionStart());
		logInfo1 = loginInfoDao.updateSessionStart(logInfo1, ts3);
		System.out.println("logInfo1 new session start: " + logInfo1.getSessionStart());
		
		// DELETE
		System.out.println("===== DELETE =====");

	}
}
