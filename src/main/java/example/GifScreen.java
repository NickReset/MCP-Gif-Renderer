package social.nickrest.gif.example;

import lombok.Getter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import social.nickrest.gif.renderer.GifRenderer;

import java.io.IOException;

@Getter
public class GifScreen extends GuiScreen {

    private final GifRenderer gifRenderer;

    private float x, y, mouseX, mouseY;
    private int width, height;

    private boolean dragging;

    public GifScreen() {
        this.gifRenderer = new GifRenderer(new ResourceLocation("test/test.gif"));
        this.width = this.gifRenderer.getWidth();
        this.height = this.gifRenderer.getHeight();
    }

    public GifScreen(float scale) {
        this.gifRenderer = new GifRenderer(new ResourceLocation("test/test.gif"));
        this.gifRenderer.scale(scale);

        this.width = this.gifRenderer.getWidth();
        this.height = this.gifRenderer.getHeight();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.gifRenderer.render(this.x, this.y, this.width, this.height);

        if(dragging) {
            this.x = mouseX - this.mouseX;
            this.y = mouseY - this.mouseY;
        }

        this.width = this.gifRenderer.getWidth();
        this.height = this.gifRenderer.getHeight();
    }

    @Override
    public void updateScreen() {
        this.gifRenderer.update();
    }

    @Override
    public void onGuiClosed() {
        this.gifRenderer.deleteTexture();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height)) {
            this.mouseX = mouseX - this.x;
            this.mouseY = mouseY - this.y;
            this.dragging = true;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public boolean isHovered(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
