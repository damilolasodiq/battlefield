About the Game
--------------
The Battlefield game is an interesting console game designed to emulate 2 nations at war.
As a player, you have can choose between a multi-player mode or a CPU simulated mode (single player).

When the game starts, each player is given a battle area(N x M matrix) with a representation of Soldiers
    C - Corporal
    S - Sergeant
    G - General

There are 3 categories of weapons in the game, a Pistol, a Shotgun and a Bazooka. A Soldier can only use weapons according to their ranks.
For example, a Corporal can only use a Pistol, a Sergent can use a Pistol and a Shotgun while a General can use any of the 3 weapons.

For a CPU simulated mode, the computer randomly selects a name from some list of TRANSFORMER names.
For every level, each player is assigned the same number of soldiers, weapons and arsenal.

How to play
-----------
    1) After reading this instructions, you would follow subsequent instructions on the screen to set a game mode (CPU or multi-player)
    1) You will be required to enter a name for your character e.g John, Xavi, Elixir e.t.c
    2) You can type "PAUSE" at any time during a game play to pause the game.

    3) The level information, current player name and health status of both players are shown on the top of the screen at everytime
    4) You can type "STAT" at any moment in the game to check your full stat such as number of Soldier killed, number of soldiers injured,
    number of points, inventory of your arsenal and so on.
    5) To launch an attack, you need to specify the position of your Soldier to carry out the attack to also a position in the battle area
    of your opponent where the attack your be carried out. For example, you type 0 1 3 4 to select your Soldier at row 1 col 0 to carry out
    an attack on row 3 col 4 in the opponent's battle area.
    6) For every attack, you can see the effect on the enemies field.
        'X' means your attack hit a position without a Soldier in which case, you don't get a point but your Soldier weapon loses a bullet.
        '!' means your attack hit a Soldier on the enemies field and the Soldier died.
        '-' means a position on the battle field that is yet to be explored.
    7) Every Soldier type has a range they can shoot for example, a Corporal cannot shoot as far as a Sergeant and a Sergeant cannot shoot as wide as general
    9) If a Soldier tries to shoot to a point on the enemy's area where his cannot reach, his weapon reduces by a round but there's no effect on the enemies field
    and hence, no point awarded.
    10) You can view this instruction anytime during a game play by typing "HELP"


How points are calculated
-------------------------

    1)Anytime you attack an opponent's area that does not have a Soldier or you shoot an area that is out of range or you shoot an already blasted area,
    you do not get a point, yet your weapon reduces by a round
    2)Depending on the Weapon used to attack, a point is gained for every successful attack (i.e when your a players soldier is able to hit another player's solder)
    However, an additional point is awarded whenever a junior ranked officer kills a higher ranked officer e.g when your Corporal hits your enemy's General or Sergent
