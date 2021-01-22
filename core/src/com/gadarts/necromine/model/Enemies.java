package com.gadarts.necromine.model;

import com.gadarts.necromine.assets.Assets;
import lombok.Getter;

@Getter
public enum Enemies implements ElementDefinition {
	ZEALOT("Zealot", Assets.Sounds.ATTACK_CLAW);

	private final String displayName;
	private final Assets.Sounds attackSound;

	Enemies(final String displayName, final Assets.Sounds attackSound) {
		this.displayName = displayName;
		this.attackSound = attackSound;
	}
}
