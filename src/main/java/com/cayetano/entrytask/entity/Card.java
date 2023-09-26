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

    public static GameResult isHigher(Card lastDrawnCard, Card newCard) {
        int lastPos = Card.getValidFaceNames().indexOf(lastDrawnCard.getFace());
        int newPos = Card.getValidFaceNames().indexOf(newCard.getFace());
        if(lastPos > newPos) {
            return GameResult.HIGHER;
        } else if(lastPos < newPos) {
            return GameResult.LOWER;
        } else {
            return GameResult.EQUAL;
        }
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        List<String> validSuitNames = getValidSuitNames();
        if(validSuitNames.contains(suit)) {
            this.suit = suit;
        }
    }

    public static List<String> getValidSuitNames() {
        return List.of("Hearts", "Spades", "Diamonds", "Clubs");
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        List<String> validFaceNames = getValidFaceNames();
        if(validFaceNames.contains(face)) {
            this.face = face;
        }
    }

    public static List<String> getValidFaceNames() {
        return List.of("2", "3", "4", "5", "6", "7", "8", "9", "10",
                        "Jack", "Queen", "King", "Ace");
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit='" + suit + '\'' +
                ", face='" + face + '\'' +
                '}';
    }
}
