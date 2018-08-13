package cloud.simple.service.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.StringUtils;


/****
 *  文件操作类
 *  @version 1.0
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	private static final int BUFFER_SIZE = 16 * 1024;
	private static final long IMG_MAX_SIZE = 2 * 1024;
	private static final String[] vidExt = new String[] { "rm", "rmvb", "mov",
			"mtv", "dat", "wmv", "avi", "3gp", "amv", "dmv" };
	private static final String[] imgExt = new String[] { "bmp", "png", "gif",
			"jpeg", "jpg" };
	private static final String[] docExt = new String[] { "doc", "docx" };

	/**
	 * 删除指定目录下的所有文件
	 * 
	 * @param folderPath
	 *            目录路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delAllFile(String folderPath) {
		boolean flag = false;
		File file = new File(folderPath);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (folderPath.endsWith(File.separator)) {
				temp = new File(folderPath + tempList[i]);
			} else {
				temp = new File(folderPath + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(folderPath + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(folderPath + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param filePath
	 *            指定文件的路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			return flag;
		}
		flag = (new File(filePath)).delete();
		return flag;
	}

	/**
	 * 删除指定文件夹(包括文件夹下的所有文件)
	 * 
	 * @param folderPath
	 *            指定文件夹路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 读取文本文件的内容
	 * 
	 * @param curfile
	 *            文本文件路径
	 * @return 返回文件内容
	 */
	public static String readFile(String curfile) {
		File f = new File(curfile);
		try {
			if (!f.exists())
				throw new Exception();
			FileReader cf = new FileReader(curfile);
			BufferedReader is = new BufferedReader(cf);
			StringBuilder filecontent = new StringBuilder("");
			String str = is.readLine();
			while (str != null) {
				filecontent.append(str);
				str = is.readLine();
				if (str != null)
					filecontent.append(System.getProperty("line.separator","\n"));
			}
			is.close();
			cf.close();
			return filecontent.toString();
		} catch (Exception e) {
			System.err.println("不能读属性文件: " + curfile + " \n" + e.getMessage());
			return "";
		}

	}

	/**
	 * 取指定文件的扩展名
	 * 
	 * @param filePathName
	 *            文件路径
	 * @return 扩展名
	 */
	public static String getFileExt(String filePathName) {
		int pos = 0;
		pos = filePathName.lastIndexOf('.');
		if (pos != -1)
			return filePathName.substring(pos + 1, filePathName.length());
		else
			return "";

	}

	/**
	 * 读取文件大小
	 * 
	 * @param filename
	 *            指定文件路径
	 * @return 文件大小
	 */
	public static int getFileSize(String filename) {
		try {
			File fl = new File(filename);
			int length = (int) fl.length();
			return length;
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * 文件拷贝
	 * 
	 * @param src
	 *            源文件
	 * @param dst
	 *            目标文件
	 * @param delete
	 *            是否删除源文件
	 */
	public static void copyFile(File src, File dst, boolean delete) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);

				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				int n = 0;
				while ((n = in.read(buffer)) > 0) {
					out.write(buffer,0,n);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
			if (delete)
				src.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取源文件类型
	 * 
	 * @param ext
	 * @return
	 */
	public static String getSourceFileType(String ext) {

		for (String img : imgExt) {
			if (ext.equalsIgnoreCase(img))
				return "IMG";
		}
		
		for (String doc : docExt) {
			if (ext.equalsIgnoreCase(doc))
				return "DOC";
		}

		for (String vid : vidExt) {
			if (ext.equalsIgnoreCase(vid))
				return "VID";
		}

		return null;
	}

	/**
	 * 检查上传文件有效性
	 * 
	 * @param file
	 * @param fileName
	 * @param type
	 *            1、 图片 2、文档 3、音频
	 * @return
	 */
	public static Object[] checkUploadFile(File file, String fileName,
			Integer type) {
		Object[] returns = new Object[] { true, "上传文件有效。" };
		if (file.length() > IMG_MAX_SIZE) {
			returns[0] = false;
			returns[1] = "上传文件过大,请重新上传。";
			return returns;
		}

		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

		// 上传文件资源类型
		String xtype = getSourceFileType(ext);
		xtype = StringUtils.isNotEmpty(xtype) == true ? xtype : "UNKWON";
		switch (type) {
		case 1:
			if (!xtype.equalsIgnoreCase("IMG")) {
				returns[0] = false;
				returns[1] = "上传图片文件错误,请重新上传。格式为[bmp, png, gif, jpeg, jpg]";
			}
			break;
		case 2:
			if (!xtype.equalsIgnoreCase("DOC")) {
				returns[0] = false;
				returns[1] = "上传文档文件错误,请重新上传。格式为[doc, docx]";
			}
			break;
		case 3:
			if (!xtype.equalsIgnoreCase("DOC")) {
				returns[0] = false;
				returns[1] = "上传音频文件错误,请重新上传。格式为[rm,rmvb,mov,mtv,dat,wmv,avi,3gp,amv,dmv]";
			}
			break;
		default:
			break;
		}

		return returns;
	}
	
	public static void WriteFile(String file_name,String content,boolean append,boolean huanhang){
		try{
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file_name,append),"UTF-8"));
			bw.write(content);
			if(huanhang){
				bw.newLine();
			}			
	  	    bw.flush();
	  	    bw.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
