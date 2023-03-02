package classes.observerData;

public class Tuple {
    Object X;
    Object Y;
    
    public Tuple(Object x_, Object y_) {
        X = x_;
        Y = y_;
    }
    
    public Object getX() {
        return X;
    }
    
    public Object getY() {
        return Y;
    }
}
