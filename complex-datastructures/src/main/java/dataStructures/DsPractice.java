package dataStructures;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class DsPractice<T> {

	// Good when each item is unique
	TreeSet<T> ts = new TreeSet<>();

	// add,peek,poke - For queues of things
	Queue<String> q = new LinkedList<String>();

	LinkedHashSet<T> str1 = new LinkedHashSet<>();

	public DsPractice() {
		super();
	}

	public static void main(String[] args) {

		DsPractice name = new DsPractice();
			
		int[] myArray = new int[] { 10, 20, 30, 40, 50, 60 };
		
		findRange(myArray);
		findMedian(myArray);
		removeWhitespace(" I am trying			to\t 		"
				+ "\n "
				+ "remove\n spaces"  );

	}

	// You have to return a string which says the number's range which are not
	// in the given array separated by comma.
	// Eg: Input: [50,75] Output: (0-49,51-74,76-100)

	public static void findRange(int[] myArray) {

		Arrays.sort(myArray);
		String myRange = "(";

		int low = 0;
		int high = 100;

		for (int n : myArray) {
			if (Arrays.binarySearch(myArray, n) != myArray.length) {

				myRange += low + " : " + (n - 1) + ", ";
				low = n + 1;
			}
		}
		myRange += low + " : " + high + ")";
		System.out.println(myRange);
	}

	// Write a function that finds the median of a set of three numbers

	public static void findMedian(int[] myArray) {

		Arrays.sort(myArray); 
		int median[] = new int[2];
		
		
		if(myArray.length%2==0){
			median[0]= myArray[(int)(myArray.length-1)/2];
			median[1]= myArray[(int)(myArray.length)/2];
		}
		else
			median[0]  = myArray[(int) Math.floor(myArray.length/2)];
		

		 for (int i = 0; i < median.length; i++) {
			System.out.println(median[i]);
		}
	}

	
//	 Write a function that removes whitespace ('\t', '\n   ', ' ') from a string 
	
	public static void removeWhitespace(String myString) {

		System.out.println(myString.replaceAll("\\s+", ""));
	}
	
//	Print out a binary tree in level order
	
	public static void binaryTreePrint(int[] myArray) {

		Arrays.sort(myArray); 
		int median[] = new int[2];
		
		
		if(myArray.length%2==0){
			median[0]= myArray[(int)(myArray.length-1)/2];
			median[1]= myArray[(int)(myArray.length)/2];
		}
		else
			median[0]  = myArray[(int) Math.floor(myArray.length/2)];
		

		 for (int i = 0; i < median.length; i++) {
			System.out.println(median[i]);
		}
	}
	
//	If you had a savings account with $1, at a 100% interest rate, at what year would you have 15 billion dollars
	
}
