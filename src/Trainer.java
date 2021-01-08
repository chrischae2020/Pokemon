import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Trainer {

    String name;
    TreeMap<Integer,Pokemon> party;
    TreeMap<Integer,Pokemon> pc;

    boolean inBattle;
    boolean blackedOut;

    HashMap<Ball,Integer> pokeballs;
    PokeBall pb;
    GreatBall gb;
    UltraBall ub;

    HashMap<Spray,Integer> potions;
    Potion p;
    SuperPotion sp;
    HyperPotion hp;

    boolean goBack;
    boolean catchSuccess;

    // Trainer constructor
    public Trainer(String name) {
        this.name = name;
        this.inBattle = false;
        this.blackedOut = false;

        this.party = new TreeMap<>();
        this.pc = new TreeMap<>();

        this.pb = new PokeBall();
        this.gb = new GreatBall();
        this.ub = new UltraBall();
        this.pokeballs = new HashMap<>();
        this.pokeballs.put(this.pb,0);
        this.pokeballs.put(this.gb,0);
        this.pokeballs.put(this.ub,0);

        this.p = new Potion();
        this.sp = new SuperPotion();
        this.hp = new HyperPotion();
        this.potions = new HashMap<>();
        this.potions.put(this.p,0);
        this.potions.put(this.sp,0);
        this.potions.put(this.hp,0);

        this.goBack = false;
        this.catchSuccess = false;
    }

    public void setGoBack() {
        this.goBack = true;
    }
    public void setGoForward() {
        this.goBack = false;
    }
    public boolean getGoBack() {
        return this.goBack;
    }

    // First pokemon switches with another during battle
    public void switchInBattle(int slot) {
        Pokemon first = getPartyPokemon(1);
        System.out.println("Nice job, " + first.name + ". Return!");
        switchPartyPokemon(1, slot);
        System.out.println("Go for it, " + getPartyPokemon(1).name + "!");
    }

    // swaps the slots between two party pokemon
    public void switchPartyPokemon(int slot1, int slot2) {
        Pokemon from = this.party.get(slot1);
        Pokemon to = this.party.get(slot2);
        System.out.println("Switching slots between Pokemon in slot " + slot1 + ": " + from.name + " slot " + slot2 + ": " + to.name);
        this.party.replace(slot1, to);
        this.party.replace(slot2, from);
    }

    // prints out pokemon available to battle (not fainted)
    public void showRemainingParty() {
        for (Map.Entry<Integer,Pokemon> entry : this.party.entrySet()) {
            int key = entry.getKey();
            Pokemon value = entry.getValue();
            if (value.isFainted() == false) {
                System.out.println("Slot " + key + "--" + value.name + " lvl " + value.level);
            }
        }
    }

    // swap between party and pc pokemon using slots (int)
    public void switchPartyPC(int partySlot, int pcSlot) {
        Pokemon party = this.party.get(partySlot);
        Pokemon pc = this.pc.get(pcSlot);
        System.out.println("Placing " + party.name + " in the PC and taking out " + pc.name);
        this.party.replace(partySlot, pc);
        this.pc.replace(pcSlot, party);
    }

    // Sends a Party pokemon to the PC
    public void deposit(int partySlot) {
        Pokemon toPC = this.getPartyPokemon(partySlot);
        if (partySlot == this.party.size()) {
            this.party.remove(partySlot);
            int number = this.pc.size() + 1;
            this.pc.put(number, toPC);
        } else {
            int lastSlot = this.party.size();
            Pokemon lastPokemon = this.getPartyPokemon(lastSlot);
            this.party.replace(partySlot, lastPokemon);
            this.party.replace(lastSlot, toPC);
            this.party.remove(lastSlot);
            int number = this.pc.size() + 1;
            this.pc.put(number, toPC);
        }
    }

    // takes a PC pokemon and adds it to the party
    public void withdraw(int pcSlot) {
        Pokemon toParty = this.pc.get(pcSlot);
        this.pc.remove(pcSlot);
        int number = this.party.size() + 1;
        this.party.put(number, toParty);
    }

    // prints out a list of pokemon in party (lvl, name, hp)
    public void showParty() {
        System.out.println("List of Party Pokemon");
        for (Map.Entry<Integer,Pokemon> entry : this.party.entrySet()) {
            Integer key = entry.getKey();
            Pokemon value = entry.getValue();
            System.out.println("Slot " + key + ": " + value.name + ", lvl " + value.level + ", HP: " + value.HP + "/" + value.maxHP);
        }
        System.out.println("");
    }

    // detailed showParty
    public void showPartyDetailed() {
        System.out.println("List of Party Pokemon");
        for (Map.Entry<Integer,Pokemon> entry : this.party.entrySet()) {
            Integer key = entry.getKey();
            Pokemon value = entry.getValue();
            System.out.println("Slot " + key + ": " + value.toString());
        }
        System.out.println("");
    }

    // prints out a list of pokemon in the pc (detailed)
    public void showPCDetailed() {
        System.out.println("List of PC Pokemon");
        for (Map.Entry<Integer,Pokemon> entry : this.pc.entrySet()) {
            Integer key = entry.getKey();
            Pokemon value = entry.getValue();
            System.out.println("Slot " + key + ": " + value.toString());
        }
        System.out.println("");
    }

    public void showPC() {
        System.out.println("List of PC Pokemon");
        for (Map.Entry<Integer,Pokemon> entry : this.pc.entrySet()) {
            Integer key = entry.getKey();
            Pokemon value = entry.getValue();
            System.out.println("Slot " + key + ": " + value.name + ", lvl " + value.level);
        }
        System.out.println("");
    }

    // catches a pokemon and moves the pokemon to pc/party
    public void caughtPokemon(Pokemon pokemon) {
        if (this.party.size() < 6) {
            int number = this.party.size() + 1;
            this.party.put(number, pokemon);
            System.out.println(pokemon.name + " has been put in slot " + number + " in your party!");
        } else {
            int number = this.pc.size() + 1;
            this.pc.put(number, pokemon);
            System.out.println("Your party is full, so " + pokemon.name + " has been put in slot " + number + " in your PC!");
        }
    }

    // setters
    public void setInBattle() {
        this.inBattle = true;
    }

    public void setBlackedOut() {
        this.blackedOut = true;
    }

    public void setToBattling() {
        this.inBattle = true;
    }

    public void setToWalking() {
        this.inBattle = false;
    }

    // getters

    public boolean canSwitch(int slot) {
        return (slot <= this.party.size()) && (getPartyPokemon(slot).HP != 0);
    }

    // check if all party pokemon fainted
    public boolean allFainted() {
        for (Pokemon pokemon : this.party.values()) {
            if (pokemon.HP > 0) {
                return false;
            }
        }
        return true;
    }
    public boolean isInBattle() {
        return this.inBattle;
    }

    public boolean isBlackedOut() {
        return this.blackedOut;
    }

    public Pokemon getFirst() {
        return this.getPartyPokemon(1);
    }

    // gets party pokemon based on slot
    public Pokemon getPartyPokemon(int slot) {
        return this.party.get(slot);
    }



    // Potions

    public void checkPotions() {
        System.out.println("All Potions:");
        for (Map.Entry<Spray,Integer> entry : this.potions.entrySet()) {
            Spray key = entry.getKey();
            int value = entry.getValue();
            String item = key.name + " -- " + value;
            System.out.println(item);
        }
        System.out.println("");
    }

    public int checkNumberPotions() { return this.potions.get(p); }
    public int checkNumberSuperPotions() { return this.potions.get(sp); }
    public int checkNumberHyperPotions() { return this.potions.get(hp); }

    public void usePotion(Pokemon pokemon) {
        if (this.potions.get(p) > 0) {
            p.heal(pokemon);
            this.potions.put(p, this.potions.get(p) - 1);
        } else {
            System.out.println("You don't have any " + p.name + "'s left!");
        }
        System.out.println("");
    }

    public void useSuperPotion(Pokemon pokemon) {
        if (this.potions.get(sp) > 0) {
            sp.heal(pokemon);
            this.potions.put(sp, this.potions.get(sp) - 1);
        } else {
            System.out.println("You don't have any " + sp.name + "'s left!");
        }
        System.out.println("");
    }

    public void useHyperPotion(Pokemon pokemon) {
        if (this.potions.get(hp) > 0) {
            hp.heal(pokemon);
            this.potions.put(hp, this.potions.get(hp) - 1);
        } else {
            System.out.println("You don't have any " + hp.name + "'s left!");
        }
        System.out.println("");
    }

    public void addPotions(int number) {
        this.potions.put(p, this.potions.get(p) + number);
    }

    public void addSuperPotions(int number) {
        this.potions.put(sp, this.potions.get(sp) + number);
    }

    public void addHyperPotions(int number) {
        this.potions.put(hp, this.potions.get(hp) + number);
    }


    // Pokeballs

    public void checkBalls() {
        String bag = "All Pokeballs:";
        System.out.println(bag);
        for (Map.Entry<Ball,Integer> entry : this.pokeballs.entrySet()) {
            Ball key = entry.getKey();
            int value = entry.getValue();
            String item = key.name + " -- " + value;
            System.out.println(item);
        }
        System.out.println("");
    }

    public int checkNumberPokeBalls() {
        return this.pokeballs.get(pb);
    }

    public int checkNumberGreatBalls() {
        return this.pokeballs.get(gb);
    }

    public int checkNumberUltraBalls() {
        return this.pokeballs.get(ub);
    }

    public void throwPokeBall(Pokemon pokemon) {
        if (this.pokeballs.get(pb) > 0) {
            System.out.println("You threw the " + pb.name + "!");
            pb.shake(pokemon);
            this.pokeballs.put(pb, this.pokeballs.get(pb) - 1);

            if (pb.numberOfShakes(pokemon) == 4) {
                this.caughtPokemon(pokemon);
                this.setToWalking();
            }
        } else {
            System.out.println("You don't have any " + pb.name + "'s left!");
        }
        System.out.println("");
    }

    public void throwGreatBall(Pokemon pokemon) {
        if (this.pokeballs.get(gb) > 0) {
            System.out.println("You threw the " + gb.name + "!");
            gb.shake(pokemon);
            this.pokeballs.put(gb, this.pokeballs.get(gb) - 1);

            if (gb.numberOfShakes(pokemon) == 4) {
                this.caughtPokemon(pokemon);
                this.setToWalking();
            }
        } else {
            System.out.println("You don't have any " + gb.name + "'s left!");
        }
        System.out.println("");
    }

    public void throwUltraBall(Pokemon pokemon) {
        if (this.pokeballs.get(ub) > 0) {
            System.out.println("You threw the " + ub.name + "!");
            ub.shake(pokemon);
            this.pokeballs.put(ub, this.pokeballs.get(ub) - 1);

            if (ub.numberOfShakes(pokemon) == 4) {
                this.caughtPokemon(pokemon);
                this.setToWalking();
            }
        } else {
            System.out.println("You don't have any " + ub.name + "'s left!");
        }
        System.out.println("");
    }

    public void addPokeBalls(int number) {
        this.pokeballs.put(pb, this.pokeballs.get(pb) + number);
    }

    public void addGreatBalls(int number) {
        this.pokeballs.put(gb, this.pokeballs.get(gb) + number);
    }

    public void addUltraBalls(int number) {
        this.pokeballs.put(ub, this.pokeballs.get(ub) + number);
    }

    public String toString() {
        return this.name + " Total number of pokemon caught: " + (this.party.size() + this.pc.size());
    }
}
