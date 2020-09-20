I'm also using Flyway for DB migrations to have some form of version control on our DB. Each of the class variables in the Vessel class will be mapped to a column in the DB. JPA Hibernate does this mapping automatically

Just pull my branch and open the Server/pom.xml as a project in IntelliJ and it should resolve all the dependencies for you. Run the TeeamsixtesterApplication src file to start the programme.

Some notes:

1. postgresql installed on your computer (you need to change the postgresql connection configurations under main/java/resources/application.properties. I created a DB called teamsix and granted user tester with password password to all privileges on this db. Flyway will automatically created the tables and manage them for you.
2. API url is localhost:8080 by default
3. GET API: /vessel/ retrieves all rows in DB
4. POST API: /create/ creates a new row in the DB
5. PUT and DELETE does not work yet, i’m still working on it
6. Packages:
   controller: this file directs the API called to the functions in the service package
   service: contains the functions used to interact with the DB
   repository: link Hibernate with the PostgresDB
