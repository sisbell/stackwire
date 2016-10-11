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

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class ContextTest {

	@Test
	public void clarifyNames() throws Exception {
		double[][] relations = { { 1, 1, 1, 1 }, { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 } };

		Context fc = new Context.ContextBuilder(relations).build();
		Context clarifiedContext = fc.clarify();

		assertTrue(clarifiedContext.getObjectNames().containsAll(Arrays.asList("x1", "x2", "x3:x4", "x5")));
		assertTrue(clarifiedContext.getAttributeNames().containsAll(Arrays.asList("y1", "y2", "y3:y4")));
	}

	/**
	 * Example 2.39 (INTRODUCTION TO FORMAL CONCEPT ANALYSIS, 2008 - BELOHLAVEK)
	 */
	@Test
	public void clarifyRelations() throws Exception {
		double[][] clarifiedRelations = { { 1, 1, 1 }, { 1, 0, 1 }, { 0, 1, 1 }, { 1, 0, 0 } };
		double[][] relations = { { 1, 1, 1, 1 }, { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 } };

		Context fc = new Context.ContextBuilder(relations).build();
		Context clarifiedContext = fc.clarify();
		assertTrue(Arrays.deepEquals(clarifiedRelations, clarifiedContext.getRelations()));

	}
}
