# Pokemon
Pokemon text adventure game made with Intellij (Java)
> This game is meant for educational purposes only with no intent to distribute


## **Version 0.2.0** 
*Completed 1/8/2021*

Release Notes
* Trainer can have a total of 6 pokemon in party, and unlimited in PC
* Trainer can heal and catch pokemon in battle
* Main PC operations like in the original games are added (withdrawing, depositing pokemon)
* Three types of pokeballs/potions (Poke, Great, Ultra) and (Potion, Super, Hyper)
* Catch rate formulas from Gen 3 used
* Stat stage changes from moves (not yet added as secondary effects from attacks)
* Trainer randomly recieves items from walking
* Battle functions from original games added (Switch, Fight, Bag, Run)
* Type multipliers from attacks from weakness/resistence following Gen 5 standards (No fairy)
* Removed HP healing from walking
* Pokemon can learn up to 3 moves
* Pokemon can learn a random move when a certain level is hit (5, 10, 15, etc)
* All pokemon who participated in battle can recieve EXP

Fixed 
* EXP gain when battling level 1 opponents

Known Bugs
* User input types must match int, String, for some conditions or InputMismatchException will be thrown
* One instance, a random wild pokemon (Chansey) dealt ~1000000 damage 


## **Version 0.1.0**
*Completed 1/3/2021*

Release Notes
* Trainer can choose a starter (Charmander, Squirtle, Bulbasaur, Pikachu)
* Can battle randomly spawned wild pokemon by walking
* One pokemon per trainer 
* Walking heals pokemon HP
* EXP gain and level up from winning
* Pokemon only know one move
* Move damage calculated from Gen 3 formulas

Known Bugs
* When battling level 1 opponents, pokemon would not gain EXP 


