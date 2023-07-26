# HospitalManagementStaff
Spring Boot project for Hospital Staff Management

Spring boot Rest api's for hospital staff management

#Pre requisite to run the projects

Mysql
JDK 17
Create a database name hms in MySQL
Change the user name and password of database in the application.properties file of the project.
#How to run the project

Setup project as a maven project in IntelliJ,STS , VS code or any other code IDE which supports Java development
It will download all the dependencies present in the pom.xml.
Now run the main method of the project or setup project SDK and run the project.
Project will run on an 8084 port or you can change the port from the application.properties file by changing the value of appilication.properties.

#Public Api 
1. Signup api - curl --location 'localhost:8084/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "abhishek",
  "age": 23,
  "phoneNumber": "123-456-7890",
  "address": "123 Main St",
  "userName" : "an@123",
  "password" : "123456"

}'

2. Login api - curl --location 'localhost:8084/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userName" :  "an@123",
    "password" : "123456"
}

3.I have added a postman collections for testing all the  api. You need to pass a Authorization header that you will get from login request.

4. Query to run  in MySQL after starting the application for the testing purpose  -
insert  into `admission_type`(`id`,`fixed_charge`,`per_day_charge`,`type`) values 
(1,500,1000,'Normal'),
(2,1000,2000,'Emergency');
