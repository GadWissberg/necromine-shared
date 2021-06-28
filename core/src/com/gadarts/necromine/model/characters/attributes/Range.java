package com.gadarts.necromine.model.characters.attributes;

import lombok.Getter;

@Getter
public enum Range {
	NONE(0), LOW(2), MED(4), HIGH(8);

	private final int maxDistance;

	Range(int maxDistance) {
		this.maxDistance = maxDistance;
	}
}
