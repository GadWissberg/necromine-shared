package com.gadarts.necromine.model.characters;

import lombok.Getter;

@Getter
public enum EnemyWeaponsDefinitions {
	ENERGY_BALL(0.1F);

	private final float frameDuration;

	EnemyWeaponsDefinitions(float frameDuration) {
		this.frameDuration = frameDuration;
	}
}
