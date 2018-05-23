package predictiongui;

import predictor.BandoriPredictor;
import predictor.DateConverter;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class PredictionMain {
    public static void main(String[] args) throws Exception {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI
        SwingUtilities.invokeLater(PredictionMain::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Use the Java look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Make sure we have nice window decorations
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        JTextField startTimeInput = new JTextField();
        JTextField predictionTimeInput = new JTextField();

        final JComponent[] inputs = new JComponent[]{
                new JLabel("Date and time of event start, e.g. 2018-01-15 9:00 PM EDT"),
                startTimeInput,
                new JLabel("Date and time to predict threshold, e.g. 2018-01-15 9:00 PM EDT"),
                predictionTimeInput,
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Rank 1000 Threshold Predictor", JOptionPane.DEFAULT_OPTION);

        // Accept input
        if (result == JOptionPane.OK_OPTION) {
            String startTime = startTimeInput.getText();
            String predictionTime = predictionTimeInput.getText();
            DateConverter datePair = new DateConverter(startTime, predictionTime);
            try {
                new BandoriPredictor(datePair.getStart(), datePair.getTarget());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User cancelled / closed the dialog, result = " + result);
            System.exit(-1);
        }

        // Read the output
        String prediction = null;
        int finalPrediction = 0;
        try {
            BufferedReader in
                    = new BufferedReader(new FileReader("data/output.arff"));

            while ((prediction = in.readLine()) != null) {
                if (prediction.contains(",")) {
                    StringTokenizer st = null;
                    st = new StringTokenizer(prediction,",");
                    st.nextToken();
                    st.nextToken();
                    finalPrediction = (int)Double.parseDouble(st.nextToken());
                    break;
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, finalPrediction, "Rank 1000 Threshold Predictor", JOptionPane.INFORMATION_MESSAGE);
    }
}