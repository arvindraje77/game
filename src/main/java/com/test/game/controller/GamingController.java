package com.test.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.test.game.model.Game;
import com.test.game.service.GameService;

@RestController
@RequestMapping("/games")
public class GamingController {
	
	private static final Logger logger = LoggerFactory.getLogger(GamingController.class);
	
	private final GameService gameService;

    @Autowired
    public GamingController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
    	logger.info("Creating a new game: {}", game);
        Game createdGame = gameService.createGame(game);
        logger.info("Game created: {}", createdGame);
        return createdGame;
    }

    @GetMapping("/{name}")
    public Game getGame(@PathVariable String name) {
    	logger.info("Fetching game with name: {}", name);
        Game retrievedGame = gameService.getGame(name);
        if (retrievedGame == null) {
            logger.info("Game not found with name: {}", name);
        } else {
            logger.info("Game retrieved: {}", retrievedGame);
        }
        return retrievedGame;
    }

    @GetMapping
    public List<Game> getAllGames() {
    	logger.info("Fetching all games");
        List<Game> allGames = gameService.getAllGames();
        logger.info("Total games retrieved: {}", allGames.size());
        return allGames;
    }

    @PutMapping("/{name}")
    public Game updateGame(@PathVariable String name, @RequestBody Game updatedGame) {
    	logger.info("Updating game with name: {}", name);
        Game result = gameService.updateGame(name, updatedGame);
        if (result == null) {
            logger.info("Game not found with name: {}", name);
        } else {
            logger.info("Game updated: {}", result);
        }
        return result;
    }

    @DeleteMapping("/{name}")
    public void deleteGame(@PathVariable String name) {
    	logger.info("Deleting game with name: {}", name);
        gameService.deleteGame(name);
        logger.info("Game deleted with name: {}", name);
    }
}

