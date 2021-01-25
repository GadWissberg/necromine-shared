package com.gadarts.necromine.model.characters;

import com.gadarts.necromine.assets.Assets;
import lombok.Getter;

@Getter
public enum Enemies implements CharacterDefinition {
	ZEALOT("Zealot", Assets.Sounds.ATTACK_CLAW, Assets.Atlases.ZEALOT);

	private final String displayName;
	private final Assets.Sounds attackSound;
	private final Assets.Atlases atlasDefinition;

	Enemies(final String displayName, final Assets.Sounds attackSound, Assets.Atlases atlasDefinition) {
		this.displayName = displayName;
		this.attackSound = attackSound;
		this.atlasDefinition = atlasDefinition;
	}

	@Override
	public String toString() {
		return getDisplayName();
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public CharacterTypes getCharacterType() {
		return CharacterTypes.ENEMY;
	}

	@Override
	public Assets.Atlases getAtlasDefinition() {
		return atlasDefinition;
	}
}
