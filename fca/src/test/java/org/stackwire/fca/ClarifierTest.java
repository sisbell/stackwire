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

public class ClarifierTest {

	@Test
	public void clarify() throws Exception {
		double[][] crossTable = { { 1, 1, 1, 1 }, { 1, 0, 1, 1 }, { 0, 1, 1, 1 }, { 0, 1, 1, 1 }, { 1, 0, 0, 0 } };
		double[][] expected = { { 1, 1, 1 }, { 1, 0, 1 }, { 0, 1, 1 }, { 1, 0, 0 } };

		double[][] result = Clarifier.clarify(crossTable).getClarifiedCrossTable();

		assertTrue(Arrays.deepEquals(expected, result));

	}
	
	@Test
	public void clarify2() throws Exception {
		double[][] crossTable = { { 1, 1, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } };
		double[][] expected = { { 1, 1, 1, 1 } };

		double[][] result = Clarifier.clarify(crossTable).getClarifiedCrossTable();

		assertTrue(Arrays.deepEquals(expected, result));

	}
}
