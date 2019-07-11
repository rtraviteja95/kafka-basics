package com.kafka.learning.basickafkaprograms.model;

import java.util.List;

public class AvroAttribute {
	
	private String attributeName;
	
	private List<String> attributeType;
	
	private String attributeDocument;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public List<String> getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(List<String> attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttributeDocument() {
		return attributeDocument;
	}

	public void setAttributeDocument(String attributeDocument) {
		this.attributeDocument = attributeDocument;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeDocument == null) ? 0 : attributeDocument.hashCode());
		result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime * result + ((attributeType == null) ? 0 : attributeType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvroAttribute other = (AvroAttribute) obj;
		if (attributeDocument == null) {
			if (other.attributeDocument != null)
				return false;
		} else if (!attributeDocument.equals(other.attributeDocument))
			return false;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		if (attributeType == null) {
			if (other.attributeType != null)
				return false;
		} else if (!attributeType.equals(other.attributeType))
			return false;
		return true;
	}

	public AvroAttribute(String attributeName, List<String> attributeType, String attributeDocument) {
		super();
		this.attributeName = attributeName;
		this.attributeType = attributeType;
		this.attributeDocument = attributeDocument;
	}

	@Override
	public String toString() {
		return "AvroAttribute {\n\tattributeName=" + attributeName + ", \n\tattributeType=" + attributeType
				+ ", \n\tattributeDocument=" + attributeDocument + "\n}";
	}
	
}
