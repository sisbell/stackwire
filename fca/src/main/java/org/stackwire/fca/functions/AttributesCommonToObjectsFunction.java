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
package org.stackwire.fca.functions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.stackwire.fca.utils.Utils;

/**
 * This is operator B'. It returns a set of attributes that are common to a
 * collection of objects.
 */
public class AttributesCommonToObjectsFunction implements Function<Collection<Integer>, Set<Integer>> {

	private final double[][] relations;

	private final double threshold;

	public AttributesCommonToObjectsFunction(double[][] relations, double threshold) {
		this.relations = relations;
		this.threshold = threshold;
	}

	@Override
	public Set<Integer> apply(Collection<Integer> indicies) { // objects
		if (indicies == null || indicies.isEmpty()) {
			return Utils.rangeSet(0, relations[0].length - 1);
		}
		Set<Integer> attributes = new HashSet<>();
		int cols = relations[0].length;
		for (int j = 0; j < cols; j++) {
			boolean add = true;
			for (int i : indicies) {// rows
				if (relations[i][j] <= threshold) {
					add = false;
					break;
				}
			}
			if (add) {
				attributes.add(j);
			}
		}
		return attributes;
	}

}
