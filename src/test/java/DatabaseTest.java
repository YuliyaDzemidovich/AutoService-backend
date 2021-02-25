import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseTest {
    @Test
    @Disabled
    void testMysqlConn() {
        DataSource ds = getMySQLDataSource();
        String sql = "select * from information_schema.innodb_cmp;";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
//            while(rs.next()){
//                System.out.println(rs.getObject(1).toString());
//            }
            Assertions.assertTrue(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static DataSource getMySQLDataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            fis = new FileInputStream(DatabaseTest.class.getClassLoader().getResource("db.properties")
                    .getPath());
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_TEST_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
            mysqlDS.setServerTimezone("UTC");
        } catch (IOException | SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }
}
