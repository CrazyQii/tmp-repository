package com.example.studentauth.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

public class ImageUtil {
    public static void main(String[] args) throws IOException {
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		/*Thumbnails.of(new File("D:/img/2.jpg"))
		.size(200, 200).toFile("D:/img/2.jpg");*/
        String result = "D:/img/";
        Thumbnails.of(result + "2.jpg").size(500, 500).toFile(result + "2.jpg");
    }
}
/**
 * watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("D:/img/1.jpg")), 0.25f)
 * .outputQuality(0.8f).
 */