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




public class App {
    public static void main(String[] args) throws Exception {
        //// QRコード生成
        String content1 = "https://www.kobe-u.ac.jp/";
		Analysis analysis = new Analysis();

		//----------------------------------------------------------------

		// String content2 = "https://www.kobe-niku.jp/";

		// analysis.genqr1(content1, "content1");
		// analysis.genqr1(content2, "content2");

		// int d = analysis.calcdistance1(content1, content2);

		// //content1,2の符号語
		// byte[] content1byte = analysis.codeword(content1);
		// byte[] content2byte = analysis.codeword(content2);
		// byte[] xor = analysis.codewordplus(content1, content2);

		// //content1のcontent2と違うbitが多いtシンボルをcontent2のシンボルに置き換える
		// byte[] content1_1byte = analysis.changesymbol(content1byte, content2byte);

		// byte[] xor2 = analysis.codewordplus2(content1_1byte, content2byte); 


		// System.out.print("content1byte:");
		// for(int i = 0; i < content1byte.length; i++){
		// 	System.out.print(content1byte[i] + " ");
		// }
		// System.out.println();
		
		// System.out.print("content1_1byte:");
		// for(int i = 0; i < content1_1byte.length; i++){
		// 	System.out.print(content1_1byte[i] + " ");
		// }
		// System.out.println();

		// System.out.print("xor:");
		// for(int i = 0; i < xor.length; i++){
		// 	System.out.print(xor[i] + " ");
		// }
		// System.out.println();

		// System.out.print("xor2:");
		// for(int i = 0; i < xor2.length; i++){
		// 	System.out.print(xor2[i] + " ");
		// }
		// System.out.println();

		// analysis.codewordBinary(xor2);

		// analysis.genqr2(content1_1byte, "content1_1");


		// String pathname1 = "C:\\git_code\\module_paint_QR\\content1_1.png"; 
		// String pathname2 = "C:\\git_code\\module_paint_QR\\content2.png"; 

		// File file1 = new File(pathname1);
		// BufferedImage image1 = ImageIO.read(file1);

		// File file2 = new File(pathname2);
		// BufferedImage image2 = ImageIO.read(file2);

		// BufferedImage compmod = analysis.comp_module_pos1(image1, image2);
		// ImageIO.write(compmod , "png", new File("compmod.png"));

		// BufferedImage wtob = analysis.comp_module_pos2(image1, image2);
		// ImageIO.write(wtob , "png", new File("wtob.png"));

		// System.out.println(d);


		//---------------------------------------------------------------------

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
		int twos = 15;
		int count = 0;
		List<int[]> resultlist= new ArrayList <>();
		int[] result = new int[xorint.length];
		ReedSolomonDecoder decoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);
		boolean break1 = false;

		for(int k1 = datacodeword2.length; k1 < xorint.length - 7; k1++){
			for(int k2 = k1 + 1; k2 < xorint.length - 6; k2++){
				for(int k3 = k2 + 1; k3 < xorint.length - 5; k3++){
					for(int k4 = k3 + 1; k4 < xorint.length - 4; k4++){
						for(int k5 = k4 + 1; k5 < xorint.length - 3; k5++){
							for(int k6 = k5 + 1; k6 < xorint.length - 2; k6++){
								for(int k7 = k6 + 1; k7 < xorint.length - 1; k7++){
									for(int k8 = k7 + 1; k8 < xorint.length; k8++){
									
										count++;
										System.out.println(count);
										try{
											int[] eraseposition = {k1, k2, k3, k4, k5, k6, k7, k8};
											int[] xorintcopy = new int[xorint.length];
											int[] xorintcopy2 = new int[xorint.length];
											
											for(int i = 0; i < xorint.length; i++){
												xorintcopy[i] = xorint[i];
											}

											int EC = decoder.erasedecodeWithECCount(xorintcopy, eraseposition, twos);

											for(int i = 0; i < xorintcopy.length; i++){
												xorintcopy2[i] = xorintcopy[i];
											}

											xorintcopy2[0] = 1;
											xorintcopy2[1] = 1;
											xorintcopy2[2] = 1;
											xorintcopy2[3] = 1;
											xorintcopy2[4] = 1;
											xorintcopy2[5] = 1;
											xorintcopy2[6] = 1;
											

											int EC2 = decoder.decodeWithECCount(xorintcopy2, twos);

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
											if((symbolcount == 16)&&(check == true)){

												resultlist.add(xorintcopy);
												break1 = true;
												break;

											}


										}catch(Exception e){
											System.err.println(false);
										}
																		
										if(break1 == true){
											break;
										}
									}
									if(break1 == true){
										break;
									}
								}
								if(break1 == true){
									break;
								}
							}
							if(break1 == true){
								break;
							}
						}
						if(break1 == true){
							break;
						}
					}
					if(break1 == true){
						break;
					}
				}
				if(break1 == true){
					break;
				}
			}
			if(break1 == true){
				break;
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
		
		
		for(int i = 0; i < resultlist.size(); i++){
			System.out.print("resultlist" + i + ":");
			for(int j = 0; j < resultlist.get(i).length; j++){
				int[] intarray = resultlist.get(i);
				System.out.print(intarray[j] + " ");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("resultsize:" + resultlist.size());
		System.out.println("arrangesize:" + resultlist_nodup.size());


		// int num = 0;
		// for(int i = 0; i < resultlist_nodup.size(); i++){
		// 	int count1 = 0;
		// 	for(int j = 0; j < resultlist_nodup.get(i).length; j++){
		// 		int[] intarray = resultlist_nodup.get(i);
		// 		if(analysis.count1((byte)intarray[j]) == 1){
		// 			count1++;
		// 		}
		// 	}
		// 	if (count1 == 1) {
		// 		num++;
		// 		if(num == 1){
		// 			result = resultlist_nodup.get(i);
					
		// 		}
		// 	}
		// }

		// System.out.println("num:" + num);

		result = resultlist_nodup.get(0);
		

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
