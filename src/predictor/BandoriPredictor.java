/**
 * Predicts the score needed to achieve rank 1000 for a given time.
 *
 * @author Alexander "Lex" Adams
 */

package predictor;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class BandoriPredictor {
    public BandoriPredictor(int start, String target) throws Exception {
        //Make input
        BufferedWriter writer = new BufferedWriter(
                new FileWriter("data/input.arff"));
        writer.write("@relation bandori\n" +
                "\n" +
                "@attribute secondsSinceEventStart numeric\n" +
                "@attribute currentTime date \"EEE hh:mm a z\"\n" +
                "@attribute points numeric\n" +
                "\n" +
                "@data" +
                "\n" +
                start + ",\"" + target + "\",?");
        writer.newLine();
        writer.flush();
        writer.close();

        // Load model
        MultilayerPerceptron mlp = (MultilayerPerceptron) weka.core.SerializationHelper.read("data/neuralnetwork.model");

        // Load input
        Instances dataPredict = new Instances(
                new BufferedReader(
                        new FileReader("data/input.arff")));
        dataPredict.setClassIndex(dataPredict.numAttributes() - 1);
        Instances predictedData = new Instances(dataPredict);

        // Make predictions
        for (int i = 0; i < dataPredict.numInstances(); i++) {
            double clsLabel = mlp.classifyInstance(dataPredict.instance(i));
            predictedData.instance(i).setClassValue(clsLabel);
        }

        // Save output
        writer = new BufferedWriter(
                new FileWriter("data/output.arff"));
        writer.write(predictedData.toString());
        writer.newLine();
        writer.flush();
        writer.close();
    }
}