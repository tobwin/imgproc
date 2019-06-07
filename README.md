

### BufferedImageOp

MorphologicalTranformOp implements the BufferedImageOp interface, which is provided with the Java standard library together with a few implementations such as ConvolveOp, LookupOp and ColorConvertOp.

The following example uses a ConvolveOp to blurr an image with a Gaussian kernel.

```Java
BufferedImage src = ImageIO.read(new File("./path/to/Image.jpg"));
BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY));

int width, height = 3;
float[] data = {1.0f, 2.0f, 1.0f,
				2.0f, 4.0f, 2.0f,
				1.0f, 2.0f, 1.0f};

Kernel kernel = new Kernel(width, height, data);
BufferedImageOp bio = new ConvolveOp(kernel);

bio.filter(src, dest);
```

### MorphologicalTransformOp

This example uses a MorphologicalTransformOp to perform a closing operation.

```Java
BufferedImage src = ImageIO.read(new File("./path/to/Image.jpg"));
BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY));

int width, height = 3;
int[] data = {1, 1, 1,
			  1, 1, 1,
			  1, 1, 1};

StructuringElement structElement = new StructuringElement(width, height, data);
BufferedImageOp bio = new MorphologicalTransformOp(structElement , MORPH_CLOSE);

bio.filter(src, dest);
```


### StructuringElement

### edgeCondition


