package com.gadarts.necromine.model.characters.attributes;

import lombok.Getter;

@Getter
public enum Accuracy {
	NONE(0), LOW(35), MED(15), HIGH(5);

	private final int maxAngle;

	Accuracy(int maxAngle) {
		this.maxAngle = maxAngle;
	}
}
