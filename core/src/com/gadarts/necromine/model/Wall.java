package com.gadarts.necromine.model;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.gadarts.necromine.assets.Assets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Wall {
	private final ModelInstance modelInstance;

	@Setter
	private Assets.FloorsTextures definition;

	@Setter
	private Float vScale;

	public Wall(ModelInstance modelInstance, Assets.FloorsTextures definition) {
		this.modelInstance = modelInstance;
		this.definition = definition;
	}
}

