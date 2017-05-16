package dataStructures;

import java.util.*;

class Student {
	private int id;
	private String fname;
	private double cgpa;

	public Student(int id, String fname, double cgpa) {
		super();
		this.id = id;
		this.fname = fname;
		this.cgpa = cgpa;
	}

	public int getId() {
		return id;
	}

	public String getFname() {
		return fname;
	}

	public double getCgpa() {
		return cgpa;
	}
}

class StudentCompare implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {
		if (o1.getCgpa() == o2.getCgpa()) {
			int nameCompare = o1.getFname().toLowerCase().compareTo(o2.getFname().toLowerCase());
			if (nameCompare == 0)
				return o1.getId() - o2.getId();
			else
				return nameCompare;
		} else
			return o1.getCgpa() < o2.getCgpa() ? 1 : -1;
	}
}

// Solution for one of the problems from HackerRank Java Sort
public class OrderStudents {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int testCases = Integer.parseInt(in.nextLine());

		List<Student> studentList = new ArrayList<Student>();
		while (testCases > 0) {
			int id = in.nextInt();
			String fname = in.next();
			double cgpa = in.nextDouble();

			Student st = new Student(id, fname, cgpa);
			studentList.add(st);

			testCases--;
		}
		Collections.sort(studentList, new StudentCompare());
		for (Student st : studentList) {
			System.out.println(st.getFname());
		}
	}
}
