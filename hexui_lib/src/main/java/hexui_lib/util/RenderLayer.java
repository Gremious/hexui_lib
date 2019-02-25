package hexui_lib.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.Optional;

public class RenderLayer {
    public Texture texture;
    public Color color;
    public BLENDMODE blendMode;
    public FloatPair displacement;
    public float angle;

    public enum BLENDMODE {
        NORMAL,
        SCREEN,
        MULTIPLY
    }

    public RenderLayer(Texture texture){
        this(texture, null, null, null, 0f);
    }

    public RenderLayer(Texture texture, Color color){
        this(texture, color, null, null, 0f);
    }

    public RenderLayer(Texture texture, Color color, BLENDMODE blendMode){
        this(texture, color, blendMode, null, 0f);
    }

    public RenderLayer(Texture texture, Color color, BLENDMODE blendMode, FloatPair displacement){
        this(texture, color, blendMode, displacement, 0f);
    }

    public RenderLayer(Texture texture, Color color, BLENDMODE blendMode, FloatPair displacement, float angle) {
        this.texture        = texture;
        this.color          = color != null           ? color         : Color.WHITE.cpy();
        this.blendMode      = blendMode != null       ? blendMode     : BLENDMODE.NORMAL;
        this.displacement   = displacement != null    ? displacement  : new FloatPair(0f, 0f);
        this.angle          = angle;
    }
}