package com.randomapps.battlefield.game;

import java.io.Serializable;
import java.util.Date;

public class SavedGame<T extends Serializable> implements Serializable {

    private Date dateSaved;

    private T game;

    private String description;

    public Date getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(Date dateSaved) {
        this.dateSaved = dateSaved;
    }

    public T getGame() {
        return game;
    }

    public void setGame(T game) {
        this.game = game;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
