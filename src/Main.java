import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        File file = new File("iris.txt");

        try {
            Scanner sc = new Scanner(file);
            float[][] vector = new float[150][4];
            int[] outputs = new int[150];
            int count = 0;

            sc.nextLine();
            //odczyt irisów
            while (sc.hasNextLine()) {
                String[] tokens = sc.nextLine().split(",");
                float[] input = new float[tokens.length - 1];
                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].equals("Iris-setosa")) {
                        outputs[count] = 1;
                    } else if (tokens[i].equals("Iris-virginica") || tokens[i].equals("Iris-versicolor")) {
                        outputs[count] = 0;
                    } else {
                        input[i] = Float.parseFloat(tokens[i]);
                        vector[count] = input;
                    }
                }
                count++;
            }


            Perceptron p = new Perceptron(0.5f, 0.1f, 4);

            // mieszanie indeksów tablic
            int[] indices = new int[150];
            for (int i = 0; i < 150; i++) {
                indices[i] = i;
            }
            shuffleArray(indices);

            // Podzielenie na zbiór treningowy i testowy 6:4
            int trainSize = 90;
            float[][] trainVector = new float[trainSize][4];
            int[] trainOutputs = new int[trainSize];
            float[][] testVector = new float[vector.length - trainSize][4];
            int[] testOutputs = new int[vector.length - trainSize];

            for (int i = 0; i < trainSize; i++) {
                trainVector[i] = vector[indices[i]];
                trainOutputs[i] = outputs[indices[i]];
            }

            for (int i = 0; i < vector.length - trainSize; i++) {
                testVector[i] = vector[indices[trainSize + i]];
                testOutputs[i] = outputs[indices[trainSize + i]];
            }

            // Trenuj na zbiorze treningowym
            for (int epoch = 0; epoch < 100; epoch++) {
                for (int i = 0; i < trainVector.length; i++) {
                    p.learn(trainVector[i], trainOutputs[i]);
                }
            }

            // Utwórz tablicę z przewidywaniami na zbiorze testowym
            int[] predictions = new int[testVector.length];
            for (int i = 0; i < testVector.length; i++) {
                predictions[i] = p.classify(testVector[i]);
            }

            double accuracy = calculateAccuracy(predictions, testOutputs);
            System.out.println("Accuracy Score: " + accuracy);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    private static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i =0; i< array.length; i++) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static double calculateAccuracy(int[] predictions, int[] expectedOutputs) {
        int correctPredictions = 0;
        for (int i = 0; i < predictions.length; i++) {
            if (predictions[i] == expectedOutputs[i]) {
                correctPredictions++;
            }
        }
        return (double) correctPredictions / predictions.length;
    }
}
