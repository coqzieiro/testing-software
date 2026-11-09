package nl.tudelft.jpacman.board;

import static org.junit.jupiter.api.Assertions.assertSame;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

class BoardTest {

    private static final int BOARD_SIZE = 5;

    private final Square[][] grid = createGrid();
    private final Board board = new Board(grid);

    @Property
    void squareAtReturnsTheSquareAtTheRequestedPosition(
        @ForAll @IntRange(min = 0, max = 4) int x,
        @ForAll @IntRange(min = 0, max = 4) int y) {
        assertSame(grid[x][y], board.squareAt(x, y));
    }

    private static Square[][] createGrid() {
        Square[][] result = new Square[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                result[x][y] = new StubSquare();
            }
        }
        return result;
    }
}
