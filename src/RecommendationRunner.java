/**
 * @Author: Yang Zhang
 * @Description: RecommendationRunner runs the overall recommendation.
 * @Date: Created in 31/12/21
 */

import java.util.*;

public class RecommendationRunner implements Recommender{
    /**
     * This method returns a list of movie IDs that will be used to look up
     * the movies in the MovieDatabase and present them to users to rate.
     */
    @Override
    public ArrayList<String> getItemsToRate() {
        return getRandomMovies(7);
    }

    private ArrayList<String> getRandomMovies(int size) {
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new YearAfterFilter(1990));
        ArrayList<String> myMovies = MovieDatabase.filterBy(allFilters);
        if (myMovies.size() < size) return myMovies;
        ArrayList<String> res = new ArrayList<>();
        Set<Integer> indeces = new HashSet<>();
        Random random = new Random();
        while (indeces.size() < size) {
            int index = random.nextInt(myMovies.size());
            if (indeces.contains(index)) continue;
            indeces.add(index);
            res.add(myMovies.get(index));
        }
        return res;
    }

    /**
     * This method returns nothing, but prints out an HTML table of the
     * movies recommended for the given rater.
     * @param webRaterID the ID of a new Rater that has been already added to
     *                   the RaterDatabase with ratings for the movies returned by the
     *                   method getItemsToRate
     */
    @Override
    public void printRecommendationsFor(String webRaterID) {
        String ratingsFileName = "ratings.csv";
        String moviesFileName = "ratedmoviesfull.csv";
        FourthRatings fourthRatings = new FourthRatings();
        RaterDatabase.initialize(ratingsFileName);
        MovieDatabase.initialize(moviesFileName);
        ArrayList<Rating> ratingList = fourthRatings.getSimilarRatings(webRaterID, 10, 3);
        if (ratingList.size() == 0) {
            System.out.println("<h2> Sorry, there are no movie recommended based on your rating! </h2>");
            System.out.println("<h2> Try again or enjoy my personally selected list! </h2>");
            Random random = new Random();
            while (ratingList.size() < 5) {
                ratingList = fourthRatings.getSimilarRatings(String.valueOf(random.nextInt(RaterDatabase.size())), 10, 5);
            }
//            ratingList = fourthRatings.getSimilarRatings("65", 20, 5);
        }
        ArrayList<String> movieToBeRate = getItemsToRate();
        ArrayList<Rating> outID = new ArrayList<>();
        int count = 0;
        for (int i = 0; outID.size() + count != ratingList.size() && outID.size() < 10; i++) {
            if (!movieToBeRate.contains(ratingList.get(i).getItem())) {
                outID.add(ratingList.get(i));
            } else {
                count++;
            }
        }
//        System.out.println("outid size = " + outID.size());


        System.out.println("<style>");
        System.out.println("h2,h3{");
        System.out.println("  text-align: center;");
        System.out.println("  height: 50px;");
        System.out.println("  line-height: 50px;");
        System.out.println("  font-family: Arial, Helvetica, sans- serif;");
        System.out.println("  background-color: black;");
        System.out.println("   color:  #ff6600 }");//font color

        System.out.println(" table {");
        System.out.println("   border-collapse: collapse;");
        System.out.println("   margin: auto;}");
        System.out.println("table, th, td {");
        System.out.println("    border: 2px solid white;");
        System.out.println("    font-size: 15px;");

        System.out.println("    padding: 2px 6px 2px 6px; }");
        System.out.println(" td img{");
        System.out.println("    display: block;");
        System.out.println("    margin-left: auto;");
        System.out.println("    margin-right: auto; }");
        System.out.println("th {");
        System.out.println("    height: 40px;");
        System.out.println("    font-size: 18px;");

        System.out.println("  background-color: black;");
        System.out.println(" color: white;");
        System.out.println("text-align: center; }");

        System.out.println(" tr:nth-child(even) {");
        System.out.println("     background-color: #f2f2f2; }");
        System.out.println("  tr:nth-child(odd) {");
        System.out.println("background-color: #cccccc; }");
        System.out.println(" tr:hover {");
        System.out.println(" background-color: #666666; ");
        System.out.println("  color:white;}");

        System.out.println("table td:first-child {");
        System.out.println(" text-align: center; }");

        System.out.println(" tr {");
        System.out.println(" font-family: Arial, Helvetica, sans-serif; }");
        System.out.println(".rating{");
        System.out.println("    color:#ff6600;");
        System.out.println("    padding: 0px 10px;");
        System.out.println("   font-weight: bold; }");
        System.out.println("</style>");


        System.out.println("<h2> Select "+ outID.size() +" Best for You from " + MovieDatabase.size() + " Movies</h2>");
        System.out.println("<table id = \"rater\">");
        System.out.println("<tr>");
        System.out.println("<th>Rank</th>");

        System.out.println("<th>Poster</th>");
        System.out.println("<th>Title & Rating</th>");
        System.out.println("<th>Genre</th>");
        System.out.println("<th>Country</th>");
        System.out.println("</tr>");

        int rank = 1;
        for (Rating i : outID) {
            String imgSrc = "data" + MovieDatabase.getPoster(i.getItem()).substring(6);
            System.out.println("<tr><td>" + rank + "</td>" +
                    "<td><img src = \"" + imgSrc + "\" width=\"100\" height=\"140\"></td> " +
                    "<td>" + MovieDatabase.getYear(i.getItem()) + "&ensp;&ensp; <a href=\"https://www.imdb.com/title/tt" +
                    i.getItem() + "\">" + MovieDatabase.getTitle(i.getItem()) + "</a><br><div class = \"rating\">&starf; &ensp;&ensp;&ensp;"
                    + String.format("%.1f", i.getValue()) + "</td>" +
                    "<td>" + MovieDatabase.getGenres(i.getItem()) + "</td>" +
                    "<td>" + MovieDatabase.getCountry(i.getItem()) + "</td>" +
                    "</tr> ");
            rank++;
        }
        System.out.println("</table>");
        System.out.println("<h3>*The rank of movies is based on other raters who have the most similar rating to yours. Enjoy!^^</h3>");
    }

        public static void main(String[] args) {
            RecommendationRunner a = new RecommendationRunner();
            a.getItemsToRate();
            a.printRecommendationsFor("65");
        }
}
