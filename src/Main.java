void main() {

    File file = new File("iris.txt");

    try {
        Scanner sc = new Scanner(file);
        float[][] vector = new float[150][4];
        int[] outputs = new int[150];
        int count = 0;

        sc.nextLine();

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


        for (int epoch = 0; epoch < 100; epoch++) {
            for (int i = 0; i < vector.length; i++) {
                p.learn(vector[i], outputs[i]);
            }
        }

        IO.println("Result for {4.7,3.2,1.6,0.2}: " + p.classify(new float[]{4.7f, 3.2f, 1.6f, 0.2f}));

    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
}
