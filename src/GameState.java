import com.sun.source.tree.Tree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameState {

    Trainer trainer;
    Pokemon opponent;
    ArrayList<Pokemon> whoBattled;
    int pokedexLines;

    // setup
    public void setup() {
        AttackMove covet = new AttackMove("Covet", "NORMAL", 80, 0.9); // eevee
        AttackMove electroBall = new AttackMove("Electro Ball", "ELECTRIC", 80, 0.9); // pikachu
        AttackMove liquidation = new AttackMove("Liquidation", "WATER", 80, 0.9); // squirtle
        AttackMove pyroBall = new AttackMove("Pyro Ball", "FIRE", 80, 0.9); // charmander
        AttackMove grassySurge = new AttackMove("Grassy Surge", "GRASS", 80, 0.9); // bulbasaur

        StatusMove workup = new StatusMove("Work Up", "NORMAL", "Attack", 1, true);
        StatusMove screech = new StatusMove("Screech", "NORMAL", "Defense", -2, false);

        TreeMap<Integer, Move> eeveeMoves = new TreeMap<>();
        eeveeMoves.put(1, covet);

        TreeMap<Integer, Move> pikachuMoves = new TreeMap<>();
        pikachuMoves.put(1, electroBall);

        TreeMap<Integer, Move> squirtleMoves = new TreeMap<>();
        squirtleMoves.put(1, liquidation);

        TreeMap<Integer, Move> charmanderMoves = new TreeMap<>();
        charmanderMoves.put(1, pyroBall);

        TreeMap<Integer, Move> bulbasaurMoves = new TreeMap<>();
        bulbasaurMoves.put(1, grassySurge);

        Pokemon eevee = new Pokemon("EEVEE", "NORMAL", eeveeMoves, 65, 75, 85, 75, 100, 5);
        Pokemon pikachu = new Pokemon("PIKACHU", "ELECTRIC", pikachuMoves, 35, 55, 50, 90, 100, 5);
        Pokemon squirtle = new Pokemon("SQUIRTLE", "WATER", squirtleMoves, 44, 50, 65, 43, 100, 5);
        Pokemon charmander = new Pokemon("CHARMANDER", "FIRE", charmanderMoves, 39, 60, 50, 65, 100, 5);
        Pokemon bulbasaur = new Pokemon("BULBASAUR", "GRASS", bulbasaurMoves, 45, 65, 65, 45, 100, 5);

        whoBattled = new ArrayList<>();

        pokedexLines = linesInFile("Pokedex");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the world of Pokemon!");
        System.out.println("First, what is your name?");

        String name = scanner.nextLine();
        trainer = new Trainer(name);

        trainer.addPotions(5);
        trainer.addPokeBalls(5);

        System.out.println("Ok, your name is " + name + "!");
        System.out.println("Choose your first pokemon!");
        System.out.println("Who would you like? (type the name (no caps) and press enter)");
        System.out.println("CHARMANDER?");
        System.out.println("SQUIRTLE?");
        System.out.println("BULBASAUR?");

        while (true) {

            String starter = scanner.nextLine().toLowerCase();

            if (starter.equals("charmander")) {
                System.out.println("Congratulations! You've received the CHARMANDER!");
                trainer.caughtPokemon(charmander);
                break;
            } else if (starter.equals("squirtle")) {
                System.out.println("Congratulations! You've received the SQUIRTLE!");
                trainer.caughtPokemon(squirtle);
                break;
            } else if (starter.equals("bulbasaur")) {
                System.out.println("Congratulations! You've received the BULBASAUR!");
                trainer.caughtPokemon(bulbasaur);
                break;
            } else if (starter.equals("pikachu")) {
                System.out.println("Congratulations! You've received the PIKACHU!");
                trainer.caughtPokemon(pikachu);
                break;
            } else if (starter.equals("eevee")) {
                System.out.println("Congratulations! You've received the EEVEE!");
                trainer.caughtPokemon(eevee);
                break;
            } else {
                System.out.println("Choose a valid starter");
            }
        }

        System.out.println("Get ready for your new journey! Good luck!");
        System.out.println("List of moves for your starter: ");
    }

    // prints out instructions/keysets
    public void instructions() {
        System.out.println("Press 'w' to walk");
        System.out.println("Press 'p' to see your trainer profile");
        System.out.println("Press 'r' to see your pokemon that can still battle");
        System.out.println("Press 'c' to check your party pokemon in detail");
        System.out.println("Press 'n' to check your pc pokemon in detail");
        System.out.println("Press 'v' to check your party pokemon not detailed");
        System.out.println("Press 'm' to check your pc pokemon not detailed");
        System.out.println("Press 'x' to check your pokeball inventory");
        System.out.println("Press 's' to check your potion inventory");
        System.out.println("Press 'j' to heal your party pokemon");
        System.out.println("Press 'k' to move your party pokemon around");
        System.out.println("Press 'l' to swap party and pc pokemon");
        System.out.println("Press 't' to deposit a party pokemon to the pc");
        System.out.println("Press 'y' to withdraw a pc pokemon to your party");
        System.out.println("Press 'z' to see this all again");
    }

    // returns whether trainer has any pokemon left
    public boolean getTrainerBlackedOut() {
        return trainer.blackedOut;
    }

    // returns whether trainer is battling
    public boolean trainerBattling() {
        return trainer.isInBattle();
    }

    // walking
    public void walking() {
        if (trainer.inBattle == false) {

            Random random = new Random();

            double encounterChance = random.nextDouble();
            if (encounterChance <= 0.5) { // You encounter a wild pokemon
                System.out.println("You've encountered a wild pokemon!");

                //opponent = new Pokemon("TEST", "NORMAL", opponentMoves, 50, 50, 50, 50, 100, 5);
                opponent = createOpponent(newPokemonString());
                System.out.println("A lvl" + opponent.level + " " + opponent.name + " has appeared!");
                System.out.println("");
                System.out.println("Go, " + trainer.getFirst().name + "!");

                trainer.setToBattling();
            } else {
                Random rand = new Random();
                double itemChance = rand.nextDouble();
                System.out.println("You continue on the path");

                if (itemChance <= 0.3) { // found pokeballs
                    Random r = new Random();
                    double pokeball = r.nextDouble();
                    if (pokeball <= 0.4) { // regular pokeballs
                        int number = ThreadLocalRandom.current().nextInt(2, 11);
                        System.out.println("You found " + number + " Poke Balls!");
                        System.out.println("You put the Poke Balls in your bag");
                        trainer.addPokeBalls(number);
                    } else if (pokeball >= 0.65) { // great balls
                        int number = ThreadLocalRandom.current().nextInt(2, 8);
                        System.out.println("You found " + number + " Great Balls!");
                        System.out.println("You put the Great Balls in your bag");
                        trainer.addGreatBalls(number);
                    } else {
                        int number = ThreadLocalRandom.current().nextInt(1, 6);
                        System.out.println("You found " + number + " Ultra Balls!");
                        System.out.println("You put the Ultra Balls in your bag");
                        trainer.addUltraBalls(number);
                    }
                } else if (itemChance >= 0.6) { // found potions
                    Random r = new Random();
                    double potion = r.nextDouble();
                    if (potion <= 0.34) { // regular potions
                        int number = ThreadLocalRandom.current().nextInt(2, 11);
                        System.out.println("You found " + number + " Potions!");
                        System.out.println("You put the Potions in your bag");
                        trainer.addPotions(number);
                    } else if (potion >= 0.66) { // Super potions
                        int number = ThreadLocalRandom.current().nextInt(2, 8);
                        System.out.println("You found " + number + " Super Potions!");
                        System.out.println("You put the Super Potions in your bag");
                        trainer.addSuperPotions(number);
                    } else {
                        int number = ThreadLocalRandom.current().nextInt(1, 6);
                        System.out.println("You found " + number + " Hyper Potions!");
                        System.out.println("You put the Hyper Potions in your bag");
                        trainer.addHyperPotions(number);
                    }
                }

                trainer.setToWalking();

            }
        } else {
            System.out.println("Can't walk in battle!");
        }
    }

    // when opponent attacks
    public void opponentAttack() {
        Pokemon first = trainer.getFirst();
        Pokemon.attack(opponent, first, opponent.randomMove());
        if (first.HP == 0) {
            System.out.println(first.name + " fainted!");
            first.resetStages();
            if (trainer.allFainted()) { // no party pokemon left
                System.out.println("You blacked out! No pokemon left!");
                trainer.setBlackedOut();
            } else { // still at least one party pokemon left
                switchIn();
            }
        }
    }

    // Battling v2
    public void battling2() {
        Scanner scanner = new Scanner(System.in);

        whoBattled.clear();
        int option;
        do {
            Pokemon first = trainer.getFirst();
            if (!whoBattled.contains(first)) {
                whoBattled.add(first);
            }
            System.out.println("");
            System.out.println("Opponent: " + opponent.name + ", lvl " + opponent.level + ", HP: " + opponent.HP + "/" + opponent.maxHP);
            System.out.println("You: " + first.name + ", lvl " + first.level + ", HP: " + first.HP + "/" + first.maxHP);
            System.out.println("");
            System.out.println("What would you like to do?");
            System.out.println("1 -- FIGHT");
            System.out.println("2 -- POTIONS");
            System.out.println("3 -- POKEBALLS");
            System.out.println("4 -- POKEMON");
            System.out.println("5 -- RUN");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            option = scanner.nextInt();
            if (option == 1) { // attack
                System.out.println("Which move? Type '0' to go back");
                first.showAllMoves();
                Scanner scan = new Scanner(System.in);
                int moveNumber = scan.nextInt();

                if (moveNumber == 0) {
                    System.out.println("Quitting attack menu");
                }
                Move moveChosen = first.getMove(moveNumber);
                if (first.speed * Pokemon.stageStatMultiplier(first.stageSpeed) >= opponent.speed * Pokemon.stageStatMultiplier(opponent.stageSpeed)) { // opponent slower
                    Pokemon.attack(first, opponent, moveChosen);
                    if (opponent.HP == 0) { // opponent fainted
                        System.out.println(opponent.name + " fainted!");
                        first.resetStages();
                        giveExp();
                        System.out.println("you went back to walking");
                        trainer.setToWalking();
                        break;
                    } else { // opponent still alive
                        Pokemon.attack(opponent, first, opponent.randomMove());
                        if (first.HP == 0) {
                            System.out.println(first.name + " fainted!");
                            first.resetStages();
                            if (trainer.allFainted()) { // no party pokemon left
                                System.out.println("You blacked out! No pokemon left!");
                                trainer.setBlackedOut();
                                break;
                            } else { // still at least one party pokemon left
                                switchIn();
                            }
                        }
                    }
                } else { // Opponent faster
                    Pokemon.attack(opponent, first, opponent.randomMove());
                    if (first.HP == 0) {
                        System.out.println(first.name + " fainted!");
                        first.resetStages();
                        if (trainer.allFainted()) { // no party pokemon left
                            System.out.println("You blacked out! No pokemon left!");
                            trainer.setBlackedOut();
                            break;
                        } else { // still at least one party pokemon left
                            switchIn();
                        }
                    } else {
                        Pokemon.attack(first, opponent, moveChosen);
                        if (opponent.HP == 0) { // opponent fainted
                            System.out.println(opponent.name + " fainted!");
                            first.resetStages();
                            giveExp();
                            System.out.println("you went back to walking");
                            trainer.setToWalking();
                            break;
                        }
                    }
                }
            } else if (option == 2) { // potions
                healPokemon();
                if (!trainer.getGoBack()) {
                    opponentAttack();
                }
            } else if (option == 3) { // pokeballs
                throwBall();
                if (!trainer.getGoBack()) {
                    opponentAttack();
                }
            } else if (option == 4) { // pokemon
                switchParty();
                if (!trainer.getGoBack() || trainer.isInBattle()) {
                    opponentAttack();
                }
            } else if (option == 5) { // flee
                flee();
            } else {
                System.out.println("Invalid command");
            }

        } while (trainer.inBattle == true);
        for (Pokemon i : whoBattled) {
            i.resetStages();
        }
    }

    // draft of battling method
    public void battling() {
        if (trainer.inBattle == true) {
            Scanner scanner = new Scanner(System.in);
            Pokemon first = trainer.getFirst();
            System.out.println("What would you like to do?");
            System.out.println("1 -- FIGHT");
            System.out.println("2 -- POTIONS");
            System.out.println("3 -- POKEBALLS");
            System.out.println("4 -- POKEMON");
            System.out.println("5 -- RUN");


            int option = scanner.nextInt();

            if (option == 1) { // attack
                System.out.println("Which move?");
                first.showAllMoves();
                Scanner scan = new Scanner(System.in);
                int moveNumber = scan.nextInt();
                Move moveChosen = first.getMove(moveNumber);
                if (first.speed * Pokemon.stageStatMultiplier(first.stageSpeed) >= opponent.speed * Pokemon.stageStatMultiplier(opponent.stageSpeed)) { // opponent slower
                    Pokemon.attack(first, opponent, moveChosen);
                    if (opponent.HP == 0) { // opponent fainted
                        System.out.println(opponent.name + " fainted!");
                        trainer.setToWalking();
                    } else { // opponent still alive
                        Pokemon.attack(opponent, first, opponent.getMove(1));
                        if (first.HP == 0) {
                            System.out.println(first.name + " fainted!");
                            if (trainer.allFainted()) { // no party pokemon left
                                System.out.println("You blacked out! No pokemon left!");
                                trainer.setBlackedOut();
                            } else { // still at least one party pokemon left
                                switchIn();
                            }
                        }
                    }
                } else { // Opponent faster
                    Pokemon.attack(opponent, first, opponent.getMove(1));
                    if (first.HP == 0) {
                        System.out.println(first.name + " fainted!");
                        if (trainer.allFainted()) { // no party pokemon left
                            System.out.println("You blacked out! No pokemon left!");
                            trainer.setBlackedOut();
                        } else { // still at least one party pokemon left
                            switchIn();
                        }
                    } else {
                        Pokemon.attack(first, opponent, moveChosen);
                        if (opponent.HP == 0) { // opponent fainted
                            System.out.println(opponent.name + " fainted!");
                            trainer.setToWalking();
                        }
                    }
                }
            } else if (option == 2) { // potions
                healPokemon();
                opponentAttack();
            } else if (option == 3) { // pokeballs
                throwBall();
                opponentAttack();
            } else if (option == 4) { // pokemon
                switchParty();
                opponentAttack();
            } else if (option == 5) { // flee
                flee();
            } else {
                System.out.println("Invalid command");
            }
        } else {
            System.out.println("There is no opponent ...");
        }
    }

    // switch in during battle
    public void switchIn() {
        System.out.println("Choose a different pokemon");
        trainer.showParty();
        Scanner in = new Scanner(System.in);
        while (true) {
            int toSwitch = in.nextInt();
            if (trainer.getPartyPokemon(toSwitch).HP != 0) {
                trainer.switchInBattle(toSwitch);
                break;
            } else {
                System.out.println("Chose a pokemon that can still battle");
            }
        }
    }

    // heal pokemon
    public void healPokemon() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which potions would you like to use? (potion, super, hyper) or 'back' to exit this menu");
        trainer.checkPotions();
        String potion = scanner.next().toLowerCase();

        if (potion.equals("back")) {
            System.out.println("Quitting potion menu");
            trainer.setGoBack();
        } else if (!potion.equals("potion") && !potion.equals("super") && !potion.equals("hyper")) {
            System.out.println("invalid potion type");
            System.out.println("Quitting potion menu");
            trainer.setGoBack();
        } else {
            System.out.println("Which pokemon would you like to heal or '0' to exit this menu?");
            trainer.showParty();
            Scanner scan = new Scanner(System.in);
            while (true) {
                int partySlot = scan.nextInt();
                if (partySlot == 0) {
                    System.out.println("Quitting potion menu");
                    trainer.setGoBack();
                    break;
                }

                if (partySlot > trainer.party.size()) {
                    System.out.println("Choose a valid slot");
                } else if (trainer.getPartyPokemon(partySlot).HP == trainer.getPartyPokemon(partySlot).maxHP) {
                    System.out.println("This pokemon already has full health!");
                    break;
                } else {
                    if (potion.equals("potion")) {
                        if (trainer.checkNumberPotions() == 0) {
                            System.out.println("You have no more potions left!");
                            System.out.println("Quitting potions menu");
                            trainer.setGoBack();
                        } else {
                            trainer.usePotion(trainer.getPartyPokemon(partySlot));
                            trainer.setGoForward();
                        }
                        break;
                    } else if (potion.equals("super")) {
                        if (trainer.checkNumberSuperPotions() == 0) {
                            System.out.println("You have no more super potions left!");
                            System.out.println("Quitting potions menu");
                            trainer.setGoBack();
                        } else {
                            trainer.useSuperPotion(trainer.getPartyPokemon(partySlot));
                            trainer.setGoForward();
                        }
                        break;
                    } else if (potion.equals("hyper")) {
                        if (trainer.checkNumberHyperPotions() == 0) {
                            System.out.println("You have no more hyper potions left");
                            System.out.println("Quitting potions menu");
                        } else {
                            trainer.useHyperPotion(trainer.getPartyPokemon(partySlot));
                            trainer.setGoForward();
                        }
                        break;
                    }
                }

            }
        }
    }

    // throw pokeball
    public void throwBall() {
        if (trainer.isInBattle()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which pokeball would you like to use? (poke, great, ultra) or 'back' to exit this menu");
            trainer.checkBalls();
            String ball = scanner.next();
            while (true) {
                if (ball.equals("back")) {
                    System.out.println("Quitting pokeball menu");
                    trainer.setGoBack();
                    break;
                }

                if (ball.equals("poke")) {
                    if (trainer.checkNumberPokeBalls() == 0) {
                        System.out.println("You have no more PokeBalls left!");
                        System.out.println("Quitting pokeball menu");
                        trainer.setGoBack();
                    } else {
                        trainer.throwPokeBall(opponent);
                        trainer.setGoForward();
                    }
                    break;
                } else if (ball.equals("great")) {
                    if (trainer.checkNumberGreatBalls() == 0) {
                        System.out.println("You have no more Great Balls left!");
                        System.out.println("Quitting pokeball menu");
                        trainer.setGoBack();
                    } else {
                        trainer.throwGreatBall(opponent);
                        trainer.setGoForward();
                    }
                    break;
                } else if (ball.equals("ultra")) {
                    if (trainer.checkNumberUltraBalls() == 0) {
                        System.out.println("You have no more Ultra Balls left!");
                        System.out.println("Quitting pokeball menu");
                        trainer.setGoBack();
                    } else {
                        trainer.throwUltraBall(opponent);
                        trainer.setGoForward();
                    }
                    break;
                } else {
                    System.out.println("Unknown ball type");
                    trainer.setGoBack();
                }
            }
        } else {
            System.out.println("There is no pokemon to catch ...");
        }

    }

    // switch around pokemon in your party
    public void switchParty() {
        if (trainer.isInBattle()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which pokemon slot to switch to? type '0' to go back");
            trainer.showParty();
            while (true) {
                int slot = scanner.nextInt();
                if (slot == 0) {
                    System.out.println("Quitting switch menu");
                    trainer.setGoBack();
                    break;
                } else if (slot > trainer.party.size()) {
                    System.out.println("Not a valid pokemon slot");
                    trainer.setGoBack();
                } else {
                    if (trainer.getPartyPokemon(slot).HP == 0) {
                        System.out.println("This pokemon has fainted!");
                        trainer.setGoBack();
                    } else {
                        trainer.switchInBattle(slot);
                        trainer.getPartyPokemon(slot).resetStages();
                        trainer.setGoForward();
                        break;
                    }
                }
            }

        } else {
            Scanner scanner = new Scanner(System.in);
            trainer.showParty();
            System.out.println("First Pokemon slot to switch (type '0' to go back)");
            int slot1 = scanner.nextInt();
            if (slot1 == 0) {
                System.out.println("Quitting switch menu");
                trainer.setGoBack();
            } else if (slot1 > trainer.party.size()) {
                System.out.println("Can't select a slot larger than your party");
                trainer.setGoBack();
            } else {
                Scanner scan = new Scanner(System.in);
                System.out.println("Second pokemon slot to switch ('0' to go back)");
                int slot2 = scan.nextInt();
                if (slot2 == 0) {
                    System.out.println("Quitting switch menu");
                    trainer.setGoBack();
                } else if (slot2 > trainer.party.size()) {
                    System.out.println("Can't select a slot larger than your party");
                    trainer.setGoBack();
                } else if (slot1 == slot2) {
                    System.out.println("Can't select same slot");
                    trainer.setGoBack();
                } else {
                    trainer.switchPartyPokemon(slot1, slot2);
                    trainer.setGoForward();
                }
            }
        }
    }

    // switch pokemon between party and PC
    public void partyPCSwitch() {
        if (trainer.isInBattle() == false) {
            Scanner scanner = new Scanner(System.in);
            trainer.showParty();
            System.out.println("First Pokemon party slot to switch (type '0' to go back)");
            int slot1 = scanner.nextInt();
            if (slot1 == 0) {
                System.out.println("Quitting switch menu");
            } else if (slot1 > trainer.party.size()) {
                System.out.println("Can't select a slot larger than your party");
            } else {
                Scanner scan = new Scanner(System.in);
                System.out.println("Second pokemon PC slot to switch ('0' to go back)");
                trainer.showPC();
                int slot2 = scan.nextInt();
                if (slot2 == 0) {
                    System.out.println("Quitting switch menu");
                } else if (slot2 > trainer.party.size()) {
                    System.out.println("Can't select a slot larger than your PC");
                } else {
                    trainer.switchPartyPC(slot1, slot2);
                }
            }
        } else {
            System.out.println("You're in a battle right now! Can't check PC!");
        }
    }

    // Run from battle
    public void flee() {
        if (trainer.inBattle) {
            Random random = new Random();
            double fleeChance = random.nextDouble();

            if (fleeChance <= 0.8) {
                System.out.println("You fled from the wild pokemon!");
                trainer.setToWalking();
            } else {
                System.out.println("The wild pokemon blocked your path!");
                opponentAttack();
            }
        } else {
            System.out.println("You aren't in a battle right now!");
        }
    }

    // Checks trainer profile
    public void trainerProfile() {
        System.out.println(trainer.toString());
    }

    // Prints out detailed description of all pokemon in party
    public void checkPartyDetailed() {
        trainer.showPartyDetailed();
    }

    // Prints out description of pokemon in party
    public void checkParty() {
        trainer.showParty();
    }

    // detailed description of all pokemon in PC
    public void checkPCDetailed() {
        trainer.showPCDetailed();
    }

    // description of all pokemon in pc
    public void checkPC() {
        trainer.showPC();
    }

    // shows remaining pokemon that can still battle (HP > 0)
    public void showRemaining() {
        trainer.showRemainingParty();
    }

    // shows number of pokeballs left
    public void showPokeballs() {
        trainer.checkBalls();
    }

    // shows number of potions left
    public void showPotions() {
        trainer.checkPotions();
    }

    // deposits a single pokemon into the PC
    public void depositFromParty() {
        Scanner scanner = new Scanner(System.in);
        if (trainer.party.size() == 1) {
            System.out.println("You only have one pokemon in your party!");
        } else {
            System.out.println("Which pokemon would you like to deposit into your PC? (by slot number) type '0' to go back");
            trainer.showParty();
            while (true) {
                int slot = scanner.nextInt();

                if (slot == 0) {
                    System.out.println("Quitting PC operations");
                    break;
                }
                if (slot > trainer.party.size()) {
                    System.out.println("Pick a valid slot number");
                } else {
                    trainer.deposit(slot);
                    break;
                }
            }
        }
    }

    // Withdraws a single pokemon from the pc
    public void withdrawFromPC() {
        Scanner scanner = new Scanner(System.in);
        if (trainer.pc.size() == 0) {
            System.out.println("You have no pokemon in your pc!");
        } else {
            System.out.println("Which pokemon would you like to withdraw? (by slot number) type '0' to go back");
            trainer.showPC();
            while (true) {
                int slot = scanner.nextInt();

                if (slot == 0) {
                    System.out.println("Quitting PC operations");
                    break;
                }
                if (slot > trainer.pc.size()) {
                    System.out.println("Pick a valid slot number");
                } else {
                    trainer.withdraw(slot);
                    break;
                }
            }
        }
    }

    // gives exp to those who participated in battle
    public void giveExp() {
        for (Pokemon i : whoBattled) {
            if (!i.isFainted()) {
                i.wonBattle(opponent);
            }
        }
    }


    /*
    RANDOM GENERATORS
     */

    // returns total number of lines in file given name (Pokedex, NORMAL, etc.)
    // When implementing this game into your own computer, make sure to change the file locations/directories in this code
    // so that it matches yours
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


    // For pokemon

    // Gets a pokemon stats from file based on dex number
    public String newPokemonString() {
        try {
            int dexNumber = ThreadLocalRandom.current().nextInt(1, pokedexLines + 1);
            String str = Files.lines(Paths.get("C:\\Users\\flare\\Pokemon\\Pokedex.txt")).skip(dexNumber - 1).findFirst().get();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // takes in newPokemonString and returns a pokemon
    public Pokemon createOpponent(String pokemonString) {
        String[] pokemonStats = pokemonString.split(",");
        int avg = averageLevelInParty();

        if (pokemonStats.length == 7) { // one type

            String name = pokemonStats[0];
            String type = pokemonStats[1];
            int hp = Integer.parseInt(pokemonStats[2]);
            int attack = Integer.parseInt(pokemonStats[3]);
            int defense = Integer.parseInt(pokemonStats[4]);
            int speed = Integer.parseInt(pokemonStats[5]);
            int catchRate = Integer.parseInt(pokemonStats[6]);
            TreeMap<Integer,Move> moves = new TreeMap<>();
            moves.put(1,createMove(newMoveString1(type)));
            moves.put(2,createMove(newMoveString1(type)));
            moves.put(3,createMove(newMoveString1(type)));

            if (avg > 20) {
                int opponentLevel = ThreadLocalRandom.current().nextInt(avg - 10, avg + 11);
                return new Pokemon(name, type, moves, hp, attack, defense, speed, catchRate, opponentLevel);
            } else {
                int opponentLevel = ThreadLocalRandom.current().nextInt(1, avg + 5);
                return new Pokemon(name, type, moves, hp, attack, defense, speed, catchRate, opponentLevel);
            }

        } else { // two type

            String name = pokemonStats[0];
            String type1 = pokemonStats[1];
            String type2 = pokemonStats[2];
            int hp = Integer.parseInt(pokemonStats[3]);
            int attack = Integer.parseInt(pokemonStats[4]);
            int defense = Integer.parseInt(pokemonStats[5]);
            int speed = Integer.parseInt(pokemonStats[6]);
            int catchRate = Integer.parseInt(pokemonStats[7]);
            TreeMap<Integer,Move> moves = new TreeMap<>();
            moves.put(1,createMove(newMoveString2(type1, type2)));
            moves.put(2,createMove(newMoveString2(type1, type2)));
            moves.put(3,createMove(newMoveString2(type1, type2)));

            if (avg > 20) {
                int opponentLevel = ThreadLocalRandom.current().nextInt(avg - 10, avg + 11);
                return new Pokemon(name, type1, type2, moves, hp, attack, defense, speed, catchRate, opponentLevel);
            } else {
                int opponentLevel = ThreadLocalRandom.current().nextInt(1, avg + 5);
                return new Pokemon(name, type1, type2, moves, hp, attack, defense, speed, catchRate, opponentLevel);
            }
        }
    }

    // calculates average level in party for random opponent level
    public int averageLevelInParty() {
        int sum = 0;
        int count = trainer.party.size();
        for (Map.Entry<Integer,Pokemon> entry : trainer.party.entrySet()) {
            Integer key = entry.getKey();
            Pokemon value = entry.getValue();
            sum = sum + value.level;
        }
        return (int) (sum / count);
    }
}
