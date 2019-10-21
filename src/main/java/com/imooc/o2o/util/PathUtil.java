package com.imooc.o2o.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathUtil {
	private static String seperator = System.getProperty("file.separator");// 获得系统的分隔符
	private static String winPath;
	private static String linuxPath;
	private static String shopPath;

	@Value("${win.base.path}")
	public void setWinPath(String winPath) {
		PathUtil.winPath = winPath;
	}
	@Value("${linux.base.path}")
	public void setLinuxPath(String linuxPath) {
		PathUtil.linuxPath = linuxPath;
	}
	@Value("${shop.relevant.path}")
	public void setShopPath(String shopPath) {
		PathUtil.shopPath = shopPath;
	}

	/**
	 * 返回项目图片的根路径
	 * 
	 * @return
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");// os.name是固定参数，表示获取系统名称
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = winPath;
		} else {
			basePath = linuxPath;
		}
		basePath = basePath.replace("/", seperator);// 确保该路径在不同系统下都是有效的
		return basePath;
	}

	/**
	 * 获取店铺图片的相对子路径（将图片分别存储在各自店铺的路径下），因此传入shopId加以区分
	 * 
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		// String imagePath = "/upload/item/shop/" + shopId + "/";
		String imagePath = shopPath + shopId + seperator;
		return imagePath.replace("/", seperator);
	}
}
