package nl.tudelft.jpacman.sprite;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

class ImageSpriteTest {

    private static final int IMAGE_WIDTH = 7;
    private static final int IMAGE_HEIGHT = 5;

    @Property
    void drawForwardsEveryCoordinateAndDimensionSignCombination(
        @ForAll @IntRange(min = -1000, max = 1000) int x,
        @ForAll @IntRange(min = -1000, max = 1000) int y,
        @ForAll @IntRange(min = -1000, max = 1000) int width,
        @ForAll @IntRange(min = -1000, max = 1000) int height) {
        BufferedImage image = new BufferedImage(
            IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = mock(Graphics.class);
        ImageSprite sprite = new ImageSprite(image);

        sprite.draw(graphics, x, y, width, height);

        verify(graphics).drawImage(image, x, y, x + width, y + height,
            0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
    }

}
