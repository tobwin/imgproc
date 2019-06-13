package imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
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


}
