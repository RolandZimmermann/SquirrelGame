package de.hsa.games.fatsquirrel.botimpls;

import java.util.function.Function;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;

public class MaToRoKi implements BotController, BotControllerFactory{
	
	NeuralNetwork nn= new NeuralNetwork();

	@Override
	public BotController createMasterBotController() {
		return this;
	}

	@Override
	public BotController createMiniBotController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void nextStep(ControllerContext view) {
		// TODO Auto-generated method stub
		
	}

class NeuralNetwork {

	private int inputNodes;
	private int hiddenNodes;
	private int hiddenNodes2;
	private int outputNodes;

	private Matrix weightsIH;
	private Matrix weightsHH;
	private Matrix weightsOH;
	private Matrix biasH;
	private Matrix biasH2;
	private Matrix biasO;

	public NeuralNetwork(int inputNodes, int hiddenNodes, int hiddenNodes2, int outputNodes) {
		this.inputNodes = inputNodes;
		this.hiddenNodes = hiddenNodes;
		this.hiddenNodes2 = hiddenNodes2;
		this.outputNodes = outputNodes;

		this.weightsIH = new Matrix(this.hiddenNodes, this.inputNodes);
		this.weightsHH = new Matrix(this.hiddenNodes2, this.hiddenNodes);
		this.weightsOH = new Matrix(this.outputNodes, this.hiddenNodes2);
		this.weightsIH.randomize();
		this.weightsHH.randomize();
		this.weightsOH.randomize();
		this.biasH = new Matrix(this.hiddenNodes, 1);
		this.biasH2 = new Matrix(this.hiddenNodes2, 1);
		this.biasO = new Matrix(this.outputNodes, 1);
		this.biasH.randomize();
		this.biasH2.randomize();
		this.biasO.randomize();
	}

	public Matrix feedforward(Matrix input) {
		Matrix hidden = null;
		Matrix hidden2 = null;
		try {
			hidden = Matrix.multiply(this.weightsIH, input);
			hidden.addMatrix(this.biasH);
			hidden.map(this::sigmoid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			hidden2 = Matrix.multiply(this.weightsHH, hidden);
			hidden2.addMatrix(this.biasH);
			hidden2.map(this::sigmoid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Matrix output = null;
		try {
			output = Matrix.multiply(this.weightsOH, hidden2);
			output.addMatrix(this.biasH);
			output.map(this::sigmoid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return output;

	}

	public double sigmoid(Double x) {
		return 1 / (1 + Math.exp(-x));
	}

}

static class Matrix {

	private int rows;
	private int cols;
	private double[][] data;

	public Matrix(int cols, int rows) {
		this.rows = rows;
		this.cols = cols;
		this.data = new double[cols][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				this.data[i][j] = 0.0;
			}
		}
	}

	public Matrix(Matrix matrix) {
		this.rows = matrix.getRows();
		this.cols = matrix.getCols();
		this.data = matrix.getData();
	}

	public Matrix(double[][] matrix) {
		this.rows = matrix[0].length;
		this.cols = matrix.length;
		this.data = matrix;
	}

	public void scale(double n) {
		for (int i = 0; i < this.cols; i++) {
			for (int j = 0; j < this.rows; j++) {
				this.data[i][j] *= n;
			}
		}
	}

	public void scaleHadamardProdukt(Matrix matrix) {
		for (int i = 0; i < this.cols; i++) {
			for (int j = 0; j < this.rows; j++) {
				this.data[i][j] *= matrix.getData()[i][j];
			}
		}
	}

	public void add(double n) {
		for (int i = 0; i < this.cols; i++) {
			for (int j = 0; j < this.rows; j++) {
				this.data[i][j] *= n;
			}
		}
	}

	public void addMatrix(Matrix matrix) {
		for (int i = 0; i < this.cols; i++) {
			for (int j = 0; j < this.rows; j++) {
				this.data[i][j] += matrix.getData()[i][j];
			}
		}
	}

	public void randomize() {
		for (int i = 0; i < this.cols; i++) {
			for (int j = 0; j < this.rows; j++) {
				this.data[i][j] = Math.random() * 2 - 1;
			}
		}
	}

	public void map(Function<Double, Double> call) {
		for (int i = 0; i < this.cols; i++) {
			for (int j = 0; j < this.rows; j++) {
				double value = this.data[i][j];
				this.data[i][j] = call.apply(this.data[i][j]);
			}
		}
	}

	public static Matrix multiply(Matrix matrix1, Matrix matrix2) throws Exception {
		if (matrix1.getRows() != matrix2.getCols()) {
			throw new Exception("Number of cols must equal number of rows");
		}

		double[][] output = new double[matrix1.getCols()][matrix2.getRows()];
		for (int i = 0; i < output.length; i++) {
			for (int j = 0; j < output[0].length; j++) {
				double sum = 0;
				for (int k = 0; k < matrix1.getRows(); k++) {
					sum += matrix1.data[i][k] * matrix2.data[k][j];
				}
				output[i][j] = sum;
			}
		}

		return new Matrix(output);
	}

	public static Matrix transpose(Matrix matrix) {
		double[][] output = new double[matrix.getRows()][matrix.getCols()];
		for (int i = 0; i < matrix.getCols(); i++) {
			for (int j = 0; j < matrix.getRows(); j++) {
				output[j][i] = matrix.getData()[i][j];
			}
		}
		return new Matrix(output);
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < this.cols; i++) {
			for (int j = 0; j < this.rows; j++) {
				s += this.data[i][j] + " ";
			}
			s += "\n";
		}
		return s;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public double[][] getData() {
		return data;
	}
}


}
