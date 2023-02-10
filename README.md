# ooad-fncd
Spring 2023 Friendly Neighborhood Car classes.Dealership project
## Project setup
This project uses `gradle`. To configure gradle in NetBeans, follow this tutorial:
https://examples.javacodegeeks.com/core-java/gradle/gradle-netbeans-example/
NetBeans *should* automatically detect the repository as a gradle project.

The main class is `src\main\java\Main`.

To run, use `gradle build run`

## Code style
Classes: `CamelCase`

Class variables and methods: `camelCase`

Parameters: `camelCase_`


(feel free to change this, just keeping a record of what I've been doing)

## Directory structure
```
src-+
    |
    +--main+
           |
           +--java+
                  |
                  +--classes+
                  |         |
                  |         +--staff+
                  |         |       |
                  |         |       +--Intern.java
                  |         |       |
                  |         |       +--Mechanic.java
                  |         |       |
                  |         |       +--Salesperson.java
                  |         |       |
                  |         |       +--Staff.java
                  |         |
                  |         +--vehicles+
                  |         |          |
                  |         |          +--PerformanceCar.java
                  |         |          |
                  |         |          +--Pickup.java
                  |         |          |
                  |         |          +--RegularCar.java
                  |         |          |
                  |         |          +--Vehicle.java
                  |         |
                  |         +--Buyer.java
                  |         |
                  |         +--classes.Dealership.java
                  |   
                  +--Main.java     
```