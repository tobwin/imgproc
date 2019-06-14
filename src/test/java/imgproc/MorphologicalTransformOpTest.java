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
            ImageIO.write(img, "png", new File("./Image.png"));
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

        // START Readme code
        BufferedImage src = null;
        try {
            src = ImageIO.read(new File("./Image.png"));
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
        // END Readme code

        try {
            ImageIO.write(dest, "png", new File("./MORPH_CLOSE.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }


        width = 5;
        height = 3;
        // START more Readme code
        StructuringElement se;

        // Rectangular element
        se = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, width, height);

        // Elliptical element
        se = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, width, height);
        // END more Readme code

    }



    @Test
    public void testErodeEllipse() {

        BufferedImage src = null;
        try {
            src = ImageIO.read(new File("./Image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        int width = 5, height = 3;
        StructuringElement structElement = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, width, height);

        BufferedImageOp bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_ERODE);

        bio.filter(src, dest);

        try {
            ImageIO.write(dest, "png", new File("./MORPH_ERODE.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testExtendBorder() {

        BufferedImage img = new BufferedImage(9, 5, BufferedImage.TYPE_BYTE_GRAY);

        for(int i = 0; i < 9; i++) {
            img.getRaster().setSample(i, 0, 0, 255);
            img.getRaster().setSample(i, 1, 0, 255);
            img.getRaster().setSample(i, 3, 0, 255);
            img.getRaster().setSample(i, 4, 0, 255);
        }
        img.getRaster().setSample(0, 2, 0, 100);
        img.getRaster().setSample(1, 2, 0, 0);
        img.getRaster().setSample(2, 2, 0, 255);
        img.getRaster().setSample(3, 2, 0, 150);
        img.getRaster().setSample(4, 2, 0, 255);
        img.getRaster().setSample(5, 2, 0, 150);
        img.getRaster().setSample(6, 2, 0, 255);
        img.getRaster().setSample(7, 2, 0, 0);
        img.getRaster().setSample(8, 2, 0, 100);

        img.getRaster().setSample(4, 1, 0, 100);
        img.getRaster().setSample(4, 3, 0, 100);

        try {
            ImageIO.write(img, "png", new File("./BorderTestOrig.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        int width = 7, height = 5;
        StructuringElement structElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, width, height);
        MorphologicalTransformOp op = new MorphologicalTransformOp(structElement , Imgproc.MORPH_ERODE);

        img = Imgproc.extendBorder(img, structElement.getXOrigin(), structElement.getYOrigin());
        try {
            ImageIO.write(img, "png", new File("./BorderTestExt.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test() {

    }

}
