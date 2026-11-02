package nl.tudelft.jpacman.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.Test;

class GameStateMachineTest {

    @Test
    void startStopAndStartAgainFollowTheLifecycle() {
        Level level = playableLevel();
        Game game = new TestGame(level);

        assertFalse(game.isInProgress());

        game.start();
        assertTrue(game.isInProgress());
        verify(level).addObserver(game);
        verify(level).start();

        game.stop();
        assertFalse(game.isInProgress());
        verify(level).stop();

        game.start();
        assertTrue(game.isInProgress());
        verify(level, org.mockito.Mockito.times(2)).start();
    }

    @Test
    void startIsIgnoredWhenAlreadyRunning() {
        Level level = playableLevel();
        Game game = new TestGame(level);

        game.start();
        game.start();

        assertTrue(game.isInProgress());
        verify(level).start();
    }

    @Test
    void startIsRejectedWhenNoPlayerIsAlive() {
        Level level = mock(Level.class);
        when(level.isAnyPlayerAlive()).thenReturn(false);
        when(level.remainingPellets()).thenReturn(1);
        Game game = new TestGame(level);

        game.start();

        assertFalse(game.isInProgress());
        verify(level, never()).start();
    }

    @Test
    void startIsRejectedWhenNoPelletsRemain() {
        Level level = mock(Level.class);
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(0);
        Game game = new TestGame(level);

        game.start();

        assertFalse(game.isInProgress());
        verify(level, never()).start();
    }

    @Test
    void movementIsForwardedOnlyWhileRunning() {
        Level level = playableLevel();
        Game game = new TestGame(level);
        Player player = mock(Player.class);

        game.move(player, Direction.EAST);
        verify(level, never()).move(player, Direction.EAST);

        game.start();
        game.move(player, Direction.EAST);
        verify(level).move(player, Direction.EAST);

        game.stop();
        game.move(player, Direction.EAST);
        verify(level).move(player, Direction.EAST);
    }

    @Test
    void winningStopsTheGame() {
        assertTerminalEventStopsGame(true);
    }

    @Test
    void losingStopsTheGame() {
        assertTerminalEventStopsGame(false);
    }

    private static Level playableLevel() {
        Level level = mock(Level.class);
        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        return level;
    }

    private static void assertTerminalEventStopsGame(boolean won) {
        Level level = playableLevel();
        Game game = new TestGame(level);
        game.start();

        if (won) {
            game.levelWon();
        } else {
            game.levelLost();
        }

        assertFalse(game.isInProgress());
        verify(level).stop();
    }

    private static final class TestGame extends Game {

        private final Level level;

        private TestGame(Level level) {
            this.level = level;
        }

        @Override
        public java.util.List<Player> getPlayers() {
            return Collections.emptyList();
        }

        @Override
        public Level getLevel() {
            return level;
        }
    }
}
