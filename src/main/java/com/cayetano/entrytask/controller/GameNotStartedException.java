package com.cayetano.entrytask.controller;

public class GameNotStartedException extends RuntimeException {
    public GameNotStartedException() {
        super("You haven't started a game yet.");
    }
}
