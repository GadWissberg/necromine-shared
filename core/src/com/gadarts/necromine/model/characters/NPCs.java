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
	public Assets.Atlases getAtlasDefinition() {
		return null;
	}
}
