package main;

import classes.Dealership;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;

public class Main {
    public static String flName;
    public static void log(String text){
        System.out.println(text);
        try {
            FileWriter writer = new FileWriter(flName);
            writer.write(text);
            writer.close();
        } catch (IOException e){
            System.out.println("File write failed");
        }
    }
    public static void main(String[] args) {
        // initialize logger
        flName = String.format("%s%sooad-fncd%slogs%sFNCD_%d.txt", Paths.get("").toAbsolutePath().toString(), File.separator, File.separator, File.separator, Instant.now().toEpochMilli());
        File fl = new File(flName);
        try {
            fl.createNewFile();
        } catch (IOException e) {
            System.out.println("File creation failed.");
        }
        
        // initialize dealership
        Dealership FNCD = new Dealership();
        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        
        // daily loop
        for (int day=0; day < 30; day++){
            log(String.format("It is %s", days[day%7]));
            if (day%7==6){ // sunday
                log("The FNCD is closed.");
            }else{
                FNCD.open();
                FNCD.wash();
                FNCD.repair();
                FNCD.sell();
                FNCD.end();
            }
        }
    }
}