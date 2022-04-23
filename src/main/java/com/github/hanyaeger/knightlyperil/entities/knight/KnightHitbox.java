package com.github.hanyaeger.knightlyperil.entities.knight;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import com.github.hanyaeger.knightlyperil.entities.map.Floor;
import javafx.scene.paint.Color;

import java.util.List;

public class KnightHitbox extends RectangleEntity implements Collided, Collider {
    public Knight knight;
    private final int knightHitboxWidth = 35;
    private final int knightHitboxHeight = 65;

    public KnightHitbox(final Coordinate2D initialPosition, Knight knight) {
        super(initialPosition);
        setWidth(knightHitboxWidth);
        setHeight(knightHitboxHeight);
        setFill(Color.TRANSPARENT);

        this.knight = knight;
    }

    @Override
    public void onCollision(List<Collider> collidingObjects) {
        final int deviant = 3; // The deviant is set to 3. This is the lowest the engine seems to reliably handle.

        for (Collider collider : collidingObjects) {
            if (collider instanceof Floor floor) { // Polymorphisme toepassen op alle Floor objecten. Deze hele check mag daar in.
                var floorBoundingBox = floor.getBoundingBox();
                var knightBoundingBox = knight.getBoundingBox();
                var hitboxBoundingBox = this.getBoundingBox();

                if( // We are above.
                        (hitboxBoundingBox.getMaxY() - floorBoundingBox.getMinY()) <= deviant &&
                        (hitboxBoundingBox.getMaxY() - floorBoundingBox.getMinY()) > 0
                ) {
                    knight.nullifySpeedInDirection(Direction.DOWN);
                    knight.setAnchorLocation(new Coordinate2D(knightBoundingBox.getMinX(), knightBoundingBox.getMinY() - deviant));
                }
                 else if( // We are below.
                        (floorBoundingBox.getMaxY() - hitboxBoundingBox.getMinY()) <= deviant &&
                        (floorBoundingBox.getMaxY() - hitboxBoundingBox.getMinY()) > 0
                ) {
                    knight.nullifySpeedInDirection(Direction.UP);
                    knight.setAnchorLocation(new Coordinate2D(knightBoundingBox.getMinX(), knightBoundingBox.getMinY() + deviant));
                }
                else if( // We are coming from the left
                        ((hitboxBoundingBox.getMaxX() - floorBoundingBox.getMinX())  <= deviant &&
                        ((hitboxBoundingBox.getMaxX() - floorBoundingBox.getMinX()) > 0))
                ) {
                    knight.nullifySpeedInDirection(Direction.RIGHT);
                    knight.setAnchorLocation(new Coordinate2D(knightBoundingBox.getMinX() - deviant, knightBoundingBox.getMinY()));
                }
                else if( // We are coming from the right
                        ((floorBoundingBox.getMaxX() - hitboxBoundingBox.getMinX()) <= deviant &&
                        ((floorBoundingBox.getMaxX() - hitboxBoundingBox.getMinX()) > 0))
                ) {
                    knight.nullifySpeedInDirection(Direction.LEFT);
                    knight.setAnchorLocation(new Coordinate2D(knightBoundingBox.getMinX() + deviant, knightBoundingBox.getMinY()));
                }
            }
        }
    }
}
