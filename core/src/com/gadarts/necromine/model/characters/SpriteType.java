package com.gadarts.necromine.model.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import lombok.Getter;

@Getter
public enum SpriteType {
	IDLE(0.4f, Animation.PlayMode.LOOP_PINGPONG),
	RUN(0.07f),
	ATTACK(0.07f, Animation.PlayMode.NORMAL),
	PAIN(0.5F),
	PICKUP(0.2f, Animation.PlayMode.NORMAL, false, true),
	LIGHT_DEATH_1(0.07f, Animation.PlayMode.NORMAL, true, false, "Light Death 1");

	private final float animationDuration;
	private final Animation.PlayMode playMode;
	private final boolean singleAnimation;
	private final boolean addReverse;
	private final String[] severalAnimations;

	SpriteType(final float animationDuration) {
		this(animationDuration, Animation.PlayMode.LOOP);
	}

	SpriteType(final float animationDuration, final Animation.PlayMode playMode) {
		this(animationDuration, playMode, false, false);
	}

	SpriteType(final float animationDuration,
			   final Animation.PlayMode playMode,
			   final boolean singleAnimation,
			   final boolean addReverse,
			   final String... severalAnimations) {
		this.animationDuration = animationDuration;
		this.playMode = playMode;
		this.singleAnimation = singleAnimation;
		this.addReverse = addReverse;
		this.severalAnimations = severalAnimations;
	}
}
