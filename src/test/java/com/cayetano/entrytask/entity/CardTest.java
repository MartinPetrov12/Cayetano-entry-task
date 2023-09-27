package com.cayetano.entrytask.entity;

import com.cayetano.entrytask.service.GameResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void isKingHigherThanJack() {
        Card kingOfHearts = new Card("Hearts", "King");
        Card jackOfSpades = new Card("Spades", "Jack");

        assertEquals(Card.isHigher(kingOfHearts, jackOfSpades), GameResult.HIGHER);
    }

    @Test
    void isJackHigherThanKing() {
        Card kingOfHearts = new Card("Hearts", "King");
        Card jackOfSpades = new Card("Spades", "Jack");

        assertEquals(Card.isHigher(jackOfSpades, kingOfHearts), GameResult.LOWER);
    }

    @Test
    void isAceHigherThanAce() {
        Card aceOfSpades = new Card("Spades", "Ace");
        Card aceOfDiamonds = new Card("Diamonds", "Ace");

        assertEquals(Card.isHigher(aceOfSpades, aceOfDiamonds), GameResult.EQUAL);
    }
}