package com.cayetano.entrytask.service;

import com.cayetano.entrytask.controller.GameStatusException;
import com.cayetano.entrytask.controller.InsufficientBalanceException;
import com.cayetano.entrytask.entity.Card;

public interface GameService {
    Card start(int balance) throws IllegalArgumentException;

    Card reshuffle() throws GameStatusException;

    Round bet(int stake, boolean higher) throws GameStatusException, InsufficientBalanceException;
}
