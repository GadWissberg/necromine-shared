package com.gadarts.necromine.model.characters;

import com.gadarts.necromine.assets.Assets;
import com.gadarts.necromine.model.ElementDefinition;

public interface CharacterDefinition extends ElementDefinition {
	CharacterTypes getCharacterType();

	Assets.Atlases getAtlasDefinition();
}
