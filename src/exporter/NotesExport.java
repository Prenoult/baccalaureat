package exporter;

import database.DBManager;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;


public class NotesExport {

    private static final Logger LOG = Logger.getLogger(NotesExport.class.getName());

    private String filename;

    public NotesExport(String filename) {
        super();
        this.filename = filename;
    }

    public boolean run(String serie, String groupe) throws SQLException, IOException {
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
            FileWriter ffw = null;
            ffw = new FileWriter(ff);
            ffw.write("id;code1;note\n");
            while (rs.next()) {
                String id = rs.getString("id");
                String code1 = rs.getString("code1");
                String note = rs.getString("note");
                System.out.println("candidat : " + id + " epreuve : " + code1 + " note : " + note);
                ffw.write(id + ";" + code1 + ";" + note + "\n");
            }
            ffw.close();

        } catch (SQLException e) {
            LOG.warning(e.getMessage());
            res = false;
        } catch (IOException e) {
            LOG.warning(e.getMessage());
            res = false;
        }
        DBManager.quit();
        return res;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Logger getLog() {
        return this.LOG;
    }

    //===========================================
    public static void main(String[] args) throws IOException, SQLException {

        NotesExport el = new NotesExport("results.csv");

        DBManager.URI = "jdbc:mysql://127.0.0.1:3306/baccalaureat";
        DBManager.USER = "root";
        DBManager.PASSWORD = "root";

        el.run("S", "1");
    }
}
