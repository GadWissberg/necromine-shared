package com.gadarts.necromine.assets;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.gadarts.necromine.assets.definitions.*;
import com.gadarts.necromine.model.pickups.WeaponsDefinitions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Definitions of the game content.
 */
public final class Assets {

	public static final String PATH_SEPARATOR = "/";

	private Assets() {
	}

	@Getter
	public enum AssetsTypes {
		ATLAS(Atlases.values()),
		MELODY(Melody.values()),
		SOUND(Sounds.values()),
		MODEL(Models.values()),
		SHADER(Shaders.values()),
		TEXTURE(TexturesTypes.getAllDefinitionsInSingleArray()),
		FONT(Fonts.values());

		private final AssetDefinition[] assetDefinitions;

		AssetsTypes(final AssetDefinition[] assetDefinitions) {
			this.assetDefinitions = assetDefinitions;
		}

	}

	/**
	 * Texture atlases.
	 */
	@Getter
	public enum Atlases implements AtlasDefinition {
		PLAYER_GENERIC(),
		PLAYER_AXE_PICK(WeaponsDefinitions.AXE_PICK),
		PLAYER_COLT(WeaponsDefinitions.COLT),
		PLAYER_HAMMER(WeaponsDefinitions.HAMMER),
		ZEALOT();

		private final String filePath;
		private final WeaponsDefinitions relatedWeapon;

		Atlases() {
			this(null);
		}

		Atlases(final WeaponsDefinitions relatedWeapon) {
			this.filePath = AtlasDefinition.FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + AtlasDefinition.FORMAT;
			this.relatedWeapon = relatedWeapon;
		}

		public static Atlases findByRelatedWeapon(final WeaponsDefinitions definition) {
			Atlases[] atlases = values();
			Atlases result = null;
			for (Atlases atlas : atlases) {
				if (atlas.getRelatedWeapon() == definition) {
					result = atlas;
					break;
				}

			}
			return result;
		}

		@Override
		public AssetLoaderParameters<Texture> getParameters() {
			return null;
		}

		@Override
		public Class<TextureAtlas> getTypeClass() {
			return TextureAtlas.class;
		}
	}

	/**
	 * Ogg files.
	 */
	@Getter
	public enum Melody implements MelodyDefinition {
		TEST;
		private final String filePath;

		Melody() {
			this.filePath = MelodyDefinition.FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + MelodyDefinition.FORMAT;
		}

		@Override
		public AssetLoaderParameters<Music> getParameters() {
			return null;
		}

		@Override
		public Class<Music> getTypeClass() {
			return Music.class;
		}
	}

	/**
	 * Shader files.
	 */
	@Getter
	public enum Shaders implements ShaderDefinition {
		VERTEX,
		FRAGMENT,
		DECAL_VERTEX,
		DECAL_FRAGMENT;

		private final String filePath;

		Shaders() {
			this.filePath = FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + FORMAT;
		}

		@Override
		public AssetLoaderParameters<String> getParameters() {
			return null;
		}

		@Override
		public Class<String> getTypeClass() {
			return String.class;
		}
	}

	/**
	 * Fonts files.
	 */
	@Getter
	public enum Fonts implements FontDefinition {
		CONSOLA(15),
		CHUBGOTHIC_SMALL("chubgothic", 40, true),
		CHUBGOTHIC_LARGE("chubgothic", 72, true);

		private final String filePath;
		private final FreetypeFontLoader.FreeTypeFontLoaderParameter params;
		private final String filename;

		Fonts(final int size) {
			this(null, size, false);
		}

		Fonts(final String filename, final int size, final boolean outline) {
			params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
			this.filename = filename != null ? filename : name().toLowerCase();
			String name = filename == null ? name().toLowerCase() : filename;
			filePath = FontDefinition.FOLDER + PATH_SEPARATOR + name + "." + FontDefinition.FORMAT;
			defineParams(size, outline);
		}

		private void defineParams(final int size, final boolean outline) {
			params.fontParameters.size = size;
			params.fontFileName = filePath;
			if (outline) {
				params.fontParameters.borderWidth = 1f;
				params.fontParameters.borderColor = new Color(0.5f, 0.1f, 0.1f, 1.0f);
			}
		}

		@Override
		public String getAssetManagerKey() {
			return filename + "_" + params.fontParameters.size + "." + FontDefinition.FORMAT;
		}

		@Override
		public AssetLoaderParameters<BitmapFont> getParameters() {
			return params;
		}

		@Override
		public Class<BitmapFont> getTypeClass() {
			return BitmapFont.class;
		}
	}

	/**
	 * Wave files.
	 */
	@Getter
	public enum Sounds implements SoundDefinition {
		STEP_1,
		STEP_2,
		STEP_3,
		ENEMY_ROAM,
		ENEMY_AWAKE,
		ENEMY_PAIN,
		ENEMY_DEATH,
		ATTACK_CLAW,
		ATTACK_AXE_PICK,
		ATTACK_COLT,
		ATTACK_HAMMER,
		PICKUP,
		PLAYER_PAIN,
		PLAYER_DEATH,
		UI_CLICK(false),
		UI_ITEM_SELECT(false),
		UI_ITEM_PLACED(false);

		private final String filePath;
		private final boolean randomPitch;

		Sounds() {
			this(true);
		}

		Sounds(final boolean randomPitch) {
			this.filePath = SoundDefinition.FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + SoundDefinition.FORMAT;
			this.randomPitch = randomPitch;
		}

		@Override
		public AssetLoaderParameters<Sound> getParameters() {
			return null;
		}

		@Override
		public Class<Sound> getTypeClass() {
			return Sound.class;
		}
	}

	/**
	 * 3D models.
	 */
	@Getter
	public enum Models implements ModelDefinition {
		WALL_1,
		WALL_2,
		COLT,
		HAMMER,
		PILLAR,
		CAVE_SUPPORTER_1(),
		CAVE_SUPPORTER_2(),
		CAVE_SUPPORTER_3(),
		CURSOR(0.1F);

		private final String filePath;
		private final float alpha;

		Models() {
			this(1.0f);
		}

		Models(final float alpha) {
			this.filePath = ModelDefinition.FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + ModelDefinition.FORMAT;
			this.alpha = alpha;
		}

		@Override
		public AssetLoaderParameters<Model> getParameters() {
			return null;
		}

		@Override
		public Class<Model> getTypeClass() {
			return Model.class;
		}
	}

	@Getter
	public enum TexturesTypes {
		Floors(FloorsTextures.values()),
		UI(UiTextures.values());

		private final TextureDefinition[] definitions;

		TexturesTypes(final TextureDefinition[] definitions) {
			this.definitions = definitions;
		}

		public static TextureDefinition[] getAllDefinitionsInSingleArray() {
			ArrayList<TextureDefinition> list = new ArrayList<>();
			Arrays.stream(values()).forEach(defs -> list.addAll(Arrays
					.stream(defs.getDefinitions())
					.collect(Collectors.toList()))
			);
			return list.toArray(new TextureDefinition[0]);
		}
	}

	/**
	 * Image files of floors.
	 */
	public enum FloorsTextures implements TextureDefinition {
		FLOOR_0,
		FLOOR_1,
		FLOOR_2,
		FLOOR_3;

		@Override
		public String getSubFolderName() {
			return "floors";
		}

		@Override
		public String getName() {
			return name();
		}

		@Override
		public AssetLoaderParameters<Texture> getParameters() {
			return null;
		}
	}

	/**
	 * Image files of UI components.
	 */
	@Getter
	public enum UiTextures implements TextureDefinition {
		PATH_ARROW,
		BULB,
		BUTTON_STORAGE(null, "buttons"),
		BUTTON_STORAGE_DOWN(null, "buttons"),
		BUTTON_STORAGE_HOVER(null, "buttons"),
		BUTTON_CLOSE(null, "buttons"),
		BUTTON_CLOSE_DOWN(null, "buttons"),
		BUTTON_CLOSE_HOVER(null, "buttons"),
		NINEPATCHES("ninepatches.9"),
		WEAPON_AXE_PICK(null, "weapons"),
		WEAPON_HAMMER(null, "weapons"),
		WEAPON_COLT(null, "weapons"),
		PLAYER_LAYOUT;

		public static final String SUB_FOLDER_NAME = "ui";
		private final String specialFileName;
		private final String subSubFolder;

		UiTextures() {
			this(null);
		}

		UiTextures(final String specialFileName) {
			this(specialFileName, null);
		}

		UiTextures(final String specialFileName, final String subSubFolder) {
			this.specialFileName = specialFileName;
			this.subSubFolder = subSubFolder;
		}

		@Override
		public String getSubFolderName() {
			return subSubFolder != null ? SUB_FOLDER_NAME + PATH_SEPARATOR + subSubFolder : SUB_FOLDER_NAME;
		}

		@Override
		public String getName() {
			return specialFileName != null ? specialFileName : name();
		}

		@Override
		public AssetLoaderParameters<Texture> getParameters() {
			return null;
		}
	}

}
