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

import java.io.IOException;
import java.io.InputStream;

public final class FCA {
	
	private final FormalConceptGenerator generator;
	
	private final InputParser inputParser;

	public FCA(FormalConceptGenerator generator, InputParser inputParser) {
		this.generator = generator;
		this.inputParser = inputParser;
	}
	
	public FormalContext generateFrom(InputStream input) throws IOException {
		FormalContext ctx = inputParser.parse(input);
		return generator.generateConceptsFor(ctx);
	}
}
