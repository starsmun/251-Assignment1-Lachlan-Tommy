import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TextFieldPanel extends JPanel {
    public JTextPane textField = new JTextPane();

    public TextFieldPanel(){
        JScrollPane jsp = new JScrollPane(textField);
        this.add(jsp);
    }

    public void paintComponents(Graphics g) {
        super.paintComponents(g);

    }

    public void FileOpener(File textFile)
    {
        try{
            FileReader reader = new FileReader(textFile);
            while(reader.read() != -1){
                textField.read(reader,null);
            }
            reader.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void clearField(){
        textField.setText("");
    }
    public void saveToFile(){
        try{
            JFileChooser chooser = new JFileChooser("./");

            int retVal = chooser.showOpenDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                FileWriter pw = new FileWriter (chooser.getSelectedFile() + ".txt");
                textField.write(pw);
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void addCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        textField.setText(dtf.format(now) + "\n");
    }

    public ArrayList<Integer> search(String q) {
        ArrayList<Integer> indexs = new ArrayList<>();
        int index = textField.getText().indexOf(q);
        while (index >=0){
            indexs.add(index);
            index = textField.getText().indexOf(q, index+q.length())   ;
        }

        if (indexs.size() == 0) {
            indexs.add(-1);
        }
        return indexs;
    }



}
