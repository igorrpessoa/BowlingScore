package com.igorrpessoa.bowling.integTest;

import com.igorrpessoa.bowling.model.ErrorMessagesEnum;
import com.igorrpessoa.bowling.exception.FileFormatException;
import com.igorrpessoa.bowling.exception.RuleException;
import com.igorrpessoa.bowling.service.GameService;
import com.igorrpessoa.bowling.model.Game;
import com.igorrpessoa.bowling.model.Player;
import com.igorrpessoa.bowling.model.Round;
import com.igorrpessoa.bowling.service.PrintService;
import com.igorrpessoa.bowling.service.ReaderService;
import com.igorrpessoa.bowling.service.ScoreService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GameService.class, ScoreService.class, PrintService.class, ReaderService.class})
@EnableConfigurationProperties
public class GameIntegTest {

    @Autowired
    ScoreService scoreService;
    @Autowired
    PrintService printService;
    @SpyBean
    ReaderService readerService;

    @Autowired
    @InjectMocks
    GameService main;

    @Test
    public void givenInputPathWhenCallRunnerThenReturnGameTwoPlayers() throws FileFormatException, RuleException, IOException {
        String path = "positive/scores.txt";

        String fileRead = readFileFromResources(path);
        Mockito.doReturn(path).when(readerService).readFromConsole();
        Mockito.doReturn(fileRead).when(readerService).readFile(path);

        Game bowlingGame = main.startBowlingGame();

        List<Round> listRoundsJeff = bowlingGame.getPlayerMap().get(new Player("Jeff"));
        List<Round> listRoundsJohn = bowlingGame.getPlayerMap().get(new Player("John"));
        Assert.assertNotNull(bowlingGame);
        Assert.assertEquals(2, bowlingGame.getPlayerMap().size());
        Assert.assertEquals( 10, listRoundsJeff.size());
        Assert.assertEquals( Integer.valueOf(167), listRoundsJeff.get(9).getScore());
        Assert.assertEquals( 10, listRoundsJohn.size());
        Assert.assertEquals( Integer.valueOf(151), listRoundsJohn.get(9).getScore());
    }

    @Test
    public void givenInputPathWhenCallRunnerThenReturnGameFullStrike() throws FileFormatException, RuleException, IOException {
        String path = "positive/perfect.txt";
        String fileRead = readFileFromResources(path);
        Mockito.doReturn(path).when(readerService).readFromConsole();
        Mockito.doReturn(fileRead).when(readerService).readFile(path);

        Game bowlingGame = main.startBowlingGame();

        List<Round> listRoundsCarl = bowlingGame.getPlayerMap().get(new Player("Carl"));
        Assert.assertNotNull(bowlingGame);
        Assert.assertEquals(1, bowlingGame.getPlayerMap().size());
        Assert.assertEquals( 10, listRoundsCarl.size());
        Assert.assertEquals( Integer.valueOf(300), listRoundsCarl.get(9).getScore());
    }

    @Test
    public void givenInputPathWhenCallRunnerThenReturnGameFullFault() throws FileFormatException, RuleException, IOException {
        String path = "positive/imperfect.txt";
        String fileRead = readFileFromResources(path);
        Mockito.doReturn(path).when(readerService).readFromConsole();
        Mockito.doReturn(fileRead).when(readerService).readFile(path);

        Game bowlingGame = main.startBowlingGame();

        List<Round> listRoundsCarl = bowlingGame.getPlayerMap().get(new Player("Carl"));
        Assert.assertNotNull(bowlingGame);
        Assert.assertEquals(1, bowlingGame.getPlayerMap().size());
        Assert.assertEquals( 10, listRoundsCarl.size());
        Assert.assertEquals( Integer.valueOf(0), listRoundsCarl.get(9).getScore());
        Assert.assertEquals( 2, listRoundsCarl.get(9).getPinfallList().size());

    }

    @Test
    public void givenEmptyFileWhenStartBowlingGameThenThrowEmptyFileException() throws FileNotFoundException {
        String path = "negative/empty.txt";

        String fileRead = readFileFromResources(path);
        Mockito.doReturn(path).when(readerService).readFromConsole();
        Mockito.doReturn(fileRead).when(readerService).readFile(path);

        FileFormatException fileFormatException = Assert.assertThrows(FileFormatException.class,
                () -> {
                    main.startBowlingGame();
                });

        Assert.assertEquals(ErrorMessagesEnum.FILE_FORMAT_ERROR_EMPTY_ENTRIES.getDescription(), fileFormatException.getMessage());

    }

    @Test
    public void givenExtraScoreWhenStartBowlingGameThenThrowExtraScoreException() throws FileNotFoundException {
        String path = "negative/extra-score.txt";

        String fileRead = readFileFromResources(path);
        Mockito.doReturn(path).when(readerService).readFromConsole();
        Mockito.doReturn(fileRead).when(readerService).readFile(path);

        RuleException ruleException = Assert.assertThrows(RuleException.class,
                () -> {
                    main.startBowlingGame();
                });

        Assert.assertEquals( ErrorMessagesEnum.RULE_ERROR_EXTRA_ROUNDS.getDescription(), ruleException.getMessage());

    }

    @Test
    public void givenFreeTextWhenStartBowlingGameThenThrowExtraScoreException() throws FileNotFoundException {
        String path = "negative/free-text.txt";
        String fileRead = readFileFromResources(path);
        Mockito.doReturn(path).when(readerService).readFromConsole();
        Mockito.doReturn(fileRead).when(readerService).readFile(path);

        FileFormatException fileFormatException = Assert.assertThrows(FileFormatException.class,
                () -> {
                    main.startBowlingGame();
                });

        Assert.assertEquals( ErrorMessagesEnum.FILE_FORMAT_ERROR_PATTERN_NOT_ACCEPTED.getDescription(), fileFormatException.getMessage());

    }

    @Test
    public void givenInvalidScoreWhenStartBowlingGameThenThrowExtraScoreException() throws FileNotFoundException {
        String path = "negative/invalid-score.txt";
        String fileRead = readFileFromResources(path);
        Mockito.doReturn(path).when(readerService).readFromConsole();
        Mockito.doReturn(fileRead).when(readerService).readFile(path);

        FileFormatException fileFormatException = Assert.assertThrows(FileFormatException.class,
                () -> {
                    main.startBowlingGame();
                });

        Assert.assertEquals(ErrorMessagesEnum.FILE_FORMAT_ERROR_PATTERN_NOT_ACCEPTED.getDescription(), fileFormatException.getMessage());

    }

    @Test
    public void givenNegativeWhenStartBowlingGameThenThrowExtraScoreException() throws FileNotFoundException {
        String path = "negative/negative.txt";
        String fileRead = readFileFromResources(path);
        Mockito.doReturn(path).when(readerService).readFromConsole();
        Mockito.doReturn(fileRead).when(readerService).readFile(path);

        FileFormatException fileFormatException = Assert.assertThrows(FileFormatException.class,
                () -> {
                    main.startBowlingGame();
                });

        Assert.assertEquals( ErrorMessagesEnum.FILE_FORMAT_ERROR_PATTERN_NOT_ACCEPTED.getDescription(), fileFormatException.getMessage());

    }

    public String readFileFromResources(String path) throws FileNotFoundException {
        StringBuilder data = new StringBuilder();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine()).append("\n");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(ErrorMessagesEnum.INPUT_ERROR_FILE_NOT_FOUND.getDescription());
        }
        return data.toString();
    }

}
