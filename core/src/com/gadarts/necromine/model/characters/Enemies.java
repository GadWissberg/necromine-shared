package com.gadarts.necromine.model.characters;

import com.gadarts.necromine.assets.Assets;
import com.gadarts.necromine.assets.Assets.Atlases;
import lombok.Getter;

import static com.gadarts.necromine.model.characters.Agility.*;

@Getter
public enum Enemies implements CharacterDefinition {
	ZEALOT("Zealot", Assets.Sounds.ATTACK_CLAW, Atlases.ZEALOT, new Agility[]{LOW, MED, MED, HIGH, HIGH});

	private final String displayName;
	private final Assets.Sounds attackSound;
	private final Atlases atlasDefinition;
	private final Agility[] agility;

	Enemies(final String displayName, final Assets.Sounds attackSound, final Atlases atlasDefinition, Agility[] agility) {
		this.displayName = displayName;
		this.attackSound = attackSound;
		this.atlasDefinition = atlasDefinition;
		this.agility = agility;
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
	public Atlases getAtlasDefinition() {
		return atlasDefinition;
	}
}
