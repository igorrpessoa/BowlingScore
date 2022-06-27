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
        int currentSum = 0;
        for (String pinfall : round.getPinfallList()) {
            if (pinfall.equals("F")) {
                score += 0;
            } else {
                currentSum += Integer.parseInt(pinfall);
                score += Integer.parseInt(pinfall);

                if (!round.getRoundNumber().equals(10)) {
                    //STRIKE
                    if (pinfall.equals("10")) {
                        score = scoreStrike(round, roundList, score);
                    //SPARE
                    } else if (currentSum == 10) {
                        score = scoreSpare(round, roundList, score);
                    }
                }
            }
        }
        return score;
    }

    private Integer scoreStrike(Round round, List<Round> roundList, Integer score) {
        List<String> pinfallList;

        score = scoreNextRoundPinfall(roundList, score, round.getRoundNumber(), 0);

        pinfallList = roundList.get(round.getRoundNumber()).getPinfallList();
        //get next pinfall if was not an strike
        if (pinfallList.size() > 1) {
            score = scoreNextRoundPinfall(roundList, score, round.getRoundNumber(), 1);
        } else {
            //get next round if was another strike
            score = scoreNextRoundPinfall(roundList, score, round.getRoundNumber() + 1, 0);

        }

        return score;
    }

    private Integer scoreSpare(Round round, List<Round> roundList, Integer score) {
        //verify if it is not last round
        if (!round.getRoundNumber().equals(10)) {
            //get next round pinfall
            score = scoreNextRoundPinfall(roundList, score, round.getRoundNumber(), 0);
        }
        return score;
    }

    private Integer scoreNextRoundPinfall(List<Round> roundList, Integer score, Integer roundNumber, Integer pinfallNumber) {
        List<String> pinfallList;
        pinfallList = roundList.get(roundNumber).getPinfallList();
        String sparePinfall = pinfallList.get(pinfallNumber);
        if (sparePinfall.equals("F")) {
            score += 0;
        } else {
            score += Integer.parseInt(sparePinfall);
        }
        return score;
    }

}
