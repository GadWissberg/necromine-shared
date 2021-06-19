package com.gadarts.necromine.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;
import com.gadarts.necromine.assets.Assets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static com.badlogic.gdx.math.Matrix4.M13;

@Getter
@Setter
public class MapNodeData {

	private final static Vector3 auxVector = new Vector3();
	private Wall eastWall;
	private Wall southWall;
	private Wall westWall;
	private Wall northWall;

	@Setter(AccessLevel.NONE)
	private int col;

	@Setter(AccessLevel.NONE)
	private int row;

	private ModelInstance modelInstance;
	private MapNodesTypes mapNodeType;
	private Assets.SurfaceTextures textureDefinition;
	private float height;

	public MapNodeData(final int row, final int col, final MapNodesTypes type) {
		this(null, row, col, type);
	}

	public MapNodeData(final Model tileModel, final int row, final int col, final MapNodesTypes type) {
		initializeFields(row, col, type);
		if (tileModel != null) {
			initializeModelInstance(tileModel);
		}
	}

	private void initializeFields(final int row, final int col, final MapNodesTypes type) {
		this.mapNodeType = type;
		this.row = row;
		this.col = col;
	}

	public void initializeModelInstance(final Model tileModel) {
		this.modelInstance = new ModelInstance(tileModel);
		Material material = modelInstance.materials.get(0);
		material.remove(ColorAttribute.Diffuse);
		material.set(TextureAttribute.createDiffuse((Texture) null));
		modelInstance.transform.setTranslation(col, 0, row);
	}

	public void lift(final float delta) {
		height += delta;
		if (modelInstance != null) {
			modelInstance.transform.translate(0, delta, 0);
		}
	}

	public void applyHeight(final float fixed) {
		height = fixed;
		if (modelInstance != null) {
			modelInstance.transform.val[M13] = fixed;
		}
	}

	public boolean equals(final int row, final int col) {
		return this.row == row && this.col == col;
	}

	public float getHeight() {
		return height;
	}
}
