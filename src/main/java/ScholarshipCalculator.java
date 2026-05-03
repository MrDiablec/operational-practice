import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.formdev.flatlaf.FlatLightLaf;

public class ScholarshipCalculator extends JFrame {
    private JTextField gradeField;
    private JComboBox<String> typeComboBox;
    private JCheckBox bonusCheckBox;
    private JLabel resultLabel;
    private ScholarshipSettings settings;

    public ScholarshipCalculator() {
        setTitle("Калькулятор стипендии — Синергия");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        // Применяем FlatLaf тему
        FlatLightLaf.setup();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        settings = new ScholarshipSettings();

        // Средний балл
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Средний балл (0–5):"), gbc);
        gbc.gridx = 1;
        gradeField = new JTextField(15);
        mainPanel.add(gradeField, gbc);

        // Тип стипендии
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Тип стипендии:"), gbc);
        gbc.gridx = 1;
        typeComboBox = new JComboBox<>(new String[]{
            "Академическая", "Повышенная", "Социальная"
        });
        mainPanel.add(typeComboBox, gbc);

        // Чекбокс надбавки
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        bonusCheckBox = new JCheckBox("Участие в олимпиадах/конференциях (+10 %)");
        mainPanel.add(bonusCheckBox, gbc);

        // Кнопка расчёта
        gbc.gridy = 3; gbc.gridwidth = 2;
        JButton calculateButton = new JButton("Рассчитать стипендию");
        calculateButton.addActionListener(new CalculateListener());
        mainPanel.add(calculateButton, gbc);

        // Результат
        gbc.gridy = 4;
        resultLabel = new JLabel("Результат: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(resultLabel, gbc);

        add(mainPanel);

        // Не добавляем меню — история больше не нужна
    }

    private class CalculateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String gradeText = gradeField.getText().trim();
                if (gradeText.isEmpty()) {
                    throw new NumberFormatException();
                }
                double averageGrade = Double.parseDouble(gradeText);

                // Валидация балла
                if (averageGrade < 0 || averageGrade > 5) {
                    JOptionPane.showMessageDialog(ScholarshipCalculator.this,
                        "Средний балл должен быть от 0 до 5!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String type = (String) typeComboBox.getSelectedItem();
                boolean hasBonus = bonusCheckBox.isSelected();
                double scholarship = calculateScholarship(averageGrade, type, hasBonus);
                resultLabel.setText(String.format("Результат: %.2f руб.", scholarship));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ScholarshipCalculator.this,
                    "Введите корректный средний балл!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private double calculateScholarship(double grade, String type, boolean bonus) {
        double baseAmount = settings.getBaseAmount(type, grade);

        if (bonus) {
            baseAmount *= (1 + settings.getBonusPercentage());
        }

        return baseAmount;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ScholarshipCalculator().setVisible(true);
        });
    }
}



