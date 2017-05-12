package dataStructures;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;
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

		fizzBuzz(1, 30);

		findTheMissingNumber(new int[] { 7, 6, 0, 1, 3, 2, 4 });

		binarySearch(new int[] { 7, 6, 0, 1, 3, 2, 4 }, 3);
		StairCase(6);
         	longestString("abcdeefghijklnmmno");
	}

	/**************************** Solution **********************************/
		
     static void StairCase(int n) {

		int j = n;
		for (int i = 1; i <= n; i++) {
			StringBuffer str = new StringBuffer();
			int var1 = j;
			while (var1 > 1) {
				str.append(' ');
				var1--;
			}
			int var2 = i;
			while (var2 > 0) {
				str.append('#');
				var2--;
			}
			System.err.println(str);
			j--;
		}
	}

	// FizzBuzz Functions Java
	public static String[] fizzBuzz(int start, int end) {

		String result[] = new String[end - start];
		int n = 0;
		for (int i = start; i < end; i++) {
			if (i % 3 == 0 && i % 5 == 0)
				result[n] = "FizzBuzz";
			else if (i % 3 == 0)
				result[n] = "Fizz";
			else if (i % 5 == 0)
				result[n] = "Buzz";
			else
				result[n] = String.valueOf(i);
			n = n + 1;

		}

		for (String s : result)
			System.out.println(s);

		return result;
	}

	// String Functions Java
	public static void stringFunctionsinJava() {

		String str = "meandmyself";
		System.out.println(str.charAt(2));

		String str1 = "java";
		System.out.println(str1.equalsIgnoreCase("JAVA"));

		String str2 = "Change me";
		System.out.println(str2.replace('m', 'M'));

		// public String substring(int begin);
		// public String substring(int begin, int end);

		String str3 = "0123456789";
		System.out.println(str3.substring(4));

		StringBuilder str4 = new StringBuilder("study");
		str4.append("tonight");
		System.out.println(str4);
		str4.replace(6, 13, "today");
		System.out.println(str4);
		str4.reverse();
		System.out.println(str4);
		str4.replace(6, 13, "today");

		String s1 = "WhataString";
		s1 = s1.concat("Hello").concat(".").concat("Bye");

		String s2 = new String("What - A - String");

		System.out.println("split(String regex, int limit) with limit=2:");
		String array2[] = s2.split("/", 2);
		for (String temp : array2) {
			System.out.println(temp);
		}
		System.out.println("split(String regex, int limit) with limit=0:");
		String array3[] = s2.split("/");
		for (String temp : array3) {
			System.out.println(temp);
		}
		System.out.println("split(String regex, int limit) with limit=-5:");
		String array4[] = s2.split("/", -7);
		for (String temp : array4) {
			System.out.println(temp);
		}

		String s3 = new String("WhataStriSng");
		System.out.println("Index of S in str1: " + s3.indexOf('S'));
		System.out.println("Last 'S' in str1: " + s3.lastIndexOf('S'));
		System.out.println("Starts with: " + s3.startsWith("What"));

		String str5 = new String("Java String Methods");

		System.out.print("Regex: (.*)String(.*) matches string? ");
		System.out.println(str5.matches("(.*)String(.*)"));

		System.out.print("Regex: (.*)Strings(.*) matches string? ");
		System.out.println(str5.matches("(.*)Strings(.*)"));

		System.out.print("Regex: (.*)Methods matches string? ");

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

		LinkedList<Integer> list = new LinkedList<>();

		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);

<<<<<<< HEAD
		// list.listIterator(2) : Returns a list-iterator of the elements in this list (in proper
=======
		// list.listIterator(2) : Returns a list-iterator of the elements in
		// this list (in proper
>>>>>>> f647fc72ee016fbce6c713366174ff089fb2c3d3
		// sequence), starting at the specified position in the list. Obeys the
		// general contract of List.listIterator(int).

		for (Iterator<Integer> iterator = list.listIterator(2); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			System.err.println(integer);
		}
<<<<<<< HEAD
		
		int mid = list.size()/2;
=======

		int mid = list.size() / 2;
>>>>>>> f647fc72ee016fbce6c713366174ff089fb2c3d3
		list.remove(mid);

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

	// I have an array of the numbers 1 to 100 in a random number. One of the
	// numbers is missing.
	// Write an algorithm to figure out what the number is and what position is
	// missing.

	public static void findTheMissingNumber(int[] numberArray) {
		int sumOfAllNumbers = 0;
		int sumOfNumbersPresent = 0;
		int blankSpace = 0;
		for (int i = 0; i < numberArray.length; i++) {
			sumOfAllNumbers += i + 1;
			sumOfNumbersPresent += numberArray[i];
			if (numberArray[i] == 0)
				blankSpace = i;
			// sumOfAllNumbers = (numberArray.length+1) * (numberArray.length)/
			// 2.0
		}
		System.out.println("\nMissing number = " + (sumOfAllNumbers - sumOfNumbersPresent) + " at location "
				+ blankSpace + " of the array");
	}

	// Binary Search
	public static void binarySearch(int[] array, int search) {
		int first, last, middle;
		int n = array.length;
		first = 0;
		last = n - 1;
		middle = (first + last) / 2;
		while (first <= last) {
			if (array[middle] < search)
				first = middle + 1;
			else if (array[middle] == search) {
				System.out.println(search + " found at location " + (middle + 1) + ".");
				break;
			} else
				last = middle - 1;
			middle = (first + last) / 2;
		}
		if (first > last)
			System.out.println(search + " is not present in the list.\n");
	}

	// Matrix Multiplication

	public static void matrixMultiplication() {
		int m, n, p, q, sum = 0, c, d, k;

		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of rows and columns of first matrix");
		m = in.nextInt();
		n = in.nextInt();

		int first[][] = new int[m][n];

		System.out.println("Enter the elements of first matrix");

		for (c = 0; c < m; c++)
			for (d = 0; d < n; d++)
				first[c][d] = in.nextInt();

		System.out.println("Enter the number of rows and columns of second matrix");
		p = in.nextInt();
		q = in.nextInt();
		in.close();

		if (n != p)
			System.out.println("Matrices with entered orders can't be multiplied with each other.");
		else {
			int second[][] = new int[p][q];
			int multiply[][] = new int[m][q];

			System.out.println("Enter the elements of second matrix");

			for (c = 0; c < p; c++)
				for (d = 0; d < q; d++)
					second[c][d] = in.nextInt();

			for (c = 0; c < m; c++) {
				for (d = 0; d < q; d++) {
					for (k = 0; k < p; k++) {
						sum = sum + first[c][k] * second[k][d];
					}
					multiply[c][d] = sum;
					sum = 0;
				}
			}

			System.out.println("Product of entered matrices:-");

			for (c = 0; c < m; c++) {
				for (d = 0; d < q; d++)
					System.out.print(multiply[c][d] + "\t");
				System.out.print("\n");
			}
		}
	}

	// Quick Sort
	int partition(int arr[], int left, int right) {
		int i = left, j = right;
		int tmp;
		int pivot = arr[(left + right) / 2];
		while (i <= j) {
			while (arr[i] < pivot)
				i++;
			while (arr[j] > pivot)
				j--;
			if (i <= j) {
				tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				i++;
				j--;
			}
		}
		return i;
	}

	void quickSort(int arr[], int left, int right) {
		int index = partition(arr, left, right);
		if (left < index - 1)
			quickSort(arr, left, index - 1);
		if (index < right)
			quickSort(arr, index, right);
	}

<<<<<<< HEAD
=======
	public static void longestString(String a) {

		// abcdeefghijklmno

		char[] clist = a.toCharArray();
		Map<Character, Integer> map = new LinkedHashMap<Character, Integer>();
		StringBuffer longest = new StringBuffer();

		for (int i = 0; i < clist.length; i++) {

			if (!map.containsKey(clist[i])) {
				map.put(clist[i], i);
				System.out.println(clist[i]);

			} else {
				System.out.println("here");
				int newIndex = i + 1;
				System.out.println(clist[i]);
				map.entrySet().removeIf(k -> k.getValue() < newIndex);
			}

			if (map.size() > longest.length()) {
				longest.setLength(0);
				map.forEach((k, v) -> {
					longest.append(k.toString());
				});
			}
		}
		System.out.println("longest sequence: " + longest.toString());
	}
	
	public static void traverseNode(int startNode,int startOffset, int endNode,int endOffset) {
	
		Jsoup
	}
	
}
