package com.framework.runtime.application.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.web.multipart.MultipartFile;

public class JxlOperateExcelUtil
{

	/**
	 * 
	* @Title: createPageExcel
	* @Description: 根据数据生成excel,适用单个sheet和多个sheet的情况
	* @param filePath: 生成文件的路径,String类型,格式例如："C:\\Users\\Administrator\\Desktop\\JXLsingletonSheet.xls"
	* @param sheetDatas:List<SheetData>,SheetData是sheet数据封装对象 
	* @param response HttpServletResponse对象
	* @param sheetDatas 参数
	* @return void 返回类型
	* @throws
	 */
	public static void createPageExcel(HttpServletResponse response, List<SheetData> sheetDatas)
	{
		try
		{
			OutputStream os = response.getOutputStream();
			// 创建工作簿workbook
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			// sheet对象
			WritableSheet sheet = null;
			// 获取单元格样式
			WritableCellFormat format = new JxlOperateExcelUtil().getFormat();
			// 循环创建sheet和sheet中的数据
			// 遍历sheet对象
			for (int i = 0; i < sheetDatas.size(); i++)
			{
				SheetData sheetData = sheetDatas.get(i);
				// 创建sheet,并设置sheetName,参数一表示sheet的名称,参数二表示第几个sheet
				sheet = workbook.createSheet(sheetData.getSheetName(), i);
				// 设置sheet的默认宽度
				sheet.getSettings().setDefaultColumnWidth(15);
				// 根据传递的单元格数据创建sheet中需要显示的数据
				Label title = null;
				Label content = null;
				// 设置标题数据
				for (int l = 0; l < sheetData.getSheetTitles().size(); l++)
				{
					// 将sheet的第一行设置为标题,并设置样式
					title = new Label(l, 0, sheetData.getSheetTitles().get(l).toString(), format);
					// 将单元格数据加入sheet中
					sheet.addCell(title);
				}
				// 遍历sheet中的每一行数据
				for (int j = 0; j < sheetData.getSheetContents().size(); j++)
				{
					// 获取每行数据
					List<Object> datas = sheetData.getSheetContents().get(j);
					// 遍历每一行中的每一列数据
					for (int k = 0; k < datas.size(); k++)
					{
						// 获取每一行对应的每一列的数据,从第二行开始写入数据
						content = new Label(k, j + 1, datas.get(k).toString());// 参数一：列数;参数二：行数;参数三：内容;
						sheet.addCell(content);
					}
				}
			}
			// 将数据写入excle表
			workbook.write();
			// 关闭工作簿对象
			workbook.close();
			// 关闭流
			os.close();

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (RowsExceededException e)
		{
			e.printStackTrace();
		}
		catch (WriteException e)
		{
			e.printStackTrace();
		}

	}

	/**
	* @Title: createScriptExcel
	* @Description: 脚本在指定位置生成excel
	* @param filePath
	* @param sheetDatas 参数
	* @return void 返回类型
	* @throws
	*/
	public static void createScriptExcel(String filePath, List<SheetData> sheetDatas)
	{
		try
		{
			// 从文件路径中获取文件输出流
			FileOutputStream fos = new FileOutputStream(filePath);
			// 创建工作簿workbook
			WritableWorkbook workbook = Workbook.createWorkbook(fos);
			// sheet对象
			WritableSheet sheet = null;
			// 获取单元格样式
			WritableCellFormat format = new JxlOperateExcelUtil().getFormat();
			// 循环创建sheet和sheet中的数据
			// 遍历sheet对象
			for (int i = 0; i < sheetDatas.size(); i++)
			{
				SheetData sheetData = sheetDatas.get(i);
				// 创建sheet,并设置sheetName,参数一表示sheet的名称,参数二表示第几个sheet
				sheet = workbook.createSheet(sheetData.getSheetName(), i);
				// 设置sheet的默认宽度
				sheet.getSettings().setDefaultColumnWidth(15);
				// 根据传递的单元格数据创建sheet中需要显示的数据
				Label title = null;
				Label content = null;
				// 设置标题数据
				for (int l = 0; l < sheetData.getSheetTitles().size(); l++)
				{
					// 将sheet的第一行设置为标题,并设置样式
					title = new Label(l, 0, sheetData.getSheetTitles().get(l).toString(), format);
					// 将单元格数据加入sheet中
					sheet.addCell(title);
				}
				// 遍历sheet中的每一行数据
				for (int j = 0; j < sheetData.getSheetContents().size(); j++)
				{
					// 获取每行数据
					List<Object> datas = sheetData.getSheetContents().get(j);
					// 遍历每一行中的每一列数据
					for (int k = 0; k < datas.size(); k++)
					{
						// 获取每一行对应的每一列的数据,从第二行开始写入数据
						content = new Label(k, j + 1, datas.get(k).toString());// 参数一：列数;参数二：行数;参数三：内容;
						sheet.addCell(content);
					}
				}
			}
			// 将数据写入excle表
			workbook.write();
			// 关闭工作簿对象
			workbook.close();
			// 关闭流
			fos.close();

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (RowsExceededException e)
		{
			e.printStackTrace();
		}
		catch (WriteException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 
	* @Title: readExcel
	* @Description: 读取excel文件，
	* @param filePath 待读取的文件路径
	* @param sheetNum sheet号 0是第一个sheet
	* @param beginRow 开始读取的行
	* @param beginColumn 开始读取的列
	* @return List<List<Object>> 返回类型
	* @throws
	 */
	public static List<List<String>> readExcel(String filePath, int sheetNum, int beginRow, int beginColumn)
	{
		// 用于存放一个sheet中的数据集合
		List<List<String>> dataList = new ArrayList<List<String>>();
		// 用于存放每一行数据的集合
		List<String> rowDatas = null;
		try
		{
			// 根据文件路径创建文件输入流
			FileInputStream fis = new FileInputStream(filePath);
			// 获取工作簿
			Workbook workbook = Workbook.getWorkbook(fis);
			// 获取需要读取数据所在的sheet,0表示第一个sheet
			Sheet sheet = workbook.getSheet(sheetNum);
			// 遍历单元格中数据
			for (int i = beginRow; i < sheet.getRows(); i++)
			{
				rowDatas = new ArrayList<String>();
				boolean overFlag = true;
				// 遍历列
				for (int j = beginColumn; j < sheet.getColumns(); j++)
				{
					// 参数一：列 参数二：行
					Cell cell = sheet.getCell(j, i);
					String content = cell.getContents().toString();
					rowDatas.add(content);
				}
				// 出现空行则跳出循环
				for (String s : rowDatas)
				{
					if (StringUtils.isNotBlank(s))
					{
						overFlag = false;
						break;
					}
				}
				if (overFlag)
				{
					break;
				}
				dataList.add(rowDatas);

			}
			// 关闭文件流
			workbook.close();
			fis.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (BiffException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public static List<List<String>> readExcel(MultipartFile file, int beginRow, int beginColumn)
	{
		List<List<String>> result = null;
		try
		{
			result = readExcel(file.getInputStream(), beginRow, beginColumn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("上传文件解析失败...");
		}
		return result;
	}

	public static List<List<String>> readExcel(File file, int beginRow, int beginColumn)
	{
		List<List<String>> result = null;
		try
		{
			result = readExcel(new FileInputStream(file), beginRow, beginColumn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("上传文件解析失败...");
		}
		return result;
	}

	public static List<List<String>> readExcel(InputStream is, int beginRow, int beginColumn) throws Exception
	{
		if (null == is)
		{
			return null;
		}
		Workbook workbook = null;
		// 用于存放一个sheet中的数据集合
		List<List<String>> dataList = new ArrayList<List<String>>();
		// 用于存放每一行数据的集合
		List<String> rowDatas = null;
		try
		{
			// 获取工作簿
			workbook = Workbook.getWorkbook(is);
			// 获取需要读取数据所在的sheet,0表示第一个sheet
			Sheet sheet = workbook.getSheet(0);
			// 遍历单元格中数据
			for (int i = beginRow; i < sheet.getRows(); i++)
			{
				rowDatas = new ArrayList<String>();
				boolean overFlag = true;
				// 遍历列
				for (int j = beginColumn; j < sheet.getColumns(); j++)
				{
					// 参数一：列 参数二：行
					Cell cell = sheet.getCell(j, i);
					String content = "";
					if (cell.getType() == CellType.DATE)
					{
						DateCell dc = (DateCell) cell;
						Date date = dc.getDate();
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						c.add(Calendar.HOUR, -8);
						content = DateUtils.formatDate(c.getTime(), "yyyyMMdd");
					}
					/*
					 * else if(cell.getType() == CellType.NUMBER){
					 * //解决读取小数四舍五入问题 但是这样会导致强行加.0
					 * NumberCell numberCell = (NumberCell) cell;
					 * double value = numberCell.getValue();
					 * content = String.valueOf(value);
					 * if(StringUtil.isNotNull(content)&&content.contains("E")){
					 * content = cell.getContents().toString().trim();
					 * }
					 * }
					 */
					else
					{
						content = cell.getContents().toString().trim();
					}
					rowDatas.add(content);
				}
				// 出现空行则跳出循环
				for (String o : rowDatas)
				{
					if (StringUtils.isNotBlank(o))
					{
						overFlag = false;
						break;
					}
				}
				if (overFlag)
				{
					break;
				}
				dataList.add(rowDatas);
			}
		}
		finally
		{
			// 关闭文件流
			try
			{
				if (null != workbook)
				{
					workbook.close();
				}
				if (null != is)
				{
					is.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new RuntimeException("关闭文件流失败...");
			}
		}
		return dataList;
	}

	/**
	 * 
	* @Title: getFormat
	* @Description: 设置单元格格式
	* @return 参数
	* @return WritableCellFormat 返回类型
	* @throws
	 */
	public WritableCellFormat getFormat()
	{
		// 设置单元格格式
		WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
		WritableCellFormat format = new WritableCellFormat(font);
		return format;
	}

}
