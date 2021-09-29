package com.gadarts.necromine.model.characters;

import com.gadarts.necromine.assets.Assets;
import com.gadarts.necromine.assets.Assets.Atlases;
import com.gadarts.necromine.model.characters.attributes.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gadarts.necromine.model.characters.attributes.Agility.*;

@Getter
@RequiredArgsConstructor
public enum Enemies implements CharacterDefinition {
	SCORPION("Scorpion",
			Assets.Sounds.ATTACK_FIST,
			Atlases.SCORPION,
			List.of(LOW, LOW, LOW, MED, MED),
			List.of(Strength.of(1), Strength.of(1), Strength.of(1), Strength.of(1, 2), Strength.of(1, 2)),
			List.of(1, 1, 2, 2, 2),
			1),
	ANUBIS("Anubis Zealot",
			Assets.Sounds.ATTACK_FIST,
			Atlases.ANUBIS,
			List.of(LOW, MED, MED, HIGH, HIGH),
			List.of(Strength.of(1), Strength.of(1), Strength.of(1), Strength.of(1, 2), Strength.of(1, 2)),
			List.of(2, 2, 2, 3, 3),
			new Accuracy[]{Accuracy.NONE, Accuracy.HIGH, Accuracy.LOW, Accuracy.MED, Accuracy.MED},
			List.of(Range.NONE, Range.MED, Range.MED, Range.MED, Range.HIGH),
			List.of(ReloadTime.NONE, ReloadTime.MED, ReloadTime.MED, ReloadTime.MED, ReloadTime.MED),
			EnemyWeaponsDefinitions.ENERGY_BALL,
			4,
			5);

	Enemies(final String displayName,
			final Assets.Sounds attackSound,
			final Atlases atlasDefinition,
			final List<Agility> agility,
			final List<Strength> strength,
			final List<Integer> health,
			final int meleeHitFrameIndex) {
		this(
				displayName,
				attackSound,
				atlasDefinition,
				agility,
				strength,
				health,
				null, null, null, null,
				meleeHitFrameIndex, 0);
	}

	private final String displayName;
	private final Assets.Sounds attackSound;
	private final Atlases atlasDefinition;
	private final List<Agility> agility;
	private final List<Strength> strength;
	private final List<Integer> health;
	private final Accuracy[] accuracy;
	private final List<Range> range;
	private final List<ReloadTime> reloadTime;
	private final EnemyWeaponsDefinitions primaryAttack;
	private final int meleeHitFrameIndex;
	private final int primaryAttackHitFrameIndex;


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
