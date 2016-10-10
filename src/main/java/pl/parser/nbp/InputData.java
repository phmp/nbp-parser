package pl.parser.nbp;

import pl.parser.nbp.utils.Currency;

import java.time.LocalDate;


public class InputData {

    private static final int CURRENCY_INDEX = 0;
    private static final int FROM_DAY_INDEX = 1;
    private static final int TO_DAY_INDEX = 2;
    private LocalDate from;
    private LocalDate to;
    private Currency currency;

    public InputData(String[] args) {
        String currency = args[CURRENCY_INDEX];
        String from = args[FROM_DAY_INDEX];
        String to = args[TO_DAY_INDEX];

        this.currency = Currency.valueOf(currency);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public Currency getCurrency() {
        return currency;
    }
}
