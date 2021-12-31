/**
 * @Author: Yang Zhang
 * @Description: RaterDatabase collects all the Rater information.
 * @Date: Created in 31/12/21
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.commons.csv.*;

public class RaterDatabase {
    private static HashMap<String,Rater> ourRaters;
     
	private static void initialize() {
	    // this method is only called from addRatings 
		if (ourRaters == null) {
			ourRaters = new HashMap<String,Rater>();
		}
	}

    public static void initialize(String filename) {
 		if (ourRaters == null) {
 			ourRaters= new HashMap<String,Rater>();
 			addRatings("data/" + filename);
 		}
 	}	
 	
    public static void addRatings(String filename) {
        initialize(); 
//        FileResource fr = new FileResource(filename);
//        CSVParser csvp = fr.getCSVParser();
        File f = new File(filename);
        CSVParser parser = null;
        try {
            parser = CSVParser.parse(f, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(CSVRecord rec : parser) {
                String id = rec.get("rater_id");
                String item = rec.get("movie_id");
                String rating = rec.get("rating");
                addRaterRating(id,item,Double.parseDouble(rating));
        } 
    }
    
    public static void addRaterRating(String raterID, String movieID, double rating) {
        initialize(); 
        Rater rater =  null;
                if (ourRaters.containsKey(raterID)) {
                    rater = ourRaters.get(raterID); 
                } 
                else { 
                    rater = new EfficientRater(raterID);
                    ourRaters.put(raterID,rater);
                 }
                 rater.addRating(movieID,rating);
    } 
	         
    public static Rater getRater(String id) {
    	initialize();
    	
    	return ourRaters.get(id);
    }
    
    public static ArrayList<Rater> getRaters() {
    	initialize();
        ArrayList<Rater> list = new ArrayList<Rater>(ourRaters.values());
    	
    	return list;
    }
 
    public static int size() {
	    return ourRaters.size();
    }
    
    
        
}
