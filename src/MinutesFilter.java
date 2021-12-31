public class MinutesFilter implements Filter{
    private int myMin;
    private int myMax;

    public MinutesFilter(int min, int max) {
        myMax = max;
        myMin = min;
    }

    @Override
    public boolean satisfies(String id) {
        int minutes = MovieDatabase.getMinutes(id);
        return  minutes >= myMin && minutes <= myMax;
    }
}
