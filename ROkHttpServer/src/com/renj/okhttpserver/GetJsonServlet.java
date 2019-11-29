package com.renj.okhttpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetJsonServlet extends HttpServlet {
	private String encoding = "utf-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 指定编码
		request.setCharacterEncoding(this.encoding);
		response.setCharacterEncoding(this.encoding);
		response.setContentType("text/html;charset=" + this.encoding);

		try {
			// 读取请求内容
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			// 打印结果
			System.out.println(sb.toString());
			
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
