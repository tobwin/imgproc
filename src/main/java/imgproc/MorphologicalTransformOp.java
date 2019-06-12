package imgproc;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.Raster;

public class MorphologicalTransformOp implements BufferedImageOp {

    private StructuringElement strel;
    private Imgproc MORPH;

    public MorphologicalTransformOp(StructuringElement strel, Imgproc morph) {
        this.strel = strel;
        this.MORPH = morph;
    }

    public Rectangle2D getBounds2D(BufferedImage src) {
        return null;
    }

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        return null;
    }

    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        return null;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }


    private int basicTransform(byte[] data, boolean[] structuringData) {
        switch(MORPH) {
            case MORPH_ERODE: return erode(data, structuringData);
            case MORPH_DILATE: return dilate(data, structuringData);
            default : return 0;
        }
    }

    private int erode(byte[] data, boolean[] structuringData) {
        int min = 255;
        for(int i = 0; i < data.length; i++) {
            if(structuringData[i]) {
                int x = (int)data[i] & 0xff;
                if(x < min) {
                    min = x;
                }
            }
        }
        return min;
    }

    private int dilate(byte[] data, boolean[] structuringData) {
        int max = 0;
        for(int i = 0; i < data.length; i++) {
            if(structuringData[i]) {
                int x = (int)data[i] & 0xff;
                if(x > max) {
                    max = x;
                }
            }
        }
        return max;
    }


    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        // src must be grayscale image without alpha

        switch (MORPH) {

            case MORPH_OPEN: {

                MorphologicalTransformOp erosion = new MorphologicalTransformOp(strel, Imgproc.MORPH_ERODE);
                BufferedImage inter = erosion.filter(src, new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY));
                MorphologicalTransformOp dilation = new MorphologicalTransformOp(strel, Imgproc.MORPH_DILATE);
                dest = dilation.filter(inter, dest);
                break;

            } case MORPH_CLOSE: {

                MorphologicalTransformOp dilation = new MorphologicalTransformOp(strel,Imgproc.MORPH_DILATE);
                BufferedImage inter = dilation.filter(src, new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY));
                MorphologicalTransformOp erosion = new MorphologicalTransformOp(strel, Imgproc.MORPH_ERODE);
                dest = erosion.filter(inter, dest);
                break;

            } default: {

                int w = src.getWidth();
                int h = src.getHeight();
                Raster raster = src.getRaster();
                byte[] data = new byte[strel.getWidth()*strel.getHeight()];
                for(int row = 0; row < h - strel.getHeight(); row++) {
                    for(int col = 0; col < w - strel.getWidth(); col++) {
                        raster.getDataElements(col, row, strel.getWidth(), strel.getHeight(), data);
                        dest.getRaster().setSample(col + strel.getXOrigin(), row + strel.getYOrigin(), 0, basicTransform(data, strel.getStructuringData())) ;
                    }
                }
                break;

            }
        }

        return dest;

    }

}
