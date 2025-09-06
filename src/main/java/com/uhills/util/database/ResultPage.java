package com.uhills.util.database;

/**
 * This class provides general all-purpose methods for actions
 * that might be performed against a database.
 *
 * @author Marc Hamilton
 * @date   August 1, 2001
 */

import java.util.Vector;
import java.util.Map;

public class ResultPage
{
    public String               pageId;

    public Vector               resultsList;
    public long                 startItem;
    public long                 totalRecordCount;

    public int                  pageNumber;
    public int                  pageCount;
    public int                  pageSize;

    public String               sortColumn;
    public boolean              sortDescending;

    public Map                  filter;

    public ResultPage()
    {
        initialize();
    }

    public void initialize()
    {
        pageId          = "";

        resultsList     = new Vector();
        startItem       = 0;

        pageNumber      = 0;
        pageCount       = 0;
        pageSize        = 0;

        sortColumn      = "";
        sortDescending  = false;
    }

    public long getEndItem()
    {
        long    lEndItem = (resultsList == null ? startItem : (startItem-1) + resultsList.size());

        if (lEndItem < 0) lEndItem = 0;

        return (lEndItem);
    }

    public int getItemCount()
    {
        return (resultsList == null ? 0 : resultsList.size());
    }

    /**
     * Performs the "page" properties such as counts
     * and index numbers.
     * We have to add one to max rows in case the total
     * record count equals the max rows
     *
     * @param iMaxRows - maximum number of rows per page
     */
    public void computeProperties(int iMaxRows)
    {
        pageSize    = iMaxRows;
        pageCount   = (int) (totalRecordCount / iMaxRows) + 1;
        pageNumber  = (pageNumber <= 0 ? 1 : pageNumber);
        pageNumber  = (pageNumber > pageCount ? pageCount : pageNumber);
        startItem   = ((pageNumber - 1) * pageSize) + 1;
    }
}