package importer;

import database.DBManager;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;


public class PreciserImport {
    private static final Logger LOG = Logger.getLogger(PreciserImport.class.getName());

    private String filename;

    public PreciserImport(String filename) {
        super();
        this.filename = filename;
    }

    public CSVParser buildCVSParser() throws IOException {
        CSVParser res = null;
        Reader in;
        in = new FileReader(filename);
        CSVFormat csvf = CSVFormat.DEFAULT.withCommentMarker('#').withDelimiter(';');
        res = new CSVParser(in, csvf);
        return res;
    }

    private boolean add(String code1, String libelle1, String code2, String libelle2) {
        boolean res = true;
        String sql = "";
        PreparedStatement ps = null;
        sql = "INSERT into assocpreciser(code1,libelle1,code2,libelle2)"
                + " VALUES(?,?,?,?)";
        try {
            ps = DBManager.CONNECTION.prepareStatement(sql);
            ps.setString(1, code1);
            ps.setString(2, libelle1);
            ps.setString(3, code2);
            ps.setString(4, libelle2);
            LOG.info(ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
            res = false;
        }
        return res;
    }

    public int updateDB(CSVParser parser) {
        int res = 0;
        DBManager.connect();
        boolean b = false;
        for (CSVRecord item : parser) {
            if (!b) {
                b = true;
            } else {
                String code1 = item.get(0).trim();
                String libelle1 = item.get(1).trim();
                String code2 = item.get(2).trim();
                String libelle2 = item.get(3).trim();


                System.out.println(code1);
                System.out.println(libelle1);
                System.out.println(code2);
                System.out.println(libelle2);
                if (add(code1, libelle1, code2, libelle2)) {
                    res++;
                }
            }
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
    public static void main(String[] args) {

        PreciserImport el = new PreciserImport("data/assoc-preciser.csv");

        DBManager.URI = "jdbc:mysql://127.0.0.1:3306/baccalaureat";
        DBManager.USER = "root";
        DBManager.PASSWORD = "root";
        CSVParser p = null;
        try {
            p = el.buildCVSParser();
            el.updateDB(p);
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        }

    }
}
