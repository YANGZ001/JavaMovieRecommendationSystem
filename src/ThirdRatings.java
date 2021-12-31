/**
 * @Author: Yang Zhang
 * @Description: ThirdRatings Class is used for completing the week3 assignment.
 * @Date: Created in 30/12/21
 */
import java.util.*;

public class ThirdRatings {
    private List<Rater> myRaters;

    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings(String ratingsfile) {
        FirstRatings firstRatings = new FirstRatings();
        myRaters = firstRatings.loadRaters(ratingsfile);
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
        List<String> myMovies = MovieDatabase.filterBy(new TrueFilter());
        for (String id : myMovies) {
            double avgScore = getAverageByID(id, minimalRaters);
            if (avgScore > 0.0) {
                res.add(new Rating(id, avgScore));
            }
        }
        return res;
    }

    public List<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        List<String> myMovies = MovieDatabase.filterBy(filterCriteria);
        List<Rating> res = new ArrayList<>();
        for (String id : myMovies) {
            double avgScore = getAverageByID(id, minimalRaters);
            if (avgScore > 0.0) {
                res.add(new Rating(id, avgScore));
            }
        }
        return res;
    }
}
