# Java Challenge

### How it works
This application is a bowling score calculator and printer.
It will calculate the score of each player on every round and print the final game into the console.
The application runs eternally waiting for input from the console from the user. 
The user will have to input a valid file path with the data to be analysed and scored.


### Reference Documentation

The inputs will validate:
- It has to point to a valid path. It can be absolute from the projects path or the entire path;
- The file should contain players ball throws and it can contain from 1 to n players;
- The file has to have the following pattern:
  - Player Name followed by TAB and a value from 0 to 10 OR F player committed a fault; 
- Each player can have 2 balls thrown by round (if strike or spare on 10th round, it can have 3 balls);
- It has to have the exactly 10 rounds for each player;

### To run

This application runs the Java Challenge. To run this application you have to:
1. Go to the project path;
2. Run ```mvn clean install ``` 
3. Run ```mvn spring-boot:run ```


### Technologies used
- Java 11
- Maven
- Spring-boot

**This application was created by Igor Rodrigues Pessoa**
