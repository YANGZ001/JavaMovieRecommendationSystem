public class GenreFilter implements Filter{
    private String myGenre;

    public GenreFilter(String genre) {
        myGenre = genre;
    }

    @Override
    public boolean satisfies(String id) {
        for (String g : MovieDatabase.getGenres(id).split(", ")) {
            if (g.equals(myGenre)) return true;
        }
        return false;
    }
}
