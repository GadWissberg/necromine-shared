package com.gadarts.necromine.model.characters;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
	SOUTH(0, 1),
	SOUTH_EAST(1, 1),
	EAST(1, 0),
	NORTH_EAST(1, -1),
	NORTH(0, -1),
	NORTH_WEST(-1, -1),
	WEST(-1, 0),
	SOUTH_WEST(-1, 1);

	private static final float DIR_ANGLE_SIZE = 45;
	private final Vector2 directionVector;
	private final float bottomBound;
	private final float upperBound;

	Direction(final int x, final int z) {
		directionVector = new Vector2(x, z).nor();
		float angleDeg = directionVector.angleDeg();
		Vector2 auxVector = new Vector2();
		bottomBound = auxVector.set(1, 0).setAngleDeg(angleDeg - DIR_ANGLE_SIZE / 2).angleDeg();
		upperBound = angleDeg + DIR_ANGLE_SIZE / 2;
	}

	public static Direction findDirection(final Vector2 direction) {
		Direction[] values = values();
		Direction result = SOUTH;
		float angleDeg = direction.angleDeg();
		for (Direction dir : values) {
			boolean isBottomLessThanUpper = dir.bottomBound < dir.upperBound;
			boolean firstOpt = isBottomLessThanUpper && angleDeg >= dir.bottomBound && angleDeg < dir.upperBound;
			boolean secondOpt = !isBottomLessThanUpper && (angleDeg >= dir.bottomBound || angleDeg < dir.upperBound);
			if (firstOpt || secondOpt) {
				result = dir;
				break;
			}
		}
		return result;
	}

	public Vector2 getDirection(final Vector2 output) {
		return output.set(directionVector);
	}
}
