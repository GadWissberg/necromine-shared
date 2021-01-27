package com.gadarts.necromine.model;

import com.gadarts.necromine.assets.Assets;
import lombok.Getter;

@Getter
public enum EnvironmentDefinitions implements ElementDefinition {
    MINE_WALL_1(Assets.Models.WALL_1, 4, 1, "Mine Wall 4x1"),
    MINE_WALL_2(Assets.Models.WALL_2, 2, 1, "Mine Wall 2x1"),
    CAVE_SUPPORTER_1(Assets.Models.CAVE_SUPPORTER_1, 1, 1, "Mine Wall Supporter #1"),
    CAVE_SUPPORTER_2(Assets.Models.CAVE_SUPPORTER_2, 1, 1, "Mine Wall Supporter #2"),
    CAVE_SUPPORTER_3(Assets.Models.CAVE_SUPPORTER_3, 1, 1, "Mine Wall Supporter #3"),
    PILLAR(Assets.Models.PILLAR, 1, 1, "Pillar", true);

    private final Assets.Models model;
    private final int width;
    private final int height;
    private final String displayName;
    private final boolean castShadow;

    EnvironmentDefinitions(final Assets.Models model, final int width, final int height, final String displayName) {
        this(model, width, height, displayName, false);
    }

    EnvironmentDefinitions(final Assets.Models model,
                           final int width,
                           final int height,
                           final String displayName,
                           final boolean castShadow) {
        this.model = model;
        this.width = width;
        this.height = height;
        this.displayName = displayName;
        this.castShadow = castShadow;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
