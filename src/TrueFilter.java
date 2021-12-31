/**
 * @Author: Yang Zhang
 * @Description: TrueFilter filters nothing.
 * @Date: Created in 30/12/21
 */

public class TrueFilter implements Filter {
	@Override
	public boolean satisfies(String id) {
		return true;
	}

}
