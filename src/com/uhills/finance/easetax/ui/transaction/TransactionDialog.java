package com.uhills.finance.easetax.ui.transaction;

/**
 * This class is the user interface dialog for entering and
 * editing transactions.
 *
 * @author Marc Hamilton
 * @date   June 21, 2003
 *
 */

import java.util.*;
import java.text.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.events.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.dialogs.*;

import com.uhills.util.exception.*;
import com.uhills.util.validation.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.main.*;
import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.ui.contact.*;
import com.uhills.finance.easetax.ui.calendar.*;
import com.uhills.finance.easetax.ui.category.*;
import com.uhills.finance.easetax.ui.jobcode.*;
import com.uhills.finance.easetax.biz.*;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class TransactionDialog extends TitleAreaDialog implements IDataEntryDialog
{
    public static final int                     DESCRIPTION_FIELD_WIDTH = 260;
    public static final int                     NUMBER_FIELD_WIDTH = 80;

    private Button                              m_revenueRadioButton;
    private Button                              m_contributionRadioButton;
    private Button                              m_expenseRadioButton;
    private Button                              m_withdrawlRadioButton;
//  private Label                               m_transTypeHelpLabel;
    private Label                               m_companyLabel;
    private Label                               m_contactLabel;
    private ComboViewer                         m_contactComboViewer;
    private Label                               m_categoryLabel;
    private ComboViewer                         m_categoryComboViewer;
    private Label                               m_memoLabel;
    private Text                                m_memoText;
    private Label                               m_numberCodeLabel;
    private ComboViewer                         m_numberCodeComboViewer;
    private Label                               m_dateLabel;
    private DateCombo                           m_dateCombo;
    private Label                               m_amountLabel;
    private Text                                m_amountText;
    private Label                               m_jobCodeLabel;
    private ComboViewer                         m_jobCodeComboViewer;
    private boolean                             m_bIsAutoCompleting;
//  private boolean                             m_bTextModified;
    private boolean                             m_bSkipAutoComplete;

    private Transaction                         m_transaction;
    private boolean                             m_bFillFields;
    private boolean                             m_bEditMode;
    private TransactionBusinessLogicBean        m_transactionBean;

    private Category                            m_currentlySelectedCategory;
    private JobCode                             m_currentlySelectedJobCode;

    public TransactionDialog(Shell parentShell)
    {
        super(parentShell);

        setShellStyle(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);

        m_transactionBean = new TransactionBusinessLogicBean();
/*
        m_contactBean = new ContactBusinessLogicBean();
        m_categoryBean = new CategoryBusinessLogicBean();
*/
    }

    public int newItem()
    {
        return (open());
    }

    /**
     * Clones an existing transaction.
     *
     * @param entry
     * @return result code
     */
    public int newItem(Object entry)
    {
        if (entry == null) return (CANCEL);

        m_bFillFields = true;
        m_transaction = (Transaction) entry;

        return (open());
    }

    public int openItem(Object entry)
    {
        if (entry == null) return (CANCEL);

        Transaction     transaction = (Transaction) entry;

        return (openItem(transaction.getID()));
    }

    public int openItem(long lTransactionId)
    {
        m_bFillFields = true;
        m_bEditMode = true;

        try
        {
            getTransaction(lTransactionId);
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
            return (CANCEL);
        }
        catch (ItemNotFoundException ex)
        {
            MessageDialog.openError(getParentShell(), "Transaction Entry", ex.getMessage());
            return (CANCEL);
        }

        return (open());
    }

    private void getTransaction(long lTransactionId) throws PersistenceException, ItemNotFoundException
    {
        m_transaction = m_transactionBean.getTransaction(lTransactionId);

        if (m_transaction == null)
        {
            throw new ItemNotFoundException("Unable to find that transaction in the database.  " +                                            "It may have already been deleted");
        }
    }

    private void fillFields()
    {
        m_bSkipAutoComplete = true;    // while we're filling the data entry fields,
                                       // make sure we don't try to do an auto-complete
        setTransactionType(m_transaction.getType());
        m_numberCodeComboViewer.getCombo().setText(m_transaction.numberCode);
        m_dateCombo.setDate(m_transaction.transactionDate);
        m_amountText.setText(m_transaction.formatAmount());
        setContact(m_transaction.getContact());
        setCategory(m_transaction.getCategory());
        setJobCode(m_transaction.getJobCode());
        m_memoText.setText(m_transaction.description);

        m_currentlySelectedCategory = m_transaction.getCategory();
    }

    private Transaction getTransactionFromForm() throws ValidationException
    {
        Transaction     transaction = new Transaction();

        transaction.initialize();

        if (m_transaction != null)
            transaction.setID(m_transaction.getID());

        transaction.setType(getTransactionType());
        transaction.numberCode = m_numberCodeComboViewer.getCombo().getText().trim();
        transaction.transactionDate = getTransactionDate();
        transaction.amount = getTransactionAmount();
        transaction.setContact(getContact());
        transaction.setCategory(getCategory());
        transaction.setJobCode(getJobCode());
        transaction.description = m_memoText.getText().trim();

        return (transaction);
    }

    private boolean searchContactCombo()
    {
        String              strContactEntry = m_contactComboViewer.getCombo().getText().trim();
        String[]            contactList = m_contactComboViewer.getCombo().getItems();
        boolean             bFound = false;

        if (strContactEntry.length() == 0 ||
            contactList == null ||
            contactList.length == 0)
        {
            return (bFound);
        }

        for (int i=0; i < contactList.length && !bFound; i++)
        {
            String          strContactInList = contactList[i].trim();

            if (strContactInList.length() == 0)
                continue;

            if ((bFound = strContactEntry.equalsIgnoreCase(strContactInList)))
            {
                m_contactComboViewer.getCombo().select(i);
            }
        }

        return (bFound);
    }

    private Contact getContact()
    {
        Contact                 selectedContact = null;
        IStructuredSelection    selection = (IStructuredSelection) m_contactComboViewer.getSelection();

        if (selection != null)
            selectedContact = (Contact) selection.getFirstElement();

        // Unfortunately if the user manually types in an entry
        // that exists in the list, the selection won't be found.
        // We need to call the searchContactCombo() to try one
        // more time to find the selection.

        if (selectedContact == null &&
            searchContactCombo())
        {
            selection = (IStructuredSelection) m_contactComboViewer.getSelection();

            if (selection != null)
                selectedContact = (Contact) selection.getFirstElement();
        }

        if (selectedContact == null)
        {
            selectedContact = new Contact();

            selectedContact.initialize();
            selectedContact.name = m_contactComboViewer.getCombo().getText();
        }

        return (selectedContact);
    }

    private void setContact(Contact contact)
    {
        m_contactComboViewer.setSelection(new StructuredSelection(contact));
    }

    private Category getCategory()
    {
        Category                selectedCategory = null;
        IStructuredSelection    selection = (IStructuredSelection) m_categoryComboViewer.getSelection();

        m_categoryComboViewer.getSelection();

        if (selection != null)
            selectedCategory = (Category) selection.getFirstElement();

        return (selectedCategory);
    }

    private void setCategory(Category category)
    {
        m_categoryComboViewer.setSelection(new StructuredSelection(category));
    }

    private JobCode getJobCode()
    {
        JobCode                 selectedJobCode = null;
        IStructuredSelection    selection = (IStructuredSelection) m_jobCodeComboViewer.getSelection();

        m_jobCodeComboViewer.getSelection();

        if (selection != null)
            selectedJobCode = (JobCode) selection.getFirstElement();

        return (selectedJobCode);
    }

    private void setJobCode(JobCode jobCode)
    {
        m_jobCodeComboViewer.setSelection(new StructuredSelection(jobCode));
    }

    private Date getTransactionDate() throws ValidationException
    {
        Date            transactionDate = null;

        for (int i=0; i < UserPreferences.DATE_FORMAT_ARRAY.length && transactionDate == null; i++)
        {
            DateFormat      dateFormat =  new SimpleDateFormat(UserPreferences.DATE_FORMAT_ARRAY[i]);

            try
            {
                transactionDate = dateFormat.parse(m_dateCombo.getText().trim());
//              transactionDate = m_dateCombo.getDate();
            }
            catch (ParseException ex)
            {
                // Keep trying until all the date formats are exhausted
                transactionDate = null;
            }
        }

        if (transactionDate == null)
            throw new ValidationException("The " + Transaction.FIELD_DATE.caption + " you entered is either missing or invalid");

        return (transactionDate);
    }

    private double getTransactionAmount() throws ValidationException
    {
        try
        {
            return (new DecimalFormat().parse(m_amountText.getText()).doubleValue());
        }
        catch (ParseException ex)
        {
            throw new ValidationException("The " + Transaction.FIELD_AMOUNT.caption + " you entered is either missing or invalid");
        }
    }

    private TransactionType getTransactionType()
    {
        TransactionType     transactionType = null;

        if (m_revenueRadioButton.getSelection())
            transactionType = TransactionType.REVENUE;
        else if (m_contributionRadioButton.getSelection())
            transactionType = TransactionType.CONTRIBUTION;
        else if (m_expenseRadioButton.getSelection())
            transactionType = TransactionType.EXPENSE;
        else if (m_withdrawlRadioButton.getSelection())
            transactionType = TransactionType.WITHDRAWL;

        return (transactionType);
    }

    private void setTransactionType(TransactionType transactionType)
    {
        if (transactionType.equals(TransactionType.REVENUE))
            m_revenueRadioButton.setSelection(true);
        else if (transactionType.equals(TransactionType.CONTRIBUTION))
            m_contributionRadioButton.setSelection(true);
        else if (transactionType.equals(TransactionType.EXPENSE))
            m_expenseRadioButton.setSelection(true);
        else if (transactionType.equals(TransactionType.WITHDRAWL))
            m_withdrawlRadioButton.setSelection(true);

        toggleTransactionType();
    }

    /**
     * When a user selects a reference type, we need to trap that
     * and fill in the combo field with the "type" property only.
     *
     * This method fills in the field with the type property from
     * the selected Reference Type.
     */
    private void fillReferenceType()
    {
        ReferenceType           selectedReferenceType = null;
        IStructuredSelection    selection = (IStructuredSelection) m_numberCodeComboViewer.getSelection();

        if (selection == null) return;
            
        selectedReferenceType = (ReferenceType) selection.getFirstElement();
        m_numberCodeComboViewer.getCombo().setText(selectedReferenceType.type);
    }

    /**
     * When a user selects a category, we need to see if they've
     * selected the new category entry.  If so, we pop up a dialog
     * to allow them to enter the new job code.
     *
     */
    private void handleCategorySelection()
    {
        Category                selectedCategory = null;
        IStructuredSelection    selection = (IStructuredSelection) m_categoryComboViewer.getSelection();

        if (selection == null) return;

        selectedCategory = (Category) selection.getFirstElement();

        if (selectedCategory.equals(Category.NEW_CATEGORY))
        {
            CategoryDialog      categoryDialog = new CategoryDialog(getShell());
            int                 iRetCode = categoryDialog.newItem();

            if (iRetCode == Window.OK)
            {
                m_categoryComboViewer.refresh();
                setCategory(categoryDialog.getCategory());
            }
            else if (m_currentlySelectedCategory != null)
            {
                setCategory(m_currentlySelectedCategory);
            }
            else
            {
                m_categoryComboViewer.getCombo().deselectAll();
            }
        }

        m_currentlySelectedCategory = getCategory(); 
    }

    /**
     * When a user selects a job code, we need to see if they've
     * selected the new job code entry.  If so, we pop up a dialog
     * to allow them to enter the new job code.
     *
     */
    private void handleJobCodeSelection()
    {
        JobCode                 selectedJobCode = null;
        IStructuredSelection    selection = (IStructuredSelection) m_jobCodeComboViewer.getSelection();

        if (selection == null) return;

        selectedJobCode = (JobCode) selection.getFirstElement();

        if (selectedJobCode.equals(JobCode.NEW_JOB_CODE))
        {
            JobCodeDialog       jobCodeDialog = new JobCodeDialog(getShell());
            int                 iRetCode = jobCodeDialog.newItem();

            if (iRetCode == Window.OK)
            {
                m_jobCodeComboViewer.refresh();
                setJobCode(jobCodeDialog.getJobCode());
            }
            else if (m_currentlySelectedJobCode != null)
            {
                setJobCode(m_currentlySelectedJobCode);
            }
            else
            {
                m_jobCodeComboViewer.getCombo().deselectAll();
            }
        }

        m_currentlySelectedJobCode = getJobCode(); 
    }

/*
    private void setDefaults()
    {
    }
*/

    protected void configureShell(Shell shell)
    {
        shell.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
        shell.setText("Transaction Entry");
    }

    protected Control createDialogArea(Composite parent)
    {
        String              strTitle, strMessage;
        Composite           dialogContainer = (Composite) super.createDialogArea(parent);
        Composite           container = new Composite(dialogContainer, SWT.NO_FOCUS);
        GridLayout          gridLayout = new GridLayout();

        gridLayout.marginHeight = 2;
        gridLayout.marginWidth = 8;
        gridLayout.horizontalSpacing = 5;
        container.setLayout(gridLayout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        createTransactionTypeArea(container);
        createTransactionDetailsArea(container);
        configureEvents();

        if (m_bFillFields)
        {
            fillFields();
        }
        else
        {
            setTransactionType(ApplicationMain.getUserPreferences().defaultTransType);
        }

        if (m_bEditMode)
        {
            strTitle = "Edit Transaction";
            strMessage = "You can modify your transaction in the fields below";
        }
        else
        {
            m_transaction = null;  // make sure the internal variable is null in
                                   // case we are cloning an existing transaction

            strTitle = "New Transaction";
            strMessage = "Enter the information for your transaction in the fields below";
        }

        setTitle(strTitle);
        setMessage(strMessage);

        return (container);
    }

    private void createTransactionDetailsArea(Composite parent)
    {
        GridLayout          gridLayout;
        GridData            gridData;
        Group               transDetailGroup = new Group(parent, SWT.NO_FOCUS);
        Label               spacerLabel;

        gridLayout = new GridLayout(5, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;
        gridData = new GridData(GridData.FILL_BOTH);
        transDetailGroup.setLayoutData(gridData);
        transDetailGroup.setLayout(gridLayout);

        transDetailGroup.setText("Transaction Details");
/*
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING);
        gridData.horizontalSpan = 2;
        gridData.verticalSpan = 1;
        m_companyLabel = new Label(transDetailGroup, SWT.LEFT);
        m_companyLabel.setLayoutData(gridData);
*/
        gridData = new GridData();
        gridData.widthHint = 5;
        gridData.verticalSpan = 5;
        spacerLabel = new Label(transDetailGroup, SWT.NO_FOCUS);
        spacerLabel.setLayoutData(gridData);

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        m_contactLabel = new Label(transDetailGroup, SWT.LEFT);
        m_contactLabel.setLayoutData(gridData);
        m_contactLabel.setText(Transaction.FIELD_CONTACT.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = DESCRIPTION_FIELD_WIDTH;
        m_contactComboViewer = new ComboViewer(transDetailGroup, SWT.DROP_DOWN);
        m_contactComboViewer.getCombo().setLayoutData(gridData);
        m_contactComboViewer.getCombo().setTextLimit(Transaction.FIELD_CONTACT.maxLength);
        m_contactComboViewer.setContentProvider(new ContactComboContentProvider());
        m_contactComboViewer.setInput(new Integer(0));

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        m_numberCodeLabel = new Label(transDetailGroup, SWT.LEFT);
        m_numberCodeLabel.setLayoutData(gridData);
        m_numberCodeLabel.setText(Transaction.FIELD_NUMBER_CODE.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = NUMBER_FIELD_WIDTH;
        m_numberCodeComboViewer = new ComboViewer(transDetailGroup, SWT.DROP_DOWN);
        m_numberCodeComboViewer.getCombo().setLayoutData(gridData);
        m_numberCodeComboViewer.getCombo().setTextLimit(Transaction.FIELD_NUMBER_CODE.maxLength);
        m_numberCodeComboViewer.setContentProvider(new ReferenceTypeContentProvider());
        m_numberCodeComboViewer.setInput(new Integer(0));

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_categoryLabel = new Label(transDetailGroup, SWT.LEFT);
        m_categoryLabel.setLayoutData(gridData);
        m_categoryLabel.setText(Transaction.FIELD_CATEGORY.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
        m_categoryComboViewer = new ComboViewer(transDetailGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
        m_categoryComboViewer.getCombo().setLayoutData(gridData);
        m_categoryComboViewer.setContentProvider(new CategoryContentProvider(false, true));
        m_categoryComboViewer.setLabelProvider(new CategoryLabelProvider());
        m_categoryComboViewer.setInput(new Boolean(false));

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        m_dateLabel = new Label(transDetailGroup, SWT.LEFT);
        m_dateLabel.setLayoutData(gridData);
        m_dateLabel.setText(Transaction.FIELD_DATE.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
        m_dateCombo = new DateCombo(transDetailGroup, SWT.LEFT | SWT.BORDER);
        m_dateCombo.setLayoutData(gridData);
        m_dateCombo.setPattern(ApplicationMain.getUserPreferences().dateFormat);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_jobCodeLabel = new Label(transDetailGroup, SWT.LEFT);
        m_jobCodeLabel.setLayoutData(gridData);
        m_jobCodeLabel.setText(Transaction.FIELD_JOB_CODE.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
        m_jobCodeComboViewer = new ComboViewer(transDetailGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
        m_jobCodeComboViewer.getCombo().setLayoutData(gridData);
        m_jobCodeComboViewer.setContentProvider(new JobCodeContentProvider(false, true));
        m_jobCodeComboViewer.setLabelProvider(new JobCodeLabelProvider());
        m_jobCodeComboViewer.setInput(new Boolean(false));

        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        m_amountLabel = new Label(transDetailGroup, SWT.LEFT);
        m_amountLabel.setLayoutData(gridData);
        m_amountLabel.setText(Transaction.FIELD_AMOUNT.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER);
        m_amountText = new Text(transDetailGroup, SWT.LEFT | SWT.BORDER);
        m_amountText.setLayoutData(gridData);
        m_amountText.setTextLimit(Transaction.FIELD_AMOUNT.maxLength);
/*
        gridData = new GridData();
        gridData.horizontalSpan = 2;
        spacerLabel = new Label(transDetailGroup, SWT.NO_FOCUS);
        spacerLabel.setLayoutData(gridData);
*/
        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        m_memoLabel = new Label(transDetailGroup, SWT.LEFT);
        m_memoLabel.setLayoutData(gridData);
        m_memoLabel.setText(Transaction.FIELD_DESCRIPTION.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_CENTER | GridData.GRAB_HORIZONTAL);
        m_memoText = new Text(transDetailGroup, SWT.LEFT | SWT.BORDER);
        m_memoText.setLayoutData(gridData);
        m_memoText.setTextLimit(Transaction.FIELD_DESCRIPTION.maxLength);
    }

    private void createTransactionTypeArea(Composite parent)
    {
        GridLayout          gridLayout;
        GridData            gridData;
        Group               transTypeGroup = new Group(parent, SWT.NO_FOCUS);

        gridLayout = new GridLayout(4, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        transTypeGroup.setLayoutData(gridData);
        transTypeGroup.setLayout(gridLayout);

        transTypeGroup.setText(TransactionType.CAPTION_TYPE);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_expenseRadioButton = new Button(transTypeGroup, SWT.RADIO);
        m_expenseRadioButton.setLayoutData(gridData);
        m_expenseRadioButton.setText(TransactionType.EXPENSE.toString());

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_withdrawlRadioButton = new Button(transTypeGroup, SWT.RADIO);
        m_withdrawlRadioButton.setLayoutData(gridData);
        m_withdrawlRadioButton.setText(TransactionType.WITHDRAWL.toString());

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_revenueRadioButton = new Button(transTypeGroup, SWT.RADIO);
        m_revenueRadioButton.setLayoutData(gridData);
        m_revenueRadioButton.setText(TransactionType.REVENUE.toString());

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_contributionRadioButton = new Button(transTypeGroup, SWT.RADIO);
        m_contributionRadioButton.setLayoutData(gridData);
        m_contributionRadioButton.setText(TransactionType.CONTRIBUTION.toString());
    }

    private void configureEvents()
    {
        m_revenueRadioButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                toggleTransactionType();
            }
        }
        );

        m_contributionRadioButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                toggleTransactionType();
            }
        }
        );

        m_expenseRadioButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                toggleTransactionType();
            }
        }
        );

        m_withdrawlRadioButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                toggleTransactionType();
            }
        }
        );

        // When a user selects a reference type, we need to trap that
        // and fill in the combo field with the "type" property only
        m_numberCodeComboViewer.getCombo().addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                fillReferenceType();
            }
        }
        );

        // When a user selects a reference type, we need to trap that
        // and fill in the combo field with the "type" property only
        m_categoryComboViewer.getCombo().addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                handleCategorySelection();
            }
        }
        );

        // When a user selects a reference type, we need to trap that
        // and fill in the combo field with the "type" property only
        m_jobCodeComboViewer.getCombo().addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                handleJobCodeSelection();
            }
        }
        );

        m_contactComboViewer.getCombo().addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
//              System.out.println("keyPressed()");
                m_bSkipAutoComplete = (e.character == SWT.DEL ||
                                       e.character == SWT.BS);
            }
        }
        );

        m_contactComboViewer.getCombo().addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
//              System.out.println("modifyText()");
                if (m_bIsAutoCompleting || m_bSkipAutoComplete) return;

                autoComplete((Combo) e.widget);
                m_bSkipAutoComplete = false;
            }
        }
        );
    }

    private void autoComplete(Combo combo)
    {
        String      strText = combo.getText();
        String[]    items = combo.getItems();
        String      strFoundText = null;

        m_bIsAutoCompleting = true;

        if (strText == null || strText.trim().length() == 0)
            return;

        for (int i = 0; i < items.length; i++)
        {
            if (items[i].toUpperCase().startsWith(strText.toUpperCase()))
            {
                strFoundText = items[i];
                break;
            }
        }

        if (strFoundText != null)
        {
            combo.setText(strFoundText);
            combo.setSelection(new Point(strText.length(), strFoundText.length()));
        }

        m_bIsAutoCompleting = false;
    }

    private void toggleTransactionType()
    {
        if (m_expenseRadioButton.getSelection() ||
            m_withdrawlRadioButton.getSelection())
        {
            m_contactLabel.setText(Transaction.CAPTION_PAYEE);
        }
        else
        {
            m_contactLabel.setText(Transaction.CAPTION_FROM);
        }
    }

    protected void okPressed()
    {
        Transaction             transaction = null;
        TransactionSaveRules    tranSaveRules = new TransactionSaveRules();
        String                  strQuestion;
        boolean                 bContinueSave = true;

        while (bContinueSave)
        {
            try
            {
                transaction = getTransactionFromForm();

                m_transactionBean.saveTransaction(transaction, tranSaveRules);
                m_transaction = transaction;

                bContinueSave = false;  // if we passed the exception tests, we're done

                super.okPressed();
            }
            catch (ValidationException ex)
            {
                MessageDialog.openError(getShell(), "Invalid Entry", ex.getMessage());
                bContinueSave = false;
            }
            catch (PersistenceException ex)
            {
                MessageDialog.openError(getShell(), "Unexpected Error", ex.getMessage());
                bContinueSave = false;
            }
            catch (TransactionTypeMismatchException ex)
            {
                strQuestion = "WARNING: " + ex.getMessage() + "\n\nAre you sure you wish to continue saving this transaction?";

                bContinueSave = MessageDialog.openQuestion(getShell(), "Transaction Type Mismatch", strQuestion);

                tranSaveRules.setAccountOk(bContinueSave);
            }
            catch (FutureTransactionDateException ex)
            {
                strQuestion = "WARNING: " + ex.getMessage() + "\n\nAre you sure you wish to continue saving this transaction?";

                bContinueSave = MessageDialog.openQuestion(getShell(), "Transaction Date Warning", strQuestion);

                tranSaveRules.setDateOk(bContinueSave);
            }
            catch (NewContactException ex)
            {
                strQuestion = ex.getMessage() + "\n\nWould you like to save this new contact?";

                boolean     bSaveContact = MessageDialog.openQuestion(getShell(), "New Contact", strQuestion);

                tranSaveRules.setSaveContact(bSaveContact);
                bContinueSave = true;
            }
        }
    }
}
