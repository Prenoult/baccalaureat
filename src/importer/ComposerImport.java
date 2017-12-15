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


public class ComposerImport {
    private static final Logger LOG = Logger.getLogger(ComposerImport.class.getName());

    private String filename;

    public ComposerImport(String filename) {
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

    private boolean add(String code, String libelle, String codeepreuve, String epreuve) {
        boolean res = true;
        String sql = "";
        PreparedStatement ps = null;
        sql = "INSERT into assoccomposer(code,libelle,codeepreuve,epreuve)"
                + " VALUES(?,?,?,?)";
        try {
            ps = DBManager.CONNECTION.prepareStatement(sql);
            ps.setString(1, code);
            ps.setString(2, libelle);
            ps.setString(3, codeepreuve);
            ps.setString(4, epreuve);
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
                String code = item.get(0).trim();
                String libelle = item.get(1).trim();
                String codeepreuve = item.get(2).trim();
                String epreuve = item.get(3).trim();

                System.out.println(code);
                System.out.println(libelle);
                System.out.println(codeepreuve);
                System.out.println(epreuve);
                if (add(code, libelle, codeepreuve, epreuve)) {
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

        ComposerImport el = new ComposerImport("data/assoc-composer.csv");

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
