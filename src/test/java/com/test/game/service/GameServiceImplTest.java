package com.test.game.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.test.game.model.Game;

class GameServiceImplTest {

    @Mock
    private List<Game> gameCache;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(cacheManager.getCache("games")).thenReturn(cache);
    }

    @Test
    void testCreateGame() {
        Game game = new Game("Test Game", LocalDate.now(), true);
        when(gameCache.add(game)).thenReturn(true);

        Game createdGame = gameService.createGame(game);

        verify(gameCache, times(1)).add(game);
        assertEquals(game, createdGame);
    }

    @Test
    void testGetGame() {
        String gameName = "Test Game";
        Game game = new Game(gameName, LocalDate.now(), true);
        when(cache.get(gameName)).thenReturn(null);
        when(gameCache.stream()).thenReturn(Stream.of(game));
        
        Game retrievedGame = gameService.getGame(gameName);

        verify(gameCache, times(1)).stream();
        assertEquals(game, retrievedGame);
    }

    @Test
    void testGetAllGames() {
    	
    	List<Game> expectedGames = new ArrayList<>();
    	expectedGames.add(new Game("Test Game 1", LocalDate.now(), true));
        expectedGames.add(new Game("Test Game 2", LocalDate.now(), false));
        when(gameCache.size()).thenReturn(expectedGames.size());
        when(gameCache).thenReturn(expectedGames);

        List<Game> allGames = gameService.getAllGames();

        verify(gameCache, times(1)).size();
        assertEquals(expectedGames, allGames);
    }

    @Test
    void testUpdateGame() {
    	   String gameName = "Test Game";
           Game existingGame = new Game(gameName, LocalDate.now(), true);
           Game updatedGame = new Game(gameName, LocalDate.now(), false);

           when(Stream.of(gameService.getGame(gameName))).thenReturn(Stream.of(existingGame));
           when(gameCache.removeIf(any())).thenReturn(true);

           Game result = gameService.updateGame(gameName, updatedGame);

           assertEquals(updatedGame, result);
    }

    @Test
    void testDeleteGame() {
        String gameName = "Test Game";
        when(gameCache.removeIf(any())).thenReturn(true);

        gameService.deleteGame(gameName);

        verify(gameCache, times(1)).removeIf(any());
    }
}
