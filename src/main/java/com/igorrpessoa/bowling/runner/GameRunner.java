package com.igorrpessoa.bowling.runner;

import com.igorrpessoa.bowling.exception.RuleException;
import com.igorrpessoa.bowling.exception.FileFormatException;
import com.igorrpessoa.bowling.service.GameService;
import com.igorrpessoa.bowling.service.PrintService;
import com.igorrpessoa.bowling.service.ReaderService;
import com.igorrpessoa.bowling.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.InputMismatchException;

@Component
public class GameRunner implements CommandLineRunner {

    @Autowired
    public GameRunner(GameService gameService){
        this.gameService = gameService;
    }

    private GameService gameService;

    @Override
    public void run(String... args) {
        while(true) {
            try {
                gameService.startBowlingGame();
            }
            catch (InvalidPathException |
                    FileNotFoundException |
                    InputMismatchException |
                    FileFormatException |
                    RuleException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
