package de.hsa.games.fatsquirrel.botimpls;

import java.util.ArrayList;
import java.util.Random;

import de.hsa.games.fatsquirrel.botapi.BotController;
import de.hsa.games.fatsquirrel.botapi.BotControllerFactory;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;
import de.hsa.games.fatsquirrel.botapi.OutOfViewException;
import de.hsa.games.fatsquirrel.core.EntityType;
import de.hsa.games.fatsquirrel.util.XY;

public class MaToRoKi implements BotController, BotControllerFactory {

	private Genome genome = start();
	public static ArrayList<connectionHistory> innovationHistory = new ArrayList<connectionHistory>();
	
	public void setGenome(Genome genome) {
		this.genome = genome;
	}
	
	public Genome getGenome() {
		return this.genome;
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

	private Genome start() {
		if (genome == null) {
			genome = new Genome(962, 17);
			genome.generateNetwork();
		}
		return genome;
	}

	@Override
	public void nextStep(ControllerContext view) {
		float[] input = new float[962];

		EntityType entity = EntityType.NONE;
		XY topleft = new XY(view.getViewLowerLeft().x - 31 / 2, view.getViewUpperRight().y + 31 / 2);
		XY downright = new XY(view.getViewUpperRight().x + 31 / 2, view.getViewLowerLeft().y - 31 / 2);
		
		input[961] = view.getEnergy()/1000;

		for (int i = topleft.y; i < downright.y; i++) {
			for (int j = topleft.x; j < downright.x; j++) {

				try {
					entity = view.getEntityAt(new XY(j, i));
					float value = 0;
					if (entity == EntityType.GOOD_BEAST) {
						value = 0.6f;
					} else if (entity == EntityType.BAD_BEAST) {
						value = 0.1f;
					} else if (entity == EntityType.GOOD_PLANT) {
						value = 0.5f;
					} else if (entity == EntityType.BAD_PLANT) {
						value = 0.2f;
					} else if (entity == EntityType.WALL) {
						value = 0.3f;
					} else if (entity == EntityType.MASTER_SQUIRREL) {
						if (view.locate() != new XY(j, i)) {
							value = 0.4f;
						}
					} else if (entity == EntityType.MINI_SQUIRREL) {
						if (view.isMine(new XY(j, i))) {
							value = 0.7f;
						} else {
							value = 0.8f;
						}
					}
					input[i - topleft.y + j - topleft.x] = value;
				} catch (OutOfViewException e) {
					input[i - topleft.y + j - topleft.x] = -1f;
					continue;
				}
			}
		}

		float[] output = genome.feedForward(input);

		float highestoutput = -100f;
		int position = -1;

		for (int i = 0; i < output.length; i++) {
			if (output[i] > highestoutput) {
				position = i;
				highestoutput = output[i];
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
	
	public MaToRoKi crossover(Genome genome2) {
		MaToRoKi newAI = new MaToRoKi();
		newAI.setGenome(genome.crossover(genome2));
		newAI.getGenome().generateNetwork();
		return newAI;
	}
	
	public void mutate() {
		genome.mutate(innovationHistory);
		genome.generateNetwork();
	}
	
	public String toString() {
		return genome.toString();
	}
}

class Genome {

	ArrayList<connectionGene> genes = new ArrayList<connectionGene>();// a list of connections between nodes which
																		// represent the NN
	ArrayList<Node> nodes = new ArrayList<Node>();// list of nodes
	int inputs;
	int outputs;
	int layers = 2;
	int nextNode = 0;
	int biasNode;
	ArrayList<Node> network = new ArrayList<Node>(); // a list of the nodes in the order that they need to be considered
														// int the NN
	
	public String toString() {
		return genes.size() + " | " + nodes.size() + " | " + network.size() + " | " + genes.get(genes.size()-1).weight + " | " + nodes.get(0).inputSum;
	}

	public Genome(int in, int out) {
		int localNextConnectionNumber = 0;
		// set input number and output number
		inputs = in;
		outputs = out;

		// create input nodes
		for (int i = 0; i < inputs; i++) {
			nodes.add(new Node(i));
			nextNode++;
			nodes.get(i).layer = 0;
		}

		// create output nodes
		for (int i = 0; i < outputs; i++) {
			nodes.add(new Node(i + inputs));
			nodes.get(i + inputs).layer = 1;
			nextNode++;
		}

		nodes.add(new Node(nextNode));// bias node
		biasNode = nextNode;
		nextNode++;
		nodes.get(biasNode).layer = 0;

		// connect inputs to all outputs outputs
		for (int i = 0; i < in; i++) {
			// add the bare minimum amount of connections to the array with random weights
			// and unique innovation numbers
			for (int j = 0; j < outputs; j++) {

				genes.add(new connectionGene(nodes.get(i), nodes.get(inputs + j), (float) (Math.random() * 2 - 1),
						localNextConnectionNumber));
				localNextConnectionNumber++;
			}
		}

		// connect the bias
		for (int i = 0; i < outputs; i++) {
			genes.add(new connectionGene(nodes.get(biasNode), nodes.get(inputs + i), (float) (Math.random() * 2 - 1),
					localNextConnectionNumber));
			localNextConnectionNumber++;
		}
	}

	// create an empty genome
	public Genome(int in, int out, boolean crossover) {
		// set input number and output number
		inputs = in;
		outputs = out;
	}

	// returns the node with a matching number
	// sometimes the nodes will not be in order
	public Node getNode(int nodeNumber) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).number == nodeNumber) {
				return nodes.get(i);
			}
		}
		return null;
	}

	// adds the conenctions going out of a node to that node so that it can acess
	// the next node during feeding forward
	public void connectNodes() {

		for (int i = 0; i < nodes.size(); i++) {// clear the connections
			nodes.get(i).outputConnections.clear();
		}

		for (int i = 0; i < genes.size(); i++) {// for each connectionGene
			genes.get(i).fromNode.outputConnections.add(genes.get(i));// add it to node
		}
	}

	// feeding in input values into the NN and returning output array
	public float[] feedForward(float[] inputValues) {
		// set the outputs of the input nodes
		for (int i = 0; i < inputs; i++) {
			nodes.get(i).outputValue = inputValues[i];
		}
		nodes.get(biasNode).outputValue = 1;// output of bias is 1

		for (int i = 0; i < network.size(); i++) {// for each node in the network engage it(see node class for what this
													// does)
			network.get(i).engage();
		}

		// the outputs are nodes[inputs] to nodes [inputs+outputs-1]
		float[] outs = new float[outputs];
		for (int i = 0; i < outputs; i++) {
			outs[i] = nodes.get(inputs + i).outputValue;
		}

		for (int i = 0; i < nodes.size(); i++) {// reset all the nodes for the next feed forward
			nodes.get(i).inputSum = 0;
		}

		return outs;
	}

	// sets up the NN as a list of nodes in order to be engaged

	public void generateNetwork() {
		connectNodes();
		network = new ArrayList<Node>();
		// for each layer add the node in that layer, since layers cannot connect to
		// themselves there is no need to order the nodes within a layer

		for (int l = 0; l < layers; l++) {// for each layer
			for (int i = 0; i < nodes.size(); i++) {// for each node
				if (nodes.get(i).layer == l) {// if that node is in that layer
					network.add(nodes.get(i));
				}
			}
		}
	}

	// mutate the NN by adding a new node
	// it does this by picking a random connection and disabling it then 2 new
	// connections are added
	// 1 between the input node of the disabled connection and the new node
	// and the other between the new node and the output of the disabled connection
	public void addNode(ArrayList<connectionHistory> innovationHistory) {
		Random r = new Random();
		// pick a random connection to create a node between
		int randomConnection = (int) Math.floor(r.nextInt(genes.size()));
		;

		while (genes.get(randomConnection).fromNode == nodes.get(biasNode)) {// dont disconnect bias
			randomConnection = (int) Math.floor(r.nextInt(genes.size()));
		}

		genes.get(randomConnection).enabled = false;// disable it

		int newNodeNo = nextNode;
		nodes.add(new Node(newNodeNo));
		nextNode++;
		// add a new connection to the new node with a weight of 1
		int connectionInnovationNumber = getInnovationNumber(innovationHistory, genes.get(randomConnection).fromNode,
				getNode(newNodeNo));
		genes.add(new connectionGene(genes.get(randomConnection).fromNode, getNode(newNodeNo), 1,
				connectionInnovationNumber));

		connectionInnovationNumber = getInnovationNumber(innovationHistory, getNode(newNodeNo),
				genes.get(randomConnection).toNode);
		// add a new connection from the new node with a weight the same as the disabled
		// connection
		genes.add(new connectionGene(getNode(newNodeNo), genes.get(randomConnection).toNode,
				genes.get(randomConnection).weight, connectionInnovationNumber));
		getNode(newNodeNo).layer = genes.get(randomConnection).fromNode.layer + 1;

		connectionInnovationNumber = getInnovationNumber(innovationHistory, nodes.get(biasNode), getNode(newNodeNo));
		// connect the bias to the new node with a weight of 0
		genes.add(new connectionGene(nodes.get(biasNode), getNode(newNodeNo), 0, connectionInnovationNumber));

		// if the layer of the new node is equal to the layer of the output node of the
		// old connection then a new layer needs to be created
		// more accurately the layer numbers of all layers equal to or greater than this
		// new node need to be incrimented
		if (getNode(newNodeNo).layer == genes.get(randomConnection).toNode.layer) {
			for (int i = 0; i < nodes.size() - 1; i++) {// dont include this newest node
				if (nodes.get(i).layer >= getNode(newNodeNo).layer) {
					nodes.get(i).layer++;
				}
			}
			layers++;
		}
		connectNodes();
	}

	// adds a connection between 2 nodes which aren't currently connected
	public void addConnection(ArrayList<connectionHistory> innovationHistory) {
		// cannot add a connection to a fully connected network
		if (fullyConnected()) {
			return;
		}

		Random r = new Random();

		// get random nodes
		int randomNode1 = (int) Math.floor(r.nextInt(nodes.size()));
		int randomNode2 = (int) Math.floor(r.nextInt(nodes.size()));
		while (nodes.get(randomNode1).layer == nodes.get(randomNode2).layer
				|| nodes.get(randomNode1).isConnectedTo(nodes.get(randomNode2))) { // while the random nodes are no good
			// get new ones
			randomNode1 = (int) Math.floor(r.nextInt(nodes.size()));
			randomNode2 = (int) Math.floor(r.nextInt(nodes.size()));
		}
		int temp;
		if (nodes.get(randomNode1).layer > nodes.get(randomNode2).layer) {// if the first random node is after the
																			// second then switch
			temp = randomNode2;
			randomNode2 = randomNode1;
			randomNode1 = temp;
		}

		// get the innovation number of the connection
		// this will be a new number if no identical genome has mutated in the same way
		int connectionInnovationNumber = getInnovationNumber(innovationHistory, nodes.get(randomNode1),
				nodes.get(randomNode2));
		// add the connection with a random array

		genes.add(new connectionGene(nodes.get(randomNode1), nodes.get(randomNode2), (float) (Math.random() * 2 - 1),
				connectionInnovationNumber));// changed this so if error here
		connectNodes();
	}

	// returns the innovation number for the new mutation
	// if this mutation has never been seen before then it will be given a new
	// unique innovation number
	// if this mutation matches a previous mutation then it will be given the same
	// innovation number as the previous one
	public int getInnovationNumber(ArrayList<connectionHistory> innovationHistory, Node from, Node to) {
		boolean isNew = true;
		int nextConnectionNo = 0;
		int connectionInnovationNumber = nextConnectionNo;
		for (int i = 0; i < innovationHistory.size(); i++) {// for each previous mutation
			if (innovationHistory.get(i).matches(this, from, to)) {// if match found
				isNew = false;// its not a new mutation
				connectionInnovationNumber = innovationHistory.get(i).innovationNumber; // set the innovation number as
																						// the innovation number of the
																						// match
				break;
			}
		}

		if (isNew) {// if the mutation is new then create an arrayList of integers representing the
					// current state of the genome
			ArrayList<Integer> innoNumbers = new ArrayList<Integer>();
			for (int i = 0; i < genes.size(); i++) {// set the innovation numbers
				innoNumbers.add(genes.get(i).innovationNo);
			}

			// then add this mutation to the innovationHistory
			innovationHistory
					.add(new connectionHistory(from.number, to.number, connectionInnovationNumber, innoNumbers));
			nextConnectionNo++;
		}
		return connectionInnovationNumber;
	}

	// returns whether the network is fully connected or not
	public boolean fullyConnected() {
		int maxConnections = 0;
		int[] nodesInLayers = new int[layers];// array which stored the amount of nodes in each layer

		// populate array
		for (int i = 0; i < nodes.size(); i++) {
			nodesInLayers[nodes.get(i).layer] += 1;
		}

		// for each layer the maximum amount of connections is the number in this layer
		// * the number of nodes infront of it
		// so lets add the max for each layer together and then we will get the maximum
		// amount of connections in the network
		for (int i = 0; i < layers - 1; i++) {
			int nodesInFront = 0;
			for (int j = i + 1; j < layers; j++) {// for each layer infront of this layer
				nodesInFront += nodesInLayers[j];// add up nodes
			}

			maxConnections += nodesInLayers[i] * nodesInFront;
		}

		if (maxConnections == genes.size()) {// if the number of connections is equal to the max number of connections
												// possible then it is full
			return true;
		}
		return false;
	}

	// mutates the genome
	public void mutate(ArrayList<connectionHistory> innovationHistory) {
		float rand1 = (float) Math.random();
		if (rand1 < 0.8) { // 80% of the time mutate weights
			for (int i = 0; i < genes.size(); i++) {
				genes.get(i).mutateWeight();
			}
		}
		// 5% of the time add a new connection
		float rand2 = (float) Math.random();
		if (rand2 < 0.05) {
			addConnection(innovationHistory);
		}

		// 3% of the time add a node
		float rand3 = (float) Math.random();
		if (rand3 < 0.03) {
			addNode(innovationHistory);
		}
	}

	// called when this Genome is better that the other parent
	public Genome crossover(Genome parent2) {
		Genome child = new Genome(inputs, outputs, true);
		child.genes.clear();
		child.nodes.clear();
		child.layers = layers;
		child.nextNode = nextNode;
		child.biasNode = biasNode;
		ArrayList<connectionGene> childGenes = new ArrayList<connectionGene>();// list of genes to be inherrited form
																				// the parents
		ArrayList<Boolean> isEnabled = new ArrayList<Boolean>();
		// all inherrited genes
		for (int i = 0; i < genes.size(); i++) {
			boolean setEnabled = true;// is this node in the chlid going to be enabled

			int parent2gene = matchingGene(parent2, genes.get(i).innovationNo);
			if (parent2gene != -1) {// if the genes match
				if (!genes.get(i).enabled || !parent2.genes.get(parent2gene).enabled) {// if either of the matching
																						// genes are disabled

					if (Math.random() < 0.75) {// 75% of the time disabel the childs gene
						setEnabled = false;
					}
				}
				float rand = (float) Math.random();
				if (rand < 0.5) {
					childGenes.add(genes.get(i));

					// get gene from this fucker
				} else {
					// get gene from parent2
					childGenes.add(parent2.genes.get(parent2gene));
				}
			} else {// disjoint or excess gene
				childGenes.add(genes.get(i));
				setEnabled = genes.get(i).enabled;
			}
			isEnabled.add(setEnabled);
		}

		// since all excess and disjoint genes are inherrited from the more fit parent
		// (this Genome) the childs structure is no different from this parent | with
		// exception of dormant connections being enabled but this wont effect nodes
		// so all the nodes can be inherrited from this parent
		for (int i = 0; i < nodes.size(); i++) {
			child.nodes.add(nodes.get(i).clone());
		}

		// clone all the connections so that they connect the childs new nodes

		for (int i = 0; i < childGenes.size(); i++) {
			child.genes.add(childGenes.get(i).clone(child.getNode(childGenes.get(i).fromNode.number),
					child.getNode(childGenes.get(i).toNode.number)));
			child.genes.get(i).enabled = isEnabled.get(i);
		}

		child.connectNodes();
		return child;
	}

	// returns whether or not there is a gene matching the input innovation number
	// in the input genome
	public int matchingGene(Genome parent2, int innovationNumber) {
		for (int i = 0; i < parent2.genes.size(); i++) {
			if (parent2.genes.get(i).innovationNo == innovationNumber) {
				return i;
			}
		}
		return -1; // no matching gene found
	}

	// returns a copy of this genome
	public Genome clone() {

		Genome clone = new Genome(inputs, outputs, true);

		for (int i = 0; i < nodes.size(); i++) {// copy nodes
			clone.nodes.add(nodes.get(i).clone());
		}

		// copy all the connections so that they connect the clone new nodes

		for (int i = 0; i < genes.size(); i++) {// copy genes
			clone.genes.add(genes.get(i).clone(clone.getNode(genes.get(i).fromNode.number),
					clone.getNode(genes.get(i).toNode.number)));
		}

		clone.layers = layers;
		clone.nextNode = nextNode;
		clone.biasNode = biasNode;
		clone.connectNodes();

		return clone;
	}

}

class Node {

	int number;
	float inputSum = 0; // current sum i.e. before activiation
	float outputValue = 0; // after activation function is applied
	ArrayList<connectionGene> outputConnections = new ArrayList<connectionGene>();
	int layer = 0;

	public Node(int number) {
		this.number = number;
	}

	public void engage() {
		if (layer != 0) { // no sigmoid for the inputs and bias
			outputValue = sigmoid(inputSum);
		}

		for (int i = 0; i < outputConnections.size(); i++) { // for each connection
			if (outputConnections.get(i).enabled) {
				outputConnections.get(i).toNode.inputSum += outputConnections.get(i).weight * outputValue;
			}
		}
	}

	public float sigmoid(float x) {
		return (float) (1 / (1 + Math.exp(x)));
	}

	// returns whether this node connected to the parameter node
	// used when adding a new connection
	public boolean isConnectedTo(Node node) {
		if (node.layer == layer) {// nodes in the same layer cannot be connected
			return false;
		}

		// you get it
		if (node.layer < layer) {
			for (int i = 0; i < node.outputConnections.size(); i++) {
				if (node.outputConnections.get(i).toNode == this) {
					return true;
				}
			}
		} else {
			for (int i = 0; i < outputConnections.size(); i++) {
				if (outputConnections.get(i).toNode == node) {
					return true;
				}
			}
		}

		return false;
	}

	public Node clone() {
		Node clone = new Node(number);
		clone.layer = layer;
		return clone;
	}
}

class connectionGene {
	Node fromNode;
	Node toNode;
	float weight;
	boolean enabled = true;
	int innovationNo; // each connection is given a innovation number to compare genomes

	public connectionGene(Node from, Node to, float w, int inno) {
		fromNode = from;
		toNode = to;
		weight = w;
		innovationNo = inno;
	}

	public void mutateWeight() {
		float rand = (float) Math.random();
		if (rand < 0.1) {// 10% of the time completely change the weight
			weight = (float) (Math.random() * 2 - 1);
		} else { // otherwise slightly change it
			Random r = new Random();
			weight += r.nextGaussian() / 50;
			// keep weight between bounds
			if (weight > 1) {
				weight = 1;
			}
			if (weight < -1) {
				weight = -1;
			}
		}
	}

	public connectionGene clone(Node from, Node to) {
		connectionGene clone = new connectionGene(from, to, weight, innovationNo);
		clone.enabled = enabled;

		return clone;
	}
}

class connectionHistory {
	int fromNode;
	int toNode;
	int innovationNumber;

	ArrayList<Integer> innovationNumbers = new ArrayList<Integer>(); // the innovation Numbers from the connections of
																		// the genome which first had this mutation

	@SuppressWarnings("unchecked")
	public connectionHistory(int from, int to, int inno, ArrayList<Integer> innovationNos) {
		fromNode = from;
		toNode = to;
		innovationNumber = inno;
		innovationNumbers = (ArrayList<Integer>) innovationNos.clone();
	}

	// returns whether the genome matches the original genome and the connection is
	// between the same nodes
	public boolean matches(Genome genome, Node from, Node to) {
		if (genome.genes.size() == innovationNumbers.size()) { // if the number of connections are different then the
																// genoemes aren't the same
			if (from.number == fromNode && to.number == toNode) {
				// next check if all the innovation numbers match from the genome
				for (int i = 0; i < genome.genes.size(); i++) {
					if (!innovationNumbers.contains(genome.genes.get(i).innovationNo)) {
						return false;
					}
				}

				// if reached this far then the innovationNumbers match the genes innovation
				// numbers and the connection is between the same nodes
				// so it does match
				return true;
			}
		}
		return false;
	}
}
