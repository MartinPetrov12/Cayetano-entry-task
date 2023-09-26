package com.cayetano.entrytask.service;

import com.cayetano.entrytask.entity.Card;
import com.cayetano.entrytask.entity.Result;
import com.cayetano.entrytask.entity.Round;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {

    private Random rand;

    private int currentPlayerBalance;
    private List<Card> deck;

    private Card lastDrawnCard;

    public Card getLastDrawnCard() {
        return lastDrawnCard;
    }

    public void setLastDrawnCard(Card lastDrawnCard) {
        this.lastDrawnCard = lastDrawnCard;
    }

    public GameServiceImpl() {
        this.rand = new Random();
        this.currentPlayerBalance = 0;
        this.lastDrawnCard = null;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
//        for(Card card: deck) {
//            System.out.println(card);
//        }
    }

    private int getCurrentPlayerBalance() {
        return currentPlayerBalance;
    }

    private void setCurrentPlayerBalance(int currentPlayerBalance) {
        this.currentPlayerBalance = currentPlayerBalance;
    }

    @Override
    public Card start(int balance) throws Exception {
        if(balance < 0) {
            throw new Exception("Negative balance is not permitted");
        } else {
            setCurrentPlayerBalance(balance);
            composeDeck();
            Card drawnCard = drawCard();
            System.out.println("New deck size: " + deck.size());
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
        for (int i = 0; i < 51; i++)
        {
            int r = i + rand.nextInt(52 - i);

            Card temp = deck.get(r);
            deck.set(r, deck.get(i));
            deck.set(i, temp);
        }

        setDeck(deck);
    }

    @Override
    public Card shuffle() throws Exception {
        return start(currentPlayerBalance);
    }

    @Override
    public Round bet(int stake, boolean higher) throws Exception {
        if(this.lastDrawnCard == null) {
            throw new Exception("You haven't drawn a card yet");
        } else if(stake > currentPlayerBalance) {
            throw new Exception("Invalid stake, you do not have sufficient balance");
        } else if(this.getDeck().size() == 0) {
            throw new Exception("Deck is empty. You need to start a new game.");
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
            System.out.println("New deck size: " + deck.size());
            return new Round(newCard, result, currentPlayerBalance);
        }
    }
}
