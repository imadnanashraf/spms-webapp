Step 1: Install mysql(8.0.27.1), tomcat 9, java 8 and eclipse ee.

Step 2: Open Eclipse and Configure TomCat 9.

Step 3: Import project in eclipse.

Step 4: After importing spms in eclipse open sql-scripts folder placed in spms/sql-scripts.

Step 5: Run Mysql Workbench and log in to database and first run user_creation sql script and then run database_creation sql script which are both placed in spms/sql-scripts folder.

Step 6: In Eclipse right click on spms project and then click on run as and then run on server.

Additional:- If you want to change the link redirect or change the email which is used to send verification links to user.

Additional Step: Simply goto spms\src\main\resources and open email-config.properties file and change mail.username which is the email and change mail.password which is the mail app password.

Additional Step: For changing link redirect simply change link.redirect parameter in the same file.