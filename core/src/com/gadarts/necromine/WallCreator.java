package com.gadarts.necromine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.gadarts.necromine.assets.Assets.FloorsTextures;
import com.gadarts.necromine.assets.GameAssetsManager;
import com.gadarts.necromine.model.MapNodeData;
import com.gadarts.necromine.model.Wall;
import lombok.Getter;

import java.util.Optional;

public class WallCreator implements Disposable {
	private static final Vector3 auxVector3_1 = new Vector3();
	private final GameAssetsManager assetsManager;

	@Getter
	private Model wallModel;

	public WallCreator(final GameAssetsManager assetsManager) {
		this.assetsManager = assetsManager;
		createWallModel();
	}

	public static Wall createWestWall(final MapNodeData n,
									  final Model wallModel,
									  final GameAssetsManager assetsManager,
									  final FloorsTextures definition) {
		Wall westWall = createWall(wallModel, assetsManager, definition);
		ModelInstance modelInstance = westWall.getModelInstance();
		modelInstance.transform.setToTranslation(n.getCol(), 0, n.getRow());
		return westWall;
	}

	public static Wall createSouthWall(final MapNodeData n,
									   final Model wallModel,
									   final GameAssetsManager assetsManager,
									   final FloorsTextures definition) {
		Wall southWall = createWall(wallModel, assetsManager, definition);
		ModelInstance modelInstance = southWall.getModelInstance();
		modelInstance.transform.setToTranslation(n.getCol(), 0, n.getRow() + 1);
		modelInstance.transform.rotate(Vector3.X, 90);
		return southWall;
	}

	public static Wall createEastWall(final MapNodeData n,
									  final Model wallModel,
									  final GameAssetsManager assetsManager,
									  final FloorsTextures definition) {
		Wall eastWall = createWall(wallModel, assetsManager, definition);
		ModelInstance modelInstance = eastWall.getModelInstance();
		modelInstance.transform.setToTranslation(n.getCol() + 1F, 0, n.getRow());
		return eastWall;
	}

	public static Wall createNorthWall(final MapNodeData n,
									   final Model wallModel,
									   final GameAssetsManager assetsManager,
									   final FloorsTextures definition) {
		Wall northWall = createWall(wallModel, assetsManager, definition);
		ModelInstance modelInstance = northWall.getModelInstance();
		modelInstance.transform.setToTranslation(n.getCol(), 0, n.getRow());
		modelInstance.transform.rotate(Vector3.X, -90);
		return northWall;
	}

	public static Wall createWall(final Model wallModel,
								  final GameAssetsManager assetsManager,
								  final FloorsTextures definition) {
		ModelInstance modelInstance = new ModelInstance(wallModel);
		TextureAttribute textureAttribute = (TextureAttribute) modelInstance.materials.get(0).get(TextureAttribute.Diffuse);
		textureAttribute.textureDescription.texture = assetsManager.getTexture(definition);
		return new Wall(modelInstance, FloorsTextures.FLOOR_PAVEMENT_0);
	}

	public static void adjustWallBetweenNorthAndSouth(final MapNodeData southernNode,
													  final MapNodeData northernNode) {
		Wall wallBetween = Optional.ofNullable(southernNode.getNorthWall()).orElse(northernNode.getSouthWall());
		ModelInstance modelInstance = wallBetween.getModelInstance();
		TextureAttribute textureAttribute = (TextureAttribute) modelInstance.materials.get(0).get(TextureAttribute.Diffuse);
		textureAttribute.scaleV = adjustWallBetweenTwoNodes(southernNode, northernNode, wallBetween);
		float degrees = (southernNode.getHeight() > northernNode.getHeight() ? -1 : 1) * 90F;
		modelInstance.transform.rotate(Vector3.X, degrees);
	}

	public static void adjustWallBetweenEastAndWest(final MapNodeData eastNode,
													final MapNodeData westNode,
													final boolean hasJustBeenCreated) {
		Wall wallBetween = Optional.ofNullable(eastNode.getWestWall()).orElse(westNode.getEastWall());
		ModelInstance modelInstance = wallBetween.getModelInstance();
		TextureAttribute textureAttribute = (TextureAttribute) modelInstance.materials.get(0).get(TextureAttribute.Diffuse);
		textureAttribute.scaleV = adjustWallBetweenTwoNodes(eastNode, westNode, wallBetween);
		boolean eastHigherThanWest = eastNode.getHeight() > westNode.getHeight();
		modelInstance.transform.rotate(Vector3.Y, (eastHigherThanWest ? -1 : 1) * 90F);
		modelInstance.transform.rotate(Vector3.X, 90F);
		if (hasJustBeenCreated) {
			if (eastHigherThanWest) {
				modelInstance.transform.translate(0F, 0F, -1F);
			} else {
				modelInstance.transform.translate(-1F, 0F, 0F);
			}
		}
	}

	public static float adjustWallBetweenTwoNodes(final MapNodeData eastOrSouthNode,
												  final MapNodeData westOrNorthNode,
												  final Wall wallBetween) {
		Vector3 wallBetweenThemPos = wallBetween.getModelInstance().transform.getTranslation(auxVector3_1);
		float eastOrSouthHeight = eastOrSouthNode.getHeight();
		float westOrNorthHeight = westOrNorthNode.getHeight();
		float sizeHeight = Math.abs(westOrNorthHeight - eastOrSouthHeight);
		float y = Math.min(eastOrSouthHeight, westOrNorthHeight) + (eastOrSouthHeight > westOrNorthHeight ? 0 : sizeHeight);
		wallBetween.getModelInstance().transform.setToTranslationAndScaling(wallBetweenThemPos.x, y, wallBetweenThemPos.z,
				1, sizeHeight, 1);
		return sizeHeight;
	}

	private void createWallModel() {
		ModelBuilder modelBuilder = new ModelBuilder();
		wallModel = modelBuilder.createRect(
				0, 0, 1,
				1, 0, 1,
				1, 0, 0,
				0, 0, 0,
				0, 1, 0,
				new Material(TextureAttribute.createDiffuse((Texture) null)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
	}

	public void adjustNorthWall(final MapNodeData southernNode,
								final MapNodeData northernNode) {
		if (northernNode.getHeight() == southernNode.getHeight()) return;
		if (northernNode.getSouthWall() == null && southernNode.getNorthWall() == null) {
			southernNode.setNorthWall(createNorthWall(southernNode, wallModel, assetsManager, FloorsTextures.FLOOR_PAVEMENT_0));
		}
		adjustWallBetweenNorthAndSouth(southernNode, northernNode);
	}

	public void adjustSouthWall(final MapNodeData northernNode,
								final MapNodeData southernNode) {
		if (northernNode.getHeight() == southernNode.getHeight()) return;
		if (southernNode.getNorthWall() == null && northernNode.getSouthWall() == null) {
			northernNode.setSouthWall(createSouthWall(northernNode, wallModel, assetsManager, FloorsTextures.FLOOR_PAVEMENT_0));
		}
		adjustWallBetweenNorthAndSouth(southernNode, northernNode);
	}

	public void adjustEastWall(final MapNodeData westernNode,
							   final MapNodeData easternNode) {
		if (easternNode.getHeight() == westernNode.getHeight()) return;
		boolean hasJustBeenCreated = false;
		if (westernNode.getEastWall() == null && easternNode.getWestWall() == null) {
			westernNode.setEastWall(createEastWall(westernNode, wallModel, assetsManager, FloorsTextures.FLOOR_PAVEMENT_0));
			hasJustBeenCreated = true;
		}
		adjustWallBetweenEastAndWest(easternNode, westernNode, hasJustBeenCreated);
	}

	public void adjustWestWall(final MapNodeData easternNode, final MapNodeData westernNode) {
		if (easternNode.getHeight() == westernNode.getHeight()) return;
		boolean hasJustBeenCreated = false;
		if (westernNode.getEastWall() == null && easternNode.getWestWall() == null) {
			easternNode.setWestWall(createWestWall(easternNode, wallModel, assetsManager, FloorsTextures.FLOOR_PAVEMENT_0));
			hasJustBeenCreated = true;
		}
		adjustWallBetweenEastAndWest(easternNode, westernNode, hasJustBeenCreated);
	}

	@Override
	public void dispose() {
		wallModel.dispose();
	}
}
