package de.hsa.games.fatsquirrel.botimpls;

import java.io.Serializable;
import java.util.function.Function;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class MaToRoKiold implements BotController, BotControllerFactory {

	private NeuralNetwork nn = new NeuralNetwork(963, 500, 500, 500, 500, 17);

	public NeuralNetwork getNn() {
		return nn;
	}

	public void setNn(Object e) {
		if (!(e instanceof NeuralNetwork))
			this.nn = (NeuralNetwork) e;
	}

	@Override
	public BotController createMasterBotController() {
		return this;
	}

	@Override
	public BotController createMiniBotController() {
		// TODO Auto-generated method stub
		return new BotController() {

			@Override
			public void nextStep(ControllerContext view) {
				view.implode(10);

			}

		};
	}

	@Override
	public void nextStep(ControllerContext view) {
		double[][] input = new double[963][1];

		EntityType entity = EntityType.NONE;
		XY topleft = new XY(view.getViewLowerLeft().x - 31 / 2, view.getViewUpperRight().y + 31 / 2);
		XY downright = new XY(view.getViewUpperRight().x + 31 / 2, view.getViewLowerLeft().y - 31 / 2);

		input[962][0] = view.getEnergy();
		for (int i = topleft.y; i < downright.y; i++) {
			for (int j = topleft.x; j < downright.x; j++) {

				try {
					entity = view.getEntityAt(new XY(j, i));
					double value = 0;
					if (entity == EntityType.GOOD_BEAST) {
						value = 0.6;
					} else if (entity == EntityType.BAD_BEAST) {
						value = 0.1;
					} else if (entity == EntityType.GOOD_PLANT) {
						value = 0.5;
					} else if (entity == EntityType.BAD_PLANT) {
						value = 0.2;
					} else if (entity == EntityType.WALL) {
						value = 0.3;
					} else if (entity == EntityType.MASTER_SQUIRREL) {
						if (view.locate() != new XY(j, i)) {
							value = 0.4;
						}
					} else if (entity == EntityType.MINI_SQUIRREL) {
						if (view.isMine(new XY(j, i))) {
							value = 0.7;
						} else {
							value = 0.8;
						}
					}
					input[i - topleft.y + j - topleft.x][0] = value;
				} catch (OutOfViewException e) {
					// e.printStackTrace();
					// if(i - topleft.y > 0 && i - topleft.y < )
					input[i - topleft.y + j - topleft.x][0] = -1;
					continue;
				}
			}
		}

		Matrix inputMatrix = new Matrix(input);
		Matrix outputMatrix = nn.feedforward(inputMatrix);

		double[][] output = outputMatrix.getData();

		double highestoutput = -100d;
		int position = -1;

		for (int i = 0; i < output.length; i++) {
			for (int j = 0; j < output[0].length; j++) {
				if (output[i][j] > highestoutput) {
					position = i;
					highestoutput = output[i][j];
				}
			}
		}

		switch (position) {
		case -1:
			view.move(XY.ZERO_ZERO);
			break;
		case 0:
			view.move(XY.DOWN);
			break;
		case 1:
			view.move(XY.LEFT);
			break;
		case 2:
			view.move(XY.LEFT_DOWN);
			break;
		case 3:
			view.move(XY.LEFT_UP);
			break;
		case 4:
			view.move(XY.RIGHT);
			break;
		case 5:
			view.move(XY.RIGHT_DOWN);
			break;
		case 6:
			view.move(XY.RIGHT_UP);
			break;
		case 7:
			view.move(XY.UP);
			break;
		case 8:
			view.move(XY.ZERO_ZERO);
		case 9:
			view.spawnMiniBot(XY.DOWN, 100);
			break;
		case 10:
			view.spawnMiniBot(XY.LEFT, 100);
			break;
		case 11:
			view.spawnMiniBot(XY.LEFT_DOWN, 100);
			break;
		case 12:
			view.spawnMiniBot(XY.LEFT_UP, 100);
			break;
		case 13:
			view.spawnMiniBot(XY.RIGHT, 100);
			break;
		case 14:
			view.spawnMiniBot(XY.RIGHT_DOWN, 100);
			break;
		case 15:
			view.spawnMiniBot(XY.RIGHT_UP, 100);
			break;
		case 16:
			view.spawnMiniBot(XY.UP, 100);

		}

	}

	public void mutate(double mutationRate) {
		nn.mutate(mutationRate);
	}
}

class NeuralNetwork implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int inputNodes;
	private int hiddenNodes;
	private int hiddenNodes2;
	private int hiddenNodes3;
	private int hiddenNodes4;
	private int outputNodes;

	private double mutationRate;

	private Matrix weightsIH;
	private Matrix weightsHH;
	private Matrix weightsOH;
	private Matrix weightsHH2;
	private Matrix weightsHH3;
	private Matrix biasH;
	private Matrix biasH2;
	private Matrix biasH3;
	private Matrix biasH4;
	private Matrix biasO;

	public NeuralNetwork(int inputNodes, int hiddenNodes, int hiddenNodes2, int hiddenNodes3, int hiddenNodes4,
			int outputNodes) {
		this.inputNodes = inputNodes;
		this.hiddenNodes = hiddenNodes;
		this.hiddenNodes2 = hiddenNodes2;
		this.hiddenNodes3 = hiddenNodes3;
		this.hiddenNodes4 = hiddenNodes4;
		this.outputNodes = outputNodes;

		this.weightsIH = new Matrix(this.hiddenNodes, this.inputNodes);
		this.weightsHH = new Matrix(this.hiddenNodes2, this.hiddenNodes);
		this.weightsHH2 = new Matrix(this.hiddenNodes3, this.hiddenNodes2);
		this.weightsHH3 = new Matrix(this.hiddenNodes4, this.hiddenNodes3);
		this.weightsOH = new Matrix(this.outputNodes, this.hiddenNodes4);
		this.weightsIH.randomize();
		this.weightsHH.randomize();
		this.weightsHH2.randomize();
		this.weightsHH3.randomize();
		this.weightsOH.randomize();
		this.biasH = new Matrix(this.hiddenNodes, 1);
		this.biasH2 = new Matrix(this.hiddenNodes2, 1);
		this.biasH3 = new Matrix(this.hiddenNodes3, 1);
		this.biasH4 = new Matrix(this.hiddenNodes4, 1);
		this.biasO = new Matrix(this.outputNodes, 1);
		this.biasH.randomize();
		this.biasH2.randomize();
		this.biasH3.randomize();
		this.biasH4.randomize();
		this.biasO.randomize();
	}

	public NeuralNetwork(NeuralNetwork neuralNetwork) {
		this.inputNodes = neuralNetwork.inputNodes;
		this.hiddenNodes = neuralNetwork.hiddenNodes;
		this.hiddenNodes2 = neuralNetwork.hiddenNodes2;
		this.hiddenNodes3 = neuralNetwork.hiddenNodes3;
		this.hiddenNodes4 = neuralNetwork.hiddenNodes4;
		this.outputNodes = neuralNetwork.outputNodes;

		this.weightsIH = new Matrix(neuralNetwork.weightsIH);
		this.weightsHH = new Matrix(neuralNetwork.weightsHH);
		this.weightsOH = new Matrix(neuralNetwork.weightsOH);

		this.biasH = new Matrix(neuralNetwork.biasH);
		this.biasH2 = new Matrix(neuralNetwork.biasH2);
		this.biasH3 = new Matrix(neuralNetwork.biasH3);
		this.biasH4 = new Matrix(neuralNetwork.biasH4);
		this.biasO = new Matrix(neuralNetwork.biasO);
	}

	public Matrix feedforward(Matrix input) {
		Matrix hidden = null;
		Matrix hidden2 = null;
		Matrix hidden3 = null;

		// 1.HiddenLayer
		try {
			hidden = Matrix.multiply(this.weightsIH, input);
			hidden.addMatrix(this.biasH);
			hidden.map(this::sigmoid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 2.HiddenLayer
		try {
			hidden2 = Matrix.multiply(this.weightsHH, hidden);
			hidden2.addMatrix(this.biasH2);
			hidden2.map(this::sigmoid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3.HiddenLayer
		try {
			hidden3 = Matrix.multiply(this.weightsHH2, hidden2);
			hidden3.addMatrix(this.biasH3);
			hidden3.map(this::sigmoid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 4.HiddenLayer
		Matrix output = null;
		try {
			output = Matrix.multiply(this.weightsOH, hidden3);
			output.addMatrix(this.biasH);
			output.map(this::sigmoid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;

	}

	public void mutate(double mutationRate) {

		this.mutationRate = mutationRate;

		this.weightsIH.map(this::mutation);
		this.weightsHH.map(this::mutation);
		this.weightsHH2.map(this::mutation);
		this.weightsHH3.map(this::mutation);
		this.weightsOH.map(this::mutation);
		this.biasH.map(this::mutation);
		this.biasO.map(this::mutation);
		this.biasH2.map(this::mutation);
		this.biasH3.map(this::mutation);
		this.biasH4.map(this::mutation);
	}

	public double sigmoid(Double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public double mutation(Double x) {
		if (mutationRate > Math.random()) {
			return x + Math.random() - 0.5;
		} else {
			return x;
		}
	}

}

class Matrix implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		this.data = new double[matrix.getCols()][matrix.getRows()];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				this.data[i][j] = matrix.getData()[i][j];
			}
		}
	}

	public Matrix(double[][] matrix) {
		this.rows = matrix[0].length;
		this.cols = matrix.length;
		this.data = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				this.data[i][j] = matrix[i][j];
			}
		}
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
				this.data[i][j] += n;
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
