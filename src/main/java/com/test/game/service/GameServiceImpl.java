package com.test.game.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.game.model.Game;

@Service
public class GameServiceImpl implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    private List<Game> gameCache = new ArrayList<>();

    @Override
    public Game createGame(Game game) {
        logger.info("Creating a new game: {}", game);
        gameCache.add(game);
        logger.info("Game created: {}", game);
        return game;
    }

    @Override
    @Cacheable(value = "games", key = "#name")
    public Game getGame(String name) {
        logger.info("Fetching game with name: {}", name);
        Game retrievedGame = gameCache.stream()
                .filter(game -> game.getName().equals(name))
                .findFirst()
                .orElse(null);
        if (retrievedGame == null) {
            logger.info("Game not found with name: {}", name);
        } else {
            logger.info("Game retrieved: {}", retrievedGame);
        }
        return retrievedGame;
    }

    @Override
    @Cacheable(value = "games")
    public List<Game> getAllGames() {
        logger.info("Fetching all games");
        logger.info("Total games retrieved: {}", gameCache.size());
        return gameCache;
    }

    @Override
    @CacheEvict(value = "games", key = "#name")
    public Game updateGame(String name, Game updatedGame) {
        logger.info("Updating game with name: {}", name);
        Game gameToUpdate = getGame(name);
        if (gameToUpdate != null) {
            gameToUpdate.setDateOfCreation(updatedGame.getDateOfCreation());
            gameToUpdate.setActive(updatedGame.isActive());
            logger.info("Game updated: {}", gameToUpdate);
        } else {
            logger.info("Game not found with name: {}", name);
        }
        return gameToUpdate;
    }

    @Override
    @CacheEvict(value = "games", key = "#name")
    public void deleteGame(String name) {
        logger.info("Deleting game with name: {}", name);
        gameCache.removeIf(game -> game.getName().equals(name));
        logger.info("Game deleted with name: {}", name);
    }
}
