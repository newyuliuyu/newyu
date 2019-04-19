package com.newyu.utils.io.file.spi;

/**
 * ClassName: SheetNameException <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 18-3-9 下午1:02 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class GetSheetNameException extends RuntimeException {
    public GetSheetNameException() {
        super();
    }

    public GetSheetNameException(String message) {
        super(message);
    }

    public GetSheetNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetSheetNameException(Throwable cause) {
        super(cause);
    }


    protected GetSheetNameException(String message, Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
