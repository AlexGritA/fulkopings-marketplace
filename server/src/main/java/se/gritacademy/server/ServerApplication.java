package se.gritacademy.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//Den här klassen startar servern.
// Spring Boot letar automatiskt upp alla controllers och endpoints.

//Spring Boot-applikation
@SpringBootApplication
public class ServerApplication {
    //Main-metoden startar servern. Startar Spring Boot och alla controllers
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}