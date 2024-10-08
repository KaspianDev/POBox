package com.github.kaspiandev.pobox.config;

public enum Message {

    COMMAND_NO_PERMISSIONS("command.no-permissions"),
    COMMAND_NO_ARGUMENTS("command.no-arguments"),
    COMMAND_INVALID_SUBCOMMAND("command.invalid-subcommand"),
    COMMAND_ONLY_PLAYERS("command.only-players"),

    MAIL_SENT("mail.sent"),
    MAIL_NO_PLAYER("mail.no-player"),
    MAIL_NO_ITEM("mail.no-item"),
    MAIL_NO_NAME("mail.no-name"),
    MAIL_NO_TYPE("mail.no-type"),
    MAIL_NO_COMMAND("mail.no-command"),
    MAIL_NO_ANONYMOUS("mail.no-anonymous");

    private final String path;

    Message(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
