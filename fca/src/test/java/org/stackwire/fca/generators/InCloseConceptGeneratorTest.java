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

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.stackwire.fca.FormalConcept;
import org.stackwire.fca.FormalContext;
import org.stackwire.fca.generators.NaiveFormalConceptGenerator;

public class InCloseConceptGeneratorTest {

	private static final int[][] relations = { { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 1, 1, 0 }, { 1, 1, 1, 1 },
			{ 0, 0, 0, 0 }, { 0, 1, 0, 0 } };

	@Test
	public void generate() throws Exception {
		FormalContext fc = FormalContext.create(relations);
		InCloseFormalConceptGenerator generator = new InCloseFormalConceptGenerator();
		generator.generateConceptsFor(fc);
		Collection<FormalConcept> result = fc.getFormalConcepts();
		System.out.println(fc.getFormalConcepts());
		assertEquals(3, result.size());
	}
}
