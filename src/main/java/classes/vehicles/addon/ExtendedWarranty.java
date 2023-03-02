package classes.vehicles.addon;

import classes.vehicles.Vehicle;

public class ExtendedWarranty extends AddOnDecorator {
    Vehicle v;
    
    public ExtendedWarranty(Vehicle v_) {
        this.v = v_;
    }
    
    public String getStr() {
        return this.v.getStr() + ", extended warranty";
    }
    
    public double getSalesPrice() {
        return (this.v.getSalesPrice() * 1.2);
    }
    
    public double getCost() {
        return this.v.getCost();
    }

    public double getBonusAmount() {
        return this.v.getBonusAmount();
    }
    
}
