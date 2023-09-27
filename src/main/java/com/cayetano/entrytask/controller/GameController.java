package com.cayetano.entrytask.controller;

import com.cayetano.entrytask.entity.Card;
import com.cayetano.entrytask.entity.Round;
import com.cayetano.entrytask.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller handles all HTTP requests related to the game.
 */
@RestController
public class GameController {

    private GameService gameService;

    /**
     * Constructor for the controller class.
     *
     * @param gameService - the service getting injected
     */
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Starts a new game.
     *
     * @param balance - the balance of the client
     * @return - the client's first drawn card
     * @throws IllegalArgumentException, thrown when the balance is nonpositive.
     */
    @GetMapping("/start")
    public Card start(@RequestParam int balance) throws IllegalArgumentException {
        return gameService.start(balance);
    }

    /**
     * Shuffles the deck of cards.
     *
     * @return - the client's first drawn card
     * @throws GameStatusException, thrown when a game hasn't been started or has already ended.
     */
    @GetMapping("/shuffle")
    public Card shuffle() throws GameStatusException {
        return gameService.reshuffle();
    }

    /**
     * Places a bet in the game.
     *
     * @param stake - the amount the client wishes to bet
     * @param higher - a boolean stating whether the client is betting on a higher value or not
     * @return - a Round object, representing the result of the bet
     * @throws InsufficientBalanceException, thrown when the client's balance is insufficient to place the bet
     * @throws GameStatusException, thrown when a game hasn't been started or has already ended
     */
    @GetMapping("/bet")
    public Round bet(@RequestParam int stake, @RequestParam boolean higher) throws InsufficientBalanceException, GameStatusException {
        return gameService.bet(stake, higher);
    }
}
