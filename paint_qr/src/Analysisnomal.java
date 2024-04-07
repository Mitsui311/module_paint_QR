import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.BitMatrixParser;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.detector.Detector;

public class Analysisnomal {

	int version = 2;
	ErrorCorrectionLevel eLevel = ErrorCorrectionLevel.M;
	GenericGF field = GenericGF.QR_CODE_FIELD_256;
	int errorcorrectnum = 3;

	int mask_pattern1 = 1;


	//文字列からQRコードを生成、バージョン誤り訂正レベルは適宜変更
	public void genqr1(String content, String filename) throws IOException, WriterException{
		String file = filename + ".png";
		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		//誤り訂正レベル
		hints.put(EncodeHintType.ERROR_CORRECTION, eLevel);
		//バージョン
		hints.put(EncodeHintType.QR_VERSION, version);
		hints.put(EncodeHintType.QR_MASK_PATTERN, mask_pattern1);
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 256, 256, hints);
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		ImageIO.write(image, "png", new File(file));

	}

	//符号語からQRコードを生成、バージョン誤り訂正レベルは適宜変更
	public void genqr2(byte[] codewords, String filename) throws IOException, WriterException{
		String file = filename + ".png";
		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		//誤り訂正レベル
		hints.put(EncodeHintType.ERROR_CORRECTION, eLevel);
		//バージョン
		hints.put(EncodeHintType.QR_VERSION, version);

		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encodecodeword(codewords, BarcodeFormat.QR_CODE, mask_pattern1, 256, 256, hints);
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		ImageIO.write(image, "png", new File(file));

	}


	//URLから符号語を生成
	public byte[] codeword(String content) throws WriterException{

		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		hints.put(EncodeHintType.ERROR_CORRECTION, eLevel);
		hints.put(EncodeHintType.QR_VERSION, version);
		QRCodeWriter writer = new QRCodeWriter();
		byte[] result = writer.encodebytearray(content, BarcodeFormat.QR_CODE, 256, 256, hints);

		return result;

	}

	//URLからデータコード語を生成
	public byte[] datacodeword(String content) throws WriterException{

		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		hints.put(EncodeHintType.ERROR_CORRECTION, eLevel);
		hints.put(EncodeHintType.QR_VERSION, version);
		QRCodeWriter writer = new QRCodeWriter();
		byte[] result = writer.encodedatacodeword(content, BarcodeFormat.QR_CODE, 256, 256, hints);

		return result;

	}

	//二つのURLをQRコードのエンコード過程で符号化して、その二つの符号語の和を求める
	public byte[] codewordplus(String content1, String content2) throws WriterException{
		
	
		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		hints.put(EncodeHintType.ERROR_CORRECTION, eLevel);
		hints.put(EncodeHintType.QR_VERSION, version);
		QRCodeWriter writer = new QRCodeWriter();
		byte[] encodebytearray1 = writer.encodebytearray(content1, BarcodeFormat.QR_CODE, 256, 256, hints);
		byte[] encodebytearray2 = writer.encodebytearray(content2, BarcodeFormat.QR_CODE, 256, 256, hints);
		byte[] result = new byte[encodebytearray1.length];

		for(int i = 0; i < result.length; i++){
			result[i] = (byte) (encodebytearray1[i] ^ encodebytearray2[i]);
		}

		return result;
		
	}

	//二つの符号語の和を求める、符号語を入力
	public byte[] codewordplus2(byte[] encodebytearray1, byte[] encodebytearray2) throws WriterException{
		byte[] result = new byte[encodebytearray1.length];

		for(int i = 0; i < result.length; i++){
			result[i] = (byte) (encodebytearray1[i] ^ encodebytearray2[i]);
		}

		return result;
		
	}

    // 二つのURLをQRコードのエンコード過程で符号化して、その二つの符号語間の距離を求める
    public int calcdistance1(String content1, String content2) throws WriterException {
        int d = 0;

		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		hints.put(EncodeHintType.ERROR_CORRECTION, eLevel);
		hints.put(EncodeHintType.QR_VERSION, version);
		QRCodeWriter writer = new QRCodeWriter();
		byte[] encodebytearray1 = writer.encodebytearray(content1, BarcodeFormat.QR_CODE, 256, 256, hints);
		byte[] encodebytearray2 = writer.encodebytearray(content2, BarcodeFormat.QR_CODE, 256, 256, hints);
		byte[] xortest = new byte[encodebytearray1.length];

		for(int i = 0; i < xortest.length; i++){
			xortest[i] = (byte) (encodebytearray1[i] ^ encodebytearray2[i]);
		}

		for(int i = 0; i < encodebytearray1.length; i++){
			if(encodebytearray1[i] != encodebytearray2[i]){
				d++;
			}
		}

        return d;
    }

	 // 二つの符号語間の距離を求める
	 public int calcdistance2(byte[] encodebytearray1, byte[] encodebytearray2) throws WriterException {
        int d = 0;

		for(int i = 0; i < encodebytearray1.length; i++){
			if(encodebytearray1[i] != encodebytearray2[i]){
				d++;
			}
		}

        return d;
    }

	// content1とcontent2を比較して異なるモジュールを抽出(画像として比較している)
    public BufferedImage comp_module_pos1(BufferedImage content1, BufferedImage content2) throws NotFoundException, FormatException{

        int box_size = 8;
        int margin = 2;

        LuminanceSource source_content1 = new BufferedImageLuminanceSource(content1);
        Binarizer binarizer_content1 = new HybridBinarizer(source_content1);
        BinaryBitmap bitmap_content1 = new BinaryBitmap(binarizer_content1);
        
        LuminanceSource source_content2 = new BufferedImageLuminanceSource(content2);
        Binarizer binarizer_content2 = new HybridBinarizer(source_content2);
        BinaryBitmap bitmap_content2 = new BinaryBitmap(binarizer_content2);
        DetectorResult detectorResult_content1 = new Detector(bitmap_content1.getBlackMatrix()).detect();
        BitMatrix bitmatrix_content1 = detectorResult_content1.getBits();
        DetectorResult detectorResult_content2 = new Detector(bitmap_content2.getBlackMatrix()).detect();
        BitMatrix bitmatrix_content2 = detectorResult_content2.getBits();
        BitMatrix bitmatrix_error = new BitMatrix(((bitmatrix_content1.getHeight())) * box_size + 2 * margin * box_size);
        for(int i = 0; i < bitmatrix_content2.getWidth(); i++){
            for(int j = 0; j < bitmatrix_content2.getHeight(); j++){
                if(bitmatrix_content2.get(i, j) != bitmatrix_content1.get(i, j)){
                    for(int i1 = i*box_size + margin * box_size; i1 < i*box_size + margin * box_size + box_size; i1++){
                        for(int j1 = j*box_size + margin * box_size; j1 < j*box_size + margin * box_size + box_size; j1++){
                            bitmatrix_error.set(i1, j1);
                        }
                    }   
                }
            }
        }

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitmatrix_error);

        return image;

    }

	// content1とcontent2を比較して白から黒のモジュールを抽出(画像として比較している)
    public BufferedImage comp_module_pos2(BufferedImage content1, BufferedImage content2) throws NotFoundException, FormatException{

        int box_size = 8;
        int margin = 2;

        LuminanceSource source_content1 = new BufferedImageLuminanceSource(content1);
        Binarizer binarizer_content1 = new HybridBinarizer(source_content1);
        BinaryBitmap bitmap_content1 = new BinaryBitmap(binarizer_content1);
        
        LuminanceSource source_content2 = new BufferedImageLuminanceSource(content2);
        Binarizer binarizer_content2 = new HybridBinarizer(source_content2);
        BinaryBitmap bitmap_content2 = new BinaryBitmap(binarizer_content2);
        DetectorResult detectorResult_content1 = new Detector(bitmap_content1.getBlackMatrix()).detect();
        BitMatrix bitmatrix_content1 = detectorResult_content1.getBits();
        DetectorResult detectorResult_content2 = new Detector(bitmap_content2.getBlackMatrix()).detect();
        BitMatrix bitmatrix_content2 = detectorResult_content2.getBits();
        BitMatrix bitmatrix_error = new BitMatrix(((bitmatrix_content1.getHeight())) * box_size + 2 * margin * box_size);
        for(int i = 0; i < bitmatrix_content2.getWidth(); i++){
            for(int j = 0; j < bitmatrix_content2.getHeight(); j++){
                if((bitmatrix_content2.get(i, j) != bitmatrix_content1.get(i, j))&&(bitmatrix_content1.get(i, j) == false)){
                    for(int i1 = i*box_size + margin * box_size; i1 < i*box_size + margin * box_size + box_size; i1++){
                        for(int j1 = j*box_size + margin * box_size; j1 < j*box_size + margin * box_size + box_size; j1++){
                            bitmatrix_error.set(i1, j1);
                        }
                    }   
                }
            }
        }

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitmatrix_error);

        return image;

    }

	 // qr1とqr2のシンボルがいくつ間違えているかを数える
    public int error_symbol_count(BufferedImage qr1, BufferedImage qr2) throws NotFoundException, FormatException{
        int errorcount = 0;
        boolean errorSymbol = false;

        LuminanceSource source_qr1 = new BufferedImageLuminanceSource(qr1);
        Binarizer binarizer_qr1 = new HybridBinarizer(source_qr1);
        BinaryBitmap bitmap_qr1 = new BinaryBitmap(binarizer_qr1);
        
        LuminanceSource source_qr2 = new BufferedImageLuminanceSource(qr2);
        Binarizer binarizer_qr2 = new HybridBinarizer(source_qr2);
        BinaryBitmap bitmap_qr2 = new BinaryBitmap(binarizer_qr2);

        DetectorResult detectorResult_qr1 = new Detector(bitmap_qr1.getBlackMatrix()).detect();
        BitMatrix bitmatrix_qr1 = detectorResult_qr1.getBits();
        DetectorResult detectorResult_qr2 = new Detector(bitmap_qr2.getBlackMatrix()).detect();
        BitMatrix bitmatrix_qr2 = detectorResult_qr2.getBits();

        BitMatrixParser parser_qr1 = new BitMatrixParser(bitmatrix_qr1);

        int[][][] Symbol = parser_qr1.ModuleToSymbol();

        for(int i = 0; i < Symbol.length; i++){
            for(int j = 0; j < Symbol[i].length; j++){
                if(bitmatrix_qr2.get(Symbol[i][j][0], Symbol[i][j][1]) != bitmatrix_qr1.get(Symbol[i][j][0], Symbol[i][j][1])){
                    errorSymbol = true;
                }
            }
            if(errorSymbol){
                errorcount++;
                errorSymbol = false;
            }
        }
        return errorcount;
    }

	//2つのQRコードの異なるシンボルの内、遷移前のモジュールを塗りつぶす(白から黒にする)と
	//シンボルが遷移後のシンボルになるシンボル数を数える
	public int change_symbol_count(BufferedImage qr1, BufferedImage qr2) throws NotFoundException, FormatException{
		int changecount = 0;
		int notchange = 0;
		int errorcount = 0;

		//遷移可能ならtrueのまま、不可ならfalseになる
		boolean changesymbol = true;

		boolean errorSymbol = false;

		LuminanceSource source_qr1 = new BufferedImageLuminanceSource(qr1);
        Binarizer binarizer_qr1 = new HybridBinarizer(source_qr1);
        BinaryBitmap bitmap_qr1 = new BinaryBitmap(binarizer_qr1);
        
        LuminanceSource source_qr2 = new BufferedImageLuminanceSource(qr2);
        Binarizer binarizer_qr2 = new HybridBinarizer(source_qr2);
        BinaryBitmap bitmap_qr2 = new BinaryBitmap(binarizer_qr2);

        DetectorResult detectorResult_qr1 = new Detector(bitmap_qr1.getBlackMatrix()).detect();
        BitMatrix bitmatrix_qr1 = detectorResult_qr1.getBits();
        DetectorResult detectorResult_qr2 = new Detector(bitmap_qr2.getBlackMatrix()).detect();
        BitMatrix bitmatrix_qr2 = detectorResult_qr2.getBits();

		BitMatrixParser parser_qr1 = new BitMatrixParser(bitmatrix_qr1);

        int[][][] Symbol = parser_qr1.ModuleToSymbol();

        for(int i = 0; i < Symbol.length; i++){
            for(int j = 0; j < Symbol[i].length; j++){
                if(bitmatrix_qr2.get(Symbol[i][j][0], Symbol[i][j][1]) != bitmatrix_qr1.get(Symbol[i][j][0], Symbol[i][j][1])){
                    errorSymbol = true;
					if(bitmatrix_qr1.get(Symbol[i][j][0], Symbol[i][j][1])){
						changesymbol = false;
					}
                }
				
            }
			if(errorSymbol){
                errorcount++;
            }
			if(!changesymbol){
				notchange++;
			}
			errorSymbol = false;
			changesymbol = true;
        }
		changecount = errorcount - notchange;

		return changecount;
	}


	//二つの符号語を比較してbit反転が多いシンボルの場所を探し、符号語1のシンボルを符号語2のシンボルに置き換える
	//置き換える数はt (= errorcorrectnum)
	public byte[] changesymbol(byte[] codeword1, byte[] codeword2) throws WriterException{

		byte[] xor = codewordplus2(codeword1, codeword2);
		byte[] xorcopy = new byte[xor.length];

		for(int i = 0; i < xor.length; i++){
			xorcopy[i] = xor[i];
		}
		
		for(int i = 0; i < xorcopy.length-1; i++){
            for(int j = i+1; j < xorcopy.length; j++){
                if(count1(xorcopy[j]) > count1(xorcopy[i])){
                    byte temp = xorcopy[i];
                    xorcopy[i] = xorcopy[j];
                    xorcopy[j] = temp;
                }
            }
        }
		for(int i = 0; i < errorcorrectnum; i++){
			for(int j = 0; j < codeword1.length; j++){
				if(xor[j] == xorcopy[i]){
					codeword1[j] = codeword2[j];
				}
			}
		}
		return codeword1;
	}


	//符号語を2進数で表示
	public void codewordBinary(byte[] codeword){
		for(int i = 0; i < codeword.length; i++){
			System.out.println(toBinaryString2(codeword[i]));
		}
	}

	//byte型を二進数にした時の1の数をカウントする
	public int count1(byte b){
		int result = Integer.bitCount(Byte.toUnsignedInt(b));
		return result;
	}

	//byte型の配列をint型の配列に変換
	public int[] tointarray(byte[] bytearray){
		int[] result = new int[bytearray.length];
		for(int i = 0; i < result.length; i++){
			result[i] = Byte.toUnsignedInt(bytearray[i]);
		}

		return result;
	}
	

	//byte型をString型で2進数に変更
	public static String toBinaryString2(byte b) {
		int[] i = new int[8];
		StringBuffer bs = new StringBuffer();
		i[0] = (b & 0b10000000) >>> 7;
		i[1] = (b & 0b01000000) >>> 6;
		i[2] = (b & 0b00100000) >>> 5;
		i[3] = (b & 0b00010000) >>> 4;
		i[4] = (b & 0b00001000) >>> 3;
		i[5] = (b & 0b00000100) >>> 2;
		i[6] = (b & 0b00000010) >>> 1;
		i[7] = (b & 0b00000001) >>> 0;
		for (int j = 0; j < 8; j++) {
			bs.append(i[j]);
		}
		return bs.toString();
	}
    
}
