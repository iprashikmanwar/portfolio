package com.praman.tambolastats;

class dataModel {
    String name, remaining;
    String cnt;

    public dataModel(String name, String remaining, String cnt) {
        this.name = name;
        this.remaining = remaining;
        this.cnt = cnt;
    }

    public String getName() {
        return name;
    }

    public String getRemaining() {
        return remaining;
    }

    public String getCnt() { return cnt; }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

}
