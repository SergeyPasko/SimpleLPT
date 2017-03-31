package spasko.simpleLPT;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import jnpout32.pPort;

public class Mainform extends JFrame {

	public static Mainform mf;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JComboBox<Integer> jComboBox_adr = null;
	private JTextField jTextField_data = null;
	private JButton jButton = null;
	private JButton jButtonTest = null;
	private JButton jButtonRead = null;
	private JTextField jTextField_pin = null;
	private pPort lpt = null;
	private Test test = null;

	public Mainform() {
		super();
		initialize();
	}

	private void initialize() {
		Mainform.mf = this;
		this.setSize(281, 210);
		lpt = new pPort();
		this.setContentPane(getJContentPane());
		this.setTitle("LPT");
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			JLabel jLabel6 = new JLabel("BIN:");
			jLabel6.setBounds(new Rectangle(184, 71, 78, 21));
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(6, 3, 6, 9);
			gridBagConstraints10.gridx = 2;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.ipadx = -91;
			gridBagConstraints10.ipady = 82;
			gridBagConstraints10.gridheight = 5;
			JLabel jLabel5 = new JLabel("Status: 889 - only read!");
			jLabel5.setBounds(new Rectangle(10, 27, 215, 16));
			JLabel jLabel4 = new JLabel();
			jLabel4.setText("Control: 890 - read and write");
			jLabel4.setBounds(new Rectangle(10, 49, 212, 16));
			JLabel jLabel3 = new JLabel("Data: 888 - read and write");
			jLabel3.setBounds(new Rectangle(10, 4, 211, 16));
			JLabel jLabel1 = new JLabel("Data:");
			jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1.setBounds(new Rectangle(9, 99, 76, 23));
			JLabel jLabel = new JLabel("Port:");
			jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel.setBounds(new Rectangle(10, 69, 75, 24));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTextField_adr());
			jContentPane.add(getJTextField_data());
			jContentPane.add(jLabel);
			jContentPane.add(jLabel1);
			jContentPane.add(getJButtonWrite());
			jContentPane.add(getJButtonTest());
			jContentPane.add(getJButtonRead());
			jContentPane.add(jLabel3);
			jContentPane.add(jLabel4);
			jContentPane.add(jLabel5);
			jContentPane.add(jLabel6);
			jContentPane.add(getJTextField_pin());
		}
		return jContentPane;
	}

	private JComboBox<Integer> getJTextField_adr() {
		if (jComboBox_adr == null) {
			jComboBox_adr = new JComboBox<Integer>();
			jComboBox_adr.addItem(888);
			jComboBox_adr.addItem(889);
			jComboBox_adr.addItem(890);
			jComboBox_adr.setSelectedIndex(0);
			jComboBox_adr.setBounds(new Rectangle(97, 69, 76, 25));
		}
		return jComboBox_adr;
	}

	private JTextField getJTextField_data() {
		if (jTextField_data == null) {
			jTextField_data = new JTextField();
			jTextField_data.setText("0");
			jTextField_data.setBounds(new Rectangle(97, 99, 76, 24));
			jTextField_data.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if (isNumeric(jTextField_data.getText())) {
						jTextField_pin.setText(Integer.toBinaryString(Integer.parseInt(jTextField_data.getText())));
						jTextField_data.setText("" + Integer.parseInt(jTextField_data.getText()));
					} else {
						jTextField_pin.setText("0");
						jTextField_data.setText("0");
					}
				}
			});
		}
		return jTextField_data;
	}

	private JButton getJButtonWrite() {
		if (jButton == null) {
			jButton = new JButton("WRITE");
			jButton.setBounds(new Rectangle(8, 136, 80, 30));
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lpt.output(Short.parseShort(jComboBox_adr.getSelectedItem().toString()),
							(byte) Integer.parseInt(jTextField_data.getText()));
				}
			});
		}
		return jButton;
	}

	private JButton getJButtonTest() {
		if (jButtonTest == null) {
			jButtonTest = new JButton();
			jButtonTest.setText("Test");
			jButtonTest.setBounds(new Rectangle(184, 136, 80, 30));
			jButtonTest.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (jButtonTest.getText().equals("Test")) {
						test = new Test(mf);
						test.start();
						jButtonTest.setText("Stop");
					} else {
						jButtonTest.setText("Test");
						test.requestStop();
					}

				}
			});
		}
		return jButtonTest;
	}

	private JButton getJButtonRead() {
		if (jButtonRead == null) {
			jButtonRead = new JButton("READ");
			jButtonRead.setBounds(new Rectangle(96, 136, 80, 30));
			jButtonRead.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jTextField_data.setText(
							Short.toString(lpt.input(Short.parseShort(jComboBox_adr.getSelectedItem().toString()))));
					jTextField_pin.setText(Integer.toBinaryString(Integer.parseInt(jTextField_data.getText())));
				}
			});
		}
		return jButtonRead;
	}

	private JTextField getJTextField_pin() {
		if (jTextField_pin == null) {
			jTextField_pin = new JTextField();
			jTextField_pin.setText("0");
			jTextField_pin.setEditable(false);
			jTextField_pin.setBounds(new Rectangle(185, 98, 76, 24));
			jTextField_pin.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if (!jTextField_pin.getText().equals(""))
						jTextField_data.setText("" + Integer.decode(jTextField_pin.getText()));
				}
			});
		}
		return jTextField_pin;
	}

	private static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	void setData(int step) {
		jTextField_data.setText(step + "");
		jTextField_pin.setText(Integer.toBinaryString(step));
	}
}
