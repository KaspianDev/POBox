package com.github.kaspiandev.pobox.command;

public enum SubCommands {

    SEND("send", "pobox.command.send");

    private final String key;
    private final String permission;

    SubCommands(String key, String permission) {
        this.key = key;
        this.permission = permission;
    }

    public String getKey() {
        return key;
    }

    public String getPermission() {
        return permission;
    }

}
