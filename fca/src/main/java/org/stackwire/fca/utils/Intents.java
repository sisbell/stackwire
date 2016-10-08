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

import org.stackwire.fca.Concept.Intent;

/**
 * Static methods of operations on intents
 */
public class Intents {

	public static Optional<Intent> intersect(Collection<Intent> intents) {
		return intersect(intents.toArray(new Intent[intents.size()]));
	}

	public static Optional<Intent> intersect(Intent... intents) {

		Set<Set<Integer>> sets = Sets.toSets(intents);
		if (sets == null || sets.isEmpty()) {
			return Optional.empty();
		}
		Set<Integer> intersectedSets = Sets.intersect(sets);
		if (intersectedSets.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new Intent(intersectedSets));
	}

}
