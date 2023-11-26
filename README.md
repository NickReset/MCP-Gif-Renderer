# Description
This library allows you to render gifs within minecraft withouth having to manually split the gif into frames and render them yourself.

# Example
this is an example of how to use the library
```java
public class Example extends GuiScreen {

    private final GifRenderer gifRenderer;
    private float x, y;

    public GifScreen(String location) {
        this.gifRenderer = new GifRenderer(new ResourceLocation(location));
    }

    public GifScreen(String location, float scale) {
        this.gifRenderer = new GifRenderer(new ResourceLocation(location));
        this.gifRenderer.scale(scale);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.gifRenderer.render(this.x, this.y, this.gifRenderer.getWidth() this.gifRenderer.getHeight());
    }

    @Override
    public void updateScreen() {
        this.gifRenderer.update();
    }

    @Override
    public void onGuiClosed() {
        this.gifRenderer.deleteTexture();
    }
}
```
You can check another example [here](https://github.com/NickReset/MCP-Gif-Renderer/blob/main/src/main/java/example/Example.jav)
