[![Build Status](https://travis-ci.org/SergiyDyrda/Voting-System.svg?branch=master)](https://travis-ci.org/SergiyDyrda/Voting-System)

Java Enterprise Project "Voting System"
=======================================

Implementation of JSON API using Maven/Spring/Security/JPA(Hibernate)/REST(Jackson). Front end built upon Angular 2.

Voting system for deciding where to have lunch.

 * 2 types of users: admin and regular **users**
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and description)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

=======================================
To deploy app you`ll need <a href="http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html">Java JDK 8</a>, <a href="https://tomcat.apache.org/download-80.cgi">Tomcat 8+</a>, <a href="https://maven.apache.org/download.cgi">Maven</a>, <a href="https://nodejs.org/en/">NodeJS</a>, <a href="https://www.postgresql.org/download/">PostgreSQL</a>

**Instruction:**
    1. Clone or download this repo, checkout to branch _master_
    2. Open PostgreSQL console editor and execute following commands:
        - ```CREATE DATABASE votingsystem;```
        - ```CREATE USER developer WITH password 'root';```
        - ```GRANT ALL privileges ON DATABASE votingsystem TO developer;```
    3. Open project folder and go to ```src/main/webapp```
    4. Execute command ```npm install```
    5. Execute command ```npm run tsc```
    6. Package project to _war_-file (execute command ```mvn package``` in project folder)
    7. Deploy _war_-file on Tomcat
    8. Done.

<a href="https://votingsystem01.herokuapp.com">Demo of application deployed on heroku cloud platform</a>

-----------------------------
Technology stack: <a href="http://projects.spring.io/spring-security/">Spring Security</a>, <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>, <a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security Test</a>, <a href="http://hibernate.org/orm/">Hibernate ORM</a>, <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
<a href="https://logback.qos.ch">Logback</a>, <a href="https://github.com/FasterXML/jackson">Json Jackson</a>, <a href="http://tomcat.apache.org/">Apache Tomcat</a>, <a href="http://ehcache.org">Ehcache</a>, <a href="http://www.postgresql.org/">PostgreSQL</a>, <a href="http://junit.org/">JUnit</a>, <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>, <a href="http://code.google.com/p/log4jdbc-remix">Log4JDBC</a>, <a href="https://angular.io/">Angular 2</a>, <a href="http://getbootstrap.com/">Bootstrap</a>



