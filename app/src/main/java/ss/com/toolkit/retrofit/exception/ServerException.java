package ss.com.toolkit.retrofit.exception;

public class ServerException extends RuntimeException {

    public ServerException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }
    public ServerException(String detailMessage) {
        super(detailMessage);
    }
    /**
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message;
        switch (code) {
            case ErrorCode.SERVER_INTERNAL_ERROR:
                message = ""+ErrorCode.SERVER_INTERNAL_ERROR;
                break;
            case ErrorCode.SERVER_RESOURCE_ERROR:
                message = ""+ErrorCode.SERVER_RESOURCE_ERROR;
                break;
            case ErrorCode.USER_OPERATION_ERROR:
                message = ""+ErrorCode.USER_OPERATION_ERROR;
                break;
            case ErrorCode.NO_USER:
                message = ""+ErrorCode.NO_USER;
                break;
            case ErrorCode.USER_NO_PERMISSION:
                message = ""+ErrorCode.USER_NO_PERMISSION;
                break;
            case ErrorCode.USER_DEVICE_NO_PERMISSION:
                message = ""+ErrorCode.USER_DEVICE_NO_PERMISSION;
                break;
            case ErrorCode.LOGIN_ERROR:
                message = ""+ErrorCode.LOGIN_ERROR;
                break;
            case ErrorCode.REQUEST_PARAM_ERROR:
                message = ""+ErrorCode.REQUEST_PARAM_ERROR;
                break;
            default:
                message = ""+ErrorCode.SERVER_UNKNOWN_ERROR;

        }
        return message;
    }
}
