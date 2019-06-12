package imgproc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MorphologicalTransformOpTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
            src = ImageIO.read(new File("./src/test/resources/imgproc/path/to/Image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        int width = 3, height = 3;
        int[] data = {  1, 1, 1,
                        1, 1, 1,
                        1, 1, 1};

        StructuringElement structElement = new StructuringElement(width, height, data);
        BufferedImageOp bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_CLOSE);

        bio.filter(src, dest);
    }

}
