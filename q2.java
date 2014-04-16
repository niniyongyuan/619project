package de.vogella.jersey.first;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Servlet implementation class q2
 */
@WebServlet("/q2")
public class q2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEAM_ID = "SunnyCloud";
	private static final String AWS_ACCOUNT_ID = "5287-0473-3900";
	private static final String USER_NAME = "sunnycloud";
	private static final String PASS_WORD = "sunny15619cloud";
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/tweet";

	private static final String QRUERY = "select tid from q2 where query=";

	private ConnectionPool connectionPool;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public q2() {
		super();
		initConnectionPool();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result = "";
		String inputString = "";
		try {
			Connection connect = connectionPool.getConnection();
			// statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// resultSet gets the result of the SQL query

			String[] uid = request.getParameterValues("userid");
			String[] tweet_time = request.getParameterValues("tweet_time");
			// inputString += "ok/n";
			long time = strDateToUnixTimestamp(tweet_time[0].replace(" ", "+"));
			inputString = Long.toString(time) + uid[0];
			// inputString = "para: " + tweet_time[0] + "\n unix: "
			// + Long.toString(time) + " uid:" + uid[0];
			resultSet = statement.executeQuery(QRUERY + inputString);
			// inputString = QRUERY + inputString;
			result = printResult(resultSet);

			// close the current stuff
			statement.close();
			resultSet.close();
			connectionPool.free(connect);
		} catch (Exception e) {
		} finally {
//			if (connectionPool != null) {
//				connectionPool.closeAllConnections();
//			}
		}

		response.getWriter().print(result);
	}

	/**
	 * Helper method to change time to timestamp
	 * 
	 * @param dt
	 * @return
	 */
	private long strDateToUnixTimestamp(String dt) {
		DateFormat formatter;
		Date date = null;
		long unixtime;
		formatter = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			date = formatter.parse(dt);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		unixtime = date.getTime() / 1000L;
		return unixtime;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void initConnectionPool() {
		try {
			connectionPool = new ConnectionPool(JDBC_DRIVER, DB_URL, USER_NAME,
					PASS_WORD, 200, 500, true);
		} catch (SQLException sqle) {
			System.err.println("Error making pool: " + sqle);
			getServletContext().log("Error making pool: " + sqle);
			connectionPool = null;
		}
	}

	public String printResult(ResultSet resultSet) throws SQLException {
		String result = TEAM_ID + "," + AWS_ACCOUNT_ID + "\n";
		while (resultSet.next()) {
			String tweetID = resultSet.getString("tid");
			result = result + tweetID;
		}
		return result;
	}

	// @Override
	// public void run() {
	//
	// }

	// public void run() {
	// try {
	// for(int i=0; i<15; i++) {
	// pause(5000);
	// Connection connection = pool.getConnection();
	// Statement statement = connection.createStatement();
	// ResultSet resultSet = statement.executeQuery(QRUERY + inputString);
	// synchronized(pool) {
	// DatabaseUtilities.printTableData("Rich Employees",
	// results, 12, false);
	// }
	// pause(10000);
	// //System.out.println("[run] about to free connection");
	// pool.free(connection);
	// //System.out.println("[run] freed connection, pool=" +
	// // pool);
	// }
	// } catch(SQLException sqle) {
	// System.err.println("Error: " + sqle);
	// }
	// }
	//
	// public void pause(int millis) {
	// try {
	// Thread.sleep((int)(Math.random()*millis));
	// } catch(InterruptedException ie) {}
	// }
	//
	// private static void printUsage() {
	// System.out.println
	// ("Usage: ConnectionPoolTest host dbName " +
	// "username password oracle|sybase.");
	// }
}