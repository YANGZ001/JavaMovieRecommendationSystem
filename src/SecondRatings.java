/**
 * @Author: Yang Zhang
 * @Description: SecondRatings Class is used for completing the week2 assignment.
 * @Date: Created in 30/12/21
 */

import java.util.*;

public class SecondRatings {
    private List<Movie> myMovies;
    private List<Rater> myRaters;
    private Map<String, Movie> idToMovie;
    private Map<String, Movie> titleToMovie;
    
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings firstRatings = new FirstRatings();
        idToMovie = new HashMap<>();
        titleToMovie = new HashMap<>();
        myMovies = firstRatings.loadMovies(moviefile);
        myRaters = firstRatings.loadRaters(ratingsfile);
        myMovies.forEach((movie) -> {
            idToMovie.put(movie.getID(), movie);
            titleToMovie.put(movie.getTitle(), movie);
        });
    }
    
    public int getMovieSize() {
        return myMovies.size();
    }
    public int getRaterSize() {
        return myRaters.size();
    }
    
    private double getAverageByID(String movieID, int minimalRaters) {
        int cnt = 0;
        double score = 0;
        for (Rater r : myRaters) {
            if (r.hasRating(movieID)) {
                cnt++;
                score += r.getRating(movieID);
            }
        }
        return cnt >= minimalRaters ? score / cnt : 0.0;
    }
    
    /* find the average rating for every movie that has been rated by at least minimalRaters raters */
    public List<Rating> getAverageRatings(int minimalRaters) {
        List<Rating> res = new ArrayList<>();
        for (Movie movie : myMovies) {
            double score = getAverageByID(movie.getID(), minimalRaters);
            if (score > 0.0) {
                res.add(new Rating(movie.getID(), score));
            }
        }
        return res;
    }
    
    public String getTitle(String id) {
        if (idToMovie.containsKey(id)) {
            return idToMovie.get(id).getTitle();
        }
        return "Movie with ID = " + id + " does not exist";
    }
   
    public String getID(String title) {
        if (titleToMovie.containsKey(title)) {
            return titleToMovie.get(title).getID();
        }
        return "Movie with title = " + title + " does not exist";
    }
    
    public boolean hasMovie(String title) {
        return titleToMovie.containsKey(title);
    }
    
}
