package com.github.kaspiandev.pobox.data;

import com.github.kaspiandev.pobox.POBox;

public abstract class Table {

    protected final POBox plugin;
    protected final String tableStatement;

    protected Table(POBox plugin, String tableStatement) {
        this.plugin = plugin;
        this.tableStatement = tableStatement;
    }

    public String getTableStatement() {
        return tableStatement;
    }

}
