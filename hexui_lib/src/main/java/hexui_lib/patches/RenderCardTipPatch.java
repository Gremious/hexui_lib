package hexui_lib.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import hexui_lib.CardRenderer;
import hexui_lib.interfaces.CustomCardPortrait;
import hexui_lib.util.FlavorTips;
import hexui_lib.util.RenderLayer;


@SpirePatch(clz = AbstractCard.class, method = "renderCardTip")
public class RenderCardTipPatch {
    @SpireInsertPatch(
            rloc=0,
            localvars={"renderTip"}
    )
    public static void Insert(AbstractCard card, SpriteBatch sb, boolean renderTip) {
        if (!Settings.hideCards && renderTip){
            FlavorTips.render(sb, card, false);
        }
    }
}

