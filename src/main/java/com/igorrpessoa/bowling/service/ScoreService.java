package com.igorrpessoa.bowling.service;

import com.igorrpessoa.bowling.model.Round;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    public List<Round> calculateGameScore(List<Round> roundList) {
        Integer score = 0;
        for (int i = 0; i < roundList.size(); i++) {
            Round round = roundList.get(i);
            score = calculateRound(round, roundList, score);
            round.setScore(score);
        }

        return roundList;
    }

    protected Integer calculateRound(Round round, List<Round> roundList, Integer score) {
        int spareSum = 0;
        for (String pinfall : round.getPinfallList()) {
            if (!pinfall.equals("F")) {
                spareSum += Integer.parseInt(pinfall);
                score += Integer.parseInt(pinfall);

                if (!round.getRoundNumber().equals(10)) {
                    //STRIKE
                    if (pinfall.equals("10")) {
                        score = scoreStrike(roundList, round.getRoundNumber(), score);
                    //SPARE
                    } else if (spareSum == 10) {
                        score = scoreSpare(roundList, round.getRoundNumber(), score);
                    }
                }
            }
        }
        return score;
    }

    private Integer scoreStrike(List<Round> roundList, int roundNumber, Integer score) {

        String pinfall = getPinfall(roundList, roundNumber, 0);
        score = scoreRoundPinfall(score, pinfall);

        //get next pinfall if was not an strike
        if (pinfall.equals("10") && roundNumber < 9) {
            //get next round if was another strike
            score = scoreRoundPinfall(score, getPinfall(roundList, roundNumber + 1, 0));
        } else {
            score = scoreRoundPinfall(score, getPinfall(roundList, roundNumber, 1));
        }

        return score;
    }

    private String getPinfall(List<Round> roundList, int round, int index) {
        return roundList.get(round).getPinfallList().get(index);
    }

    private Integer scoreSpare(List<Round> roundList, Integer roundNumber , Integer score) {
        //verify if it is not last round
        if (!roundNumber.equals(10)) {
            //get next round pinfall
            score = scoreRoundPinfall(score, getPinfall(roundList, roundNumber, 0));
        }
        return score;
    }

    private Integer scoreRoundPinfall(Integer score, String pinfall) {
        if (pinfall.equals("F")) {
            score += 0;
        } else {
            score += Integer.parseInt(pinfall);
        }
        return score;
    }

}
