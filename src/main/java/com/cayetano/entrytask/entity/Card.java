package com.cayetano.entrytask.entity;

import com.cayetano.entrytask.service.GameResult;

import java.util.List;

public class Card {
    private String suit;
    private String face;

    public Card(String suit, String face) {
        this.suit = suit;
        this.face = face;
    }

    public String getSuit() {
        return suit;
    }

    public String getFace() {
        return face;
    }

    /**
     * Gets a list of all possible card faces. The elements are in ascending
     * order in terms of rank, with 2 having the lowest rank, and
     * the ace having the highest rank.
     *
     * @return
     */
    public static List<String> getValidFaceNames() {
        return List.of("2", "3", "4", "5", "6", "7", "8", "9", "10",
                "Jack", "Queen", "King", "Ace");
    }

    /**
     * Gets a list of the all possible card suits.
     * The suits are irrelevant for the ranks of the cards.
     * @return
     */
    public static List<String> getValidSuitNames() {
        return List.of("Hearts", "Spades", "Diamonds", "Clubs");
    }

    public void setSuit(String suit) {
        List<String> validSuitNames = getValidSuitNames();
        if(validSuitNames.contains(suit)) {
            this.suit = suit;
        }
    }

    public void setFace(String face) {
        List<String> validFaceNames = getValidFaceNames();
        if(validFaceNames.contains(face)) {
            this.face = face;
        }
    }

    /**
     * The method returns the result of comparing the ranks of two cards.
     * Since getValidFaceNames() returns the list of cards in ascending order
     * in terms of ranks, it is sufficient to compare the cards by their index
     * in the array.
     *
     * @param lastDrawnCard - the last drawn card before the round
     * @param newCard - the newly drawn card
     * @return - a GameResult enum, stating if the lastDrawnCard is higher than
     * the newCard
     */
    public static GameResult isHigher(Card lastDrawnCard, Card newCard) {
        List<String> validFaceNames = Card.getValidFaceNames();
        int lastPos = validFaceNames.indexOf(lastDrawnCard.getFace());
        int newPos = validFaceNames.indexOf(newCard.getFace());

        if(lastPos > newPos) {
            return GameResult.HIGHER;
        } else if(lastPos < newPos) {
            return GameResult.LOWER;
        } else {
            return GameResult.EQUAL;
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit='" + suit + '\'' +
                ", face='" + face + '\'' +
                '}';
    }
}
