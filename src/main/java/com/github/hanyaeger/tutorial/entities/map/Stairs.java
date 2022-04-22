package com.github.hanyaeger.tutorial.entities.map;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.Direction;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;
import com.github.hanyaeger.tutorial.entities.knight.KnightHitbox;

public class Stairs extends SpriteEntity implements Collided {
    public Stairs(final Coordinate2D location, final Size size, final String resource) {
        super(resource, location, size);
    }

    @Override
    public void onCollision(Collider collider) {
        if(collider instanceof KnightHitbox knightHitbox)
        {
            knightHitbox.knight.nullifySpeedInDirection(Direction.DOWN);
        }
    }
}
