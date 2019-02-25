package hexui_lib;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hexui_lib.interfaces.HexUICard;
import hexui_lib.util.RenderLayer;

import static hexui_lib.HexUILib.logger;

public class CardRenderer {

    public static void renderHelper(AbstractCard card, SpriteBatch sb, Color color, Texture img, float drawX, float drawY){
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


    public static void renderHelper(AbstractCard card, SpriteBatch sb, RenderLayer renderLayer, float drawX, float drawY, boolean isBigCard){
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

            //if(renderLayer.displacement.x != 0 || renderLayer.displacement.y != 0){
                double theta = Math.atan2(renderLayer.displacement.y, renderLayer.displacement.x);
                double distance = Math.sqrt(Math.pow(renderLayer.displacement.x, 2) + Math.pow(renderLayer.displacement.y, 2));
                theta += Math.PI/180*card.angle;
                dispX += Math.cos(theta)*distance;
                dispY += Math.sin(theta)*distance;
            //}

            sb.draw(renderLayer.texture,
                    drawX+dispX*scale, drawY+dispY*scale,
                    originX, originY,
                    texture.getWidth(), texture.getHeight(),
                    scale, scale,
                    card.angle+renderLayer.angle, 0, 0,
                    texture.getWidth(), texture.getHeight(),
                    false, false);

            /*

            if(isBigCard) {
                sb.draw(renderLayer.texture,
                        drawX, drawY,
                        originX, originY,
                        texture.getWidth(), texture.getHeight(),
                        Settings.scale, Settings.scale,
                        card.angle, 0, 0,
                        texture.getWidth(), texture.getHeight(),
                        false, false);
            }else{
                sb.draw(renderLayer.texture,
                        drawX, drawY,
                        256.0F, 256.0F,
                        512.0F, 512.0F,
                        card.drawScale * Settings.scale, card.drawScale * Settings.scale,
                        card.angle, 0, 0,
                        512, 512,
                        false, false);
            }*/

            sb.setColor(Color.WHITE.cpy());
            setBlending(sb, RenderLayer.BLENDMODE.NORMAL);
        }
        catch (Exception e)
        {
            ExceptionHandler.handleException(e, logger);
        }
    }

    private static void setBlending(SpriteBatch sb, RenderLayer.BLENDMODE blendMode) {
        switch(blendMode){
            case NORMAL: sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); break;
            case SCREEN: sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE); break;
            case MULTIPLY: sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE); break;
        }
    }
}
