package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import BL.TestAdminForGUI;

public class PnlManualTestCreation extends JPanel implements Updateable {

	private SpringLayout layManualTest;
	private JLabel lblChooseQuestion;
	private JComboBox<String> cmbQuestions;
	private JLabel lblChooseAnswers;
	private JList<String> lstAnswers;
	private JScrollPane scpAnswers;
	private JButton btnAddQuestion;
	private JLabel lblTestPreview;
	private JTextPane txpTestPreview;
	private JScrollPane scpTestPreview;
	private JButton btnSaveTest;
	private int questionChoice;

	public PnlManualTestCreation(TestAdminForGUI testAdmin) {

		layManualTest = new SpringLayout();

		lblChooseQuestion = new JLabel("Please choose a question to add to the test:");
		cmbQuestions = new JComboBox<String>();
		update(testAdmin);

		cmbQuestions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				questionChoice = cmbQuestions.getSelectedIndex();
				updateAnswers(testAdmin, questionChoice);
				lstAnswers.setEnabled(true);
			}
		});

		lblChooseAnswers = new JLabel("Please choose the answers for the question:");

		lstAnswers = new JList<String>();
		lstAnswers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstAnswers.setPreferredSize(new Dimension(0, 0));
		lstAnswers.setEnabled(false);

		lstAnswers.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				btnAddQuestion.setEnabled(true);

			}
		});

		scpAnswers = new JScrollPane(lstAnswers);
		scpAnswers.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		btnAddQuestion = new JButton("Add Question to the test");
		btnAddQuestion.setEnabled(false);

		lblTestPreview = new JLabel("Test preview");
		txpTestPreview = new JTextPane();
		txpTestPreview.setPreferredSize(new Dimension(0, 0));
		txpTestPreview.setEnabled(false);

		scpTestPreview = new JScrollPane(txpTestPreview);
		scpTestPreview.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		btnSaveTest = new JButton("Save Test");
		btnSaveTest.setEnabled(false);

		setLayout(layManualTest);
		add(lblChooseQuestion);
		add(cmbQuestions);
		add(lblChooseAnswers);
		add(scpAnswers);
		add(btnAddQuestion);
		add(lblTestPreview);
		add(scpTestPreview);
		add(btnSaveTest);

		layManualTest.putConstraint(layManualTest.WEST, lblChooseQuestion, 10, layManualTest.WEST, this);
		layManualTest.putConstraint(layManualTest.NORTH, lblChooseQuestion, 10, layManualTest.NORTH, this);

		layManualTest.putConstraint(layManualTest.WEST, cmbQuestions, 0, layManualTest.WEST, lblChooseQuestion);
		layManualTest.putConstraint(layManualTest.NORTH, cmbQuestions, 10, layManualTest.SOUTH, lblChooseQuestion);

		layManualTest.putConstraint(layManualTest.WEST, lblChooseAnswers, 0, layManualTest.WEST, cmbQuestions);
		layManualTest.putConstraint(layManualTest.NORTH, lblChooseAnswers, 20, layManualTest.SOUTH, cmbQuestions);

		layManualTest.putConstraint(layManualTest.WEST, scpAnswers, 0, layManualTest.WEST, cmbQuestions);
		layManualTest.putConstraint(layManualTest.EAST, scpAnswers, 0, layManualTest.EAST, cmbQuestions);
		layManualTest.putConstraint(layManualTest.NORTH, scpAnswers, 20, layManualTest.SOUTH, lblChooseAnswers);

		layManualTest.putConstraint(layManualTest.WEST, btnAddQuestion, 0, layManualTest.WEST, lblChooseQuestion);
		layManualTest.putConstraint(layManualTest.NORTH, btnAddQuestion, 20, layManualTest.SOUTH, scpAnswers);

		layManualTest.putConstraint(layManualTest.WEST, lblTestPreview, 20, layManualTest.EAST, cmbQuestions);
		layManualTest.putConstraint(layManualTest.NORTH, lblTestPreview, 0, layManualTest.NORTH, lblChooseQuestion);

		layManualTest.putConstraint(layManualTest.WEST, scpTestPreview, 0, layManualTest.WEST, lblTestPreview);
		layManualTest.putConstraint(layManualTest.EAST, scpTestPreview, -15, layManualTest.EAST, this);
		layManualTest.putConstraint(layManualTest.NORTH, scpTestPreview, 10, layManualTest.SOUTH, lblChooseQuestion);
		layManualTest.putConstraint(layManualTest.SOUTH, scpTestPreview, -10, layManualTest.NORTH, btnSaveTest);

		layManualTest.putConstraint(layManualTest.EAST, btnSaveTest, 0, layManualTest.EAST, scpTestPreview);
		layManualTest.putConstraint(layManualTest.SOUTH, btnSaveTest, -10, layManualTest.SOUTH, this);

	} // PnlManualTestCreation(TestAdminForGUI testAdmin)

	public void updateAnswers(TestAdminForGUI testAdmin, int questionChoice) {
		lstAnswers.removeAll();

		String[] answers = new String[testAdmin.getQuestionsVector().elementAt(questionChoice).getNumOfAnswers()];
		for (int i = 0; i < answers.length; i++) {
			answers[i] = testAdmin.getQuestionsVector().elementAt(questionChoice).getAnswerVector().get(i).toString();
		}

		lstAnswers.setListData(answers);
	}

	@Override
	public void save(TestAdminForGUI testAdmin) throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(TestAdminForGUI testAdmin) {
		cmbQuestions.removeAllItems();
		
		for (int i = 0; i < testAdmin.getQuestionsVector().size(); i++) {
			cmbQuestions.addItem(testAdmin.getQuestionsVector().elementAt(i).getQuestion());
		} // for
	}

} // public class PnlManualTestCreation extends JPanel
