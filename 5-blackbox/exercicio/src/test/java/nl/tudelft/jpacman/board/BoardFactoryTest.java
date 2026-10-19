package nl.tudelft.jpacman.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardFactoryTest {

    private final BoardFactory factory = new BoardFactory(mock(PacManSprites.class));

    @ParameterizedTest(name = "createBoard links a {0}x{1} grid")
    @MethodSource("gridDimensions")
    void createBoardPreservesCellsAndConnectsWrappedNeighbours(int width, int height) {
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

    private static Stream<Arguments> gridDimensions() {
        return IntStream.rangeClosed(1, 3).boxed()
            .flatMap(width -> IntStream.rangeClosed(1, 3)
                .mapToObj(height -> Arguments.of(width, height)));
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
