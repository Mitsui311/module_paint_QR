import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


//いろいろ実験するためのクラス
public class Apptest {
    public static void main(String[] args) throws Exception {

        Analysisnomal analysis = new Analysisnomal();

        String pathname1 = "C:\\code\\module_paint_QR\\content1.png"; 
        String pathname2 = "C:\\code\\module_paint_QR\\content2.png"; 

        File file1 = new File(pathname1);
        BufferedImage image1 = ImageIO.read(file1);

        File file2 = new File(pathname2);
        BufferedImage image2 = ImageIO.read(file2);
        BufferedImage compmod = analysis.comp_module_pos1(image1, image2);
        ImageIO.write(compmod , "png", new File("compmod.png"));

        BufferedImage wtob = analysis.comp_module_pos2(image1, image2);
        ImageIO.write(wtob , "png", new File("wtob.png"));


        int[] changesymmod = analysis.change_symbol_count(image1, image2);
        System.out.println("changesym" + ":" + analysis.error_symbol_count(image1, image2));
        System.out.println("whitechangesym" +":" + changesymmod[0]);
        System.out.println("paintmod" +":" + changesymmod[1]);
    }
}
