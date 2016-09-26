package org.stackwire.fca;

public class IndexTag implements ConceptTag {

	private Object index;

	public IndexTag(int index) {
		this.index = index;
	}

	public Object getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "IndexTag [index=" + index + "]";
	}
	
}
