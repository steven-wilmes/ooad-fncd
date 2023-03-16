package main;

import classes.Dealership;

import java.util.concurrent.Semaphore;

public class Main {
    
    public static synchronized void log(String text) {
        System.out.println(text);
    }
    
    public static void main(String[] args) {
        Semaphore primary = new Semaphore(1);
        Semaphore secondary = new Semaphore(0);
        // initialize dealerships
        Dealership fncdNorth = new Dealership("North", primary, secondary);
        fncdNorth.start();
        Dealership fncdSouth = new Dealership("South", secondary, primary);
        fncdSouth.start();
        
    }
}