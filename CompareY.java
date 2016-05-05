//this class implement Comparator and serializable and override Comparable's compare(Object o1, Object o2)
 /* @author Nghi Nguyen
 *
 *
 */
import java.util.Comparator;

public class CompareY implements Comparator<Point>, java.io.Serializable 
{
	@Override
	public int compare(Point p1, Point p2)
	{
		if(p1.getY()<p2.getY())
		{
			return -1;
		}else if(p1.getY()==p2.getY())
		{
			if(p1.getX()<p2.getX())
			{
				return -1;
			}else if(p1.getX()==p2.getX())
			{
				return 0;
			}else
			{
				return 1;
			}
		}else
		{
			return 1;
		}
	}
}


	