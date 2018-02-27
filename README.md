# Tender Manager

Non-commercial, educational project, still in progress.

Ultimately, it solves the problem of efficient information flow in the company, where the customer is mainly public institutions based on public tenders. This kind of client causes a large circulation of documentation, as well as a large number of projects carried out simultaneously by many people (the department of tenders, sales, marketing, etc.).
The application will implement several user profiles along with the possibilities / tasks,
for which they are responsible so that they can participate in selected projects from A to Z


## Getting Started
Application uses the following technologies: 
-	JAVA, 
-	Spring (Boot, Core, Security, MVC, Data), 
-	SQL (H2 and PostgreSQL), 
-	Hibernate, 
-	Thymeleaf, 
-	Tomcat.

### Prerequisites
-	Java SDK
-	Apache Maven

### Instaling JDK
https://docs.oracle.com/javase/7/docs/webnotes/install/windows/jdk-installation-windows.html

### Installing Maven
https://maven.apache.org/download.cgi#Installation

## Clone repository to your computer:
```
$ git clone https://github.com/wojtaszewski/tenderManager.git
```

## Run the project using Spring Boot plugin
```
$ mvn spring-boot:run -Dspring.profiles.active=dev
```

# Enjoy to watching effects
http://localhost:8080/login

default admin user:
login: w@w.w
pass: w

default standard user:
login: p@p.p
pass: p

## Running the tests
To run integration tests use Maven command
```
$ mvn integration-test
```
