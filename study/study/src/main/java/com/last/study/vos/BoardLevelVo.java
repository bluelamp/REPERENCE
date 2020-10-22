package com.last.study.vos;

public class BoardLevelVo {
    private final int readLevel;
    private final int writeLevel;

    public BoardLevelVo(int readLevel, int writeLevel) {
        this.readLevel = readLevel;
        this.writeLevel = writeLevel;
    }

    public int getReadLevel() {
        return readLevel;
    }

    public int getWriteLevel() {
        return writeLevel;
    }
}
