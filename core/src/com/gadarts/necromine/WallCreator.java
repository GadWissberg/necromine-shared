package com.gadarts.necromine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.gadarts.necromine.assets.Assets.SurfaceTextures;
import com.gadarts.necromine.assets.GameAssetsManager;
import com.gadarts.necromine.model.Coords;
import com.gadarts.necromine.model.MapNodeData;
import com.gadarts.necromine.model.Wall;
import lombok.Getter;

import java.util.Optional;

import static com.gadarts.necromine.assets.Assets.SurfaceTextures.MISSING;

/**
 * A tool to generate walls for nodes.
 */
public class WallCreator implements Disposable {
	private static final Vector3 auxVector3_1 = new Vector3();
	private static final Vector3 auxVector3_2 = new Vector3();
	private final GameAssetsManager assetsManager;

	@Getter
	private Model wallModel;

	public WallCreator(final GameAssetsManager assetsManager) {
		this.assetsManager = assetsManager;
		createWallModel();
	}

	/**
	 * Creates a western wall model for a given node.
	 *
	 * @param n             The target node.
	 * @param wallModel     The wall model used for creating the model instance.
	 * @param assetsManager Used to get the texture.
	 * @param definition    The texture definition for the wall.
	 */
	public static Wall createWestWall(final MapNodeData n,
									  final Model wallModel,
									  final GameAssetsManager assetsManager,
									  final SurfaceTextures definition) {
		Wall westWall = createWall(wallModel, assetsManager, definition);
		ModelInstance modelInstance = westWall.getModelInstance();
		Coords coords = n.getCoords();
		modelInstance.transform.setToTranslation(coords.getCol(), 0, coords.getRow());
		return westWall;
	}

	/**
	 * Creates a southern wall model for a given node.
	 *
	 * @param n             The target node.
	 * @param wallModel     The wall model used for creating the model instance.
	 * @param assetsManager Used to get the texture.
	 * @param definition    The texture definition for the wall.
	 */
	public static Wall createSouthWall(final MapNodeData n,
									   final Model wallModel,
									   final GameAssetsManager assetsManager,
									   final SurfaceTextures definition) {
		Wall southWall = createWall(wallModel, assetsManager, definition);
		ModelInstance modelInstance = southWall.getModelInstance();
		Coords coords = n.getCoords();
		modelInstance.transform.setToTranslation(coords.getCol(), 0, coords.getRow() + 1);
		modelInstance.transform.rotate(Vector3.X, 90);
		return southWall;
	}

	/**
	 * Creates an eastern wall model for a given node.
	 *
	 * @param n             The target node.
	 * @param wallModel     The wall model used for creating the model instance.
	 * @param assetsManager Used to get the texture.
	 * @param definition    The texture definition for the wall.
	 */
	public static Wall createEastWall(final MapNodeData n,
									  final Model wallModel,
									  final GameAssetsManager assetsManager,
									  final SurfaceTextures definition) {
		Wall eastWall = createWall(wallModel, assetsManager, definition);
		ModelInstance modelInstance = eastWall.getModelInstance();
		Coords coords = n.getCoords();
		modelInstance.transform.setToTranslation(coords.getCol() + 1F, 0, coords.getRow());
		return eastWall;
	}

	/**
	 * Creates a northern wall model for a given node.
	 *
	 * @param n             The target node.
	 * @param wallModel     The wall model used for creating the model instance.
	 * @param assetsManager Used to get the texture.
	 * @param definition    The texture definition for the wall.
	 */
	public static Wall createNorthWall(final MapNodeData n,
									   final Model wallModel,
									   final GameAssetsManager assetsManager,
									   final SurfaceTextures definition) {
		Wall northWall = createWall(wallModel, assetsManager, definition);
		ModelInstance modelInstance = northWall.getModelInstance();
		Coords coords = n.getCoords();
		modelInstance.transform.setToTranslation(coords.getCol(), 0, coords.getRow());
		return northWall;
	}

	/**
	 * Creates a wall model.
	 *
	 * @param wallModel     The wall model used for creating the model instance.
	 * @param assetsManager Used to get the texture.
	 * @param definition    The texture definition for the wall.
	 * @return
	 */
	public static Wall createWall(final Model wallModel,
								  final GameAssetsManager assetsManager,
								  final SurfaceTextures definition) {
		ModelInstance modelInstance = new ModelInstance(wallModel);
		TextureAttribute textureAttribute = (TextureAttribute) modelInstance.materials.get(0).get(TextureAttribute.Diffuse);
		textureAttribute.textureDescription.texture = assetsManager.getTexture(definition);
		return new Wall(modelInstance, definition);
	}

	public static void adjustWallBetweenNorthAndSouth(final MapNodeData southernN,
													  final MapNodeData northernN) {
		adjustWallBetweenNorthAndSouth(southernN, northernN, 0);
	}

	public static void adjustWallBetweenNorthAndSouth(final MapNodeData southernN,
													  final MapNodeData northernN,
													  final float vScale) {
		Wall wallBetween = Optional.ofNullable(southernN.getWalls().getNorthWall()).orElse(northernN.getWalls().getSouthWall());
		ModelInstance modelInstance = wallBetween.getModelInstance();
		TextureAttribute textureAtt = (TextureAttribute) modelInstance.materials.get(0).get(TextureAttribute.Diffuse);
		textureAtt.scaleV = adjustWallBetweenTwoNodes(southernN, northernN, wallBetween);
		textureAtt.scaleV = vScale != 0 ? vScale : textureAtt.scaleV;
		float degrees = (southernN.getHeight() > northernN.getHeight() ? -1 : 1) * 90F;
		if (southernN.getHeight() > northernN.getHeight()) {
			textureAtt.scaleV *= -1;
			textureAtt.scaleU *= -1;
		}
		modelInstance.transform.rotate(Vector3.X, degrees);
	}

	public static void adjustWallBetweenEastAndWest(final MapNodeData eastNode,
													final MapNodeData westNode,
													final boolean hasJustBeenCreated) {
		adjustWallBetweenEastAndWest(eastNode, westNode, hasJustBeenCreated, 0);
	}

	public static void adjustWallBetweenEastAndWest(final MapNodeData eastNode,
													final MapNodeData westNode,
													final boolean hasJustBeenCreated,
													final float vScale) {
		Wall wallBetween = Optional.ofNullable(eastNode.getWalls().getWestWall()).orElse(westNode.getWalls().getEastWall());
		ModelInstance modelInstance = wallBetween.getModelInstance();
		TextureAttribute textureAtt = (TextureAttribute) modelInstance.materials.get(0).get(TextureAttribute.Diffuse);
		textureAtt.scaleV = adjustWallBetweenTwoNodes(eastNode, westNode, wallBetween);
		textureAtt.scaleV = vScale != 0 ? vScale : textureAtt.scaleV;
		boolean eastHigherThanWest = eastNode.getHeight() > westNode.getHeight();
		modelInstance.transform.getTranslation(auxVector3_1).z = eastNode.getCoords().getRow();
		modelInstance.transform.getScale(auxVector3_2);
		modelInstance.transform.setToTranslationAndScaling(auxVector3_1, auxVector3_2);
		modelInstance.transform.rotate(Vector3.Y, (eastHigherThanWest ? -1 : 1) * 90F);
		modelInstance.transform.rotate(Vector3.X, 90F);
		if (eastHigherThanWest) {
			modelInstance.transform.translate(0F, 0F, -1F);
		} else {
			modelInstance.transform.translate(-1F, 0F, 0F);
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

	private void createWallModel( ) {
		ModelBuilder modelBuilder = new ModelBuilder();
		wallModel = modelBuilder.createRect(
				0, 0, 1,
				1, 0, 1,
				1, 0, 0,
				0, 0, 0,
				0, 1, 0,
				new Material(TextureAttribute.createDiffuse((Texture) null)),
				Usage.Position | Usage.Normal | Usage.TextureCoordinates);
	}

	public void adjustNorthWall(final MapNodeData southernNode,
								final MapNodeData northernNode) {
		if (northernNode.getHeight() != southernNode.getHeight()) {
			if (northernNode.getWalls().getSouthWall() == null && southernNode.getWalls().getNorthWall() == null) {
				southernNode.getWalls().setNorthWall(createNorthWall(southernNode, wallModel, assetsManager, MISSING));
			}
			adjustWallBetweenNorthAndSouth(southernNode, northernNode);
		} else {
			clearWallBetweenNorthAndSouthNodes(northernNode, southernNode);
		}
	}

	public void adjustSouthWall(final MapNodeData northernNode,
								final MapNodeData southernNode) {
		if (northernNode.getHeight() != southernNode.getHeight()) {
			if (southernNode.getWalls().getNorthWall() == null && northernNode.getWalls().getSouthWall() == null) {
				northernNode.getWalls().setSouthWall(createSouthWall(northernNode, wallModel, assetsManager, MISSING));
			}
			adjustWallBetweenNorthAndSouth(southernNode, northernNode, 0);
		} else {
			clearWallBetweenNorthAndSouthNodes(northernNode, southernNode);
		}
	}

	public void adjustEastWall(final MapNodeData westernNode,
							   final MapNodeData easternNode) {
		if (easternNode.getHeight() != westernNode.getHeight()) {
			boolean hasJustBeenCreated = false;
			if (westernNode.getWalls().getEastWall() == null && easternNode.getWalls().getWestWall() == null) {
				westernNode.getWalls().setEastWall(createEastWall(westernNode, wallModel, assetsManager, MISSING));
				hasJustBeenCreated = true;
			}
			adjustWallBetweenEastAndWest(easternNode, westernNode, hasJustBeenCreated, 0);
		} else {
			clearWallBetweenWestAndEastNodes(westernNode, easternNode);
		}
	}

	private void clearWallBetweenWestAndEastNodes(MapNodeData westernNode, MapNodeData easternNode) {
		easternNode.getWalls().setWestWall(null);
		westernNode.getWalls().setEastWall(null);
	}

	private void clearWallBetweenNorthAndSouthNodes(MapNodeData northernNode, MapNodeData southernNode) {
		southernNode.getWalls().setNorthWall(null);
		northernNode.getWalls().setSouthWall(null);
	}

	public void adjustWestWall(final MapNodeData easternNode, final MapNodeData westernNode) {
		if (easternNode.getHeight() != westernNode.getHeight()) {
			boolean hasJustBeenCreated = false;
			if (westernNode.getWalls().getEastWall() == null && easternNode.getWalls().getWestWall() == null) {
				easternNode.getWalls().setWestWall(createWestWall(easternNode, wallModel, assetsManager, MISSING));
				hasJustBeenCreated = true;
			}
			adjustWallBetweenEastAndWest(easternNode, westernNode, hasJustBeenCreated, 0);
		} else {
			clearWallBetweenWestAndEastNodes(westernNode, easternNode);
		}
	}

	@Override
	public void dispose( ) {
		wallModel.dispose();
	}
}
