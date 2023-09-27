package com.cayetano.entrytask.service;

import com.cayetano.entrytask.controller.GameStatusException;
import com.cayetano.entrytask.controller.InsufficientBalanceException;
import com.cayetano.entrytask.entity.Card;
import com.cayetano.entrytask.entity.Result;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {

    private Integer playerBalance;
    private List<Card> deck;

    private Card lastDrawnCard;

    public GameServiceImpl() {
        this.playerBalance = null;
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

    public Integer getPlayerBalance() {
        return playerBalance;
    }

    public void setPlayerBalance(Integer playerBalance) {
        this.playerBalance = playerBalance;
    }

    /**
     * Starts a new game. First the balance is checked for validity.
     * Then, the balance is being saved, the deck is being created and shuffled
     * and a card is being taken out of it. After the card is saved as the last
     * drawn card, it is returned to the player.
     *
     * @param balance - the initial balance of the player
     * @return - the first drawn card from the deck
     * @throws IllegalArgumentException, thrown when the balance is nonpositive
     */
    @Override
    public Card start(int balance) throws IllegalArgumentException {
        if(balance <= 0) {
            throw new IllegalArgumentException("Nonpositive balance is not permitted.");
        } else {
            setPlayerBalance(balance);
            composeDeck();
            Card drawnCard = drawCard();
            setLastDrawnCard(drawnCard);
            return drawnCard;
        }
    }

    /**
     * Randomly draws a card from the deck.
     * @return - the drawn card
     */
    private Card drawCard() {
        return deck.remove(new Random().nextInt(deck.size()));
    }

    /**
     * Creates a deck of cards and randomly shuffles it.
     */
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
        setDeck(deck);
    }

    /**
     * Reshuffles the deck of cards and returns it to its beginning state.
     * Essentially it does the same as the start method, with the only difference
     * that the deck object should have been previously created.
     *
     * @return - the first drawn card from the reshuffled deck
     * @throws GameStatusException, thrown if there hasn't been a previous game
     */
    @Override
    public Card reshuffle() throws GameStatusException {
        if(deck == null) throw new GameStatusException("You haven't started a game yet.");
        return start(playerBalance);
    }

    /**
     * Places a bet. First, the bet size and the state of the game are being checked.
     * Then, a new card is drawn, and it is being compared with the last drawn card.
     * If there is a match between the difference of ranks and the player's bet,
     * the bet is doubled and returned to the client. Otherwise, it is taken from him.
     * When the ranks of the cards is the same, nothing happens and the client receives his
     * bet back.
     *
     * @param stake - the amount the client bets
     * @param higher - the client's prediction on whether the newly drawn card will be of
     *               higher rank than the previous one
     * @return - a Round object, consisting of the object of the newly drawn card,
     *              the result of the client's bet and his new balance
     * @throws GameStatusException - thrown if a game hasn't been started yet
     *              or if the deck has run out of cards a new game needs to be started
     * @throws InsufficientBalanceException - thrown if the bet size is bigger than
     *              the balance of the player
     */
    @Override
    public Round bet(int stake, boolean higher) throws GameStatusException, InsufficientBalanceException {
        if(this.lastDrawnCard == null) {
            throw new GameStatusException("You can not place a bet since a game hasn't been started yet.");
        } else if(stake > playerBalance) {
            throw new InsufficientBalanceException(stake, playerBalance);
        } else if(this.getDeck().size() == 0) {
            throw new GameStatusException("There are no more cards in the deck. You need to start a new game.");
        } else {
            Result result;
            Card newCard = drawCard();
            GameResult lastHigher = Card.isHigher(newCard, lastDrawnCard);

            if((lastHigher.equals(GameResult.HIGHER) && higher)
                    || (lastHigher.equals(GameResult.LOWER) && !higher)) {
                setPlayerBalance(playerBalance + stake);
                result = Result.WIN;
            } else if(lastHigher.equals(GameResult.EQUAL)) {
                result = Result.DRAW;
            } else {
                setPlayerBalance(playerBalance - stake);
                result = Result.LOSS;
            }

            setLastDrawnCard(newCard);
            return new Round(newCard, result, playerBalance);
        }
    }
}
