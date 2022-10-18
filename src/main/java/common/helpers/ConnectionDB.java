package common.helpers;

import java.io.*;
import java.sql.*;

public class ConnectionDB {
    public static Connection getConnection() throws Exception {
        ExcelHelpers excel = new ExcelHelpers();
        File excelFile = new File("./src/test/resources/TestData.xlsx");
        String excelPath = excelFile.getAbsolutePath();
        excel.setExcelFile(excelPath, "ConnectionInfo");
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                    excel.getCellData("DB_URL", 1),
                    excel.getCellData("USER_NAME", 1),
                    excel.getCellData("PASSWORD", 1));
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }

        return conn;
    }
}
