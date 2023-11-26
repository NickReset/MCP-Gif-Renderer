package social.nickrest.gif.util.image;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

@UtilityClass
public class TextureUtil {

    public static int loadTextureFromBufferedImage(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();

        int textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);
        {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        }
        glBindTexture(GL_TEXTURE_2D, 0);
        return textureID;
    }

    public static void drawTexture(float x, float y, float width, float height, int textureID) {
        GlStateManager.enableTexture2D();

        glBindTexture(GL_TEXTURE_2D, textureID);
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(x, y);
            glTexCoord2f(0, 1);
            glVertex2f(x, y + height);
            glTexCoord2f(1, 1);
            glVertex2f(x + width, y + height);
            glTexCoord2f(1, 0);
            glVertex2f(x + width, y);
        }
        glEnd();

        GlStateManager.bindTexture(textureID);
    }


    public static BufferedImage scale(BufferedImage image, float scale) {
        int width = (int) (image.getWidth() * scale);
        int height = (int) (image.getHeight() * scale);

        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        scaledImage.getGraphics().drawImage(image, 0, 0, width, height, null);

        return scaledImage;
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        for(int w = 0; w < image.getWidth(); w++) {
            for(int h = 0; h < image.getHeight(); h++) {
                if(w > width || h > height) {
                    image.setRGB(w, h, 0);
                }
            }
        }

        return image;
    }

    public static void deleteTexture(int textureID) {
        glDeleteTextures(textureID);
    }

}