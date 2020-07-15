package database;

import java.util.*;
import java.sql.*;

public class SQL {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false&serverTimezone=UTC", "root", "");
            Statement statement = conn.createStatement();

            while(true) {
                System.out.printf("SQL_Name->%s>", conn.getMetaData().getDatabaseProductName());
                String SQLCommand = SCANNER.nextLine();

                if(SQLCommand.matches("SELECT.*")) {
                    SQLSearch(SQLCommand, statement);
                } else if(SQLCommand.matches("CREATE.*")) {
                    SQLCreateDataBase(SQLCommand, statement);
                } else if(SQLCommand.matches("USE.*")) {
                    SQLChangeDataBase(SQLCommand, statement);
                } else {
                    break;
                }
            }
            statement.close();
            conn.close();

        } catch(ClassNotFoundException CNFE) {
            System.out.println(CNFE.getMessage());
        } catch(SQLException SQLE) {
            System.out.printf("Code:%s\nState:%s\nDetails:%s\n", SQLE.getErrorCode(), SQLE.getSQLState(), SQLE.getMessage());
        }
    }

    private static void SQLChangeDataBase(String SQLCommand, Statement stat) {
        try {
            stat.executeUpdate(SQLCommand);
            printf("Change\n\n");
        } catch(SQLException SQLE) {
            System.out.printf("Code:%s\nState:%s\nDetails:%s\n", SQLE.getErrorCode(), SQLE.getSQLState(), SQLE.getMessage());
        }
    }


    private static void SQLCreateDataBase(String SQLCommand, Statement stat) {
        try {
            stat.executeUpdate(SQLCommand);
            printf("Create\n");
        } catch(SQLException SQLE) {
            System.out.printf("Code:%s\nState:%s\nDetails:%s\n", SQLE.getErrorCode(), SQLE.getSQLState(), SQLE.getMessage());
        }
    }

    private static void SQLSearch(String SQLCommand, Statement statement) {
        try {
            ResultSet rs = statement.executeQuery(SQLCommand);
            ArrayList<String> tables = new ArrayList<>(getTable());

            while (rs.next()) {

                for (String table : tables) {
                    String data = rs.getString(table);
                    System.out.printf("%s:%s\n", table, data);
                }

                System.out.println("\n");
            }
        } catch(SQLException SQLE) {
            System.out.printf("Code:%s\nState:%s\nDetails:%s\n", SQLE.getErrorCode(), SQLE.getSQLState(), SQLE.getMessage());
        }
    }

    private static ArrayList<String> getTable() {
        ArrayList<String> tableName = new ArrayList<>();
        String table;

        do {
            System.out.printf("SQL-getTable>");
            table = SCANNER.nextLine();
            tableName.add(table);
        } while(!table.equals("FXXK"));

        tableName.remove(tableName.size() - 1);
        return tableName;
    }

    private static void printf(String command) {
        System.out.printf(command);
    }
}
