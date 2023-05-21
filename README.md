# user management project

Sample project to work with SpringBoot REST controllers, Bean Mapping libraries, Spring Data JPA and in-memory h2 database.

## Spring Data JPA
The project shows how to use One-To-Many and Many-To-One relations. User object has one to many relation to Club.

## Postman
The project has postman collection json file which can be used to trigger endpoints.  I have configured Spring Security Basic Authentication.  The username/password are in local properties file.  Just in case they are root/root.

## Swagger URL 

Swagger url:
http://localhost:8080/swagger-ui/index.html

## Running project in a docker container

Execute gralde command ` ./gradlew bootBuildImage`

That will build the docker image `usermanagement` ( configured in build.gradle ) and will save it in local docker.

To start the docker container with the project image run:  <br>
`docker run -p 8080:8080 usermanagement`

The project will be accessable on local port 8080.





