package BL;

import java.io.PrintWriter;
import java.util.Scanner;

public class Answer {

	private String answer;
	private boolean correctness;

	public Answer(String answer, boolean correctness) {
		this.answer = answer;
		setCorrectness(correctness);
	}// Answer(String ansText, boolean correctness)

	public Answer(Scanner s) {
		answer = s.nextLine();
		correctness = s.nextBoolean();
		s.nextLine();
	} // Answer(Scanner s)

	public Answer(String answer) {
		this.answer = answer;
	} // Answer(String answer)

	public Answer(Answer ans) {
		this(ans.answer, ans.correctness);
	} // Answer(Answer ans)

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + (correctness ? 1231 : 1237);
		return result;
	} // int hashCode()

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Answer)) {
			return false;
		}

		Answer temp = (Answer) obj;
		return answer == temp.answer && correctness == temp.correctness;
	} // boolean equals (Object obj)

	public void setCorrectness(boolean tBoolean) {
		correctness = tBoolean;
	} // setCorrectness(boolean tBoolean)

	public String getAnswer() {
		return answer;
	}// String getAnsText()

	public boolean getCorrectness() {
		return correctness;
	}// boolean getCorrectness()

	public String toString() {
		return answer + " - " + correctness;
	}// String toString()

	public void updateAnswer(String newAnswer) {
		answer = newAnswer;
	}// void updateAnswerText(String newAnswer)

	public void printToFile(PrintWriter pw) {
		pw.println(answer);
	} // void printToFile(PrintWriter pw)

	public void printToFileWithCorrectness(PrintWriter pw) {
		pw.println(answer);
		pw.println(correctness);
	} // void printToFile(PrintWriter pw)

}// public class Answer
