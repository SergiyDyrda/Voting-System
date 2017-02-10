package ua.webapp.votingsystem.exception;



public class TooLateToVoteException extends RuntimeException {

    public TooLateToVoteException(String message) {
        super(message);
    }
}
