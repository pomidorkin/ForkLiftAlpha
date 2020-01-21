package com.mygdx.forkliftaone.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class AssetDescriptors {

    private AssetDescriptors(){}

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

//    public static final AssetDescriptor<TextureAtlas> TEST_ATLAS =
//            new AssetDescriptor<TextureAtlas>(AssetPaths.FORKLIFT_PACK, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> TEST_ATLAS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.CUSTOM_PACK, TextureAtlas.class);

    public static final AssetDescriptor<TiledMap> TEST_MAP =
            new AssetDescriptor<TiledMap>(AssetPaths.TEST_TILED_MAP, TiledMap.class);

    public static final AssetDescriptor<TiledMap> CUSTOM_MAP =
            new AssetDescriptor<TiledMap>(AssetPaths.CUSTOM_TILED_MAP, TiledMap.class);

    public static final AssetDescriptor<Music> TEST_MUSIC =
            new AssetDescriptor<Music>(AssetPaths.TEST_MUSIC, Music.class);

    public static final AssetDescriptor<Sound> TEST_ENGINE =
            new AssetDescriptor<Sound>(AssetPaths.TEST_ENGINE_SOUND, Sound.class);

}
