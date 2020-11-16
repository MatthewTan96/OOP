How to initiate the G1T6 application (Windows)

1) Have a linux program installed on your computer. For this project, Ubuntu 20.04 LTS was used.
2) On the linux system, install postgreSQL. This can be done using the commands
	2a) sudo apt-get update
	2b) sudo apt-get install postgresql-11 pgadmin4
3) Start postgreSQL on your linux server using the following command:
	3a) sudo service postgresql start
4) Connect with psql, an interactive command line terminal interface for working with PostgreSQL, using the command:
	4a) sudo -u postgres psql
5) Type the three commands to create your database:
	5a) create database teamsix;
	5b) create user tester with password 'password';
	5c) grant all privileges on database teamsix to tester;
Your database has been initialised on Ubuntu.

Using Springboot to populate your database 

1) Open the Project file on Intellij
2) Navigate to Server > entrypoint > pom.xml
3) Use Maven to download dependencies 
4) Build the application 
5) Navigate to entrypoint > src > main > java > com.IS442.teamsixtester > Boot 
6) Run the main() method to start the Springboot application 

Populate the database with information retrieved from the PSA API

1) Compile the initializer file using compile.bat . A macbook version has been created as well. 
2) Run the initializer file using run.bat . A macbook version has been created as well
3) Follow the prompts as per the initializer file. Prepare the following:
	- The domain name you wish to make exclusive 
	- The time interval for the API calls 
	- The API security key 
4) Copy the WebContent file into your WAMP web root 
5) Run the file by typing localhost/app in the URL of your browser