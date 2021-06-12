package game.entity.guessingGame;

import game.entity.BaseEntity;

import java.util.Locale;
import java.util.UUID;

/**
 * Rappresenta le entità indovinello presenti nel file guessingGames.json.
 */
public class GuessingGame extends BaseEntity
{
    private String text;
    private String afterAnswered;
    private String correctAnswer;

    public GuessingGame (UUID id, String name, String text, String correctAnswer, String afterAnswered)
    {
        super(id, name);
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.afterAnswered = afterAnswered;
    }

    /**
     *
     * @return il testo dell'indovinello.
     */
    public String getText()
    {
        return text;
    }

    /**
     *
     * @return frase da stampare dopo aver risposto correttamente alla domanda.
     */
    public String getAfterAnswered()
    {
        return afterAnswered;
    }

    /**
     * Verifica che la stringa passata come parametro
     * sia uguale alla risposta corretta.
     * @param answer risposta
     * @return true se le due stringhe coincidono.
     */
    public boolean giveAnswer(String answer)
    {
        boolean isRight = false;

        if (answer.toLowerCase(Locale.ROOT).equals(correctAnswer.toLowerCase(Locale.ROOT)))
        {
            isRight = true;
        }

        return isRight;
    }
}
