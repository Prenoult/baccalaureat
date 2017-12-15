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


public class DeterminerImport {
    private static final Logger LOG = Logger.getLogger(DeterminerImport.class.getName());

    private String filename;

    public DeterminerImport(String filename) {
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

    private boolean add(String serie, String mention, String specialite, String opt, String code, String libelle,
                        String coefficient, String composition, String bonus, String facultatif) {
        boolean res = true;
        String sql = "";
        PreparedStatement ps = null;
        sql = "INSERT into assocdeterminer(serie,mention,specialite,opt,code,libelle,coefficient,composition,bonus,facultatif)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = DBManager.CONNECTION.prepareStatement(sql);
            ps.setString(1, serie);
            ps.setString(2, mention);
            ps.setString(3, specialite);
            ps.setString(4, opt);
            ps.setString(5, code);
            ps.setString(6, libelle);
            ps.setString(7, coefficient);
            ps.setString(8, composition);
            ps.setString(9, bonus);
            ps.setString(10, facultatif);
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
                String opt = item.get(3).trim();
                String code = item.get(4).trim();
                String libelle = item.get(5).trim();
                String coefficient = item.get(6).trim();
                String composition = item.get(7).trim();
                String bonus = item.get(8).trim();
                String facultatif = item.get(9).trim();

                System.out.println(serie);
                System.out.println(mention);
                System.out.println(specialite);
                System.out.println(opt);
                System.out.println(code);
                System.out.println(libelle);
                System.out.println(coefficient);
                System.out.println(composition);
                System.out.println(bonus);
                System.out.println(facultatif);
                if (add(serie, mention, specialite, opt, code, libelle, coefficient, composition, bonus, facultatif)) {
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

        DeterminerImport el = new DeterminerImport("data/assoc-determiner.csv");

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
