package com.uhills.finance.easetax.core;

/**
 * This class holds the properties for a JobCode.  A job code
 * allows a user to attach a specific job to a transaction.
 *
 * @author Marc Hamilton
 * @date   October 11, 2006
 *
 */

import com.uhills.util.validation.*;

public class JobCode extends EaseTaxObject
{
    // Serialization unique identifier.  This number may remain the same
    // so long as the changes to this object adhere to the use cases
    // outlined in the Java serialization specification.
    private static final long           serialVersionUID            = 6740722502372760734L;

    public static final JobCode         NULL_JOB_CODE               = new JobCode(0, "", "");
    public static final JobCode         ALL_JOB_CODES               = new JobCode(-1, "All Job Codes", "All Job Codes");
    public static final JobCode         NEW_JOB_CODE                = new JobCode(Integer.MAX_VALUE, "(Enter New Job Code...)", "(Enter New Job Code...)");

    public static final String          CAPTION_JOB_CODE            = "Job Code";

    public static final EntryField      FIELD_CODE                  = new EntryField(1, "Code", 150);
    public static final EntryField      FIELD_DESCRIPTION           = new EntryField(2, "Description", 255);

    public String                       code;
    public String                       description;

    public JobCode()
    {
    }

    public JobCode(long lId, String strCode, String strDescription)
    {
        initialize();

        setID(lId);
        code = strCode;
        description = strDescription;
    }

    /**
     * This method sets the id for this job code.
     * For this type of object, the id is
     * represented as a long integer.
     *
     * @param lId - the id for this job code
     */
    public void setID(long lId)
    {
        setOID(new Long(lId));
    }

    /**
     * Retrieves the id for this job code as a
     * long integer.
     *
     * @return the id for this company type
     */
    public long getID()
    {
        Long        longID = (Long) getOID();

        if (longID == null)
            return (0L);

        return (longID.longValue());
    }

    /**
     * Initializes the properties for this object.
     */
    public void initialize()
    {
        code                = "";
        description         = "";
    }

    /**
     * This method validates the options entered by the user.
     *
     */
    public void validate() throws ValidationException
    {
        getValidator().exists(code, FIELD_CODE.caption);
        getValidator().isValidNonWhitespace(code, FIELD_CODE.caption);
    }

    /**
     * Overridden from java.lang.Object, this method
     * compares the current object with the object
     * passed in to see if they are equal.  In this
     * implementation, we compare the ids.
     *
     * @param obj - the object to compare
     * @return true if the objects are equal
     */
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof JobCode)) return false;

        JobCode     comparison = (JobCode) obj;

        if ((comparison.getOID() == null || ((Long) comparison.getOID()).longValue() == 0) &&
            this.getOID() == null || ((Long) this.getOID()).longValue() == 0)
        {
            return (super.equals(obj));
        }

        return (comparison.getOID().equals(this.getOID()));
    }

    /**
     * Returns the String representation of this object.
     * In this case, the job code name.
     *
     * @return the String representation of this object
     */
    public String toString()
    {
        return (code == null ? "" : code);
    }
}
