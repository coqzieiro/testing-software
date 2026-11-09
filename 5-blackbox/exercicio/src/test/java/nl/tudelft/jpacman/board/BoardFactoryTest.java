package nl.tudelft.jpacman.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import nl.tudelft.jpacman.sprite.PacManSprites;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

class BoardFactoryTest {

    private final BoardFactory factory = new BoardFactory(mock(PacManSprites.class));

    @Property
    void createBoardPreservesCellsAndConnectsWrappedNeighbours(
        @ForAll @IntRange(min = 1, max = 8) int width,
        @ForAll @IntRange(min = 1, max = 8) int height) {
        Square[][] grid = createGrid(width, height);

        Board board = factory.createBoard(grid);

        assertNotNull(board);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                assertSame(grid[x][y], board.squareAt(x, y));
                for (Direction direction : Direction.values()) {
                    int neighbourX = Math.floorMod(
                        x + direction.getDeltaX(), width);
                    int neighbourY = Math.floorMod(
                        y + direction.getDeltaY(), height);
                    assertSame(grid[neighbourX][neighbourY],
                        grid[x][y].getSquareAt(direction));
                }
            }
        }
    }

    private static Square[][] createGrid(int width, int height) {
        Square[][] result = new Square[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result[x][y] = new StubSquare();
            }
        }
        return result;
    }
}
