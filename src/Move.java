import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Move {

    String name;
    String type;
    int power;
    double accuracy;


    public Move(String name, String type, int power, double accuracy) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;

    }

    public String toString() {
            return String.format("Move Name: " + this.name +
                    ", Type: " + this.type +
                    ", Power: " + this.power +
                    ", Accuracy: " + this.accuracy);

    }
}
