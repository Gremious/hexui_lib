package hexui_lib.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import hexui_lib.interfaces.CustomCardPortrait;

/*, paramtypez = { SpriteBatch.class, float.class, float.class }*/
@SpirePatch(clz = SingleCardViewPopup.class, method = "renderFrame")
public class RenderSingleCardPortraitFramePatch {
    @SpireInsertPatch(
            rloc=0,
            localvars={"card"}
    )
    public static SpireReturn Insert(SingleCardViewPopup view, final SpriteBatch sb, AbstractCard card) {
        if(card instanceof CustomCardPortrait){
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}

