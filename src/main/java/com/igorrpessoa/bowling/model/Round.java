package com.igorrpessoa.bowling.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Round {

    public Round(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }
    private Integer roundNumber;

    private List<String> pinfallList;

    private Integer score;

}
