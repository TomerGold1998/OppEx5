package pepse.util;

import danogl.gui.UserInputListener;

import java.util.Map;

/**
 * Listen to the user text input and creates words from it.
 * (used as part of the bonus)
 *
 * @author Tomer Goldberg
 */
public class GameTextInputGetter {

    private final Map<Integer, Character> keyEventToChar;
    private final UserInputListener listener;
    private final int maxWordLength;

    private String output = "";
    private float sumDeltaTime = 0;
    private float lastUserInputDelta = 0;

    //At least 0.14 seconds different between a Char to a new Char
    private final static float MIN_LETTER_BUFFER = 0.14f;

    /**
     * Creates new GameTextInputGetter object
     *
     * @param wordsToListenTo a map between a the int value of a KeyEvent and the matching Char
     * @param listener        user input listener object
     * @param maxWordLength   max word length to store at each moment
     */
    public GameTextInputGetter(Map<Integer, Character> wordsToListenTo,
                               UserInputListener listener,
                               int maxWordLength) {

        this.keyEventToChar = wordsToListenTo;
        this.listener = listener;
        this.maxWordLength = maxWordLength;
    }

    /**
     * listen to the user key strokes of our selected keys
     * updates the user string by the key pressed
     *
     * @param deltaTime time passed in seconds since last update
     */
    public void listenToUser(float deltaTime) {
        sumDeltaTime += deltaTime;
        //only allow to enter a letter every BUFFER of seconds
        if (sumDeltaTime > lastUserInputDelta + MIN_LETTER_BUFFER) {
            for (var keyEvent : keyEventToChar.keySet()) {
                if (listener.isKeyPressed(keyEvent)) {
                    addWordToOutput(keyEventToChar.get(keyEvent));
                    lastUserInputDelta = sumDeltaTime;
                }
            }
        }
    }

    /**
     * get the user inputted word of X length
     *
     * @param wordLength the requested word length to read from the user
     * @return the user entered word, null if the user has not entered enough letters yet
     */
    public String getUserWord(int wordLength) {
        if (this.output.length() < wordLength)
            return null;
        return this.output.substring(this.output.length() - wordLength);
    }

    /**
     * resets the current String output
     */
    public void reset() {
        this.output = "";
    }

    /**
     * adds a character the to output String, if the current word length is at max size,
     * its removes the first char entered, and adds the new char to the last spot
     *
     * @param character a char to be added to the word string
     */
    private void addWordToOutput(Character character) {
        if (this.output.length() == this.maxWordLength) {
            var subOutput = this.output.substring(1);
            this.output = subOutput + character;
        } else
            this.output += character;
    }


}
