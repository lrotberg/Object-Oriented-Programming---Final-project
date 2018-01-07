package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;
import BL.TestAdminForGUI;

public class PnlDeleteAnswer extends JPanel implements Updateable {

	private SpringLayout layDeleteAnswer;
	private JLabel lblQuestionChoice;
	private JComboBox<String> cmbQuestionChoice;
	private JTextPane txpSelectedQuestion;
	private JScrollPane scpTextScroller;
	private JLabel lblAnswerChoice;
	private JComboBox<String> cmbAnswerChoice;
	private JButton btnDeleteAnswer;
	private int questionChoice;
	private int answerChoice;

	public PnlDeleteAnswer(TestAdminForGUI testAdmin) {

		layDeleteAnswer = new SpringLayout();
		lblQuestionChoice = new JLabel("Please choose a question:");

		cmbQuestionChoice = new JComboBox<String>();
		update(testAdmin);

		cmbAnswerChoice = new JComboBox<String>();
		cmbAnswerChoice.setEnabled(false);

		cmbQuestionChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cmbQuestionChoice.getSelectedIndex() >= 0) {
					questionChoice = cmbQuestionChoice.getSelectedIndex();

					txpSelectedQuestion.setText(testAdmin.getQuestionsVector().elementAt(questionChoice).toStringWithCorretness());

					updateAnswers(testAdmin);

					cmbAnswerChoice.setEnabled(true);
				}
			}
		});

		txpSelectedQuestion = new JTextPane();
		txpSelectedQuestion.setPreferredSize(new Dimension(0, 150));

		scpTextScroller = new JScrollPane(txpSelectedQuestion);
		scpTextScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		lblAnswerChoice = new JLabel("Please choose an answer:");

		cmbAnswerChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnDeleteAnswer.setEnabled(true);
			}
		});

		// txtSelectedAnswer = new JTextArea("", 3, 30);
		btnDeleteAnswer = new JButton("Delete Answer");
		btnDeleteAnswer.setEnabled(false);

		btnDeleteAnswer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbQuestionChoice.getSelectedIndex() >= 0) {
					
					questionChoice = cmbQuestionChoice.getSelectedIndex();
					
					if(cmbAnswerChoice.getSelectedIndex() >= 0) {
						
						answerChoice = cmbAnswerChoice.getSelectedIndex();
						
						testAdmin.deleteAnswer(questionChoice, answerChoice);
						txpSelectedQuestion.setText(testAdmin.getQuestionsVector().elementAt(questionChoice).toString());
						JOptionPane.showMessageDialog(null, "Your answer has been deleted successfully");
						updateAnswers(testAdmin);
						
						try {
							save(testAdmin);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		setLayout(layDeleteAnswer);
		add(lblQuestionChoice);
		add(cmbQuestionChoice);
		add(scpTextScroller);
		add(lblAnswerChoice);
		add(cmbAnswerChoice);
		add(btnDeleteAnswer);

		layDeleteAnswer.putConstraint(layDeleteAnswer.WEST, lblQuestionChoice, 10, layDeleteAnswer.WEST, this);
		layDeleteAnswer.putConstraint(layDeleteAnswer.NORTH, lblQuestionChoice, 10, layDeleteAnswer.NORTH, this);

		layDeleteAnswer.putConstraint(layDeleteAnswer.WEST, cmbQuestionChoice, 0, layDeleteAnswer.WEST,
				lblQuestionChoice);
		layDeleteAnswer.putConstraint(layDeleteAnswer.NORTH, cmbQuestionChoice, 10, layDeleteAnswer.SOUTH,
				lblQuestionChoice);

		layDeleteAnswer.putConstraint(layDeleteAnswer.WEST, scpTextScroller, 0, layDeleteAnswer.WEST,
				cmbQuestionChoice);
		layDeleteAnswer.putConstraint(layDeleteAnswer.EAST, scpTextScroller, 0, layDeleteAnswer.EAST,
				cmbQuestionChoice);
		layDeleteAnswer.putConstraint(layDeleteAnswer.NORTH, scpTextScroller, 10, layDeleteAnswer.SOUTH,
				cmbQuestionChoice);

		layDeleteAnswer.putConstraint(layDeleteAnswer.WEST, lblAnswerChoice, 0, layDeleteAnswer.WEST,
				lblQuestionChoice);
		layDeleteAnswer.putConstraint(layDeleteAnswer.NORTH, lblAnswerChoice, 10, layDeleteAnswer.SOUTH,
				scpTextScroller);

		layDeleteAnswer.putConstraint(layDeleteAnswer.WEST, cmbAnswerChoice, 0, layDeleteAnswer.WEST,
				lblQuestionChoice);
		layDeleteAnswer.putConstraint(layDeleteAnswer.NORTH, cmbAnswerChoice, 10, layDeleteAnswer.SOUTH,
				lblAnswerChoice);

		layDeleteAnswer.putConstraint(layDeleteAnswer.EAST, btnDeleteAnswer, 0, layDeleteAnswer.EAST, scpTextScroller);
		layDeleteAnswer.putConstraint(layDeleteAnswer.NORTH, btnDeleteAnswer, 20, layDeleteAnswer.SOUTH,
				cmbAnswerChoice);

		setVisible(true);

	}
	
	public void updateQuestions(TestAdminForGUI testAdmin) {
		cmbQuestionChoice.removeAllItems();
		for (int i = 0; i < testAdmin.getQuestionsVector().size(); i++) {
			cmbQuestionChoice.addItem(testAdmin.getQuestionsVector().elementAt(i).getQuestion());
		}
	}
	
	public void updateAnswers(TestAdminForGUI testAdmin) {
		if(questionChoice >= 0) {
			cmbAnswerChoice.removeAllItems();

			for (int i = 0; i < testAdmin.getQuestionsVector().elementAt(questionChoice).getNumOfAnswers(); i++) {
				cmbAnswerChoice.addItem(testAdmin.getQuestionsVector().elementAt(questionChoice).getAnswer(i).getAnswer());
			} // for
		} // if
	}

	@Override
	public void save(TestAdminForGUI testAdmin) throws FileNotFoundException {
		testAdmin.printToFile();
	}

	@Override
	public void update(TestAdminForGUI testAdmin) {
		updateQuestions(testAdmin);
	}

} // DeleteAnswerGUI class
