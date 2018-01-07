package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import BL.Answer;
import BL.Question;
import BL.Test;
import BL.TestAdmin;

public class Program {
	private static Scanner s = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("Inventory.txt");
		TestAdmin ta = new TestAdmin(f.getName());

		boolean program = true;
		int choice;

		do {
			showManu();
			choice = s.nextInt();

			switch (choice) {
			case 1:
				printAllQuestions(ta);
				break;
			case 2:
				addNewQuestion(ta);
				break;
			case 3:
				updateQuestion(ta);
				break;
			case 4:
				deleteQuestion(ta);
				break;
			case 5:
				addNewAnswerToExistingQuestion(ta);
				break;
			case 6:
				updateAnAnswer(ta);
				break;
			case 7:
				deleteAnAnswer(ta);
				break;
			case 8:
				manualGenerationOfTest(ta);
				break;
			case 9:
				automaticGenerationOfTest(ta);
				break;
			case -1:
				exitProgram(ta, f);
				program = false;
				break;
			default:
				showManu();
				choice = s.nextInt();
				break;
			} // switch
		} while (program);
	} // main

	public static void automaticGenerationOfTest(TestAdmin ta) throws FileNotFoundException {
		s.nextLine(); // clean the buffer

		System.out.println();
		System.out.println("there are " + ta.getNumOfQuestions() + " questions in the inventory");
		System.out.println();
		ta.printQuestionsOnly();
		System.out.println();

		System.out.println("Please enter the number of questions you want the test to have:");
		int testNumOfQuestions = s.nextInt();
		s.nextLine(); // clean the buffer

		while (testNumOfQuestions > ta.getNumOfQuestions() || testNumOfQuestions < 1) {
			System.out.println(
					"The test need to have between 1 and " + ta.getNumOfQuestions() + " questions, choose again:");
			testNumOfQuestions = s.nextInt();
			s.nextLine(); // clean the buffer
		} // while

		Test test = ta.automaticTest(testNumOfQuestions);

		StringBuilder testTemp = new StringBuilder(test.getName());
		testTemp.append(".txt");
		File f = new File(testTemp.toString());
		PrintWriter pw = new PrintWriter(f.getName());
		pw.println(test.toString());
		pw.close();

		StringBuilder testSolutionTemp = new StringBuilder(test.getName());
		testSolutionTemp.append(" - Solution.txt");
		File f2 = new File(testSolutionTemp.toString());
		PrintWriter pw2 = new PrintWriter(f2.getName());
		pw2.println(test.toStringWithSolutions());
		pw2.close();
		
		System.out.println("The test has been created and printed!");

		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	} // static void automaticGenerationOfTest(TestAdmin ta)

	public static void manualGenerationOfTest(TestAdmin ta) throws FileNotFoundException {
		s.nextLine(); // clean the buffer

		System.out.println();
		System.out.println("there are " + ta.getNumOfQuestions() + " questions in the inventory");
		System.out.println();
		ta.printQuestionsOnly();
		System.out.println();

		System.out.println("Please enter the number of questions you want the test to have:");
		int testNumOfQuesetions = s.nextInt();
		s.nextLine(); // clean the buffer

		while (testNumOfQuesetions > ta.getNumOfQuestions() || testNumOfQuesetions < 1) {
			System.out.println(
					"The test need to have between 1 and " + ta.getNumOfQuestions() + " questions, choose again:");
			testNumOfQuesetions = s.nextInt();
			s.nextLine(); // clean the buffer
		} // while

		System.out.println("To each question you choose, the system will add 2 more answers:");
		System.out.println("\t1. \"All answers are false\"");
		System.out.println("\t2. \"There's more then 1 true answers\"");
		System.out.println();

		ta.printQuestionsOnly();
		System.out.println();

		System.out.println("Please choose how manny answers you want each question to have:");
		int howMannyAnswers = s.nextInt();
		s.nextLine(); // clean the buffer

		int[] questionChoices = new int[testNumOfQuesetions];
		int[][] answerChoices = new int[testNumOfQuesetions][howMannyAnswers];
		for (int i = 0; i < testNumOfQuesetions; i++) {
			System.out.println("Please enter a number for a question you want the test to have");
			int tempChoice = s.nextInt();
			questionChoices[i] = tempChoice;
			s.nextLine(); // clean the buffer

			while (!(ta.checkIfThereIsAQuestion(tempChoice))) {
				System.out.println("There isn't a question with that number, choose again:");
				tempChoice = s.nextInt();
				s.nextLine(); // clean the buffer
			}

			System.out.println("Question " + tempChoice + " has " + ta.getNumOfAnswers(tempChoice) + " answers");
			System.out.println("These are the answers for question number " + tempChoice);
			ta.printAnswersAndCorrecness(tempChoice);

			for (int j = 0; j < howMannyAnswers; j++) {
				System.out.println("Please choose an answer you want to include in the test:");
				int tempAnswerChoice = s.nextInt();
				answerChoices[i][j] = tempAnswerChoice;
				s.nextLine(); // clean the buffer

				while (!(ta.checkIfThereIsAnAnswer(tempAnswerChoice))) {
					System.out.println("There isn't an answer with that number, choose again:");
					tempAnswerChoice = s.nextInt();
					s.nextLine(); // clean the buffer
				}

				System.out.println();
			} // for j
		} // for i

		Test test = ta.manualTest(testNumOfQuesetions, howMannyAnswers, questionChoices, answerChoices);

		StringBuilder testTemp = new StringBuilder(test.getName());
		testTemp.append(".txt");
		File f = new File(testTemp.toString());
		PrintWriter pw = new PrintWriter(f.getName());
		pw.println(test.toString());
		pw.close();

		StringBuilder testSolutionTemp = new StringBuilder(test.getName());
		testSolutionTemp.append(" - Solution.txt");
		File f2 = new File(testSolutionTemp.toString());
		PrintWriter pw2 = new PrintWriter(f2.getName());
		pw2.println(test.toStringWithSolutions());
		pw2.close();
		
		System.out.println("The test has been created and printed!");

		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	} // static void manualGenerationOfTest(TestAdmin ta)

	public static void exitProgram(TestAdmin ta, File f) throws FileNotFoundException {
		s.nextLine(); // clean the buffer

		System.out.println(
				"You chose to exit the program, do you wish to save your progress in the inventory? enter \'y\' or \'Y\' for yes, \'n\' or \'N\' for no");
		char saveProgressChoice = s.next().charAt(0);
		s.nextLine(); // clean the buffer

		boolean saveBoolean = true;
		switch (saveProgressChoice) {
		case 'n':
		case 'N':
			saveBoolean = false;
			break;
		case 'y':
		case 'Y':
			PrintWriter pw = new PrintWriter(f);
			ta.printToFile(pw);
			pw.close();
			break;
		default:
			System.out.println(
					"You chose to exit the program, do you wish to save your progress in the inventory? enter \'y\' or \'Y\' for yes, \'n\' or \'N\' for no");
			saveProgressChoice = s.next().charAt(0);
			s.nextLine(); // clean the buffer
			break;
		} // switch
		System.out.println("Thank you for using the Test managment program!");
	} // static void exitProgram(TestAdmin ta, File f)

	public static void deleteAnAnswer(TestAdmin ta) {
		s.nextLine(); // clean the buffer

		System.out.println();
		System.out.println("Please enter the number of the question the answer belongs to:");
		ta.printQuestionsOnly();
		int qustionToDeleteAnswerNumber = s.nextInt();

		s.nextLine(); // clean the buffer
		while (!(ta.checkIfThereIsAQuestion(qustionToDeleteAnswerNumber))) {
			System.out.println("There isn't a question with that number, choose again:");
			qustionToDeleteAnswerNumber = s.nextInt();
			s.nextLine(); // clean the buffer
		}

		System.out.println("Please enter the number of the answer you want to delete:");
		ta.printAnswersOnly(qustionToDeleteAnswerNumber);
		int answerDeleteChoice = s.nextInt();
		s.nextLine(); // clean the buffer

		while (!(ta.checkIfThereIsAnAnswer(answerDeleteChoice))) {
			System.out.println("There isn't an answer with that number, choose again:");
			answerDeleteChoice = s.nextInt();
			s.nextLine(); // clean the buffer
		}

		ta.deleteAnswer(qustionToDeleteAnswerNumber, answerDeleteChoice);

		System.out.println("The answer has been deleted");

		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	}
	

	public static void updateAnAnswer(TestAdmin ta) {
		s.nextLine(); // clean the buffer

		System.out.println();
		System.out.println("Please enter the number of the question the answer belongs to:");
		ta.printQuestionsOnly();
		int qustionToUpdateAnswerNumber = s.nextInt();

		s.nextLine(); // clean the buffer
		while (!(ta.checkIfThereIsAQuestion(qustionToUpdateAnswerNumber))) {
			System.out.println("There isn't a question with that number, choose again:");
			qustionToUpdateAnswerNumber = s.nextInt();
			s.nextLine(); // clean the buffer
		}

		System.out.println("Please enter the number of the answer you want to update:");
		ta.printAnswersOnly(qustionToUpdateAnswerNumber);
		int answerChoice = s.nextInt();
		s.nextLine(); // clean the buffer

		while (!(ta.checkIfThereIsAnAnswer(answerChoice))) {
			System.out.println("There isn't an answer with that number, choose again:");
			answerChoice = s.nextInt();
			s.nextLine(); // clean the buffer
		}

		System.out.println("Enter the new answer and it's correctness:");
		String temp = s.nextLine();
		boolean bTemp = s.nextBoolean();
		ta.updateAnswer(qustionToUpdateAnswerNumber, answerChoice, temp, bTemp);

		s.nextLine(); // clean the buffer

		System.out.println("The answer has been updated");

		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	} // static void updateAnAnswer(TestAdmin ta)

	public static void addNewAnswerToExistingQuestion(TestAdmin ta) {
		s.nextLine(); // clean the buffer

		System.out.println();
		System.out.println("Please enter the number of the question to add answer to:");
		ta.printQuestionsOnly();
		int qustionToAddAnswerNumber = s.nextInt();

		s.nextLine(); // clean the buffer
		while (!(ta.checkIfThereIsAQuestion(qustionToAddAnswerNumber))) {
			System.out.println("There isn't a question with that number, choose again:");
			qustionToAddAnswerNumber = s.nextInt();
			s.nextLine(); // clean the buffer
		}

		System.out.println("Please enter the new answer and it's correctness");
		Answer answerTemp = new Answer(s.nextLine(), s.nextBoolean());
		s.nextLine(); // clean the buffer
		if (!(ta.addAnswerToExistingQuestion(qustionToAddAnswerNumber, answerTemp))) {
			System.out.println("The answer wasn't added");
		}
		System.out.println("The answer was added");

		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

	} // addNewAnswer(TestAdmin ta)

	public static void deleteQuestion(TestAdmin ta) {
		s.nextLine(); // clean the buffer
		System.out.println();
		System.out.println("please enter the number of the question you want to delete:");
		ta.printQuestionsOnly();
		int numOfQuestionChoice = s.nextInt();
		s.nextLine(); // clean the buffer

		while (!(ta.checkIfThereIsAQuestion(numOfQuestionChoice))) {
			System.out.println("There isn't a question with that number, choose again:");
			numOfQuestionChoice = s.nextInt();
			s.nextLine(); // clean the buffer
		}

		ta.deleteQuestion(numOfQuestionChoice);
		System.out.println("The question has been deleted");

		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	} // static void deleteQuestion(TestAdmin ta)

	public static void updateQuestion(TestAdmin ta) {
		s.nextLine(); // clean the buffer
		System.out.println();
		System.out.println("please enter the number of the question you want to update:");
		ta.printQuestionsOnly();
		int numOfQuestionChoice = s.nextInt();
		s.nextLine(); // clean the buffer

		while (!(ta.checkIfThereIsAQuestion(numOfQuestionChoice))) {
			System.out.println("There isn't a question with that number, choose again:");
			numOfQuestionChoice = s.nextInt();
			s.nextLine(); // clean the buffer
		}

		System.out.println("Enter the new question:");
		String temp = s.nextLine();
		ta.updateQuestion(numOfQuestionChoice, temp);
		System.out.println("The question has been updated");

		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	} // static void updateQuestion(TestAdmin ta)

	public static void addNewQuestion(TestAdmin ta) {
		System.out.println();

		s.nextLine(); // clean the buffer
		System.out.println("Please enter the new Question:");
		Question questionTemp = new Question(s.nextLine());
		boolean fcase2 = true;
		do {
			System.out.println(
					"Would you like to add an answer to the Question? enter \'y\' or \'Y\' for yes, \'n\' or \'N\' for no");
			char addAnswerChoice = s.next().charAt(0);
			s.nextLine(); // clean the buffer
			switch (addAnswerChoice) {
			case 'n':
			case 'N':
				fcase2 = false;
				break;
			case 'y':
			case 'Y':
				System.out.println("Please enter the answer and it's correctness: ");
				Answer answerTemp = new Answer(s.nextLine(), s.nextBoolean());
				s.nextLine(); // clean the buffer
				questionTemp.addAnswer(answerTemp);
				break;
			default:
				System.out.println(
						"Would you like to add an answer to the Question? enter \'y\' or \'Y\' for yes, \'n\' or \'N\' for no");
				addAnswerChoice = s.next().charAt(0);
				s.nextLine(); // clean the buffer
			} // switch
		} while (fcase2);
		if (!(ta.addQuestion(questionTemp))) {
			System.out.println("The question wasn't added");
		}
		System.out.println("The question was added to the inventory");

		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	} // static void addQuestion(TestAdmin ta)

	public static void printAllQuestions(TestAdmin ta) {
		System.out.println();
		ta.printAllQuestions();
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	} // void printAllQuestions(TestAdmin ta)

	public static void showManu() {
		System.out.println("Welcome to the test managment program, please choose one of the following options:");
		System.out.println("(1) - Print all the questions in the inventory and the solutions");
		System.out.println("(2) - Add a new question");
		System.out.println("(3) - Update a question");
		System.out.println("(4) - Delete a question");
		System.out.println("(5) - Add a new answer to an exiting question");
		System.out.println("(6) - Update an answer");
		System.out.println("(7) - Delete an answer");
		System.out.println("(8) - Manual generation of a test");
		System.out.println("(9) - Automatic generation of a test");
		System.out.println("(-1) - Exit the program");
	} // void showManu()

}// Program class
