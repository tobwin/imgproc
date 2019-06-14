package imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public enum Imgproc {

    MORPH_ERODE (0),
    MORPH_DILATE (1),
    MORPH_OPEN (2),
    MORPH_CLOSE (3),

    MORPH_RECT (0),
    MORPH_CROSS (1),
    MORPH_ELLIPSE (2),

    BORDER_CONSTANT (0),
    BORDER_REPLICATE (1),
    BORDER_REFLECT (2), // ..cba/abcdefgh/hgf...
    BORDER_WRAP (3),
    BORDER_DEFAULT (4), BORDER_REFLECT101 (4), BORDER_REFLECT_101 (4), // ..cb/abcdefgh/gf...
    BORDER_TRANSPARENT (5);

    private final int index;

    Imgproc(int index) {
        this.index = index;
    }


    public static StructuringElement getStructuringElement(Imgproc shape, int width, int height) {

        int w = width / 2;
        int h = height / 2;

        boolean[] data = new boolean[width*height];

        switch (shape) {

            case MORPH_ELLIPSE: {

                int i = 0;
                for(int y = -h; y <= h; y++) {
                    for(int x = -w; x <= w; x++) {
                        data[i] = Math.pow(x,2)/Math.pow(w,2) + Math.pow(y,2)/Math.pow(h,2) <= 1;
                        i++;
                    }
                }
                break;

            } case MORPH_RECT: {
                for(int i = 0; i < data.length; i++) {
                    data[i] = true;
                }
                break;

            } default: {
                for(int i = 0; i < data.length; i++) {
                    data[i] = false;
                }
                break;
            }

        }

        return new StructuringElement(width, height, data);
    }

    public static BufferedImage extendBorder(BufferedImage orig, int xOrigin, int yOrigin) {
        return extendBorder(orig, xOrigin, yOrigin, BORDER_DEFAULT);
    }

    public static BufferedImage extendBorder(BufferedImage orig, int xOrigin, int yOrigin, Imgproc BORDER) {

        // BORDER_DEFAULT = BORDER_REFLECT_101

        BufferedImage ext = new BufferedImage(orig.getWidth() + 2 *xOrigin, orig.getHeight() + 2 * yOrigin, BufferedImage.TYPE_BYTE_GRAY);

        Raster raster = orig.getRaster();

        int w = orig.getWidth();
        int h = orig.getHeight();

        // fill white
        for(int row = 0; row < ext.getHeight(); row++) {
            for(int col = 0; col < ext.getWidth(); col++) {
                ext.getRaster().setSample(col, row, 0, 255);
            }
        }

        // copy original part
        for(int row = 0; row < h; row++) {
            for(int col = 0; col < w; col++) {
                ext.getRaster().setSample(col + xOrigin, row + yOrigin, 0, raster.getSample(col, row, 0) );
            }
        }

        // left and right border
        for(int row = 0; row < h; row++) {
            for(int x = 0; x < xOrigin; x++) {
                ext.getRaster().setSample(x, row + yOrigin, 0, raster.getSample(xOrigin - x, row, 0));
                ext.getRaster().setSample(w + xOrigin +  x, row + yOrigin, 0, raster.getSample(w - x - (w % 2 == 0 ? 1 : 2), row, 0));
            }
        }

        // top and bottom border
        for(int col = 0; col < w; col++) {
            for(int y = 0; y < yOrigin; y++) {
                ext.getRaster().setSample(col + xOrigin, y, 0, raster.getSample(col, yOrigin - y, 0));
                ext.getRaster().setSample(col + xOrigin, h + yOrigin +  y, 0, raster.getSample(col, h - y - (h % 2 == 0 ? 1 : 2), 0));
            }
        }

        return ext;
    }


    public static BufferedImage trimBorder(BufferedImage ext, BufferedImage trim, int xOrigin, int yOrigin) {

        int w = ext.getWidth();
        int h = ext.getHeight();

        Raster raster = ext.getRaster();

        for(int row = 0; row < h - 2 * yOrigin; row++) {
            for(int col = 0; col < w - 2 * xOrigin; col++) {
                trim.getRaster().setSample(col, row, 0, raster.getSample(col + yOrigin + 1, row + xOrigin - 1, 0) );
            }
        }

        return trim;
    }


}
