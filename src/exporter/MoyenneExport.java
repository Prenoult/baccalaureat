package exporter;

import database.DBManager;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;


public class MoyenneExport {

    private static final Logger LOG = Logger.getLogger(MoyenneExport.class.getName());

    private String filename;

    public MoyenneExport() {
        super();
    }

    public boolean run(String serie, String groupe) throws SQLException {
        boolean res = true;
        DBManager.connect();
        File ff = new File("src/results/results.csv");
        String sql = "";
        PreparedStatement ps = null;
        sql = "SELECT id, code1, note FROM tabnotes INNER JOIN tabscandidats " +
                "WHERE tabnotes.id = tabscandidats.numerocandidat " +
                "AND serie = ? AND code1 IN (SELECT code FROM attrgroupes WHERE groupe = ?) ORDER BY id";
        try {
            ps = DBManager.CONNECTION.prepareStatement(sql);
            ps.setString(1, serie);
            ps.setString(2, groupe);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String code1 = rs.getString("code1");
                String note = rs.getString("note");
                System.out.println("candidat : " + id + " epreuve : " + code1 + " note : " + note);
            }

        } catch (SQLException e) {
            LOG.warning(e.getMessage());
            res = false;
        }
        DBManager.quit();
        return res;
    }

    public Logger getLog() {
        return this.LOG;
    }

    //===========================================
    public static void main(String[] args) throws IOException, SQLException {

        MoyenneExport el = new MoyenneExport();

        DBManager.URI = "jdbc:mysql://127.0.0.1:3306/baccalaureat";
        DBManager.USER = "root";
        DBManager.PASSWORD = "root";

        el.run("S", "1");
    }
}
