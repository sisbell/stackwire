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
package org.stackwire.fca;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.stackwire.fca.Concept.Extent;
import org.stackwire.fca.Concept.Intent;

import com.google.common.collect.Sets;

/**
 * Perform common operations on concepts
 */
public final class ConceptOperations {


	
	private static Set<Integer> intersect(Set<Set<Integer>> set) {
		Iterator<Set<Integer>> it = set.iterator();
		Set<Integer> result = it.next();
		while (it.hasNext()) {
			result = Sets.intersection(result, Sets.newHashSet(it.next()));
		}
		return result;
	}

	private static Set<Set<Integer>> toSets(SemanticIndexSet... indexSet) {
		Set<Set<Integer>> items = Arrays.stream(indexSet).map(SemanticIndexSet::getIndicies)
				.collect(Collectors.toSet());
		if (items.isEmpty()) {
			return null;
		}
		return items;
	}

	private Optional<Concept> createFormalConceptFrom(Collection<Extent> extents,
			Collection<Intent> intents) {
			Optional<Extent> extent = intersection(extents.toArray(new Extent[extents.size()]));
			if(extent.isPresent()) {
				Optional<Intent> intent = intersection(intents.toArray(new Intent[intents.size()]));
				if(intent.isPresent()) {
					return Optional.of(Concept.create(0, extent.get(), intent.get(), ConceptType.FORMAL_CONCEPT));
				}
			}
			
			return Optional.empty();
	}

	public Optional<Concept> intersection(Concept... concepts) {
		return Optional.empty();
	}

	/**
	 * Returns intersection of all specified extents, or empty if no intersection
	 * 
	 * @param extents 
	 * @return intersection of all specified extents, or empty if no intersection
	 */
	public Optional<Extent> intersection(Extent... extents) {
		Set<Set<Integer>> sets = toSets(extents);
		if (sets == null || sets.isEmpty()) {
			return Optional.empty();
		}
		Set<Integer> intersectedSets = intersect(sets);
		if (intersectedSets.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new Extent(intersectedSets));
	}

	public Optional<Intent> intersection(Intent... intents) {
		Set<Set<Integer>> sets = toSets(intents);
		if (sets == null || sets.isEmpty()) {
			return Optional.empty();
		}
		Set<Integer> intersectedSets = intersect(sets);
		if (intersectedSets.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new Intent(intersectedSets));
	}

}
