package com.igorrpessoa.bowling.service;

import com.igorrpessoa.bowling.model.Round;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GameService.class, ScoreService.class, PrintService.class, ReaderService.class})
@EnableConfigurationProperties
public class ScoreServiceTest {

    @Autowired
    ScoreService scoreService;
    @Mock
    PrintService printService;
    @Mock
    ReaderService readerService;
    @Mock
    GameService gameService;

    private List<Round> roundList;
    private Round roundStrike1;
    private Round roundStrike2;
    private Round roundNormal;
    private Round roundFault;

    @Before
    public void initRounds(){
        roundStrike1 = Round.builder()
                .pinfallList(List.of("10"))
                .build();
        roundStrike2 = Round.builder()
                .pinfallList(List.of("10"))
                .build();
        roundNormal = Round.builder()
                .pinfallList(List.of("3" ,"4"))
                .build();
        roundFault = Round.builder()
                .pinfallList(List.of("F" ,"F"))
                .build();
    }



    @Test
    public void givenNormalRoundWhenCalculateRoundThenScoreNormal() throws IOException {
        roundNormal.setRoundNumber(1);
        roundList = List.of(roundNormal);

        Integer score = scoreService.calculateRound(roundNormal, roundList, 0);

        Assert.assertEquals(Integer.valueOf(7), score);
    }

    @Test
    public void givenStrikeAndNormalRoundWhenCalculateRoundThenScoreSumBonusPinfall() throws IOException {
        roundStrike1.setRoundNumber(1);
        roundNormal.setRoundNumber(2);
        roundList = List.of(roundStrike1, roundNormal);

        Integer score = scoreService.calculateRound(roundStrike1, roundList, 10);

        Assert.assertEquals(Integer.valueOf(27), score);
    }

    @Test
    public void givenTwoStrikesWhenCalculateRoundThenScoreSumTwoBonusPinfall() throws IOException {
        roundStrike1.setRoundNumber(1);
        roundStrike2.setRoundNumber(2);
        roundNormal.setRoundNumber(3);
        roundList = List.of(roundStrike1, roundStrike2, roundNormal);

        Integer score = scoreService.calculateRound(roundStrike1, roundList, 3);

        Assert.assertEquals(Integer.valueOf(26), score);
    }

    @Test
    public void givenFaultWhenCalculateRoundThenScoreSumZero() throws IOException {
        roundNormal.setRoundNumber(1);
        roundFault.setRoundNumber(2);
        roundList = List.of(roundNormal, roundFault);

        Integer score = scoreService.calculateRound(roundNormal, roundList, 0);

        Assert.assertEquals(Integer.valueOf(7), score);
    }
}