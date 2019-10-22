package prog03;

/**
 *
 * @author vjm
 */
public class LinFib implements Fib {
    /** The Fibonacci number generator 0, 1, 1, 2, 3, 5, ...
	@param n index
	@return nth Fibonacci number
    */
    public double fib (int n)
    {
    	double a = 0;
    	double b = 1;
    	double total = 0;
    	if( n == 0)
    		return total = 0;
    	if( n == 1 || n == 2)
    		return total = 1;
    	else{
    	
    	for( int i = 0; i < n; i++)
    	{
    		total = a + b;
    		a = b;
    		b = total;
    	}
    	return total;
    	}
    }

    /** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
    */
    public double o (int n)
    {
    	return (double) n;
    }
}
