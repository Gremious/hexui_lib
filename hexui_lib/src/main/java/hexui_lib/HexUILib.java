package hexui_lib;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import hexui_lib.util.TextureLoader;
import hexui_lib.variables.DefaultCustomVariable;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class HexUILib implements
        PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(HexUILib.class.getName());
    private static String modID;
    
    private static final String MODNAME = "HexUI Lib";
    private static final String AUTHOR = "Hexblue"; // And pretty soon - You!
    private static final String DESCRIPTION = "Hexblue's UI Library";
    
    public static final String BADGE_IMAGE = "images/Badge.png";


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public HexUILib() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);
        setModID("hexui_lib");

        logger.info("Done subscribing");

        logger.info("Done creating the color");
    }


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        HexUILib defaultmod = new HexUILib();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }


    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================

    
    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("HexUILib doesn't have any settings! An example of those may come later.", 400.0f, 700.0f,
                settingsPanel, (me) -> {
        }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) {
        modID = ID;
    }
    
    public static String getModID() {
        return modID;
    }
    
    private static void pathCheck() {
        String packageName = HexUILib.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals("theDefaultDev")) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException("Rename your hexui_lib folder to match your mod ID! " + getModID());
            }
            if (!resourcePathExists.exists()) {
                throw new RuntimeException("Rename your magineerResources folder to match your mod ID! " + getModID() + "Resources");
            }
        }
    }
    
    // ====== YOU CAN EDIT AGAIN ======

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

}
