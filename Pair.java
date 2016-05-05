
/*
 *  @author Nghi Nguyen
 *  assignment 5 - 22.7 
 * Define a class named Pair with the data fields p1 and p2 to represent two points, 
 * and a method named getDistance() that returns the distance between the two points.
 */
import java.util.ArrayList;
import java.util.Arrays;


public class Pair{
	
	
	private Point p1;
	private Point p2;
	
	public Pair(Point p1, Point p2) {
		this.p1=p1;
		this.p2=p2;
	}
	public void setp1(Point p1)
	{
		this.p1 = p1;
	}
	public void setp2(Point p2)
	{
		this.p2 = p2;
	}
	
	public Point getp1()
	{
		return p1;
	}
	public Point getp2()
	{
		return p2;
	}
	public double getDistance()
	{
		return distance(p1,p2);
	}
	/** Compute the distance between two points p1 and p2 */
	public static double distance(Point p1, Point p2)
	{
		return distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	/** Compute the distance between points (x1, y1) and (x2, y2) */
	public static double distance(double x1, double y1, double x2, double y2)
	{
		
		return Math.sqrt( ((x2 -x1)*(x2-x1)) + ((y2-y1)*(y2-y1)));
		
	}
	
	/*
	 * Step 1: sort points increasing in x-direction and y-direction
	 * getClosestPair(Point[] points): Return the distance of the closest pair of points
	 */
	
	public static Pair getClosestPair(Point[] points)
	{
		Arrays.sort(points); // pointsOrderedOnX
		Point[] pointsOrderedOnY = points.clone(); //pointsOrderedOnY
		Arrays.sort(pointsOrderedOnY, new CompareY());
		return distance(points, 0, points.length - 1, pointsOrderedOnY);
	}
	/* Return the distance of the closest pair of points 
	 * in case of the inputs are 2d array
	 */
	public static Pair getClosestPair(double[][] points)
	{
		Point[] points2 = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			points2[i] = new Point(points[i][0], points[i][1]);
		}
		return getClosestPair(points2);
	}
	
	/** Return the distance of the closest pair of points
	  * in pointsOrderedOnX[low..high]. This is a recursive
	  * method. pointsOrderedOnX and pointsOrderedOnY are
	  * not changed in the subsequent recursive calls.
	*/
	public static Pair distance(Point[] pointsOrderedOnX, int low, int high, Point[] pointsOrderedOnY)
	{
		if(low >= high) 
		{
			return null;
		} else if (low + 1 == high) 
		{
			return new Pair(pointsOrderedOnX[low], pointsOrderedOnX[high]); // only 1 pair present
		}
		
		/*
		 * Divide S into two subsets, S1 and S2, of equal size using the midpoint in the sorted list. 
		 * Let the midpoint be in S1. Recursively find the closest pair in S1 and S2. 
		 * Let d1: leftPair.getDistance()
		 * and d2: rightPair.getDistance()
		 * denote the distance of the closest pairs in the two subsets, respectively.
		 */
		int midPoint = (low + high) / 2; // divide into 2 subset 
		
		 // return the closet pair on the left side( including midpoint) - S1
		Pair leftPair = distance(pointsOrderedOnX, low, midPoint, pointsOrderedOnY);
		// return the closet pair on the right side( not including midpoint) - S2
		Pair rightPair = distance(pointsOrderedOnX, midPoint + 1, high, pointsOrderedOnY);
		
		double d = 0;
		Pair p = null;

		if (leftPair == null && rightPair == null)  // no pairs present on both sides
		{
			d = 0;
			p = null;
		} else if (leftPair == null)  // no pairs on the left side, then the right pair is the shortest
		{
			d = rightPair.getDistance(); // get d2
			p = rightPair;
		} else if (rightPair == null) // no pairs on the right side, then the left pair is the shortest
		{
			d = leftPair.getDistance(); //get d1
			p = leftPair;
		} else 
		{
			// get the minimum distance of d1 and d2: d = min(d1,d2)
			d = Math.min(leftPair.getDistance(), rightPair.getDistance());
			// get the point that has minimum distance d
			if(leftPair.getDistance() <= rightPair.getDistance())
			{
				p =leftPair;
			}else
			{
				p=rightPair;
			}
			
		}
		/*
		 * Obtaining stripL and stripR algorithm
		 * for each point p in pointsOrderedOnY
				if (p is in S1 and mid.x – p.x <= d) 
					append p to stripL;
				else if (p is in S2 and p.x - mid.x <= d) 
					append p to stripR;
		 */
		
		//create list stripL and stripR to hold the points
		ArrayList<Point> stripL = new ArrayList<Point>();
		ArrayList<Point> stripR = new ArrayList<Point>();
		for (int i = 0; i < pointsOrderedOnY.length; i++) 
		{
			if ((pointsOrderedOnY[i].getX() <= pointsOrderedOnX[midPoint].getX())&&
					(pointsOrderedOnX[midPoint].getX() - pointsOrderedOnY[i].getX() <= d)) 
			{
				stripL.add(pointsOrderedOnY[i]);
			}else if((pointsOrderedOnY[i].getX() > pointsOrderedOnX[midPoint].getX()) &&
						(pointsOrderedOnY[i].getX() - pointsOrderedOnX[midPoint].getX() <=d ))
			{
				stripR.add(pointsOrderedOnY[i]);
			}
		}

		/*
		 * finding the closet pair algorithm
		 * d = min(d1, d2);
 		   r = 0; // r is the index of a point in stripR 
 		   for (each point p in stripL) 
 		   {
				// Skip the points in stripR below p.y - d
				while (r < stripR.length && q[r].y <= p.y - d) r++;
				let r1 = r;
				while (r1 < stripR.length && |q[r1].y – p.y| <= d) 
				{
					// Check if (p, q[r1]) is a possible closest pair
					if (distance(p, q[r1]) < d) {
					d = distance(p, q[r1]);
					(p, q[r1]) is now the current closest pair;
      			}
			r1 = r1 + 1 ;
			}
		 */
		
		int r = 0;
		for (int i = 0; i < stripL.size(); i++) 
		{
			while (r < stripR.size() && stripR.get(r).getY() <= stripL.get(i).getY() - d) 
			{
				r++;
			}
			int r1 = r;
			while (r1 < stripR.size() && Math.abs(stripR.get(r1).getY() - stripL.get(i).getY()) <=  d) 
			{
				if (distance(stripL.get(i), stripR.get(r1)) < d) {
					d = distance(stripL.get(i), stripR.get(r1));
					p.p1 = stripL.get(i);
					p.p2 = stripR.get(r1);
				}
				r1++;
			}
		}

		return p;
	}
}
