package jdbc_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//import com.mysql.cj.xdevapi.Result;

public class StudentDao {
	
	private Connection conn;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	private static final String URL = "jdbc:mysql://localhost/sqldb";

	public StudentDao() {
		try {
			System.out.println("생성자");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("로딩 성공");
			
		} catch (Exception e) {
			
			
		}
		
	}
	public Student selectOne(String id) {
		String sql = "select * from student where id = ?;";
		PreparedStatement pstmt = null;
		Student re = new Student();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				re.setId(rs.getString("id"));
				re.setName(rs.getString("name"));
				re.setGrade(rs.getString("grade"));
			}
		} catch (Exception e) {
			System.out.println("한줄 가져오는데 에러발생");
		}
		return re;
	}
	public void insertStudent(Student student) {
		
		String sql = "insert into student values(?,?,?);";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getId());
			pstmt.setString(2, student.getName());
			pstmt.setString(3, student.getGrade());
			
			pstmt.executeUpdate();
			System.out.println("student 데이터 삽입 성공");
		} catch (Exception e) {
			System.out.println("student 데이터 삽입 실패");
			
		}finally {
			try {
				if(pstmt !=null && !pstmt.isClosed())
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		
		}
	}
	public void updateStudent(Student student) {
		String sql = "update student set name=?, grade=? where id=?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getName());
			pstmt.setString(2, student.getGrade());
			pstmt.setString(3, student.getId());
			} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt !=null && !pstmt.isClosed())
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public void deleteStudent(String id) {
		String sql = "delete from student where id=?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			
		}finally {
			try {
				if(pstmt !=null && !pstmt.isClosed())
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}
	public List studentList() {
		String sql = "select * from student;";
		PreparedStatement pstmt = null;
		List<Student> list = new ArrayList<Student>();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet re = pstmt.executeQuery();
			
			while(re.next()) {
				Student s = new Student();
				s.setId(re.getString("id"));
				s.setName(re.getString("name"));
				s.setGrade(re.getString("grade"));
				list.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt !=null && !pstmt.isClosed())
					pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
}
