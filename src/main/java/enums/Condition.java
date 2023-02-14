package enums;

public enum Condition {
    BROKEN("Broken"),
    USED("Used"),
    LIKE_NEW("Like New");
    
    private String str;
    Condition (String str_){ str = str_; }
    public String getStr(){return str;}
}
