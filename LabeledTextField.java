package james.games.graphics;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Label;
/**
 * A LabeledTextField consists of a label and a textfield. The label will appear close to the textField fo that the user knows what the TextField represents.
 */
public class LabeledTextField extends Panel {
	private static final long serialVersionUID = 1L;
	private TextField field;
	private Label label;	
	/**
	 * Method getValue
	 *
	 *
	 * @return the value of the textField
	 *
	 */
	public String getValue() {
		return field.getText();
	}


	/**
	 * Method LabeledTextField
	 *
	 *
	 * @param name - the text for the label.
	 *
	 */
	public LabeledTextField(String name) {
		field=new TextField();
		addComponents(name);
	}
	/**
	 * Method LabeledTextField
	 *
	 *
	 * @param name - the text for the label.
	 * @param value - the initial value of the textField.
	 *
	 */
	public LabeledTextField(String name, String value) {
		field=new TextField(value);
		addComponents(name);
	}	
	private void addComponents(String name) {
		label=new Label(name,Label.CENTER);
		setLayout(new GridLayout(1,0));
		add(field);
		add(label);
	}
	/**
	 * Method setValue
	 *
	 *
	 * @param value - the new value for the textField.
	 *
	 */
	public void setValue(String val) {
		field.setText(val);
	}
}
