# ParkingGarageManagementSystem
Backend application for parking garage management system

<img width="616" alt="Screen Shot 2023-02-08 at 11 15 01" src="https://user-images.githubusercontent.com/49978504/217501030-a3177df8-a516-45f1-a404-9e8e24b3af29.png">


__Technologies__
 - Maven - dependency management and building
 - Lombok - constructor and getter/setter generation with annotations
 - JPA - Object/Relational Mapping Facility for Managing Relational Data in Java Applications
 - Hibernate - Data Access and Object Relational Mapping
 - Spring Boot - Dependency Injection and Web Controllers
 - H2 - Relational Database Management System 
 - SLF4J - Logging, injected with Lombok
 
__Setup__

To run this project, be sure that mvn 4.0.0 is installed. Then run the command below:

$ mvn spring-boot:run

__Endpoints__

Now that we’ve learned about the anatomy of our endpoints and the different request methods that we should use, it’s time for some examples:

| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `POST`   | `/createGarage/{numberOfFloors}/{numberOfParkSpots}`  | Creates the garage by taking the first parameter as the nuber of floors and second as park spots on a floor|
| `GET`    | `/getSpots`                          | Returns the number of fully empty parking spots                       |
| `POST`   | `/checkIn`                 | Takes the vehicle object as the request body and creates a ticket if there is enough space in garage    |
| `POST`   | `/checkOut`                 | Takes the vehicle object as the request body and update the relevant ticket by giving the payment info |
