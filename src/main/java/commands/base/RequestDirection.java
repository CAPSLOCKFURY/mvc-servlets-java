package commands.base;

/**
 * Direction of request, {@link RequestDirection#FORWARD} is for forwarding request, {@link RequestDirection#REDIRECT} is for redirect,
 * {@link RequestDirection#VOID} is for requests that do not forward or redirect
 */
public enum RequestDirection {
    FORWARD, REDIRECT, VOID
}
