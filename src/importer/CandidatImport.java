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


public class CandidatImport {
    private static final Logger LOG = Logger.getLogger(CandidatImport.class.getName());

    private String filename;

    public CandidatImport(String filename) {
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

    private boolean add(String libelleexamen, String anneesession, String moissession, String serie,
                        String mention, String specialite, String section, String numerocandidat) {
        boolean res = true;
        String sql = "";
        PreparedStatement ps = null;
        sql = "INSERT into tabscandidats(libelleexamen,anneesession,moissession,serie,mention,specialite,section,numerocandidat)"
                + " VALUES(?,?,?,?,?,?,?,?)";
        try {
            ps = DBManager.CONNECTION.prepareStatement(sql);
            ps.setString(1, libelleexamen);
            ps.setString(2, anneesession);
            ps.setString(3, moissession);
            ps.setString(4, serie);
            ps.setString(5, mention);
            ps.setString(6, specialite);
            ps.setString(7, section);
            ps.setString(8, numerocandidat);
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
                String libelleexamen = item.get(0).trim();
                String anneesession = item.get(1).trim();
                String moissession = item.get(2).trim();
                String serie = item.get(3).trim();
                String mention = item.get(4).trim();
                String specialite = item.get(5).trim();
                String section = item.get(6).trim();
                String numerocandidat = item.get(7).trim();

                System.out.println(libelleexamen);
                System.out.println(anneesession);
                System.out.println(moissession);
                System.out.println(serie);
                System.out.println(mention);
                System.out.println(specialite);
                System.out.println(section);
                System.out.println(numerocandidat);
                if (add(libelleexamen, anneesession, moissession, serie, mention, specialite, section, numerocandidat)) {
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

        CandidatImport el = new CandidatImport("data/tabs-candidats.csv");

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
