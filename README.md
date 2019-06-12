The **MorphologicalTransformOp** class provided by this package is an implementation of the [BufferedImageOp](https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImageOp.html) interface. It is intended to be a pure Java alternative to the corresponding class provided by OpenCV for Java and derives many of its syntactic features, defaults etc. from it.

# Use

### BufferedImageOp

The BufferedImageOp interface together with implementations such as ConvolveOp, LookupOp and ColorConvertOp comes with the Java standard library. The following example uses a ConvolveOp to blurr an image with a Gaussian kernel.

```Java
import java.awt.image.*;  
import javax.imageio.ImageIO;  
import java.io.File;

BufferedImage src = null;
try {  
    src = ImageIO.read(new File("./Image.jpg"));  
} catch (IOException e) {  
    e.printStackTrace();  
}
BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

int width = 3, height = 3;
float[] data = {1.0f, 2.0f, 1.0f,
				2.0f, 4.0f, 2.0f,
				1.0f, 2.0f, 1.0f};

Kernel kernel = new Kernel(width, height, data);
BufferedImageOp bio = new ConvolveOp(kernel);

bio.filter(src, dest);
```

### MorphologicalTransformOp

To perform a closing operation you would use MorphologicalTransformOp like this:

```Java
import java.awt.image.*;  
import javax.imageio.ImageIO;  
import java.io.File;

import imgproc.*;

BufferedImage src = null; 
try {  
    src = ImageIO.read(new File("./Image.jpg"));  
} catch (IOException e) {  
    e.printStackTrace();  
}
BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

int width = 3, height = 3;
int[] data = {1, 1, 1,
			  1, 1, 1,
			  1, 1, 1};

StructuringElement structElement = new StructuringElement(width, height, data);
BufferedImageOp bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_CLOSE);

bio.filter(src, dest);
```

## StructuringElement

####  Constructors
Structuring elements can be thought of as binary kernels. The StructuringElement class therefore uses the same "interface" as the standard Kernel class. The data elements are in fact of boolean type and the main constructor accepts a corresponding array:
```Java
int width = 3, height = 3;
int[] data = {true, true, true,
			  true, true, true,
			  true, true, true};
StructuringElement se = new StructuringElement(width, height, data);
```
The main example just uses a more convenient overloading constructor. 
####  getStructuringElement()
Structuring elements can also be created using the Imgproc.getStructuringElement function:
```Java
int width = 5, height = 3;
StructuringElement se;

// Rectangular element
se = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, width, height);

// Elliptical element
se = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, width, height);
```

