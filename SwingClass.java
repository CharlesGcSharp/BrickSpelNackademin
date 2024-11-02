package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwingClass extends JFrame implements ActionListener {
    static JPanel myJpanel = new JPanel(new GridLayout(4, 4));
    JButton button1 = new JButton("1");
    JButton button2 = new JButton("2");
    JButton button3 = new JButton("3");
    JButton button4 = new JButton("4");
    JButton button5 = new JButton("5");
    JButton button6 = new JButton("6");
    JButton button7 = new JButton("7");
    JButton button8 = new JButton("8");
    JButton button9 = new JButton("9");
    JButton button10 = new JButton("10");
    JButton button11 = new JButton("11");
    JButton button12 = new JButton("12");
    JButton button13 = new JButton("13");
    JButton button14 = new JButton("14");
    JButton button15 = new JButton("15");
    JButton blankTile;

    JButton newGame = new JButton("Nytt spel");

    public SwingClass() {
        blankTile = new JButton("");


        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        myJpanel.add(button1);
        myJpanel.add(button2);
        myJpanel.add(button3);
        myJpanel.add(button4);
        myJpanel.add(button5);
        myJpanel.add(button6);
        myJpanel.add(button7);
        myJpanel.add(button8);
        myJpanel.add(button9);
        myJpanel.add(button10);
        myJpanel.add(button11);
        myJpanel.add(button12);
        myJpanel.add(button13);
        myJpanel.add(button14);
        myJpanel.add(button15);
        myJpanel.add(blankTile);


        addActionListeners();


        JPanel newGamePanel = new JPanel();
        newGamePanel.add(newGame);
        newGame.addActionListener(this);


        this.add(newGamePanel, BorderLayout.NORTH);
        this.add(myJpanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void addActionListeners() {
        for (int i = 1; i <= 15; i++)
        {
            try {

                JButton button = (JButton) getClass().getDeclaredField("button" + i).get(this);
                button.addActionListener(this);

            } catch (IllegalAccessException | NoSuchFieldException ignored) { }
        }
        blankTile.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventTrigger = e.getSource();

        if (eventTrigger instanceof JButton) {
            JButton clickedButton = (JButton) eventTrigger;


            if ("Nytt spel".equals(clickedButton.getText())) {
                newGame();
            } else {

                int buttonPlace = getIndex(clickedButton.getText());
                int emptyTilePlace = getEmptyTileIndex();

                if (nextTo(buttonPlace, emptyTilePlace))
                {

                    if (victoryCheck())
                    {
                        JOptionPane.showMessageDialog(null, "Grattis, du vann!");
                    }

                    String number = clickedButton.getText();
                    clickedButton.setText("");
                    JButton emptyButtonToClickableButton = (JButton) myJpanel.getComponent(emptyTilePlace);
                    emptyButtonToClickableButton.setText(number);

                    Color newColor = clickedButton.getBackground();
                    clickedButton.setBackground(emptyButtonToClickableButton.getBackground());
                    emptyButtonToClickableButton.setBackground(newColor);

                    myJpanel.revalidate();
                    myJpanel.repaint();
                }
            }
        }
    }

    public static int getIndex(String buttonText)
    {
        for (int i = 0; i < myJpanel.getComponentCount(); i++)
        {
            Component c = myJpanel.getComponent(i);

            if (c instanceof JButton) {
                String indexText = ((JButton) c).getText();
                if (indexText.equals(buttonText)) {
                    return i;
                }
            }
        }
        return 0;
    }

    public static int getEmptyTileIndex()
    {

        for (int i = 0; i < myJpanel.getComponentCount(); i++)
        {

            Component c = myJpanel.getComponent(i);

            if (c instanceof JButton) {
                if (((JButton) c).getText().isEmpty()) {
                    return i;
                }
            }
        }
        return 0;
    }

    public boolean nextTo(int buttonPlace, int emptyTilePlace) {
        int firstRow = buttonPlace / 4;
        int firstColumn = buttonPlace % 4;
        int secondRow = emptyTilePlace / 4;
        int secondColumn = emptyTilePlace % 4;

        // Metod hämtad från Stackoverflow: "https://stackoverflow.com/questions/8224470/calculating-manhattan-distance"
        return (Math.abs(firstRow - secondRow) + Math.abs(firstColumn - secondColumn) == 1);
    }

    public  void newGame() {
        List<JButton> buttonList = new ArrayList<>();
        Component[] panelButtons = myJpanel.getComponents();


        for (Component c : panelButtons) {
            if (c instanceof JButton && !((JButton)c).getText().isEmpty()) {
                buttonList.add((JButton) c);
            }
        }


        Collections.shuffle(buttonList);


        myJpanel.removeAll();


        for (JButton button : buttonList) {
            myJpanel.add(button);
        }
        //
        JButton newBlankTile = new JButton("");
        newBlankTile.addActionListener(this);
        myJpanel.add(newBlankTile);








        //
        myJpanel.revalidate();
        myJpanel.repaint();
    }

    public static Boolean victoryCheck()
    {
        int value = 1;
        int count = 0;

        for (int i = 0; i < myJpanel.getComponentCount(); i++)
        {
            Component c = myJpanel.getComponent(i);
            JButton buttonIndex = (JButton) c;
            if (buttonIndex.getText().equals(String.valueOf(value)))
            {
                value ++;

            }

        }

        if (value >= 15)
        {
            return true;
        }
        return false;


    }




}
