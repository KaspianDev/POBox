package com.github.kaspiandev.pobox.gui;

import java.util.List;

public record GuiContext(String[] mask, String title, List<ItemContext> items) {
}
