package game.player.status;

import game.entity.guessingGame.GuessingGame;

/**
 * Possibili stati dell'azione 'answer'.
 */
public enum AnswerStatus
{
    noQuestions,
    alreadySolved,
    notSolved,
    definitelyAlreadyResolved,
    solved;

    private GuessingGame guessingGame;

    /**
     * Confronta lo stato attuale con un altro stato.
     * Restituisce lo stato con valore dell'ordine maggiore.
     * @param otherState altro stato
     * @return restituisce l'AnswerStatus con l'ordinal maggiore
     */
    public AnswerStatus getMajor(AnswerStatus otherState)
    {
        return otherState.ordinal() > this.ordinal() ? otherState : this;
    }

    /**
     *
     * @return l'indovinello
     */
    public GuessingGame getGuessingGame()
    {
        return guessingGame;
    }

    /**
     * Imposta l'indovinello.
     * @param guessingGame indovinello da impostare
     */
    public void setGuessingGame(GuessingGame guessingGame)
    {
        this.guessingGame = guessingGame;
    }
}