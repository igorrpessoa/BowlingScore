package com.igorrpessoa.bowling.service;

import com.igorrpessoa.bowling.model.Game;
import com.igorrpessoa.bowling.model.Player;
import com.igorrpessoa.bowling.model.Round;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PrintService {

    public void printScore(Game game) {

        //PRINT ROUND NUMBERS
        System.out.print("Frame\t\t");
        for (int roundNumber = 1; roundNumber <= 10; roundNumber++) {
            System.out.print(roundNumber + 	"\t\t");
        }
        for (Map.Entry<Player, List<Round>> pair : game.getPlayerMap().entrySet()) {

            printPlayerGame(pair.getKey(), pair.getValue());
            System.out.println();

        }
    }

    private void printPlayerGame(Player player, List<Round> value) {

        System.out.println();
        //PRINT PLAYER NAME
        System.out.println(player.getName());
        //PRINT PINFALLS
        printPinfalls(value);
        //PRINT SCORE
        printScore(value);
    }

    private void printScore(List<Round> value) {
        System.out.print("Score\t\t");
        value.forEach(round -> {
            System.out.print(round.getScore() + "\t\t");
        });
    }

    private void printPinfalls(List<Round> value) {
        System.out.print("Pinfalls\t");
        value.forEach(round -> {
            List<String> pinfallList = round.getPinfallList();
            int sum = 0;
            for (String pinfall : pinfallList) {
                if(!pinfall.equals("F")) {
                    if(pinfall.equals("10")) {
                        if(round.getRoundNumber() == 10) {
                            System.out.print("X" + "\t");
                        } else {
                            System.out.print("   " + "X" + "\t");
                        }
                    } else {
                        sum += Integer.parseInt(pinfall);
                        if(sum == 10) {
                            System.out.print("/" + "\t");
                        } else {
                            System.out.print(pinfall + "\t");
                        }
                    }
                } else {
                    System.out.print(pinfall + "\t");
                }
            }
        });
        System.out.println();
    }


}
