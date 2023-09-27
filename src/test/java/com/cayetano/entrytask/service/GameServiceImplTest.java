package com.cayetano.entrytask.service;

import com.cayetano.entrytask.controller.GameStatusException;
import com.cayetano.entrytask.controller.InsufficientBalanceException;
import com.cayetano.entrytask.entity.Card;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceImplTest {

    GameServiceImpl gameService = new GameServiceImpl();

    @Test
    void startThrowsOnNonpositiveBalance() {
        assertThrows(IllegalArgumentException.class, () -> gameService.start(0));
    }

    @Test
    void startRunsSuccessfullyOnValidBalance() {
        int initialBalance = 100;
        Card card = gameService.start(initialBalance);
        assertTrue(Card.getValidFaceNames().contains(card.getFace())
                && Card.getValidSuitNames().contains(card.getSuit()));
        assertEquals(initialBalance, gameService.getPlayerBalance());
        assertNotNull(gameService.getDeck());
        assertEquals(51, gameService.getDeck().size());
    }

    @Test
    void reshuffleThrowsExceptionOnNullDeck() {
        assertThrows(GameStatusException.class, () -> gameService.reshuffle());
    }

    @Test
    void betThrowsOnGameNotStarted() {
        assertThrows(GameStatusException.class, () -> gameService.bet(100, true));
    }

    @Test
    void betThrowsOnInsufficientBalance() {
        gameService.start(10);
        assertThrows(InsufficientBalanceException.class, () -> gameService.bet(100, true));
    }

    @Test
    void betThrowsOnEmptyDeck() {
        gameService.start(1000);
        for(int i=0; i<51; i++) {
            gameService.bet(1, true);
        }
        assertThrows(GameStatusException.class, () -> gameService.bet(1, true));
    }
}