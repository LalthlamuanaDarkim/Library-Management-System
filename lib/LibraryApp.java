
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class LibraryApp extends JFrame {
    JTextField book, student, issue, due, returned, search;
    JTextArea output;
    ArrayList<String[]> records = new ArrayList<>();

    LibraryApp() {
        setTitle("Library Management");
        setSize(500, 500);
        setLayout(new FlowLayout());

        book = new JTextField(10);
        student = new JTextField(10);
        issue = new JTextField(10);
        due = new JTextField(10);
        returned = new JTextField(10);
        search = new JTextField(10);
        output = new JTextArea(12, 40);

        JButton borrow = new JButton("Borrow Book");
        JButton returnBook = new JButton("Return Book");
        JButton view = new JButton("View Student Books");

        add(new JLabel("Book Name:"));
        add(book);
        add(new JLabel("Student Name:"));
        add(student);
        add(new JLabel("Issue Date (YYYY-MM-DD):"));
        add(issue);
        add(new JLabel("Due Date (YYYY-MM-DD):"));
        add(due);
        add(new JLabel("Return Date (YYYY-MM-DD):"));
        add(returned);
        add(borrow);
        add(returnBook);
        add(new JLabel("Search Student:"));
        add(search);
        add(view);
        add(new JScrollPane(output));

        borrow.addActionListener(e -> {
            records.add(new String[]{
                book.getText(),
                student.getText(),
                issue.getText(),
                due.getText(),
                ""
            });
            output.setText("Book borrowed successfully.");
        });

        returnBook.addActionListener(e -> {
            try {
                for (String[] r : records) {
                    if (r[0].equals(book.getText())
                            && r[1].equals(student.getText())
                            && r[4].equals("")) {

                        r[4] = returned.getText();

                        LocalDate dueDate = LocalDate.parse(r[3]);
                        LocalDate returnDate = LocalDate.parse(r[4]);

                        long days = Math.max(0,
                            ChronoUnit.DAYS.between(dueDate, returnDate));
                        long fine = days * 5;

                        output.setText("Book Returned: " + r[0]
                            + "\nOverdue Days: " + days
                            + "\nFine: Rs. " + fine);
                        return;
                    }
                }
                output.setText("Borrowed book not found.");
            } catch (Exception ex) {
                output.setText("Enter dates in YYYY-MM-DD format.");
            }
        });

        view.addActionListener(e -> {
            String result = "";
            for (String[] r : records) {
                if (r[1].equalsIgnoreCase(search.getText())
                        && r[4].equals("")) {
                    result += "Book: " + r[0]
                        + ", Due: " + r[3] + "\n";
                }
            }
            output.setText(result.isEmpty()
                ? "No books currently borrowed."
                : result);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LibraryApp();
    }
}
