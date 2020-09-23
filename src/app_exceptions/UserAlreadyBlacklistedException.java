package app_exceptions;

public class UserAlreadyBlacklistedException extends Exception {

    public UserAlreadyBlacklistedException(String s)
    {
        super(s);
    }
}
