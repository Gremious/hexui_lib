package hexui_lib.interfaces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hexui_lib.util.RenderLayer;

import java.util.ArrayList;

public interface CustomCardPortrait {
    ArrayList<RenderLayer> getPortraitLayers512();
    ArrayList<RenderLayer> getPortraitLayers1024();
}
