package cp213;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class OrderPanel extends JPanel {

    private class PrintListener implements ActionListener {
	@Override
	public void actionPerformed(final ActionEvent e) {
	    PrinterJob job = PrinterJob.getPrinterJob();
	    job.setPrintable(order);
	    boolean doPrint = job.printDialog();
	    if (doPrint) {
		try {
		    job.print();
		} catch (PrinterException ex) {
		    JOptionPane.showMessageDialog(OrderPanel.this, "Printing error: " + ex.getMessage(),
			    "Printer Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	}
    }

    private class QuantityListener implements FocusListener {
	private MenuItem item = null;

	QuantityListener(final MenuItem item) {
	    this.item = item;
	}

	@Override
	public void focusGained(FocusEvent e) {
	    // do nothing
	}

	@Override
	public void focusLost(FocusEvent e) {
	    JTextField field = (JTextField) e.getSource();
	    int quantity = 0;

	    try {
		quantity = Integer.parseInt(field.getText());
		if (quantity < 0) {
		    quantity = 0;
		}
	    } catch (NumberFormatException ex) {
		quantity = 0;
	    }

	    field.setText(Integer.toString(quantity));
	    order.update(item, quantity);
	    updateTotals();
	}
    }

    private Menu menu = null;
    private final Order order = new Order();
    private final DecimalFormat priceFormat = new DecimalFormat("$##0.00");
    private final JButton printButton = new JButton("Print");
    private final JLabel subtotalLabel = new JLabel("0");
    private final JLabel taxLabel = new JLabel("0");
    private final JLabel totalLabel = new JLabel("0");

    private JLabel nameLabels[] = null;
    private JLabel priceLabels[] = null;
    private JTextField quantityFields[] = null;

    public OrderPanel(final Menu menu) {
	this.menu = menu;
	this.nameLabels = new JLabel[this.menu.size()];
	this.priceLabels = new JLabel[this.menu.size()];
	this.quantityFields = new JTextField[this.menu.size()];
	this.layoutView();
	this.registerListeners();
    }

    private void layoutView() {
	this.setLayout(new GridLayout(menu.size() + 5, 4)); // Extra rows for totals/print

	this.add(new JLabel("Item"));
	this.add(new JLabel("Price"));
	this.add(new JLabel("Quantity"));
	this.add(new JLabel()); // Spacer

	for (int i = 0; i < menu.size(); i++) {
	    MenuItem item = menu.getItem(i);

	    nameLabels[i] = new JLabel(item.getDescript());
	    priceLabels[i] = new JLabel(priceFormat.format(item.getCost()));
	    quantityFields[i] = new JTextField("0");

	    this.add(nameLabels[i]);
	    this.add(priceLabels[i]);
	    this.add(quantityFields[i]);
	    this.add(new JLabel()); // empty spacer
	}

	this.add(new JLabel("Subtotal:"));
	this.add(subtotalLabel);
	this.add(new JLabel());
	this.add(new JLabel());

	this.add(new JLabel("Taxes:"));
	this.add(taxLabel);
	this.add(new JLabel());
	this.add(new JLabel());

	this.add(new JLabel("Total:"));
	this.add(totalLabel);
	this.add(new JLabel());
	this.add(new JLabel());

	this.add(printButton);
    }

    private void registerListeners() {
	this.printButton.addActionListener(new PrintListener());

	for (int i = 0; i < quantityFields.length; i++) {
	    quantityFields[i].addFocusListener(new QuantityListener(menu.getItem(i)));
	}
    }

    private void updateTotals() {
	subtotalLabel.setText(priceFormat.format(order.getSubTotal()));
	taxLabel.setText(priceFormat.format(order.getTaxes()));
	totalLabel.setText(priceFormat.format(order.getTotal()));
    }
}
