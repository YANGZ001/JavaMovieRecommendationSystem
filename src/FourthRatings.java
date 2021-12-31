/**
 * @Author: Yang Zhang
 * @Description: Use RaterDatabase and MovieDatabase
 *         static methods in place of instance variables—so where you have
 *         code with "myRaters", you need to replace the code with calls to
 *         methods in the RaterDatabase class.
 * @Date: Created in 30/12/21
 */
import java.util.*;

public class FourthRatings {
    private double dotProduct(Rater me, Rater r) {
        //0-10 -> -5-5
        double res = 0;
        for (String id : me.getItemsRated()) {
            if (!r.hasRating(id)) continue;
            double score_me = me.getRating(id) - 5;
            double score_r = r.getRating(id) - 5;
            res += score_me * score_r;
        }
        return res;
    }

    /*
    getSimilarities ： this method computes a similarity rating for each rater in the RaterDatabase (except the rater with the ID given by the parameter)
    to see how similar they are to the Rater whose ID is the parameter to getSimilarities.
    @return: an ArrayList of type Rating sorted by ratings from highest to lowest rating
     and only including those raters who have a positive similarity rating since those with negative values are not similar in any way.
     */
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> res = new ArrayList<>();
        for (Rater r : RaterDatabase.getRaters()) {
            if (r.getID().equals(id)) continue;//skip itself
            double score = dotProduct(RaterDatabase.getRater(id), r);
            res.add(new Rating(r.getID(), score));
        }
        Collections.sort(res, Collections.reverseOrder());
        return res;
    }

    /*
    @return: an ArrayList of type Rating, of movies and their weighted average ratings using only the top numSimilarRaters with positive ratings
    and including only those movies that have at least minimalRaters ratings from those most similar raters (not just minimalRaters ratings overall).
     */
    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRaters) {
        return getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, new TrueFilter());
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String raterID, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> res = new ArrayList<>();
        ArrayList<String> myMovies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> similarities = getSimilarities(raterID);
        Map<String, Double> raterIDToSimilarScore = new HashMap<>();
        int minIndex = Math.min(similarities.size(), numSimilarRaters);
        for (int i = 0; i < minIndex; i++) {
            Rating cur = similarities.get(i);
            if (cur.getValue() > 0) {
                raterIDToSimilarScore.put(cur.getItem(), cur.getValue());
            }
        }

        for (String movieID : myMovies) {
            double avgWeightedScore = 0;
            int cnt = 0;
            for (Rater rater : RaterDatabase.getRaters()) {
                if (raterIDToSimilarScore.containsKey(rater.getID()) && rater.hasRating(movieID)) {
                    avgWeightedScore += raterIDToSimilarScore.get(rater.getID()) * rater.getRating(movieID);
                    cnt++;
                }
            }
            if (cnt >= minimalRaters && avgWeightedScore != 0) {
                res.add(new Rating(movieID, avgWeightedScore / cnt));
            }
        }
        Collections.sort(res, Collections.reverseOrder());
        return res;
    }

    private double getAverageByID(String movieID, int minimalRaters) {
        int cnt = 0;
        double score = 0;
        for (Rater r : RaterDatabase.getRaters()) {
            if (r.hasRating(movieID)) {
                cnt++;
                score += r.getRating(movieID);
            }
        }
        return cnt >= minimalRaters ? score / cnt : 0.0;
    }

    /* find the average rating for every movie that has been rated by at least minimalRaters raters */
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        return getAverageRatingsByFilter(minimalRaters, new TrueFilter());
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<String> myMovies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> res = new ArrayList<>();
        for (String id : myMovies) {
            double avgScore = getAverageByID(id, minimalRaters);
            if (avgScore > 0.0) {
                res.add(new Rating(id, avgScore));
            }
        }
        return res;
    }
}
