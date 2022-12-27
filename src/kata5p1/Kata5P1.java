
package kata5p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import kata5p1.model.Mail;
import kata5p1.view.MailListReader;

public class Kata5P1 {
    
    public static void main(String[] args) {
        createNewTable();
        insertAllEmailsFromData("email.txt");
        selectAll();
    }
    
    private static Connection connect() {
        String url = "jdbc:sqlite:mail.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    private static void selectAll(){
        String sql = "SELECT * FROM direcc_email";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                rs.getString("direccion") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS direcc_email (\n"
        + " id integer PRIMARY KEY AUTOINCREMENT,\n"
        + " direccion text NOT NULL);";
        try (Connection conn = connect();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla creada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void insert(String email) {
        String sql = "INSERT INTO direcc_email(direccion) VALUES(?)";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void insertAllEmailsFromData(String dataPath) {
        Iterable<Mail> mails = new MailListReader(dataPath).mails();
        Iterator i = mails.iterator();
        while(i.hasNext()) {
            Mail m = (Mail)i.next();
            if (m == null) {
                break;
            }
            insert(m.getMail());
        }
    }


    
}
