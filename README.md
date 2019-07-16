# imgproc.MorphologicalTransformOp

The **MorphologicalTransformOp** class provided by this package is an implementation of the [BufferedImageOp](https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImageOp.html) interface. It is intended as a pure Java alternative to the corresponding class provided by OpenCV for Java.


# 

### BufferedImageOp

The Java standard library provides several basic image processing classes, such as ConvolveOp, LookupOp and ColorConvertOp. These are all implementations of the builtin BufferedImageOp interface. 

The following example uses a ConvolveOp to blurr an image with a Gaussian kernel:

```Java
import java.awt.image.*;  
import javax.imageio.ImageIO;  
import java.io.File;
```
```Java
BufferedImage src = null;
try {  
    src = ImageIO.read(new File("./Image.png"));  
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

### Morphological Transformations
[Morphological transformations](https://en.wikipedia.org/wiki/Mathematical_morphology) - erosion, dilation, opening and closing - are not provided by the standard library. [OpenCV](https://opencv.org/) is a great open-source image processing library that also supports Java, however using native libraries is not always desirable and sometimes even impossible. This pure Java package borrows some structure and terminology from OpenCV in its implementation of morphological transformations and structuring elements.




# Documentation


## MorphologicalTransformOp

Using a MorphologicalTransformOp is completely analogous to performing a ConvolveOp:

```Java
import java.awt.image.*;  
import javax.imageio.ImageIO;  
import java.io.File;

import imgproc.*;
```
```Java
BufferedImage src = null; 
try {  
    src = ImageIO.read(new File("./Image.png"));  
} catch (IOException e) {  
    e.printStackTrace();  
}
BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

int width = 3, height = 3;
int[] data = {	1, 1, 1,
		1, 1, 1,
		1, 1, 1};

StructuringElement structElement = new StructuringElement(width, height, data);
BufferedImageOp bio = new MorphologicalTransformOp(structElement , Imgproc.MORPH_CLOSE);

bio.filter(src, dest);
```

## StructuringElement

Structuring elements are implemented as binary kernels. The StructuringElement class therefore provides the same interface as the builtin [Kernel](https://docs.oracle.com/javase/8/docs/api/index.html?java/awt/image/Kernel.html) class, the only difference being the boolean data array:
```Java
int width = 3, height = 3;
int[] data = {	true, true, true,
		true, true, true,
		true, true, true};
StructuringElement se = new StructuringElement(width, height, data);
```
The main example uses an overloading constructor accepting an integer array. 



###  getStructuringElement()

Special structuring elements can also be created by calling Imgproc.getStructuringElement():
```Java
int width = 5, height = 3;
StructuringElement se;

// Rectangular element
se = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, width, height);

// Elliptical element
se = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, width, height);
```


*Note: The current version only allows odd width and height parameters.*

# Development Notes

(1) The class in its current version reproduces transformations by OpenCV on **grayscale** images **without alpha-channel** and with **odd-dimensional** and **reasonably-sized** structuring elements. All of these assumptions are currently unchecked.

(2) The interface implementation remains incomplete with the following methods returning null:
- getBounds2D()
- createCompatibleDestImage()
- getPoint2D() and
- getRenderingHints() 

Code extensons and bug-fixes are welcome contributions. If you discover a bug that is not related to (1) or (2), you're also welcome to open an issue. 