package myDate;

public enum MonthName {
    JANUARY(31, 1),
    FEBRUARY(28, 2),
    MARCH(31, 3),
    APRIL(30, 4),
    MAY(31, 5),
    JUNE(30, 6),
    JULY(31, 7),
    AUGUST(31, 8),
    SEPTEMBER(30, 9),
    OCTOBER(31, 10),
    NOVEMBER(30, 11),
    DECEMBER(31, 12);

    int monthLastDay;
    int monthNumber;

    MonthName(int monthLastDay, int monthNumber) {
        this.monthLastDay = monthLastDay;
        this.monthNumber = monthNumber;
    }


}
