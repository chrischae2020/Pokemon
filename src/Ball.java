import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Ball {

    final double ballRate;
    String name;

    public Ball(double ballRate) {
        this.ballRate = ballRate;
        this.name = "Ball";
    }

    public int captureRate(Pokemon pokemon) { // capture rate (int) of ball on pokemon
        return (int) ((pokemon.maxHP * 3 - pokemon.HP * 2 ) * pokemon.catchRate * this.ballRate) / (pokemon.maxHP * 3);
    }

    public int breakOutChance(Pokemon pokemon) { // chance of pokemon breaking out; <65535, then shake
        return (int) (1038560 / Math.sqrt((int) Math.sqrt(16711680 / this.captureRate(pokemon))));
    }

    public int numberOfShakes(Pokemon pokemon) {
        int counter = 0;
        while(true) {
            if (counter == 4) {
                break;
            }

            int random = ThreadLocalRandom.current().nextInt(0, 65535);
            if (random < breakOutChance(pokemon)) {
                counter++;
            } else {
                break;
            }
        }
        return counter;
    }

    public void shake(Pokemon pokemon) { // calculates shakes and message
        try {
            int shakes = numberOfShakes(pokemon);
            int counter = 0;
            Thread.sleep(1500);
            for (int i = 0; i < (shakes-1); i++) {
                System.out.println("The ball wobbled " + (counter + 1) + " times");
                Thread.sleep(1500);
                counter++;
            }
            if (shakes == 0 || shakes == 1) {
                System.out.println(pokemon.name + " broke out of the ball!");
                System.out.println("Oh no! The Pokemon broke free!");
            } else if (shakes == 2) {
                System.out.println(pokemon.name + " broke out of the ball!");
                System.out.println("Aww! It appeared to be caught!");
            } else if (shakes == 3) {
                System.out.println(pokemon.name + " broke out of the ball!");
                System.out.println("Aargh! Almost had it!");
            } else {
                System.out.println("Ding!");
                System.out.println("You've caught the wild " + pokemon.name + "!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
