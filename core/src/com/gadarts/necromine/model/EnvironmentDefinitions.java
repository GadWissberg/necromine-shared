package com.gadarts.necromine.model;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.gadarts.necromine.assets.Assets;
import com.gadarts.necromine.assets.Assets.Models;
import com.gadarts.necromine.model.characters.Direction;
import lombok.AccessLevel;
import lombok.Getter;

import static com.gadarts.necromine.model.characters.Direction.EAST;
import static com.gadarts.necromine.model.characters.Direction.NORTH;

/**
 * Definitions of misc objects.
 */
@Getter
public enum EnvironmentDefinitions implements ModelElementDefinition {
	SARCOPHAG_1(Models.SARCOPHAG_1, 1, 1, "Sarcophag #1", true, new Vector3(-0.4F, 0, 0), MapNodesTypes.OBSTACLE_KEY_DIAGONAL_ALLOWED),
	CAVE_SUPPORTER_1(Models.CAVE_SUPPORTER_1, 1, 1, "Mine Wall Supporter #1", false, new Vector3(0.5f, 0, 0), MapNodesTypes.PASSABLE_NODE),
	CAVE_SUPPORTER_2(Models.CAVE_SUPPORTER_2, 1, 1, "Mine Wall Supporter #2", false, new Vector3(0.5f, 0, 0), MapNodesTypes.PASSABLE_NODE),
	CAVE_SUPPORTER_3(Models.CAVE_SUPPORTER_3, 1, 1, "Mine Wall Supporter #3", false, new Vector3(0.5f, 0, 0), MapNodesTypes.PASSABLE_NODE),
	PILLAR(Models.PILLAR, 1, 1, "Pillar", true, Vector3.Zero, MapNodesTypes.OBSTACLE_KEY_DIAGONAL_ALLOWED),
	STREET_LAMP(Models.STREET_LAMP, 1, 1, "Street Lamp", true, Vector3.Zero, MapNodesTypes.PASSABLE_NODE, new LightEmission(new Vector3(0, 2, 0), 1f, 3f)),
	STREET_DOOR(Models.STREET_DOOR, 1, 1, "Street Door", false, Vector3.Zero, MapNodesTypes.PASSABLE_NODE, true),
	STREET_SIGN(Models.STREET_SIGN, 1, 1, "Easy St.", true, Vector3.Zero, MapNodesTypes.PASSABLE_NODE),
	FIRE_HYDRANT(Models.FIRE_HYDRANT, 1, 1, "Fire Hydrant", true, Vector3.Zero, MapNodesTypes.OBSTACLE_KEY_DIAGONAL_ALLOWED),
	SIGN(Models.SIGN, 1, 1, "Syphilis", true, Vector3.Zero, MapNodesTypes.OBSTACLE_KEY_DIAGONAL_ALLOWED),
	WINDOW(Models.WINDOW, 1, 1, "Window", false, Vector3.Zero, MapNodesTypes.PASSABLE_NODE, true),
	MINE_WALL_3(Models.CAVE_WALL_SINGLE, 1, 1, "Mine Wall 1x1", new Vector3(0.5f, 0, 0), MapNodesTypes.OBSTACLE_KEY_DIAGONAL_FORBIDDEN, true, true);

	@Getter(AccessLevel.NONE)
	private final Vector3 offset;

	@Getter(AccessLevel.NONE)
	private final Models model;

	private final int width;
	private final int depth;
	private final String displayName;
	private final boolean castShadow;
	private final MapNodesTypes nodeType;
	private final boolean isWall;
	private final LightEmission lightEmission;
	private final boolean renderWhenFrontOnly;

	EnvironmentDefinitions(final Models model,
						   final int width,
						   final int depth,
						   final String displayName,
						   final Vector3 offset,
						   final MapNodesTypes nodeType,
						   final boolean isWall,
						   final boolean renderWhenFrontOnly) {
		this(model, width, depth, displayName, true, offset, nodeType, isWall, null, renderWhenFrontOnly);
	}

	EnvironmentDefinitions(final Models model,
						   final int width,
						   final int depth,
						   final String displayName,
						   final boolean castShadow,
						   final Vector3 offset,
						   final MapNodesTypes nodeType,
						   final boolean renderWhenFrontOnly) {
		this(model, width, depth, displayName, castShadow, offset, nodeType, false, null, renderWhenFrontOnly);
	}

	EnvironmentDefinitions(final Models model,
						   final int width,
						   final int depth,
						   final String displayName,
						   final boolean castShadow,
						   final Vector3 offset,
						   final MapNodesTypes nodeType) {
		this(model, width, depth, displayName, castShadow, offset, nodeType, false, null, false);
	}

	EnvironmentDefinitions(final Models model,
						   final int width,
						   final int depth,
						   final String displayName,
						   final boolean castShadow,
						   final Vector3 offset,
						   final MapNodesTypes nodeType,
						   final LightEmission lightEmission) {
		this(model, width, depth, displayName, castShadow, offset, nodeType, false, lightEmission, false);
	}

	EnvironmentDefinitions(final Models model,
						   final int width,
						   final int depth,
						   final String displayName,
						   final boolean castShadow,
						   final Vector3 offset,
						   final MapNodesTypes nodeType,
						   final boolean isWall,
						   final LightEmission lightEmission,
						   final boolean renderWhenFrontOnly) {
		this.model = model;
		this.width = width;
		this.depth = depth;
		this.displayName = displayName;
		this.castShadow = castShadow;
		this.offset = offset;
		this.nodeType = nodeType;
		this.isWall = isWall;
		this.lightEmission = lightEmission;
		this.renderWhenFrontOnly = renderWhenFrontOnly;
	}

	public Vector3 getOffset(final Vector3 output) {
		return output.set(offset);
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public Models getModelDefinition() {
		return model;
	}

	/**
	 * When objects have even width or depth, this method will correct their position.
	 *
	 * @param definition      The definition of the misc object.
	 * @param modelInstance   The model of the misc object.
	 * @param facingDirection The facing direction of the misc object.
	 */
	public static void handleEvenSize(final EnvironmentDefinitions definition,
									  final ModelInstance modelInstance,
									  final Direction facingDirection) {
		boolean handleEvenSize = facingDirection == EAST || facingDirection == NORTH;
		if (definition.getWidth() % 2 == 0) {
			modelInstance.transform.translate(0.5f * (handleEvenSize ? -1 : 1), 0, 0);
		}
		if (definition.getDepth() % 2 == 0) {
			modelInstance.transform.translate(0, 0, 0.5f * (handleEvenSize ? -1 : 1));
		}
	}
}
