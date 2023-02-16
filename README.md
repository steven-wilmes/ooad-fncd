# ooad-fncd
Spring 2023 Friendly Neighborhood Car Dealership project

## UML Diagram
![](..\ooad-fncd\UMLDiagram.png)

#### Changes from 2.1

## OO Elements
Inheritance - Dealership:158

Polymorphism - Dealership:314

Cohesion - Dealership:147

Identity - Dealership:273

Encapsulation - Dealership:365

Abstraction - Vehicle:90
## File Structure
```
ooad-fncd
│   .gitignore
│   build.gradle
│   gradlew
│   gradlew.bat
│   README.md
│   settings.gradle
│   SimResults.txt
│
├───gradle
│   └───wrapper
│           gradle-wrapper.jar
│           gradle-wrapper.properties
│
├───logs
│       logs_README.md
│
└───src
    └───main
        └───java
            │   Main.java
            │
            ├───classes
            │   │   Buyer.java
            │   │   Dealership.java
            │   │
            │   ├───staff
            │   │       Intern.java
            │   │       Mechanic.java
            │   │       Salesperson.java
            │   │       Staff.java
            │   │
            │   └───vehicles
            │           PerformanceCar.java
            │           Pickup.java
            │           RegularCar.java
            │           Vehicle.java
            │
            └───enums
                    Cleanliness.java
                    Condition.java
                    VehicleType.java
```