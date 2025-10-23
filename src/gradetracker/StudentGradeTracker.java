package gradetracker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentGradeTracker {

    JFrame frameWindow;
    JTextField rollnoField, studentnameField, scoreField, sectionField;
    JLabel RollnoLabel, SectionLabel, nameLabel, GradeLabel;
    ArrayList<String> studentName = new ArrayList<>();
    ArrayList<Integer> studentScore = new ArrayList<>();
    ArrayList<Integer> studentRollNo = new ArrayList<>();
    ArrayList<String> StudentSection = new ArrayList<>();

    public StudentGradeTracker() {
        // FRAME
        frameWindow = new JFrame();
        frameWindow.setTitle("Student Grade Tracker");
        frameWindow.setSize(600, 600);
        frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameWindow.setLayout(null); // manual layout for stacking

        ImageIcon logoIcon = new ImageIcon("images/logo for student grade tracker.jpg");
        frameWindow.setIconImage(logoIcon.getImage());

        JPanel gradientPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = Color.decode("#43B3E5");
                Color color2 = Color.decode("#902e86");
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        
        gradientPanel.setBounds(0, 0, 600, 600);
        gradientPanel.setLayout(null);

        // 🌿 WHITE CARD WITH SHADOW
        JPanel whiteCardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 30;
                int shadowGap = 8;

                g2d.setColor(new Color(0, 0, 0, 50)); // shadow
                g2d.fillRoundRect(shadowGap, shadowGap, getWidth() - shadowGap, getHeight() - shadowGap, arc, arc);

                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth() - shadowGap, getHeight() - shadowGap, arc, arc);

                g2d.dispose();
                super.paintComponent(g);
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        whiteCardPanel.setLayout(new BoxLayout(whiteCardPanel, BoxLayout.Y_AXIS));
        whiteCardPanel.setBackground(Color.WHITE);
        whiteCardPanel.setBounds(75, 80, 450, 400);

        //  FORM PANEL
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false);

        RollnoLabel = new JLabel("Roll No:");
        rollnoField = new JTextField(10);
        nameLabel = new JLabel("Name:");
        studentnameField = new JTextField(15);
        SectionLabel = new JLabel("Section:");
        sectionField = new JTextField(10);
        GradeLabel = new JLabel("Grade:");
        scoreField = new JTextField(5);

        Font labelFont = new Font("Arial", Font.BOLD, 15);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        RollnoLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        SectionLabel.setFont(labelFont);
        GradeLabel.setFont(labelFont);

        rollnoField.setFont(fieldFont);
        studentnameField.setFont(fieldFont);
        sectionField.setFont(fieldFont);
        scoreField.setFont(fieldFont);

        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.add(RollnoLabel);
        formPanel.add(rollnoField);
        formPanel.add(nameLabel);
        formPanel.add(studentnameField);
        formPanel.add(SectionLabel);
        formPanel.add(sectionField);
        formPanel.add(GradeLabel);
        formPanel.add(scoreField);

        //  BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        RoundedButton addStudent = new RoundedButton("Add Student", 20);
        RoundedButton summaryReport = new RoundedButton("Report", 20);
        RoundedButton Average = new RoundedButton("Average", 20);
        RoundedButton Highest = new RoundedButton("Highest", 20);
        RoundedButton Lowest = new RoundedButton("Lowest", 20);

        buttonPanel.add(addStudent);
        buttonPanel.add(Highest);
        buttonPanel.add(Lowest);
        buttonPanel.add(Average);
        buttonPanel.add(summaryReport);

        // add everything inside white card
        whiteCardPanel.add(formPanel);
        whiteCardPanel.add(buttonPanel);

        //  STATUS LABEL (ABOVE CARD)
        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBounds(100, 510, 400, 30); // below card
        gradientPanel.add(statusLabel);

        // Add white card after label to keep label above visually
        gradientPanel.add(whiteCardPanel);
        gradientPanel.setComponentZOrder(statusLabel, 0); // label always on top

        // Add to frame
        frameWindow.add(gradientPanel);

        //  BUTTON LOGIC
        addStudent.addActionListener(e -> {
            try {
                String name = studentnameField.getText();
                int score = Integer.parseInt(scoreField.getText());
                int RollNo = Integer.parseInt(rollnoField.getText());
                String Section = sectionField.getText();

                studentName.add(name);
                studentScore.add(score);
                studentRollNo.add(RollNo);
                StudentSection.add(Section);

                statusLabel.setText("✅ Student Added Successfully");
                new javax.swing.Timer(3000, ev -> statusLabel.setText("")).start();

                rollnoField.setText("");
                studentnameField.setText("");
                sectionField.setText("");
                scoreField.setText("");

            } catch (Exception x) {
                JOptionPane.showMessageDialog(frameWindow, "⚠️ Please enter valid data!");
            }
        });

        Average.addActionListener(e -> {
            if (studentScore.isEmpty()) {
                statusLabel.setText("⚠️ No Students Available");
            } else {
                int sum = 0;
                for (int s : studentScore) sum += s;
                double avg = (double) sum / studentScore.size();
                statusLabel.setText("📊 Average Score: " + avg);
            }
        });

        Highest.addActionListener(e -> {
            if (studentScore.isEmpty()) {
                statusLabel.setText("⚠️ No Students Available");
            } else {
                int highest = studentScore.get(0);
                for (int s : studentScore)
                    if (s > highest) highest = s;
                statusLabel.setText("🏆 Highest Score: " + highest);
            }
        });

        Lowest.addActionListener(e -> {
            if (studentScore.isEmpty()) {
                statusLabel.setText("⚠️ No Students Available");
            } else {
                int lowest = studentScore.get(0);
                for (int s : studentScore)
                    if (s < lowest) lowest = s;
                statusLabel.setText("🔻 Lowest Score: " + lowest);
            }
        });

        summaryReport.addActionListener(e -> {
            if (studentName.isEmpty()) {
                statusLabel.setText("⚠️ No Students Available");
            } else {
                String[] columns = {"Roll No", "Name", "Section", "Score"};
                String[][] data = new String[studentName.size()][4];
                for (int i = 0; i < studentName.size(); i++) {
                    data[i][0] = String.valueOf(studentRollNo.get(i));
                    data[i][1] = studentName.get(i);
                    data[i][2] = StudentSection.get(i);
                    data[i][3] = String.valueOf(studentScore.get(i));
                }

                JTable table = new JTable(data, columns);
                JScrollPane scrollPane = new JScrollPane(table);
                JDialog dialog = new JDialog(frameWindow, "Summary Report", true);
                dialog.add(scrollPane);
                dialog.setSize(400, 300);
                dialog.setLocationRelativeTo(frameWindow);
                dialog.setVisible(true);
            }
        });

        frameWindow.setLocationRelativeTo(null);
        frameWindow.setVisible(true);
    }

    // ✅ INNER CLASS FOR ROUNDED BUTTON
    class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setBackground(Color.decode("#902e86"));
            setFont(new Font("Arial", Font.BOLD, 12));
            setPreferredSize(new Dimension(110, 30));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(getModel().isPressed() ? getBackground().darker() : getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            super.paintComponent(g2d);
            g2d.dispose();
        }

        @Override
        public void setContentAreaFilled(boolean b) {}
    }

    public static void main(String[] args) {
        new StudentGradeTracker();
    }
}
