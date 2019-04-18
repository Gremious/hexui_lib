package hexui_lib;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import hexui_lib.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class HexUILib implements PostInitializeSubscriber {
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
    }
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= ⬡ Initializing Hexblue's UI Library! ⬡ =========================");
        HexUILib hexuilib = new HexUILib();
        logger.info("========================= / ⬢ Hexblue's UI Library is Initialized! ⬢ / =========================");
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
    
    // =============== /POST-INITIALIZE/ =================
    
    
    // =============== ID =================
    
    public static String getModID() {
        return modID;
    }
    
    public static void setModID(String ID) {
        modID = ID;
    }
    
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
    
    // =============== /ID/ =================
}
