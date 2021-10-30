package com.gadarts.necromine.model.characters.enemies;

import com.gadarts.necromine.assets.Assets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.gadarts.necromine.assets.Assets.ParticleEffects;
import static com.gadarts.necromine.assets.Assets.Sounds.ATTACK_ENERGY_BALL;
import static com.gadarts.necromine.assets.Assets.Sounds.SMALL_EXP;

@Getter
@RequiredArgsConstructor
public enum EnemyWeaponsDefinitions {
	ENERGY_BALL(
			0.1F,
			new Integer[]{1, 1, 1, 2, 2},
			ParticleEffects.ENERGY_BALL_EXPLOSION,
			ATTACK_ENERGY_BALL,
			SMALL_EXP);

	private final float frameDuration;
	private final Integer[] damagePoints;
	private final ParticleEffects particleEffect;
	private final Assets.Sounds engageSound;
	private final Assets.Sounds impactSound;

}
