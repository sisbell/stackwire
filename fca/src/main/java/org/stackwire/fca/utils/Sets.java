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
package org.stackwire.fca.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class Sets {

	public static Set<Set<Integer>> toSets(SemanticIndexSet... indexSet) {
		Set<Set<Integer>> items = Arrays.stream(indexSet).map(SemanticIndexSet::getIndicies)
				.collect(Collectors.toSet());
		if (items.isEmpty()) {
			return null;
		}
		return items;
	}

	public static Set<Integer> intersect(Set<Set<Integer>> set) {
		Iterator<Set<Integer>> it = set.iterator();
		Set<Integer> result = it.next();
		while (it.hasNext()) {
			result = com.google.common.collect.Sets.intersection(result,
					com.google.common.collect.Sets.newHashSet(it.next()));
		}
		return result;
	}
}
