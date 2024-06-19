# TreasureDiggers
A summer project I was working on before starting my CS studies. To learn more about Java.

### Installation
Requirements:
<br>JAVA 8 with JavaFX. [Oracle](https://www.oracle.com/java/technologies/downloads/#java8-linux) version recommended

Download the [release](https://github.com/alhopasi/TreasureDiggers/releases/)
<br>unzip into selected directory
<br>Run with 'java -jar treasurediggers.jar'

### How to play

KEYS - Player 1:
<br>Up: W
<br>Down: S
<br>Left: A
<br>Right: D
<br>Item 1: Q
<br>Item 2: E

KEYS - Player 2:
<br>Up: NUMPAD_8
<br>Down: NUMPAD_5
<br>Left: NUMPAD_4
<br>Right: NUMPAD_6
<br>Item 1: NUMPAD_7
<br>Item 2: NUMPAD_9

CHEATS:
<br>Z - show map
<br>X - add more movement speed and dig speed to players
<br>MOUSE BUTTONS - Explosion on cursor tile

CHANGING CURRENT ITEMS:
<br>Press the item number (1 or 2) when standing on the Starting Doorway to change your selected item.



### Compiling from the command line
Note: if you have java binaries in Path, this is easier. Otherwise you might need to point to the java binary file to execute javac, jar and java -commands. Also, if you are using windows or linux, some of the basic commands might vary. And directory paths (using \ or /).

Follow these steps to compile TreasureDiggers from source code:
- Use a new and empty directory, where the final game is going to be compiled using these instructions.
- Get the source code from Github `git clone https://github.com/alhopasi/TreasureDiggers.git` or download from https://github.com/alhopasi/TreasureDiggers and unzip the source into chosen directory. (Rename the unzipped directory from `TreasureDiggers-master` to `TreasureDiggers`.)
- Make a directory, where to compile the source and compile the source:
```
mkdir compiled
javac -d compiled/ -verbose -sourcepath TreasureDiggers/src/ TreasureDiggers/src/treasurediggers/TreasureDiggers.java
```
- Copy MANIFEST.MF file to compiled source path:
```
mkdir compiled/META-INF
cp TreasureDiggers/META-INF/MANIFEST.MF compiled/META-INF/
cd compiled
```
- Create the jar with MANIFEST bundled:
```
jar cvfm ../treasurediggers.jar META-INF/MANIFEST.MF treasurediggers
```

- Copy image files and config.cfg needed to run the java application
```
cd ..
cp TreasureDiggers/images/* ./
cp TreasureDiggers/config.cfg ./
java -jar treasurediggers.jar
```

### Version notes
#### v1.0.1 - released 19.6.2024
Added config.cfg, which can be used to set parameters and change input keys.
<br>Use [javafx KeyCodes](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyCode.html) for correct keycodes

#### v1.0.0 - released summer 2018
Made the game more fast paced.
<br>Added check for game end (death). Currently it finishes the animation if one is going on... like setting up the bomb.
<br>Gamemap is now scalable. However, if played with less than 40 width, player 2 info is not visible.