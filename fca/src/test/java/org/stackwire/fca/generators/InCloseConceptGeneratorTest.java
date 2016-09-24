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
package org.stackwire.fca.generators;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.stackwire.fca.FormalConcept;
import org.stackwire.fca.FormalConcept.Extent;
import org.stackwire.fca.FormalConcept.Intent;

import org.stackwire.fca.FormalContext;

public class InCloseConceptGeneratorTest {

	private static final int[][] relations = { { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 1, 1, 0 }, { 1, 1, 1, 1 },
			{ 0, 0, 0, 0 }, { 0, 1, 0, 0 } };

	@Test
	public void generate() throws Exception {
		FormalContext fc = FormalContext.create(relations);
		InCloseFormalConceptGenerator generator = new InCloseFormalConceptGenerator();
		generator.generateConceptsFor(fc);
		Collection<FormalConcept> result = fc.getFormalConcepts();
		assertEquals(4, result.size());

		FormalConcept fc0 = FormalConcept.create(0, new Extent(Arrays.asList(1, 3)),
				new Intent(Arrays.asList(0, 1, 2, 3)));
		FormalConcept fc1 = FormalConcept.create(0, new Extent(Arrays.asList(1, 2, 3)),
				new Intent(Arrays.asList(1, 2)));
		FormalConcept fc2 = FormalConcept.create(0, new Extent(Arrays.asList(1, 2, 3, 5)),
				new Intent(Arrays.asList(1)));
		FormalConcept fc3 = FormalConcept.create(0, new Extent(Arrays.asList(0, 1, 2, 3, 4, 5)),
				new Intent(Arrays.asList()));

		List<FormalConcept> expectedConcepts = Arrays.asList(fc0, fc1, fc2, fc3);
		assertTrue(result.containsAll(expectedConcepts));
	}
}
