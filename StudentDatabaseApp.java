import java.sql.*;

public class StudentDatabaseApp {

    private final String url = "jdbc:postgresql://localhost:5433/postgres";
    private final String user = "postgres";
    private final String password = "Samo0oha";

    // Method to establish a connection to the PostgreSQL database
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // getAllStudents method
    public void getAllStudents() {
        String SQL = "SELECT * FROM students";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            while (rs.next()) {
                System.out.println(rs.getInt("student_id") + " " +
                        rs.getString("first_name") + " " +
                        rs.getString("last_name") + " " +
                        rs.getString("email") + " " +
                        rs.getDate("enrollment_date"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // addStudent method
    public void addStudent(String first_name, String last_name, String email, Date enrollment_date) {
        String SQL = "INSERT INTO students(first_name, last_name, email, enrollment_date) VALUES(?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, enrollment_date);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // updateStudentEmail method
    public void updateStudentEmail(int student_id, String new_email) {
        String SQL = "UPDATE students SET email = ? WHERE student_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, new_email);
            pstmt.setInt(2, student_id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // deleteStudent method
    public void deleteStudent(int student_id) {
        String SQL = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, student_id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Main method to test the functionality
    public static void main(String[] args) {
        StudentDatabaseApp app = new StudentDatabaseApp();
        System.out.printf("first\n");
        app.getAllStudents();
        System.out.printf("second\n");
        app.addStudent("sameh", "Gawish", "MyEmail@example.com", Date.valueOf("2023-09-01"));
        System.out.printf("third\n");
        app.updateStudentEmail(3, "new.jim.beam@example.com");
        app.deleteStudent(2);
        System.out.printf("first again\n");
        app.getAllStudents();
    }
}
