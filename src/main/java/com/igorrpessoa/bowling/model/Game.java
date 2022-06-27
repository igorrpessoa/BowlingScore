package com.igorrpessoa.bowling.model;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@Builder
@Getter
public class Game {

    private HashMap<Player, List<Round>> playerMap;
}
