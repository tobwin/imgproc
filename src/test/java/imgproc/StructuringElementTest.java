package imgproc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StructuringElementTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLegalArguments() {
        int[] data = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
        StructuringElement se = new StructuringElement(2, 5, data);
    }

    @Test
    public void testIllegalArguments1() {
        thrown.expect(IllegalArgumentException.class);
        int[] data = {1, 0};
        StructuringElement se = new StructuringElement(2, 5, data);

    }

    @Test
    public void testIllegalArguments2() {
        thrown.expect(IllegalArgumentException.class);
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        StructuringElement se = new StructuringElement(2, 5, data);

    }

}
