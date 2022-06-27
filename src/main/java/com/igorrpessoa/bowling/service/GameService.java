package com.igorrpessoa.bowling.service;

import com.igorrpessoa.bowling.model.ErrorMessagesEnum;
import com.igorrpessoa.bowling.exception.RuleException;
import com.igorrpessoa.bowling.exception.FileFormatException;
import com.igorrpessoa.bowling.factory.GameFactory;
import com.igorrpessoa.bowling.model.Game;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

@Service
public class GameService {

    @Autowired
    public GameService(ReaderService readerService, ScoreService scoreService, PrintService printService){
        this.readerService = readerService;
        this.scoreService = scoreService;
        this.printService = printService;
    }

    private PrintService printService;

    private ReaderService readerService;

    private ScoreService scoreService;

    public Game startBowlingGame() throws FileNotFoundException, RuleException, FileFormatException {
        System.out.println("Please insert a valid path to the file");
        Game bowlingGame;

        String filePath = readerService.readFromConsole();
        filePath = treatFilePath(filePath);
        String fileData = readerService.readFile(filePath);
        bowlingGame = GameFactory.createGame(fileData);
        bowlingGame.getPlayerMap().values()
            .forEach(roundList -> {
                scoreService.calculateGameScore(roundList);
            });

        printService.printScore(bowlingGame);
        return bowlingGame;
    }

    protected String treatFilePath(String filePath) {
        if(Strings.isEmpty(filePath)) {
            throw new InvalidPathException(filePath, ErrorMessagesEnum.INPUT_ERROR_EMPTY_ENTRIES.getDescription());
        }
        if(!filePath.contains(".txt")) {
            return filePath.trim() + ".txt";
        }
        return filePath.trim();
    }
}
