import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class DCSSGui {

	private JFrame frame;
	private DCSSMapper dcssMap;
	private boolean exclusives;
	private String startName;
	private String endName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DCSSGui window = new DCSSGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DCSSGui() {
		dcssMap = new DCSSMapper();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ProcessFile();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);	

		ArrayList<String> names = dcssMap.SortedNames();
		
		final JComboBox<String> startNames = new JComboBox<>();
		startNames.setBounds(103, 53, 223, 28);
		for (String s : names){
			startNames.addItem(s);
		}
		startName = (String) startNames.getSelectedItem(); // Initial Value
		startNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				startName = (String) startNames.getSelectedItem();
			}
		});
		frame.getContentPane().add(startNames);
		
		final JComboBox<String> endNames = new JComboBox<>();
		endNames.setBounds(103, 137, 223, 28);
		for (String s : names){
			endNames.addItem(s);
		}		
		endName = (String) endNames.getSelectedItem(); // Initial Value
		endNames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
					endName = (String) endNames.getSelectedItem();
			}
		});
		frame.getContentPane().add(endNames);
		
		final JCheckBox chckbxIncludePreorderExclusives = new JCheckBox("Include Pre-Order Exclusives");
		chckbxIncludePreorderExclusives.setBounds(114, 182, 200, 23);
		exclusives = chckbxIncludePreorderExclusives.isSelected();
		chckbxIncludePreorderExclusives.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				exclusives = chckbxIncludePreorderExclusives.isSelected();
			}
		});
		frame.getContentPane().add(chckbxIncludePreorderExclusives);
		
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBounds(33, 290, 363, 143);
		frame.getContentPane().add(textArea);
		
		JLabel startNameLabel = new JLabel("Starting Digimon");
		startNameLabel.setBounds(103, 22, 223, 23);
		frame.getContentPane().add(startNameLabel);
		
		JLabel endNameLabel = new JLabel("Ending Digimon");
		endNameLabel.setBounds(103, 103, 223, 23);
		frame.getContentPane().add(endNameLabel);
		
		JButton btnNewButton = new JButton("Go!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				textArea.setText(dcssMap.FindPath(startName, endName, exclusives));
			}
		});
		btnNewButton.setBounds(170, 209, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}

	private void ProcessFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("dcsslist.csv"));
			String strLine = br.readLine();

			while (strLine != null) {
				dcssMap.MapName(strLine);
				strLine = br.readLine();
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
