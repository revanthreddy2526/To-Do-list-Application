import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class ToDoListApp {
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskField;
    private static final String FILE_NAME = "tasks.txt";

    public ToDoListApp() {
        JFrame frame = new JFrame("Daily To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        listModel = new DefaultListModel<>();
        loadTasks();
        taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
        
        taskField = new JTextField(20);
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton clearButton = new JButton("Clear All");

        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        clearButton.addActionListener(e -> clearTasks());

        JPanel panel = new JPanel();
        panel.add(taskField);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(clearButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }

    private void addTask() {
        String task = taskField.getText().trim();
        if (!task.isEmpty()) {
            listModel.addElement(task);
            taskField.setText("");
            saveTasks();
        }
    }

    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
            saveTasks();
        }
    }

    private void clearTasks() {
        listModel.clear();
        saveTasks();
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < listModel.size(); i++) {
                writer.write(listModel.getElementAt(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listModel.addElement(line);
            }
        } catch (IOException e) {
            // File might not exist initially, no action needed
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListApp::new);
    }
}
