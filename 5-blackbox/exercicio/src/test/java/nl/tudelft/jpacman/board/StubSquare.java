package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;

final class StubSquare extends Square {

    @Override
    public boolean isAccessibleTo(Unit unit) {
        return true;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }
}
