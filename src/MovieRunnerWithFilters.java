/**
 * @Author: Yang Zhang
 * @Description: Run some tests with filters.
 * @Date: Created in 31/12/21
 */

import java.util.Collections;
import java.util.List;

public class MovieRunnerWithFilters {
    private String ratingsFileName;
    private String moviesFileName;
    public MovieRunnerWithFilters(String ratingsFileName, String moviesFileName) {
        this.ratingsFileName = ratingsFileName;
        this.moviesFileName = moviesFileName;
    }

    public void printAverageRatings(int minimalRaters) {
        ThirdRatings thirdRatings = new ThirdRatings("data/" + ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + thirdRatings.getRaterSize());
        List<Rating> avgRatings = thirdRatings.getAverageRatings(minimalRaters);
        System.out.println("Found "+avgRatings.size() + " ratings with " + minimalRaters + " raters");
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }

    public void printAverageRatingsByYear(int minimalRaters, int year) {
        ThirdRatings thirdRatings = new ThirdRatings("data/" + ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + thirdRatings.getRaterSize());

        Filter filter = new YearAfterFilter(year);
        List<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minimalRaters, filter);
        System.out.println("Found "+ avgRatings.size() + " ratings with " + minimalRaters + " raters");
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", year = " + MovieDatabase.getYear(a.getItem()) + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }

    public void printAverageRatingsByGenre(int minimalRaters, String genre) {
        ThirdRatings thirdRatings = new ThirdRatings("data/" + ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + thirdRatings.getRaterSize());

        Filter filter = new GenreFilter(genre);
        List<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minimalRaters, filter);
        System.out.println("Found "+ avgRatings.size() + " ratings with " + minimalRaters + " raters");
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", genre = " + MovieDatabase.getGenres(a.getItem()) + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }

    public void printAverageRatingsByMinutes(int minimalRaters, int minMinutes, int maxMinutes) {
        ThirdRatings thirdRatings = new ThirdRatings("data/" + ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + thirdRatings.getRaterSize());

        Filter filter = new MinutesFilter(minMinutes, maxMinutes);
        List<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minimalRaters, filter);
        System.out.println("Found "+ avgRatings.size() + " ratings with " + minimalRaters + " raters");
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", time = " + MovieDatabase.getMinutes(a.getItem()) + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }

    public void printAverageRatingsByDirectors(int minimalRaters, String directors) {
        ThirdRatings thirdRatings = new ThirdRatings("data/" + ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + thirdRatings.getRaterSize());

        Filter filter = new DirectorsFilter(directors);
        List<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minimalRaters, filter);
        System.out.println("Found "+ avgRatings.size() + " ratings with " + minimalRaters + " raters");
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", directors = " + MovieDatabase.getDirector(a.getItem()) + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }

    public void printAverageRatingsByYearAfterAndGenre(int minimalRaters, String genre, int year) {
        ThirdRatings thirdRatings = new ThirdRatings("data/" + ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + thirdRatings.getRaterSize());

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new GenreFilter(genre));
        allFilters.addFilter(new YearAfterFilter(year));
        List<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minimalRaters, allFilters);
        System.out.println("Found "+ avgRatings.size() + " ratings with " + minimalRaters + " raters");
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", year = " + MovieDatabase.getYear(a.getItem()) + ", Title = " + MovieDatabase.getTitle(a.getItem()));
            System.out.println("     Genre = " + MovieDatabase.getGenres(a.getItem()));
        });
    }

    public void printAverageRatingsByDirectorsAndMinutes(int minimalRaters, String directors, int minMinutes, int maxMinutes) {
        ThirdRatings thirdRatings = new ThirdRatings("data/" + ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + thirdRatings.getRaterSize());

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new DirectorsFilter(directors));
        allFilters.addFilter(new MinutesFilter(minMinutes, maxMinutes));
        List<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minimalRaters, allFilters);
        System.out.println("Found "+ avgRatings.size() + " ratings with " + minimalRaters + " raters");
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", time = " + MovieDatabase.getMinutes(a.getItem()) + ", Title = " + MovieDatabase.getTitle(a.getItem()));
            System.out.println("     Directors = " + MovieDatabase.getDirector(a.getItem()));
        });
    }

    public static void main(String[] args) {
//        MovieRunnerWithFilters movieRunnerWithFilters = new MovieRunnerWithFilters("ratings_short.csv", "ratedmovies_short.csv");
        MovieRunnerWithFilters movieRunnerWithFilters = new MovieRunnerWithFilters("ratings.csv", "ratedmoviesfull.csv");
//        movieRunnerWithFilters.printAverageRatings(35);
//        movieRunnerWithFilters.printAverageRatingsByYear(20, 2000);
//        movieRunnerWithFilters.printAverageRatingsByGenre(20, "Comedy");
//        movieRunnerWithFilters.printAverageRatingsByMinutes(5, 105, 135);
//        movieRunnerWithFilters.printAverageRatingsByDirectors(4, "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
//        movieRunnerWithFilters.printAverageRatingsByYearAfterAndGenre(8, "Drama", 1990);
        movieRunnerWithFilters.printAverageRatingsByDirectorsAndMinutes(3, "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack", 90, 180);
    }
}
