import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
        String content1 = "http://almond.jp";

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


		//消失誤り訂正を利用した符号化で探索
		//--------------------------------------------------------------------------------------------


		// int maxchange = 0;
		// int count = 0;
		// boolean charbreak = false;
		

		// analysis.genqr1(content1, "content1");

		// byte[] content1byte = analysis.codeword(content1);
		// byte[] datacodeword1 = analysis.datacodeword(content1);
		// byte[] datacodeword1pad = new byte[content1byte.length]; 

		// System.out.println(datacodeword1.length);

		// for(int i = 0; i < datacodeword1pad.length; i++){
		// 	if(i < datacodeword1.length){
		// 		datacodeword1pad[i] = datacodeword1[i];
		// 	}else{
		// 		datacodeword1pad[i] = 0;
		// 	}
		// }

		// String content2 = "http://almond.jp";

		// System.out.println(content2);


		// byte[] content2byte = analysis.codeword(content2);
		// byte[] datacodeword2 = analysis.datacodeword(content2);
		// byte[] datacodeword2pad = new byte[content2byte.length]; 

		// System.out.println(datacodeword2.length);

		// for(int bit = 0; bit < 8; bit++){
		// 	count++;
		// 	System.out.println(count);

		// 	for(int i = 0; i < datacodeword2pad.length; i++){
		// 		if(i < datacodeword2.length){
		// 			datacodeword2pad[i] = datacodeword2[i];
		// 		}else{
		// 			datacodeword2pad[i] = 0;
		// 		}
		// 	}

		// 	// 誤りを入れる
		// 	datacodeword2pad[12] = analysis.bitchange(datacodeword2pad[12], bit);

		// 	// for(int i = 0; i < datacodeword2pad.length; i++){
		// 	// 	System.out.print(datacodeword2pad[i] + " ");
		// 	// }
		// 	// System.out.println();
		

		// 	// System.out.println();


		// 	byte[] xor = analysis.codewordplus2(datacodeword1pad, datacodeword2pad);
		// 	int[] xorint = analysis.tointarray(xor);

		// 	for(int i = 0; i < xorint.length; i++){
		// 		System.out.print(xorint[i] + " ");
		// 	}
		// 	System.out.println();
		// }

			//消失誤り訂正を利用して、消失点を総当たりしながら符号語を生成
		// 	int twos = 16;
		// 	List<int[]> resultlist= new ArrayList <>();
		// 	List<int[]> resultlist9_11= new ArrayList <>();
		// 	ReedSolomonDecoder decoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

		// 	// 埋め草コード後から符号語の終わりまでで消失点を選ぶ、消失点の数はループの数に等しい

		// 	for(int k1 = datacodeword2.length; k1 < xorint.length - 15; k1++){
		// 		for(int k2 = k1 + 1; k2 < xorint.length - 14; k2++){
		// 			for(int k3 = k2 + 1; k3 < xorint.length - 13; k3++){
		// 				for(int k4 = k3 + 1; k4 < xorint.length - 12; k4++){
		// 					for(int k5 = k4 + 1; k5 < xorint.length - 11; k5++){
		// 						for(int k6 = k5 + 1; k6 < xorint.length - 10; k6++){
		// 							for(int k7 = k6 + 1; k7 < xorint.length - 9; k7++){
		// 								for(int k8 = k7 + 1; k8 < xorint.length - 8; k8++){
		// 									for(int k9 = k8 + 1; k9 < xorint.length - 7; k9++){
		// 										for(int k10 = k9 + 1; k10 < xorint.length - 6; k10++){
		// 											for(int k11 = k10 + 1; k11 < xorint.length - 5; k11++){
		// 												for(int k12 = k11 + 1; k12 < xorint.length - 4; k12++){
		// 													for(int k13 = k12 + 1; k13 < xorint.length - 3; k13++){
		// 														for(int k14 = k13 + 1; k14 < xorint.length - 2; k14++){
		// 															for(int k15 = k14 + 1; k15 < xorint.length - 1; k15++){
		// 																for(int k16 = k15 + 1; k16 < xorint.length; k16++){
				
		// 																	try{
		// 																		int[] eraseposition = {k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11, k12, k13, k14, k15, k16};
		// 																		int[] xorintcopy = new int[xorint.length];
																				
		// 																		for(int i = 0; i < xorint.length; i++){
		// 																			xorintcopy[i] = xorint[i];
		// 																		}

		// 																		int EC = decoder.erasedecodeWithECCount(xorintcopy, eraseposition, twos);


		// 																		int symbolcount = 0;
		// 																		boolean check = true;
		// 																		for(int i = 0; i < xorintcopy.length; i++){
		// 																			if(xorintcopy[i] != 0){
		// 																				symbolcount++;
		// 																			}
		// 																			if((i < datacodeword2.length)&&(xorintcopy[i] != xorint[i])){
		// 																				check = false;
		// 																			}
		// 																		}

		// 																		if((symbolcount == 17)&&(check == true)){

		// 																			resultlist.add(xorintcopy);

		// 																		}


		// 																	}catch(Exception e){
		// 																		// System.err.println(false);
		// 																	}	
		// 																}
		// 															}
		// 														}
		// 													}
		// 												}
		// 											}
		// 										}
		// 									}
		// 								}																			
		// 							}																
		// 						}
		// 					}
		// 				}
		// 			}
		// 		}
		// 	}	

		// 	// //リストをcsvに一旦出力
		// 	// // 出力ファイルの作成
		// 	// String csvfile1 = "resultlist.csv";
		// 	// FileWriter fw1 = new FileWriter(csvfile1, false);
		// 	// // PrintWriterクラスのオブジェクトを生成
		// 	// PrintWriter pw1 = new PrintWriter(new BufferedWriter(fw1));

		// 	// // データを書き込む
		// 	// for(int i = 0; i < resultlist.size(); i++){

		// 	// 	int[] codeword = resultlist.get(i);

		// 	// 	for(int j = 0; j < codeword.length; j++){
					
		// 	// 		pw1.print(codeword[j]);
		// 	// 		if(j < codeword.length - 1){
		// 	// 			pw1.print(",");
		// 	// 		}
		// 	// 	}
		// 	// 	pw1.println();

		// 	// }
		// 	// // ファイルを閉じる
		// 	// pw1.close();


		// 	// //保存したcsvからリストを生成。再度探索することなく以降の処理を実行するため	＊制作中
		// 	//---------------------------------------------------------------------------------

		// 	// //パスは適宜変更
		// 	// Path path = Paths.get("C:\\code\\module_paint_QR\\resultlist.csv");
		// 	// try {
		// 	//     // CSVファイルの読み込み
		// 	//     List<String> lines = Files.readAllLines(path, Charset.forName("Shift-JIS"));
		// 	//     for (int i = 1; i < lines.size(); i++) {
		// 	//         String[] data = lines.get(i).split(",");
		// 	//         if (data.length > 3) {
		// 	//             // 読み込んだCSVファイルの内容を出力
		// 	//             System.out.print(data[0] + ",");
		// 	//             System.out.print(data[1] + ",");
		// 	//             System.out.print(data[2] + ",");
		// 	//             System.out.println(data[3]);
		// 	//         }
		// 	//     }
		// 	// } catch (IOException e) {
		// 	//     System.out.println("ファイル読み込みに失敗");
		// 	// }
		// 	//---------------------------------------------------------------------------------



		// 	for(int i = 0; i < resultlist.size(); i++){
		// 		int count1bitsymbol = 0;
		// 		int count2bitsymbol = 0;
		// 		int[] codeword = resultlist.get(i);
				
		// 		for(int j = 0; j < codeword.length; j++){
		// 			if(analysis.count1((byte)(codeword[j])) == 1){
		// 				count1bitsymbol++;
		// 			}
		// 			if(analysis.count1((byte)(codeword[j])) == 2){
		// 				count2bitsymbol++;
		// 			}
		// 		}

		// 		int count1_2sum = count1bitsymbol + count2bitsymbol;
		// 		int changebitsum = count1bitsymbol + 2 * count2bitsymbol;
		// 		if(count1bitsymbol >= 9){
		// 			// resultlist9_11.add(codeword);

		// 			for(int j = 0; j < codeword.length; j++){
		// 				System.out.print(codeword[j] + " ");
		// 			}
		// 			System.out.println();

		// 			byte[] resultbyte = new byte[content1byte.length]; 

		// 			try {
		// 				for(int j = 0; j < codeword.length; j++){
		// 					resultbyte[j] = (byte)codeword[j];
		// 				}

		// 				byte[] content2_1byte = analysis.codewordplus2(resultbyte, content1byte);
		// 				analysis.genqr2(content2_1byte, "content2");


		// 				String pathname1 = "C:\\code\\module_paint_QR\\content1.png"; 
		// 				String pathname2 = "C:\\code\\module_paint_QR\\content2.png"; 

		// 				File file1 = new File(pathname1);
		// 				BufferedImage image1 = ImageIO.read(file1);

		// 				File file2 = new File(pathname2);
		// 				BufferedImage image2 = ImageIO.read(file2);

		// 				BufferedImage compmod = analysis.comp_module_pos1(image1, image2);
		// 				ImageIO.write(compmod , "png", new File("compmod.png"));

		// 				BufferedImage wtob = analysis.comp_module_pos2(image1, image2);
		// 				ImageIO.write(wtob , "png", new File("wtob.png"));

		// 				charbreak = true;
						
		// 			} catch (Exception e) {
		// 				System.err.println(false);
		// 			}
		// 		}

		// 		if(charbreak == true){
		// 			break;
		// 		}
		// 	}

		// 	if(charbreak == true){
		// 		break;
		// 	}
			
		// }


		// // // 出力ファイルの作成
		// // String csvfile2 = "resultlist9_11.csv";
		// // FileWriter fw2 = new FileWriter(csvfile2, false);
		// // // PrintWriterクラスのオブジェクトを生成
		// // PrintWriter pw2 = new PrintWriter(new BufferedWriter(fw2));

		// // // データを書き込む
		// // for(int i = 0; i < resultlist9_11.size(); i++){

		// // 	int[] codeword = resultlist9_11.get(i);

		// // 	for(int j = 0; j < codeword.length; j++){
				
		// // 		pw2.print(codeword[j]);
		// // 		if(j < codeword.length - 1){
		// // 			pw2.print(",");
		// // 		}
		// // 	}
		// // 	pw2.println();

		// // }
		// // // ファイルを閉じる
		// // pw2.close();



		// // byte[] resultbyte = new byte[content1byte.length]; 

		// // int forcount = 0;

		// // for(int i = 0; i < resultlist9_11.size(); i++){
		// // 	forcount++;
		// // 	System.out.println("tobyte:" + forcount + "/" + resultlist9_11.size());


		// // 	try {

		// // 		int[] result = resultlist9_11.get(i);
		// // 		for(int j = 0; j < result.length; j++){
		// // 			resultbyte[j] = (byte)result[j];
		// // 		}

		// // 		byte[] content2_1byte = analysis.codewordplus2(resultbyte, content1byte);
		// // 		analysis.genqr2(content2_1byte, "content2");


		// // 		String pathname1 = "C:\\code\\module_paint_QR\\content1.png"; 
		// // 		String pathname2 = "C:\\code\\module_paint_QR\\content2.png"; 

		// // 		File file1 = new File(pathname1);
		// // 		BufferedImage image1 = ImageIO.read(file1);

		// // 		File file2 = new File(pathname2);
		// // 		BufferedImage image2 = ImageIO.read(file2);

		// // 		BufferedImage compmod = analysis.comp_module_pos1(image1, image2);
		// // 		ImageIO.write(compmod , "png", new File("compmod.png"));

		// // 		BufferedImage wtob = analysis.comp_module_pos2(image1, image2);
		// // 		ImageIO.write(wtob , "png", new File("wtob.png"));

		// // 		int changesymbolnum = analysis.change_symbol_count(image1, image2);
		// // 		System.out.println("changesym" + i +":" + changesymbolnum);

		// // 		if(maxchange < changesymbolnum){
		// // 			maxchange = changesymbolnum;
		// // 		}

		// // 		if(changesymbolnum >= 9){
		// // 			charbreak = true;
		// // 			break;
		// // 		}

		// // 	} catch (Exception e) {
		// // 		System.err.println(false);
		// // 	}
		// // }

						
		

		// // System.out.println("maxchange:" + maxchange);

		

		//--------------------------------------------------------------------------------------------

		//誤りを入れて探索

		analysis.genqr1(content1, "content1");

		byte[] content1byte = analysis.codeword(content1);
		byte[] content1bytecopy = analysis.codeword(content1);

		// analysis.codewordBinary(content1byte);

		// for(int i = 0; i < content1byte.length; i++){
		// 	System.out.print(content1byte[i] + " ");
		// }
		// System.out.println();

		// System.out.println("content1len:" + content1byte.length);

		int[] content1int = new int[content1byte.length];

		int twos = 16;
		int count = 0;
		int EC = 0;
		boolean breakcheck = false;
		byte[] content2_1byte = new byte[content1byte.length];
		List<int[]> resultlist= new ArrayList <>();
		ReedSolomonDecoder decoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

		//誤りを入れる位置を選んで通常の復号を繰り返すことで符号語を探索

		for(int k1 = 9; k1 < content1byte.length - 8; k1++){
			for(int k2 = k1 + 1; k2 < content1byte.length - 7; k2++){
				for(int k3 = k2 + 1; k3 < content1byte.length - 6; k3++){
					for(int k4 = k3 + 1; k4 < content1byte.length - 5; k4++){
						for(int k5 = k4 + 1; k5 < content1byte.length - 4; k5++){
							for(int k6 = k5 + 1; k6 < content1byte.length - 3; k6++){
								for(int k7 = k6 + 1; k7 < content1byte.length - 2; k7++){
									for(int k8 = k7 + 1; k8 < content1byte.length - 1; k8++){
										for(int k9 = k8 + 1; k9 < content1byte.length; k9++){
											
											count++;

											System.out.println(count);


											int[] errorpos = {k1, k2, k3, k4, k5, k6, k7, k8, k9};


											for(int m1 = 0; m1 < 8; m1++){
												for(int m2 = 0; m2 < 8; m2++){
													for(int m3 = 0; m3 < 8; m3++){
														for(int m4 = 0; m4 < 8; m4++){
															for(int m5 = 0; m5 < 8; m5++){
																for(int m6 = 0; m6 < 8; m6++){
																	for(int m7 = 0; m7 < 8; m7++){
																		for(int m8 = 0; m8 < 8; m8++){
																			for(int m9 = 0; m9 < 8; m9++){
																

																				int[] errorbit = {m1, m2, m3, m4, m5, m6, m7, m8, m9}; 

																				content1bytecopy = analysis.codeword(content1);

																				for(int i = 0; i < errorpos.length; i++){
																					content1bytecopy[errorpos[i]] = analysis.bitchange(content1bytecopy[errorpos[i]], errorbit[i]);
																				}
																				content1int = analysis.tointarray(content1bytecopy);
																				try{

																					EC = decoder.decodeWithECCount(content1int, twos);

																					for(int i = 0; i < content1int.length; i++){
																						content2_1byte[i] = (byte)content1int[i];
																					}
																																				
																					analysis.genqr2(content2_1byte, "content2");
																					String pathname1 = "C:\\code\\module_paint_QR\\content1.png"; 
																					String pathname2 = "C:\\code\\module_paint_QR\\content2.png"; 

																					File file1 = new File(pathname1);
																					BufferedImage image1 = ImageIO.read(file1);

																					File file2 = new File(pathname2);
																					BufferedImage image2 = ImageIO.read(file2);

																				    int[] changesymmod = analysis.change_symbol_count(image1, image2);
																					if((changesymmod[0] == 9) && (changesymmod[1] == 9)){
																						breakcheck = true;
																					}
									
																													
																				}catch(Exception e){
																					// System.err.println(false);
																				}
																				if(breakcheck == true){
																					break;
																				}	
																				
																			}
																			if(breakcheck == true){
																				break;
																			}	
																		}
																		if(breakcheck == true){
																			break;
																		}	
																	}
																	if(breakcheck == true){
																		break;
																	}	
																}
																if(breakcheck == true){
																	break;
																}	
															}
															if(breakcheck == true){
																break;
															}	
														}
														if(breakcheck == true){
															break;
														}	
													}
													if(breakcheck == true){
														break;
													}	
												}
												if(breakcheck == true){
													break;
												}	
											}
											if(breakcheck == true){
												break;
											}	
											
											
										}
										if(breakcheck == true){
											break;
										}	
									}
									if(breakcheck == true){
										break;
									}																										
								}
								if(breakcheck == true){
									break;
								}																	
							}
							if(breakcheck == true){
								break;
							}	
						}
						if(breakcheck == true){
							break;
						}	
					}
					if(breakcheck == true){
						break;
					}	
				}
				if(breakcheck == true){
					break;
				}	
			}
			if(breakcheck == true){
				break;
			}		
		}



		//リストをcsvに一旦出力
		// 出力ファイルの作成
		String file = "resultlist.csv";
		FileWriter fw = new FileWriter(file, false);
		// PrintWriterクラスのオブジェクトを生成
		PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

		// データを書き込む


		int[] codeword = content1int;

		for(int j = 0; j < codeword.length; j++){
			
			pw.print(codeword[j]);
			if(j < codeword.length - 1){
				pw.print(",");
			}
		}
		pw.println();

		

		// ファイルを閉じる
		pw.close();
		


	

    }
}
