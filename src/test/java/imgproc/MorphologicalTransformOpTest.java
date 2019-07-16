package imgproc;

import org.junit.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.rules.ExpectedException;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MorphologicalTransformOpTest {

    BufferedImage src;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void readImage() {
        try {
            src = ImageIO.read(new File("./src/test/resources/orig.png"));
        } catch (IOException e) {
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
    public void testEllipseOdd() {

        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        BufferedImageOp bio;
        BufferedImage target;

        int width = 5, height = 3;
        StructuringElement structElement = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, width, height);

        bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_ERODE);
        bio.filter(src, dest);
        try {
            target = ImageIO.read(new File("./src/test/resources/odd/erode.png"));
            Assert.assertTrue(compareImages(target, dest));
        } catch(IOException e) {
            e.printStackTrace();
        }

        bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_DILATE);
        bio.filter(src, dest);
        try {
            target = ImageIO.read(new File("./src/test/resources/odd/dilate.png"));
            Assert.assertTrue(compareImages(target, dest));
        } catch(IOException e) {
            e.printStackTrace();
        }

        bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_OPEN);
        bio.filter(src, dest);
        try {
            target = ImageIO.read(new File("./src/test/resources/odd/open.png"));
            Assert.assertTrue(compareImages(target, dest));
        } catch(IOException e) {
            e.printStackTrace();
        }

        bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_CLOSE);
        bio.filter(src, dest);
        try {
            target = ImageIO.read(new File("./src/test/resources/odd/close.png"));
            Assert.assertTrue(compareImages(target, dest));
        } catch(IOException e) {
            e.printStackTrace();
        }

    }


//    @Test
//    public void testEllipseEven() {
//
//        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
//        BufferedImageOp bio;
//        BufferedImage target;
//
//        int width = 2, height = 4;
//        StructuringElement structElement = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, width, height);
//
//        bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_ERODE);
//        bio.filter(src, dest);
//        try {
//            target = ImageIO.read(new File("./src/test/resources/even/erode.png"));
////            ImageIO.write(dest, "png", new File("./erode.png"));
//            Assert.assertTrue(compareImages(target, dest));
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//
//        bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_DILATE);
//        bio.filter(src, dest);
//        try {
//            target = ImageIO.read(new File("./src/test/resources/even/dilate.png"));
////            ImageIO.write(dest, "png", new File("./dilate.png"));
//            Assert.assertTrue(compareImages(target, dest));
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//
//        bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_OPEN);
//        bio.filter(src, dest);
//        try {
//            target = ImageIO.read(new File("./src/test/resources/even/open.png"));
//            Assert.assertTrue(compareImages(target, dest));
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//
//        bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_CLOSE);
//        bio.filter(src, dest);
//        try {
//            target = ImageIO.read(new File("./src/test/resources/even/close.png"));
//            Assert.assertTrue(compareImages(target, dest));
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//
//    }



    static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The images must be the same size.
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        int width  = imgA.getWidth();
        int height = imgA.getHeight();

        // Loop over every pixel.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

}

