package classes.vehicles.addon;

import classes.vehicles.Vehicle;

public class RoadRescueCoverage extends AddOnDecorator{
    Vehicle v;

    public RoadRescueCoverage(Vehicle v_){
        this.v = v_;
    }
    public String getStr(){
        return this.v.getStr() + ", road rescue coverage";
    }

    public double getSalesPrice(){
        return (this.v.getSalesPrice() * 1.02);
    }
}
