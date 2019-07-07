# technical-challenge

This project was a technical challenge proposed by Sicredi.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

1 - Install Java 8 or greater version.

2 - Install Maven, since it should be used to manage the dependecies and build it.

3 - This project is integrating with a MySql database. (can be downloaded here: https://dev.mysql.com/downloads/mysql/)

4 - Clone this repository.

### Installing

There were no custom install needed for the previous applications.

The only configuration needed is to setup the project file (desafio-tecnico\src\main\resources\application.properties) with your MySql Database config:

E.g:

	spring.jpa.hibernate.ddl-auto=update
	spring.datasource.url=jdbc:mysql://localhost:3306/desafiotecnico?useTimezone=true&serverTimezone=UTC
	spring.datasource.username=root
	spring.datasource.password=admin
	
Now you can build and start it:

1 - Run command to generate the jar file: 
mvn clean install

2 - Start the application:
java -jar desafiotecnico-0.0.1-SNAPSHOT.jar

3 - You can see all the APIs available in this application in the Swagger, accessing link below:
http://localhost:8080/swagger-ui.html#/

## Built With

* [Spring boot](http://www.dropwizard.io/1.0.2/docs/) - Application framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [MySql](https://dev.mysql.com/) - Database
* [Swagger](https://swagger.io/) - Used to describe and document the APIs

## Authors

* **Marlon Gamba**

