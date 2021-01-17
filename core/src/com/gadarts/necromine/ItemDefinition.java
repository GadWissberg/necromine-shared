package com.gadarts.necromine;

public interface ItemDefinition {
	int getWidth();

	int[] getMask();

	int getHeight();

	int getId();

	Assets.UiTextures getImage();

	String getDisplayName();
}
