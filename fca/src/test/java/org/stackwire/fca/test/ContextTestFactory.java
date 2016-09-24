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
package org.stackwire.fca.test;

import java.util.Arrays;

import org.stackwire.fca.FormalContext;

public class ContextTestFactory {

	/**
	 * Formal Context from Concept Data Analysis (Table 1.1)
	 * 
	 * @return
	 */
	public static FormalContext vertebateAnimals() {
		int[][] relations = { { 0, 1, 0, 0, 1, 1, 0, 1, 0 }, { 0, 1, 1, 0, 1, 1, 0, 0, 0 },
				{ 0, 0, 0, 1, 1, 0, 0, 1, 0 }, { 1, 0, 1, 0, 1, 0, 1, 0, 0 }, { 0, 0, 1, 0, 1, 1, 1, 0, 0 },
				{ 1, 0, 0, 0, 1, 0, 1, 0, 0 }, { 1, 0, 0, 0, 1, 0, 1, 0, 1 } };
		return FormalContext.create(
				Arrays.asList("Bat (1)", "Eagle (2)", "Monkey (3)", "Parrot fish (4)", "Penguin (5)", "Shark (6)",
						"Lantern fish (7)"),
				Arrays.asList("breathes in water (a)", "can fly (b)", "has beak (c)", "has hands (d)",
						"has skeleton (e)", "has wings (f)", "lives in water (g)", "is viviparous (h)",
						"produces light (i)"),
				relations);
	}
}
