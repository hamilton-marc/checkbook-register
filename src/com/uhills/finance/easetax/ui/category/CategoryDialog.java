/*
 * Created on Jun 21, 2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.uhills.finance.easetax.ui.category;

import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

import com.uhills.util.exception.*;
import com.uhills.util.validation.*;

import com.uhills.finance.easetax.core.*;
import com.uhills.finance.easetax.graphics.*;
import com.uhills.finance.easetax.ui.common.*;
import com.uhills.finance.easetax.biz.*;

/**
 * @author HamiltonM
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class CategoryDialog extends TitleAreaDialog
{
    private static final int        MAX_TEXT_WIDTH = 250;
    private static final int        MIN_LABEL_WIDTH = 90;

    private Label           m_categoryNumberLabel;
    private Text            m_categoryNumberText;
    private Label           m_categoryNameLabel;
    private Text            m_categoryNameText;
    private Label           m_categoryDescriptionLabel;
    private Text            m_categoryDescriptionText;
    private Button          m_revenueRadioButton;
    private Button          m_contributionRadioButton;
    private Button          m_expenseRadioButton;
    private Button          m_withdrawlRadioButton;
    private Label           m_taxFormLabelHeader;
    private Label           m_taxLineLabelHeader;
    private Label[]         m_taxFormLabel = new Label[TaxCode.TAX_FORMS.length];
    private Text[]          m_taxLineText = new Text[TaxCode.TAX_FORMS.length];

    private CategoryBusinessLogicBean       m_categoryBean;
    private Category                        m_category;
    private boolean                         m_bFillFields;

    public CategoryDialog(Shell parentShell)
    {
        super(parentShell);

        setShellStyle(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
        m_categoryBean = new CategoryBusinessLogicBean();
    }

    public int newItem()
    {
        return (open());
    }

    public int openItem(Object entry)
    {
        if (entry == null) return (CANCEL);

        Category     category = (Category) entry;

        return (openItem(category.getID()));
    }

    public int openItem(long lCategoryId)
    {
        m_bFillFields = true;

        try
        {
            getCategory(lCategoryId);
        }
        catch (PersistenceException ex)
        {
            ex.printStackTrace(System.err);
            return (CANCEL);
        }
        catch (ItemNotFoundException ex)
        {
            MessageDialog.openError(getParentShell(), "Account Entry", ex.getMessage());
            return (CANCEL);
        }

        return (open());
    }

    public Category getCategory()
    {
        return (m_category);
    }

    private void getCategory(long lCategoryId) throws PersistenceException, ItemNotFoundException
    {
        m_category = m_categoryBean.getCategory(lCategoryId);

        if (m_category == null)
        {
            throw new ItemNotFoundException("Unable to find that account in the database.  " +
                                            "It may have already been deleted");
        }
    }

    private void fillFields()
    {
        m_categoryNumberText.setText(String.valueOf(m_category.number));
        m_categoryNameText.setText(m_category.name);
        m_categoryDescriptionText.setText(m_category.description);
        setTransactionType(m_category.getType());
        setTaxLineItems(m_category);

        // We can't let the user change the account number
        // in case it is in use.
        m_categoryNumberText.setEditable(false);
    }

    private Category getCategoryFromForm() throws ValidationException
    {
        Category         category = new Category();

        category.initialize();

        if (m_category != null)
        {
            category.setID(m_category.getID());
        }

        category.number = getCategoryNumber();
        category.name = m_categoryNameText.getText();
        category.description = m_categoryDescriptionText.getText();
        category.setType(getTransactionType());
        getTaxLineItems(category);

        return (category);
    }

    private void getTaxLineItems(Category category)
    {
        for (int i=0; i < TaxCode.TAX_FORMS.length; i++)
        {
            TaxCode     taxCode = new TaxCode(TaxCode.TAX_FORMS[i], m_taxLineText[i].getText());

            category.setTaxCode(taxCode);
        }
    }

    private void setTaxLineItems(Category category)
    {
        for (int i=0; i < TaxCode.TAX_FORMS.length; i++)
        {
            TaxCode     taxCode = category.getTaxCode(TaxCode.TAX_FORMS[i]);

            if (taxCode != null)
                m_taxLineText[i].setText(taxCode.line);
        }
    }

    private int getCategoryNumber() throws ValidationException
    {
        try
        {
            return (Integer.parseInt(m_categoryNumberText.getText()));
        }
        catch (NumberFormatException e)
        {
            throw new ValidationException("The " + Category.FIELD_NUMBER.caption + " you entered is either missing or invalid");
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
    }

    protected void configureShell(Shell shell)
    {
        shell.setImage(ImagePool.getInstance().getImageRegistry().get(ImagePool.ICON_MAIN));
        shell.setText("Account Entry");
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

        createCategoryNameArea(container);
        createCategoryTypeArea(container);
        createTaxInformationArea(container);

        if (m_bFillFields)
        {
            strTitle = "Edit Account";
            strMessage = "You can modify the account in the fields below";

            fillFields();
        }
        else
        {
            strTitle = "New Account";
            strMessage = "Enter the information for the new account in the fields below";
        }

        setTitle(strTitle);
        setMessage(strMessage);

        return (container);
    }

    private void createCategoryNameArea(Composite parent)
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

        container.setText("Account Information");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_categoryNumberLabel = new Label(container, SWT.LEFT);
        m_categoryNumberLabel.setLayoutData(gridData);
        m_categoryNumberLabel.setText(Category.FIELD_NUMBER.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = (int) (MAX_TEXT_WIDTH * 0.4);
        m_categoryNumberText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_categoryNumberText.setLayoutData(gridData);
        m_categoryNumberText.setTextLimit(Category.FIELD_NAME.maxLength);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_categoryNameLabel = new Label(container, SWT.LEFT);
        m_categoryNameLabel.setLayoutData(gridData);
        m_categoryNameLabel.setText(Category.FIELD_NAME.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_categoryNameText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_categoryNameText.setLayoutData(gridData);
        m_categoryNameText.setTextLimit(Category.FIELD_NAME.maxLength);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_categoryDescriptionLabel = new Label(container, SWT.LEFT);
        m_categoryDescriptionLabel.setLayoutData(gridData);
        m_categoryDescriptionLabel.setText(Category.FIELD_DESCRIPTION.caption);

        gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MAX_TEXT_WIDTH;
        m_categoryDescriptionText = new Text(container, SWT.LEFT | SWT.BORDER);
        m_categoryDescriptionText.setLayoutData(gridData);
        m_categoryDescriptionText.setTextLimit(Category.FIELD_DESCRIPTION.maxLength);
    }

    private void createTaxInformationArea(Composite parent)
    {
        final Label         underlineLabel;
        GridLayout          gridLayout;
        GridData            gridData;
        Group               container = new Group(parent, SWT.NO_FOCUS);
        int                 i;

        gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;
        gridData = new GridData(GridData.FILL_BOTH);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        container.setText("Tax Information");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.widthHint = MIN_LABEL_WIDTH;
        m_taxFormLabelHeader = new Label(container, SWT.LEFT);
        m_taxFormLabelHeader.setLayoutData(gridData);
        m_taxFormLabelHeader.setText(Category.FIELD_TAX_FORM.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_taxLineLabelHeader = new Label(container, SWT.LEFT);
        m_taxLineLabelHeader.setLayoutData(gridData);
        m_taxLineLabelHeader.setText(Category.FIELD_TAX_LINE.caption);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        gridData.horizontalSpan = 2;
        gridData.heightHint = 5;
        underlineLabel = new Label(container, SWT.LEFT);
        underlineLabel.setLayoutData(gridData);
        underlineLabel.setText("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");

        for (i=0; i < TaxCode.TAX_FORMS.length; i++)
        {
            gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
            m_taxFormLabel[i] = new Label(container, SWT.LEFT);
            m_taxFormLabel[i].setLayoutData(gridData);
            m_taxFormLabel[i].setText(TaxCode.TAX_FORMS[i]);
    
            gridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
            gridData.widthHint = (int) (MAX_TEXT_WIDTH * 0.4);
            m_taxLineText[i] = new Text(container, SWT.LEFT | SWT.BORDER);
            m_taxLineText[i].setLayoutData(gridData);
            m_taxLineText[i].setTextLimit(Category.FIELD_TAX_LINE.maxLength);
        }
    }

    private void createCategoryTypeArea(Composite parent)
    {
        GridLayout          gridLayout;
        GridData            gridData;
        Group               container = new Group(parent, SWT.NO_FOCUS);

        gridLayout = new GridLayout(4, false);
        gridLayout.marginHeight = 10;
        gridLayout.marginWidth = 10;
        gridLayout.horizontalSpacing = 10;
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        container.setLayoutData(gridData);
        container.setLayout(gridLayout);

        container.setText("Account Type");

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_expenseRadioButton = new Button(container, SWT.RADIO);
        m_expenseRadioButton.setLayoutData(gridData);
        m_expenseRadioButton.setText(Transaction.CAPTION_EXPENSE);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_withdrawlRadioButton = new Button(container, SWT.RADIO);
        m_withdrawlRadioButton.setLayoutData(gridData);
        m_withdrawlRadioButton.setText(Transaction.CAPTION_WITHDRAWL);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_revenueRadioButton = new Button(container, SWT.RADIO);
        m_revenueRadioButton.setLayoutData(gridData);
        m_revenueRadioButton.setText(Transaction.CAPTION_REVENUE);

        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_CENTER);
        m_contributionRadioButton = new Button(container, SWT.RADIO);
        m_contributionRadioButton.setLayoutData(gridData);
        m_contributionRadioButton.setText(Transaction.CAPTION_CONTRIBUTION);
    }

    protected void okPressed()
    {
        try
        {
            m_category = getCategoryFromForm();
            m_categoryBean.saveCategory(m_category);

            super.okPressed();
        }
        catch (ValidationException ex)
        {
            MessageDialog.openError(getShell(), "Invalid Entry", ex.getMessage());
        }
        catch (PersistenceException ex)
        {
            MessageDialog.openError(getShell(), "Unexpected Error", ex.getMessage());
        }
    }
}
