/**
 * This class defines the dialog box used to edit
 * the user preferences.  Note that, this is different
 * than other dialogs in that it does not connect to
 * a business logic class submit and retrieve the
 * user preferences.
 * 
 * Since user preferences are not persisted in the
 * "ledger database", but rather in a properties file,
 * for brevity, the dialog simply interacts with the
 * ApplicationMain class to read and write user
 * preference information.
 * 
 * @author Marc Hamilton
 * @date   June 3, 2004
 * 
 */
package com.uhills.finance.easetax.ui.preferences;

import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.jface.viewers.*;

import com.uhills.util.validation.*;

import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.graphics.*;
//import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.core.*;

import com.uhills.finance.easetax.ui.transaction.*;


public final class PreferencesDialog extends TitleAreaDialog
{
/*
    private static final int        MAX_TEXT_WIDTH = 250;
    private static final int        MIN_LABEL_WIDTH = 90;
*/
    private static final int        MAX_COMBO_WIDTH = 120;

    private Label                   m_dateFormatLabel;
    private ComboViewer             m_dateFormatComboViewer;
    private Button                  m_confirmDeleteCheckBox;
    private Label                   m_transTypeLabel;
    private ComboViewer             m_transTypeComboViewer;
/*
    private Label                   m_defaultTransDateLabel;
    private Button                  m_transDateBlankRadioButton;
    private Button                  m_transDateTodayRadioButton;
    private Button                  m_transDatePrevRadioButton;
*/
    private Label                   m_newContactLabel;
    private Button                  m_newContactPromptRadioButton;
    private Button                  m_newContactAutoAddRadioButton;

    private UserPreferences         m_preferences;

    /**
     * The constructor retrieves the current cached
     * user preferences.
     *
     */
    public PreferencesDialog(Shell parentShell)
    {
        super(parentShell);

        setShellStyle(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);

        m_preferences = ApplicationMain.getUserPreferences();
    }

    /**
     * This method takes the user preferences and fills the
     * GUI objects with the associated values.
     *
     */
    private void fillFields()
    {
        setDateFormat(m_preferences.dateFormat);
        m_confirmDeleteCheckBox.setSelection(m_preferences.confirmOnDelete);
        setTransactionType(m_preferences.defaultTransType);
        setPromptOnNewContact(m_preferences.promptOnNewContact);
    }

    /**
     * This method takes the values from the GUI components and
     * populates a new instance of a UserPreferences object. 
     *
     */
    private UserPreferences getPreferencesFromForm()
    {
        UserPreferences         preferences = new UserPreferences(m_preferences.getFilePath());

        preferences.initialize();

        preferences.dateFormat = getDateFormat();
        preferences.confirmOnDelete = m_confirmDeleteCheckBox.getSelection();
        preferences.defaultTransType = getTransactionType();
        preferences.promptOnNewContact = getPromptOnNewContact();

        return (preferences);
    }

    /**
     * This method updates the "new contact prompt" radio
     * buttons to reflect the action that will be taken
     * when a new contact is entered into a transaction. 
     *
     */
    private void setPromptOnNewContact(boolean bPrompt)
    {
        m_newContactPromptRadioButton.setSelection(bPrompt);
        m_newContactAutoAddRadioButton.setSelection(!bPrompt);
    }

    /**
     * If true, the user will be prompted every time a new
     * contact is entered. 
     *
     */
    private boolean getPromptOnNewContact()
    {
        return (m_newContactPromptRadioButton.getSelection());
    }

    /**
     * Updates the user interface with the default transaction
     * type that the user prefers. 
     *
     */
    private void setTransactionType(TransactionType transType)
    {
        m_transTypeComboViewer.setSelection(new StructuredSelection(transType));
    }

    /**
     * Retrieves the the default transaction type that the user
     * has selected from the combo box.
     *
     */
    private TransactionType getTransactionType()
    {
        TransactionType         selectedTransType = null;
        IStructuredSelection    selection = (IStructuredSelection) m_transTypeComboViewer.getSelection();

        m_transTypeComboViewer.getSelection();

        if (selection != null)
            selectedTransType = (TransactionType) selection.getFirstElement();

        return (selectedTransType);
    }

    /**
     * Retrieves the the default transaction type that the user
     * has selected from the combo box.
     *
     */
    private void setDateFormat(String strDateFormat)
    {
        m_dateFormatComboViewer.setSelection(new StructuredSelection(strDateFormat));
    }

    private String getDateFormat()
    {
        String                  selectedDateFormat = null;
        IStructuredSelection    selection = (IStructuredSelection) m_dateFormatComboViewer.getSelection();

        m_dateFormatComboViewer.getSelection();

        if (selection != null)
            selectedDateFormat = (String) selection.getFirstElement();

        return (selectedDateFormat);
    }

    protected void configureShell(Shell shell)
    {
        shell.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
        shell.setText("User Preferences");
    }

    protected Control createDialogArea(Composite parent)
    {
//      String              strTitle, strMessage;
        Composite           dialogContainer = (Composite) super.createDialogArea(parent);
        Composite           container = new Composite(dialogContainer, SWT.NO_FOCUS);
        GridLayout          gridLayout = new GridLayout();

        gridLayout.marginHeight = 2;
        gridLayout.marginWidth = 8;
        gridLayout.horizontalSpacing = 5;
        container.setLayout(gridLayout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        createGeneralOptionsArea(container);
        createTransactionOptionsArea(container);

        setTitle("Edit User Preferences");
        setMessage("Use this dialog to modify your preferences for " + ApplicationMain.getAppName());

        fillFields();

        return (container);
    }

    private void createGeneralOptionsArea(Composite parent)
    {
        GridLayout          gridLayout;
        GridData            gridData;
        Group               container = new Group(parent, SWT.NO_FOCUS);

        gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;
        gridData = new GridData(GridData.FILL_BOTH);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        container.setText("General Options");

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gridData.horizontalSpan = 2;
        m_confirmDeleteCheckBox = new Button(container, SWT.CHECK);
        m_confirmDeleteCheckBox.setLayoutData(gridData);
        m_confirmDeleteCheckBox.setText("Display a confirmation before deleting items");

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        m_dateFormatLabel = new Label(container, SWT.LEFT);
        m_dateFormatLabel.setLayoutData(gridData);
        m_dateFormatLabel.setText("Show dates using the following format");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_COMBO_WIDTH;
        m_dateFormatComboViewer = new ComboViewer(container, SWT.DROP_DOWN | SWT.READ_ONLY);
        m_dateFormatComboViewer.getCombo().setLayoutData(gridData);
        m_dateFormatComboViewer.setContentProvider(new DateFormatContentProvider());
        m_dateFormatComboViewer.setLabelProvider(new DateFormatLabelProvider());
        m_dateFormatComboViewer.setInput(new Integer(0));
    }

    private void createTransactionOptionsArea(Composite parent)
    {
        GridLayout          gridLayout;
        GridData            gridData;
        Group               container = new Group(parent, SWT.NO_FOCUS);
        Composite           radioButtonComposite;
        RowLayout           rowLayout;

        gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;
        gridData = new GridData(GridData.FILL_BOTH);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        container.setText("Transaction Options");

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        m_transTypeLabel = new Label(container, SWT.LEFT);
        m_transTypeLabel.setLayoutData(gridData);
        m_transTypeLabel.setText("New transactions should default to");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_COMBO_WIDTH;
        m_transTypeComboViewer = new ComboViewer(container, SWT.DROP_DOWN | SWT.READ_ONLY);
        m_transTypeComboViewer.getCombo().setLayoutData(gridData);
        m_transTypeComboViewer.setContentProvider(new TransactionTypeContentProvider());
        m_transTypeComboViewer.setInput(new Boolean(false));

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gridData.horizontalSpan = 2;
        m_newContactLabel = new Label(container, SWT.LEFT);
        m_newContactLabel.setLayoutData(gridData);
        m_newContactLabel.setText("When I enter an unrecognized contact into a new transaction...");

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        radioButtonComposite = new Composite(container, SWT.NO_FOCUS);
        rowLayout = new RowLayout(SWT.VERTICAL);
        rowLayout.wrap = false;
        rowLayout.marginLeft = 30;
        radioButtonComposite.setLayout(rowLayout);
        radioButtonComposite.setLayoutData(gridData);

        m_newContactPromptRadioButton = new Button(radioButtonComposite, SWT.RADIO);
        m_newContactPromptRadioButton.setText("Ask me what to do");

        m_newContactAutoAddRadioButton = new Button(radioButtonComposite, SWT.RADIO);
        m_newContactAutoAddRadioButton.setText("Automatically add it to the contact list");

        m_newContactPromptRadioButton.setSelection(true);
    }

    protected void okPressed()
    {
        try
        {
            m_preferences = getPreferencesFromForm();
            m_preferences.validate();

            ApplicationMain.setUserPreferences(m_preferences);

            super.okPressed();
        }
        catch (ValidationException ex)
        {
            MessageDialog.openError(getShell(), "Invalid Entry", ex.getMessage());
        }
    }
}
