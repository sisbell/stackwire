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
package org.stackwire.fca.graph;

import java.util.HashMap;
import java.util.Set;

public class Graph {

	private HashMap<String, Node> nodes = new HashMap<>();

	public void add(Node node) {
		if (!contains(node.label)) {
			nodes.put(node.label, node);
		}
	}

	public boolean contains(String label) {
		return nodes.containsKey(label);
	}

	public Node get(String label) {
		return nodes.get(label);
	}

	public Set<String> labels() {
		return nodes.keySet();
	}
}
