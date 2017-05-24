package hacker;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;

public class WordSquare {

	public static void main(String[] args) {

		List<String> words = new ArrayList<>();
		List<String> words1 = new ArrayList<>();

		words.add("BALL");
		words.add("AREA");
		words.add("LEAD");
		words.add("LADY");

		words1.add("BMLL");
		words1.add("AREA");
		words1.add("LEQD");
		words1.add("LADY");

		System.out.println("\nIs it a Word Square? " + wordSquare(words) + "\n");
		System.out.println("\nIs it a Word Square? " + wordSquare(words1) + "\n");

	}

	public static boolean isSquare(List<String> words) {

		int length = words.size();

		for (String s : words) {
			if (s.length() != length) {
				return false;
			}
		}
		return true;
	}

	public static boolean wordSquare(List<String> words) {

		int length = words.size();

		Character[][] sArray = new Character[5][5];

		if (isSquare(words)) {
			int row = 0;
			for (String s : words) {
				for (int j = 0; j < length; j++)
					sArray[row][j] = s.charAt(j);
				row++;
			}
		} else
			return false;

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(sArray[i][j] + " ");
			}

			System.out.println();
		}

		for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++)
				if (!sArray[i][j].equals(sArray[j][i])) {
					return false;
				}

		return true;
	}
}
