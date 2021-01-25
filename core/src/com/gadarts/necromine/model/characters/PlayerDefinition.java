package com.gadarts.necromine.model.characters;


import com.gadarts.necromine.assets.Assets;

public class PlayerDefinition implements CharacterDefinition {
	@Override
	public String getDisplayName() {
		return "Player";
	}

	@Override
	public String toString() {
		return getDisplayName();
	}

	@Override
	public CharacterTypes getCharacterType() {
		return CharacterTypes.PLAYER;
	}

	@Override
	public Assets.Atlases getAtlasDefinition() {
		return Assets.Atlases.PLAYER_AXE_PICK;
	}
}
