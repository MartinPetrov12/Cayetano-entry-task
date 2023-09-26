package com.cayetano.entrytask.service;

import com.cayetano.entrytask.controller.FinishedGameException;
import com.cayetano.entrytask.controller.GameNotStartedException;
import com.cayetano.entrytask.controller.InsufficientBalanceException;
import com.cayetano.entrytask.entity.Card;
import com.cayetano.entrytask.entity.Round;

public interface GameService {
    Card start(int balance) throws IllegalArgumentException;

    Card shuffle() throws GameNotStartedException;

    Round bet(int stake, boolean higher) throws FinishedGameException, InsufficientBalanceException, GameNotStartedException;
}
