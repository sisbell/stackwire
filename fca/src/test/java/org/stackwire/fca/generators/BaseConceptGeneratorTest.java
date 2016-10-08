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
import org.stackwire.fca.tags.IndexTag;

public abstract class BaseConceptGeneratorTest {

	private static final double[][] relations = { { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 1, 1, 0 }, { 1, 1, 1, 1 },
			{ 0, 0, 0, 0 }, { 0, 1, 0, 0 } };

	@Test
	public void generate() throws Exception {
		Context fc = Context.create(relations);
		ConceptGenerator generator = getGenerator();
		generator.generateConceptsFor(fc);
		Collection<Concept> result = fc.getConceptsOf(ConceptType.FORMAL_CONCEPT).get();
		assertEquals(4, result.size());
		// System.out.println(result);
		Concept fc0 = Concept.create(new Extent(Arrays.asList(1, 3)), new Intent(Arrays.asList(0, 1, 2, 3)),
				ConceptType.FORMAL_CONCEPT);
		Concept fc1 = Concept.create(new Extent(Arrays.asList(1, 2, 3)), new Intent(Arrays.asList(1, 2)),
				ConceptType.FORMAL_CONCEPT);
		Concept fc2 = Concept.create(new Extent(Arrays.asList(1, 2, 3, 5)), new Intent(Arrays.asList(1)),
				ConceptType.FORMAL_CONCEPT);
		Concept fc3 = Concept.create(new Extent(Arrays.asList(0, 1, 2, 3, 4, 5)), new Intent(Arrays.asList()),
				ConceptType.FORMAL_CONCEPT, new IndexTag(0));

		List<Concept> expectedConcepts = Arrays.asList(fc0, fc1, fc2, fc3);
		assertTrue(result.containsAll(expectedConcepts));
	}

	protected abstract ConceptGenerator getGenerator();

}
