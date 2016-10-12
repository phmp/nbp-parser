package pl.parser.nbp;

import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.utils.exceptions.InvalidInputException;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

import static java.time.temporal.ChronoUnit.DAYS;


public class InputData {

    private static final int CURRENCY_INDEX = 0;
    private static final int FIRST_DAY_INDEX = 1;
    private static final int LAST_DAY_INDEX = 2;
    private static final LocalDate THE_OLDEST_RATES_PUBLISH_DATE = LocalDate.of(2002, Month.JANUARY, 2);
    private static final int MAXIMUM_NUMBER_OF_DAYS_BETWEEN_DATES = 93;
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 3;
    private String firstDay;
    private String lastDay;
    private String currencyCode;

    public InputData(String[] args) throws InvalidInputException {
        checkNumberOfArguments(args);

        this.currencyCode = args[CURRENCY_INDEX];
        this.firstDay = args[FIRST_DAY_INDEX];
        this.lastDay = args[LAST_DAY_INDEX];
    }

    public void validate() throws InvalidInputException {
        checkDatesFormat();
        checkDatesOrder();
        checkIfCurrencyCodeIsSupported();
        checkIfDatesAreTooOld();
        checkIfPeriodBetweenDatesIsTooLong();
    }

    private void checkNumberOfArguments(String[] args) throws InvalidInputException {
        if (args.length != CORRECT_NUMBER_OF_ARGUMENTS){
            throw new InvalidInputException("Wrong number of arguments");
        }
    }

    private void checkDatesFormat() throws InvalidInputException {
        try {
            LocalDate.parse(firstDay);
            LocalDate.parse(lastDay);
        }catch(DateTimeParseException e){
            throw new InvalidInputException("Date should be provided in format yyyy-mm-dd");
        }
    }

    private void checkDatesOrder() throws InvalidInputException {
        if (getFirstDay().isAfter(getLastDay())) {
            throw new InvalidInputException("Dates should be provided in chronological order");
        }
    }

    private void checkIfCurrencyCodeIsSupported() throws InvalidInputException {
        try {
            CurrencyCode.valueOf(currencyCode);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Currency code "+currencyCode+" is unsupported");
        }
    }

    private void checkIfDatesAreTooOld() throws InvalidInputException {
        if(getFirstDay().isBefore(THE_OLDEST_RATES_PUBLISH_DATE)){
            throw new InvalidInputException("NBP do not provide rates published before "+THE_OLDEST_RATES_PUBLISH_DATE);
        }
    }

    private void checkIfPeriodBetweenDatesIsTooLong() throws InvalidInputException {
        if(DAYS.between(getFirstDay(), getLastDay()) > MAXIMUM_NUMBER_OF_DAYS_BETWEEN_DATES){
            throw new InvalidInputException("Period between dates must not be longer than "+MAXIMUM_NUMBER_OF_DAYS_BETWEEN_DATES+" days");
        }
    }

    public LocalDate getFirstDay() {
        return LocalDate.parse(firstDay);
    }

    public LocalDate getLastDay() {
        return LocalDate.parse(lastDay);
    }

    public CurrencyCode getCurrencyCode() {
        return CurrencyCode.valueOf(currencyCode);
    }


}
