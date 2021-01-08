import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        GameState gs = new GameState();
        gs.setup();
        gs.instructions();

        Scanner scanner = new Scanner(System.in);
        while (gs.getTrainerBlackedOut() == false) {
            char input = scanner.next().charAt(0);
            if (input == 'w') {
                gs.walking();
                if (gs.trainerBattling()) {
                    gs.battling2();
                    if (gs.getTrainerBlackedOut()) {
                        break;
                    }
                }
            } else if (input == 'p') {
                gs.trainerProfile();
            } else if (input == 'r') {
                gs.showRemaining();
            } else if (input == 'c') {
                gs.checkPartyDetailed();
            } else if (input == 'n') {
                gs.checkPCDetailed();
            } else if (input == 'v') {
                gs.checkParty();
            } else if (input == 'm') {
                gs.checkPC();
            } else if (input == 'x') {
                gs.showPokeballs();
            } else if (input == 's') {
                gs.showPotions();
            } else if (input == 'j') {
                gs.healPokemon();
            } else if (input == 'k') {
                gs.switchParty();
            } else if (input == 'l') {
                gs.partyPCSwitch();
            } else if (input == 't') {
                gs.depositFromParty();
            } else if (input == 'y') {
                gs.withdrawFromPC();
            } else if (input == 'z') {
                gs.instructions();
            } else {
                System.out.println("Invalid command, use 'z' to see a list of commands");
            }
        }



//        PokeBall pb = new PokeBall();
//        GreatBall gb = new GreatBall();
//        UltraBall ub = new UltraBall();
//
//        AttackMove covet = new AttackMove("Covet", "NORMAL", 80, 0.9); // eevee
//        AttackMove electroBall = new AttackMove("Electro Ball", "ELECTRIC", 80, 0.9); // pikachu
//        AttackMove liquidation = new AttackMove("Liquidation", "WATER", 80, 0.9); // squirtle
//        AttackMove pyroBall = new AttackMove("Pyro Ball", "FIRE", 80, 0.9); // charmander
//        AttackMove grassySurge = new AttackMove("Grassy Surge", "GRASS", 80, 0.9); // bulbasaur
//
//        StatusMove workup = new StatusMove("Work Up", "NORMAL", "Attack", 1, true);
//        StatusMove screech = new StatusMove("Screech", "NORMAL", "Defense", -2, false);
//
//        Trainer trainer = new Trainer("CHRIS");
//
//        TreeMap<Integer,Move> eeveeMoves = new TreeMap<>();
//        TreeMap<Integer,Move> pikachuMoves = new TreeMap<>();
//        TreeMap<Integer,Move> squirtleMoves = new TreeMap<>();
//        TreeMap<Integer,Move> charmanderMoves = new TreeMap<>();
//        TreeMap<Integer,Move> bulbasaurMoves = new TreeMap<>();
//        eeveeMoves.put(1,covet);
//        pikachuMoves.put(1,electroBall);
//        squirtleMoves.put(1,liquidation);
//        charmanderMoves.put(1,pyroBall);
//        bulbasaurMoves.put(1,grassySurge);
//
//
//
//
//        Pokemon eevee = new Pokemon("EEVEE", "NORMAL", eeveeMoves, 65, 75, 85, 75, 100, 5);
//        Pokemon pikachu = new Pokemon("PIKACHU", "ELECTRIC", pikachuMoves, 35, 55, 50, 90, 100, 5);
//        Pokemon squirtle = new Pokemon("SQUIRTLE", "WATER", squirtleMoves, 44, 50, 65, 43, 100, 5);
//        Pokemon charmander = new Pokemon("CHARMANDER", "FIRE", charmanderMoves, 39, 60, 50, 65, 100, 5);
//        Pokemon bulbasaur = new Pokemon("BULBASAUR", "GRASS", bulbasaurMoves, 45, 65, 65, 45,100, 5);
//
//
//        bulbasaur.showAllMoves();

//        Pokemon.attack(squirtle, bulbasaur, bulbasaurMoves.get(1));
//        Pokemon.attack(bulbasaur, squirtle, squirtleMoves.get(1));
//        squirtle.printSecretStats();
//        bulbasaur.printSecretStats();


//
//        Pokemon.attack(squirtle, bulbasaur, squirtleMoves.get(1));
//        Pokemon.attack(bulbasaur, squirtle, bulbasaurMoves.get(1));
//        System.out.println(squirtle);
//        System.out.println(bulbasaur);
    }



}
