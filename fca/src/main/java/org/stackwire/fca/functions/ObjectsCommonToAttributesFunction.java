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
 * This is operator A'
 */
public class ObjectsCommonToAttributesFunction implements Function<Collection<Integer>, Set<Integer>> {

	private final double[][] relations;

	public ObjectsCommonToAttributesFunction(double[][] relations) {
		this.relations = relations;
	}

	@Override
	public Set<Integer> apply(Collection<Integer> indicies) { // attributes
		if(indicies == null || indicies.isEmpty()) {
			return Utils.rangeSet(0, relations.length - 1);
		}
		Set<Integer> objects = new HashSet<>();
		int rows = relations.length;
		for (int i = 0; i < rows; i++) {
			boolean add = true;
			for (int j : indicies) {// cols
				if (relations[i][j] == 0) {
					add = false;
					break;
				}
			}
			if (add) {
				objects.add(i);
			}

		}
		return objects;
	}
}
