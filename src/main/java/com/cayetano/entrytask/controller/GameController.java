package com.cayetano.entrytask.controller;

import com.cayetano.entrytask.entity.Card;
import com.cayetano.entrytask.entity.Round;
import com.cayetano.entrytask.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     *
     * Opciq 1: Vrushtam prosto object na kartata
     * Opciq 2: Vrushtam, v koito opisvam kartata i nachaloto na igrata
     */
    /**
     *
     * @param balance
     * @return
     * @throws IllegalArgumentException
     */
    @GetMapping("/start")
    public Card start(@RequestParam int balance) throws IllegalArgumentException {
        return gameService.start(balance);
    }

    @GetMapping("/shuffle")
    public Card shuffle() throws GameStatusException {
        return gameService.shuffle();
    }

    @GetMapping("/bet")
    public Round bet(@RequestParam int stake, @RequestParam boolean higher) throws InsufficientBalanceException, GameStatusException{
        return gameService.bet(stake, higher);
    }
}
