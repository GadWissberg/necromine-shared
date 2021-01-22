package com.gadarts.necromine.model;

import com.gadarts.necromine.assets.Assets;

public interface ItemDefinition {
	int getWidth();

	int[] getMask();

	int getHeight();

	int getId();

	Assets.UiTextures getImage();

	String getDisplayName();
}