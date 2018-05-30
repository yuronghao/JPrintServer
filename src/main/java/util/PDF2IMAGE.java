package util;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDF2IMAGE {
    public static void main(String[] args) {
        pdf2Image("D:/1.pdf", "D:/pdf", 300);
    }

    /***
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath pdf完整路径
     * @param dstImgFolder 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @return
     */
    public static void pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
        File file = new File(PdfFilePath);
        PDDocument pdDocument = null;
        PdfReader reader = null;
        try {
            String imgPDFPath = file.getParent();
            int dot = file.getName().lastIndexOf('.');
            String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
            String imgFolderPath = null;
            if (dstImgFolder.equals("")) {
                imgFolderPath = imgPDFPath + File.separator;// 获取图片存放的文件夹路径
            } else {
                imgFolderPath = dstImgFolder + File.separator;
            }

            if (createDirectory(imgFolderPath)) {

                pdDocument = PDDocument.load(file);
                PDFRenderer renderer  = new PDFRenderer(pdDocument);
                /* dpi越大转换后越清晰，相对转换速度越慢 */
                reader = new PdfReader(PdfFilePath);
                int pages = reader.getNumberOfPages();
                StringBuffer imgFilePath = new StringBuffer();
                String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
                imgFilePath.append(imgFilePathPrefix);
                imgFilePath.append(".JPG");
                File dstFile = new File(imgFilePath.toString());
                BufferedImage image = renderer.renderImageWithDPI(0, dpi);
                ImageIO.write(image, "JPG", dstFile);

//                for (int i = 0; i < pages; i++) {
//                    String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
//                    imgFilePath = new StringBuffer();
//                    imgFilePath.append(imgFilePathPrefix);
//                    imgFilePath.append("_");
//                    imgFilePath.append(String.valueOf(i + 1));
//                    imgFilePath.append(".JPG");
//                    File dstFile = new File(imgFilePath.toString());
//                    BufferedImage image = renderer.renderImageWithDPI(i, dpi);
//                    ImageIO.write(image, "JPG", dstFile);
//                }
                System.out.println("PDF文档转JPG图片成功！");
            } else {
                System.out.println("PDF文档转JPG图片失败：" + "创建" + imgFolderPath + "失败");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(pdDocument != null){
                    pdDocument.close();
                }
                if(reader != null){
                    reader.close();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean createDirectory(String folder) {
        File dir = new File(folder);
        if (dir.exists()) {
            return true;
        } else {
            return dir.mkdirs();
        }
    }
}
