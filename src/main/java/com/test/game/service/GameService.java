package com.test.game.service;

import java.util.List;

import com.test.game.model.Game;

public interface GameService {

	public Game getGame(String name);
	public List<Game> getAllGames();
	public Game createGame(Game game);
	public Game updateGame(String name, Game game);
	public void deleteGame(String name);
}
