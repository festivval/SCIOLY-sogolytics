import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class eventRankerTest implements ActionListener {

    JCheckBox checkNames, checkGrades;
    JTextArea dataInputBox, dataOutputBox;
    JButton bSubmit;

    boolean useNames, useGrades;

    public eventRankerTest()
    {
        useNames = false;
        useGrades = false;
    }

    public void makeWindow()
    {
        JFrame frame = new JFrame("SOGO Events Ranked Converter");//creating instance of JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        JPanel panelOne = new JPanel();
        JLabel lInfo = new JLabel("Welcome to SOGO rank converter!");

        // include names checkbox
        JPanel panelTwo = new JPanel();
        JLabel lNames = new JLabel("include names?");
        checkNames = new JCheckBox();
        checkNames.addActionListener(this);

        // include grade checkbox
        JLabel lGrades = new JLabel("include grades?");
        checkGrades = new JCheckBox();
        checkGrades.addActionListener(this);

        // input data text box
        JLabel lInputData = new JLabel("input data:");
        dataInputBox = new JTextArea(20, 15);
        JScrollPane scroll = new JScrollPane (dataInputBox,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // input data submit button
        bSubmit = new JButton("submit");
        bSubmit.addActionListener(this);

        // output data text box
        JLabel lOutputData = new JLabel("output data:");
        dataOutputBox = new JTextArea(20, 15);
        JScrollPane scroll2 = new JScrollPane (dataOutputBox,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        /*
          ADD EVERYTHING TO THE FRAME
        */
        panelOne.add(lInfo);
        container.add(panelOne);

        panelTwo.add(lNames);
        panelTwo.add(checkNames);
        panelTwo.add(Box.createRigidArea(new Dimension(20,0)));
        panelTwo.add(lGrades);
        panelTwo.add(checkGrades);
        container.add(panelTwo);

        container.add(lInputData);
        container.add(scroll);

        container.add(bSubmit);

        container.add(lOutputData);
        container.add(scroll2);

        frame.add(container);
        frame.setIconImage(new ImageIcon("resource/witch.png").getImage());
        frame.setSize(500,600); // 500 width and 600 height
        frame.setVisible(true); // making the frame visible
    }

    public static void main(String[] args)
    {
        eventRankerTest test = new eventRankerTest();
        test.makeWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == checkGrades) // check "include grades?" box
            useGrades = !useGrades;
        else if(e.getSource() == checkNames) // check "include names?" box
            useNames = !useNames;
        else if(e.getSource() == bSubmit) // create sorted data
        {
            // get each member's rankings
            ArrayList<String> ranks =
                    new ArrayList<>(Arrays.asList(dataInputBox.getText().split("\n")));

            // convert member's rankings from numbers to event names
            eventRanker2 ranker = new eventRanker2(ranks, useNames, useGrades);
            ArrayList<ArrayList<String>> temp = ranker.returnTopChoices();

            // display converted rankings in output box
            StringBuilder output = new StringBuilder();
            for(ArrayList<String> a : temp)
            {
                Iterator<String> iter = a.iterator();
                output.append(iter.next());
                while(iter.hasNext())
                    output.append("\t").append(iter.next());
                output.append("\n");
            }

            dataOutputBox.setText(output.toString());
        }
    }

}  