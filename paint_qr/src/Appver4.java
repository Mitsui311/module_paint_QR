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




public class Appver4 {
    public static void main(String[] args) throws Exception {
        //// QRコード生成
		Analysisver4 analysis = new Analysisver4();

        String content1 = "https://www.kobe-u.ac.jp/";
		String content2 = "https://www.kobe-niku.jp/";

		byte[] content1byte = analysis.codeword(content1);
		byte[] datacodeword1 = analysis.datacodeword(content1);
		byte[] datacodeword1pad = new byte[content1byte.length]; 

		System.out.println(datacodeword1.length);
		
		for(int i = 0; i < datacodeword1pad.length; i++){
			if(i < datacodeword1.length){
				datacodeword1pad[i] = datacodeword1[i];
			}else{
				datacodeword1pad[i] = 0;
			}
		}
		
		for(int i = 0; i < datacodeword1pad.length; i++){
			System.out.print(datacodeword1pad[i] + " ");
		}
		System.out.println();

		byte[] content2byte = analysis.codeword(content2);
		byte[] datacodeword2 = analysis.datacodeword(content2);
		byte[] datacodeword2pad = new byte[content2byte.length]; 

		System.out.println(datacodeword2.length);
		
		for(int i = 0; i < datacodeword2pad.length; i++){
			if(i < datacodeword2.length){
				datacodeword2pad[i] = datacodeword2[i];
			}else{
				datacodeword2pad[i] = 0;
			}
		}

		System.out.println(datacodeword2pad.length);

		for(int i = 0; i < datacodeword2pad.length; i++){
			System.out.print(datacodeword2pad[i] + " ");
		}
		System.out.println();

		byte[] xor = analysis.codewordplus2(datacodeword1pad, datacodeword2pad);
		int[] xorint = analysis.tointarray(xor);

		for(int i = 0; i < xorint.length; i++){
			System.out.print(xorint[i] + " ");
		}
		System.out.println();

		////消失誤り訂正を利用して、消失点を総当たりしながら符号語を生成
		int twos = 20;
		int count = 0;
		boolean breakcheck = false;
		int[] result = new int[xorint.length];
	
		ReedSolomonDecoder decoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

		for(int k1 = datacodeword2.length; k1 < xorint.length - 11; k1++){
			for(int k2 = k1 + 1; k2 < xorint.length - 10; k2++){
				for(int k3 = k2 + 1; k3 < xorint.length - 9; k3++){
					for(int k4 = k3 + 1; k4 < xorint.length - 8; k4++){
						for(int k5 = k4 + 1; k5 < xorint.length - 7; k5++){
							for(int k6 = k5 + 1; k6 < xorint.length - 6; k6++){
								for(int k7 = k6 + 1; k7 < xorint.length - 5; k7++){
									for(int k8 = k7 + 1; k8 < xorint.length - 4; k8++){
										for(int k9 = k8 + 1; k9 < xorint.length - 3; k9++){
											for(int k10 = k9 + 1; k10 < xorint.length - 2; k10++){
												for(int k11 = k10 + 1; k11 < xorint.length - 1; k11++){
													for(int k12 = k11 + 1; k12 < xorint.length; k12++){
										
		
														count++;
														System.out.println(count);
														try{
															int[] eraseposition = {k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11, k12};
															int[] xorintcopy = new int[xorint.length];
															
															
															for(int i = 0; i < xorint.length; i++){
																xorintcopy[i] = xorint[i];
															}

															int EC = decoder.erasedecodeWithECCount(xorintcopy, eraseposition, twos);

															

															
															int symbolcount = 0;
															boolean check = true;
															for(int i = 0; i < xorintcopy.length; i++){
																if(xorintcopy[i] != 0){
																	symbolcount++;
																}
																if((i < datacodeword2.length)&&(xorintcopy[i] != xorint[i])){
																	check = false;
																}
															}
															if((symbolcount == 21)&&(check == true)){

																for(int i = 0; i < xorintcopy.length; i++){
																	result[i] = xorintcopy[i];
																}

																breakcheck = true;
																break;

															}


														}catch(Exception e){
															System.err.println(false);
														}
													}	
													if(breakcheck){
														break;
													}
												}	
												if(breakcheck){
													break;
												}
											}	
											if(breakcheck){
												break;
											}
										}	
										if(breakcheck){
											break;
										}

									}	
									if(breakcheck){
										break;
									}
											
								}	
								if(breakcheck){
									break;
								}														
							}
							if(breakcheck){
								break;
							}
						}
						if(breakcheck){
							break;
						}
					}
					if(breakcheck){
						break;
					}
				}
				if(breakcheck){
					break;
				}
			}
			if(breakcheck){
				break;
			}
		}	
		

		System.out.print("result:");
		for(int i = 0; i < result.length; i++){
			System.out.print(result[i] + " ");
		}
		System.out.println();


		byte[] resultbyte = new byte[result.length];
		for(int i = 0; i < result.length; i++){
			resultbyte[i] = (byte)result[i];
		}



		byte[] content1_1byte = analysis.codewordplus2(content2byte, resultbyte);
		System.out.println("content1d:" + analysis.calcdistance2(content2byte, content1_1byte));
		
		System.out.print("content1:");
		for(int i = 0; i < content1_1byte.length; i++){
			System.out.print(Byte.toUnsignedInt(content1_1byte[i]) + " ");
		}
		System.out.println();


		analysis.genqr2(content1_1byte, "content1");
		analysis.genqr2(content2byte, "content2");


		content1_1byte = analysis.changesymbol(content1_1byte, content2byte);
		System.out.println("content1-1d:" + analysis.calcdistance2(content2byte, content1_1byte));

		System.out.print("content1_1:");
		for(int i = 0; i < content1_1byte.length; i++){
			System.out.print(Byte.toUnsignedInt(content1_1byte[i]) + " ");
		}
		System.out.println();

		System.out.print("content2:");
		for(int i = 0; i < content2byte.length; i++){
			System.out.print(Byte.toUnsignedInt(content2byte[i]) + " ");
		}
		System.out.println();

		analysis.genqr2(content1_1byte, "content1_1");

		analysis.codewordBinary(analysis.codewordplus2(content2byte, content1_1byte));


		String pathname1 = "C:\\git_code\\module_paint_QR\\content1_1.png"; 
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
