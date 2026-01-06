package cp213;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Uses GUI widgets and listeners to illustrate the use of inner classes.
 */
@SuppressWarnings("serial")
public class InnerClassPanel extends JPanel {

    // Constants
    private static final int START = 0;
    private static final int END = 100;
    private static final int INC = 5;
    private static final String[] MONTH_STRINGS = new DateFormatSymbols().getMonths();
    private static final SpinnerListModel MONTH_MODEL = new SpinnerListModel(MONTH_STRINGS);

    // Attributes
    private String buttonPush = "";
    private String radioButtonSet = "";
    private int sliderSet = START;
    private String spinnerSet = "";
    private String textEntry = "";

    // GUI elements
    private final JButton button = new JButton("Push Me");
    private final ArrayList<String> checkBoxesSelected = new ArrayList<>();
    private final JCheckBox ketchup = new JCheckBox("Ketchup");
    private final JCheckBox mustard = new JCheckBox("Mustard");
    private final JCheckBox onions = new JCheckBox("Onions");
    private final JLabel label = new JLabel();
    private final JSlider slider = new JSlider(JSlider.HORIZONTAL, START, END, INC);
    private final JSpinner spinner = new JSpinner(MONTH_MODEL);
    private final ButtonGroup starGroup = new ButtonGroup();
    private final JRadioButton starTrek = new JRadioButton("Star Trek");
    private final JRadioButton starWars = new JRadioButton("Star Wars");
    private final JTextField textField = new JTextField();

    public InnerClassPanel() {
	layoutView();
	registerListeners();
    }

    private void layoutView() {
	this.starGroup.add(this.starTrek);
	this.starGroup.add(this.starWars);
	this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	this.setLayout(new GridLayout(17, 1, 5, 5));

	this.add(new JLabel("Button"));
	this.add(this.button);
	this.add(new JLabel("Slider"));
	this.add(this.slider);
	this.add(new JLabel("Text Field"));
	this.add(this.textField);
	this.add(new JLabel("Spinner"));
	this.add(this.spinner);
	this.add(new JLabel("Radio Buttons"));
	this.add(this.starTrek);
	this.add(this.starWars);
	this.add(new JLabel("Check Boxes"));
	this.add(this.mustard);
	this.add(this.onions);
	this.add(this.ketchup);
	this.add(new JSeparator(SwingConstants.HORIZONTAL));
	this.add(this.label);
    }

    private void registerListeners() {
	this.button.addActionListener(new ButtonListener());
	this.textField.addActionListener(new TextFieldListener());
	this.starTrek.addActionListener(new RadioButtonListener());
	this.starWars.addActionListener(new RadioButtonListener());
	this.spinner.addChangeListener(new SpinnerListener());
	this.slider.addChangeListener(new SliderListener());
	this.ketchup.addItemListener(new CheckBoxListener());
	this.mustard.addItemListener(new CheckBoxListener());
	this.onions.addItemListener(new CheckBoxListener());
    }

    private class ButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
	    buttonPush = button.getText();
	    label.setText(buttonPush);
	    System.out.println(buttonPush);
	}
    }

    private class RadioButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
	    final JRadioButton source = (JRadioButton) e.getSource();
	    radioButtonSet = source.getText();
	    label.setText(radioButtonSet);
	    System.out.println(radioButtonSet);
	}
    }

    private class CheckBoxListener implements ItemListener {
	@Override
	public void itemStateChanged(final ItemEvent e) {
	    final JCheckBox source = (JCheckBox) e.getItem();
	    final String text = source.getText();

	    if (e.getStateChange() == ItemEvent.SELECTED) {
		checkBoxesSelected.add(text);
	    } else {
		checkBoxesSelected.remove(text);
	    }

	    String result = String.join(", ", checkBoxesSelected);
	    label.setText(result);
	    System.out.println(result);
	}
    }

    private class SliderListener implements ChangeListener {
	@Override
	public void stateChanged(final ChangeEvent e) {
	    final JSlider source = (JSlider) e.getSource();
	    if (!source.getValueIsAdjusting()) {
		sliderSet = source.getValue();
		String text = Integer.toString(sliderSet);
		label.setText(text);
		System.out.println(text);
	    }
	}
    }

    private class SpinnerListener implements ChangeListener {
	@Override
	public void stateChanged(final ChangeEvent e) {
	    final JSpinner source = (JSpinner) e.getSource();
	    spinnerSet = source.getValue().toString();
	    label.setText(spinnerSet);
	    System.out.println(spinnerSet);
	}
    }

    private class TextFieldListener implements ActionListener {
	@Override
	public void actionPerformed(final ActionEvent e) {
	    final JTextField source = (JTextField) e.getSource();
	    textEntry = source.getText();
	    label.setText(textEntry);
	    System.out.println(textEntry);
	}
    }

    public String CheckBoxesSelectedToString() {
	return Arrays.toString(this.checkBoxesSelected.toArray());
    }

    public String getButtonPush() {
	return this.buttonPush;
    }

    public ArrayList<String> getCheckBoxesSelected() {
	return this.checkBoxesSelected;
    }

    public String getRadioButtonSet() {
	return this.radioButtonSet;
    }

    public int getSliderSet() {
	return this.sliderSet;
    }

    public String getSpinnerSet() {
	return this.spinnerSet;
    }

    public String getTextEntry() {
	return this.textEntry;
    }
}
