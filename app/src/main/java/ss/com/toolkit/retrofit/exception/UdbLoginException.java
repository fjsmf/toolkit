package ss.com.toolkit.retrofit.exception;

public class UdbLoginException extends RuntimeException {
    private int exceptionCode;
    private String errorMsg;

    public UdbLoginException(int exceptionCode, String errorMsg) {
        this.exceptionCode = exceptionCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
//        String msg="errorCode:"+exceptionCode+","+errorMsg;
//        if (exceptionCode == 101) {
//            msg=String.format(MyApplication.getInstance().getString(R.string.login_phone_format_error));
//        } else if (exceptionCode == 1021) {
//            msg=String.format(MyApplication.getInstance().getString(R.string.login_code_invalidation));
//        } else if (exceptionCode == 210009) {
//            msg=String.format(MyApplication.getInstance().getString(R.string.login_code_invalidation_error));
//        }
        return errorMsg;
    }
}
