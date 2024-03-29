# ooad-fncd

Spring 2023 Friendly Neighborhood Car Dealership project.

**Authors**: Jocelyn McHugo, Steven Wilmes

**Java Version**: 11

## UML Diagram

![](UMLDiagram.png)

#### Changes from 2.1

We changed quite a lot on the UML Class Diagram between Project 2.1 and 2.2. We changed how we were tracking which staff
members were employed or quit in an array instead of an attribute of the staff member. We consolidated the wash, repair,
and sell function in the dealership class into one 'work()' function. We added a report function, and ended up removing
a lot of the getters since the attributes are only used internally. For both staff and vehicles we changed the
attributes a bit to better match what we were doing. We also added Vehicle ID numbers to the vehicle class, which we had
forgotten to include at all in our first draft.

#### Changes from 3.1
We had some changes on the UML Diagram between Project 3.1 and 3.2. Most were relatively minor with some implementation details concerning parameters and functions for Tracker and Logger and Dealership. We also added the WashBehavior subclasses. For Project 3.1 we had a decent idea with what had to be done and a good baseline to start with, so there were no major structural changes

#### Changes from 4.1
We had some minor changes on the UML Diagram in Project 4.2.

1. We added the `UISell(Vehicle, Scanner)` to `Salesperson```
2. We move the static `names` and `stageNames` lists from the abstract classes to their resp``ective factories to head off any threading issues
3. We added a second `hireStaff()` method to `StaffFactory` to handle promoting interns
4. Most notably, we converted `Dealership` to an implementation of `Thread`, and added the `run()` method

### Testing
Testing was performed in the IDE by running each test class file, and results have been captured in the testOutputs pdf

## File Structure

```
ooad-fncd
|   .gitignore
│   build.gradle
│   gradlew
│   gradlew.bat
│   README.md
│   settings.gradle
│   SimResults.txt
│   UMLDiagram.png
|
├───.gradle
│   
├───.idea
│   
├───build
│   
├───gradle
|
├───logs
│       logs_README.md
│
└───src
    ├───main
    │   └───java
    │       │   Main.java
    │       │
    │       ├───classes
    │       │   │   Buyer.java
    │       │   │   Command.java
    │       │   │   Dealership.java
    │       │   │   Logger.java
    │       │   │   Tracker.java
    │       │   │
    │       │   ├───observerData
    │       │   │       RaceOutcome.java
    │       │   │       RepairOutcome.java
    │       │   │       SaleOutcome.java
    │       │   │       Tuple.java
    │       │   │       WashOutcome.java
    │       │   │
    │       │   ├───staff
    │       │   │   │   Driver.java
    │       │   │   │   Intern.java
    │       │   │   │   Mechanic.java
    │       │   │   │   Salesperson.java
    │       │   │   │   Staff.java
    │       │   │   │   StaffFactory.java
    │       │   │   │
    │       │   │   └───WashBehavior
    │       │   │           ChemicalWash.java
    │       │   │           DetailedWash.java
    │       │   │           ElbowGrease.java
    │       │   │           WashBehavior.java
    │       │   │
    │       │   └───vehicles
    │       │       │   CollectorCar.java
    │       │       │   ElectricCar.java
    │       │       │   MonsterTruck.java
    │       │       │   Motorcycle.java
    │       │       │   Motorhome.java
    │       │       │   PerformanceCar.java
    │       │       │   Pickup.java
    │       │       │   RegularCar.java
    │       │       │   Van.java
    │       │       │   Vehicle.java
    │       │       │   VehicleFactory.java
    │       │       │
    │       │       └───addon
    │       │               AddOnDecorator.java
    │       │               ExtendedWarranty.java
    │       │               RoadRescueCoverage.java
    │       │               SatelliteRadio.java
    │       │               Undercoating.java
    │       │
    │       └───enums
    │               Cleanliness.java
    │               Condition.java
    │               StaffType.java
    │               VehicleType.java
    │
    └───test
        └───java
                LoggerTest.java
```
