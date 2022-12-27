package com.uhills.finance.easetax.biz;

/**
 * This class contains boolean properties to allow the
 * Transaction user interface to communicate certain
 * business rule flags to the business logic layer.
 * 
 * @author Marc Hamilton
 * @date   August 1, 2003
 * 
 */
public class TransactionSaveRules
{
    // use Boolean instead of boolean because we
    // actually want 3 states: true, false, or null

    private Boolean         m_boolSaveContact;
    private Boolean         m_boolCategoryOk;
    private Boolean         m_boolDateOk;

    public TransactionSaveRules()
    {
    }
/*
    public TransactionSaveRules(boolean bNewValue)
    {
        markAll(bNewValue);
    }

    public void markAll(boolean bNewValue)
    {
        m_boolSaveContact = new Boolean(bNewValue);
        m_boolAccountOk = new Boolean(bNewValue);
        m_boolDateOk = new Boolean(bNewValue);
    }
*/
    /**
     * @return flag indicating that the category selection is ok despite any warnings
     */
    public Boolean getCategoryOk()
    {
        return (m_boolCategoryOk);
    }

    /**
     * @return flag indicating that the date entered is ok despite any warnings
     */
    public Boolean getDateOk()
    {
        return (m_boolDateOk);
    }

    /**
     * @return flag telling the business logic to save the new contact entered
     */
    public Boolean getSaveContact()
    {
        return (m_boolSaveContact);
    }

    /**
     * This setter method is used to set the flag indicating whether or not
     * the category selection is ok despite any warnings.
     * 
     * @param boolNewValue
     */
    public void setCategoryOk(Boolean boolNewValue)
    {
        m_boolCategoryOk = boolNewValue;
    }

    /**
     * This setter method is used to set the flag indicating whether or not
     * the category selection is ok despite any warnings.
     * 
     * @param bNewValue
     */
    public void setAccountOk(boolean bNewValue)
    {
        m_boolCategoryOk = new Boolean(bNewValue);
    }

    /**
     * This setter method is used to set the flag indicating whether or not
     * the date entered is ok despite any warnings.
     * 
     * @param boolNewValue
     */
    public void setDateOk(Boolean boolNewValue)
    {
        m_boolDateOk = boolNewValue;
    }

    /**
     * This setter method is used to set the flag indicating whether or not
     * the date entered is ok despite any warnings.
     * 
     * @param bNewValue
     */
    public void setDateOk(boolean bNewValue)
    {
        m_boolDateOk = new Boolean(bNewValue);
    }

    /**
     * This setter method is used to set the flag indicating whether or not
     * to save the new contact.
     * 
     * @param boolNewValue
     */
    public void setSaveContact(Boolean boolNewValue)
    {
        m_boolSaveContact = boolNewValue;
    }

    /**
     * This setter method is used to set the flag indicating whether or not
     * to save the new contact.
     * 
     * @param bNewValue
     */
    public void setSaveContact(boolean bNewValue)
    {
        m_boolSaveContact = new Boolean(bNewValue);
    }
}
