package dataStructures;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class DsPractice<T> {

	// Good when each item is unique
	TreeSet<T> ts = new TreeSet<>();
	TreeMap<Integer, Integer> tmap = new TreeMap<Integer, Integer>();

	// add,peek,poke - For queues of things
	Queue<String> q = new LinkedList<String>();

	LinkedHashSet<T> str1 = new LinkedHashSet<>();

	public DsPractice() {
		super();
	}

	public static void main(String[] args) {

		int[] myArray = new int[] { 10, 20, 30, 40, 50, 60 };
		int[] myArray2 = new int[] { 50, 30, 40, 60, 10, 50, 20 };

		findRange(myArray);

		findMedian(myArray);

		removeWhitespace(" I am trying			to\t 		" + "\n " + "remove\n spaces");

		binaryTree(myArray2);

		powerOfTwo(15000000000.0);

		twoSum(new int[] { 50, 30, 40, 60, 10, 50, 20 }, 100);

		stringFunctionsinJava();
		
		regexJava();
		

	}

	/**************************** Solution **********************************/
	
	// String Functions Java
		public static void regexJava() {
			
			
		}
			

	// String Functions Java
	public static void stringFunctionsinJava() {
		
		String str = "meandmyself";
		System.out.println(str.charAt(2));
		
		String str1 = "java";
		System.out.println(str1.equalsIgnoreCase("JAVA"));
		
		String str2 = "Change me";
		System.out.println(str2.replace('m','M'));
		
//		public String substring(int begin);
//		public String substring(int begin, int end); 
		
		String str3 = "0123456789";
		System.out.println(str3.substring(4));
		
		 StringBuilder str4 = new StringBuilder("study");
		  str4.append( "tonight" );
		  System.out.println(str4);
		  str4.replace( 6, 13, "today");
		  System.out.println(str4);
		  str4.reverse();
		  System.out.println(str4);
		  str4.replace( 6, 13, "today");
		  
		  String s1="WhataString";
		  s1= s1.concat("Hello").concat(".").concat("Bye");
		  
		  String s2 = new String("What - A - String");
		  
		  System.out.println("split(String regex, int limit) with limit=2:");
	       String array2[]= s2.split("/", 2);
	       for (String temp: array2){
	          System.out.println(temp);
	       }
	       System.out.println("split(String regex, int limit) with limit=0:");
	       String array3[]= s2.split("/");
	       for (String temp: array3){
	          System.out.println(temp);
	       }
	       System.out.println("split(String regex, int limit) with limit=-5:");
	       String array4[]= s2.split("/", -7);
	       for (String temp: array4){
	          System.out.println(temp);
	       }
	       
	       String s3 = new String("WhataStriSng");
	       System.out.println("Index of S in str1: "+s3.indexOf('S'));
	       System.out.println("Last 'S' in str1: "+s3.lastIndexOf('S'));
	       System.out.println("Starts with: "+s3.startsWith("What"));
		
		  
		  String str5 = new String("Java String Methods");

	       System.out.print("Regex: (.*)String(.*) matches string? " );
	       System.out.println(str5.matches("(.*)String(.*)"));

	       System.out.print("Regex: (.*)Strings(.*) matches string? " );
	       System.out.println(str5.matches("(.*)Strings(.*)"));

	       System.out.print("Regex: (.*)Methods matches string? " );
	
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

		if (myArray.length % 2 == 0) {
			median[0] = myArray[(int) (myArray.length - 1) / 2];
			median[1] = myArray[(int) (myArray.length) / 2];
		} else
			median[0] = myArray[(int) Math.floor(myArray.length / 2)];

		for (int i = 0; i < median.length; i++) {
			System.out.println(median[i]);
		}
	}

	// Write a function that removes whitespace ('\t', '\n ', ' ') from a string

	public static void removeWhitespace(String myString) {

		System.out.println(myString.replaceAll("\\s+", ""));
		// string.split("[ \n\t\r;,!]");
	}

	// Print out a binary tree

	public static void binaryTree(int[] myArray) {

		TreeSet<Integer> tset = new TreeSet<Integer>();

		for (int n : myArray) {
			tset.add(n);
		}

		System.out.println(tset.headSet(tset.last()));
		System.out.println(tset.tailSet(tset.first()));
	}

	// If you had a savings account with $1, at a 100% interest rate, at what
	// year would you have 15 billion dollars
	// 2^10 is 1024, so 2^20 = 1024^2 is approx a million, 2^30 is approx a
	// billion

	public static void powerOfTwo(double limit) {

		int i = 1;
		double n = 1;

		while (n <= limit) {
			n = Math.pow(2, i);
			i++;
		}
		System.out.println(i);
	}

	// Find 2 numbers in an array adding up to a given sum S. 2SUM
	public static void twoSum(int[] numbers, int target) {

		Map<Integer, Integer> twosumList = new HashMap<Integer, Integer>();

		for (int i = 0; i < numbers.length; i++) {
			Integer diff = target - numbers[i];
			if (twosumList.containsKey(diff)) {
				int toReturn[] = { twosumList.get(diff) + 1, i + 1 };
			}
			twosumList.put(numbers[i], i);
		}

		System.out.println(twosumList);
	}

	// Write a program that breaks up a string of words with no spaces into a
	// string with appropriate spaces

	// Find 3 numbers in an array adding up to a given sum S. 3SUM

	// Given a list of integers of at least length 7,
	// print the average of each consecutive 7 number long subsequence (sliding
	// window).

	// How would you find the power set of a set of numbers?

}
