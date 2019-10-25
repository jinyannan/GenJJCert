package com.studio.cloudstone;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GenCert {
    /**
     * 获取水印地址
     *
     * @return
     */
    private static String getWaterMarkPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("watermark/water.png").getPath();
        return path;
    }

    /**
     * 图片格式：JPG
     */
    private static final String PICTRUE_FORMATE_JPG = "png";
    private static final String TARGETIMGPATH = "C:\\Users\\lwz\\Desktop\\temp.png";
    private static final String DISTPATH = "C:\\Users\\lwz\\Desktop\\temp\\temp.png";
    private static final String WATERIMGPATH = "C:\\Users\\0\\Desktop\\img\\water.png";

    private GenCert() {
    }

    /**
     * 添加图片水印
     * @param //targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param //waterImg 水印图片路径，如：C://myPictrue//logo.png
     * @param x 水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y 水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha 透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
//    public final static void pressImage(String filePath, int x, int y, float alpha) {
//
//        String imgType = filePath.substring(filePath.lastIndexOf(".")+1,filePath.length());
//        try {
//            File targetFile = new File(filePath);
//            Image image = ImageIO.read(targetFile);
//            int width = image.getWidth(null);
//            int height = image.getHeight(null);
//            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g = bufferedImage.createGraphics();
//            g.drawImage(image, 0, 0, width, height, null);
//
//            File file = new File(getWaterMarkPath());
//            if(file == null){
//                return;
//            }
//            Image waterImage = ImageIO.read(file);	// 水印文件
//            int width_1 = waterImage.getWidth(null);
//            int height_1 = waterImage.getHeight(null);
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
//
//            int widthDiff = width - width_1;
//            int heightDiff = height - height_1;
//            if(x < 0){
//                x = widthDiff / 2;
//            }else if(x > widthDiff){
//                x = widthDiff;
//            }
//            if(y < 0){
//                y = heightDiff / 2;
//            }else if(y > heightDiff){
//                y = heightDiff;
//            }
//            g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
//            g.dispose();
//            ImageIO.write(bufferedImage, imgType, targetFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 添加文字水印
     *
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText 水印文字， 如：中国证券网
     * @param fontName  字体名称，	如：宋体
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize  字体大小，单位为像素
     * @param color     字体颜色
     * @param x         水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y         水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha     透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        try {
            File file = new File(targetImg);

            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int width_1 = fontSize * getLength(pressText);
            int height_1 = fontSize;
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if (x < 0) {
                x = widthDiff / 2;
            } else if (x > widthDiff) {
                x = widthDiff;
            }
            if (y < 0) {
                y = heightDiff / 2;
            } else if (y > heightDiff) {
                y = heightDiff;
            }

            g.drawString(pressText, x, y + height_1);
            g.dispose();
            ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     *
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }

    /**
     * 图片缩放
     *
     * @param filePath 图片路径
     * @param ratio    指定比例缩放图片
     */
    public static void resize(String filePath, Double ratio) {
        try {
            File file = new File(filePath);
            BufferedImage bi = ImageIO.read(file);
            int fileWidth = bi.getWidth();
            int fileHeight = bi.getHeight();
            resize(filePath, (int) (fileWidth * ratio), (int) (fileHeight * ratio), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片缩放
     *
     * @param filePath 图片路径
     * @param width    指定宽度缩放图片
     * @param height   指定高度缩放图片
     */
    public static void resize(String filePath, int width, int height) {
        resize(filePath, width, height, false);
    }

    /**
     * 图片缩放
     *
     * @param filePath 图片路径
     * @param height   高度
     * @param width    宽度
     * @param bb       比例不对时是否需要补白
     */
    public static void resize(String filePath, int width, int height, boolean bb) {
        String formatName = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        try {
            double ratio = 0d; //缩放比例
            File f = new File(filePath);
            BufferedImage bi = ImageIO.read(f);
            Image imageItem = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            //计算比例
            if ((bi.getHeight() >= height) || (bi.getWidth() >= width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                imageItem = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == imageItem.getWidth(null)) {
                    g.drawImage(imageItem, 0, (height - imageItem.getHeight(null)) / 2, imageItem.getWidth(null), imageItem.getHeight(null), Color.white, null);
                } else {
                    g.drawImage(imageItem, (width - imageItem.getWidth(null)) / 2, 0, imageItem.getWidth(null), imageItem.getHeight(null), Color.white, null);
                }
                g.dispose();
                imageItem = image;
            }
            ImageIO.write((BufferedImage) imageItem, formatName, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        String carId = args[0];
        String carOwnerName = args[1];
        DateTime applyBeginDate = DateTime.parse(args[2], DateTimeFormat.forPattern("yyyy-MM-dd"));
        DateTime applyEndDate = null;
        if (args.length == 4){
            applyEndDate = DateTime.parse(args[3], DateTimeFormat.forPattern("yyyy-MM-dd"));
        }

        ArrayList<DateTime> applyDateList = genApplyDateList(applyBeginDate,applyEndDate);
        for (DateTime applyDate : applyDateList) {
            CertInfo certInfo = new CertInfo();
            certInfo.setCarId(carId);
            certInfo.setApplyDate(applyDate);
            DateTime certBeginDate = applyDate.plusDays(1);
            DateTime certEndDate = applyDate.plusDays(8);
            certInfo.setBeginDate(certBeginDate);
            certInfo.setEndDate(certEndDate);
            certInfo.setCarOwnerName(carOwnerName);
            certInfo.setCertPicName( "temp.jpg");
            certInfo.setNo(getNo(applyBeginDate));
            pressText(certInfo);
        }
    }

    public static ArrayList<DateTime> genApplyDateList(DateTime applyBeginDate, DateTime applyEndDate){
        ArrayList<DateTime> applyDateList = new ArrayList<DateTime>();
        if (applyEndDate == null) {
            applyDateList.add(applyBeginDate);
            return applyDateList;
        }
        while(applyBeginDate.isBefore(applyEndDate)){
            applyDateList.add(applyBeginDate);
            applyBeginDate = applyBeginDate.plusDays(7);
        }
        return applyDateList;
    }

    public static void pressText(CertInfo certInfo){
        String tempPath = GenCert.class.getResource("/").getPath();
        String targetFileName = certInfo.getCarId() + "-" + certInfo.getApplyDate().toString("yyMMdd") + ".jpg";
        File tempFile = new File(tempPath + certInfo.getCertPicName());
        File targetFile = new File(tempPath + targetFileName);
        String fontName = "fontName";

        int baseX = 212;
        int baseY = 78;
        int intervalDistance = 218;

        FileUtils.deleteQuietly(targetFile);
        try {
            FileUtils.copyFile(tempFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开始日期
        pressText(tempPath + targetFileName, certInfo.getBeginDate().toString("yyyy"), fontName, Font.TRUETYPE_FONT, 24, Color.BLACK, baseX, baseY, 0.5f);
        pressText(tempPath + targetFileName, certInfo.getBeginDate().toString("MM"), fontName, Font.TRUETYPE_FONT, 24, Color.BLACK, baseX + 87, baseY, 0.5f);
        pressText(tempPath + targetFileName, certInfo.getBeginDate().toString("dd"), fontName, Font.TRUETYPE_FONT, 24, Color.BLACK, baseX + 137, baseY, 0.5f);
        //截止日期
        pressText(tempPath + targetFileName, certInfo.getEndDate().toString("yyyy"), fontName, Font.TRUETYPE_FONT, 24, Color.BLACK, baseX + intervalDistance, baseY, 0.5f);
        pressText(tempPath + targetFileName, certInfo.getEndDate().toString("MM"), fontName, Font.TRUETYPE_FONT, 24, Color.BLACK, baseX + intervalDistance + 94, baseY, 0.5f);
        pressText(tempPath + targetFileName, certInfo.getEndDate().toString("dd"), fontName, Font.TRUETYPE_FONT, 24, Color.BLACK, baseX + intervalDistance + 144, baseY, 0.5f);

        //随机no
        pressText(tempPath + targetFileName, certInfo.getNo(), fontName, Font.TRUETYPE_FONT|Font.BOLD, 18, Color.BLACK, 160, 306, 0.5f);

        //申请日期
        int applyBasex = 390;
        int applyBasey = 401;
        int applyFontSize = 14;
        int applyFontStyle = Font.TRUETYPE_FONT;
        pressText(tempPath + targetFileName, certInfo.getApplyDate().toString("yyyy"), fontName, applyFontStyle, applyFontSize, Color.BLACK, applyBasex, applyBasey, 0.5f);
        pressText(tempPath + targetFileName, certInfo.getApplyDate().toString("MM"), fontName, applyFontStyle, applyFontSize, Color.BLACK, applyBasex + 60, applyBasey, 0.5f);
        pressText(tempPath + targetFileName, certInfo.getApplyDate().toString("dd"), fontName, applyFontStyle, applyFontSize, Color.BLACK, applyBasex + 105, applyBasey, 0.5f);

        //姓名
        int nameBasex = 372;
        int nameBasey = 370;
        int nameFontSize = 14;
        int nameFontStyle = Font.TRUETYPE_FONT;
        pressText(tempPath + targetFileName, certInfo.getCarOwnerName(), fontName, nameFontStyle, nameFontSize, Color.BLACK, nameBasex, nameBasey, 0.5f);
        pressText(tempPath + targetFileName, certInfo.getCarId(), fontName, nameFontStyle, nameFontSize, Color.BLACK, nameBasex + 126, nameBasey, 0.5f);

    }

    public static String getNo(DateTime applyDate){
        String shortDate = applyDate.toString("yyMMdd");
        int randomNumber = (int)((Math.random()*9+1)*100000);
        if (randomNumber > 500000){
            randomNumber = randomNumber - 400000;
        }
        return String.format("A%s%d", shortDate, randomNumber);
    }


}
