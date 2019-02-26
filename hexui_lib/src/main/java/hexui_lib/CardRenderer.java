package hexui_lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.Settings;
import hexui_lib.interfaces.CustomCardPortrait;
import hexui_lib.util.RenderCommandLayer;
import hexui_lib.util.RenderImageLayer;
import hexui_lib.util.RenderLayer;

import java.util.ArrayList;

import static hexui_lib.HexUILib.logger;

public class CardRenderer {
    private static ArrayList<FrameBuffer> fboStack = new ArrayList<FrameBuffer>();
    private static FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Settings.WIDTH, Settings.HEIGHT, false, false);

    public static void renderImageHelper(AbstractCard card, SpriteBatch sb, Color color, Texture img, float drawX, float drawY){
        sb.setColor(color);
        try
        {
            sb.draw(img, drawX, drawY, 256.0F, 256.0F, 512.0F, 512.0F, card.drawScale * Settings.scale, card.drawScale * Settings.scale, card.angle, 0, 0, 512, 512, false, false);
        }
        catch (Exception e)
        {
            ExceptionHandler.handleException(e, logger);
        }
    }


    public static void renderPortrait(SpriteBatch sb, AbstractCard card, float drawX, float drawY, boolean isBigCard) {
        CustomCardPortrait customPortraitCard = (CustomCardPortrait)card;
        
        if (card.isLocked) {
            //Maybe check for custom locked image here?
        }

        for(RenderLayer renderLayer : (isBigCard ? customPortraitCard.getPortraitLayers1024() : customPortraitCard.getPortraitLayers512())){
            if(renderLayer instanceof RenderImageLayer) {
                renderImageHelper(card, sb, (RenderImageLayer)renderLayer, drawX, drawY, false);
            }else if(renderLayer instanceof RenderCommandLayer){
                renderCommandHelper(sb, (RenderCommandLayer)renderLayer);
            }
        }
    }

    private static void renderCommandHelper(SpriteBatch sb, RenderCommandLayer renderLayer) {
        switch(renderLayer.command){
            case FBO_START:
                sb.end();
                fbo.begin();
                Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                Gdx.gl.glColorMask(true, true, true, true);
                sb.begin();
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(-1, -1);//disable spritebatch blending override
                Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                break;
            case FBO_END:
                sb.end();
                fbo.end();
                sb.begin();
                TextureRegion drawTex = new TextureRegion(fbo.getColorBufferTexture());
                drawTex.flip(false, true);
                sb.draw(drawTex, 0.0F, 0.0F);
                break;
        }
    }

    public static void renderImageHelper(AbstractCard card, SpriteBatch sb, RenderImageLayer renderLayer, float drawX, float drawY, boolean isBigCard){
        try
        {
            Texture texture = renderLayer.texture;

            float scale = Settings.scale;
            float centerX = 512;
            float centerY = 512;
            if(!isBigCard){
                scale *= card.drawScale;
                centerX = 256;
                centerY = 256;
            }

            float originX = texture.getWidth()/2;
            float originY = texture.getHeight()/2;

            float dispX = renderLayer.displacement.x;
            float dispY = renderLayer.displacement.y;

            dispX += centerX;
            dispY += centerY;

            dispX -= originX;
            dispY -= originY;



            sb.setColor(renderLayer.color);
            setBlending(sb, renderLayer.blendMode);
            setColorMask(renderLayer.colormask);

            double theta = Math.atan2(renderLayer.displacement.y, renderLayer.displacement.x);
            double distance = Math.sqrt(Math.pow(renderLayer.displacement.x, 2) + Math.pow(renderLayer.displacement.y, 2));
            theta += Math.PI/180*card.angle;
            dispX += Math.cos(theta)*distance;
            dispY += Math.sin(theta)*distance;

            sb.draw(renderLayer.texture,
                    drawX+dispX*scale, drawY+dispY*scale,
                    originX, originY,
                    texture.getWidth(), texture.getHeight(),
                    scale, scale,
                    card.angle+renderLayer.angle, 0, 0,
                    texture.getWidth(), texture.getHeight(),
                    false, false);



            //sb.setColor(Color.WHITE.cpy());
            //setBlending(sb, RenderLayer.BLENDMODE.NORMAL);
        }
        catch (Exception e)
        {
            ExceptionHandler.handleException(e, logger);
        }
    }

    private static void setColorMask(boolean[] colormask){
        if(colormask == null) return;
        Gdx.gl.glColorMask(colormask[0], colormask[1], colormask[2], colormask[3]);
    }

    private static void setBlending(SpriteBatch sb, RenderLayer.BLENDMODE blendMode) {
        if(blendMode == null) return;
        switch(blendMode){
            case NORMAL:
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                break;
            case SCREEN:
                sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
                break;
            case MULTIPLY:
                sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
                break;
            case CREATEMASK:
                sb.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_ALPHA);
                break;
            case RECEIVEMASK:
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                break;
        }
    }
}
