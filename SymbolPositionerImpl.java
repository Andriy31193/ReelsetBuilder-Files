package com.greentube.exercise.reelsetbuilder.impl;

import com.greentube.exercise.reelsetbuilder.Main;
import com.greentube.exercise.reelsetbuilder.ReelView;
import com.greentube.exercise.reelsetbuilder.SymbolPositioner;

/**
 * The methods of this class should be implemented to animate the symbols of the reels.
 * <br>
 * In the end, it should look like this animated gif (note, that the gif might be displayed with a wrong speed):
 * <br><br>
 * <img src="doc-files/reelanim.gif">
 * <br><br>
 * Each symbol of the reel is identified by a character. Depending on the character, the symbol automatically gets it's color.
 * The order of the symbols is called a "reelset". It's a char array that contains all symbols of this reel.
 * <br><br>
 * Example:<br>
 * Let's say, we have the following reelset: AGHHBX
 * <br>
 * When we start the reel at position 0, this means, symbols A, G and H should be visible. Then, the symbols move down, which
 * reveals symbol X at the top. Now, symbols X, A, G and H are visible while X and H are visible only partially. When the reel
 * moves on, you can see B, X, A and G, and so on. So, in most cases, 4 symbols are visible.
 * <br><br>
 * For this example, the whole gui implementation was done already. You only have to implement the methods of this class. You shouldn't
 * edit the other classes. Of course, you can edit the {@link Main} class to play with different reelsets, start positions and
 * speeds.
 * <br>
 * When starting the {@link Main} class, the {@link #update()}, {@link #getFirstSymbolPosition()} and {@link #getSymbol(int)} methods
 * are called on a regular basis. This happens about every 20 milliseconds, but you shouldn't take that for granted and shouldn't base
 * your animation on this interval.
 * <br>
 * As long as {@link #start(int, char[], int)} wasn't called, nothing should happen. You can return space as a character. This will
 * display empty black rectangles.
 * <br>
 * When the user presses the Start button, the {@link #start(int, char[], int)} method of this class gets called. You get a position
 * (index on the reelset) where the reel should start, the reelset and a speed (in milliseconds per symbol). After the call of
 * {@link #start(int, char[], int)}, you should do your calculations in the {@link #update()} method and return the updated values
 * in the {@link #getFirstSymbolPosition()} and {@link #getSymbol(int)} methods. Also, check the javadoc of these methods.
 * <br>
 * You can get the height of the symbol by calling {@link ReelView#SYMBOL_SIZE}.
 * <br><br><br>
 * Tasks:
 * <ul>
 * <li> Symbols of the start reelset should move down in the correct speed after start was called. </li>
 * <li> Your code has to be production ready. </li>
 * </ul>
 * 
 * <br>
 * If you have any questions, feel free to contact hannes.witzmann@greentube.com.
 * <br>
 */

// Code by Andrii Skyba
public class SymbolPositionerImpl implements SymbolPositioner {

    // Current position of the symbol in the reel
    private int currentPosition;

    // Array representing the symbols in the reel
    private char[] reelset;

    // Speed at which the symbols move
    private int speed;

    // Time stamp of the last update
    private long lastUpdateTime;
    
    private boolean initialized = false;

    ///
    /// This method initializes the symbol positioner using the given values.
    /// It throws an IllegalStateException if the reelset is empty, the speed is less than 0, or the position is less than 0.
    
    public void start(int position, char[] reelset, int speed) {
        
    	validateParameters(position, reelset, speed);
    	
        // Set initial values and update the last update time stamp
        this.currentPosition = position;
        this.reelset = reelset;
        this.speed = speed;
        this.lastUpdateTime = System.currentTimeMillis();
        
        this.initialized = true;
    }

    /// This method updates the symbol position by considering elapsed time and speed.
    /// It returns true if the symbols are in motion and false if the movement has not started yet.

    public boolean update() {
        if (reelset == null)
        {
        	printError("The reelset cannot be null.");
        	
            return false; // Not started yet
        }
            
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastUpdateTime;

        if (elapsedTime >= speed) {
            // Move to the previous symbol (opposite direction)
            currentPosition = (currentPosition - 1 + reelset.length) % reelset.length;
            lastUpdateTime = currentTime;
        }

        return true; // Symbols are moving
    }

    /// This method calculates and returns the position of the first symbol based on elapsed time and speed.

    public int getFirstSymbolPosition() {
        if (speed <= 0)	
        {
        	printError("The speed cannot be less or equal 0. Value: " + speed);
            
        	return 0;
        }
            
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastUpdateTime;

        // Calculate the fractional part of the position
        double fractionalPosition = (double) elapsedTime / speed;

        // Calculate the total position, including the fractional part
        int totalPosition = (int) (fractionalPosition * ReelView.SYMBOL_SIZE);

        // Subtract one symbol size to get one symbol less
        return totalPosition - ReelView.SYMBOL_SIZE;
    }

    /// If the reelset is not initialized, it returns a space character.


    public char getSymbol(int index) {
        if (reelset == null)
        {
        	printError("The reelset cannot be null.");
        	
        	return ' ';
}
            
        int position = (currentPosition + index) % reelset.length;
        return reelset[position];
    }
    
    private void validateParameters(int position, char[] reelset, int speed) {
        if (reelset == null || reelset.length == 0) {
            System.err.println("The reelset is empty. Please initialize it and try again. Value: " + speed);
            throw new IllegalStateException("The reelset is empty. Please initialize it and try again. Value: " + speed);
        }

        if (speed < 0) {
            System.err.println("The speed cannot be less or equal 0. Value: " + speed);
            throw new IllegalStateException("The speed cannot be less or equal 0. Value: " + speed);
        }

        if (position < 0) {
            System.err.println("The position cannot be less than 0. Value: " + position);
            throw new IllegalStateException("The position cannot be less than 0. Value: " + position);
        }
    }
    private void printError(String text)
    {
    	if(initialized)
    		System.err.println(text);
    }
    
    
    
    /// Getters for JUnitTests
    public int getCurrentPosition()
    {
    	return this.currentPosition;
    }
    public char[] getReelset()
    {
    	return this.reelset;
    }
    public int getSpeed()
    {
    	return this.speed;
    }
    public long getLastUpdateTime()
    {
    	return this.lastUpdateTime;
    }
}