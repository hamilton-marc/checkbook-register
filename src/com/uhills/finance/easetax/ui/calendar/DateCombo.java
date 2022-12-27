/*
 * Created on Aug 29, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.uhills.finance.easetax.ui.calendar;

import java.util.*;
import java.text.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;

/**
 * @author hamiltonm
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateCombo extends Composite
{
    public static final String  DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    private Text                m_dateEntryText;
    private Button              m_comboButton;
    private int                 m_iStyle;
    private Date                m_date;
    private SimpleDateFormat    m_dateFormatter;

    public DateCombo(Composite parent, int iStyle)
    {
        super(parent, SWT.NO_RADIO_GROUP);

        m_iStyle = iStyle;

        initializeDefaultValues();
        createControl();
    }

    private void createControl()
    {
        GridLayout          gridLayout = new GridLayout();
        GridData            gridData;

        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        gridLayout.horizontalSpacing = 2;
        gridLayout.verticalSpacing = 0;
        gridLayout.numColumns = 2;

        setLayout(gridLayout);

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        m_dateEntryText = new Text(this, m_iStyle);
        m_dateEntryText.setLayoutData(gridData);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_comboButton = new Button(this, (m_iStyle & SWT.FLAT) | SWT.PUSH | SWT.CENTER);
        m_comboButton.setLayoutData(gridData);
        m_comboButton.setText("...");
        m_comboButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                pickDate();
            }
        }
        );
    }

    private void initializeDefaultValues()
    {
        Calendar        calendar = Calendar.getInstance();

        m_dateFormatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

        calendar.clear(Calendar.HOUR);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        m_date = calendar.getTime();
    }

    private void pickDate()
    {
        Date                    chosenDate = null;
        CalendarDatePicker      datePicker;
        String                  strDateEntry = m_dateEntryText.getText().trim();

        if (strDateEntry.length() > 0)
        {
            try
            {
                chosenDate = m_dateFormatter.parse(m_dateEntryText.getText());
            }
            catch (ParseException ex)
            {
            }
        }

        datePicker = new CalendarDatePicker(getShell());
        datePicker.setBlockOnOpen(true);

        if (chosenDate != null)
            datePicker.setSelectedDate(chosenDate);

        datePicker.open();

        if (datePicker.getReturnCode() != CalendarDatePicker.CANCEL)
        {
            chosenDate = datePicker.getSelectedDate();
            setDate(chosenDate);
        }
    }

    public void setPattern(String strPattern)
    {
        m_dateFormatter.applyPattern(strPattern);
        refresh();
    }

    public String getPattern()
    {
        return (m_dateFormatter.toPattern());
    }

    public String getText()
    {
        return (m_dateEntryText.getText());
    }

    public void setText(String strText)
    {
        Date                    chosenDate = null;

        try
        {
            chosenDate = m_dateFormatter.parse(strText);
        }
        catch (ParseException ex)
        {
        }

        if (chosenDate != null)
        {
            m_date = chosenDate;
            refresh();
        }
        else
        {
            m_dateEntryText.setText("");
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
     */
    public void setEnabled(boolean bEnabled)
    {
        m_dateEntryText.setEnabled(bEnabled);
        m_comboButton.setEnabled(bEnabled);

        super.setEnabled(bEnabled);
    }

    public Date getDate()
    {
        Date                    chosenDate = null;

        try
        {
            chosenDate = m_dateFormatter.parse(m_dateEntryText.getText());
        }
        catch (ParseException ex)
        {
        }

        return (chosenDate);
    }

    public void setDate(Date newDate)
    {
        m_date = newDate;
        refresh();
    }

    public void clear()
    {
        setDate(null);
    }

    public void refresh()
    {
        if (m_date != null)
            m_dateEntryText.setText(m_dateFormatter.format(m_date));
        else
            m_dateEntryText.setText("");
    }
}
