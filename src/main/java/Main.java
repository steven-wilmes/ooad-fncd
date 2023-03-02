package main;

import classes.Dealership;

public class Main {
    
    @Deprecated
    public static void log(String text) {
        System.out.println(text);
    }
    
    public static void main(String[] args) {
        // initialize dealership
        Dealership FNCD = new Dealership();
        
        // daily loop
        for (int day = 0; day < 30; day++) {
            FNCD.day(day);
        }
        FNCD.report();
        
    }
}