import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner s= new Scanner(System.in);

        Integer[] arr = new Integer[s.nextInt()]; // read first line into size
        int[] out = new int[3];

        for (int i = 0; i < arr.length; i++)
        	arr[i] = s.nextInt();


        if (sorted (arr, out)){ // check out array and print accordingly
        	System.out.println("yes");
        	switch(out[0]){
        		case 1:
        			System.out.println("swap "+ out[1] + " "+ out[2]);
        			break;
        		case 2:
        			System.out.println("reverse "+ out[1] + " "+ out[2]);
        			break;
        		default:
        			break;
        	}
        }
        else {
        	System.out.println("no");
        }
    }

    private static boolean sorted(Integer[] arr, int[] out){
    	if (arr.length == 1)
    		return true;
    	if (arr.length == 2){ // optimizations
    		if (arr[0] > arr[1]){
    			set(out, 1, 0, 1);
    			return true;
    		}
    		else return true;
    	}

    	int l = 0, r = arr.length-1, i = l;
    	// look for an irregularity from the left first
    	for (; i < r && arr[i] <= arr[i+1]; i++){
    		l=i;
    		// skip past any multiple occurrences but include the first position so it can be used if part of reverse or swap trail
    		if (arr[i] == arr[i+1]){
    			for (; i < r && arr[i] == arr[i+1]; i++);
    			i--; // to make up for outer loop i++
    		}    		
    	}
    	
    	// already sorted if the end is reached
    	if (i == arr.length-1)
    		return true;

    	if (l != i && arr[l] != arr[i])
    		l=i;
    

    	for (i = r; i > l && arr[i] >= arr[i-1]; i--){
    		r = i;
    		// skip past any multiple occurrences but include the first position so it can be used if part of reverse or swap trail
    		if (arr[i] == arr[i-1]){
    			for (; i > l && arr[i] == arr[i-1]; i--);	
    			i++;// to make up for outer loop i--	
    		}
    	}
    	
    	if (r != i && arr[r] != arr[i])
    		r=i;

    	// !!!NOTE: function ordered can only be used after l and r have been set correctly

    	if (r == l+1){// adjacent elements out of place
    		return ordered(out, 1, arr, l, r);
    	} // so we know there is at least a single element between l and r because earlier statements would've stopped l and r from overlapping

    	boolean sorted = DSorted(arr, l, r);
    	if (!sorted) // if l to r is not in descending order, check for swap else return no
    		if (ASorted(arr, l+1, r-1)) // swap required
    			return ordered(out, 1, arr, l, r);

    	// check a sorted subsection
		if (r-l == 2 && arr[l] -arr[l+1] != 0 && arr[l+1] - arr[r] != 0){ // 3 unique elements in reverse order
			return ordered(out, 1, arr, l, r);
		}
		// next two if statements check for cases of swap like 55554 or 54444
		if (arr[l] != arr[l+1]){
			for (i = l+1; i < r && arr[i] == arr[i+1]; i++);
			if (i == r)
				return ordered(out, 1, arr, l, r);
		}
		if (arr[r] != arr[r-1]){
			for (i = r-1; i > l && arr[i] == arr[i-1]; i--);
			if (i == l)
				return ordered(out, 1, arr, l, r);
		}

		// if in order there is only one choice left -> reverse
		return ordered(out, 2, arr, l, r);
    }

    private static void set(int[] out, int op, int first, int last){
    	out[0] = op;
		out[1] = first+1;
		out[2] = last+1;
    }

    // ascending sorted
    private static boolean ASorted(Integer[] arr, int first, int last){
		int i = first;
		for (; i < last && arr[i] <= arr[i+1]; i++);
		if (i == last)
			return true;
    
    	return false;
    }

    // descending sorted
    private static boolean DSorted(Integer[] arr, int first, int last){
		int i = first;
		for (; i < last && arr[i] >= arr[i+1]; i++);
		if (i == last)
			return true;
    
    	return false;
    }

    private static boolean ordered(int[] out, int op, Integer[] arr, int l, int r){
    	if (((r == arr.length-1 || arr[l] <= arr[r+1]) && arr[l] >= arr[r-1]) && ((l == 0 || arr[r] >= arr[l-1]))&& arr[r] <= arr[l+1]){ // still has to maintain order
    		set(out, op, l, r);
    		return true;
    	}
	   	return false;
    }
}
// 2
// 4 2 