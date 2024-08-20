package com.github.kaspiandev.pobox.exception;

public class PluginLoadFailureException extends Exception {

    public PluginLoadFailureException(String message) {
        super(message);
    }

    public PluginLoadFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}
