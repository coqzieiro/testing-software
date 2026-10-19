package nl.tudelft.jpacman.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LevelFactoryTest {

    @ParameterizedTest(name = "createLevel with {0} ghosts and {1} start positions")
    @MethodSource("listSizes")
    void createLevelUsesTheProvidedBoardAndLists(int ghostCount, int startCount) {
        Board board = createBoard();
        List<TrackingGhost> trackingGhosts = createGhosts(ghostCount);
        List<Ghost> ghosts = new ArrayList<>(trackingGhosts);
        List<Square> startPositions = createStartPositions(board, startCount);
        LevelFactory factory = new LevelFactory(
            mock(PacManSprites.class), mock(GhostFactory.class));

        Level level = factory.createLevel(board, ghosts, startPositions);

        assertSame(board, level.getBoard());
        assertFalse(level.isInProgress());

        int playerCount = startCount == 0 ? 0 : startCount + 1;
        for (int i = 0; i < playerCount; i++) {
            Player player = mock(Player.class);
            level.registerPlayer(player);
            Square expectedStart = startPositions.get(i % startCount);
            verify(player).occupy(expectedStart);
        }

        try {
            level.start();
            assertTrue(level.isInProgress());
            for (TrackingGhost ghost : trackingGhosts) {
                assertEquals(1, ghost.getIntervalCalls());
            }
        } finally {
            if (level.isInProgress()) {
                level.stop();
            }
        }
        assertFalse(level.isInProgress());
    }

    private static Stream<Arguments> listSizes() {
        return IntStream.rangeClosed(0, 2).boxed()
            .flatMap(ghostCount -> IntStream.rangeClosed(0, 2)
                .mapToObj(startCount -> Arguments.of(ghostCount, startCount)));
    }

    private static Board createBoard() {
        BoardFactory boardFactory = new BoardFactory(mock(PacManSprites.class));
        Square[][] grid = {
            {boardFactory.createGround()},
            {boardFactory.createGround()}
        };
        return boardFactory.createBoard(grid);
    }

    private static List<TrackingGhost> createGhosts(int count) {
        List<TrackingGhost> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(new TrackingGhost());
        }
        return result;
    }

    private static List<Square> createStartPositions(Board board, int count) {
        List<Square> result = new ArrayList<>();
        for (int x = 0; x < count; x++) {
            result.add(board.squareAt(x, 0));
        }
        return result;
    }

    private static final class TrackingGhost extends Ghost {

        private int intervalCalls;

        TrackingGhost() {
            super(Collections.emptyMap(), 60_000, 1);
        }

        @Override
        public long getInterval() {
            intervalCalls++;
            return 60_000L;
        }

        @Override
        public Optional<Direction> nextAiMove() {
            return Optional.empty();
        }

        int getIntervalCalls() {
            return intervalCalls;
        }
    }
}
