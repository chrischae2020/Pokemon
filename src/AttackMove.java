public class AttackMove extends Move {

    public AttackMove(String name, String type, int power, double accuracy) {
        super(name, type, power, accuracy);
        }

    // calculates type effectiveness multiplier (use only for attackmove)
    public double againstType(String type) { // calculates damage multiplier based on move type and opponent type
        switch(this.type) {
            case "NORMAL" :
                if ((type.equals("GHOST"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "FIGHTING" :
                if ((type.equals("NORMAL")) || (type.equals("ROCK")) || (type.equals("ICE")) || (type.equals("STEEL")) || (type.equals("DARK"))) {
                    return 2;
                } else if ((type.equals("FLYING")) || (type.equals("POISON")) || (type.equals("BUG")) || (type.equals("GHOST")) || (type.equals("PSYCHIC"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "FLYING" :
                if ((type.equals("FIGHTING")) || (type.equals("BUG")) || (type.equals("GRASS"))) {
                    return 2;
                } else if ((type.equals("ROCK")) || (type.equals("STEEL")) || (type.equals("ELECTRIC"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "POISON" :
                if ((type.equals("GRASS"))) {
                    return 2;
                } else if ((type.equals("POISON")) || (type.equals("GROUND")) || (type.equals("ROCK")) || (type.equals("GHOST")) || (type.equals("STEEL"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "GROUND" :
                if ((type.equals("POISON")) || (type.equals("ROCK")) || (type.equals("STEEL")) || (type.equals("FIRE")) || (type.equals("ELECTRIC"))) {
                    return 2;
                } else if ((type.equals("FLYING")) || (type.equals("BUG")) || (type.equals("GRASS"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "ROCK" :
                if ((type.equals("FLYING")) || (type.equals("BUG")) || (type.equals("FIRE")) || (type.equals("ICE"))) {
                    return 2;
                } else if ((type.equals("FIGHTING")) || (type.equals("GROUND")) || (type.equals("STEEL"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "BUG" :
                if ((type.equals("GRASS")) || (type.equals("PSYCHIC")) || (type.equals("DARK"))) {
                    return 2;
                } else if ((type.equals("FIGHTING")) || (type.equals("FLYING")) || (type.equals("POISON")) || (type.equals("GHOST")) || (type.equals("STEEL")) || (type.equals("FIRE"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "GHOST" :
                if ((type.equals("GHOST")) || (type.equals("PSYCHIC"))) {
                    return 2;
                } else if ((type.equals("NORMAL")) || (type.equals("STEEL")) || (type.equals("DARK"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "STEEL" :
                if ((type.equals("ROCK")) || (type.equals("ICE"))) {
                    return 2;
                } else if ((type.equals("STEEL")) || (type.equals("FIRE")) || (type.equals("WATER")) || (type.equals("ELECTRIC"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "FIRE" :
                if ((type.equals("BUG")) || (type.equals("STEEL")) || (type.equals("GRASS")) || (type.equals("ICE"))) {
                    return 2;
                } else if ((type.equals("ROCK")) || (type.equals("FIRE")) || (type.equals("WATER")) || (type.equals("DRAGON"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "WATER" :
                if ((type.equals("GROUND")) || (type.equals("ROCK")) || (type.equals("FIRE"))) {
                    return 2;
                } else if ((type.equals("WATER")) || (type.equals("GRASS")) || (type.equals("DRAGON"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "GRASS" :
                if ((type.equals("GROUND")) || (type.equals("ROCK")) || (type.equals("WATER"))) {
                    return 2;
                } else if ((type.equals("FLYING")) || (type.equals("POISON")) || (type.equals("BUG")) || (type.equals("STEEL")) || (type.equals("FIRE")) || (type.equals("GRASS")) || (type.equals("DRAGON"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "ELECTRIC" :
                if ((type.equals("FLYING")) || (type.equals("WATER"))) {
                    return 2;
                } else if ((type.equals("GROUND")) || (type.equals("GRASS")) || (type.equals("ELECTRIC")) || (type.equals("DRAGON"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "PSYCHIC" :
                if ((type.equals("FIGHTING")) || (type.equals("POISON"))) {
                    return 2;
                } else if ((type.equals("STEEL")) || (type.equals("PSYCHIC")) || (type.equals("DARK"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "ICE" :
                if ((type.equals("FLYING")) || (type.equals("GROUND")) || (type.equals("GRASS")) || (type.equals("DRAGON"))) {
                    return 2;
                } else if ((type.equals("STEEL")) || (type.equals("FIRE")) || (type.equals("WATER")) || (type.equals("ICE"))) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "DRAGON" :
                if (type.equals("DRAGON")) {
                    return 2;
                } else if (type.equals("STEEL")) {
                    return 0.5;
                } else {
                    return 1;
                }
            case "DARK" :
                if ((type.equals("GHOST")) || (type.equals("PSYCHIC"))) {
                    return 2;
                } else if ((type.equals("FIGHTING")) || (type.equals("STEEL")) || (type.equals("DARK"))) {
                    return 0.5;
                } else {
                    return 1;
                }
        }
        return 0;
    }

    public String toString() {
        return super.toString() + "Category: Physical/Special Attack" + "\n";
    }

}
