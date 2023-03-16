package classes.observerData;

public class Tuple {
    Object X;
    Object Y;
    
    public Tuple(Object x_, Object y_) {
        X = x_;
        Y = y_;
    }
    
    /**
     * @return the first tuple element
     */
    public Object getX() {
        return X;
    }
    
    /**
     * @return the second tuple element
     */
    public Object getY() {
        return Y;
    }
}
