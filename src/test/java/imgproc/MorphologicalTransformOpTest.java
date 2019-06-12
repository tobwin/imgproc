package imgproc;

import org.junit.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MorphologicalTransformOpTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void makeImage() {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);

        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                if(i <= 75 & i >= 25 & j <= 90 & j >= 10) {
                    img.getRaster().setSample(i, j, 0, 0); // black
                } else if(j <= 75 & j >= 25 & i <= 90 & i >= 10) {
                    img.getRaster().setSample(i, j, 0, 127); // gray
                } else {
                    img.getRaster().setSample(i, j, 0, 255); // white
                }
            }
        }

        try {
            ImageIO.write(img, "png", new File("./Image.jpg"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testLegalConstruction() {
        boolean data[] = {true, true, true, true, true, true, true, true, true};
        StructuringElement strel = new StructuringElement(3, 3, data);
        MorphologicalTransformOp op = new MorphologicalTransformOp(strel, Imgproc.MORPH_CLOSE);
    }

    @Test
    public void testReadme() {

        BufferedImage src = null;
        try {
            src = ImageIO.read(new File("./Image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        int width = 3, height = 3;
        int[] data = {  0, 1, 0,
                        1, 1, 1,
                        0, 1, 0};

        StructuringElement structElement = new StructuringElement(width, height, data);
        BufferedImageOp bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_CLOSE);

        bio.filter(src, dest);

        try {
            ImageIO.write(dest, "jpg", new File("./MORPH_CLOSE.jpg"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test() {

    }


}
