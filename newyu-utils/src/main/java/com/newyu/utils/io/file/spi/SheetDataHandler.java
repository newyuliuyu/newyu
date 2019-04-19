package com.newyu.utils.io.file.spi;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: SheetNameHandler <br/>
 * Function: <br/>
 * Reason:  <br/>
 * date: 18-3-8 下午6:25 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class SheetDataHandler extends AbstractSheetDataHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private SetSheetData setSheetData;

    public SheetDataHandler(SharedStringsTable sst, SetSheetData setSheetData) {
        this(sst, setSheetData, 2);
    }

    public SheetDataHandler(SharedStringsTable sst, SetSheetData setSheetData, int rowIdx) {
        super(sst, rowIdx);
        this.setSheetData = setSheetData;
    }

    @Override
    protected boolean isContinue() {
        return curRowIdx >= rowIdx;
    }

    @Override
    protected void processEndRow() {
        setSheetData.set(data.toArray(new Excel2007Cell[0]));
    }
}
