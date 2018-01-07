package BL;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
public class NewTest {

	private String name;
	private Vector<NewQuestion> allQuestions;
	private int numOfQuestions;
	private int maxQuestions;

	public NewTest() {
		setName();
		allQuestions = new Vector<NewQuestion>();
		numOfQuestions = allQuestions.size();
	} // NewTest()

	public NewTest(int maxQuestions) {
		setName();
		allQuestions = new Vector<NewQuestion>(this.maxQuestions = maxQuestions);
		numOfQuestions = allQuestions.size();
	} // NewTest(int maxQuestions)

	public NewTest(Scanner s) {
		numOfQuestions = s.nextInt();
		// clean the buffer
		s.nextLine();

		System.out.println(numOfQuestions);
		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions.add(new NewQuestion(s));
		} // for

	} // NewTest(Scanner s)

	public NewTest(NewTest newTest) {
		name = newTest.name;
		allQuestions = new Vector<NewQuestion>(newTest.allQuestions);
	} // NewTest(NewTest newTest)
	
	public NewTest(NewQuestion[] allQuestions){
		numOfQuestions=allQuestions.length;
		this.allQuestions=new Vector<NewQuestion>(Arrays.asList(allQuestions));
	}

	public void setName() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		name = "exam_" + ldt.format(dtf);
	} // void setName()

	public void printToFile(PrintWriter pw) {
		pw.println(name);
		pw.println(numOfQuestions);

		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions.elementAt(i).printToFile(pw);
		} // for
	} // void printToFile(PrintWriter pw)

	public void printToFileSolution(PrintWriter pw) {
		pw.println(name);
		pw.println(numOfQuestions);

		for (int i = 0; i < numOfQuestions; i++) {
			allQuestions.elementAt(i).printToFileWithCorrectness(pw);
		} // for
	} // void printToFileSolution(PrintWriter pw)

	public boolean addQuestion(NewQuestion newQuestion) {
		if (allQuestions.size() < maxQuestions) {
			allQuestions.add(newQuestion);
			numOfQuestions = allQuestions.size();
			return true;
		} // if
		return false;
	}// boolean addQuestion(NewQuestion newQuestion)

	public String getName() {
		return name;
	}// String getName()

	public int getNumOfQuestions() {
		return numOfQuestions;
	}// int getNumOfQuestions()

	public void deleteQuestion(NewQuestion question) {
		for (int i = 0; i < numOfQuestions; i++) {
			if (allQuestions.elementAt(i).equals(question)) {
				allQuestions.remove(i);
			} // if
		} // for
	} // void deleteQuestion(NewQuestion question)

	public void deleteQuestion(int questionIndex) {
		allQuestions.remove(questionIndex);
	} // void deleteQuestion(NewQuestion question)

	public String toString() {
		StringBuilder sb = new StringBuilder(
				getName() + "\nthere are " + getNumOfQuestions() + " Questions, the questions are: ");

		for (int i = 0; i < numOfQuestions; i++) {
			sb.append("\n" + allQuestions.elementAt(i).toString() + "\n");
		} // for
		return sb.toString();
	}// String toString()

	public String toStringWithSolutions() {
		StringBuilder sb = new StringBuilder(
				getName() + " - Solution\nthere are " + getNumOfQuestions() + " Questions, the questions are: ");

		for (int i = 0; i < numOfQuestions; i++) {
			sb.append("\n" + allQuestions.elementAt(i).toStringWithCorretness() + "\n");
		} // for
		return sb.toString();
	}// String toStringNoSolutions()

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allQuestions == null) ? 0 : allQuestions.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	} // int hashCode()

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NewTest)) {
			return false;
		}

		NewTest temp = (NewTest) obj;
		return name == temp.name && allQuestions == temp.allQuestions;
	} // boolean equals(Object obj)

} // public class NewTest
