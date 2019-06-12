package imgproc;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StructuringElementTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLegalConstruction() {
        boolean[] bools = {false, false, false, false, false, true, true, true, true, true};
        StructuringElement se1 = new StructuringElement(2, 5, bools);
        int[] ints = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
        StructuringElement se2 = new StructuringElement(2, 5, ints);
        Assert.assertEquals(se1.getStructuringData(), se2.getStructuringData());
    }

    @Test
    public void testIllegalConstruction1() {
        thrown.expect(IllegalArgumentException.class);
        int[] data = {1, 0};
        StructuringElement se = new StructuringElement(2, 5, data);

    }

    @Test
    public void testIllegalConstruction2() {
        thrown.expect(IllegalArgumentException.class);
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        StructuringElement se = new StructuringElement(2, 5, data);

    }

    @Test
    public void testGetStructuringElement() {

        StructuringElement se = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, 3, 3);
        boolean[] expected = {false, true, false, true, true, true, false, true, false};
        boolean[] data = se.getStructuringData();
        for(int i = 0; i < 9; i++) {
            Assert.assertEquals(expected[i], data[i]);
        }

    }

}
