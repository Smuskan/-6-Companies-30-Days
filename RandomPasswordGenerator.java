import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public class RandomPasswordGenerator extends JFrame {

   // Author: Muskan Shaikh

   private JTextField passwordField;
   private JSpinner lengthSpinner;
   private JCheckBox includeUppercase;
   private JCheckBox includeNumbers;
   private JCheckBox includeSpecialChars;
   private JButton generateButton;
   private JButton copyButton;

   private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
   private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   private static final String NUMBERS = "0123456789";
   private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:',.<>?";

   public RandomPasswordGenerator() {
      setTitle("Random Password Generator");
      setSize(400, 300);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(10, 10, 10, 10);

      // Create UI components
      JLabel lengthLabel = new JLabel("Password Length:");
      lengthSpinner = new JSpinner(new SpinnerNumberModel(12, 4, 20, 1));

      includeUppercase = new JCheckBox("Include Uppercase Letters");
      includeNumbers = new JCheckBox("Include Numbers");
      includeSpecialChars = new JCheckBox("Include Special Characters");

      passwordField = new JTextField();
      passwordField.setEditable(false);
      passwordField.setBackground(Color.LIGHT_GRAY);
      passwordField.setFont(new Font("Arial", Font.BOLD, 14));

      generateButton = new JButton("Generate Password");
      copyButton = new JButton("Copy to Clipboard");

      // Add components to the frame with GridBagLayout
      gbc.gridx = 0;
      gbc.gridy = 0;
      add(lengthLabel, gbc);

      gbc.gridx = 1;
      add(lengthSpinner, gbc);

      gbc.gridx = 0;
      gbc.gridy = 1;
      add(includeUppercase, gbc);

      gbc.gridx = 1;
      add(includeNumbers, gbc);

      gbc.gridx = 0;
      gbc.gridy = 2;
      add(includeSpecialChars, gbc);

      gbc.gridx = 0;
      gbc.gridy = 3;
      gbc.gridwidth = 2;
      add(new JLabel("Generated Password:"), gbc);

      gbc.gridx = 0;
      gbc.gridy = 4;
      add(passwordField, gbc);

      gbc.gridwidth = 1;
      gbc.gridx = 0;
      gbc.gridy = 5;
      add(generateButton, gbc);

      gbc.gridx = 1;
      add(copyButton, gbc);

      // Add action listeners
      generateButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            generatePassword();
         }
      });

      copyButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            copyToClipboard();
         }
      });

      // Add item listeners to checkboxes to change background color
      includeUppercase.addActionListener(e -> changeBackgroundColor());
      includeNumbers.addActionListener(e -> changeBackgroundColor());
      includeSpecialChars.addActionListener(e -> changeBackgroundColor());
   }

   private void generatePassword() {
      int length = (int) lengthSpinner.getValue();
      StringBuilder characterPool = new StringBuilder(LOWERCASE);

      if (includeUppercase.isSelected()) {
         characterPool.append(UPPERCASE);
      }
      if (includeNumbers.isSelected()) {
         characterPool.append(NUMBERS);
      }
      if (includeSpecialChars.isSelected()) {
         characterPool.append(SPECIAL_CHARS);
      }

      // Check if character pool is empty
      if (characterPool.length() == 0) {
         JOptionPane.showMessageDialog(this, "Please select at least one character type.", "Warning",
               JOptionPane.WARNING_MESSAGE);
         return;
      }

      StringBuilder password = new StringBuilder();
      SecureRandom random = new SecureRandom();

      for (int i = 0; i < length; i++) {
         int index = random.nextInt(characterPool.length());
         password.append(characterPool.charAt(index));
      }

      passwordField.setText(password.toString()); // Update password field
      passwordField.setCaretPosition(0); // Move caret to the beginning
   }

   private void copyToClipboard() {
      String password = passwordField.getText();
      if (!password.isEmpty()) {
         StringSelection stringSelection = new StringSelection(password);
         Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         clipboard.setContents(stringSelection, null);
         JOptionPane.showMessageDialog(this, "Password copied to clipboard!", "Success",
               JOptionPane.INFORMATION_MESSAGE);
      } else {
         JOptionPane.showMessageDialog(this, "No password to copy!", "Warning", JOptionPane.WARNING_MESSAGE);
      }
   }

   private void changeBackgroundColor() {
      Color randomColor = new Color((int) (Math.random() * 0x1000000));
      getContentPane().setBackground(randomColor);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         RandomPasswordGenerator app = new RandomPasswordGenerator();
         app.setVisible(true);
      });
   }
}
