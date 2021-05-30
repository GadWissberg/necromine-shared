package com.gadarts.necromine.model.pickups;

import com.gadarts.necromine.assets.Assets;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public enum WeaponsDefinitions implements ItemDefinition {
	AXE_PICK(4, 5, 4, Assets.UiTextures.WEAPON_AXE_PICK, Assets.Sounds.ATTACK_AXE_PICK, new int[]{
			1, 1, 1, 1,
			0, 1, 0, 0,
			0, 1, 0, 0,
			0, 1, 0, 0,
			0, 1, 0, 0,
	}, true, "Axe-Pick"),
	COLT(2, 2, 2, Assets.UiTextures.WEAPON_COLT, Assets.Sounds.ATTACK_COLT, new int[]{
			1, 1,
			1, 0
	}, "Colt M1911", Assets.Models.COLT);

	@Getter(AccessLevel.NONE)
	private final int width;

	@Getter(AccessLevel.NONE)
	private final int height;

	@Getter(AccessLevel.NONE)
	private final Assets.UiTextures image;

	@Getter(AccessLevel.NONE)
	private final int[] mask;

	private final Assets.Sounds attackSound;
	private final boolean melee;
	private final int hitFrameIndex;
	private final String displayName;
	private final Assets.Models modelDef;

	WeaponsDefinitions(final int width,
					   final int height,
					   final int hitFrameIndex,
					   final Assets.UiTextures image,
					   final Assets.Sounds attackSound,
					   final int[] mask,
					   final boolean melee,
					   final String displayName,
					   final Assets.Models model) {
		this.width = width;
		this.height = height;
		this.hitFrameIndex = hitFrameIndex;
		this.image = image;
		this.attackSound = attackSound;
		this.mask = flipMatrixVertically(mask);
		this.melee = melee;
		this.displayName = displayName;
		this.modelDef = model;
	}

	WeaponsDefinitions(final int width,
					   final int depth,
					   final int hitFrameIndex,
					   final Assets.UiTextures image,
					   final Assets.Sounds attackSound,
					   final int[] mask,
					   final boolean melee,
					   final String displayName) {
		this(width, depth, hitFrameIndex, image, attackSound, mask, melee, displayName, null);
	}

	WeaponsDefinitions(final int width,
					   final int depth,
					   final int hitFrameIndex,
					   final Assets.UiTextures image,
					   final Assets.Sounds attackSound,
					   final int[] mask,
					   final String displayName,
					   final Assets.Models model) {
		this(width, depth, hitFrameIndex, image, attackSound, mask, false, displayName, model);
	}

	private int[] flipMatrixVertically(final int[] mask) {
		int[] flipped = new int[mask.length];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				flipped[row * width + col] = mask[((height - 1 - row) * width) + col];
			}
		}
		return flipped;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int[] getMask() {
		return mask;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getId() {
		return ordinal() + 1;
	}

	@Override
	public Assets.UiTextures getImage() {
		return image;
	}


	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public Assets.Models getModelDefinition() {
		return modelDef;
	}
}
