package enums;

public enum Condition {
    BROKEN("Broken"),
    USED("Used"),
    LIKE_NEW("Like New");
    
    private String str;
    
    /**
     * @return the String representation of the Condition
     */
    Condition(String str_) {
        str = str_;
    }
    
    public String getStr() {
        return str;
    }
}
