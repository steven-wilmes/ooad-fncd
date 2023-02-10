package classes.staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public abstract class Staff {
    static ArrayList<String> names = new ArrayList<>(Arrays.asList("John ", "William ", "James ", "Charles ", "George ", "Frank ", "Joseph ", "Thomas ", "Henry ", "Robert ", "Edward ", "Harry ", "Walter ", "Arthur ", "Fred ", "Albert ", "Samuel ", "David ", "Louis ", "Joe ", "Charlie ", "Clarence ", "Richard ", "Andrew ", "Daniel ", "Ernest ", "Will ", "Jesse ", "Oscar ", "Lewis ", "Peter ", "Benjamin ", "Frederick ", "Willie ", "Alfred ", "Sam ", "Roy ", "Herbert ", "Jacob ", "Tom ", "Elmer ", "Carl ", "Lee ", "Howard ", "Martin ", "Michael ", "Bert ", "Herman ", "Jim ", "Francis ", "Harvey ", "Earl ", "Eugene ", "Ralph ", "Ed ", "Claude ", "Edwin ", "Ben ", "Charley ", "Paul ", "Edgar ", "Isaac ", "Otto ", "Luther ", "Lawrence ", "Ira ", "Patrick ", "Guy ", "Oliver ", "Theodore ", "Hugh ", "Clyde ", "Alexander ", "August ", "Floyd ", "Homer ", "Jack ", "Leonard ", "Horace ", "Marion ", "Philip ", "Allen ", "Archie ", "Stephen ", "Chester ", "Willis ", "Raymond ", "Rufus ", "Warren ", "Jessie ", "Milton ", "Alex ", "Leo ", "Julius ", "Ray ", "Sidney ", "Bernard ", "Dan ", "Jerry ", "Calvin ", "Perry ", "Dave ", "Anthony ", "Eddie ", "Amos ", "Dennis ", "Clifford ", "Leroy ", "Wesley ", "Alonzo ", "Garfield ", "Franklin ", "Emil ", "Leon ", "Nathan ", "Harold ", "Matthew ", "Levi ", "Moses ", "Everett ", "Lester ", "Winfield ", "Adam ", "Lloyd ", "Mack ", "Fredrick ", "Jay ", "Jess ", "Melvin ", "Noah ", "Aaron ", "Alvin ", "Norman ", "Gilbert ", "Elijah ", "Victor ", "Gus ", "Nelson ", "Jasper ", "Silas ", "Christopher ", "Jake ", "Mike ", "Percy ", "Adolph ", "Maurice ", "Cornelius ", "Felix ", "Reuben ", "Wallace ", "Claud ", "Roscoe ", "Sylvester ", "Earnest ", "Hiram ", "Otis ", "Simon ", "Willard ", "Irvin ", "Mark ", "Jose ", "Wilbur ", "Abraham ", "Virgil ", "Clinton ", "Elbert ", "Leslie ", "Marshall ", "Owen ", "Wiley ", "Anton ", "Morris ", "Manuel ", "Phillip ", "Augustus ", "Emmett ", "Eli ", "Nicholas ", "Wilson ", "Alva ", "Harley ", "Newton ", "Timothy ", "Marvin ", "Ross ", "Curtis ", "Edmund ", "Jeff ", "Elias ", "Harrison ", "Stanley ", "Columbus ", "Lon ", "Ora ", "Ollie ", "Russell ", "Pearl ", "Solomon ", "Arch ", "Asa"));
    /**
     * randomly chosen name
     */
    String name;
    
    /**
     * cumulative salary earned
     */
    double totalSalary;
    /**
     * amount they get paid each day
     */
    double dailyPay;
    /**
     * cumulative bonuses earned
     */
    double bonusEarned;
    /**
     * total days worked
     */
    int daysWorked;
    /**
     * currently employed
     */
    boolean employed;
    
    /**
     * random seed for the class
     */
    Random rng;
    
    /**
     * Superclass constructor, chooses {@link #name} and creates {@link #rng}. Sets cumulative values to 0.
     */
    public Staff() {
        rng = new Random();
        name = names.get(rng.nextInt(names.size()));
        totalSalary = 0;
        bonusEarned = 0;
        daysWorked = 0;
        employed = true;
    }
    
    /**
     * Increases {@link #bonusEarned}
     *
     * @param bonus_ amount to add
     */
    public void giveBonus(double bonus_) {
        if (bonus_ >= 0) {
            bonusEarned += bonus_;
        }
    }
    
    /**
     * Daily closeout method.
     * <p>
     * Increments {@link #daysWorked}, increments {@link #totalSalary} by one {@link #dailyPay}, decides whether to
     * quit
     */
    public void workDay() {
        daysWorked++;
        totalSalary += dailyPay;
        if (rng.nextInt(10) == 0) { // 10% chance
            employed = false; // quit
        }
    }
    
    /**
     * Gets cumulative pay
     *
     * @return {@link #totalSalary}
     */
    public double getTotalSalary() {
        return totalSalary;
    }
    
    /**
     * Gets amount of pay per day
     *
     * @return {@link #dailyPay}
     */
    public double getDailyPay() {
        return dailyPay;
    }
    
    /**
     * Gets cumulative bonuses
     *
     * @return {@link #bonusEarned}
     */
    public double getBonusEarned() {
        return bonusEarned;
    }
    
    /**
     * Gets total days worked
     *
     * @return {@link #daysWorked}
     */
    public int getDaysWorked() {
        return daysWorked;
    }
    
    /**
     * Gets name
     *
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }
}
