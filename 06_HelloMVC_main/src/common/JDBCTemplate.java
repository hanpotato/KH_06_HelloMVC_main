package common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	public static Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		// Properties는 파일 입출력 기능을 가지고 있고, 문자열로 받아 온다.
		String fileName = JDBCTemplate.class.getResource("./driver.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"),
					prop.getProperty("pw"));
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) {
		try {
			if (!stmt.isClosed() && stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (!rs.isClosed() && rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commit(Connection conn) {
		try {
			if (!conn.isClosed() && conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection conn) {
		try {
			if (!conn.isClosed() && conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

//
//	private static String driver;
//	private static String url;
//	private static String user;
//	private static String pw;
//	
//	static {
//		try {
//			Properties prop=new Properties();
//			prop.load(new FileReader("resource/driver.properties"));
//			driver = prop.getProperty("driver");
//			url = prop.getProperty("url");
//			user = prop.getProperty("user");
//			pw = prop.getProperty("pw");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static Connection getConnection() {
//		Connection conn = null;
//		
//		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
//			conn.setAutoCommit(false);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return conn;
//	}
//	
//	public static void close(Connection conn) {
//		try {
//			if(!conn.isClosed() && conn != null) {
//				conn.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void close(Statement stmt) {
//		try {
//			if (!stmt.isClosed() && stmt != null) {
//				stmt.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void close(ResultSet rs) {
//		try {
//			if (!rs.isClosed() && rs != null) {
//				rs.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void commit(Connection conn) {
//		try {
//			if (!conn.isClosed() && conn != null) {
//				conn.commit();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void rollback(Connection conn) {
//		try {
//			if (!conn.isClosed() && conn != null) {
//				conn.rollback();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
