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
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.utils.Array;
import com.gadarts.necromine.assets.definitions.*;
import com.gadarts.necromine.model.pickups.WeaponsDefinitions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Definitions of the game content.
 */
public final class Assets {

	public static final String PATH_SEPARATOR = "/";

	private Assets( ) {
	}

	@Getter
	@RequiredArgsConstructor
	public enum AssetsTypes {
		ATLAS(Atlases.values()),
		MELODY(Melody.values()),
		SOUND(Sounds.values()),
		MODEL(Models.values()),
		SHADER(Shaders.values()),
		TEXTURE(TexturesTypes.getAllDefinitionsInSingleArray()),
		PARTICLES(ParticleEffects.values(), true),
		FONT(Fonts.values());

		private final AssetDefinition[] assetDefinitions;
		private final boolean manualLoad;

		AssetsTypes(final AssetDefinition[] assetDefinitions) {
			this(assetDefinitions, false);
		}

	}

	/**
	 * Texture atlases.
	 */
	@Getter
	public enum Atlases implements AtlasDefinition {
		PLAYER_GENERIC,
		PLAYER_KNIFE(WeaponsDefinitions.KNIFE),
		PLAYER_COLT(WeaponsDefinitions.COLT),
		SCORPION,
		ANUBIS,
		FLAME;

		private final String filePath;
		private final WeaponsDefinitions relatedWeapon;

		Atlases( ) {
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
		public AssetLoaderParameters<Texture> getParameters( ) {
			return null;
		}

		@Override
		public Class<TextureAtlas> getTypeClass( ) {
			return TextureAtlas.class;
		}

		@Override
		public String getName( ) {
			return name();
		}
	}

	/**
	 * Ogg files.
	 */
	@Getter
	public enum Melody implements MelodyDefinition {
		TEST;
		private final String filePath;

		Melody( ) {
			this.filePath = MelodyDefinition.FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + MelodyDefinition.FORMAT;
		}

		@Override
		public AssetLoaderParameters<Music> getParameters( ) {
			return null;
		}

		@Override
		public Class<Music> getTypeClass( ) {
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
		DECAL_FRAGMENT,
		DEPTHMAP_VERTEX,
		DEPTHMAP_FRAGMENT,
		SHADOW_VERTEX,
		SHADOW_FRAGMENT;

		private final String filePath;

		Shaders( ) {
			this.filePath = FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + FORMAT;
		}

		@Override
		public AssetLoaderParameters<String> getParameters( ) {
			return null;
		}

		@Override
		public Class<String> getTypeClass( ) {
			return String.class;
		}
	}

	/**
	 * Shader files.
	 */

	@Getter
	public enum ParticleEffects implements ParticleDefinition {
		BLOOD_SPLATTER, ENERGY_BALL_EXPLOSION, ENERGY_BALL_TRAIL, BULLET_RICOCHET;

		private final String filePath;

		ParticleEffects( ) {
			this.filePath = FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + FORMAT;
		}

		@Override
		public AssetLoaderParameters getParameters( ) {
			return null;
		}

		@Override
		public String getSubFolderName( ) {
			return null;
		}

		@Override
		public Class<ParticleEffect> getTypeClass( ) {
			return ParticleEffect.class;
		}

		@Override
		public String getName( ) {
			return name();
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
		public String getAssetManagerKey( ) {
			return filename + "_" + params.fontParameters.size + "." + FontDefinition.FORMAT;
		}

		@Override
		public AssetLoaderParameters<BitmapFont> getParameters( ) {
			return params;
		}

		@Override
		public Class<BitmapFont> getTypeClass( ) {
			return BitmapFont.class;
		}
	}

	/**
	 * Wave files.
	 */
	@Getter
	public enum Sounds implements SoundDefinition {
		STEP("step_1", "step_2", "step_3"),
		ENEMY_ROAM("enemy_roam_1", "enemy_roam_2", "enemy_roam_3"),
		ENEMY_AWAKE("enemy_awake_1", "enemy_awake_2", "enemy_awake_3"),
		ENEMY_PAIN("enemy_pain_1", "enemy_pain_2", "enemy_pain_3"),
		ENEMY_DEATH("enemy_death_1", "enemy_death_2", "enemy_death_3"),
		ATTACK_FIST,
		ATTACK_ENERGY_BALL,
		SMALL_EXP,
		SCORPION_ATTACK,
		SCORPION_AWAKE("scorpion_awake_1", "scorpion_awake_2"),
		SCORPION_PAIN,
		SCORPION_DEATH("scorpion_death_1", "scorpion_death_2"),
		SCORPION_ROAM,
		LIGHT_STEP("light_step_1", "light_step_2", "light_step_3"),
		ATTACK_COLT,
		ATTACK_KNIFE,
		PICKUP,
		PLAYER_PAIN,
		PLAYER_DEATH,
		UI_CLICK(false),
		UI_ITEM_SELECT(false),
		UI_ITEM_PLACED(false),
		AMB_WIND(false, true);

		private final String filePath;
		private final boolean randomPitch;
		private final boolean loop;
		private final String[] files;

		Sounds( ) {
			this(true);
		}

		Sounds(final boolean randomPitch) {
			this(randomPitch, false);
		}

		Sounds(final boolean randomPitch, final boolean loop) {
			this(randomPitch, loop, new String[0]);
		}

		Sounds(final boolean randomPitch, final boolean loop, final String... files) {
			this.filePath = FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + FORMAT;
			this.randomPitch = randomPitch;
			this.loop = loop;
			this.files = files;
			IntStream.range(0, files.length).forEach(i -> files[i] = FOLDER + PATH_SEPARATOR + files[i] + "." + FORMAT);
		}

		Sounds(final String... files) {
			this(true, false, files);
		}

		@Override
		public String[] getFilesList( ) {
			return files;
		}

		@Override
		public AssetLoaderParameters<Sound> getParameters( ) {
			return null;
		}

		@Override
		public Class<Sound> getTypeClass( ) {
			return Sound.class;
		}
	}

	/**
	 * 3D models.
	 */
	@Getter
	public enum Models implements ModelDefinition {
		SARCOPHAG_1,
		POTTERY_1,
		STATUE_1,
		PILLAR_1,
		TORCH_1,
		COLT,
		CAVE_SUPPORTER_1(),
		CAVE_SUPPORTER_2(),
		CAVE_SUPPORTER_3(),
		CURSOR(0.1F),
		TORCH,
		SIGN,
		CAVE_WALL_SINGLE;

		private final String filePath;
		private final float alpha;
		private Color skipColor;

		Models( ) {
			this(1.0f);
		}

		Models(final float alpha) {
			this.filePath = ModelDefinition.FOLDER + PATH_SEPARATOR + name().toLowerCase() + "." + ModelDefinition.FORMAT;
			this.alpha = alpha;
		}

		Models(final String skipColor) {
			this();
			this.skipColor = Color.valueOf(skipColor);
		}

		@Override
		public AssetLoaderParameters<Model> getParameters( ) {
			return null;
		}

		@Override
		public Class<Model> getTypeClass( ) {
			return Model.class;
		}
	}

	@Getter
	public enum TexturesTypes {
		Floors(SurfaceTextures.values()),
		Environment(SurfaceTextures.values()),
		UI(UiTextures.values());

		private final TextureDefinition[] definitions;

		TexturesTypes(final TextureDefinition[] definitions) {
			this.definitions = definitions;
		}

		public static TextureDefinition[] getAllDefinitionsInSingleArray( ) {
			ArrayList<TextureDefinition> list = new ArrayList<>();
			Arrays.stream(values()).forEach(defs -> list.addAll(Arrays
					.stream(defs.getDefinitions())
					.collect(Collectors.toList()))
			);
			return list.toArray(new TextureDefinition[0]);
		}
	}

	/**
	 * Image files of surfaces.
	 */
	@Getter
	public enum SurfaceTextures implements TextureDefinition {
		MISSING,
		SAND_1_VER_1,
		SAND_1_VER_2,
		SAND_1_VER_3,
		ANTIQUE_WALL_1_SQUARE,
		ANTIQUE_WALL_1_TALL_1,
		ANTIQUE_WALL_1_TALL_2,
		ANTIQUE_WALL_1_TALL_3,
		ANTIQUE_WALL_1_TALL_BLOOD_1,
		ANTIQUE_WALL_1_TALL_BLOOD_2,
		ANTIQUE_WALL_1_TALL_BLOOD_3,
		ANTIQUE_WALL_1_TALL_GLYPH_1,
		ANTIQUE_WALL_1_TALL_GLYPH_2,
		ANTIQUE_WALL_1_TALL_GLYPH_3,
		SANDSTONE_BRICK_1,
		SANDSTONE_BRICK_2,
		SANDSTONE_BRICK_3,
		SANDSTONE_BRICK_4,
		SANDSTONE_BRICK_5,
		SANDSTONE_BRICK_6,
		SANDSTONE_BRICK_BLOOD_1,
		SANDSTONE_BRICK_BLOOD_2,
		SANDSTONE_BRICK_BLOOD_3,
		SANDSTONE_BRICK_GLYPH_1,
		SANDSTONE_BRICK_GLYPH_2,
		SANDSTONE_BRICK_GLYPH_3,
		SANDSTONE_BRICK_GLYPH_4,
		SANDSTONE_BRICK_GLYPH_5,
		BLANK,
		;

		private final Texture.TextureWrap textureWrap;

		SurfaceTextures(final Texture.TextureWrap textureWrap) {
			this.textureWrap = textureWrap;
		}

		SurfaceTextures( ) {
			this(Texture.TextureWrap.Repeat);
		}

		@Override
		public String getSubFolderName( ) {
			return "surfaces";
		}

		@Override
		public String getName( ) {
			return name();
		}

		@Override
		public AssetLoaderParameters<Texture> getParameters( ) {
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
		WEAPON_KNIFE(null, "weapons"),
		WEAPON_COLT(null, "weapons"),
		PLAYER_LAYOUT,
		SKILL_FLOWER_CENTER(null, "skill_flower"),
		SKILL_FLOWER_CENTER_IDLE(null, "skill_flower"),
		SKILL_FLOWER_1(null, "skill_flower"),
		SKILL_FLOWER_2(null, "skill_flower"),
		SKILL_FLOWER_3(null, "skill_flower"),
		SKILL_FLOWER_4(null, "skill_flower"),
		SKILL_FLOWER_5(null, "skill_flower"),
		SKILL_FLOWER_6(null, "skill_flower"),
		SKILL_FLOWER_7(null, "skill_flower"),
		SKILL_FLOWER_8(null, "skill_flower"),
		ICON_LOOKING_FOR(null, "skill_flower"),
		ICON_SPOTTED(null, "skill_flower");

		public static final String SUB_FOLDER_NAME = "ui";
		private final String specialFileName;
		private final String subSubFolder;

		UiTextures( ) {
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
		public String getSubFolderName( ) {
			return subSubFolder != null ? SUB_FOLDER_NAME + PATH_SEPARATOR + subSubFolder : SUB_FOLDER_NAME;
		}

		@Override
		public String getName( ) {
			return specialFileName != null ? specialFileName : name();
		}

		@Override
		public AssetLoaderParameters<Texture> getParameters( ) {
			return null;
		}
	}

}
