package imgproc;

public class StructuringElement implements Cloneable{

    private int width;
    private int height;
    private boolean data[];

    public final int getXOrigin() {
        return (this.width-1)/2;
    }

    public final int getYOrigin() {
        return (this.height-1)/2;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final boolean[] getStructuringData(boolean[] data) {
        data = this.data;
        return data;
    }

    public StructuringElement(int width, int height, boolean[] data) throws IllegalArgumentException {
        if(data.length != width * height) { throw new IllegalArgumentException(); }
    }

    public StructuringElement(int width, int height, int[] data) throws IllegalArgumentException {
        this(width, height, convert(data));
    }

    private static boolean[] convert(int[] ints) {
        boolean[] bools = new boolean[ints.length];
        for(int i = 0; i < ints.length; i++) {
            switch(ints[i]) {
                case 0: bools[i] = false; break;
                case 1: bools[i] = true; break;
                default: throw new IllegalArgumentException("int[] data must be binary");
            }
        }
        return bools;
    }

    public Object clone() {
        return new StructuringElement(this.width, this.height, this.data);
    }

}
