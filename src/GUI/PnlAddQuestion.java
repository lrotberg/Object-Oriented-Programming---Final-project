package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;
import BL.*;

public class PnlAddQuestion extends JPanel implements Updateable {

	private SpringLayout addQuestionLayout;
	private JLabel lblQuestionText;
	private JTextArea txaQuestion;
	private JButton bntAddQuestionTxt;
	private JLabel lblAnswerTxt;
	private JTextArea txaAnswer;
	private JButton bntAddAnswerTxt;
	private JLabel lblAllAnswers;
	private JLabel lblIsTrue;
	private JCheckBox chkIsTrue;
	private JTextPane txpShowQuestion;
	private JButton bntSave;
	private NewQuestion tempQuestion;
	private Answer tempAnswer;
	
	public PnlAddQuestion (TestAdminForGUI testAdmin){
		
		addQuestionLayout = new SpringLayout ();
		lblQuestionText = new JLabel ("Enter your question text:");
		txaQuestion = new JTextArea ("", 3, 30);
		bntAddQuestionTxt = new JButton ("Add Question");
		lblAnswerTxt = new JLabel ("Enter answer text:");
		txaAnswer= new JTextArea ("", 3, 30);
		lblIsTrue = new JLabel ("Is true?");
		chkIsTrue = new JCheckBox ();
		bntAddAnswerTxt = new JButton ("Add Answer");
		lblAllAnswers = new JLabel ("All Answers");
		txpShowQuestion = new JTextPane();
		
		txpShowQuestion.setPreferredSize(new Dimension(600, 350));
		bntSave = new JButton("save question");
		
		bntAddQuestionTxt.setEnabled(false);
		txaAnswer.setEnabled(false);
		chkIsTrue.setEnabled(false);
		bntAddAnswerTxt.setEnabled(false);
		
		txaQuestion.addKeyListener(new KeyListener() {		
			@Override
			public void keyTyped(KeyEvent e) {
				if(txaQuestion.getText().equals("")){
					bntAddQuestionTxt.setEnabled(false);								
				}else{
					bntAddQuestionTxt.setEnabled(true);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}		
			@Override
			public void keyPressed(KeyEvent e) {}
		});
		
		bntAddQuestionTxt.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				tempQuestion = new NewQuestion(txaQuestion.getText());
				if(!testAdmin.addQuestion(tempQuestion)){
					JOptionPane.showMessageDialog(null, "Question couldn't be added");
				}else{
					JOptionPane.showMessageDialog(null, "Question was added successfully");
					txaQuestion.setEnabled(false);
					bntAddQuestionTxt.setEnabled(false);
					txaAnswer.setEnabled(true);
					chkIsTrue.setEnabled(true);
					bntAddAnswerTxt.setEnabled(true);
					txpShowQuestion.setText(testAdmin.getQuestionsVector().get(testAdmin.getNumOfQuestions()-1).toString());
				}
			}
		});
		
		bntAddAnswerTxt.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				int questionIndex = testAdmin.getNumOfQuestions();
				String newAnswer = txaAnswer.getText();
				boolean answerIsCorrect;
				
				if(chkIsTrue.isSelected() == true){
					answerIsCorrect = true;
				}else{
					answerIsCorrect = false;
				}
				
				tempAnswer = new Answer(newAnswer , answerIsCorrect);
				
				if(testAdmin.addAnswerToExistingQuestion(questionIndex, tempAnswer)){
					JOptionPane.showMessageDialog(null, "Answer was added successfully");
					txaAnswer.setText("");
					chkIsTrue.setSelected(false);
				}else{
					JOptionPane.showMessageDialog(null, "Question can have a max of 10 Answers, delete some and try again");
				};
				txpShowQuestion.setText(testAdmin.getQuestionsVector().get(questionIndex-1).toStringWithCorretness());			
			}
		});
		
		bntSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					save(testAdmin);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "The question was saved to the Inventory");
			}
		});
		
		add(lblQuestionText);
		add(txaQuestion);
		add(bntAddQuestionTxt);
		add(lblAnswerTxt);
		add(txaAnswer);
		add(lblIsTrue);
		add(chkIsTrue);
		add(bntAddAnswerTxt);
		add(lblAllAnswers);
		add(txpShowQuestion);
		add(bntSave);
		
		addQuestionLayout.putConstraint(SpringLayout.WEST, lblQuestionText, 10, SpringLayout.WEST, this);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, lblQuestionText, 10, SpringLayout.NORTH, this);
		
		addQuestionLayout.putConstraint(SpringLayout.WEST, txaQuestion, 10, SpringLayout.EAST, lblQuestionText);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, txaQuestion, 0, SpringLayout.NORTH, lblQuestionText);
		
		addQuestionLayout.putConstraint(SpringLayout.EAST, bntAddQuestionTxt, 0, SpringLayout.EAST, txpShowQuestion);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, bntAddQuestionTxt, 10, SpringLayout.SOUTH, txaQuestion);
		
		addQuestionLayout.putConstraint(SpringLayout.WEST, lblAnswerTxt, 0, SpringLayout.WEST, lblQuestionText);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, lblAnswerTxt, 10, SpringLayout.SOUTH, bntAddQuestionTxt);
		
		addQuestionLayout.putConstraint(SpringLayout.WEST, txaAnswer, 0, SpringLayout.WEST, txaQuestion);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, txaAnswer, 0, SpringLayout.NORTH, lblAnswerTxt);
		
		addQuestionLayout.putConstraint(SpringLayout.WEST, lblIsTrue, 0, SpringLayout.WEST, lblQuestionText);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, lblIsTrue, 10, SpringLayout.SOUTH, lblAnswerTxt);
		
		addQuestionLayout.putConstraint(SpringLayout.WEST, chkIsTrue, 10, SpringLayout.EAST, lblIsTrue);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, chkIsTrue, 0, SpringLayout.NORTH, lblIsTrue);
		
		addQuestionLayout.putConstraint(SpringLayout.EAST, bntAddAnswerTxt, 0, SpringLayout.EAST, bntAddQuestionTxt);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, bntAddAnswerTxt, 10, SpringLayout.SOUTH, chkIsTrue);
		
		addQuestionLayout.putConstraint(SpringLayout.WEST, lblAllAnswers, 0, SpringLayout.WEST, lblQuestionText);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, lblAllAnswers, 15, SpringLayout.SOUTH, bntAddAnswerTxt);
		
		addQuestionLayout.putConstraint(SpringLayout.WEST, txpShowQuestion, 0, SpringLayout.WEST, txaQuestion);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, txpShowQuestion, 0, SpringLayout.NORTH, lblAllAnswers);

		addQuestionLayout.putConstraint(SpringLayout.EAST, bntSave, 0, SpringLayout.EAST, bntAddQuestionTxt);
		addQuestionLayout.putConstraint(SpringLayout.NORTH, bntSave, 10, SpringLayout.SOUTH, txpShowQuestion);
		
		setLayout(addQuestionLayout);
		
		setVisible(true);
		
	} // AddQuestionGui ()

	@Override
	public void save(TestAdminForGUI testAdmin) throws FileNotFoundException {
		testAdmin.printToFile();
	}

	@Override
	public void update(TestAdminForGUI testAdmin) {
		// already took care of this
	}
	
} // PnlAddQuestion Class
