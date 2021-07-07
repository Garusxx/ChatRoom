package CliendWindow;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class ClientWindow {

    private JFrame frame;
    private JTextField massgaeField;
    private static JTextArea textArea = new JTextArea();
    private Client client;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientWindow window = new ClientWindow();
                    window.frame.setVisible(true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public ClientWindow(){
        initailize();
        String name = JOptionPane.showInputDialog("Enter name:");
        client = new Client(name,"127.0.0.1" , 52864);

    }
    private void initailize(){
        frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle("Char Room");
        frame.setBounds(100,100,650,450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0,0));

        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel,BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));

        massgaeField = new JTextField();
        panel.add(massgaeField);
        massgaeField.setColumns(50);

        JButton buttonSend = new JButton("Send");
        buttonSend.addActionListener(e ->{
                client.send(massgaeField.getText());
                massgaeField.setText("");
        });
        panel.add(buttonSend);

        frame.setLocationRelativeTo(null);


    }

    public static void prindToConsol(String massage){
        textArea.setText(textArea.getText() + massage + "\n");
    }

}
