/*
 * Created on Jun 30, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.common;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.internal.win32.LOGFONT;

import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.ui.main.*;
import com.uhills.finance.easetax.main.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NavigationCommandBar extends Composite
{
    private Button                  m_headerButton;
//  private Label                   m_headerLabel;
    private Label[]                 m_functionImage = new Label[ApplicationController.NUM_FUNCTIONS];
    private Label[]                 m_functionLabel = new Label[ApplicationController.NUM_FUNCTIONS];

    private MainWindow              m_mainForm;

    public NavigationCommandBar(Composite parent, int iStyle, MainWindow mainForm)
    {
        super(parent, iStyle);

        m_mainForm = mainForm;
        initialize();
    }

    public void initialize()
    {
        GridData            gridData;
//      Font                headerFont = new Font(getParent().getDisplay(), "Verdana", 11, SWT.BOLD);
        GridLayout          gridLayout = new GridLayout();
        int                 i;

        setLayout(gridLayout);
        gridLayout.horizontalSpacing = 7;
        gridLayout.verticalSpacing = 7;
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        gridLayout.numColumns = 2;

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
        gridData.horizontalSpan = 2;
        gridData.heightHint = 20;

        m_headerButton = new Button(this, SWT.CENTER | SWT.NO_FOCUS);
        m_headerButton.setLayoutData(gridData);
        m_headerButton.setText(ApplicationMain.getAppName() + " Links");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        gridData.horizontalSpan = 2;
        gridData.heightHint = 0;
        final Label spacerLabel = new Label(this, SWT.CENTER | SWT.NO_FOCUS);
        spacerLabel.setLayoutData(gridData);

        for (i = 0; i < ApplicationController.NUM_FUNCTIONS; i++)
        {
            Image               image;
            String              strCaption;

            image = null;
            strCaption = null;

            switch (i)
            {
                case ApplicationController.FUNCTION_HOME:
                    strCaption = "Home";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_HOME);
                    break;
/*
                case ApplicationController.FUNCTION_LEDGERS:
                    strCaption = "Ledgers";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_ACCOUNTS);
                    break;
*/
                case ApplicationController.FUNCTION_TRANSACTIONS:
                    strCaption = "Transactions";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_TRANSACTIONS);
                    break;
/*
                case ApplicationController.FUNCTION_CALENDAR:
                    strCaption = "Calendar";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_CALENDAR);
                    break;
*/
                case ApplicationController.FUNCTION_REPORTS:
                    strCaption = "Reports";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_REPORTS);
                    break;

                case ApplicationController.FUNCTION_CONTACTS:
                    strCaption = "Contacts";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_CONTACTS);
                    break;

                case ApplicationController.FUNCTION_CATEGORIES:
                    strCaption = "Accounts";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_CATEGORIES);
                    break;

                case ApplicationController.FUNCTION_JOB_CODES:
                    strCaption = "Job Codes";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_JOB_CODES);
                    break;
/*
                case ApplicationController.FUNCTION_TASKS:
                    strCaption = "Tasks";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_TASKS);
                    break;
*/
                case ApplicationController.FUNCTION_PREFERENCES:
                    strCaption = "Preferences";
                    image = ImagePool.getInstance().getImageRegistry().get(ImagePool.IMAGE_PREFERENCES);
                    break;
            }

            if (strCaption != null && image != null)
            {
                m_functionImage[i] = new Label(this, SWT.LEFT);
                m_functionLabel[i] = new Label(this, SWT.LEFT);

                gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
                gridData.horizontalIndent = 6;
                m_functionImage[i].setImage(image);
                m_functionImage[i].setLayoutData(gridData);
                m_functionImage[i].addMouseListener(new FunctionLabelMouseAdapter(i));
                m_functionImage[i].addMouseTrackListener(new FunctionMouseTrackAdapter(m_functionLabel[i]));

                gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
                m_functionLabel[i].setText(strCaption);
                m_functionLabel[i].setLayoutData(gridData);
                m_functionLabel[i].addMouseListener(new FunctionLabelMouseAdapter(i));
                m_functionLabel[i].addMouseTrackListener(new FunctionMouseTrackAdapter(m_functionLabel[i]));
            }
        }
    }

    /**
     * This inner class is used to track mouse movement over
     * the function labels.  When we enter the Label, we want
     * the Color to change to Blue and the Font to be underlined.
     *
     * Unfortunately, the solution for underlining the text
     * for the function labels is platform specific and will
     * only work for Windows.
     */
    public class FunctionMouseTrackAdapter extends MouseTrackAdapter
    {
        private Label       m_targetLabel;

        public FunctionMouseTrackAdapter(Label targetLabel)
        {
            super();
            m_targetLabel = targetLabel;
        }

        public void mouseEnter(MouseEvent e)
        {
            FontData    fontData = m_targetLabel.getFont().getFontData()[0];
            LOGFONT     logFont = fontData.data;

            logFont.lfUnderline = 0x4;  //Set the byte to display the underline

            m_targetLabel.setFont(new Font(getDisplay(), fontData));
            m_targetLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
        }

        public void mouseExit(MouseEvent e)
        {
            FontData    fontData = m_targetLabel.getFont().getFontData()[0];
            LOGFONT     logFont = fontData.data;

            logFont.lfUnderline = 0x0;  //Unset the byte to display the underline

            m_targetLabel.setFont(new Font(getDisplay(), fontData));
            m_targetLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
        }
    }

    /**
     * This inner class is used to track mouse clicks over
     * the function labels.  When the user clicks on a
     * function label, we want them to switch to that function
     * just like they would with a toolbar click.
     *
     */
    public class FunctionLabelMouseAdapter extends MouseAdapter
    {
        int         m_iFunction;

        public FunctionLabelMouseAdapter(int iFunction)
        {
            super();
            m_iFunction = iFunction;
        }

        public void mouseDown(MouseEvent e)
        {
            m_mainForm.getApplicationController().switchFunction(m_iFunction);
        }

    }
}
