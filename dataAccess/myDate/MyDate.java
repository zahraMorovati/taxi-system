package myDate;

public class MyDate {

    private int year;
    private int month;
    private  int day;

    public MyDate() {
    }

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean isValidDate(int year, int month, int day) {

        if (year < 9999 && year >= 1) {
            if (month <= 12 && month >= 1) {
                if (day <= getMonthLastDay(month) && day >= 1)
                    return true;
            }
        }
        return false;
    }

    public int getMonthLastDay(int month) {

        for (MonthName name : MonthName.values()) {
            if (name.monthNumber == month) {
                return name.monthLastDay;
            }
        }
        return -1;
    }

    public MonthName getMonthName(int month) {

        for (MonthName name : MonthName.values()) {
            if (name.monthNumber == month) {
                return name;
            }
        }
        return null;
    }

    public void nextDay(){
        if (day== getMonthLastDay(month)){
            if(month==12){
                setMonth(1);
                setDay(1);
                setYear(year+1);
            }else {
                setMonth(month+1);
                setDay(1);
            }
        }else {
            setDay(day+1);
        }
    }

    public static MyDate getCurrentDate(){
        int year=java.time.LocalDate.now().getYear();
        int month=java.time.LocalDate.now().getMonthValue();
        int day=java.time.LocalDate.now().getDayOfMonth();
        MyDate currentDate=new MyDate(year,month,day);
        return currentDate;
    }

    public static MyDate convertStringToDate(String str){
        String[] dateArray=new String[3];
        if(str.contains("-")){
            dateArray=str.split("-");
        }else if(str.contains("/")){
            dateArray=str.split("/");
        }else {
            return null;
        }
        int year=Integer.parseInt(dateArray[0]);
        int month=Integer.parseInt(dateArray[1]);
        int day=Integer.parseInt(dateArray[2]);
        MyDate date=new MyDate(year,month,day);
        return date;
    }

    @Override
    public String toString() {
        return year+"-"+month+"-"+day;
    }

    public void print(){
        System.out.println(year+"/"+month+"/"+day);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
