import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.BitMatrixParser;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.detector.Detector;




public class Appnomal {
    public static void main(String[] args) throws Exception {
        
		Analysisnomal analysis = new Analysisnomal();

		//// QRコード生成
		// String content1 = "https://www.kobe-u.ac.jp/";
		// String content2 = "https://www.aobe-u.ac.jp/";
        String content1 = "http://almond.jp";
		String content2 = "http://blmond.jp";

		analysis.genqr1(content1, "content1");
		analysis.genqr1(content2, "content2");

		int d = analysis.calcdistance1(content1, content2);
		System.out.println(d);

		//content1,2の符号語
		byte[] content1byte = analysis.codeword(content1);
		byte[] content2byte = analysis.codeword(content2);
		byte[] xor = analysis.codewordplus2(content1byte, content2byte); 


		System.out.print("content1byte:");
		for(int i = 0; i < content1byte.length; i++){
			System.out.print(content1byte[i] + " ");
		}
		System.out.println();
		

		System.out.print("xor:");
		for(int i = 0; i < xor.length; i++){
			System.out.print(xor[i] + " ");
		}
		System.out.println();


		analysis.codewordBinary(xor);

		String pathname1 = "C:\\git_code\\module_paint_QR\\content1.png"; 
		String pathname2 = "C:\\git_code\\module_paint_QR\\content2.png"; 

		File file1 = new File(pathname1);
		BufferedImage image1 = ImageIO.read(file1);

		File file2 = new File(pathname2);
		BufferedImage image2 = ImageIO.read(file2);

		BufferedImage compmod = analysis.comp_module_pos1(image1, image2);
		ImageIO.write(compmod , "png", new File("compmod.png"));

		BufferedImage wtob = analysis.comp_module_pos2(image1, image2);
		ImageIO.write(wtob , "png", new File("wtob.png"));


	

    }
}
