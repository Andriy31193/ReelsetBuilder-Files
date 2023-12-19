package com.greentube.exercise.reelsetbuilder.impl;

import static org.junit.Assert.*;

import org.junit.Test;


public class SymbolPositionerImplTest {

    @Test
    public void start_validInput_valuesSetCorrectly() {
        SymbolPositionerImpl positioner = new SymbolPositionerImpl();
        char[] reelset = {'A', 'B', 'C'};
        int position = 1;
        int speed = 100;

        positioner.start(position, reelset, speed);

        assertEquals(position, positioner.getCurrentPosition());
        assertArrayEquals(reelset, positioner.getReelset());
        assertEquals(speed, positioner.getSpeed());
        assertTrue(positioner.getLastUpdateTime() > 0);
    }

    public void start_emptyReelset_throwsException() {
        SymbolPositionerImpl positioner = new SymbolPositionerImpl();
        char[] emptyReelset = {};

        try {
            positioner.start(0, emptyReelset, 100);
        } catch (IllegalStateException exception) {
            assertEquals("The reelset is empty. Please initialize it and try again.", exception.getMessage());
        }
    }

    @Test
    public void start_negativeSpeed_throwsException() {
        SymbolPositionerImpl positioner = new SymbolPositionerImpl();

        try {
            positioner.start(0, new char[]{'A', 'B'}, -50);
        } catch (IllegalStateException exception) {
            assertEquals("The speed cannot be less or equal 0.", exception.getMessage());
        }
    }

    @Test
    public void update_notStartedYet_returnsFalse() {
        SymbolPositionerImpl positioner = new SymbolPositionerImpl();

        assertFalse(positioner.update());
    }

    @Test
    public void update_symbolsMoving_returnsTrue() {
        SymbolPositionerImpl positioner = new SymbolPositionerImpl();
        char[] reelset = {'A', 'B', 'C'};
        int speed = 100;

        positioner.start(0, reelset, speed);
        assertTrue(positioner.update());
    }

    @Test
    public void getFirstSymbolPosition_zeroSpeed_returnsZero() {
        SymbolPositionerImpl positioner = new SymbolPositionerImpl();

        positioner.start(0, new char[]{'A', 'B', 'C'}, 0);
        assertEquals(0, positioner.getFirstSymbolPosition());
    }

    @Test
    public void getSymbol_validIndex_returnsCorrectSymbol() {
        SymbolPositionerImpl positioner = new SymbolPositionerImpl();
        char[] reelset = {'A', 'B', 'C'};

        positioner.start(0, reelset, 100);

        assertEquals('A', positioner.getSymbol(0));
        assertEquals('B', positioner.getSymbol(1));
        assertEquals('C', positioner.getSymbol(2));
    }

    @Test
    public void getSymbol_notStartedYet_returnsSpaceCharacter() {
        SymbolPositionerImpl positioner = new SymbolPositionerImpl();

        assertEquals(' ', positioner.getSymbol(0));
    }
}
