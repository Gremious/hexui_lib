package hexui_lib.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.Settings;
import hexui_lib.CardRenderer;
import hexui_lib.interfaces.CustomCardPortrait;
import hexui_lib.util.RenderLayer;

import static hexui_lib.HexUILib.logger;


@SpirePatch(clz = AbstractCard.class, method = "renderPortrait")
public class RenderCardPortraitPatch {
    @SpireInsertPatch(
            rloc=0,
            localvars={"renderColor"}
    )
    public static SpireReturn Insert(AbstractCard card, SpriteBatch sb, Color renderColor) {
        if(card instanceof CustomCardPortrait){

            renderPortrait(card, sb, renderColor);
            return SpireReturn.Return(null);

        }
        return SpireReturn.Continue();
    }

    private static void renderPortrait(AbstractCard card, SpriteBatch sb, Color renderColor) {
        CustomCardPortrait customPortraitCard = (CustomCardPortrait)card;

        float drawX = card.current_x - 256.0F;
        float drawY = card.current_y - 256.0F;

        if (!card.isLocked){
            sb.setColor(renderColor);
            for(RenderLayer renderLayer : customPortraitCard.getPortraitLayers512()){
                CardRenderer.renderHelper(card, sb, /*renderColor,*/ renderLayer, drawX, drawY, false);
            }
        }else{
            //Maybe check for custom locked image here?
            for(RenderLayer renderLayer : customPortraitCard.getPortraitLayers512()){
                CardRenderer.renderHelper(card, sb, /*renderColor,*/ renderLayer, drawX, drawY, false);
            }
        }
    }
}

