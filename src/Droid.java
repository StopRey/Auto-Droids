import java.util.*;

public class Droid {
    private String name;
    private int dmg;
    private int hp;
    private List<String> abilities = new ArrayList<>();

    public Droid(String name, int dmg, int hp) {
        this.name = name;
        this.dmg = dmg;
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public int getDmg() {
        return dmg;
    }

    public int getHp() {
        return hp;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }




    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public void addAbility(String ability) {
        this.abilities.add(ability);
    }

    public String toString(){
        return "Name: " + this.name + ", Attack: " + this.dmg + ", Health: " + this.hp + ", Abilities: " + oneLine(this.abilities) + "\n";
    }

    public String oneLine(List<String> abilities){
        return String.join(", ", abilities);
    }

    public void deleteAbility(String abilityDel){
        int i=0;
        for (String ability : this.abilities) {
            if(ability.equals(abilityDel)){
                List<String> copy = this.getAbilities();
                copy.set(i, "");
                this.setAbilities(copy);
            }
            i++;
        }
    }

    public boolean checkRat(){
        for (String ability : this.abilities) {
            if(ability.equals("Rat")){
                return true;
            }
        }
        return false;
    }

    public boolean checkTaunt(){
        for (String ability : this.abilities) {
            if(ability.equals("Taunt")){
                return true;
            }
        }
        return false;
    }
    public boolean checkShield(){
        for (String ability : this.abilities) {
            if(ability.equals("Shield")){
                return true;
            }
        }
        return false;
    }
    public boolean checkDoubler(){
        for (String ability : this.abilities) {
            if(ability.equals("Doubler")){
                return true;
            }
        }
        return false;
    }
    public boolean checkWind(){
        for (String ability : this.abilities) {
            if(ability.equals("Wind")){
                return true;
            }
        }
        return false;
    }
}
