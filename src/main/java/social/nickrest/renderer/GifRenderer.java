package social.nickrest.gif.renderer;

import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import social.nickrest.gif.util.gif.GifUtil;
import social.nickrest.gif.util.gif.ImageFrame;
import social.nickrest.gif.util.image.TextureUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GifRenderer {

    private final ResourceLocation location;

    private final ArrayList<ImageFrame> frames;

    private int index, textureId;
    private long lastUpdate;

    public GifRenderer(ResourceLocation location) {
        this.location = location;

        this.frames = GifUtil.decodeGif(location);
        this.textureId = TextureUtil.loadTextureFromBufferedImage(frames.get(index).getImage());
        this.lastUpdate = System.currentTimeMillis();
    }

    public GifRenderer(InputStream stream) {
        this.location = null;
        this.frames = GifUtil.decodeGif(stream);

        this.textureId = TextureUtil.loadTextureFromBufferedImage(frames.get(index).getImage());
        this.lastUpdate = System.currentTimeMillis();
    }

    public void render(float x, float y, int width, int height) {
        TextureUtil.drawTexture(x, y, width, height, textureId);
    }

    public void update() {
        if(!(System.currentTimeMillis() - lastUpdate >= frames.get(index).getDelay())) return;

        this.index++;

        if(this.index >= this.frames.size()) this.index = 0;

        TextureUtil.deleteTexture(this.textureId);
        this.textureId = TextureUtil.loadTextureFromBufferedImage(frames.get(index).getImage());
        this.lastUpdate = System.currentTimeMillis();
    }

    public void scale(float scale) {
        List<ImageFrame> frames = new ArrayList<>();

        for (ImageFrame frame : this.frames) {
            frames.add(new ImageFrame(TextureUtil.scale(frame.getImage(), scale), frame.getIndex(), frame.getDelay()));
        }

        this.frames.clear();
        this.frames.addAll(frames);
    }

    public void setGifSize(int width, int height) {
        List<ImageFrame> frames = new ArrayList<>();

        for (ImageFrame frame : this.frames) {
            frames.add(new ImageFrame(TextureUtil.resize(frame.getImage(), width, height), frame.getIndex(), frame.getDelay()));
        }

        this.frames.clear();
        this.frames.addAll(frames);
    }

    public int getWidth() {
        return this.frames.get(this.index).getImage().getWidth();
    }

    public int getHeight() {
        return this.frames.get(this.index).getImage().getHeight();
    }

    public void deleteTexture() {
        TextureUtil.deleteTexture(this.textureId);
    }

}
