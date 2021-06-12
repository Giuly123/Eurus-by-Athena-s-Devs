package game.player.status;

import game.entity.guessingGame.GuessingGame;

public enum AnswerStatus
{
    noQuestions,
    alreadySolved,
    notSolved,
    definitelyAlreadyResolved,
    solved;

    private GuessingGame guessingGame;

    public AnswerStatus getMajor(AnswerStatus otherState)
    {
        return otherState.ordinal() > this.ordinal() ? otherState : this;
    }

    public GuessingGame getGuessingGame()
    {
        return guessingGame;
    }

    public void setGuessingGame(GuessingGame guessingGame)
    {
        this.guessingGame = guessingGame;
    }
}