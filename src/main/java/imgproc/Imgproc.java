package imgproc;

public enum Imgproc {

    MORPH_ERODE (0),
    MORPH_DILATE (1),
    MORPH_OPEN (2),
    MORPH_CLOSE (3);

    private final int index;

    Imgproc(int index) {
        this.index = index;
    }

}
