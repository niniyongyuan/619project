package de.vogella.jersey.first;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Servlet implementation class q1
 */
@WebServlet("/q1")
public class Query1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEAM_ID = "SunnyCloud";
	private static final String AWS_ACCOUNT_ID = "5287-0473-3900";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
		.format(Calendar.getInstance().getTime());
		PrintWriter out = response.getWriter();
		out.println(TEAM_ID + "," + AWS_ACCOUNT_ID + "\n" + timeStamp);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

