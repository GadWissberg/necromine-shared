package com.gadarts.necromine.model.characters;

import com.gadarts.necromine.model.characters.enemies.Enemies;
import com.gadarts.necromine.model.characters.player.NPCs;
import com.gadarts.necromine.model.characters.player.PlayerDefinition;
import lombok.Getter;

@Getter
public enum CharacterTypes {
	PLAYER(new PlayerDefinition[]{new PlayerDefinition()}), ENEMY(Enemies.values()), NPC(NPCs.values());

	public static final float BILLBOARD_SCALE = 0.013F;
	public static final float BILLBOARD_Y = 0.7f;
	private final CharacterDefinition[] definitions;

	CharacterTypes(final CharacterDefinition[] definitions) {
		this.definitions = definitions;
	}
}
