package com.gadarts.necromine.model;

import com.gadarts.necromine.assets.Assets;

public interface ItemDefinition extends ElementDefinition {
	int getWidth();

	int[] getMask();

	int getHeight();

	int getId();

	Assets.UiTextures getImage();

	String getDisplayName();
}