/**
 * Copyright 2016 Shane Isbell
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.stackwire.fca.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executors;

import org.apache.jena.graph.Triple;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.lang.PipedRDFIterator;
import org.apache.jena.riot.lang.PipedRDFStream;
import org.apache.jena.riot.lang.PipedTriplesStream;
import org.stackwire.fca.ConceptGenerator;
import org.stackwire.fca.Context;
import org.stackwire.fca.graph.Graph;
import org.stackwire.fca.graph.Node;

/**
 * Reader for schema.org n-triple format.
 */
public final class SchemaContextReader implements ContextReader {

	protected final class GraphResult {

		public final Set<String> attributeNames = new HashSet<>();

		public final Graph graph;

		public GraphResult(Graph graph) {
			this.graph = graph;
		}
	}

	/**
	 * Returns the node with the specified label, if it exists. Otherwise, it
	 * creates the node and adds it to the specified graph prior to returning
	 * the node.
	 * 
	 * @param label
	 *            label of the node
	 * @param graph
	 *            graph
	 * @return node with the specified label
	 */
	private static Node getOrCreateNode(String label, Graph graph) {
		Node node = graph.get(label);
		if (node == null) {
			node = new Node(label);
			graph.add(node);
		}
		return node;
	}

	/**
	 * Builds a context for the specified graph
	 * 
	 * @param graph
	 *            graph
	 * @param attributeNames
	 *            attribute names
	 * 
	 * @return context
	 */
	protected Context buildContext(Graph graph, Set<String> attributeNames) {
		List<String> attributes = new ArrayList<>(attributeNames);
		List<String> objects = new ArrayList<>(graph.labels());

		double[][] table = new double[objects.size()][attributes.size()];

		Queue<Node> queue = new LinkedList<>();
		queue.add(graph.get("Thing"));
		while (!queue.isEmpty()) {
			Node parent = queue.poll();
			if (parent != null) {
				int objectIndex = objects.indexOf(parent.label);
				for (String property : parent.properties) {
					table[objectIndex][attributes.indexOf(property)] = 1;
				}
				for (Node child : parent.children) {
					child.properties.addAll(parent.properties);
					queue.add(child);
				}
			}
		}

		return new Context.ContextBuilder(objects, attributes).relations(table).build();
	}

	/**
	 * Builds a graph from a set of n-triples iterator
	 * 
	 * @param iter
	 *            n-triples iterator
	 * 
	 * @return graph
	 */
	protected GraphResult buildGraph(PipedRDFIterator<Triple> iter) {
		Graph graph = new Graph();
		GraphResult result = new GraphResult(graph);

		while (iter.hasNext()) {
			Triple t = iter.next();

			String subject = t.getSubject().getLocalName();
			String predicate = t.getPredicate().getURI();

			if ("http://schema.org/domainIncludes".equals(predicate)) {
				getOrCreateNode(t.getObject().getLocalName(), graph).properties.add(subject);
				result.attributeNames.add(subject);
			} else if ("http://www.w3.org/2000/01/rdf-schema#subClassOf".equals(predicate)) {
				getOrCreateNode(t.getObject().getLocalName(), graph).children.add(getOrCreateNode(subject, graph));
			}
		}
		return result;
	}

	@Override
	public Context read(InputStream inputStream, ConceptGenerator generator) throws IOException {

		PipedRDFIterator<Triple> iter = new PipedRDFIterator<>();
		final PipedRDFStream<Triple> input = new PipedTriplesStream(iter);

		Executors.newSingleThreadExecutor().submit(() -> RDFDataMgr.parse(input, inputStream, Lang.TURTLE));

		GraphResult result = buildGraph(iter);
		return buildContext(result.graph, result.attributeNames);
	}

}
