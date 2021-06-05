package game.player.status;

import game.entity.guessingGame.GuessingGame;

public enum AnswerStatus
{
    noQuestions,
    alreadySolved,
    notSolved,
    definitelyAlreadyResolved,
    solved;

    public GuessingGame guessingGame;

    public AnswerStatus getMajor(AnswerStatus otherState)
    {
        return otherState.ordinal() > this.ordinal() ? otherState : this;
    }
}