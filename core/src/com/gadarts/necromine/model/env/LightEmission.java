package com.gadarts.necromine.model.env;

import com.badlogic.gdx.math.Vector3;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LightEmission {

	@Getter(AccessLevel.NONE)
	private final Vector3 relativePosition;
	private final float intensity;
	private final float radius;
	private final boolean flicker;

	public Vector3 getRelativePosition(final Vector3 output) {
		return output.set(relativePosition);
	}
}
