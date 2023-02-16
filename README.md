# ooad-fncd

Spring 2023 Friendly Neighborhood Car Dealership project.

**Authors**: Jocelyn McHugo, Steven Wilmes

**Java Version**: 11

## UML Diagram

![](..\ooad-fncd\UMLDiagram.png)

#### Changes from 2.1

We changed quite a lot on the UML Class Diagram between Project 2.1 and 2.2. We changed how we were tracking which staff
members were employed or quit in an array instead of an attribute of the staff member. We consolidated the wash, repair,
and sell function in the dealership class into one 'work()' function. We added a report function, and ended up removing
a lot of the getters since the attributes are only used internally. For both staff and vehicles we changed the
attributes a bit to better match what we were doing. We also added Vehicle ID numbers to the vehicle class, which we had
forgotten to include at all in our first draft.

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