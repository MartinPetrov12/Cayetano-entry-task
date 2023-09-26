package com.cayetano.entrytask.controller;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(int stake, int balance) {
        super("You do not have sufficient funds for this bet.\n" +
                "Bet size: " + stake + ". \n " +
                "Your balance: " + balance + ".");
    }
}
