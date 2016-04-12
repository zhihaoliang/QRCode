
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {
	private static final String FORMAT_NAME = "JPG";
	private static final String CHARSET = "utf-8";
	
	/**
	 * 320 277 FFFFFF hello.jpg 220 220 000000  FFFFFF  15 15 www.baidu.com  4 -2  130 145
	 * 传递的参数介绍：
     *1.目标图片的宽度
     *2.目标图片的高度
     *     *3.目标图片的背景色
     *4.目标图片的名字

     *5.二维码的宽度220
     *6.二维码的高度220
     *7.二维码的显示颜色
     *8.二维码的背景色
     *9.二维码距离图片左端的距离
     *10.二维码距离图片顶端的距离
     *11.二维码的内容文字

     *12.用于微调底部文字的x坐标为正向右移动为负向左移动
     *13.用于微调底部文字的y坐标为正向下移动为负向上移动

     *14.右端图片的X坐标
     *15右端图片的Y坐标
	 * 
	 */
	public static void main(String name,String content) throws Exception {
		int ResWidth = 220 ;
		int resHeight = 220;
		int bgColor = Integer.parseInt("FFFFFF", 16);
		String imgResName = "images/"+name; 
		
		int codeWidth = 220; 
		int codeHeight = 220;
		int codeShowColor = Integer.parseInt("000000", 16);
		int codeBgColor = Integer.parseInt("FFFFFF", 16);
		int codeSplaceX = 0;
		int codeSplaceY = 0;
		
	/*	BufferedImage logoImg =ImageIO.read(new FileInputStream(new File("logo.png")));
		int logoWidth = logoImg.getWidth();
		int logoHeight = logoImg.getHeight();*/
		/*int logoX = codeSplaceX +(codeWidth-logoWidth>>1);
		int logoY = codeSplaceY +(codeHeight-logoHeight>>1);
		
		int bottomOffSpaceX = Integer.parseInt(args[11]);
		int bottomOffSpaceY = Integer.parseInt(args[12]);
		
		int rightX =Integer.parseInt(args[13]);
		int rightY =Integer.parseInt(args[14]);
		*/
		
		BufferedImage imgBg = createImBg(ResWidth,resHeight,bgColor);
		BufferedImage imgCode = createImage(content, codeWidth, codeHeight ,codeBgColor,codeShowColor);
		insertImage(imgBg, imgCode,  codeSplaceX, codeSplaceY) ;
		
		//insertImage(imgBg, "logo.png",  logoX, logoY) ;
		
		//insertImage(imgBg, "bottom.png", codeSplaceX+bottomOffSpaceX, bottomOffSpaceY+codeSplaceY+codeHeight) ;
	
   // insertImage(imgBg, "bottom-right.png", rightX, rightY) ;
		
		//insertImage(imgBg, "bottom.png", codeSplaceX+bottomOffSpaceX, bottomOffSpaceY+codeSplaceY+codeHeight) ;
		
		ImageIO.write(imgBg, FORMAT_NAME, new File(imgResName));
		
	}
	
	private static BufferedImage createImBg(int width,int height,int bgColor){
		BufferedImage imageDes = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = imageDes.getMinY(); i < imageDes.getHeight(); i++) {
			for (int j = imageDes.getMinX(); j < imageDes.getWidth(); j++) {
				imageDes.setRGB(j, i, bgColor);
			}
		}
		return imageDes;
	}
	
	private static BufferedImage createImage(String content, int width, int height ,int bgColor,int showColor) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? showColor : bgColor);
			}
		}
		return image;
	}
	
	private static void insertImage(BufferedImage imBg, BufferedImage imfrontBuffer, int x, int y) throws Exception {
		Graphics2D graph = imBg.createGraphics();
		int width = imfrontBuffer.getWidth();
		int height = imfrontBuffer.getHeight();
		Image imfront = (Image)imfrontBuffer;
		graph.drawImage(imfront, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}
	
	private static void insertImage(BufferedImage imBg, String imgPath,  int x, int y) throws Exception {
		BufferedImage sourceImg =ImageIO.read(new FileInputStream(new File(imgPath)));
		insertImage( imBg,  sourceImg,    x,  y);
	}
	
	
}
