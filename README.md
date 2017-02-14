
Java Enterprise Project "Voting System"
=======================================

Implementation of JSON API using Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson). Front end built upon Angular 2.

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



<a href="">Demo of application deployed on heroku</a>

-----------------------------
Technology stack: <a href="http://projects.spring.io/spring-security/">Spring Security</a>, <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>, <a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security Test</a>, <a href="http://hibernate.org/orm/">Hibernate ORM</a>, <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
<a href="https://logback.qos.ch">Logback</a>, <a href="https://github.com/FasterXML/jackson">Json Jackson</a>, <a href="http://tomcat.apache.org/">Apache Tomcat</a>, <a href="http://ehcache.org">Ehcache</a>, <a href="http://www.postgresql.org/">PostgreSQL</a>, <a href="http://junit.org/">JUnit</a>, <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>, <a href="http://code.google.com/p/log4jdbc-remix">Log4JDBC</a>, <a href="https://angular.io/">Angular 2</a>, <a href="http://getbootstrap.com/">Bootstrap</a>



