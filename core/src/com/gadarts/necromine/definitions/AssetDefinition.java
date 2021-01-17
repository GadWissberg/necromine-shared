package com.gadarts.necromine.definitions;

import com.badlogic.gdx.assets.AssetLoaderParameters;

public interface AssetDefinition {
	String getFilePath();

	AssetLoaderParameters getParameters();

	Class<?> getTypeClass();
}
