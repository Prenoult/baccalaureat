package modifier;

import database.DBManager;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;


public class DeleteCandidat {

    private static final Logger LOG = Logger.getLogger(DeleteCandidat.class.getName());

    private String filename;

    public DeleteCandidat() {
        super();
    }

    public boolean run(String id) throws SQLException {
        boolean res = true;
        DBManager.connect();
        String sql = "";
        PreparedStatement ps = null;
        sql = "DELETE FROM tabscandidats WHERE numerocandidat = ?";
        try {
            ps = DBManager.CONNECTION.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
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

        DeleteCandidat el = new DeleteCandidat();

        DBManager.URI = "jdbc:mysql://127.0.0.1:3306/baccalaureat";
        DBManager.USER = "root";
        DBManager.PASSWORD = "root";

        el.run("516043657");
    }
}
