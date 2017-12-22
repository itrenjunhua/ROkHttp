package com.renj.okhttpserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReturnResult extends HttpServlet {
	private String encoding = "utf-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 request.setCharacterEncoding(this.encoding);  
         response.setCharacterEncoding(this.encoding);  
         response.setContentType("text/html;charset="+this.encoding);  
		try {
			ServletOutputStream out = response.getOutputStream();
			String result = "{\n"
					+ "            \"create_time\": \"2016-12-17 11:41:26\",\n"
					+ "            \"explain\": \"湖南公司\",\n"
					+ "            \"id\": 241,\n"
					+ "            \"is_generate\": 0,\n"
					+ "            \"is_pack\": 0,\n"
					+ "            \"pack_nums\": 6633,\n"
					+ "            \"prefix_no\": \"100116\",\n"
					+ "            \"production_name\": \"湖南种子企业\",\n"
					+ "            \"status\": 0,\n"
					+ "            \"stock_nums\": 0,\n"
					+ "            \"user_id\": 116,\n"
					+ "            \"varieties\": {\n"
					+ "                \"approval_number\": \"好纠结斤斤计较\",\n"
					+ "                \"crop_variety\": \"玉米\",\n"
					+ "                \"id\": 116,\n"
					+ "                \"license_number\": \"VB那你抱抱你\",\n"
					+ "                \"seed_class\": \"杂交种子\",\n"
					+ "                \"status\": 1,\n"
					+ "                \"user_id\": 116,\n"
					+ "                \"variety_name\": \"玉米1号\",\n"
					+ "                \"warning_signs\": \"12个月\"\n"
					+ "            },\n"
					+ "            \"varieties_id\": 116\n" + "        }";
			String succeed = "{\"code\":\"1\",\"msg\":\"succeed\",\"result\":"
					+ result + "}";
			out.write(succeed.getBytes("utf-8"));
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
