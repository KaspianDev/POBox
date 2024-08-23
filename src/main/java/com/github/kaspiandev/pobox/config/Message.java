package com.github.kaspiandev.pobox.config;

public enum Message {

    COMMAND_NO_PERMISSIONS("command.no-permissions"),
    COMMAND_NO_ARGUMENTS("command.no-arguments"),
    COMMAND_INVALID_SUBCOMMAND("command.invalid-subcommand"),
    COMMAND_ONLY_PLAYERS("command.only-players"),

    MAIL_SENT("mail.sent");

    private final String path;

    Message(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
