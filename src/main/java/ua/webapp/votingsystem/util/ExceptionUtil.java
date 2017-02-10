package ua.webapp.votingsystem.util;


import ua.webapp.votingsystem.exception.NotFoundException;

public class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFound(object != null, "id = " + id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id = " + id);
    }

    public static <T> T checkNotFound(T object, String message) {
        checkNotFound(object != null, message);
        return object;
    }

    public static void checkNotFound(boolean found, String message) {
        if (!found) throw new NotFoundException("Not found entity with " + message);
    }
}
