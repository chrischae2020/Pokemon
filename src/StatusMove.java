public class StatusMove extends Move {

    String nameStageChanged;
    int amountStageChanged;
    boolean self;

    public StatusMove(String name, String type, String stageName, int amount, boolean self) {
        super(name, type, 0, 1);
        this.nameStageChanged = stageName;
        this.amountStageChanged = amount;
        this.self = self;
    }

    // Uses status move on pokemon
    public void calculateStageChange(Pokemon pokemon) {
        if (this.nameStageChanged.equals("Attack")) {
            if (pokemon.stageAttack + this.amountStageChanged > 6) {
                System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any higher!");
                pokemon.setStageStat("Attack", 6);
            } else if (pokemon.stageAttack + this.amountStageChanged < -6) {
                System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any lower!");
                pokemon.setStageStat("Attack", -6);
            } else {
                switch (this.amountStageChanged) {
                    case (1):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose!");
                        pokemon.setStageStat("Attack", pokemon.stageAttack + this.amountStageChanged);
                        break;
                    case (2):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " sharply rose!");
                        pokemon.setStageStat("Attack", pokemon.stageAttack + this.amountStageChanged);
                        break;
                    case (3):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose drastically!");
                        pokemon.setStageStat("Attack", pokemon.stageAttack + this.amountStageChanged);
                        break;
                    case (-1):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " fell!");
                        pokemon.setStageStat("Attack", pokemon.stageAttack + this.amountStageChanged);
                        break;
                    case (-2):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " harshly fell!");
                        pokemon.setStageStat("Attack", pokemon.stageAttack + this.amountStageChanged);
                        break;
                    case (-3):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " severely fell!");
                        pokemon.setStageStat("Attack", pokemon.stageAttack + this.amountStageChanged);
                        break;
                }
            }
        } else if (this.nameStageChanged.equals("Defense")) {
            if (pokemon.stageDefense + this.amountStageChanged > 6) {
                System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any higher!");
                pokemon.setStageStat("Defense", 6);
            } else if (pokemon.stageDefense + this.amountStageChanged < -6) {
                System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any lower!");
                pokemon.setStageStat("Defense", -6);
            } else {
                switch (this.amountStageChanged) {
                    case (-1):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " fell!");
                        pokemon.setStageStat("Defense", pokemon.stageDefense + this.amountStageChanged);
                        break;
                    case (-2):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " harshly fell!");
                        pokemon.setStageStat("Defense", pokemon.stageDefense + this.amountStageChanged);
                        break;
                    case (-3):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " severely fell!");
                        pokemon.setStageStat("Defense", pokemon.stageDefense + this.amountStageChanged);
                        break;
                    case (1):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose!");
                        pokemon.setStageStat("Defense", pokemon.stageDefense + this.amountStageChanged);
                        break;
                    case (2):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " sharply rose!");
                        pokemon.setStageStat("Defense", pokemon.stageDefense + this.amountStageChanged);
                        break;
                    case (3):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose drastically!");
                        pokemon.setStageStat("Defense", pokemon.stageDefense + this.amountStageChanged);
                        break;
                }
            }
        } else if (this.nameStageChanged.equals("Speed")) {
            if (pokemon.stageSpeed + this.amountStageChanged > 6) {
                System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any higher!");
                pokemon.setStageStat("Speed", 6);
            } else if (pokemon.stageSpeed + this.amountStageChanged < -6) {
                System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any lower!");
                pokemon.setStageStat("Speed", -6);
            } else {
                switch (this.amountStageChanged) {
                    case (1):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose!");
                        pokemon.setStageStat("Speed", pokemon.stageSpeed + this.amountStageChanged);
                        break;
                    case (2):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " sharply rose!");
                        pokemon.setStageStat("Speed", pokemon.stageSpeed + this.amountStageChanged);
                        break;
                    case (3):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose drastically!");
                        pokemon.setStageStat("Speed", pokemon.stageSpeed + this.amountStageChanged);
                        break;
                    case (-1):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " fell!");
                        pokemon.setStageStat("Speed", pokemon.stageSpeed + this.amountStageChanged);
                        break;
                    case (-2):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " harshly fell!");
                        pokemon.setStageStat("Speed", pokemon.stageSpeed + this.amountStageChanged);
                        break;
                    case (-3):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " severely fell!");
                        pokemon.setStageStat("Speed", pokemon.stageSpeed + this.amountStageChanged);
                        break;
                }
            }
        } else if (this.nameStageChanged.equals("Accuracy")) {
            if (pokemon.stageAccuracy + this.amountStageChanged > 6) {
                System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any higher!");
                pokemon.setStageStat("Accuracy", 6);
            } else if (pokemon.stageAccuracy + this.amountStageChanged < -6) {
                System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any lower!");
                pokemon.setStageStat("Accuracy", -6);
            } else {
                switch (this.amountStageChanged) {
                    case (1):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose!");
                        pokemon.setStageStat("Accuracy", pokemon.stageAccuracy + this.amountStageChanged);
                        break;
                    case (2):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " sharply rose!");
                        pokemon.setStageStat("Accuracy", pokemon.stageAccuracy + this.amountStageChanged);
                        break;
                    case (3):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose drastically!");
                        pokemon.setStageStat("Accuracy", pokemon.stageAccuracy + this.amountStageChanged);
                        break;
                    case (-1):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " fell!");
                        pokemon.setStageStat("Accuracy", pokemon.stageAccuracy + this.amountStageChanged);
                        break;
                    case (-2):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " harshly fell!");
                        pokemon.setStageStat("Accuracy", pokemon.stageAccuracy + this.amountStageChanged);
                        break;
                    case (-3):
                        System.out.println(pokemon.name + "'s " + this.nameStageChanged + " severely fell!");
                        pokemon.setStageStat("Accuracy", pokemon.stageAccuracy + this.amountStageChanged);
                        break;
                }
            }
        } else if (this.nameStageChanged.equals("Evasion")) {
                if (pokemon.stageEvasion + this.amountStageChanged > 6) {
                    System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any higher!");
                    pokemon.setStageStat("Evasion", 6);
                } else if (pokemon.stageEvasion + this.amountStageChanged < -6) {
                    System.out.println(pokemon.name + "'s " + this.nameStageChanged + " won't go any lower!");
                    pokemon.setStageStat("Evasion", -6);
                } else {
                    switch(this.amountStageChanged) {
                        case (1) :
                            System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose!");
                            pokemon.setStageStat("Evasion", pokemon.stageEvasion+this.amountStageChanged);
                            break;
                        case (2) :
                            System.out.println(pokemon.name + "'s " + this.nameStageChanged + " sharply rose!");
                            pokemon.setStageStat("Evasion", pokemon.stageEvasion+this.amountStageChanged);
                            break;
                        case (3) :
                            System.out.println(pokemon.name + "'s " + this.nameStageChanged + " rose drastically!");
                            pokemon.setStageStat("Evasion", pokemon.stageEvasion+this.amountStageChanged);
                            break;
                        case (-1) :
                            System.out.println(pokemon.name + "'s " + this.nameStageChanged + " fell!");
                            pokemon.setStageStat("Evasion", pokemon.stageEvasion+this.amountStageChanged);
                            break;
                        case (-2) :
                            System.out.println(pokemon.name + "'s " + this.nameStageChanged + " harshly fell!");
                            pokemon.setStageStat("Evasion", pokemon.stageEvasion+this.amountStageChanged);
                            break;
                        case (-3) :
                            System.out.println(pokemon.name + "'s " + this.nameStageChanged + " severely fell!");
                            pokemon.setStageStat("Evasion", pokemon.stageEvasion+this.amountStageChanged);
                            break;
                    }
                }
        } else {
            System.out.println("Unknown stage name");
        }
    }


    public String toString() {
        if (this.self) {
            return String.format("Move Name: " + this.name +
                    ", Type: " + this.type +
                    ", Effect: This pokemon's " + this.nameStageChanged + " changes by " + this.amountStageChanged + " stages" + "/n");
        } else {
            return String.format("Move Name: " + this.name +
                    ", Type: " + this.type +
                    ", Effect: The opponent pokemon's " + this.nameStageChanged + " changes by " + this.amountStageChanged + " stages" + "/n");
        }
    }

}
