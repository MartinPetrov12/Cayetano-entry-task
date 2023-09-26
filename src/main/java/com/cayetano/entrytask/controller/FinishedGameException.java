package com.cayetano.entrytask.controller;

public class FinishedGameException extends RuntimeException {
    public FinishedGameException(String message) {
        super(message);
    }
}
