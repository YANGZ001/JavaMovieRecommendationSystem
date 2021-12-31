/**
 * @Author: Yang Zhang
 * @Description: Run some tests with filters.
 * @Date: Created in 31/12/21
 */

import java.util.Collections;
import java.util.List;

public class MovieRunnerSimilarRatings {
    private String ratingsFileName;
    private String moviesFileName;

    public MovieRunnerSimilarRatings(String ratingsFileName, String moviesFileName) {
        this.ratingsFileName = ratingsFileName;
        this.moviesFileName = moviesFileName;
    }

    public void printSimilarRatings(String raterID, int numSimilarRaters, int minimalSimilarRatings) {
        FourthRatings fourthRatings = new FourthRatings();
        RaterDatabase.initialize(ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + RaterDatabase.size());
        List<Rating> similarRatings = fourthRatings.getSimilarRatings(raterID, numSimilarRaters, minimalSimilarRatings);
        System.out.println("Found "+ similarRatings.size() + " ratings with " + numSimilarRaters + " raters");
        similarRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }

    public void printSimilarRatingsByGenre(String genre, String raterID, int numSimilarRaters, int minimalSimilarRatings) {
        FourthRatings fourthRatings = new FourthRatings();
        RaterDatabase.initialize(ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + RaterDatabase.size());
        List<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalSimilarRatings, new GenreFilter(genre));
        System.out.println("Found "+ similarRatings.size() + " ratings with " + numSimilarRaters + " raters");
        similarRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }

    public void printSimilarRatingsByDirector(String directors, String raterID, int numSimilarRaters, int minimalSimilarRatings) {
        FourthRatings fourthRatings = new FourthRatings();
        RaterDatabase.initialize(ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + RaterDatabase.size());
        List<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalSimilarRatings, new DirectorsFilter(directors));
        System.out.println("Found "+ similarRatings.size() + " ratings with " + numSimilarRaters + " raters");
        similarRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }
    public void printSimilarRatingsByGenreAndMinutes(String genre, int minMintues, int maxMinutes, String raterID, int numSimilarRaters, int minimalSimilarRatings) {
        FourthRatings fourthRatings = new FourthRatings();
        RaterDatabase.initialize(ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + RaterDatabase.size());
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new GenreFilter(genre));
        allFilters.addFilter(new MinutesFilter(minMintues, maxMinutes));
        List<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalSimilarRatings, allFilters);
        System.out.println("Found "+ similarRatings.size() + " ratings with " + numSimilarRaters + " raters");
        similarRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }

    public void printSimilarRatingsByYearAfterAndMinutes(int year, int minMintues, int maxMinutes, String raterID, int numSimilarRaters, int minimalSimilarRatings) {
        FourthRatings fourthRatings = new FourthRatings();
        RaterDatabase.initialize(ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + RaterDatabase.size());
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new YearAfterFilter(year));
        allFilters.addFilter(new MinutesFilter(minMintues, maxMinutes));
        List<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalSimilarRatings, allFilters);
        System.out.println("Found "+ similarRatings.size() + " ratings with " + numSimilarRaters + " raters");
        similarRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }

    public void printAverageRatings(int minimalRaters) {
        FourthRatings fourthRatings = new FourthRatings();
        RaterDatabase.initialize(ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + RaterDatabase.size());
        List<Rating> avgRatings = fourthRatings.getAverageRatings(minimalRaters);
        System.out.println("Found "+ avgRatings.size() + " ratings with " + minimalRaters + " raters");
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", Title = " + MovieDatabase.getTitle(a.getItem()));
        });
    }



    public void printAverageRatingsByYearAfterAndGenre(int minimalRaters, String genre, int year) {
        FourthRatings fourthRatings = new FourthRatings();
        RaterDatabase.initialize(ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        System.out.println("Number of Movies = " + MovieDatabase.size());
        System.out.println("Number of Raters = " + RaterDatabase.size());

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new GenreFilter(genre));
        allFilters.addFilter(new YearAfterFilter(year));
        List<Rating> avgRatings = fourthRatings.getAverageRatingsByFilter(minimalRaters, allFilters);
        System.out.println("Found "+ avgRatings.size() + " ratings with " + minimalRaters + " raters");
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", year = " + MovieDatabase.getYear(a.getItem()) + ", Title = " + MovieDatabase.getTitle(a.getItem()));
            System.out.println("     Genre = " + MovieDatabase.getGenres(a.getItem()));
        });
    }

    public static void main(String[] args) {
//        MovieRunnerSimilarRatings movieRunnerSimilarRatings = new MovieRunnerSimilarRatings("ratings_short.csv", "ratedmovies_short.csv");
        MovieRunnerSimilarRatings movieRunnerSimilarRatings = new MovieRunnerSimilarRatings("ratings.csv", "ratedmoviesfull.csv");
        movieRunnerSimilarRatings.printSimilarRatings("337", 10, 3);
//        movieRunnerSimilarRatings.printSimilarRatingsByGenre("Mystery",964, 20, 5);
//        movieRunnerSimilarRatings.printSimilarRatingsByDirector("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh", 120,10,2);
//        movieRunnerSimilarRatings.printSimilarRatingsByGenreAndMinutes("Drama", 80, 160,168,10,3);
//        movieRunnerSimilarRatings.printSimilarRatingsByYearAfterAndMinutes(1975, 70, 200,314,10,5);
    }

}
