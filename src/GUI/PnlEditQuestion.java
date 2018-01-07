package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import javax.swing.*;

import BL.TestAdminForGUI;

public class PnlEditQuestion extends JPanel implements Updateable {

	private SpringLayout layEditQuestion;
	private JLabel lblQuestionChoice;
	private JComboBox<String> cmbQuestionNumberChoice;
	private JTextPane txpQuestion;
	private JScrollPane scpTextScroller;
	private JLabel lblEnterText;
	private JTextArea txaNewQuestion;
	private JButton btnSaveQuestion;
	private int questionChoice;

	public PnlEditQuestion(TestAdminForGUI testAdmin) {

		layEditQuestion = new SpringLayout();
		lblQuestionChoice = new JLabel("Please Choose a question to edit:");

		cmbQuestionNumberChoice = new JComboBox<String>();
		updateInventory(testAdmin);

		cmbQuestionNumberChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbQuestionNumberChoice.getSelectedIndex() >= 0) {
					questionChoice = cmbQuestionNumberChoice.getSelectedIndex();

					txpQuestion.setText(testAdmin.getQuestionsVector().elementAt(questionChoice).toStringWithCorretness());
					txaNewQuestion.setEnabled(true);
				}
			}
		});

		txpQuestion = new JTextPane();
		txpQuestion.setPreferredSize(new Dimension(0, 150));
		scpTextScroller = new JScrollPane(txpQuestion);
		scpTextScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		lblEnterText = new JLabel("Enter the new question");
		txaNewQuestion = new JTextArea("", 3, 30);
		txaNewQuestion.setEnabled(false);

		txaNewQuestion.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txaNewQuestion.getText().equals("")) {
					btnSaveQuestion.setEnabled(false);
				} else {
					btnSaveQuestion.setEnabled(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		btnSaveQuestion = new JButton("Save question");
		btnSaveQuestion.setEnabled(false);

		btnSaveQuestion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				questionChoice = cmbQuestionNumberChoice.getSelectedIndex();

				String newQuestion = txaNewQuestion.getText();
				testAdmin.updateQuestion(questionChoice, newQuestion);

				JOptionPane.showMessageDialog(null, "The question was updated successfully");
				updateInventory(testAdmin);
				try {
					save(testAdmin);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		setLayout(layEditQuestion);
		add(lblQuestionChoice);
		add(cmbQuestionNumberChoice);
		add(scpTextScroller);
		add(lblEnterText);
		add(txaNewQuestion);
		add(btnSaveQuestion);

		layEditQuestion.putConstraint(layEditQuestion.WEST, lblQuestionChoice, 10, layEditQuestion.WEST, this);
		layEditQuestion.putConstraint(layEditQuestion.NORTH, lblQuestionChoice, 10, layEditQuestion.NORTH, this);

		layEditQuestion.putConstraint(layEditQuestion.WEST, cmbQuestionNumberChoice, 0, layEditQuestion.WEST,
				lblQuestionChoice);
		layEditQuestion.putConstraint(layEditQuestion.NORTH, cmbQuestionNumberChoice, 10, layEditQuestion.SOUTH,
				lblQuestionChoice);

		layEditQuestion.putConstraint(layEditQuestion.WEST, scpTextScroller, 0, layEditQuestion.WEST,
				cmbQuestionNumberChoice);
		layEditQuestion.putConstraint(layEditQuestion.EAST, scpTextScroller, 0, layEditQuestion.EAST,
				cmbQuestionNumberChoice);
		layEditQuestion.putConstraint(layEditQuestion.NORTH, scpTextScroller, 10, layEditQuestion.SOUTH,
				cmbQuestionNumberChoice);

		layEditQuestion.putConstraint(layEditQuestion.WEST, lblEnterText, 0, layEditQuestion.WEST, lblQuestionChoice);
		layEditQuestion.putConstraint(layEditQuestion.NORTH, lblEnterText, 10, layEditQuestion.SOUTH, scpTextScroller);

		layEditQuestion.putConstraint(layEditQuestion.WEST, txaNewQuestion, 0, layEditQuestion.WEST,
				cmbQuestionNumberChoice);
		layEditQuestion.putConstraint(layEditQuestion.EAST, txaNewQuestion, 0, layEditQuestion.EAST,
				cmbQuestionNumberChoice);
		layEditQuestion.putConstraint(layEditQuestion.NORTH, txaNewQuestion, 10, layEditQuestion.SOUTH, lblEnterText);

		layEditQuestion.putConstraint(layEditQuestion.EAST, btnSaveQuestion, 0, layEditQuestion.EAST, scpTextScroller);
		layEditQuestion.putConstraint(layEditQuestion.NORTH, btnSaveQuestion, 20, layEditQuestion.SOUTH,
				txaNewQuestion);

		setVisible(true);
	}

	public void updateInventory(TestAdminForGUI testAdmin) {
		cmbQuestionNumberChoice.removeAllItems();
		for (int i = 0; i < testAdmin.getQuestionsVector().size(); i++) {
			cmbQuestionNumberChoice.addItem(testAdmin.getQuestionsVector().elementAt(i).getQuestion());
		} // for
	}

	@Override
	public void save(TestAdminForGUI testAdmin) throws FileNotFoundException {
		testAdmin.printToFile();

	}

	@Override
	public void update(TestAdminForGUI testAdmin) {
		updateInventory(testAdmin);
	}

} // EditQuestionGUI
