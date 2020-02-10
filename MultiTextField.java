package james.games.graphics;

import java.awt.Panel;
import java.awt.GridLayout;
/**
 *  MultiTextField is a panel that is designed to collect values for a number of variables.
 */
public class MultiTextField extends Panel {
	private static final long serialVersionUID = 1L;
	private final LabeledTextField[] fields;	
	/**
	 * Method MultiTextField
	 *
	 *
	 * @param labels - the names of the variables
	 * @param values - the initial values for said variables
	 *
	 */
	public MultiTextField(String[] labels, String[] values) {
		fields = new LabeledTextField[labels.length];
		for (int i = 0; i < fields.length; i++) fields[i] = new LabeledTextField(labels[i],values[i]);
		setLayout(new GridLayout(0,1));
		for (int i = 0; i < fields.length; i++) add(fields[i]);
	}

	/**
	 * Method getValues
	 *
	 *
	 * @return the values of the variables.
	 *
	 */
	public String[] getValues() {
		String[] out = new String[fields.length];
		for (int i = 0; i < out.length; i++) out[i]=fields[i].getValue();
		return out;
	}	
}
