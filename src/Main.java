import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static int numDroidsAll = 5;
    public static int numDroids = 7;
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        Droid[] DroidsAll = new Droid[numDroidsAll];
        allDroids(DroidsAll);
        showAllDroids(DroidsAll);

        Droid[] Droids1 = new Droid[numDroids];
        Droid[] Droids2 = new Droid[numDroids];
        createArrray(Droids2, DroidsAll, scanner);
        createRandomArrray(Droids1, DroidsAll);

        startGame(DroidsAll, Droids1, Droids2, scanner);

    }

    public static int nextAttack(int num, Droid[] DroidsOp){
        num++;
        if(num==7){
            num=0;
        }
        while(DroidsOp[num].getHp()==0){
            num++;
            if(num==7){
                num=0;
            }
        }
        return num;
    }


    public static void startGame(Droid[] DroidsAll, Droid[] Droids1, Droid[] Droids2, Scanner scanner) throws InterruptedException {
        Random random = new Random();
        showTable(Droids1,Droids2);
        pressToStart(scanner);
        int numAtk1=6;
        int numAtk2=6;
        while(checkAlive(Droids1, Droids2)) {
            Thread.sleep(3000);
            numAtk1=nextAttack(numAtk1,Droids1);
            System.out.println("\nPlayer 1 attacking:");
            attack(numAtk1, random.nextInt(7), Droids1, Droids2);
            showTable(Droids1,Droids2);
            if(!checkAlive(Droids1, Droids2)){
                break;
            }
            Thread.sleep(3000);
            numAtk2=nextAttack(numAtk2,Droids2);
            System.out.println("\nPlayer 2 attacking:");
            attack(numAtk2, random.nextInt(7), Droids2, Droids1);
            showTable(Droids1,Droids2);
        }

        checkWinner(Droids1, Droids2);
    }

    public static void attack(int numAtk, int numDef,Droid[] Droids1, Droid[] Droids2){
        Random random = new Random();
        if(checkCanAttack(numAtk, numDef, Droids1, Droids2)){
            System.out.println("\n" + numAtk+" Attacked "+numDef);
            attacking(numAtk,numDef,Droids1, Droids2);
        }
        else{
            attack(numAtk, random.nextInt(7), Droids1, Droids2);

        }
        if(Droids1[numAtk].checkWind()){
            Droids1[numAtk].deleteAbility("Wind");
            if(checkAlive(Droids1, Droids2)){
                attack(numAtk, random.nextInt(7), Droids1, Droids2);
            }
        }
    }

    public static void attacking(int numAtk, int numDef,Droid[] Droids1, Droid[] Droids2){
        if(Droids1[numAtk].checkDoubler()){
            Droids1[numAtk].setDmg(Droids1[numAtk].getDmg()*2);
        }
        int atk1 = Droids1[numAtk].getDmg();
        int atk2 = Droids2[numDef].getDmg();

        if(Droids1[numAtk].checkShield()){
            Droids1[numAtk].deleteAbility("Shield");
        }
        else{
            dealDmg(atk2, numAtk, Droids2, Droids1);
        }
        if(Droids2[numDef].checkShield()){
            Droids2[numDef].deleteAbility("Shield");
        }
        else{
            dealDmg(atk1, numDef, Droids1, Droids2);
        }
        deleteDrone(numAtk,Droids1);
        deleteDrone(numDef,Droids2);
    }

    public static void deleteDrone(int num, Droid[] Droids){
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        if(Droids[num].getHp()==0){
            Droids[num].setName(RED + "GG" + RESET) ;
            Droids[num].setDmg(0);
            Droids[num].setAbilities(new ArrayList<>(List.of("")));
        }
    }

    public static void dealDmg(int atk, int numDef,Droid[] Droids1, Droid[] Droids2){
        if(atk>Droids2[numDef].getHp()){
            Droids2[numDef].setHp(0);
        }
        else{
            Droids2[numDef].setHp(Droids2[numDef].getHp()-atk);
        }
    }

    public static boolean checkCanAttack(int numAtk, int numDef,Droid[] Droids1, Droid[] Droids2){
        if(Droids2[numDef].getHp()==0){
            return false;
        }
        if(Droids1[numAtk].checkRat()){
            return true;
        }
        if(Droids2[numDef].checkTaunt()) {
            return true;
        }
        else{
            for (int i = 0; i < numDroids; i++) {
                if (i != numDef) {
                    if(Droids2[i].checkTaunt()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void checkWinner(Droid[] Droids1, Droid[] Droids2){
        for(int i=0;i<numDroids;i++){
            if(Droids1[i].getHp()!=0){
                System.out.println("\nBot win");
                return;
            }
        }
        for(int i=0;i<numDroids;i++){
            if(Droids2[i].getHp()!=0){
                System.out.println("\nPlayer win");
                return;
            }
        }
        System.out.println("\nDraw");
    }

    public static boolean checkAlive(Droid[] Droids1, Droid[] Droids2){
        int count1 = 0;
        int count2 = 0;
        for(int i=0;i<numDroids;i++){
            if(Droids1[i].getHp()!=0){
                count1++;
            }

            if(Droids2[i].getHp()!=0){
                count2++;
            }
        }

        if(count2==0 || count1==0){
            return false;
        }
        return true;
    }

    public static void showTable(Droid[] Droids1, Droid[] Droids2){
        showDroids(Droids1);
        centerLine();
        showDroids(Droids2);
    }

    public static void allDroids(Droid[] DroidsAll){
        Random random = new Random();
        for(int i=0;i<numDroidsAll;i++){
            DroidsAll[i] = createcopy(i+1);
        }
    }

    public static Droid createcopy(int num){
        Random random = new Random();
        int rNum = random.nextInt(6);
        switch(num){
            case 1:
                return new Tank1("Tank1",15+rNum,12-rNum);
            case 2:
                return new Sniper1("Sniper1",12+rNum,12-rNum);
            case 3:
                return new Killer1("Killer1",14+rNum,18-rNum);
            case 4:
                return new Undead1("Undead1",23+rNum,13-rNum);
            case 5:
                return new Pusher1("Pusher1",17+rNum,17-rNum);
        }
        return new Tank1("Tank1",15+rNum,12-rNum);
    }

    public static void showAllDroids(Droid[] DroidsAll){
        System.out.println("List of all Droids:");
        for(int i=0;i<numDroidsAll; i++){
            System.out.printf( i+1 + ". " + DroidsAll[i].toString());
        }
    }

    public static void createArrray(Droid[] Droids2, Droid[] DroidsAll, Scanner scanner){
        System.out.println("Choose 7 Droids(Enter sequence number):");
        for(int i=0;i<numDroids;i++){
            while(true){
                int num = scanner.nextInt();
                if(num>=1 && num<=numDroidsAll){
                    Droids2[i] = createcopy(num);
                    break;
                }
                else{
                    System.out.println("Enter again");
                }
            }
        }
    }

    public static void createRandomArrray(Droid[] Droids1, Droid[] DroidsAll ){
        Random random = new Random();
        int rNum;
        for(int i=0;i<numDroids;i++){
            rNum = random.nextInt(numDroidsAll);
            Droids1[i] = createcopy(rNum+1);
        }

    }

    public static void pressToStart(Scanner scanner){
        while(true) {
            System.out.print("\nPress 1 to start:");
            if(scanner.nextInt() == 1){
                return;
            }
        }
    }

    public static void centerLine(){
        System.out.print("\n _____________________________________________________________________________________________ \n");
    }


    public static void showDroids(Droid[] Droids1){
        String RESET = "\u001B[0m";
        String YELLOW = "\u001B[33m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        for(int i=0;i<numDroids;i++){
            System.out.print(" _________ " + "   ");
        }

        System.out.print("\n");

        for(int i=0;i<numDroids;i++){
            System.out.print("/         \\" + "   ");
        }

        System.out.print("\n");

        for(int i=0;i<numDroids;i++){
            System.out.print("|" + Droids1[i].getName());
            if(Droids1[i].getName().length()>9) {
                System.out.print("       ");
            }
            else{
                for (int j = 0; j < 9 - Droids1[i].getName().length(); j++) {
                    System.out.print(" ");
                }
            }
            System.out.print("|" + "   ");
        }

        System.out.print("\n");

        for(int i=0;i<numDroids;i++){
            System.out.print("|_________|" + "   ");
        }

        System.out.print("\n");

        for(int i=0;i<numDroids;i++){
            if(Droids1[i].getAbilities().isEmpty()) {
                System.out.print("|         |" + "   ");
            }
            else{
                if ((Droids1[i].getAbilities()).get(0) != null) {
                    System.out.print("|" + GREEN + Droids1[i].getAbilities().get(0) + RESET);
                    for (int j = 0; j < 9 - Droids1[i].getAbilities().get(0).length(); j++) {
                        System.out.print(" ");
                    }
                    System.out.print("|" + "   ");
                }
            }
        }

        System.out.print("\n");

        for(int i=0;i<numDroids;i++){
            if(Droids1[i].getAbilities().size() >=2 && (Droids1[i].getAbilities()).get(1) != null) {
                System.out.print("|" + GREEN + Droids1[i].getAbilities().get(1) + RESET);
                for (int j = 0; j < 9 - Droids1[i].getAbilities().get(1).length(); j++) {
                    System.out.print(" ");
                }
                System.out.print("|" + "   ");
            }
            else{
                System.out.print("|         |" + "   ");
            }
        }

        System.out.print("\n");

        for(int i=0;i<numDroids;i++){
            System.out.print("|_________|" + "   ");
        }

        System.out.print("\n");

        for(int i=0;i<numDroids;i++){
            if(Droids1[i].getDmg()>9){
                System.out.print("| " + YELLOW + Droids1[i].getDmg() + RESET + " | ");
            }
            else{
                System.out.print("| " + YELLOW + "0" + Droids1[i].getDmg() + RESET + " | ");
            }

            if(Droids1[i].getHp()>9){
                System.out.print(RED + Droids1[i].getHp() + RESET + " |" + "   ");
            }
            else{
                System.out.print(RED + "0" + Droids1[i].getHp() + RESET + " |" + "   ");
            }
        }

        System.out.print("\n");

        for(int i=0;i<numDroids;i++){
            System.out.print("\\____|____/" + "   ");
        }

    }

}
