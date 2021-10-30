package com.gadarts.necromine.model.map;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.gadarts.necromine.assets.Assets;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Wall {
	private final ModelInstance modelInstance;

	@Setter
	private Assets.SurfaceTextures definition;

	@Setter
	private Float vScale;

	public Wall(ModelInstance modelInstance, Assets.SurfaceTextures definition) {
		this.modelInstance = modelInstance;
		this.definition = definition;
	}
}

