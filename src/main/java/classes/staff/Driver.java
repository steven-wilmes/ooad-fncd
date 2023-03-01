package classes.staff;

public class Driver extends Staff {

    int wins;
    public Boolean isInjured;

    public Driver(){
        super();
        this.wins = 0;
        this.isInjured = false;
    }

    public String getPosition() {
        return "Driver";
    }

    public void race(int position){
        double bonus = 500;
        switch(position){
            case 1:
            case 2:
            case 3:
                this.wins+=1;
                this.giveBonus(bonus);
                main.Main.log(String.format("Driver %s placed %d in the race! (earned $%.2f bonus).",
                        this.name,
                        position,
                        bonus));
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                if(rng.nextInt(10) < 3) {  //30% chance of being injured
                    this.isInjured = true;
                    main.Main.log(String.format("Driver %s placed %d in the race and got injured!",
                            this.name,
                            position));
                }
                break;
            default:
                main.Main.log(String.format("Driver %s placed %d in the race, how unremarkable.",
                        this.name,
                        position));
                break;
        }
    }
}
