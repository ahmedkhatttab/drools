package com.example.drools_app.model;

import lombok.Data;

@Data
public class Score {
    private Long finalScore = 0L;

    public Long calcScore(Long score){
    return this.finalScore = finalScore+score;
}

    public Long getFinalScore(){
    return this.finalScore;
}

}