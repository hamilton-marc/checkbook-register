/*
 * Created on Jun 28, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.common;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;

/**
 * @author hamiltonm
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ApplicationHeaderBar extends Composite
{
    private Label           m_headerLabel;
    private Label           m_headerImage;

    public ApplicationHeaderBar(Composite parent)
    {
        super(parent, SWT.NO_FOCUS);

        initialize();
    }

    private void initialize()
    {
        GridData            gridData;
        Font                headerFont = new Font(getDisplay(), "Verdana", 11, SWT.BOLD);
        GridLayout          gridLayout = new GridLayout();

        setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
        setLayout(gridLayout);
        gridLayout.horizontalSpacing = 4;
        gridLayout.verticalSpacing = 0;
        gridLayout.marginHeight = 2;
        gridLayout.numColumns = 2;
    
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.horizontalIndent = 0;
    
        m_headerImage = new Label(this, SWT.LEFT);
        m_headerImage.setLayoutData(gridData);
        m_headerImage.setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
    
        m_headerLabel = new Label(this, SWT.LEFT);
        m_headerLabel.setLayoutData(gridData);
        m_headerLabel.setFont(headerFont);
        m_headerLabel.setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
        m_headerLabel.setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
    }

    public void setText(String strNewValue)
    {
        m_headerLabel.setText(strNewValue);
    }
    
    public String getText()
    {
        return (m_headerLabel.getText());
    }

    public void setImage(Image newImage)
    {
        m_headerImage.setImage(newImage);
    }

    public Image getImage()
    {
        return (m_headerImage.getImage());
    }
}
