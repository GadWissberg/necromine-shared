package com.gadarts.necromine.assets.definitions;

import com.badlogic.gdx.assets.AssetLoaderParameters;

public interface AssetDefinition {
	String getFilePath();

	AssetLoaderParameters getParameters();

	default String getAssetManagerKey() {
		return null;
	}

	Class<?> getTypeClass();
}
