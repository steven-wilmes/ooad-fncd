package classes.staff;

import enums.StaffType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class StaffFactory {
    private ArrayList<String> names = new ArrayList<>(Arrays.asList("John", "William", "James", "Charles", "George", "Frank", "Joseph", "Thomas", "Henry", "Robert", "Edward", "Harry", "Walter", "Arthur", "Fred", "Albert", "Samuel", "David", "Louis", "Joe", "Charlie", "Clarence", "Richard", "Andrew", "Daniel", "Ernest", "Will", "Jesse", "Oscar", "Lewis", "Peter", "Benjamin", "Frederick", "Willie", "Alfred", "Sam", "Roy", "Herbert", "Jacob", "Tom", "Elmer", "Carl", "Lee", "Howard", "Martin", "Michael", "Bert", "Herman", "Jim", "Francis", "Harvey", "Earl", "Eugene", "Ralph", "Ed", "Claude", "Edwin", "Ben", "Charley", "Paul", "Edgar", "Isaac", "Otto", "Luther", "Lawrence", "Ira", "Patrick", "Guy", "Oliver", "Theodore", "Hugh", "Clyde", "Alexander", "August", "Floyd", "Homer", "Jack", "Leonard", "Horace", "Marion", "Philip", "Allen", "Archie", "Stephen", "Chester", "Willis", "Raymond", "Rufus", "Warren", "Jessie", "Milton", "Alex", "Leo", "Julius", "Ray", "Sidney", "Bernard", "Dan", "Jerry", "Calvin", "Perry", "Dave", "Anthony", "Eddie", "Amos", "Dennis", "Clifford", "Leroy", "Wesley", "Alonzo", "Garfield", "Franklin", "Emil", "Leon", "Nathan", "Harold", "Matthew", "Levi", "Moses", "Everett", "Lester", "Winfield", "Adam", "Lloyd", "Mack", "Fredrick", "Jay", "Jess", "Melvin", "Noah", "Aaron", "Alvin", "Norman", "Gilbert", "Elijah", "Victor", "Gus", "Nelson", "Jasper", "Silas", "Christopher", "Jake", "Mike", "Percy", "Adolph", "Maurice", "Cornelius", "Felix", "Reuben", "Wallace", "Claud", "Roscoe", "Sylvester", "Earnest", "Hiram", "Otis", "Simon", "Willard", "Irvin", "Mark", "Jose", "Wilbur", "Abraham", "Virgil", "Clinton", "Elbert", "Leslie", "Marshall", "Owen", "Wiley", "Anton", "Morris", "Manuel", "Phillip", "Augustus", "Emmett", "Eli", "Nicholas", "Wilson", "Alva", "Harley", "Newton", "Timothy", "Marvin", "Ross", "Curtis", "Edmund", "Jeff", "Elias", "Harrison", "Stanley", "Columbus", "Lon", "Ora", "Ollie", "Russell", "Pearl", "Solomon", "Arch", "Asa"));
    
    public StaffFactory(){}
    
    public Staff hireStaff(StaffType type_, Intern toPromote_){
        if (type_ != StaffType.MECHANIC && type_ != StaffType.SALESPERSON){
            main.Main.log("Interns can only be promoted to mechanics or salespeople");
            return hireStaff(type_);
        }else{
            return toPromote_.promote(type_ == StaffType.MECHANIC);
        }
    }
    
    public Staff hireStaff(StaffType type_){
        String name = names.get((new Random()).nextInt(names.size()));
        names.remove(name);
        Staff newStaff = null;
        
        switch(type_){
            case DRIVER:
                newStaff = new Driver(name);
                break;
            case INTERN:
                newStaff = new Intern(name);
                break;
            case MECHANIC:
                newStaff = new Mechanic(name);
                break;
            case SALESPERSON:
                newStaff = new Salesperson(name);
        }
        
        return newStaff;
    }
}
