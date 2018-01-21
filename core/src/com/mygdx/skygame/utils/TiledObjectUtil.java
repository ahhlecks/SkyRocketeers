package com.mygdx.skygame.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.mygdx.skygame.SkyRocketeers;
import com.mygdx.skygame.managers.Box2dObjectManager;
import com.mygdx.skygame.objects.ChainBody;
import com.mygdx.skygame.objects.Player;
import com.mygdx.skygame.objects.Spawn;

import static com.mygdx.skygame.utils.Constants.PPM;

/**
 * Created by alexa on 3/9/2017.
 */
public class TiledObjectUtil {

    public SkyRocketeers game;
    private TiledMap map;

    public static void parseTiledObjectLayer(TiledMap map, String layer, Box2dObjectManager objectManager) {

        MapProperties properties = map.getProperties();
        MapLayer tiledLayer = map.getLayers().get(layer);
        MapObjects objects = tiledLayer.getObjects();

        int width = properties.get("width", Integer.class);
        int height = properties.get("height", Integer.class);
        int tileWidth = properties.get("tilewidth", Integer.class);
        float tileHeight = properties.get("tileheight", Integer.class);

        for(MapObject object : objects) {
            if(object instanceof PolylineMapObject) {
                ChainShape shape;
                String material = object.getName();
                shape = createPolyLine((PolylineMapObject) object);
                objectManager.add(new ChainBody(shape,material));
                shape.dispose();
            }
            if(object instanceof EllipseMapObject) {
                if(object.getName().equals("spawn")) {
                    float x = object.getProperties().get("x",Float.class);
                    float y = object.getProperties().get("y",Float.class);
                    objectManager.add(new Spawn(x,y));
                }
                if(object.getName().equals("player")) {
                    float x = object.getProperties().get("x",Float.class);
                    float y = object.getProperties().get("y",Float.class);
                    float radius = object.getProperties().get("radius",Float.class);
                    objectManager.add(new Player(x,y,radius));
                }
            }
        }
    }
    private static ChainShape createPolyLine(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices(); // x1,y1,x2,y2...
        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices); //worldVertices
        return cs;
    }
}
