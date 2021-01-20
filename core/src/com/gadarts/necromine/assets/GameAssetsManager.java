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
import com.gadarts.necromine.Assets;
import com.gadarts.necromine.assets.loaders.ShaderLoader;
import com.gadarts.necromine.definitions.AtlasDefinition;
import com.gadarts.necromine.definitions.FontDefinition;
import com.gadarts.necromine.definitions.TextureDefinition;

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
					String path = Gdx.files.getFileHandle(assetsLocation + def.getFilePath(), FileType.Internal).path();
					Class<?> typeClass = def.getTypeClass();
					if (Optional.ofNullable(def.getParameters()).isPresent()) {
						load(path, typeClass, def.getParameters());
					} else {
						load(path, typeClass);
					}
				}));
		finishLoading();
	}

	@Override
	public <T> void addAsset(final String fileName, final Class<T> type, final T asset) {
		super.addAsset(fileName, type, asset);
		if (type == Model.class) {
			Model model = (Model) asset;
			model.materials.forEach(material -> material.remove(ColorAttribute.Specular));
		}
	}

	@Override
	public synchronized <T> T get(String fileName, Class<T> type) {
		return super.get(assetsLocation + fileName, type);
	}

	public TextureAtlas getAtlas(final AtlasDefinition atlas) {
		return get(atlas.getFilePath(), TextureAtlas.class);
	}

	public Model getModel(final com.gadarts.necromine.definitions.ModelDefinition model) {
		return get(model.getFilePath(), Model.class);
	}

	public Texture getTexture(final TextureDefinition definition) {
		return get(definition.getFilePath(), Texture.class);
	}

	public Music getMelody(final Assets.Melody definition) {
		return get(definition.getFilePath(), Music.class);
	}

	public Sound getSound(final Assets.Sounds sound) {
		return get(sound.getFilePath(), Sound.class);
	}

	public String getShader(final Assets.Shaders shaders) {
		return get(shaders.getFilePath(), String.class);
	}
}