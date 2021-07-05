package com.gadarts.necromine.model.characters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnemyWeaponsDefinitions {
	ENERGY_BALL(0.1F, new Integer[]{1, 1, 1, 2, 2});

	private final float frameDuration;
	private final Integer[] damagePoints;

}
