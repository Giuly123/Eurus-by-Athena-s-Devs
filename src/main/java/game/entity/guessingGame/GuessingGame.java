package game.entity.guessingGame;

import game.entity.BaseEntity;

import java.util.Locale;
import java.util.UUID;

public class GuessingGame extends BaseEntity
{
    private String text;
    private String afterAnswered;
    private String correctAnswer;

    // questa risposta non verra' salvata, quindi sara' necessario reinserirla a ogni run

    public GuessingGame (UUID id, String name, String text, String correctAnswer, String afterAnswered)
    {
        super(id, name);
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.afterAnswered = afterAnswered;
    }

    public String getText()
    {
        return text;
    }

    public String getAfterAnswered()
    {
        return afterAnswered;
    }

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
