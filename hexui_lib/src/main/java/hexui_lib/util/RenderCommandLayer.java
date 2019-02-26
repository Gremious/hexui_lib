package hexui_lib.util;

public class RenderCommandLayer extends RenderLayer {
    public enum COMMAND{
        FBO_START,
        FBO_END
    }
    public COMMAND command;

    public RenderCommandLayer(COMMAND command){
        this.command = command;
    }
}
