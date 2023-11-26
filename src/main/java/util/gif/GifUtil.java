package social.nickrest.gif.util.gif;

import com.madgag.gif.fmsware.GifDecoder;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

@UtilityClass
public class GifUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final IResourceManager resourceManager = mc.getResourceManager();

    public static ArrayList<ImageFrame> decodeGif(InputStream stream) {
        GifDecoder decoder = new GifDecoder();
        decoder.read(stream);

        ImageFrame[] frames = new ImageFrame[decoder.getFrameCount()];
        for(int i = 0; i < decoder.getFrameCount(); i++) {
            frames[i] = new ImageFrame(decoder.getFrame(i), i, decoder.getDelay(i));
        }

        return new ArrayList<>(Arrays.asList(frames));
    }

    public static ArrayList<ImageFrame> decodeGif(ResourceLocation location) {
        try {
            return decodeGif(resourceManager.getResource(location).getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static void debug(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(String.format("§7[§bDEBUG§7] %s", message)));
    }

}
