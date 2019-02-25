package hexui_lib.interfaces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface HexUICard {
    boolean hasCardBgOverwrite();

    Texture getBgTexture();

    boolean hasCustomCardBgRendering();

    void customCardBgRendering(SpriteBatch sb, float x, float y, Color renderColor);

    boolean hasCustomFrameRendering();

    void customCardFrameRendering(SpriteBatch sb, float x, float y, Color renderColor);

    boolean hasCustomBannerRendering();

    void customCardBannerRendering(SpriteBatch sb, float x, float y, Color renderColor);

    boolean hasCustomBg();

    Texture getCustomBg();
}
