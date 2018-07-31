import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;

public class student_info extends JFrame {
    static student_info frame;
    private JPanel contentPane;
    private JTable table;
    private JTextField textField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new student_info();
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
    public student_info() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        String data[][] = null;
        String column[] = null;
        JLabel lblEnterId = new JLabel("Enter Student Id:");
        textField = new JTextField();
        textField.setColumns(10);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sid = textField.getText();
                String data[][] = null;
                String column[] = null;
                if (sid == null || sid.trim().equals("")) {
                    JOptionPane.showMessageDialog(student_info.this, "Id can't be blank");
                } else {
                    int id = Integer.parseInt(sid);
                    try {
                        Connection con = DB.getConnection();
                        PreparedStatement ps = con.prepareStatement("select * from issuebooks where studentid=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ps.setInt(1, id);
                        ResultSet rs = ps.executeQuery();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int cols = rsmd.getColumnCount();
                        column = new String[cols];
                        for (int i = 1; i <= cols; i++) {
                            column[i - 1] = rsmd.getColumnName(i);
                        }

                        rs.last();
                        int rows = rs.getRow();
                        rs.beforeFirst();

                        data = new String[rows][cols];
                        int count = 0;
                        while (rs.next()) {
                            for (int i = 1; i <= cols; i++) {
                                data[count][i - 1] = rs.getString(i);
                            }
                            count++;
                        }
                        con.close();
                    } catch (Exception e1) {
                        System.out.println(e1);
                    }
                    table = new JTable(data, column);
                    JScrollPane sp = new JScrollPane(table);
                    contentPane.add(sp, BorderLayout.CENTER);
                }
            }
        });
        JButton btnPenalty = new JButton("Penalty");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
            });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(39)
                                .addComponent(lblEnterId)
                                .addGap(57)
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(107, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap(175, Short.MAX_VALUE)
                                .addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                .addGap(140))
                        .addGroup(GroupLayout.Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap(322, Short.MAX_VALUE)
                                .addComponent(btnPenalty, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(19)
                                .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblEnterId))
                                .addGap(33)
                                .addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                                .addGap(63)
                                .addComponent(btnPenalty)
        );
        contentPane.setLayout(gl_contentPane);
    }
}