package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.ListSelectionListener;
import BL.TestAdminForGUI;

public class FrmMainWindow extends JFrame {

	private JPanel pnlMainWindow;
	private JSplitPane spltMain;
	private CardPanel pnlCards;
	private OptionsList lstOptions;
	private SpringLayout layMainWindow;
	private TestAdminForGUI testAdmin ;
	
	public FrmMainWindow(TestAdminForGUI testAdmin) {
		
		this.testAdmin = testAdmin;
		
		setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.65),
				(int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.6));
		
		pnlCards = new CardPanel();
		lstOptions = new OptionsList();
		
		spltMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lstOptions, pnlCards);
		spltMain.setDividerLocation(300);
		spltMain.setEnabled(false);
		add(spltMain);
		
		setLocationRelativeTo(null);
		
		setVisible(true);
		
	}

	class CardPanel extends JPanel {

		private CardLayout cl;
		private JPanel PnlTemp;
		private PnlShowInventory PnlShowInventory;
		private PnlAddQuestion PnlAddQuestion;
		private PnlEditQuestion PnlEditQuestion;
		private PnlDeleteQuestion PnlDeleteQuestion;
		private PnlAddAnswer PnlAddAnswer;
		private PnlEditAnswer PnlEditAnswer;
		private PnlDeleteAnswer PnlDeleteAnswer;
		private PnlAutomaticTestCreation PnlAutomaticTestCreation;
		private PnlManualTestCreation PnlManualTestCreation;

		public CardPanel() {

			cl = new CardLayout();
			setLayout(cl);

			PnlTemp = new JPanel();
			PnlShowInventory = new PnlShowInventory(testAdmin);
			PnlAddQuestion = new PnlAddQuestion(testAdmin);
			PnlEditQuestion = new PnlEditQuestion(testAdmin);
			PnlDeleteQuestion = new PnlDeleteQuestion(testAdmin);
			PnlAddAnswer = new PnlAddAnswer(testAdmin);
			PnlEditAnswer = new PnlEditAnswer(testAdmin);
			PnlDeleteAnswer = new PnlDeleteAnswer(testAdmin);
			PnlAutomaticTestCreation = new PnlAutomaticTestCreation(testAdmin);
			PnlManualTestCreation = new PnlManualTestCreation(testAdmin);

			add(PnlTemp, "0");
			add(PnlShowInventory, "1");
			add(PnlAddQuestion, "2");
			add(PnlEditQuestion, "3");
			add(PnlDeleteQuestion, "4");
			add(PnlAddAnswer, "5");
			add(PnlEditAnswer, "6");
			add(PnlDeleteAnswer, "7");
			add(PnlAutomaticTestCreation, "8");
			add(PnlManualTestCreation, "9");
			cl.show(this, "0");
			setVisible(true);

		} // public CardPanel()
		
		public void showAndUpdate(String index) {
			cl.show(PnlTemp,index);
		}

	} // class CardPanel extends JPanel

	class OptionsList extends JPanel {

		private JList<String> lstOptions;
		private String[] options = { "Show all questions", "Add a question", "Edit a question", "Delete a question",
				"Add an answer", "Edit an answer", "Delete an answer", "Automatic test creation",
				"Manual test creation" };

		public OptionsList() {
			lstOptions = new JList<String>(options);
			lstOptions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			setBackground(Color.WHITE);

			lstOptions.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					Integer pnlSelected = lstOptions.getSelectedIndex() + 1;
					pnlCards.cl.show(pnlCards, pnlSelected.toString());
					if (pnlCards.getComponent(pnlSelected) instanceof Updateable) {
						Updateable tempPanel = (Updateable) pnlCards.getComponent(pnlSelected);
						tempPanel.update(testAdmin);
					}
				}
			});
			add(lstOptions);
			setVisible(true);
		} // public OptionsList()
		
	} // class OptionsList extends JPanel

} // public class FrmMainWindow
