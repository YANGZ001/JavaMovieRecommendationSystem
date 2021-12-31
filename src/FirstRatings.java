import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.nio.charset.Charset;

/**
 * To process the movie and ratings data and to answer questions about them.
 *
 * @author (Yang Zhang)
 * @Version（V1-29/12/2021）
 */
public class FirstRatings {
    /*
     * This method process every record from the CSV file whose name is filename,
     * a file of raters and their ratings, and return an ArrayList of type Rater
     * with all the rater data from the file.
     */
    public List<Rater> loadRaters(String filename) {
        File f = new File(filename);
        CSVParser parser = null;
        try {
            parser = CSVParser.parse(f, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Rater> mapping = new HashMap<>();
        Map<String, Integer> movieToNumOfRatings = new HashMap<>();
        for (CSVRecord csvRecord : parser) {
            String rater_id = csvRecord.get("rater_id");
            mapping.putIfAbsent(rater_id, new EfficientRater(rater_id));
            String movie_id = csvRecord.get("movie_id");
            movieToNumOfRatings.put(movie_id, movieToNumOfRatings.getOrDefault(movie_id, 0) +1);
            double rating = Double.parseDouble(csvRecord.get("rating"));
            mapping.get(rater_id).addRating(movie_id, rating);
        }
        List<Rater> res = new ArrayList<>(mapping.size());
        int cnt = 0;
        for (String k : mapping.keySet()) {
            res.add(mapping.get(k));
            cnt = Math.max(cnt, mapping.get(k).numRatings());
        }
        List<Rater> maxRaters = new ArrayList<>();
        for (String k : mapping.keySet()) {
            if (cnt == mapping.get(k).numRatings()) {
                maxRaters.add(mapping.get(k));
            }
        }
        //System.out.println("Number of Ratings of Rater 193 = " + mapping.get("193").numRatings());
        //System.out.println("Max number of Ratings = " + cnt);
        //System.out.println("Raters = ");
        //maxRaters.forEach(i -> System.out.println(i.getID()));
        //System.out.println("Number of Ratings for "+ 1798709+ " = " + movieToNumOfRatings.get("1798709"));
        //System.out.println("Number of Different movies = " + movieToNumOfRatings.size());
        return res;
    }

    public void testLoadRaters(){
        //String testName = "data/ratings_short.csv";
        String testName = "data/ratings.csv";
        List<Rater> lst = loadRaters(testName);
        System.out.println("Number of Rater: " + lst.size());
        for (Rater i : lst) {
            String result = "[Rater ID="+ i.getID() + ", Number of Ratings="+ i.numRatings() +"]";
            //System.out.println(result);
            List<String> items = i.getItemsRated();
            for (String itm : items) {
                double rating = i.getRating(itm);
                //System.out.println("Movie ID = " + itm + ", Ratings = "+ rating);
            }
        }
        System.out.println("--------");
    }

    public List<Movie> loadMovies(String filename) {
        File f = new File(filename);
        CSVParser parser = null;
        try {
            parser = CSVParser.parse(f, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Movie> res = new ArrayList<>();
        int comedyCnt = 0;
        int longMovieCnt = 0;//More than 150 minutes;
        Map<String, Integer> mapping = new HashMap<>();
        int maxNum = 0;
        for (CSVRecord csvRecord : parser) {
            int i = 0;
            String id = csvRecord.get(i++);
            String title = csvRecord.get(i++);
            String year = csvRecord.get(i++);
            String country = csvRecord.get(i++);
            String genres = csvRecord.get(i++).trim();
            //System.out.println("genres : " + genres);
            for (String calss : genres.split(", ")) {
                //System.out.println("calss :" + calss);
                comedyCnt += calss.equals("Comedy") ? 1 : 0;
            }
            String director = csvRecord.get(i++).trim();
            for (String name : director.split(", ")) {
                mapping.put(name, mapping.getOrDefault(name, 0) + 1);
                if (mapping.get(name) > maxNum) {
                    maxNum = mapping.get(name);
                }
            }
            int minutes = Integer.parseInt(csvRecord.get(i++));
            longMovieCnt += minutes > 150 ? 1 : 0;
            String poster = csvRecord.get(i++);
            Movie m = new Movie(id, title, year, genres, director, country, poster, minutes);
            res.add(m);
        }
        //System.out.println("comedyCnt = " + comedyCnt);
        List<String> maxDirectors = new ArrayList<>();
        for (String name : mapping.keySet()) {
            if (mapping.get(name) == maxNum) {
                maxDirectors.add(name);
            }
        }
        //System.out.println("maxDirectors  = " + maxDirectors + " Max number of movies directed= " + maxNum);
        //System.out.println("Number of Such Directors = " + maxDirectors.size());
        //System.out.println("longMovieCnt = " + longMovieCnt);
        return res;
    }

    public void testLoadMovies(){
        //String testName = "data/ratedmovies_short.csv";
        String testName = "data/ratedmoviesfull.csv";
        List<Movie> lst = loadMovies(testName);
        System.out.println("Number of Movies: " + lst.size());
        for (Movie i : lst) {
            //System.out.println(i);
        }
        System.out.println("--------");
    }

    public static void main(String[] args) {
        FirstRatings fr = new FirstRatings();
        fr.testLoadMovies();
        fr.testLoadRaters();
    }
}
