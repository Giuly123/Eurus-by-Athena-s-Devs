package game.entity.guessingGame;

import java.util.Locale;

public class GuessingGame
{
    private String text;
    private String afterAnswered;
    private String correctAnswer;

    // questa risposta non verra' salvata, quindi sara' necessario reinserirla a ogni run
    public boolean isResolved = false;

    public GuessingGame (String text, String correctAnswer, String afterAnswered)
    {
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
            isResolved = true;
            isRight = true;
        }

        return isRight;
    }

}
