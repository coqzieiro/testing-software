package nl.tudelft.jpacman.sprite;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ImageSpriteTest {

    private static final int IMAGE_WIDTH = 7;
    private static final int IMAGE_HEIGHT = 5;

    @ParameterizedTest(name = "draw forwards x={0}, y={1}, width={2}, height={3}")
    @MethodSource("drawInputCombinations")
    void drawForwardsEveryCoordinateAndDimensionSignCombination(
        int x, int y, int width, int height) {
        BufferedImage image = new BufferedImage(
            IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = mock(Graphics.class);
        ImageSprite sprite = new ImageSprite(image);

        sprite.draw(graphics, x, y, width, height);

        verify(graphics).drawImage(image, x, y, x + width, y + height,
            0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
    }

    private static Stream<Arguments> drawInputCombinations() {
        return IntStream.of(-1, 0, 1).boxed()
            .flatMap(x -> IntStream.of(-1, 0, 1).boxed()
                .flatMap(y -> IntStream.of(-1, 0, 1).boxed()
                    .flatMap(width -> IntStream.of(-1, 0, 1)
                        .mapToObj(height ->
                            Arguments.of(x, y, width, height)))));
    }
}
