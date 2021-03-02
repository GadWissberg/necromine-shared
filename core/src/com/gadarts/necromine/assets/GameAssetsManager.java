package com.gadarts.necromine.assets;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.utils.Array;
import com.gadarts.necromine.assets.definitions.AtlasDefinition;
import com.gadarts.necromine.assets.definitions.FontDefinition;
import com.gadarts.necromine.assets.definitions.TextureDefinition;
import com.gadarts.necromine.assets.loaders.ShaderLoader;

import java.util.Arrays;
import java.util.Optional;

/**
 * Assets loader and manager.
 */
public class GameAssetsManager extends AssetManager {
	private final String assetsLocation;

	public GameAssetsManager() {
		this("");
	}

	public GameAssetsManager(final String assetsLocation) {
		this.assetsLocation = assetsLocation;
		setLoader(String.class, new ShaderLoader(getFileHandleResolver()));
		FileHandleResolver resolver = new InternalFileHandleResolver();
		setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		FreetypeFontLoader loader = new FreetypeFontLoader(resolver);
		setLoader(BitmapFont.class, FontDefinition.FORMAT, loader);
	}

	/**
	 * Loads all defined assets and inflating animations.
	 */
	public void loadGameFiles(final Assets.AssetsTypes... assetsTypesToExclude) {
		Arrays.stream(Assets.AssetsTypes.values())
				.filter(type -> Arrays.stream(assetsTypesToExclude).noneMatch(toExclude -> toExclude == type))
				.forEach(type -> Arrays.stream(type.getAssetDefinitions()).forEach(def -> {
					String filePath = assetsLocation + def.getFilePath();
					String path = Gdx.files.getFileHandle(filePath, FileType.Internal).path();
					Class<?> typeClass = def.getTypeClass();
					if (Optional.ofNullable(def.getParameters()).isPresent()) {
						String assetManagerKey = def.getAssetManagerKey();
						load(assetManagerKey != null ? assetManagerKey : path, typeClass, def.getParameters());
					} else {
						load(path, typeClass);
					}
				}));
		finishLoading();
	}

	/**
	 * Sets repeat value wrap for all loaded textures.
	 */
	public void applyRepeatWrapOnAllTextures() {
		Array<Texture> textures = new Array<>();
		getAll(Texture.class, textures);
		textures.forEach(texture -> texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat));
	}

	@Override
	public <T> void addAsset(final String fileName, final Class<T> type, final T asset) {
		super.addAsset(fileName, type, asset);
		if (type == Model.class) {
			Model model = (Model) asset;
			model.materials.forEach(material -> material.remove(ColorAttribute.Specular));
		}
	}

	public TextureAtlas getAtlas(final AtlasDefinition atlas) {
		return get(assetsLocation + atlas.getFilePath(), TextureAtlas.class);
	}

	public Model getModel(final com.gadarts.necromine.assets.definitions.ModelDefinition model) {
		return get(assetsLocation + model.getFilePath(), Model.class);
	}

	public Texture getTexture(final TextureDefinition definition) {
		return get(assetsLocation + definition.getFilePath(), Texture.class);
	}

	public Music getMelody(final Assets.Melody definition) {
		return get(assetsLocation + definition.getFilePath(), Music.class);
	}

	public Sound getSound(final Assets.Sounds sound) {
		return get(assetsLocation + sound.getFilePath(), Sound.class);
	}

	public String getShader(final Assets.Shaders shaders) {
		return get(assetsLocation + shaders.getFilePath(), String.class);
	}
}