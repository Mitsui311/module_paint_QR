import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;




public class App {
    public static void main(String[] args) throws Exception {
        //// QRコード生成。基本使わないが必要な時があるかもなので一応置いておく
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
    }
}
