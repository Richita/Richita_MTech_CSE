package com.microkernel.core.spring.bean;

import java.beans.PropertyEditorSupport;

/**
 * Not Properly Implemented
 * @author NinadIngole
 *
 */
public class CustomPropertyEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println(text);
		super.setAsText(text);
	}

}
