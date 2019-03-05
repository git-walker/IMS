package cn.rootyu.ims.common.utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片压缩工具类
 */

public class ImageUtil {
    //输出错误日志
    private static final Logger logger = Logger.getLogger(ImageUtil.class);

    public void imageScale(String srcFilePath, String targetFilePath,int width, int height) {
        this.imageScale(new File(srcFilePath), new File(targetFilePath), width,
                height);
    }
    /**
     *
     * @param srcFile 读取源图片
     * @param targetFile 写入图片
     * @param width 裁剪宽度
     * @param height 裁剪高度
     */

    public void imageScale(File srcFile, File targetFile, int width, int height) {
        FileOutputStream out = null;
        try {
            Image image = javax.imageio.ImageIO.read(srcFile);//获取图片流
            image = image.getScaledInstance(width, height,
                    Image.SCALE_AREA_AVERAGING);
            //提供一个 BufferedImage，将其用作解码像素数据的目标。
            BufferedImage mBufferedImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            // 绘制缩小后的图片
            Graphics2D g2 = mBufferedImage.createGraphics();
            g2.drawImage(image, 0, 0, width, height, Color.white, null);
            g2.dispose();
            //像素处理
            float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,
                    -0.125f, -0.125f, -0.125f, -0.125f };
            Kernel kernel = new Kernel(3, 3, kernelData2);
            ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            mBufferedImage = cOp.filter(mBufferedImage, null);

            File targetDir = targetFile.getParentFile();
            if (!targetDir.exists())
                targetDir.mkdirs();
            //输出到指定路径
            out = new FileOutputStream(targetFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(mBufferedImage);
        } catch (Exception e) {
            logger.error(
                    "imageScale(String, String, int, int) - 图片压缩出错 - srcFilePath="
                            + srcFile.getPath() + ", targeFilePath="
                            + targetFile.getPath() + ", width=" + width
                            + ", height=" + height, e);
        }finally{
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
     * @param imgsrc 源图片地址
     * @param imgdist 目标图片地址
     * @param widthdist 压缩后图片宽度（当rate==null时，必传）
     * @param heightdist 压缩后图片高度（当rate==null时，必传）
     * @param rate 压缩比例
     */
    public static void reduceImg(String imgsrc, String imgdist, int widthdist, int heightdist, Float rate) {
        try {
            File srcfile = new File(imgsrc);
            // 检查文件是否存在
            if (!srcfile.exists()) {
                return;
            }
            // 如果rate不为空说明是按比例压缩
            if (rate != null && rate > 0) {
                // 获取文件高度和宽度
                int[] results = getImgWidth(srcfile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return;
                } else {
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            Image src = javax.imageio.ImageIO.read(srcfile);
            BufferedImage tag = new BufferedImage((int) widthdist,
                    (int) heightdist, BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(
                    src.getScaledInstance(widthdist, heightdist,
                            Image.SCALE_SMOOTH), 0, 0, null);

            FileOutputStream out = new FileOutputStream(imgdist);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取图片宽度
     *
     * @param file
     *            图片文件
     * @return 宽度
     */
    public static int[] getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = { 0, 0 };
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图宽
            result[1] = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
