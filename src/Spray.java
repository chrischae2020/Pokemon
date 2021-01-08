public abstract class Spray {

    final int heal;
    String name;

    public Spray(int heal) {
        this.heal = heal;
        this.name = "Spray";
    }

    public void heal(Pokemon pokemon) {
        System.out.println("You used the " + this.name + " on " + pokemon.name);
        System.out.println("");
        if ((pokemon.HP + this.heal) > pokemon.maxHP) {
            int toHeal = pokemon.maxHP - pokemon.HP;
            pokemon.healHP(toHeal);
            System.out.println(pokemon.name + " healed for " + toHeal + " HP");
        } else {
            pokemon.healHP(this.heal);
            System.out.println(pokemon.name + " healed for " + this.heal + " HP");
        }
        System.out.println(pokemon.name + " new HP: " + pokemon.HP);
    }


}
