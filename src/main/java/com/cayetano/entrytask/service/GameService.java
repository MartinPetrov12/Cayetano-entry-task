package com.cayetano.entrytask.service;

import com.cayetano.entrytask.entity.Card;
import com.cayetano.entrytask.entity.Round;

public interface GameService {
    Card start(int balance) throws Exception;

    Card shuffle() throws Exception;

    Round bet(int stake, boolean higher) throws Exception;
}
