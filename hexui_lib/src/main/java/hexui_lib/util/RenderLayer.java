package hexui_lib.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class RenderLayer {
    public static final boolean[] CM_FULL = {true, true, true, true};
    public static final boolean[] CM_ALPHA = {false, false, false, true};

    public enum BLENDMODE {
        NORMAL,
        SCREEN,
        LINEAR_DODGE,
        MULTIPLY,
        CREATEMASK,
        RECEIVEMASK
    }

}