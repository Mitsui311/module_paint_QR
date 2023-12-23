import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
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
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.BitMatrixParser;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.detector.Detector;




public class App {
    public static void main(String[] args) throws Exception {
        //// QRコード生成
        String contents = "https://www.kobe-u.ac.jp/";
		System.out.println(contents);
		try {
			Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
      		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			hints.put(EncodeHintType.QR_VERSION, 2);
			QRCodeWriter writer = new QRCodeWriter();
			BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, 256, 256, hints);
			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ImageIO.write(image, "png", new File("example.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		////QRコードリーダー
		// 環境ごとにresourseがあるパスが違うので適宜変更
		String pathname = "C:\\code\\module_paint_QR\\paint_qr\\example.png"; 

		File file = new File(pathname);
		BufferedImage image = ImageIO.read(file);
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap bitmap = new BinaryBitmap(binarizer);
		QRCodeReader reader = new QRCodeReader();

		DetectorResult detectorResult = new Detector(bitmap.getBlackMatrix()).detect();
		BitMatrix bits = detectorResult.getBits();
		

		for(int i = 0; i < bits.getHeight(); i++){
			for(int j= 0; j < bits.getWidth(); j++){
				if(bits.get(j, i)){
					System.out.print("1" + " ");
				}
				if(!bits.get(j, i)){
					System.out.print("0" + " ");
				}
			}
			System.out.println();
		}

		BitMatrixParser parser = new BitMatrixParser(bits);

		//白モジュールの座標を取り出す
		int[][] whitemodpos = parser.getwhitemod();

		System.out.println(whitemodpos[0].length);

		BitMatrix blackbits = new BitMatrix(bits.getWidth());
		for(int i = 0; i < blackbits.getHeight(); i++){
			for(int j = 0; j < blackbits.getWidth(); j++){
				blackbits.set(i, j);
			}
		}


		for(int i = 0; i < whitemodpos[0].length; i++){
			blackbits.unset(whitemodpos[0][i], whitemodpos[1][i]);
		}

		
		BufferedImage whitemod = MatrixToImageWriter.toBufferedImage(blackbits);
		ImageIO.write(whitemod , "png", new File("whitemod.png"));

		

		try {
			Result result = reader.decode(bitmap);
			System.out.println(result.getText());
		} catch (NotFoundException | ChecksumException | FormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
