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


public class ProfilImport {
    private static final Logger LOG = Logger.getLogger(ProfilImport.class.getName());

    private String filename;

    public ProfilImport(String filename) {
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

    private boolean add(String serie, String mention, String specialite, String section) {
        boolean res = true;
        String sql = "";
        PreparedStatement ps = null;
        sql = "INSERT into tabprofils(serie,mention,specialite,section)"
                + " VALUES(?,?,?,?)";
        try {
            ps = DBManager.CONNECTION.prepareStatement(sql);
            ps.setString(1, serie);
            ps.setString(2, mention);
            ps.setString(3, specialite);
            ps.setString(4, section);
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
                String serie = item.get(0).trim();
                String mention = item.get(1).trim();
                String specialite = item.get(2).trim();
                String section = item.get(3).trim();

                System.out.println(serie);
                System.out.println(mention);
                System.out.println(specialite);
                System.out.println(section);
                if (add(serie, mention, specialite, section)) {
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

        ProfilImport el = new ProfilImport("data/tab-profils.csv");

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
