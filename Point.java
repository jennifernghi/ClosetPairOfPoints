/**
 * @author Nghi Nguyen
 *this class implement Comparable and serializable and override Comparable's compareTo(Object o)
 *
 */
import java.text.*;
public class Point implements Comparable<Point>, java.io.Serializable
{
	//field
	private double x; 
	private double y;
	//constructor
	public Point(double x, double y)
	{
		this.x=x;
		this.y=y;
	}
	//setter
	public void setX(double x)
	{
		this.x=x;
	}
	public void setY(double y)
	{
		this.y=y;
	}
	//getter
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	
	//override compareTo(Object o)
	@Override 
	public int compareTo(Point p)
	{
		if(this.getX()<p.getX())
		{
			return -1;
		}else if(this.getX()==p.getX())
		{
			if(this.getY()<p.getY())
			{
				return -1;
			}else if(this.getY()==p.getY())
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
	
	//override toString()
	@Override
	public String toString()
	{
		DecimalFormat format = new DecimalFormat("###.00");
		String str ="("+ format.format(x) +" , "+ format.format(y)+")";
		return str;
	}
}
