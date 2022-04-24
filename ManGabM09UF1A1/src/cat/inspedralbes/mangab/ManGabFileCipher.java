package cat.inspedralbes.mangab;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManGabFileCipher extends JFrame implements ItemListener{

		private JPanel contentPane;
		private JTextField txtSource;
		private JTextField txtPasswd;
		private JTextField txtDestination;
		private JComboBox<String> comboBox1;
		private JComboBox<String> comboBox2;
		private JComboBox<String> comboBox3;
		private JComboBox comboBoxHash;
		private JComboBox comboBoxKeyLength;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ManGabFileCipher frame = new ManGabFileCipher();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		/**
		 * Create the frame.
		 */
		public ManGabFileCipher() {
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {

					String[] opcions = { "Exit", "Cancel" };
					int n = JOptionPane.showOptionDialog(null, "Vols tancar la finestra?", "Exit",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcions, opcions[1]);
					if (n == 0) {
						System.exit(0);
					}

				}
			});
				
			setTitle("ManGabA7FileCipher");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 550, 441);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Method:");
			lblNewLabel.setBounds(12, 12, 70, 15);
			contentPane.add(lblNewLabel);
			
			comboBox1 = new JComboBox<String>();
			comboBox1.addItemListener(this);
			comboBox1.setModel(new DefaultComboBoxModel<String>(new String[] {"AES", "DES"}));		
			comboBox1.setBounds(78, 7, 55, 24);
			contentPane.add(comboBox1);
			
			JLabel lblNewLabel_1 = new JLabel("Bloc Type:");
			lblNewLabel_1.setBounds(152, 12, 72, 15);
			contentPane.add(lblNewLabel_1);
			
			comboBox2 = new JComboBox<String>();
			comboBox2.addItemListener(this);
			comboBox2.setModel(new DefaultComboBoxModel<String>(new String[] {"ECB", "CBC"}));
			comboBox2.setBounds(229, 7, 55, 24);
			contentPane.add(comboBox2);
			
			JLabel lblPadding = new JLabel("Padding:");
			lblPadding.setBounds(302, 12, 70, 15);
			contentPane.add(lblPadding);
			
			comboBox3 = new JComboBox<String>();
			comboBox3.addItemListener(this);
			comboBox3.setModel(new DefaultComboBoxModel(new String[] {"NoPadding", "PKCS5Padding"}));
			comboBox3.setBounds(372, 7, 139, 24);
			contentPane.add(comboBox3);
			
			JLabel lblNewLabel_2 = new JLabel("KeyLength:");
			lblNewLabel_2.setBounds(12, 81, 90, 15);
			contentPane.add(lblNewLabel_2);
			
			comboBoxKeyLength = new JComboBox();
			comboBoxKeyLength.setModel(new DefaultComboBoxModel(new Integer[] {128, 192, 256}));
			comboBoxKeyLength.addItemListener(this);
			comboBoxKeyLength.setBounds(101, 76, 52, 20);
			contentPane.add(comboBoxKeyLength);
			
			JLabel lblNewLabel_3 = new JLabel("Hash:");
			lblNewLabel_3.setBounds(169, 81, 55, 15);
			contentPane.add(lblNewLabel_3);
			
			comboBoxHash = new JComboBox();
			comboBoxHash.setModel(new DefaultComboBoxModel(new String[] {"MD5", "SHA-1", "SHA-256"}));
			comboBoxHash.setBounds(212, 76, 90, 20);
			
			contentPane.add(comboBoxHash);
			
			JLabel lblNewLabel_4 = new JLabel("Passwd Key:");
			lblNewLabel_4.setBounds(318, 74, 95, 29);
			contentPane.add(lblNewLabel_4);
			
			JLabel lblNewLabel_5 = new JLabel("Source File:");
			lblNewLabel_5.setBounds(12, 152, 90, 15);
			contentPane.add(lblNewLabel_5);
			
			txtSource = new JTextField();
			txtSource.setBounds(152, 148, 236, 24);
			contentPane.add(txtSource);
			txtSource.setColumns(10);
			
			txtPasswd = new JTextField();
			txtPasswd.setBounds(412, 79, 114, 19);
			contentPane.add(txtPasswd);
			txtPasswd.setColumns(10);
			
			JButton btnSelect = new JButton("Select");
			btnSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					seleccionaFicherorSource();
				}
			});
			btnSelect.setBounds(397, 147, 78, 24);
			contentPane.add(btnSelect);
			
			JLabel lblNewLabel_6 = new JLabel("Destination File:");
			lblNewLabel_6.setBounds(12, 205, 121, 15);
			contentPane.add(lblNewLabel_6);
			
			txtDestination = new JTextField();
			txtDestination.setColumns(10);
			txtDestination.setBounds(152, 201, 236, 24);
			contentPane.add(txtDestination);
			
			JButton btnSelect_1 = new JButton("Select");
			btnSelect_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					seleccionarFicheroDestination();
					
				}
			});
			btnSelect_1.setBounds(397, 200, 78, 24);
			contentPane.add(btnSelect_1);
			
			JCheckBox chckbxNewCheckBox = new JCheckBox("Delete source");
			chckbxNewCheckBox.setBounds(12, 244, 129, 23);
			contentPane.add(chckbxNewCheckBox);
			
			JButton btnNewButton = new JButton("Transform");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					transformButton();
				}
			});
			btnNewButton.setBounds(274, 362, 117, 25);
			contentPane.add(btnNewButton);
			
			JButton btnReset = new JButton("Reset");
			btnReset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				
				txtPasswd.setText("");
				txtSource.setText("");
				txtDestination.setText("");
				comboBox1.setSelectedIndex(0);
				comboBox2.setSelectedIndex(0);
				comboBox3.setSelectedIndex(0);
				comboBoxKeyLength.setSelectedIndex(0);
				comboBoxHash.setSelectedIndex(0);
							
				}
			});
			btnReset.setBounds(397, 362, 117, 25);
			contentPane.add(btnReset);
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				if (e.getItem() instanceof String) {

					// Strings
					if (e.getItem().equals("AES")) {

						comboBoxKeyLength.setModel(new DefaultComboBoxModel(new Integer[] { 128, 192, 256 }));
						comboBoxKeyLength.setEnabled(true);

					} else {

						comboBoxKeyLength.setModel(new DefaultComboBoxModel(new Integer[] { 64 }));
						comboBoxKeyLength.setEnabled(false);
						System.out.println("Enter");
					}

				} else {
					
					if((int) e.getItem() == 128) {
						
						comboBoxHash.setModel(new DefaultComboBoxModel(new String[] {"MD5", "SHA-1", "SHA-256"}));
						comboBoxHash.setEnabled(true);
						
						
					}else {
						
						comboBoxHash.setModel(new DefaultComboBoxModel(new String[] {"SHA-256"}));
						comboBoxHash.setEnabled(false);
					}
				}
				 			
				//System.out.println(e.getItemSelectable());
				System.out.println(e.getItem());
			}
			
		}
		public JComboBox ComboBox1() {
			return comboBox1;
		}
		public JComboBox ComboBox_2() {
			return comboBox2;
		}
		public JComboBox ComboBox_3() {
			return comboBox3;
		}
		public JComboBox ComboBox_5() {
			return comboBoxHash;
		}
		public JComboBox ComboBox_4() {
			return comboBoxKeyLength;
		}
		
		public void seleccionarFicheroDestination() {
			
			JFileChooser file = new JFileChooser();
			file.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int seleccio = file.showOpenDialog(this);
			
			if(seleccio == 0) {
			File fichero = file.getSelectedFile();
			txtDestination.setText(fichero.getPath());
			
			}
			
		}
		
		public void seleccionaFicherorSource() {
			
			JFileChooser file = new JFileChooser();
			file.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int seleccio = file.showOpenDialog(this);
			
			if(seleccio == 0) {
			File fichero = file.getSelectedFile();
			txtSource.setText(fichero.getPath());
			
			}
		}
		
		public byte[] transformaFitxer(SecretKey sKey, byte[] data, int valor) {
			
			byte[] data2 = null;
			
			try {
				
				Cipher cipher = Cipher.getInstance(comboBox1.getActionCommand() + "/" + comboBox2.getActionCommand() + "/" + comboBox3.getActionCommand());
				
				if(comboBox2.equals("ECB")) {
					
					cipher.init(valor, sKey);
				
				}else {
					
					IvParameterSpec iv = new IvParameterSpec(IV_PARAM);
					cipher.init(valor, sKey, iv);
					data2 = cipher.doFinal(data);
				}
				
				
			}catch(Exception e) {
				
				System.err.println("Error en xifrar les dades " + e);
			}
			
			return data2;
		}
		
		public SecretKey passwordGenerator() {
			
			SecretKey skey = null;
			System.out.println("PASSWORD: " + txtPasswd.getText());
			
			if(comboBoxKeyLength.equals("128") || comboBoxKeyLength.equals("192") || comboBoxKeyLength.equals("256") || comboBoxKeyLength.equals("64")) {
				
				try {
					
					byte[] data = ((String) txtPasswd.getText()).getBytes();
					MessageDigest message = MessageDigest.getInstance(comboBoxHash.getActionCommand());
					byte[] hash = message.digest(data);
					
				} catch (Exception e) {

					System.err.println("Error en generar la clau: " + e);
				}
			}
			
			return skey;
		}
		
		public byte[] IV_PARAM = {0x00, 0x01, 0x02, 0x03,
				0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D,
				0x0E, 0x0F};
		
		public void transformButton() {
			
			SecretKey sKey = passwordGenerator();
			ManGabA01File file = new ManGabA01File();
			
			byte[] data = file.readFile(new File(txtSource.getText()));
			byte[] d;
			
			if(txtSource.getText().endsWith(".cry")) {
				
				d = transformaFitxer(sKey, data, 1);
				file.writeFile(d, txtSource.getText() + ".cry");
				
			}else {
				
				d = transformaFitxer(sKey, data, 2);
				file.writeFile(d, txtSource.getText());
			}
		}
}