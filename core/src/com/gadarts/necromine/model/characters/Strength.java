package com.gadarts.necromine.model.characters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Strength {
	private final int minDamage;
	private final int maxDamage;

	public Strength(final int damage) {
		this.minDamage = damage;
		this.maxDamage = damage;
	}
}
