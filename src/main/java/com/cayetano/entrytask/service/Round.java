package com.cayetano.entrytask.service;

import com.cayetano.entrytask.entity.Card;
import com.cayetano.entrytask.entity.Result;

public class Round {
    private Card drawnCard;
    private Result result;
    private int newBalance;

    public Round(Card drawnCard, Result result, int newBalance) {
        this.drawnCard = drawnCard;
        this.result = result;
        this.newBalance = newBalance;
    }

    public Card getDrawnCard() {
        return drawnCard;
    }

    public void setDrawnCard(Card drawnCard) {
        this.drawnCard = drawnCard;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(int newBalance) {
        this.newBalance = newBalance;
    }
}
