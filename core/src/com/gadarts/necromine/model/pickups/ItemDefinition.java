package com.gadarts.necromine.model.pickups;

import com.gadarts.necromine.assets.Assets;
import com.gadarts.necromine.model.ModelElementDefinition;

public interface ItemDefinition extends ModelElementDefinition {
	int getWidth();

	int[] getMask();

	int getHeight();

	int getId();

	Assets.UiTextures getImage();

	String getDisplayName();
}