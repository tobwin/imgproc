package imgproc;

public enum Imgproc {

    MORPH_ERODE (0),
    MORPH_DILATE (1),
    MORPH_OPEN (2),
    MORPH_CLOSE (3),

    MORPH_RECT (0),
    MORPH_CROSS (1),
    MORPH_ELLIPSE (2),

    IPL_BORDER_CONSTANT (0),
    IPL_BORDER_REPLICATE (1), // default in opencv (?)
    IPL_BORDER_REFLECT (2),
    PL_BORDER_WRAP (3);

    private final int index;

    Imgproc(int index) {
        this.index = index;
    }


    public static StructuringElement getStructuringElement(Imgproc shape, int width, int height) {

        int w = width / 2;
        int h = height / 2;

        boolean[] structuringData = new boolean[width*height];

        switch (shape) {

            case MORPH_ELLIPSE: {

                int i = 0;
                for(int x = -w; x <= w; x++) {
                    for(int y = -h; y <= h; y++) {
                        structuringData[i] = Math.pow(x,2)/Math.pow(w,2) + Math.pow(y,2)/Math.pow(h,2) <= 1;
                        i++;
                    }
                }
                break;

            } case MORPH_RECT: {

                int i = 0;
                for(int x = -w; x <= w; x++) {
                    for(int y = -h; y <= h; y++) {
                        structuringData[i] = true;
                        i++;
                    }
                }
                break;

            } default: {

                int i = 0;
                for(int x = -w; x <= w; x++) {
                    for(int y = -h; y <= h; y++) {
                        structuringData[i] = false;
                        i++;
                    }
                }
                break;

            }

        }

        return new StructuringElement(width, height, structuringData);
    }

}
