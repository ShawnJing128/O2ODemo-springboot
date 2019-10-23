package com.imooc.o2o.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * 判断验证码是否符合预期
 * 
 * @author shawn
 *
 */
public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		// 实际的验证码
		String verifyCodeExcepted = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		// 输入的验证码
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExcepted)) {
			return false;
		}
		return true;
	}

	public static BitMatrix generateQRCodeStream(String content, HttpServletResponse resp) {
		// 给响应添加头部信息，告诉浏览器返回的是图片流
		resp.setHeader("Cache-Control", "no-store"); // 不要存储
		resp.setHeader("Pragma", "no-cache"); // 不要缓存
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/png");
		// 设置图片的文字编码以及内边框距
		Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 编码
		hints.put(EncodeHintType.MARGIN, 0); // 内边框距
		BitMatrix bitMatrix;
		try {
			// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
		return bitMatrix;
	}
}
