package pepse.util;

import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Handles the executing of code according to user text input
 * part of the bonus
 *
 * @author Tomer Goldberg
 */
public class WordToActionHandler {

    private final Map<String, Consumer<String>> wordToAction;
    private final GameTextInputGetter textInputGetter;
    private final HashSet<Integer> wordsLengthToFollow;

    /**
     * @param wordToAction    a map from the used entered string to the game action
     * @param textInputGetter a GameTextInputGetter used in order to read the user input
     */
    public WordToActionHandler(Map<String, Consumer<String>> wordToAction,
                               GameTextInputGetter textInputGetter) {

        this.wordToAction = wordToAction;
        this.textInputGetter = textInputGetter;
        wordsLengthToFollow = new HashSet<>();

        //loads the words' length to a hash set in order to know what to follow in
        //our textInputGetter.getUserWord(wordLength)
        for (var word : this.wordToAction.keySet()) {
            wordsLengthToFollow.add(word.length());
        }
    }

    /**
     * execute the 'update' function
     * listen to user text
     * for each word size get the user word
     * if the word has an action in map, execute the action and reset the 'words'
     *
     * @param deltaTime time passed since last called this function
     */
    public void runUpdate(float deltaTime) {
        textInputGetter.listenToUser(deltaTime);

        for (var wordLength : wordsLengthToFollow) {
            var word = textInputGetter.getUserWord(wordLength);
            if (word != null && wordToAction.containsKey(word)) {
                this.wordToAction.get(word).accept(word);
                this.textInputGetter.reset();
                break;
            }
        }
    }
}
