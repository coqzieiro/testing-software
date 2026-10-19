package nl.tudelft.jpacman.board;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardTest {

    private static final int BOARD_SIZE = 5;

    private final Square[][] grid = createGrid();
    private final Board board = new Board(grid);

    @ParameterizedTest(name = "squareAt({0}, {1}) returns the stored square")
    @MethodSource("inBoundsPositions")
    void squareAtReturnsTheSquareAtTheRequestedPosition(int x, int y) {
        assertSame(grid[x][y], board.squareAt(x, y));
    }

    private static Stream<Arguments> inBoundsPositions() {
        return IntStream.range(0, BOARD_SIZE).boxed()
            .flatMap(x -> IntStream.range(0, BOARD_SIZE)
                .mapToObj(y -> Arguments.of(x, y)));
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
