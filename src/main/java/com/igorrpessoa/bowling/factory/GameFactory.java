package com.igorrpessoa.bowling.factory;

import com.igorrpessoa.bowling.exception.ErrorMessagesEnum;
import com.igorrpessoa.bowling.exception.RuleException;
import com.igorrpessoa.bowling.exception.FileFormatException;
import com.igorrpessoa.bowling.model.Game;
import com.igorrpessoa.bowling.model.Round;
import com.igorrpessoa.bowling.model.Player;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class GameFactory {

    public static final String END_LINE = "\n";
    public static final String TAB = "\t";


    public static Game createGame(String gameInput) throws RuleException, FileFormatException {

        HashMap<Player, List<Round>> playerMap = new HashMap<>();
        String currentPlayer;
        List<Round> roundList;
        Boolean nextRound = true;
        Round round;
        for (String line : gameInput.split(END_LINE)) {

            validateFileFormat(line);

            String[] splitLine = line.split(TAB);
            String pinfallString = splitLine[1];

            currentPlayer = splitLine[0];
            Player player = new Player(currentPlayer);
            if(!playerMap.containsKey(player)) {
                roundList = new ArrayList<>();
                round = createNewRound(roundList, pinfallString);
            } else {
                roundList = playerMap.get(player);
                if(nextRound) {
                    round = createNewRound(roundList, pinfallString);
                } else {
                    round = addPinfallToExistingRound(roundList, pinfallString);
                }
            }
            validateRule(roundList);

            nextRound = isFinishedAddingLastRound(round) || isStrikeOrFinishedReadingRegularRound(pinfallString, round);
            playerMap.put(player, roundList);
        }
        return Game.builder().playerMap(playerMap).build();
    }

    private static void validateRule(List<Round> roundList) throws RuleException {
        if(roundList.size() > 10) {
            throw new RuleException(ErrorMessagesEnum.RULE_ERROR_EXTRA_ROUNDS.getDescription());
        }
    }

    private static void validateFileFormat(String line) throws FileFormatException {
        if(Strings.isEmpty(line)) {
            throw new FileFormatException(ErrorMessagesEnum.FILE_FORMAT_ERROR_EMPTY_ENTRIES.getDescription());
        }
        String[] splitLine = line.split(TAB);
        if(splitLine.length < 2){
            throw new FileFormatException(ErrorMessagesEnum.FILE_FORMAT_ERROR_PATTERN_NOT_ACCEPTED.getDescription());
        }
        Pattern p = Pattern.compile("(?<!\\S)\\d(?!\\S)|(10)|(F)");
        String pinfallString = splitLine[1];
        if(!p.matcher(pinfallString).matches()){
            throw new FileFormatException(ErrorMessagesEnum.FILE_FORMAT_ERROR_PATTERN_NOT_ACCEPTED.getDescription());
        }
    }

    private static Round addPinfallToExistingRound(List<Round> roundList, String pinfallString) {
        List<String> pinfallList;
        Round round;
        round = roundList.get(roundList.size()-1);
        pinfallList = round.getPinfallList();
        pinfallList.add(pinfallString);
        round.setPinfallList(pinfallList);
        round.setScore(0);
        roundList.set(roundList.size()-1, round);
        return round;
    }

    private static boolean isStrikeOrFinishedReadingRegularRound(String pinfallString, Round round) {
        return (pinfallString.equals("10") || round.getPinfallList().size() == 2) && !isLastRound(round);
    }

    private static boolean isFinishedAddingLastRound(Round round) {
        return isLastRound(round) && round.getPinfallList().size() == 3;
    }

    private static boolean isLastRound(Round round){
        return round.getRoundNumber() == 10;
    }
    private static Round createNewRound(List<Round> roundList,String pinfallString) {
        List<String> pinfallList;
        Round round;
        pinfallList = new ArrayList<>();
        round = new Round(roundList.size() + 1);
        pinfallList.add(pinfallString);
        round.setPinfallList(pinfallList);
        roundList.add(round);
        return round;
    }

}
