Not using Flyway for migrations anymore, JPA does the table creation automatically

Just pull my branch and open the Server/pom.xml as a project in IntelliJ and it should resolve all the dependencies for you. Run the TeamsixtesterApplication src file to start the programme.

Some notes:

1. You need Postgresql installed on your computer (you need to change the postgresql connection configurations under main/java/resources/application.yml.
2. Steps to create database
    > docker exec -it postgresimagename bin/bash (if you're using docker) <br>
    > psql -U psql <br>
    > create database teamsix; <br>
    > create user tester with password 'password'; <br>
    > grant all privileges on database teamsix to tester; <br>
3. API Documentation: https://docs.google.com/document/d/1fhckql2LpTw5Fdc6PbbmIeQEwXTdtgtKuCPRg_p_g7g/edit?usp=sharing
4. Packages:
   > controller: this file directs the API called to the functions in the service package <br>
   > service: contains the functions used to interact with the DB <br>
   > repository: link Hibernate with the PostgresDB <br>
