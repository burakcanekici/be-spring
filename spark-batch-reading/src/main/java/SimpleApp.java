import model.Player;
import model.Team;
import org.apache.spark.api.java.function.FilterFunction;
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

    public static void main(String[] args) throws AnalysisException, FileNotFoundException {
        /*

        make it single jar file and sign postgresql-42.2.24.jar from local repository
        # mvn package
        # spark-submit --class SimpleApp --driver-class-path ~/.m2/repository/org/postgresql/postgresql/42.2.24/postgresql-42.2.24.jar target/spark-data-migrate-1.0-SNAPSHOT.jar

        OR

        make it uber jar file and it takes postgresql-42.2.24.jar from the uber jar
        # mvn clean compile assembly:single
        # spark-submit --class SimpleApp target/spark-data-migrate-1.0-SNAPSHOT-jar-with-dependencies.jar


        docker cp IdeaProjects/spark-data-migrate/cb.csv new-seller-tag-api_couchbase_1:/
        cbimport csv -c couchbase://127.0.0.1 -u SellerTag -p password -b SellerTag -d file://cb.csv -g %id% --ignore-fields id, -t 4
        cbimport csv -c http://10.1.10.171 -u SellerTaskChallenge -p password -b SellerTag -d file://cb.csv -g %id% --ignore-fields id, -t 4
        */

        start();
    }

    public static void start() throws AnalysisException, FileNotFoundException {
        SparkSession spark = SparkSession.builder()
                .master("local[*]")
                .appName("Simple Application").getOrCreate();
        System.out.println(" ====> Session created");

        //readFromTxtFile(spark);

        //readFromPgViaTable(spark);

        readFromPgViaSQLQuery(spark);

        spark.stop();
    }

    public static void readFromTxtFile(SparkSession spark){
        String logFile = "/Users/burakcan.ekici/IdeaProjects/spark-batch-reading/source.txt";
        Dataset<String> logData = spark.read().textFile(logFile).cache();
        long numAs = logData.filter((FilterFunction<String>) s -> s.contains("a")).count();
        long numBs = logData.filter((FilterFunction<String>) s -> s.contains("b")).count();
        System.out.println(" ====> Lines with a: " + numAs + ", lines with b: " + numBs);
    }

    public static void readFromPg(SparkSession spark){
        // set credentials as properties
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("driver", "org.postgresql.Driver");

        Date startDate = new Timestamp(System.currentTimeMillis());

        // execute the SQL script and cast all returned to Player class
        Dataset<Team> jdbcDF = spark.read()
                .jdbc(url, "teams", props)
                .as(Encoders.bean(Team.class));

        Date endDate = new Timestamp(System.currentTimeMillis());
        long seconds = (endDate.getTime() - startDate.getTime()) / 1000;

        System.out.println(" ====> Count of table 'teams' #" + jdbcDF.count());
        System.out.println(" ====> Obj#1 : " + jdbcDF.collectAsList().get(0).toString());
        System.out.println(" ====> Time in seconds : " + seconds);
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
        SELECT p.id as player_id, p.name as player_name, t.id as team_id, t.name as team_name, l.id as league_id, l.name as league_name
        FROM players p
            INNER JOIN teams t on p.teams_id = t.id
            INNER JOIN leagues l on t.leagues_id = l.id
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
    }
}
