package ru.BeYkeRYkt.LightSource.gui.editor;

import ru.BeYkeRYkt.LightSource.items.LightItem;

public class PlayerEditor extends Editor {

    private LightItem changeItem;

    public PlayerEditor(String name, LightItem item) {
        super(name);
        this.changeItem = item;
    }

    public LightItem getItem() {
        return changeItem;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof PlayerEditor)) {
            return false;
        } else {
            PlayerEditor editor = (PlayerEditor) other;
            return getBukkitPlayer().getName().equals(editor.getBukkitPlayer().getName());
        }
    }
}