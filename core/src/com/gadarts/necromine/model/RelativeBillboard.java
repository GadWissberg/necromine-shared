package com.gadarts.necromine.model;

import com.badlogic.gdx.math.Vector3;
import com.gadarts.necromine.assets.definitions.AtlasDefinition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RelativeBillboard {
	private final AtlasDefinition billboard;
	private final float frameDuration;

	@Getter(AccessLevel.NONE)
	private final Vector3 relativePosition;


	public Vector3 getRelativePosition(final Vector3 output) {
		return output.set(relativePosition);
	}
}
