package classes.vehicles.addon;

import classes.vehicles.Vehicle;

public class Undercoating extends AddOnDecorator {
    Vehicle v;

    public Undercoating(Vehicle v_){
        this.v = v_;
    }
    public String getStr(){
        return this.v.getStr() + ", undercoating";
    }

    public double getSalesPrice(){
        return (this.v.getSalesPrice() * 1.05);
    }

    public double getCost() {return this.v.getCost();}
}
