package com.cayetano.entrytask.service;

import com.cayetano.entrytask.controller.GameStatusException;
import com.cayetano.entrytask.controller.InsufficientBalanceException;
import com.cayetano.entrytask.entity.Card;
import com.cayetano.entrytask.entity.Result;
import com.cayetano.entrytask.entity.Round;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {

    private Random rand;

    private int currentPlayerBalance;
    private List<Card> deck;

    private Card lastDrawnCard;

    public GameServiceImpl() {
        this.rand = new Random();
        this.currentPlayerBalance = 0;
        this.lastDrawnCard = null;
    }

    public Card getLastDrawnCard() {
        return lastDrawnCard;
    }

    public void setLastDrawnCard(Card lastDrawnCard) {
        this.lastDrawnCard = lastDrawnCard;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    private int getCurrentPlayerBalance() {
        return currentPlayerBalance;
    }

    private void setCurrentPlayerBalance(int currentPlayerBalance) {
        this.currentPlayerBalance = currentPlayerBalance;
    }

    @Override
    public Card start(int balance) throws IllegalArgumentException {
        if(balance <= 0) {
            throw new IllegalArgumentException("Nonpositive balance is not permitted.");
        } else {
            setCurrentPlayerBalance(balance);
            composeDeck();
            Card drawnCard = drawCard();
            setLastDrawnCard(drawnCard);
            return drawnCard;
        }
    }

    private Card drawCard() {
        int cardPosition = rand.nextInt(deck.size());
        return deck.remove(cardPosition);
    }

    private void composeDeck() {
        List<String> suits = Card.getValidSuitNames();
        List<String> faces = Card.getValidFaceNames();
        List<Card> deck = new LinkedList<>();

        for(String suit: suits) {
            for(String face: faces) {
                deck.add(new Card(suit, face));
            }
        }

        Collections.shuffle(deck);
        for(Card card: deck) {
            System.out.println(card);
        }

        setDeck(deck);
    }

    @Override
    public Card shuffle() throws GameStatusException {
        if(deck == null) throw new GameStatusException("You haven't started a game yet.");
        return start(currentPlayerBalance);
    }

    @Override
    public Round bet(int stake, boolean higher) throws GameStatusException, InsufficientBalanceException {
        if(this.lastDrawnCard == null) {
            throw new GameStatusException("You haven't started a game yet.");
        } else if(stake > currentPlayerBalance) {
            throw new InsufficientBalanceException(stake, currentPlayerBalance);
        } else if(this.getDeck().size() == 0) {
            throw new GameStatusException("Deck is empty. You need to start a new game.");
        } else {

            Result result;
            Card newCard = drawCard();

            GameResult lastHigher = Card.isHigher(newCard, lastDrawnCard);
            if((lastHigher.equals(GameResult.HIGHER) && higher)
                    || (lastHigher.equals(GameResult.LOWER) && !higher)) {
                setCurrentPlayerBalance(currentPlayerBalance + stake);
                result = Result.WIN;
            } else if(lastHigher.equals(GameResult.EQUAL)) {
                result = Result.DRAW;
            } else {
                setCurrentPlayerBalance(currentPlayerBalance - stake);
                result = Result.LOSS;
            }

            setLastDrawnCard(newCard);
            return new Round(newCard, result, currentPlayerBalance);
        }
    }
}
