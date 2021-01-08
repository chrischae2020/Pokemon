import jdk.jshell.Snippet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class Pokemon {

    String name;
    int level;
    String type1;
    String type2;
    TreeMap<Integer,Move> moves;

    int baseHP;
    int baseAttack;
    int baseDefense;
    int baseSpeed;

    int HP;
    int maxHP;
    int attack;
    int defense;
    int speed;

    int stageAttack;
    int stageDefense;
    int stageSpeed;
    int stageAccuracy;
    int stageEvasion;

    int exp;
    int expNext;

    int catchRate;
    boolean caught;

    boolean fainted;

    // single type pokemon
    public Pokemon(String name, String type, TreeMap<Integer,Move> moves, int baseHP, int baseAttack, int baseDefense, int baseSpeed, int catchRate, int level) {
        this.name = name;
        this.level = level;
        this.type1 = type;
        this.type2 = null;
        this.moves = moves;

        this.baseHP = baseHP;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;

        this.catchRate = catchRate;
        this.caught = false;

        this.HP = (int) ((2 * this.baseHP * this.level / 100) + this.level + 10);
        this.maxHP = (int) ((2 * this.baseHP * this.level / 100) + this.level + 10);
        this.attack = (int) ((2 * this.baseAttack * this.level / 100) + 5);
        this.defense = (int) ((2 * this.baseDefense * this.level / 100) + 5);
        this.speed = (int) ((2 * this.baseSpeed * this.level / 100) + 5);

        this.stageAttack = 0;
        this.stageDefense = 0;
        this.stageSpeed = 0;
        this.stageAccuracy = 0;
        this.stageEvasion = 0;

        if (this.level == 1) {
            this.exp = 0;
        } else {
            this.exp = (int) (Math.pow((this.level-1), 3) - (this.level-1));
        }
        if (this.level == 1) {
            this.expNext = 2;
        } else {
            this.expNext = (int) (Math.pow(this.level, 3) - this.level);
        }
        this.fainted = false;

    }

    // two type pokemon
    public Pokemon(String name, String type1, String type2, TreeMap<Integer,Move> moves, int baseHP, int baseAttack, int baseDefense, int baseSpeed, int catchRate, int level) {
        this.name = name;
        this.level = level;
        this.type1 = type1;
        this.type2 = type2;
        this.moves = moves;

        this.baseHP = baseHP;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;

        this.catchRate = catchRate;
        this.caught = false;

        this.HP = (int) ((2 * this.baseHP * this.level / 100) + this.level + 10);
        this.maxHP = (int) ((2 * this.baseHP * this.level / 100) + this.level + 10);
        this.attack = (int) ((2 * this.baseAttack * this.level / 100) + 5);
        this.defense = (int) ((2 * this.baseDefense * this.level / 100) + 5);
        this.speed = (int) ((2 * this.baseSpeed * this.level / 100) + 5);

        this.stageAttack = 0;
        this.stageDefense = 0;
        this.stageSpeed = 0;
        this.stageAccuracy = 0;
        this.stageEvasion = 0;

        if (this.level == 1) {
            this.exp = 0;
        } else {
            this.exp = (int) (Math.pow((this.level-1), 3) - (this.level-1));
        }
        if (this.level == 1) {
            this.expNext = 2;
        } else {
            this.expNext = (int) (Math.pow(this.level, 3) - this.level);
        }
        this.fainted = false;
    }

    /*
    Functions to be used in GS
    */

    // Pokemon learns new move
    public void learnNewMove(Move move) {
        int numberOfMoves = this.moves.size();
        if (numberOfMoves < 3) {
            this.moves.put(numberOfMoves+1, move);
            System.out.println(this.name + " learned " + move.name + "!");
        } else {
            System.out.println(this.name + " would like to learn a new move");
            System.out.println("Which move would you like to replace?");
            showAllMoves();
            if (move instanceof AttackMove) {
                System.out.println("4 (New move) --" + ((AttackMove) move).toString());
            } else if (move instanceof StatusMove){
                System.out.println("4 (New move) --" + ((StatusMove) move).toString());
            }

            Scanner scanner = new Scanner(System.in);
            while (true) {
                int moveSlot = scanner.nextInt();
                if (moveSlot == 1) {
                    System.out.println("Replacing move " + this.moves.get(1) +" with " + move.name);
                    this.moves.replace(1, move);
                    System.out.println(this.name + " learned " + move.name + "!");
                    break;
                } else if (moveSlot == 2) {
                    System.out.println("Replacing move 2 " + this.moves.get(2) + " with " + move.name);
                    this.moves.replace(2, move);
                    System.out.println(this.name + " learned " + move.name + "!");
                    break;
                } else if (moveSlot == 3) {
                    System.out.println("Replacing move 3 " + this.moves.get(3) + " with " + move.name);
                    this.moves.replace(3, move);
                    System.out.println(this.name + " learned " + move.name + "!");
                    break;
                } else if (moveSlot == 4){
                    System.out.println("You chose not to learn the move " + move.name);
                    break;
                } else {
                    System.out.println("Chose a valid move slot (1,2,3,4)");
                }
            }
        }
    }

    // Calculates what multiplier damage does to a pokemon based on their types (4x,2x,1x,0.5x,0.25x)
    public static double damageToTypeMultiplier(Pokemon opponent, AttackMove move) {
        if (opponent.type2 != null) {
            return (move.againstType(opponent.type1) * move.againstType(opponent.type2));
        } else {
            return move.againstType(opponent.type1);
        }
    }

    // attacker attacks with move 'move', attacker should be speedier
    public static void attack(Pokemon attacker, Pokemon defender, Move move) { // Attack function, attacker is speedier
        Random random = new Random();
        double hitChance = random.nextDouble();
        //Move move = attacker.moves.get(1);

        System.out.println(attacker.name + " used " + move.name + "!");

        if (move instanceof StatusMove && ((StatusMove) move).self) { // self status move

            ((StatusMove) move).calculateStageChange(attacker);

        } else // not self move

            if ((move.accuracy * Pokemon.stageAccuracyMultiplier(attacker.stageAccuracy, defender.stageEvasion) >= hitChance)) { // Move lands

                if (move instanceof AttackMove) { // move is attack move

                    double effectiveness = Pokemon.damageToTypeMultiplier(defender, (AttackMove) move);
                    if (effectiveness >= 2.0) {
                        System.out.println("Super effective!");
                    } else if (effectiveness <= 0.5) {
                        System.out.println("Not very effective ...");
                    }
                    double criticalHit = random.nextDouble();
                    if (criticalHit <= 0.0625) { // critical hit
                        System.out.println("The move was a critical hit!");
                        double criticalMultiplier = ThreadLocalRandom.current().nextDouble(1.9, 2.2);
                        int criticalDamage = (int) (attacker.damageDealt(defender, (AttackMove) move) * criticalMultiplier * effectiveness);
                        System.out.println(defender.name + " took " + criticalDamage + " damage!");
                        defender.takeDamage(criticalDamage);
                    } else { // regular hit
                        int regularDamage = (int) (attacker.damageDealt(defender, (AttackMove) move) * effectiveness);
                        System.out.println(defender.name + " took " + regularDamage + " damage!");
                        defender.takeDamage(regularDamage);
                    }
                } else { // move is status attack
                    ((StatusMove) move).calculateStageChange(defender);
                }

            } else { // move is a miss
                System.out.println(attacker.name + "'s move missed!");
            }
        }


    /*
    Helper functions
    */

    // from a new stage (-6 to 6), compute the new multiplier to the effective stat (not base stat)
    public static double stageStatMultiplier(int stage) {
        int number = 2;
        if (stage > 0) {
            return (number + stage) / 2;
        } else if (stage < 0) {
            return number / (2 - stage);
        } else {
            return 1;
        }
    }

    // computes  multiplier for move hitting based on attacker accuracy and opponent evasion
    public static double stageAccuracyMultiplier(int accuracy, int evasion) {
        int stage = accuracy - evasion;
        int number = 3;
        if (stage > 0) {
            return (number + stage) / 3;
        } else if (stage < 0) {
            return 3 / (3 - stage);
        } else {
            return 1;
        }
    }

    // calculates the amount of damage a pokemon does to another (includes stageAttack of attacker and stageDefense of opponent)
    public int damageDealt(Pokemon opponent, AttackMove move) {
        double otherMultiplier = ThreadLocalRandom.current().nextDouble(0.85, 1.3);
        int dmg = (int) (((((2*this.level/5)+2)*move.power*this.attack/opponent.defense/50)+2)*otherMultiplier*(stageStatMultiplier(this.stageAttack)/stageStatMultiplier(opponent.stageDefense)));
        return dmg;
    }

    // Shows all moves learned by pokemon
    public void showAllMoves() {
        System.out.println("List of moves for " + this.name);
        for (Map.Entry<Integer,Move> entry : this.moves.entrySet()) {
            Integer key = entry.getKey();
            Move value = entry.getValue();
            if (value instanceof AttackMove) {
                System.out.println(key + "--" + ((AttackMove) value).toString());
            } else if (value instanceof StatusMove){
                System.out.println(key + "--" + ((StatusMove) value).toString());
            }
        }
    }

    // Get random move
    public Move randomMove() {
        int moveNumber = ThreadLocalRandom.current().nextInt(1, this.moves.size()+1);
        return getMove(moveNumber);
    }

    /*
    Winning battle
     */

    // calculates stat from base
    public int baseToStat(String base, int baseStat) {
        if (base.equals("HP")) {
            return (int) (((2 * baseStat * this.level) / 100 + this.level + 10) - ((2 * baseStat * (this.level-1)) / 100 + (this.level-1) + 10));
        } else {
            return (int) (((2 * baseStat * this.level) / 100 + 5) - ((2 * baseStat * (this.level-1)) / 100 + 5));
        }
    }

    // Stat additions after level up
    public void addStats() {
        Random stat = new Random();

        if (this.maxHP > this.speed) {
            int newHp1 = stat.nextInt(2) + baseToStat("HP", this.baseHP);
            int newSpeed1 = stat.nextInt(1) + baseToStat("Speed", this.baseSpeed);

            System.out.println("New max HP: +" + newHp1);
            System.out.println("New speed: +" + newSpeed1);
            this.maxHP = this.maxHP + newHp1;
            this.speed = this.speed + newSpeed1;
            this.HP = this.maxHP;
            ;
        } else {
            int newHp2 = stat.nextInt(1) + baseToStat("HP", this.baseHP);
            int newSpeed2 = stat.nextInt(2) + baseToStat("Speed", this.baseSpeed);

            System.out.println("New max HP: +" + newHp2);
            System.out.println("New speed: +" + newSpeed2);
            this.maxHP = this.maxHP + newHp2;
            this.speed = this.speed + newSpeed2;
            this.HP = this.maxHP;
        }

        if (this.attack > this.defense) {
            int newAttack1 = stat.nextInt(2) + baseToStat("Attack", this.baseAttack);
            int newDefense1 = stat.nextInt(1) + baseToStat("Defense", this.baseDefense);

            System.out.println("New attack: +" + newAttack1);
            System.out.println("New defense: +" + newDefense1);
            this.attack = this.attack + newAttack1;
            this.defense = this.defense + newDefense1;
        } else {
            int newAttack2 = stat.nextInt(1) + baseToStat("Attack", this.baseAttack);
            int newDefense2 = stat.nextInt(2) + baseToStat("Defense", this.baseDefense);

            System.out.println("New attack: +" + newAttack2);
            System.out.println("New defense: +" + newDefense2);
            this.attack = this.attack + newAttack2;
            this.defense = this.defense + newDefense2;
        }
        System.out.println("\n");
    }

    // calculates stat and exp gain from battle
    public void wonBattle(Pokemon opponent) {
        //System.out.println("You defeated " + opponent.name);
        System.out.println(this.name+" gained " + (opponent.exp + opponent.level * 2) + " experience points!");
        this.exp = this.exp + (opponent.exp + opponent.level * 2);
        while (this.exp >= this.expNext) {
            this.level++;
            System.out.println(this.name + " leveled up to level " + this.level + "!");
            System.out.println("\n");
            if (this.level % 5 == 0) {
                if (this.type2 == null) { // one type
                    learnNewMove(createMove(newMoveString1(this.type1)));
                } else { // two type
                    learnNewMove(createMove(newMoveString2(this.type1, this.type2)));
                }
            }
            addStats();
            this.expNext = (int) (Math.pow(this.level, 3) - this.level);

        }
    }

    // returns total number of lines in file given name (Pokedex, NORMAL, etc.)
    public int linesInFile(String fileName) { // returns total number of lines in file given name (Pokedex, NORMAL, etc.)
        try {
            if (fileName.equals("Pokedex")) {
                Scanner file = new Scanner(new File("C:\\Users\\flare\\Pokemon\\Pokedex.txt"));
                int count = 0;
                while (file.hasNext()) {
                    count++;
                    file.nextLine();
                }
                return count;
            } else {
                String address = "C:\\Users\\flare\\Pokemon\\Moves\\" + fileName + ".txt";
                Scanner file = new Scanner(new File(address));
                int count = 0;
                while (file.hasNext()) {
                    count++;
                    file.nextLine();
                }
                return count;
            }

        } catch (IOException e) {
            System.out.println("Cannot get file");
            e.printStackTrace();
        }
        return 0;
    }

    // generates a random moveString based on type 1
    public String newMoveString1(String pokemonType) {
        try {
            double moveTypeChance = new Random().nextDouble();
            if (moveTypeChance <= 0.6) { // Move equals the pokemon type
                int moveNumber = ThreadLocalRandom.current().nextInt(1, (linesInFile(pokemonType) + 1));
                String str = Files.lines(Paths.get("C:\\Users\\flare\\Pokemon\\Moves\\" + pokemonType + ".txt")).skip(moveNumber - 1).findFirst().get();
                return str;
            } else { // choose normal type move
                int moveNumber = ThreadLocalRandom.current().nextInt(1, (linesInFile("NORMAL") + 1));
                String str = Files.lines(Paths.get("C:\\Users\\flare\\Pokemon\\Moves\\NORMAL.txt")).skip(moveNumber - 1).findFirst().get();
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "TEST,TEST,10,10,10,10,1";
        }
    }

    // generates a random moveString based on two types
    public String newMoveString2(String type1, String type2) {
        try {
            double moveTypeChance = new Random().nextDouble();
            if (moveTypeChance <= 0.35) { // Type1 move
                int moveNumber = ThreadLocalRandom.current().nextInt(1, (linesInFile(type1) + 1));
                String str = Files.lines(Paths.get("C:\\Users\\flare\\Pokemon\\Moves\\" + type1 + ".txt")).skip(moveNumber - 1).findFirst().get();
                return str;
            } else if (moveTypeChance >= 0.65) { // type2 move
                int moveNumber = ThreadLocalRandom.current().nextInt(1, (linesInFile(type2) + 1));
                String str = Files.lines(Paths.get("C:\\Users\\flare\\Pokemon\\Moves\\" + type2 + ".txt")).skip(moveNumber - 1).findFirst().get();
                return str;
            } else { // normal type move
                int moveNumber = ThreadLocalRandom.current().nextInt(1, (linesInFile("NORMAL") + 1));
                String str = Files.lines(Paths.get("C:\\Users\\flare\\Pokemon\\Moves\\NORMAL.txt")).skip(moveNumber - 1).findFirst().get();
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "TEST,TEST,TEST,10,10,10,10,1";
        }
    }

    // takes in newMoveString to generate Attack/Status move
    public Move createMove(String moveString) {
        String[] moveStats = moveString.split(",");
        if (moveStats.length == 4) {
            String name = moveStats[0];
            String type = moveStats[1];
            int power = Integer.parseInt(moveStats[2]);
            double accuracy = Double.parseDouble(moveStats[3]);
            return new AttackMove(name, type, power, accuracy);
        } else {
            String name = moveStats[0];
            String type = moveStats[1];
            String stageName = moveStats[2];
            int amount = Integer.parseInt(moveStats[3]);
            boolean self = Boolean.parseBoolean(moveStats[4]);
            return new StatusMove(name, type, stageName, amount, self);
        }
    }


    // setters
    public void setFaint() { // Sets HP to zero
        this.HP = 0;
    }
    // sets new hp after taking damage
    public void takeDamage(int damage) { // Calculates the new HP after taking damage
        this.HP = this.HP - damage;
        if (this.HP < 0) {
            this.setFaint();
        }
    }
    // Sets the stage stat to the amount 'amount'
    public void setStageStat(String stage, int amount) {
        if (stage == "Attack") {
            this.stageAttack = amount;
        } else if (stage == "Defense") {
            this.stageDefense = amount;
        } else if (stage == "Speed") {
            this.stageSpeed = amount;
        } else if (stage == "Accuracy") {
            this.stageAccuracy = amount;
        } else if (stage == "Evasion") {
            this.stageEvasion = amount;
        } else {
            System.out.println("Unknown stage name");
        }
    }
    // reset the stages to 0
    public void resetStages() {
        this.stageAttack = 0;
        this.stageDefense = 0;
        this.stageSpeed = 0;
        this.stageAccuracy = 0;
        this.stageEvasion = 0;
    }

    // getters
    public Move getMove(int slot) {
        return this.moves.get(slot);
    }

    public int getCatchRate() {
        return this.catchRate;
    }

    public int getHP() {
        return this.HP;
    }

    public int getMaxHP() {
        return this.maxHP;
    }

    public boolean isFainted() { return this.fainted; }

    // heals by certain HP
    public void healHP(int number) {
        this.HP = this.HP + number;
    }

    // Lowers HP by a certain amount
    public void lowerHP(int hp) {
        if ((this.HP - hp) <= 0 ) {
            this.HP = 1;
        } else {
            this.HP -= hp;
        }
        System.out.println(this.name + "'s new HP: " + this.HP);
    }

    // Prints out stage stats
    public void printSecretStats() {
        System.out.println(
                "Attack Stage: " + this.stageAttack + "\n" +
                "Defense Stage: " + this.stageDefense + "\n" +
                "Speed Stage: " + this.stageSpeed + "\n" +
                "Accuracy Stage: " + this.stageAccuracy + "\n" +
                "Evasion Stage: " + this.stageEvasion + "\n");
    }

    // toString
    public String toString() {
        if (type2 == null) {
            return String.format("Name: "+this.name+"\n"+"Type: "+this.type1+"\n"+"Level: "+this.level+"\n"+"HP: "+this.HP+"/"+this.maxHP+"\n"+
                    "Attack: "+this.attack+"\n"+"Defense: "+this.defense+"\n"+"Speed: "+this.speed+"\n"+
                    "EXP: "+this.exp+"/"+this.expNext+"\n");
        } else {
            return String.format("Name: "+this.name+"\n"+"Type: "+this.type1+" and " +this.type2+"\n"+"Level: "+this.level+"\n"+"HP: "+this.HP+"/"+this.maxHP+"\n"+
                    "Attack: "+this.attack+"\n"+"Defense: "+this.defense+"\n"+"Speed: "+this.speed+"\n"+
                    "EXP: "+this.exp+"/"+this.expNext+"\n");
        }

    }

}
