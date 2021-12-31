/**
 * @Author: Yang Zhang
 * @Description: Run some tests with filters.
 * @Date: Created in 30/12/21
 */

import java.util.*;

public class MovieRunnerAverage {
    public void printAverageRatings() {
        //String ratingsFileName = "data/ratings_short.csv";
        String ratingsFileName = "data/ratings.csv";
        //String moviesFileName = "ratedmovies_short.csv";
        String moviesFileName = "data/ratedmoviesfull.csv";
        SecondRatings secondRatings = new SecondRatings(moviesFileName, ratingsFileName);
        System.out.println("Number of Movies = " + secondRatings.getMovieSize());
        System.out.println("Number of Raters = " + secondRatings.getRaterSize());
        List<Rating> avgRatings = secondRatings.getAverageRatings(12);
        Collections.sort(avgRatings, (a, b) -> {
            return Double.compare(a.getValue(), b.getValue());
        });
        avgRatings.forEach((a) -> {
            System.out.println("Rating = "+ a.getValue() + ", Title = " + secondRatings.getTitle(a.getItem()));
        });
        System.out.println("--------");
    }
    
    public void getAverageRatingOneMovie() {
        //String ratingsFileName = "/Users/zhangyang/Downloads/data/ratings_short.csv";
        String ratingsFileName = "data/ratings.csv";
        //String moviesFileName = "/Users/zhangyang/Downloads/data/ratedmovies_short.csv";
        String moviesFileName = "data/ratedmoviesfull.csv";
        SecondRatings secondRatings = new SecondRatings(moviesFileName, ratingsFileName);
        List<Rating> avgRatings = secondRatings.getAverageRatings(3);
        String movieTitle = "The Maze Runner";
        if (secondRatings.hasMovie(movieTitle)) {
            for (Rating r : avgRatings) {
                if (secondRatings.getTitle(r.getItem()).equals(movieTitle)) {
                    System.out.println("The Average Ratings for Movie = "+movieTitle + " is " + r.getValue());
                    break;
                }
            }   
        }
        movieTitle = "Moneyball";
        if (secondRatings.hasMovie(movieTitle)) {
            for (Rating r : avgRatings) {
                if (secondRatings.getTitle(r.getItem()).equals(movieTitle)) {
                    System.out.println("The Average Ratings for Movie = "+movieTitle + " is " + r.getValue());
                    break;
                }
            }   
        }
        movieTitle = "Vacation";
        if (secondRatings.hasMovie(movieTitle)) {
            for (Rating r : avgRatings) {
                if (secondRatings.getTitle(r.getItem()).equals(movieTitle)) {
                    System.out.println("The Average Ratings for Movie = "+movieTitle + " is " + r.getValue());
                    break;
                }
            }   
        }
        System.out.println("--------");
    }
    
    public static void main(String[] args) {
        MovieRunnerAverage mra = new MovieRunnerAverage();
        mra.printAverageRatings();
        //mra.getAverageRatingOneMovie();
    }
}
