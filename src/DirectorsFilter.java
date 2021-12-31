import java.util.*;

public class DirectorsFilter implements Filter{
    private Set<String> myDirectors;

    public DirectorsFilter(String directors) {
        myDirectors = new HashSet<>(Arrays.asList(directors.split(",")));
    }

    @Override
    public boolean satisfies(String id) {
        for (String d : MovieDatabase.getDirector(id).split(", ")) {
            if (myDirectors.contains(d)) return true;
        }
        return false;
    }
}
