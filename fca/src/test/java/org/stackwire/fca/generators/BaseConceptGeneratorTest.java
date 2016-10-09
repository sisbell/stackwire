package org.stackwire.fca.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.stackwire.fca.Concept;
import org.stackwire.fca.Concept.Extent;
import org.stackwire.fca.Concept.Intent;
import org.stackwire.fca.ConceptGenerator;
import org.stackwire.fca.ConceptType;
import org.stackwire.fca.Context;

public abstract class BaseConceptGeneratorTest {

	private static final double[][] relations = { { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 1, 1, 0 }, { 1, 1, 1, 1 },
			{ 0, 0, 0, 0 }, { 0, 1, 0, 0 } };

	@Test
	public void generate() throws Exception {
		Context fc = new Context.ContextBuilder(relations).build();
		
		ConceptGenerator generator = getGenerator();
		generator.generateConceptsFor(fc, 0);
		Collection<Concept> result = fc.getConceptsOf(ConceptType.FORMAL_CONCEPT).get();
		assertEquals(4, result.size());
		// System.out.println(result);
		Concept fc0 = new Concept.ConceptBuilder(new Extent(Arrays.asList(1, 3)), new Intent(Arrays.asList(0, 1, 2, 3)))
				.build();
		Concept fc1 = new Concept.ConceptBuilder(new Extent(Arrays.asList(1, 2, 3)), new Intent(Arrays.asList(1, 2)))
				.build();
		Concept fc2 = new Concept.ConceptBuilder(new Extent(Arrays.asList(1, 2, 3, 5)), new Intent(Arrays.asList(1)))
				.build();
		Concept fc3 = new Concept.ConceptBuilder(new Extent(Arrays.asList(0, 1, 2, 3, 4, 5)),
				new Intent(Arrays.asList())).build();

		List<Concept> expectedConcepts = Arrays.asList(fc0, fc1, fc2, fc3);
		assertTrue(result.containsAll(expectedConcepts));
	}

	protected abstract ConceptGenerator getGenerator();

}
