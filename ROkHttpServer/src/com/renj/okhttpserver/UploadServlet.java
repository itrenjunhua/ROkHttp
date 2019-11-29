package com.renj.okhttpserver;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadServlet extends HttpServlet {
	private String encoding = "utf-8";

	/**
	需要使用到的jar包：
	commons-fileupload-1.2.1.jar
	commons-1.4.jar
	
	步骤：
	第一步：创建磁盘文件项工厂
	= new DiskFileItemFactory();
	第二步：创建核心上传类
	= new ServletFileUpload(FileItemFactory fileItemFactory);
	第三步：使用核心上传类解析request对象
	= parseRequest(javax.servlet.http.HttpServletRequest request)
	= 返回的List集合，集合里面有多个FileItem，List<FileItem>
	第四步：遍历list集合，得到每个FileItem
	第五步：判断是普通输入项还是文件上传项
	= boolean isFormField()
	第六步：如果是普通输入项得到值；如果是文件上传项编写上传的代码
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//指定编码
		request.setCharacterEncoding(this.encoding);  
        response.setCharacterEncoding(this.encoding);  
        response.setContentType("text/html;charset="+this.encoding);  
        System.out.println("请求方式：" + request.getMethod());
		try {
			//创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置缓冲区大小和临时文件路径
			factory.setSizeThreshold(1*1024*1024); //字节
			String temp = getServletContext().getRealPath("/temp");
			factory.setRepository(new File(temp));
			//创建核心上传类
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			//设置上传文件名的编码
			fileUpload.setHeaderEncoding("utf-8");
			//设置上传文件的大小
//			fileUpload.setFileSizeMax(1*1024*1024); //字节
			//解析request对象
			List<FileItem> list = fileUpload.parseRequest(request);
			//遍历list集合
			for (FileItem fileItem : list) {
				//判断普通输入项
				if(fileItem.isFormField()) { //普通输入项
					//得到普通输入项的name的属性值和输入的值
					String name = fileItem.getFieldName();
					String value = fileItem.getString("utf-8");
					System.out.println(name+" :: "+value);
				} else {//文件上传项
					//得到上传文件的名称 getName()
					//在某些浏览器里面，得到的是带路径名称  c:\aa\1.txt
					String filename = fileItem.getName();
					//判断是否带 \，如果带 \进行截取
					int lens = filename.lastIndexOf("\\");
					if(lens != -1) {
						//截取
						filename = filename.substring(lens+1);
					}
					//在文件名称里面添加随机的唯一的值
					String uuid = UUID.randomUUID().toString();
					//uuid_filename
					filename = uuid+"_"+filename;
					
					//得到提交的文件的输入流
					InputStream in = fileItem.getInputStream();
					//得到文件夹的带盘符的路径 ，servletContext对象
					String path = getServletContext().getRealPath("/upload");
					//创建输出流
					OutputStream out = new FileOutputStream(path+"/"+filename);
					System.out.println("文件地址：" + path+"/"+filename);
					//流对接
					int len = 0;
					byte[] b = new byte[1024];
					while((len=in.read(b))!=-1) {
						out.write(b, 0, len);
					}
					//关闭
					in.close();
					out.close();
					//删除临时文件
					fileItem.delete();
				}
			}
			
			ServletOutputStream out = response.getOutputStream();
			out.write("{\"code\":\"1\",\"msg\":\"succeed\"}".getBytes("utf-8"));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			ServletOutputStream out = response.getOutputStream();
			out.write("{\"code\":\"0\",\"msg\":\"fail\"}".getBytes("utf-8"));
			out.flush();
			out.close();
		}
	}

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
