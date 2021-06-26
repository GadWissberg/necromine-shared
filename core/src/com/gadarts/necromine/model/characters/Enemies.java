package com.gadarts.necromine.model.characters;

import com.gadarts.necromine.assets.Assets;
import com.gadarts.necromine.assets.Assets.Atlases;
import lombok.Getter;

import java.util.List;

import static com.gadarts.necromine.model.characters.Agility.*;

@Getter
public enum Enemies implements CharacterDefinition {
	ANUBIS("Anubis Zealot",
			Assets.Sounds.ATTACK_FIST,
			Atlases.ANUBIS,
			List.of(LOW, MED, MED, HIGH, HIGH),
			List.of(Strength.of(1), Strength.of(1), Strength.of(1), Strength.of(1, 2), Strength.of(1, 2)),
			List.of(2, 2, 2, 3, 3));

	private final String displayName;
	private final Assets.Sounds attackSound;
	private final Atlases atlasDefinition;
	private final List<Agility> agility;
	private final List<Strength> strength;
	private final List<Integer> health;

	Enemies(final String displayName,
			final Assets.Sounds attackSound,
			final Atlases atlasDefinition,
			final List<Agility> agility,
			final List<Strength> strength,
			final List<Integer> health) {
		this.displayName = displayName;
		this.attackSound = attackSound;
		this.atlasDefinition = atlasDefinition;
		this.agility = agility;
		this.strength = strength;
		this.health = health;
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
