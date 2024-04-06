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
        String content1 = "https://aodo.jp/";

		//通常の符号化
		//--------------------------------------------------------------------------------------------------

		// int maxchangesym = 0;
		// for(char c = '-'; c <= 'z'; c++){
		// 	String content2 = "http://" + String.valueOf(c) + "lt.mchoco.jp/";

		// 	System.out.println(content2);

		// 	analysis.genqr1(content1, "content1");
		// 	analysis.genqr1(content2, "content2");

		// 	int d1 = analysis.calcdistance1(content1, content2);
		// 	// System.out.println("d1:" + d1);


		// 	//content1,2の符号語
		// 	byte[] content1byte = analysis.codeword(content1);
		// 	byte[] content2byte = analysis.codeword(content2);
		// 	byte[] xor = analysis.codewordplus2(content1byte, content2byte); 


		// 	// System.out.print("content1byte:");
		// 	// for(int i = 0; i < content1byte.length; i++){
		// 	// 	System.out.print(content1byte[i] + " ");
		// 	// }
		// 	// System.out.println();
			

		// 	// System.out.print("xor:");
		// 	// for(int i = 0; i < xor.length; i++){
		// 	// 	System.out.print(xor[i] + " ");
		// 	// }
		// 	// System.out.println();


		// 	// analysis.codewordBinary(xor);

		// 	String pathname1 = "C:\\git_code\\module_paint_QR\\content1.png"; 
		// 	String pathname2 = "C:\\git_code\\module_paint_QR\\content2.png"; 

		// 	File file1 = new File(pathname1);
		// 	BufferedImage image1 = ImageIO.read(file1);

		// 	File file2 = new File(pathname2);
		// 	BufferedImage image2 = ImageIO.read(file2);

		// 	int d2 = analysis.error_symbol_count(image1, image2);
		// 	// System.out.println("d2:" + d2);

		// 	int changesymbolnum = analysis.change_symbol_count(image1, image2);
		// 	System.out.println("changesym:" + changesymbolnum);

		// 	if(maxchangesym < changesymbolnum){
		// 		maxchangesym = changesymbolnum;
		// 	}

		// 	if((changesymbolnum >= 5)&&(d1 == 8)){
		// 		break;

		// 	}

	    // }

		// System.out.println(maxchangesym);

		// BufferedImage compmod = analysis.comp_module_pos1(image1, image2);
		// ImageIO.write(compmod , "png", new File("compmod.png"));

		// BufferedImage wtob = analysis.comp_module_pos2(image1, image2);
		// ImageIO.write(wtob , "png", new File("wtob.png"));

		//--------------------------------------------------------------------------------------------


		//消失誤り訂正を利用した符号化
		//--------------------------------------------------------------------------------------------


		int maxchange = 0;
		boolean charbreak = false;
		for(char c1 = 'a'; c1 <= 'z'; c1++){
			for(char c2 = 'a'; c2 <= 'z'; c2++){
				for(char c3 = 'a'; c3 <= 'z'; c3++){
					for(char c4 = 'a'; c4 <= 'z'; c4++){

						String content2 = "https://" + String.valueOf(c1) + String.valueOf(c2) + String.valueOf(c3) + String.valueOf(c4) +".jp/";

						System.out.println(content2);

						int d1 = analysis.calcdistance1(content1, content2);
						// System.out.println("d1:" + d1);


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
						int twos = 7;
						List<int[]> resultlist= new ArrayList <>();
						ReedSolomonDecoder decoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

						for(int k1 = datacodeword2.length; k1 < xorint.length - 6; k1++){
							for(int k2 = k1 + 1; k2 < xorint.length - 5; k2++){
								for(int k3 = k2 + 1; k3 < xorint.length - 4; k3++){
									for(int k4 = k3 + 1; k4 < xorint.length - 3; k4++){
										for(int k5 = k4 + 1; k5 < xorint.length - 2; k5++){
											for(int k6 = k5 + 1; k6 < xorint.length - 1; k6++){
												for(int k7 = k6 + 1; k7 < xorint.length; k7++){
													
													try{
														int[] eraseposition = {k1, k2, k3, k4, k5, k6, k7};
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
														if((symbolcount == 8)&&(check == true)){

															resultlist.add(xorintcopy);

														}


													}catch(Exception e){
														System.err.println(false);
													}																				
												}																
											}
										}
									}
								}
							}
						}	


						//同じ符号語が重複してリストに入っているので重複した符号語を消去したリストを生成
						List<int[]> resultlist_nodup = new ArrayList <>();

						for(int i = 0; i < resultlist.size(); i++){
							if(i == 0){
								resultlist_nodup.add(resultlist.get(i));
							}else{
								boolean dupcheck = true;
								for(int j = 0; j < resultlist_nodup.size(); j++){
									if(Arrays.equals(resultlist.get(i), resultlist_nodup.get(j)) == true){
										dupcheck = false;
									}
								}

								if(dupcheck == true){
									resultlist_nodup.add(resultlist.get(i));
								}
							}
						}

						System.out.println("resultnum:" + resultlist_nodup.size());
						
						
						for(int i = 0; i < resultlist_nodup.size(); i++){
							System.out.print("resultlist" + i + ":");
							for(int j = 0; j < resultlist_nodup.get(i).length; j++){
								int[] intarray = resultlist_nodup.get(i);
								System.out.print(intarray[j] + " ");
							}
							System.out.println();
						}
						System.out.println();


						byte[] resultbyte = new byte[content1byte.length]; 

						for(int i = 0; i < resultlist_nodup.size(); i++){
							int[] result = resultlist_nodup.get(i);
							for(int j = 0; j < result.length; j++){
								resultbyte[j] = (byte)result[j];
							}

							byte[] content2_1byte = analysis.codewordplus2(resultbyte, content1byte);
							analysis.genqr1(content1, "content1");
							analysis.genqr2(content2_1byte, "content2");


							String pathname1 = "C:\\git_code\\module_paint_QR\\content1.png"; 
							String pathname2 = "C:\\git_code\\module_paint_QR\\content2.png"; 

							File file1 = new File(pathname1);
							BufferedImage image1 = ImageIO.read(file1);

							File file2 = new File(pathname2);
							BufferedImage image2 = ImageIO.read(file2);

							int changesymbolnum = analysis.change_symbol_count(image1, image2);
							System.out.println("changesym" + i +":" + changesymbolnum);

							if(maxchange < changesymbolnum){
								maxchange = changesymbolnum;
							}

							if(changesymbolnum >= 5){
								charbreak = true;
								break;
							}
						}
						if(charbreak){
							break;
						}
					}
					if(charbreak){
						break;
					}				
				}
				if(charbreak){
					break;
				}
			}
			if(charbreak){
				break;
			}

	    }
		

		System.out.println("maxchange:" + maxchange);

		// BufferedImage compmod = analysis.comp_module_pos1(image1, image2);
		// ImageIO.write(compmod , "png", new File("compmod.png"));

		// BufferedImage wtob = analysis.comp_module_pos2(image1, image2);
		// ImageIO.write(wtob , "png", new File("wtob.png"));

		//--------------------------------------------------------------------------------------------
	

    }
}
