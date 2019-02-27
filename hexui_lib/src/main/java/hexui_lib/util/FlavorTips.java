package hexui_lib.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.PowerTip;
import hexui_lib.HexUILib;
import hexui_lib.interfaces.FlavorTooltips;

import java.util.ArrayList;

public class FlavorTips {
    public static void render(SpriteBatch sb, AbstractCard card, boolean isSingleCardView){
        if(card instanceof FlavorTooltips) {
            FlavorTooltips fCard = (FlavorTooltips) card;

            String[] TEXT = {
                    "Flavor Text",
                    "Artist Attribution",
                    "Card Art By:"
            };

            ArrayList<PowerTip> tips = new ArrayList();

            String artistAttributionBody = TEXT[2];
            for(String artistName : fCard.getArtistNames()){
                artistAttributionBody += " NL " + artistName;
            }

            tips.add(new PowerTip(TEXT[1], artistAttributionBody));
            tips.add(new PowerTip(TEXT[0], '"' + fCard.getFlavorText() + '"'));

            if (!tips.isEmpty()) {
                if(isSingleCardView) {
                    SecondTipHelper.renderPowerTips(300.0F * Settings.scale, 440.0F * Settings.scale, sb, tips);
                }else{
                    float CARD_TIP_PAD = 12.0F * Settings.scale;
                    float BOX_EDGE_H = 32.0F * Settings.scale;
                    float BOX_W = 320.0F * Settings.scale;
                    float tooltipX_left         = card.current_x - AbstractCard.IMG_WIDTH / 2.0F - CARD_TIP_PAD - BOX_W;
                    float tooltipX_farLeft      = tooltipX_left - CARD_TIP_PAD - BOX_W;
                    float tooltipX_farRight     = card.current_x + AbstractCard.IMG_WIDTH / 2.0F + CARD_TIP_PAD + BOX_W;
                    float tooltipY = card.current_y + AbstractCard.IMG_HEIGHT / 2.0F - BOX_EDGE_H;
                    if (card.current_x < Settings.WIDTH * 0.33F) {
                        //Ok, we're really far left. We need to render flavor tips on the far right side.
                        SecondTipHelper.renderPowerTips(tooltipX_farRight, tooltipY, sb, tips);
                    }else if (card.current_x <= Settings.WIDTH * 0.75F) {
                        SecondTipHelper.renderPowerTips(tooltipX_left, tooltipY, sb, tips);
                    }else{
                        //Card is really far right. Normal tooltips will be rendered on the left side.
                        //So we'll render the flavor tips even further left.
                        SecondTipHelper.renderPowerTips(tooltipX_farLeft, tooltipY, sb, tips);
                    }
                }
            }
        }
    }
}
