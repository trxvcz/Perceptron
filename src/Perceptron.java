public class Perceptron {
    float[] weights;
    float bias;
    float learningRate;

    public Perceptron(float bias, float learningRate, int dimension) {
        this.bias = bias;
        this.learningRate = learningRate;
        this.weights = new float[dimension];

        for (int i = 0; i < dimension; i++) {
            weights[i] = (float) (Math.random() * 2 - 1);
        }
    }


    public void learn(float[] input, int output) {
        if (output > 1 || output < 0) {
            throw new IllegalArgumentException("Output must be 0 or 1");
        }

        float[] W = new float[weights.length];
        int y = classify(input);
        for (int i = 0; i < weights.length; i++) {
            weights[i] += (output - y) * input[i] * learningRate;
        }

        bias += (output - y) * learningRate;
    }


    public int classify(float[] x) {
        if (weights.length != x.length) {
            throw new IllegalArgumentException("Weights and X lengths don't match");
        }

        float net = bias;
        for (int i = 0; i < weights.length; i++) {
            net += weights[i] * x[i];
        }

        return net >= 0 ? 1 : 0;
    }

}
