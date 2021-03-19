package com.gadarts.necromine.model;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.gadarts.necromine.assets.Assets;
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
	MINE_WALL_1(Assets.Models.WALL_1, 1, 4, "Mine Wall 4x1", new Vector3(0.5f, 0, 0), MapNodesTypes.OBSTACLE_KEY_DIAGONAL_FORBIDDEN, true),
	MINE_WALL_2(Assets.Models.WALL_2, 1, 2, "Mine Wall 2x1", new Vector3(0.5f, 0, 0), MapNodesTypes.OBSTACLE_KEY_DIAGONAL_FORBIDDEN, true),
	CAVE_SUPPORTER_1(Assets.Models.CAVE_SUPPORTER_1, 1, 1, "Mine Wall Supporter #1", false, new Vector3(0.5f, 0, 0), MapNodesTypes.PASSABLE_NODE),
	CAVE_SUPPORTER_2(Assets.Models.CAVE_SUPPORTER_2, 1, 1, "Mine Wall Supporter #2", false, new Vector3(0.5f, 0, 0), MapNodesTypes.PASSABLE_NODE),
	CAVE_SUPPORTER_3(Assets.Models.CAVE_SUPPORTER_3, 1, 1, "Mine Wall Supporter #3", false, new Vector3(0.5f, 0, 0), MapNodesTypes.PASSABLE_NODE),
	PILLAR(Assets.Models.PILLAR, 1, 1, "Pillar", true, Vector3.Zero, MapNodesTypes.OBSTACLE_KEY_DIAGONAL_ALLOWED),
	STREET_LAMP(Assets.Models.STREET_LAMP, 1, 1, "Street Lamp", true, Vector3.Zero, MapNodesTypes.OBSTACLE_KEY_DIAGONAL_ALLOWED, new LightEmission(new Vector3(0, 2, 0), 1f, 3f));

	@Getter(AccessLevel.NONE)
	private final Vector3 offset;

	@Getter(AccessLevel.NONE)
	private final Assets.Models model;

	private final int width;
	private final int depth;
	private final String displayName;
	private final boolean castShadow;
	private final MapNodesTypes nodeType;
	private final boolean isWall;
	private final LightEmission lightEmission;

	EnvironmentDefinitions(final Assets.Models model,
						   final int width,
						   final int depth,
						   final String displayName,
						   final Vector3 offset,
						   final MapNodesTypes nodeType,
						   final boolean isWall) {
		this(model, width, depth, displayName, true, offset, nodeType, isWall, null);
	}

	EnvironmentDefinitions(final Assets.Models model,
						   final int width,
						   final int depth,
						   final String displayName,
						   final boolean castShadow,
						   final Vector3 offset,
						   final MapNodesTypes nodeType) {
		this(model, width, depth, displayName, castShadow, offset, nodeType, false, null);
	}

	EnvironmentDefinitions(final Assets.Models model,
						   final int width,
						   final int depth,
						   final String displayName,
						   final boolean castShadow,
						   final Vector3 offset,
						   final MapNodesTypes nodeType,
						   final LightEmission lightEmission) {
		this(model, width, depth, displayName, castShadow, offset, nodeType, false, lightEmission);
	}

	EnvironmentDefinitions(final Assets.Models model,
						   final int width,
						   final int depth,
						   final String displayName,
						   final boolean castShadow,
						   final Vector3 offset,
						   final MapNodesTypes nodeType,
						   final boolean isWall,
						   final LightEmission lightEmission) {
		this.model = model;
		this.width = width;
		this.depth = depth;
		this.displayName = displayName;
		this.castShadow = castShadow;
		this.offset = offset;
		this.nodeType = nodeType;
		this.isWall = isWall;
		this.lightEmission = lightEmission;
	}

	public Vector3 getOffset(final Vector3 output) {
		return output.set(offset);
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public Assets.Models getModelDefinition() {
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
