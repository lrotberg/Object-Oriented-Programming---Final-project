package BL;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Test {

	private String name;
	private Question[] allQuestions;
	private int maxQuestions;
	private int numOfQuestions;

	public Test(int maxQuestions) {
		setName();
		allQuestions = new Question[this.maxQuestions = maxQuestions];
		numOfQuestions = 0;
	} // Test()

	public Test(Scanner s) {
		numOfQuestions = s.nextInt();
		// clean the buffer
		s.nextLine();

		System.out.println(numOfQuestions);
		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions[i] = new Question(s);
		} // for

	} // Test(Scanner s)

	public void printToFile(PrintWriter pw) {
		pw.println(name);
		pw.println(numOfQuestions);

		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions[i].printToFile(pw);
		} // for
	} // void printToFile(PrintWriter pw)

	public void printToFileSolution(PrintWriter pw) {
		pw.println(name);
		pw.println(numOfQuestions);

		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions[i].printToFileWithCorrectness(pw);
		} // for
	} // void printToFile(PrintWriter pw)

	public boolean addQuestion(Question question) {
		if (checkIfThereIsPlaceForQuestion(question) == true) {
			allQuestions[numOfQuestions++] = question;
			return true;
		} // if
		return false;
	}// boolean addAnswer(Question que)

	public boolean checkIfThereIsPlaceForQuestion(Question que) {
		if (numOfQuestions < maxQuestions) {
			return true;
		} // if
		return false;
	}// boolean checkIfThereIsPlaceForQuestion(Question que)

	public void setName() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		name = "exam_" + ldt.format(dtf);
	}

	public String getName() {
		return name;
	}// String getName()

	public int getNumOfQuestions() {
		return numOfQuestions;
	}// int getNumOfQuestions()

	public void deleteQuestion(Question question) {
		for (int i = 0; i < numOfQuestions; i++) {
			if (allQuestions[i].getQuestion().equalsIgnoreCase(question.getQuestion())) {
				Question temp = allQuestions[--numOfQuestions];
				allQuestions[i] = temp;
				allQuestions[numOfQuestions] = null;
			} // if
		} // for
	} // void deleteAnswer(Answer ans)

	public String toString() {
		StringBuilder sb = new StringBuilder(
				getName() + "\nthere are " + getNumOfQuestions() + " Questions, the questions are: ");

		for (int i = 0; i < numOfQuestions; i++) {
			sb.append("\n" + allQuestions[i].toString() + "\n");
		} // for
		return sb.toString();
	}// String toString()

	public String toStringWithSolutions() {
		StringBuilder sb = new StringBuilder(
				getName() + " - Solution\nthere are " + getNumOfQuestions() + " Questions, the questions are: ");

		for (int i = 0; i < numOfQuestions; i++) {
			sb.append("\n" + allQuestions[i].toStringWithCorretness() + "\n");
		} // for
		return sb.toString();
	}// String toStringNoSolutions()

}// Test class
