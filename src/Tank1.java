public class Tank1 extends Droid {

    public Tank1(String name, int atk, int hp) {
        super(name, atk, hp);
        this.addAbility("Taunt");
        this.addAbility("Shield");
    }
}
