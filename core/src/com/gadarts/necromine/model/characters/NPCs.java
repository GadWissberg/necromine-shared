package com.gadarts.necromine.model.characters;

import com.gadarts.necromine.assets.Assets;

public enum NPCs implements CharacterDefinition {
	;

	@Override
	public String getDisplayName() {
		return "NPC";
	}

	@Override
	public CharacterTypes getCharacterType() {
		return CharacterTypes.NPC;
	}

	@Override
	public boolean isSingleDeathAnimation() {
		return false;
	}

	@Override
	public int getMeleeHitFrameIndex() {
		return 0;
	}

	@Override
	public int getPrimaryAttackHitFrameIndex() {
		return 0;
	}

	@Override
	public Assets.Atlases getAtlasDefinition() {
		return null;
	}
}
