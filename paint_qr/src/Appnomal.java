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
		// boolean charbreak = false;
		// for(char c1 = 'b'; c1 <= 'z'; c1++){
		// 	// for(char c2 = '0'; c2 <= 'z'; c2++){
		// 	// 	for(char c3 = '0'; c3 <= 'z'; c3++){
		// 	// 		for(char c4 = '0'; c4 <= 'z'; c4++){
		// 	// 			for(char c5 = '0'; c5 <= 'z'; c5++){
		// 	// 				for(char c6 = '0'; c6 <= 'z'; c6++){
								

		// 						analysis.genqr1(content1, "content1");
								
		// 						String content2 = "http://" + String.valueOf(c1) +"lmond.jp";

		// 						System.out.println(content2);

		// 						int d1 = analysis.calcdistance1(content1, content2);
		// 						// System.out.println("d1:" + d1);


		// 						byte[] content1byte = analysis.codeword(content1);
		// 						byte[] datacodeword1 = analysis.datacodeword(content1);
		// 						byte[] datacodeword1pad = new byte[content1byte.length]; 

		// 						System.out.println(datacodeword1.length);
								
		// 						for(int i = 0; i < datacodeword1pad.length; i++){
		// 							if(i < datacodeword1.length){
		// 								datacodeword1pad[i] = datacodeword1[i];
		// 							}else{
		// 								datacodeword1pad[i] = 0;
		// 							}
		// 						}
								
		// 						for(int i = 0; i < datacodeword1pad.length; i++){
		// 							System.out.print(datacodeword1pad[i] + " ");
		// 						}
		// 						System.out.println();

		// 						byte[] content2byte = analysis.codeword(content2);
		// 						byte[] datacodeword2 = analysis.datacodeword(content2);
		// 						byte[] datacodeword2pad = new byte[content2byte.length]; 

		// 						System.out.println(datacodeword2.length);
								
		// 						for(int i = 0; i < datacodeword2pad.length; i++){
		// 							if(i < datacodeword2.length){
		// 								datacodeword2pad[i] = datacodeword2[i];
		// 							}else{
		// 								datacodeword2pad[i] = 0;
		// 							}
		// 						}

		// 						for(int i = 0; i < datacodeword2pad.length; i++){
		// 							System.out.print(datacodeword2pad[i] + " ");
		// 						}
		// 						System.out.println();

		// 						byte[] xor = analysis.codewordplus2(datacodeword1pad, datacodeword2pad);
		// 						int[] xorint = analysis.tointarray(xor);

		// 						for(int i = 0; i < xorint.length; i++){
		// 							System.out.print(xorint[i] + " ");
		// 						}
		// 						System.out.println();

		// 						//消失誤り訂正を利用して、消失点を総当たりしながら符号語を生成
		// 						int twos = 16;
		// 						int count = 0;
		// 						List<int[]> resultlist= new ArrayList <>();
		// 						ReedSolomonDecoder decoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

		//						// 埋め草コード後から符号語の終わりまでで消失点を選ぶ、消失点の数はループの数に等しい

		// 						for(int k1 = datacodeword2.length; k1 < xorint.length - 15; k1++){
		// 							for(int k2 = k1 + 1; k2 < xorint.length - 14; k2++){
		// 								for(int k3 = k2 + 1; k3 < xorint.length - 13; k3++){
		// 									for(int k4 = k3 + 1; k4 < xorint.length - 12; k4++){
		// 										for(int k5 = k4 + 1; k5 < xorint.length - 11; k5++){
		// 											for(int k6 = k5 + 1; k6 < xorint.length - 10; k6++){
		// 												for(int k7 = k6 + 1; k7 < xorint.length - 9; k7++){
		// 													for(int k8 = k7 + 1; k8 < xorint.length - 8; k8++){
		// 														for(int k9 = k8 + 1; k9 < xorint.length - 7; k9++){
		// 															for(int k10 = k9 + 1; k10 < xorint.length - 6; k10++){
		// 																for(int k11 = k10 + 1; k11 < xorint.length - 5; k11++){
		// 																	for(int k12 = k11 + 1; k12 < xorint.length - 4; k12++){
		// 																		for(int k13 = k12 + 1; k13 < xorint.length - 3; k13++){
		// 																			for(int k14 = k13 + 1; k14 < xorint.length - 2; k14++){
		// 																				for(int k15 = k14 + 1; k15 < xorint.length - 1; k15++){
		// 																					for(int k16 = k15 + 1; k16 < xorint.length; k16++){
		// 																						count++;
		// 																						System.out.println(count);
															
		// 																						try{
		// 																							int[] eraseposition = {k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11, k12, k13, k14, k15, k16};
		// 																							int[] xorintcopy = new int[xorint.length];
																									
		// 																							for(int i = 0; i < xorint.length; i++){
		// 																								xorintcopy[i] = xorint[i];
		// 																							}

		// 																							int EC = decoder.erasedecodeWithECCount(xorintcopy, eraseposition, twos);


		// 																							int symbolcount = 0;
		// 																							boolean check = true;
		// 																							for(int i = 0; i < xorintcopy.length; i++){
		// 																								if(xorintcopy[i] != 0){
		// 																									symbolcount++;
		// 																								}
		// 																								if((i < datacodeword2.length)&&(xorintcopy[i] != xorint[i])){
		// 																									check = false;
		// 																								}
		// 																							}
		// 																							if((symbolcount == 17)&&(check == true)){

		// 																								resultlist.add(xorintcopy);

		// 																							}


		// 																						}catch(Exception e){
		// 																							System.err.println(false);
		// 																						}	
		// 																					}
		// 																				}
		// 																			}
		// 																		}
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


		// 						//同じ符号語が重複してリストに入っているので重複した符号語を消去したリストを生成
		// 						List<int[]> resultlist_nodup = new ArrayList <>();

		// 						// for(int i = 0; i < resultlist.size(); i++){

		// 						// 	if(i == 0){
		// 						// 		resultlist_nodup.add(resultlist.get(i));
		// 						// 	}else{
		// 						// 		boolean dupcheck = true;
		// 						// 		for(int j = 0; j < resultlist_nodup.size(); j++){
		// 						// 			if(Arrays.equals(resultlist.get(i), resultlist_nodup.get(j)) == true){
		// 						// 				dupcheck = false;
		// 						// 			}
		// 						// 		}

		// 						// 		if(dupcheck == true){
		// 						// 			resultlist_nodup.add(resultlist.get(i));
		// 						// 		}
		// 						// 	}
		// 						// }

		// 						System.out.println("resultnum:" + resultlist.size());
								
								
		// 						// for(int i = 0; i < resultlist.size(); i++){
		// 						// 	System.out.print("resultlist" + i + ":");
		// 						// 	for(int j = 0; j < resultlist.get(i).length; j++){
		// 						// 		int[] intarray = resultlist.get(i);
		// 						// 		System.out.print(intarray[j] + " ");
		// 						// 	}
		// 						// 	System.out.println();
		// 						// }
		// 						// System.out.println();


		// 						byte[] resultbyte = new byte[content1byte.length]; 

		// 						int forcount = 0;
								

								

		// 						for(int i = 0; i < resultlist.size(); i++){
		// 							forcount++;
		// 							System.out.println("tobyte:" + forcount + "/" + resultlist.size());


		// 							try {

		// 								int[] result = resultlist.get(i);
		// 								for(int j = 0; j < result.length; j++){
		// 									resultbyte[j] = (byte)result[j];
		// 								}

		// 								byte[] content2_1byte = analysis.codewordplus2(resultbyte, content1byte);
		// 								analysis.genqr2(content2_1byte, "content2");


		// 								String pathname1 = "C:\\code\\module_paint_QR\\content1.png"; 
		// 								String pathname2 = "C:\\code\\module_paint_QR\\content2.png"; 

		// 								File file1 = new File(pathname1);
		// 								BufferedImage image1 = ImageIO.read(file1);

		// 								File file2 = new File(pathname2);
		// 								BufferedImage image2 = ImageIO.read(file2);

		// 								BufferedImage compmod = analysis.comp_module_pos1(image1, image2);
		// 								ImageIO.write(compmod , "png", new File("compmod.png"));
								
		// 								BufferedImage wtob = analysis.comp_module_pos2(image1, image2);
		// 								ImageIO.write(wtob , "png", new File("wtob.png"));

		// 								int changesymbolnum = analysis.change_symbol_count(image1, image2);
		// 								System.out.println("changesym" + i +":" + changesymbolnum);

		// 								if(maxchange < changesymbolnum){
		// 									maxchange = changesymbolnum;
		// 								}

		// 								if(changesymbolnum >= 9){
		// 									charbreak = true;
		// 									break;
		// 								}

		// 							} catch (Exception e) {
		// 								System.err.println(false);
		// 							}
		// 						}
								
		// 						if(charbreak){
		// 							break;
		// 						}
		// 	// 				}
		// 	// 				if(charbreak){
		// 	// 					break;
		// 	// 				}
		// 	// 			}
		// 	// 			if(charbreak){
		// 	// 				break;
		// 	// 			}
		// 	// 		}
		// 	// 		if(charbreak){
		// 	// 			break;
		// 	// 		}
		// 	// 	}
		// 	// 	if(charbreak){
		// 	// 		break;
		// 	// 	}
		// 	// }
		// 	// if(charbreak){
		// 	// 	break;
		// 	// }
	    // }
		

		// System.out.println("maxchange:" + maxchange);

		

		//--------------------------------------------------------------------------------------------

		//誤りを入れて探索

		byte[] content1byte = analysis.codeword(content1);
		byte[] datacodeword1 = analysis.datacodeword(content1);

		// System.out.println("content1len:" + content1byte.length);
		// System.out.println("datacodeword1len:" + datacodeword1.length);

		// for(int i = 0; i < content1byte.length; i++){
		// 	System.out.print(content1byte[i] + " ");
		// }
		// System.out.println();

		// for(int i = 0; i < datacodeword1.length; i++){
		// 	System.out.print(datacodeword1[i] + " ");
		// }
		// System.out.println();

		int[] content1int = analysis.tointarray(content1byte);

		// System.out.println("content1intlen:" + content1int.length);

		// for(int i = 0; i < content1int.length; i++){
		// 	System.out.print(content1int[i] + " ");
		// }
		// System.out.println();


		int twos = 16;
		int count = 0;
		int addcount = 0;
		List<int[]> resultlist= new ArrayList <>();
		ReedSolomonDecoder decoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

		//http://~ からスタート。最初の消失点は終端コードまで、それ以降の消失点は符号語の終わりまでから選ぶ

		// int k1 = 8;
		// for(int k2 = datacodeword1.length; k2 < content1int.length - 14; k2++){
		// 	for(int k3 = k2 + 1; k3 < content1int.length - 13; k3++){
		// 		for(int k4 = k3 + 1; k4 < content1int.length - 12; k4++){
		// 			for(int k5 = k4 + 1; k5 < content1int.length - 11; k5++){
		// 				for(int k6 = k5 + 1; k6 < content1int.length - 10; k6++){
		// 					for(int k7 = k6 + 1; k7 < content1int.length - 9; k7++){
		// 						for(int k8 = k7 + 1; k8 < content1int.length - 8; k8++){
		// 							for(int k9 = k8 + 1; k9 < content1int.length - 7; k9++){
		// 								for(int k10 = k9 + 1; k10 < content1int.length - 6; k10++){
		// 									for(int k11 = k10 + 1; k11 < content1int.length - 5; k11++){
		// 										for(int k12 = k11 + 1; k12 < content1int.length - 4; k12++){
		// 											for(int k13 = k12 + 1; k13 < content1int.length - 3; k13++){
		// 												for(int k14 = k13 + 1; k14 < content1int.length - 2; k14++){
		// 													for(int k15 = k14 + 1; k15 < content1int.length - 1; k15++){
		// 														for(int k16 = k15 + 1; k16 < content1int.length; k16++){
		// 															count++;
		// 															System.out.println(count);
								
		// 															try{
		// 																//消失点
		// 																int[] eraseposition = {k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11, k12, k13, k14, k15, k16};
		// 																int[] content1intcopy = new int[content1int.length];
																		
		// 																for(int i = 0; i < content1int.length; i++){
		// 																	content1intcopy[i] = content1int[i];
		// 																}

		// 																int EC = decoder.erasedecodeWithECCount(content1intcopy, eraseposition, twos);


		// 																int symbolcount = 0;
		// 																boolean dupcheck = true;
		// 																for(int i = 0; i < content1intcopy.length; i++){
		// 																	if(content1intcopy[i] != content1int[i]){
		// 																		symbolcount++;
		// 																	}
		// 																}

		// 																if(resultlist.size() != 0){

		// 																	for(int i = 0; i < resultlist.size(); i++){
		// 																		if(Arrays.equals(resultlist.get(i), content1intcopy) == true){
		// 																			dupcheck = false;
		// 																		}
		// 																	}

		// 																}
		// 																if((symbolcount == 17) && (dupcheck == true)){

		// 																	resultlist.add(content1intcopy);

		// 																	addcount++;
		// 																	System.out.println(addcount);

		// 																}


		// 															}catch(Exception e){
		// 																System.err.println(false);
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
		// }	


		// //リストをcsvに一旦出力
		// // 出力ファイルの作成
		// String file = "resultlist.csv";
		// FileWriter fw = new FileWriter(file, false);
		// // PrintWriterクラスのオブジェクトを生成
		// PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

		// // データを書き込む
		// for(int i = 0; i < resultlist.size(); i++){
		// 	pw.print(i);
		// 	pw.print(":");

		// 	int[] codeword = resultlist.get(i);

		// 	for(int j = 0; j < codeword.length; j++){
				
		// 		pw.print(codeword[j]);
		// 		if(j < codeword.length - 1){
		// 			pw.print(",");
		// 		}
		// 	}
		// 	pw.println();

		// }
		

		// // ファイルを閉じる
		// pw.close();
		


	

    }
}
