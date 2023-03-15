package classes.staff;

import enums.StaffType;

public class StaffFactory {
    public StaffFactory(){}
    
    public Staff hireStaff(StaffType type_, Intern toPromote_){
        if (type_ != StaffType.MECHANIC && type_ != StaffType.SALESPERSON){
            main.Main.log("Interns can only be promoted to mechanics or salespeople");
            return hireStaff(type_);
        }else{
            return toPromote_.promote(type_ == StaffType.MECHANIC);
        }
    }
    
    public Staff hireStaff(StaffType type_){
        Staff newStaff = null;
        
        switch(type_){
            case DRIVER:
                newStaff = new Driver();
                break;
            case INTERN:
                newStaff = new Intern();
                break;
            case MECHANIC:
                newStaff = new Mechanic();
                break;
            case SALESPERSON:
                newStaff = new Salesperson();
        }
        
        return newStaff;
    }
}
