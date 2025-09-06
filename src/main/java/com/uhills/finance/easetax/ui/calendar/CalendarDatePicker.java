/*
 * Created on Jun 21, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.calendar;

import java.util.*;
import java.text.*;

import org.eclipse.jface.window.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.events.*;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class CalendarDatePicker extends Window
{
    private static final int        DAYS_IN_WEEK = 7;
    private static final int        MAX_WEEKS_IN_MONTH = 6;
/*
    private static final int        MAX_TEXT_WIDTH = 250;
    private static final int        MIN_BUTTON_WIDTH = 90;
*/
    private static final String[]   DAYS_OF_WEEK = { "Sunday",
                                                     "Monday",
                                                     "Tuesday",
                                                     "Wednesday",
                                                     "Thursday",
                                                     "Friday",
                                                     "Saturday"
                                                   };

    private Color           m_defaultBackground;
    private Color           m_defaultForeground;

    private Button          m_prevMonthButton;
    private Button          m_nextMonthButton;
    private Label           m_monthLabel;
    private Label[]         m_dayHeaderLabels;
    private Label[][]       m_dayLabels;
    private Button          m_todayButton;
    private Button          m_cancelButton;

    private Date            m_selectedDate, m_dateShown;
    private boolean         m_bUseSingleCharDayNames;

    public CalendarDatePicker(Shell parentShell)
    {
        super(parentShell);

        setShellStyle(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);

        m_dayHeaderLabels = new Label[DAYS_IN_WEEK];
        m_dayLabels = new Label[MAX_WEEKS_IN_MONTH][DAYS_IN_WEEK];

        m_defaultBackground = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
        m_defaultForeground = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);

        m_selectedDate = removeTime(new Date());
    }

    public CalendarDatePicker(Shell parentShell, Date newDate)
    {
        this(parentShell);

        m_selectedDate = removeTime(newDate);
    }

    private Date removeTime(Date theDate)
    {
        Calendar        calendar = Calendar.getInstance();

        calendar.setTime(theDate);

        calendar.clear(Calendar.HOUR);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        return (calendar.getTime());
    }

    public void setUseSingleCharDayNames(boolean bNewValue)
    {
        m_bUseSingleCharDayNames = bNewValue;
    }

    public boolean getUseSingleCharDayNames()
    {
        return (m_bUseSingleCharDayNames);
    }

    public Date getSelectedDate()
    {
        return (m_selectedDate);
    }

    public void setSelectedDate(Date newDate)
    {
        m_selectedDate = newDate;
    }

    protected void configureShell(Shell newShell)
    {
        super.configureShell(newShell);

        newShell.setText("Date Picker");
    }

    protected Control createContents(Composite parent)
    {
        Composite           container = new Composite(parent, SWT.NO_FOCUS);
        GridLayout          gridLayout = new GridLayout();

        parent.setBackground(m_defaultBackground);
        container.setBackground(m_defaultBackground);

        gridLayout.marginHeight = 2;
        gridLayout.marginWidth = 2;
        gridLayout.horizontalSpacing = 1;
        gridLayout.verticalSpacing = 1;

        container.setLayout(gridLayout);

        createHeader(container);
        createCalendar(container);
        createButtonBar(container);

        showDate(m_selectedDate);

        return (container);
    }

    private void createHeader(Composite parent)
    {
        Composite           container = new Composite(parent, SWT.NO_FOCUS);
        GridLayout          gridLayout;
        GridData            gridData;

        gridLayout = new GridLayout(3, false);
        gridLayout.marginHeight = 1;
        gridLayout.marginWidth = 1;
        gridLayout.horizontalSpacing = 1;
        gridLayout.verticalSpacing = 1;
        gridData = new GridData(GridData.FILL_BOTH);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_prevMonthButton = new Button(container, SWT.CENTER | SWT.FLAT);
        m_prevMonthButton.setLayoutData(gridData);
        m_prevMonthButton.setText("<");
        m_prevMonthButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                goToPrevMonth();
            }
        }
        );

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        m_monthLabel = new Label(container, SWT.CENTER);
        m_monthLabel.setLayoutData(gridData);
        m_monthLabel.setText("Month Year");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
        m_nextMonthButton = new Button(container, SWT.CENTER | SWT.FLAT);
        m_nextMonthButton.setLayoutData(gridData);
        m_nextMonthButton.setText(">");
        m_nextMonthButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                goToNextMonth();
            }
        }
        );
    }

    private void createCalendar(Composite parent)
    {
        Composite           container = new Composite(parent, SWT.NO_FOCUS);
        GridLayout          gridLayout;
        GridData            gridData;
//      String              strCaption = "";
        int                 i,j;
        int                 iNumChars = (m_bUseSingleCharDayNames ? 1 : 3);
        Label               separator;

        container.setBackground(m_defaultBackground);

        gridLayout = new GridLayout(DAYS_IN_WEEK, false);
        gridLayout.marginHeight = 1;
        gridLayout.marginWidth = 1;
        gridLayout.horizontalSpacing = 6;
        gridLayout.verticalSpacing = 2;
        gridData = new GridData(GridData.FILL_BOTH);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        for (i=0; i < m_dayHeaderLabels.length; i++)
        {
            gridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_CENTER);
            m_dayHeaderLabels[i] = new Label(container, SWT.CENTER);
            m_dayHeaderLabels[i].setLayoutData(gridData);
            m_dayHeaderLabels[i].setText(DAYS_OF_WEEK[i].substring(0, iNumChars));
            m_dayHeaderLabels[i].setBackground(m_defaultBackground);
        }

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING);
        gridData.horizontalSpan = DAYS_IN_WEEK;
        gridData.heightHint = 1;
        separator = new Label(container, SWT.NO_FOCUS);
        separator.setLayoutData(gridData);
        separator.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));

        for (i=0; i < m_dayLabels.length; i++)
        {
            for (j=0; j < m_dayLabels[i].length; j++)
            {
                gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
                m_dayLabels[i][j] = new Label(container, SWT.CENTER);
                m_dayLabels[i][j].setLayoutData(gridData);

                int iCellNumber =  (i*DAYS_IN_WEEK) + j + 1;
                m_dayLabels[i][j].setText(String.valueOf(iCellNumber));
                m_dayLabels[i][j].setBackground(m_defaultBackground);
/*
                m_dayLabels[i][j].addPaintListener(new PaintListener()
                {
                    public void paintControl (PaintEvent event)
                    {
                        paintCellDate(event);
                    }
                }
                );
*/
                m_dayLabels[i][j].addMouseListener(new MouseAdapter()
                {
                    public void mouseDown(MouseEvent event)
                    {
                        Label       cellDateLabel = (Label) event.getSource();
                        Date        cellDate = (Date) cellDateLabel.getData();

                        selectDate(cellDate);
                    }
                }
                );
            }
        }

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_END);
        gridData.horizontalSpan = DAYS_IN_WEEK;
        gridData.heightHint = 1;
        separator = new Label(container, SWT.NO_FOCUS);
        separator.setLayoutData(gridData);
        separator.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
    }

    private void selectDate(Date theDate)
    {
        if (theDate == null) return;

        m_selectedDate = theDate;
        setReturnCode(OK);

        showDate(m_selectedDate);
        close();
    }

    private void createButtonBar(Composite parent)
    {
        Composite           container = new Composite(parent, SWT.NO_FOCUS);
        RowLayout           rowLayout;
        GridData            gridData;

        container.setBackground(m_defaultBackground);

        rowLayout = new RowLayout(SWT.HORIZONTAL);
        rowLayout.wrap = false;
        rowLayout.pack = true;
        rowLayout.spacing = 10;
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_CENTER);
        container.setLayoutData(gridData);
        container.setLayout(rowLayout);

        m_todayButton = new Button(container, SWT.CENTER);
        m_todayButton.setText(" Today ");
        m_todayButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                goToToday();
            }
        }
        );

        m_cancelButton = new Button(container, SWT.CENTER);
        m_cancelButton.setText(" Cancel ");
        m_cancelButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                setReturnCode(CANCEL);
                close();
            }
        }
        );
    }

/*
    private void paintCellDate(PaintEvent event)
    {
        Label           cellLabel = (Label) event.getSource();
        Date            cellDate = (Date) cellLabel.getData();

        if (cellDate == null) return;

        Date            todayDate = removeTime(new Date());

        if (!cellDate.equals(todayDate)) return;

        GC          gc = event.gc;
        Color       red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
        Rectangle   rect = cellLabel.getBounds();

        gc.setForeground(red);
        gc.drawRectangle (0, 0, cellLabel.getSize().x - 1, cellLabel.getSize().y - 1);
    }
*/
    public void showDate()
    {
        m_selectedDate = removeTime(new Date());

        showDate(m_selectedDate);
    }

    public void showDate(Date theDate)
    {
//      String              strMonthYear, strDayOfMonth;
        SimpleDateFormat    dateFormatter = new SimpleDateFormat("MMMM yyyy");
        Calendar            calendar = Calendar.getInstance();
        int                 i,j;
        int                 iSelectedMonth, iDayOfWeek;
        Color               gray = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
        Color               white = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);

        m_dateShown = (theDate == null ? new Date() : theDate);

        calendar.setTime(m_dateShown);

        calendar.clear(Calendar.HOUR);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        m_dateShown = calendar.getTime();

        iSelectedMonth = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);                             // set to the first of the month
        iDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_YEAR, Calendar.SUNDAY - iDayOfWeek);   // we need to start with Sunday

        m_monthLabel.setText(dateFormatter.format(m_dateShown));

        dateFormatter.applyPattern("d");

        for (i=0; i < m_dayLabels.length; i++)
        {
            for (j=0; j < m_dayLabels[i].length; j++)
            {
                Date        cellDate = calendar.getTime();
                int         iMonth = calendar.get(Calendar.MONTH);

                m_dayLabels[i][j].setData(cellDate);
                m_dayLabels[i][j].setText(dateFormatter.format(cellDate));

                if (iMonth == iSelectedMonth)
                    m_dayLabels[i][j].setForeground(m_defaultForeground);
                else                    
                    m_dayLabels[i][j].setForeground(gray);

                m_dayLabels[i][j].setBackground(white);

                if (cellDate.equals(m_selectedDate))
                {
                    m_dayLabels[i][j].setBackground(gray);
                    m_dayLabels[i][j].setForeground(white);
                }

                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
    }

    private void goToNextMonth()
    {
        addMonth(+1);
    }

    private void goToPrevMonth()
    {
        addMonth(-1);
    }

    private void goToToday()
    {
        selectDate(removeTime(new Date()));
    }

    private void addMonth(int iOffset)
    {
        Calendar        calendar = Calendar.getInstance();

        calendar.setTime(m_dateShown);
        calendar.add(Calendar.MONTH, iOffset);

        showDate(calendar.getTime());
    }

    protected Point getInitialLocation(Point initialSize)
    {
        Rectangle           screenDimensions = Display.getCurrent().getBounds();
        Point               location = new Point(0, 0);

        location.x = screenDimensions.width / 2 - initialSize.x / 2;
        location.y = screenDimensions.height / 2 - initialSize.y / 2;

        return (location);
    }
}
