package nl.tudelft.jpacman.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MapParserTest {

    @ParameterizedTest(name = "makeGhostSquare appends to a list of size {0}")
    @ValueSource(ints = {0, 1, 2})
    void makeGhostSquareCreatesAndOccupiesASquare(int existingGhostCount) {
        BoardFactory boardFactory = new BoardFactory(mock(PacManSprites.class));
        MapParser parser = new MapParser(
            mock(LevelFactory.class), boardFactory);
        List<Ghost> ghosts = new ArrayList<>();
        List<Ghost> existingGhosts = new ArrayList<>();
        for (int i = 0; i < existingGhostCount; i++) {
            Ghost existingGhost = new TestGhost();
            existingGhost.occupy(boardFactory.createGround());
            existingGhosts.add(existingGhost);
            ghosts.add(existingGhost);
        }
        Ghost ghost = new TestGhost();

        Square square = parser.makeGhostSquare(ghosts, ghost);

        assertEquals(existingGhostCount + 1, ghosts.size());
        for (int i = 0; i < existingGhostCount; i++) {
            assertSame(existingGhosts.get(i), ghosts.get(i));
        }
        assertSame(ghost, ghosts.get(existingGhostCount));
        assertSame(square, ghost.getSquare());
        assertTrue(square.getOccupants().contains(ghost));
    }

    private static final class TestGhost extends Ghost {

        TestGhost() {
            super(Collections.emptyMap(), 1, 1);
        }

        @Override
        public Optional<Direction> nextAiMove() {
            return Optional.empty();
        }
    }
}
