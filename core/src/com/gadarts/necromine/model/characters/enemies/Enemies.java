package com.gadarts.necromine.model.characters.enemies;

import com.gadarts.necromine.assets.Assets;
import com.gadarts.necromine.assets.Assets.Atlases;
import com.gadarts.necromine.model.characters.CharacterDefinition;
import com.gadarts.necromine.model.characters.CharacterTypes;
import com.gadarts.necromine.model.characters.attributes.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gadarts.necromine.assets.Assets.Sounds.*;
import static com.gadarts.necromine.model.characters.attributes.Agility.*;

@Getter
@RequiredArgsConstructor
public enum Enemies implements CharacterDefinition {
	SCORPION("Scorpion",
			Atlases.SCORPION,
			List.of(LOW, LOW, LOW, MED, MED),
			List.of(Strength.of(1), Strength.of(1), Strength.of(1), Strength.of(1, 2), Strength.of(1, 2)),
			List.of(1, 1, 2, 2, 2),
			1,
			true,
			0.1F,
			SCORPION_AWAKE, SCORPION_ROAM, SCORPION_ATTACK, SCORPION_PAIN, SCORPION_DEATH, LIGHT_STEP),

	ANUBIS("Anubis Zealot",
			Atlases.ANUBIS,
			List.of(LOW, MED, MED, HIGH, HIGH),
			List.of(Strength.of(1), Strength.of(1), Strength.of(1), Strength.of(1, 2), Strength.of(1, 2)),
			List.of(2, 2, 2, 3, 3),
			new Accuracy[]{Accuracy.NONE, Accuracy.LOW, Accuracy.MED, Accuracy.MED, Accuracy.MED},
			List.of(Range.NONE, Range.MED, Range.MED, Range.MED, Range.HIGH),
			List.of(ReloadTime.NONE, ReloadTime.MED, ReloadTime.MED, ReloadTime.MED, ReloadTime.MED),
			EnemyWeaponsDefinitions.ENERGY_BALL,
			4,
			5,
			false,
			ENEMY_AWAKE, ENEMY_ROAM, ATTACK_FIST, ENEMY_PAIN, ENEMY_DEATH, STEP);

	private final String displayName;
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
	private final boolean singleDeathAnimation;
	private final float height;
	private final Assets.Sounds awakeSound;
	private final Assets.Sounds roamSound;
	private final Assets.Sounds attackSound;
	private final Assets.Sounds painSound;
	private final Assets.Sounds deathSound;
	private final Assets.Sounds stepSound;

	Enemies(final String displayName,
			final Atlases atlasDefinition,
			final List<Agility> agility,
			final List<Strength> strength,
			final List<Integer> health,
			final int meleeHitFrameIndex,
			final boolean singleDeathAnimation,
			final float height,
			final Assets.Sounds awakeSound,
			final Assets.Sounds roamSound,
			final Assets.Sounds attackSound,
			final Assets.Sounds painSound,
			final Assets.Sounds deathSound,
			final Assets.Sounds stepSound) {
		this(
				displayName,
				atlasDefinition,
				agility,
				strength,
				health,
				null, null, null, null,
				meleeHitFrameIndex, 0,
				singleDeathAnimation,
				height,
				awakeSound,
				roamSound,
				attackSound,
				painSound,
				deathSound,
				stepSound);
	}

	Enemies(final String displayName,
			final Atlases atlasDefinition,
			final List<Agility> agility,
			final List<Strength> strength,
			final List<Integer> health,
			final Accuracy[] accuracy,
			final List<Range> range,
			final List<ReloadTime> reloadTime,
			final EnemyWeaponsDefinitions primaryAttack,
			final int meleeHitFrameIndex,
			final int primaryAttackHitFrameIndex,
			final boolean singleDeathAnimation,
			final Assets.Sounds awakeSound,
			final Assets.Sounds roamSound,
			final Assets.Sounds attackSound,
			final Assets.Sounds painSound,
			final Assets.Sounds deathSound,
			final Assets.Sounds stepSound) {
		this(
				displayName,
				atlasDefinition,
				agility,
				strength,
				health,
				accuracy,
				range,
				reloadTime,
				primaryAttack,
				meleeHitFrameIndex,
				primaryAttackHitFrameIndex,
				singleDeathAnimation,
				1F,
				awakeSound, roamSound, attackSound, painSound, deathSound, stepSound);
	}


	@Override
	public String toString( ) {
		return getDisplayName();
	}

	@Override
	public String getDisplayName( ) {
		return displayName;
	}

	@Override
	public CharacterTypes getCharacterType( ) {
		return CharacterTypes.ENEMY;
	}

	@Override
	public boolean isSingleDeathAnimation( ) {
		return singleDeathAnimation;
	}

	@Override
	public Atlases getAtlasDefinition( ) {
		return atlasDefinition;
	}
}
