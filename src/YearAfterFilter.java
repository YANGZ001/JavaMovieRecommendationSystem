/**
 * @Author: Yang Zhang
 * @Description: YearAfterFilter filters movies that comes out later than myYear.
 * @Date: Created in 30/12/21
 */
public class YearAfterFilter implements Filter {
	private int myYear;
	
	public YearAfterFilter(int year) {
		myYear = year;
	}
	
	@Override
	public boolean satisfies(String id) {
		return MovieDatabase.getYear(id) >= myYear;
	}

}
