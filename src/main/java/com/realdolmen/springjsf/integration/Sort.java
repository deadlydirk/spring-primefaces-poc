package com.realdolmen.springjsf.integration;

/**
 * Class to be used by repository classes to order a set of results.
 * 
 */
public class Sort {

	/**
	 * The field name to sort on. Should be a property of the entity.
	 */
	private final String fieldName;
	/**
	 * The direction the sort should be applied in
	 */
	private final SortOrder sortOrder;

	/**
	 * Creates a Sort object, containing the order and the fieldName
	 * 
	 * @param fieldName
	 *            The field name to sort on
	 * @param order
	 *            the direction the order should be applied in
	 */
	public Sort(final String fieldName, final SortOrder order) {
		validateFieldname(fieldName);
		this.fieldName = fieldName;
		this.sortOrder = order;
	}

	private void validateFieldname(final String fieldName2) {
		if (fieldName2 == null || fieldName2.trim().length() == 0) {
			throw new IllegalArgumentException("Fieldname cannot be null");
		}
	}

	/**
	 * Creates a Sort object, containing the order and the fieldName. SortOrder
	 * will result to default, Ascending.
	 * 
	 * @param fieldName
	 *            The field name to sort on
	 */
	public Sort(final String fieldName) {
		this(fieldName, SortOrder.ASCENDING);
	}

	/**
	 * Returns the fieldName.
	 * 
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result
				+ ((sortOrder == null) ? 0 : sortOrder.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Sort other = (Sort) obj;
		if (fieldName == null) {
			if (other.fieldName != null) {
				return false;
			}
		} else if (!fieldName.equals(other.fieldName)) {
			return false;
		}
		if (sortOrder != other.sortOrder) {
			return false;
		}
		return true;
	}

	public enum SortOrder {
		ASCENDING, DESCENDING;
	}
}
