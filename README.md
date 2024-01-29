
# TRIVIA GAME
## Description
A game that involves competing to answer general knowledge questions to achieve a better score than the opponent. Players take turns selecting question categories and try to provide the quickest answers. There is a 30-second time limit, and points denoted by '$' are awarded based on the speed of the response. Each player goes through 5 rounds, and the questions are unique in each round. THE GAME WORKS ONLY ON A LOCAL MACHINE!

---

## Technologies Used:
- Java (Oracle OpenJDK version 21.02.2)
- JavaFX (javafx: 21-ea-24)
- sqlite-jdbc-3.40.1.0
- Maven for project packaging
- And the plugins and dependencies specified in the "pom.xml" file

---

## User Guide:
### The game can be launched in two ways, but both require having Java installed with a minimum version of 21.
Link to download Java installer: https://www.oracle.com/java/technologies/downloads/

After installation, open the command prompt (cmd) and type 'java --version'. If you receive a message about the installed Java version, you can proceed.

--

### Using Java Only
1. Navigate to the project's target->app->bin folder.
2. In the address bar, replace the current path with 'cmd'.
3. When the console opens, type 'app.bat' to launch the game.
4. Enter a nickname and choose the "Host" option.
5. Repeat steps 2, 3, and 4, but this time, after entering the nickname, choose the "Guest" option.
6. On the Guest's screen, information about the server created by the Host should be displayed, along with a "Join" button. After joining, start the game.

**Instead of step 2, you can manually find the 'app.bat' file in the folder and double-click on it.**

This method is possible thanks to the generation of a local JavaFX environment using Maven with the Jlink plugin. This eliminates the need to install JavaFX on your computer.

---

### Using Java and JavaFX
1. After downloading Java, download JavaFX version 21 from this link: https://gluonhq.com/products/javafx/. Choose the correct version for your operating system and the "SDK" type.
2. Extract the zip file to a chosen folder.
3. Navigate to javafx-sdk-21.0.2->lib and copy the path to this folder.
4. In the project files, go to out->artifacts->Trivia_Game_jar.
5. In the address bar, replace the current path with 'cmd'.
6. Type the following command: 'java --module-path "<path to the copied lib folder>" --add-modules javafx.controls,javafx.fxml -jar Trivia_Game.jar'.
7. The game should start. Enter a nickname and choose the "Host" option.
8. Repeat steps 5 and 6, but after entering the nickname, choose the "Guest" option.
9. On the Guest's screen, information about the server created by the Host should be displayed, along with a "Join" button. After joining, start the game.

In this method, we use the project artifact created by Maven but without the Jlink plugin. Therefore, we need to provide the JavaFX environment to run JavaFX classes, which is done by downloading JavaFX and specifying it in the command that launches the game.

---

## Additional Game Information
The game uses an SQLite database to store statistics related to gameplay. If the game is restarted, and a nickname that has been used before is entered, it updates the statistics for the existing player instead of creating a new one. The game uses TCP protocol and multithreading, so if one player closes it before finishing, it results in an error and the inability to continue the game for both players. In that case, the game needs to be restarted. Although the game is played in a 1 vs. 1 format, multiple instances can be launched simultaneously, remembering to always join the game as the host first and then as the guest.

---

## Preview
https://github.com/kurczakooo/Trivia_Game/assets/121618421/83e55f8e-4fd1-420b-bf89-4ebccdfce69c

