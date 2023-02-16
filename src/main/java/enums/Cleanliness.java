package enums;

public enum Cleanliness {
    DIRTY("Dirty"),
    CLEAN("Clean"),
    SPARKLING("Sparkling");
    
    private String str;
    
    /**
     * @return the String representation of the Cleanliness
     */
    Cleanliness(String str_) {
        str = str_;
    }
    
    public String getStr() {
        return str;
    }
}
