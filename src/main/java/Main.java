package main;

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
        flName = String.format("%s%sooad-fncd%slogs%sFNCD_%d.txt", Paths.get("").toAbsolutePath().toString(), File.separator, File.separator, File.separator, Instant.now().toEpochMilli());
        File fl = new File(flName);
        try {
            fl.createNewFile();
        } catch (IOException e) {
            System.out.println("File creation failed.");
        }
        log("Hello, world!");
    }
}