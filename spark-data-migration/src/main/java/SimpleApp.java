import model.Player;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SimpleApp {

    private final static String url = "jdbc:postgresql://localhost:5432/mediumdb";
    private final static String user = "root";
    private final static String password = "password";

    private final static String csvPath = "IdeaProjects/spark-data-migration/_export/export.csv";

    public static void main(String[] args) throws AnalysisException, FileNotFoundException {
        start();
    }

    public static void start() throws AnalysisException, FileNotFoundException {
        SparkSession spark = SparkSession.builder()
                .master("local[*]")
                .appName("Simple Application").getOrCreate();
        System.out.println(" ====> Session created");

        readFromPgViaSQLQuery(spark);

        spark.stop();
    }

    public static void readFromPgViaSQLQuery(SparkSession spark) throws AnalysisException, FileNotFoundException {
        // the SQL query will be executed
        String sql =
                "(SELECT p.id as player_id, " +
                        "p.name as player_name, " +
                        "t.id as team_id, " +
                        "t.name as team_name, " +
                        "l.id as league_id, " +
                        "l.name as league_name " +
                "FROM players p " +
                    "INNER JOIN teams t on p.team_id = t.id " +
                    "INNER JOIN leagues l on t.league_id = l.id) AS my_table";

        /*
        SELECT  p.id AS player_id,
                p.name AS player_name,
                t.id AS team_id,
                t.name AS team_name,
                l.id AS league_id,
                l.name AS league_name
        FROM    players p
        INNER JOIN  teams t ON p.teams_id = t.id
        INNER JOIN  leagues l ON t.leagues_id = l.id
        */

        // set credentials as properties
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("driver", "org.postgresql.Driver");

        Date startDate = new Timestamp(System.currentTimeMillis());

        // execute the SQL script and cast all returned to Player class
        Dataset<Player> jdbcDF = spark.read()
                .jdbc(url, sql, props)
                .as(Encoders.bean(Player.class));

        Date endDate = new Timestamp(System.currentTimeMillis());
        long seconds = (endDate.getTime() - startDate.getTime()) / 1000;

        System.out.println(" ====> Count of related SQL uery #" + jdbcDF.count());
        System.out.println(" ====> Obj#1 : " + jdbcDF.collectAsList().get(0).toString());
        System.out.println(" ====> Time in seconds : " + seconds);

        writeToCSV(jdbcDF.collectAsList());
    }

    public static void writeToCSV(List<Player> dtos) throws FileNotFoundException {
        // set the CSV file path
        File csvOutputFile = new File(csvPath);
        try(PrintWriter printWriter = new PrintWriter(csvOutputFile)) {

            // set the header of CSV
            StringBuilder sb = new StringBuilder();
            sb.append("id,player_name,team_id,team_name,league_id,league_name\n");
            printWriter.write(sb.toString());

            // set the content of CSV
            dtos.stream().map(Player::toString)
                    .forEach(printWriter::println);
            System.out.println(" ====> the CSV is created");
        }

        // print the line count of CSV
        printCSVLineCount();
    }

    private static void printCSVLineCount(){
        Path path = Paths.get(csvPath);
        Long lines = 0L;
        try {
            lines = Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" ====> Line count in the CSV : " + lines);
    }
}
