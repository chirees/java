/**
* @Project: fmp-agent
* @Package com.freemypay.util
* FileName：ValidateCodeUtil.java
* Version：v1.0
* date：2014-7-1
* Copyright © 2014 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/

package com.framework.runtime.application.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
* FileName: ValidateCodeUtil.java
* @Description: 验证码生成工具
* @author: Hubert 
* @version: v1.0
* @create at: 2016年3月19日 下午1:26:42
* @reviewer:
* @review at:
*
* Revision history:
* date        author      version     content
* ------------------------------------------------------------
* 2016年3月19日    Hubert    v1.0        XXXX
*
* Copyright © 2016 Shanghai Deyi Network Technology Co.,Ltd All Rights Reserved
*/
public class ValidateCodeUtil
{
	private Log log = LogFactory.getLog(ValidateCodeUtil.class);

	private ByteArrayInputStream image;// 图像
	private String valiteCode;// 验证码

	private ValidateCodeUtil(String validateCode)
	{
		init(validateCode);
	}

	public static ValidateCodeUtil instance(String validateCode)
	{
		return new ValidateCodeUtil(validateCode);
	}

	public ByteArrayInputStream getImage()
	{
		return image;
	}

	public String getValiteCode()
	{
		return valiteCode;
	}

	private void init(String rands)
	{
		// 在内存中创建图象
		int width = 85, height = 35;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(new Color(0xFFFFFF));
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(0xFFFFFF));
		g.drawRect(0, 0, width - 1, height - 1);// 画边框
		// 随机产生8条干扰线，使图象中的认证码不易被其它程序探测到
		// g.setColor(getRandColor(100,200));
		// g.drawLine(10,20,30,12);
		// g.drawLine(25,12,50,20);
		// g.drawLine(50,25,90,12);
		// g.drawLine(10,15,90,15);
		// for (int i=0;i<8;i++){
		// int x = random.nextInt(width);
		// int y = random.nextInt(height);
		// int xl = random.nextInt(30);
		// int yl = random.nextInt(30);
		// g.drawLine(x,y,x+xl,y+yl);
		// }
		// g.setColor(getRandColor(160,200));// 随机产生100点，使图象中的认证码不易被其它程序探测到
		// for(int i=0;i<50;i++){
		// int x=random.nextInt(width);
		// int y=random.nextInt(height);
		// g.drawLine(x, y, x, y);
		// }

		// 取随机产生的认证码(6位数字)
		int codeLen = 4;
		String sRand = "";
		String[] codes = new String[codeLen];
		char mapTable[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', 'a', 'b', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
				'm', 'n', 'q', 'r', 's', 't', 'y', 'z', '2', '3', '4', '5',
				'6', '7', '8', '9' };
		Font[] fonts = new Font[] { new Font("Atlantic Inline", Font.BOLD, 25),
				new Font("Arial", Font.BOLD, 26),
				new Font("Verdana", Font.BOLD, 23),
				new Font("Time News Roman", Font.BOLD, 26),
				new Font("宋体", Font.BOLD, 24), new Font("黑体", Font.BOLD, 24),
				new Font("楷体_GB2312", Font.BOLD, 24), };
		char rand;
		for (int i = 0; i < codeLen; i++)
		{
			rand = mapTable[(int) (mapTable.length * Math.random())];
			sRand += rand;
			codes[i] = String.valueOf(rand);
		}
		// 生成
		int leftsize = random.nextInt(5);
		int topsize = 25;
		int gap;
		int topMin;
		AffineTransform at = new AffineTransform();
		int number = random.nextInt(3) - 1;
		double role = number * random.nextDouble() * (Math.PI / 4);
		at.setToShear(leftsize, topsize);
		g.setColor(new Color(0xC000000));//设置验证码字体颜色
		for (int i = 0; i < codeLen; i++)
		{
			gap = random.nextInt(5);
			topMin = random.nextInt(5);
			leftsize += gap + 12;
			// 设置旋转
			at.setToRotation(role, leftsize + 5, topsize + 5);
			g.setTransform(at);
			g.setFont(fonts[random.nextInt(fonts.length)]);
			g.drawString(codes[i], leftsize, topsize + topMin);
		}

		this.valiteCode = sRand;/* 赋值验证码 */
		// 图象生效
		g.dispose();
		ByteArrayInputStream input = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try
		{
			ImageOutputStream imageOut = ImageIO
					.createImageOutputStream(output);
			ImageIO.write(image, "JPEG", imageOut);
			imageOut.close();
			input = new ByteArrayInputStream(output.toByteArray());
		}
		catch (Exception e)
		{
			log.error("-----生成验证码错误-----" + e.toString());
		}
		this.image = input;/* 赋值图像 */
	}

	/***
	 * 
	 * 产生随机数字或字符
	 */
	public static String getRandomChar()
	{
		int index = (int) Math.round(Math.random() * 2);
		String randChar = "";
		switch (index)
		{
			case 0:// 大写字符
				randChar = String.valueOf(Math.round(Math.random() * 9));
				break;
			case 1:// 小写字符
				randChar = String.valueOf(Math.round(Math.random() * 9));
				break;
			default:// 数字
				randChar = String.valueOf(Math.round(Math.random() * 9));
				break;
		}
		return randChar;
	}
}
