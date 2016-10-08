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

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.stackwire.fca.Concept.Extent;

/**
 * Static methods of operations on extents
 */
public class Extents {

	/**
	 * Returns intersection of all specified extents, or empty if no
	 * intersection
	 * 
	 * @param extents
	 * @return intersection of all specified extents, or empty if no
	 *         intersection
	 */
	public static Optional<Extent> intersect(Extent... extents) {
		Set<Set<Integer>> sets = Sets.toSets(extents);
		if (sets == null || sets.isEmpty()) {
			return Optional.empty();
		}
		Set<Integer> intersectedSets = Sets.intersect(sets);
		if (intersectedSets.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new Extent(intersectedSets));
	}

	public static Optional<Extent> intersectExtents(Collection<Extent> extents) {
		return intersect(extents.toArray(new Extent[extents.size()]));
	}

}
