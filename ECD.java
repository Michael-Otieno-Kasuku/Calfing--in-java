public class ECD {
    private int day;
    private int month;
    private int year;
    int firstJanIndex; //stores the index of the day on which first Jan falls
    private final int[] daysPerMonth;
    private final String[] daysNames;
    private final String[] monthsNames;

    //this keyword is used to initialize values in a constructor of a class
    //or when you pass variables to the mutator methods
    ECD() {
        this.daysPerMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        this.daysNames = new String[]{"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
        this.monthsNames = new String[]{"January", "February", "March", "April",
                "May", "June", "July", "August", "September", "October", "November", "December"};
    }

    //Question I
    //mutator methods-->are set methods
    public void setYear(int year) {this.year = year;}
    public void setMonth(int month) {
        this.month = month;
    }
    public void setDay(int day) {
        this.day = day;
    }
    //accessor methods-->are get methods
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }

    //Question II
    //getting the index of first January(which can either be 0 or 1 or 2...or 6)
    public int setFirstJan() {
        firstJanIndex=((5 * ((getYear() - 1) % 4) + 4 * ((getYear() - 1) % 100) + 6 * ((getYear() - 1) % 400)) % 7);
        return firstJanIndex;
    }

    /*A leap year is a year which is divisible by four and a leap year modulus 100 is not equal to zero
    except for the end of century years
    which must be divisible by 400.This means that 2000 was a leap year although 1900 was not*/

    //Question III
    public void isLeap() {
        if (((getYear() % 4 == 0)&&(getYear()%100!=0))||(getYear()%400)==0) {
            //updates the number of days in february if it is a leap year
            daysPerMonth[1] = 29;
        } else {
            //days in February remain unchanged
            daysPerMonth[1] = 28;
        }
    }

    //Question IV
    public int serviceDay() {
        //holds the index of the first January
        int sum = setFirstJan();
        //check if it is a leap year
        isLeap();
        //calculate the total number of days from january to the month before the service month
        /*
        * for example if getMonth()==6 i.e the service month is June
        * getMonth()-1 will be 5, hence the loop will execute 5 times starting from January at index 0
        * to May at index 4*/
        for (int i = 0; i<getMonth()-1; i++) {
            sum += daysPerMonth[i];
        }
        //calculate the total  days from first of January of the entered year upto the day the cow was served
        sum +=getDay()-1;//add upto a day before the service day
        //return the index of the service day
        return  sum %7;
    }

    //Question V
    public int calfingDay(){
        int sum = serviceDay()+282;//calculate the total days upto the time the cow will calf down
        return sum%7;
    }

    //Question VI
    public void calfing() {
        //display the date the cow was served
        System.out.print("Served on " + daysNames[serviceDay()] + " " + getDay() + "-" + monthsNames[getMonth() - 1] + "-" + getYear() + "\n");
        int deviation = daysPerMonth[getMonth()-1]-getDay();//to get the days remaining before the service month elapse
        int difference = 282 - deviation;//get the days remaining after reaching the end of the  month which the cow was served
        while (difference>=0){
            setMonth(getMonth()+1);//move to the next month
            //check if it is the end of the year
            if(getMonth()==13){
                setMonth(1);
                setYear(getYear()+1);
                isLeap();//check to update the days in February
                //if differnce is 0 it means that the date will be the last day of the month before incrementing i.e December of the
                //previous year
                if(difference==0){
                    setDay(31);//set day to 31st
                    setMonth(12);//set month to December
                    setYear(getYear()-1);//decrement year by 1 because you incremented before checking if it is the end month of December
                }else {
                    setDay(difference);//the calfing day will be the difference
                }//if the month is either January  or February or...or December
            }else if(getMonth()>0&&getMonth()<=12){
                //if differenve is 0 it means that the calfing date will be the last day of the month
                // you were in before incrementing
                if(difference==0){
                    setMonth(getMonth()-1);//move back to the previous month
                    setDay(daysPerMonth[month-1]);//the calfing date will the last day of the month
                }else{
                    setDay(difference);//this will be the date in the current month
                }
            }
            difference = difference - daysPerMonth[month-1];//update the difference so that you move closer to the calfing month
        }
        //display the calfing date
        System.out.print("Calfing on " + daysNames[calfingDay()] + " " + getDay() + "-" + monthsNames[getMonth()- 1] + "-" + getYear() + "\n");
    }

    //Question VII
    //display the calendar of the calfing month
    public void calendar() {
        //set font colors for the output
        final String BLACK= "\u001B[30m";//for setting the output font color to black
        final String RED = "\u001B[31m";//for setting the output font color to red
        final String BLUE= "\u001B[34m";//for setting the output font color to blue
        final String LIGHT_GRAY= "\u001B[37m";//for setting the font color to light gray
        final String RESET = "\u001B[0m";//for setting the output font color to default(as per the IDE you're using)

        //display the calendar heading as per the variable month
        System.out.print(BLACK + "\nCalendar for " + monthsNames[getMonth() - 1] + " " + getYear() + "\n" + RESET);
        //display the heading for the days of the week
        for(int i = 0;i<daysNames.length;i++){
            if(i==5){
                //display the Sa in font color blue
                System.out.print(BLUE+daysNames[i]+RESET+"\t");
            }else if(i==6){
                //display the Su in font color Red
                System.out.print(RED+daysNames[i]+RESET);
                System.out.print("\n");
            }else{
                //display the rest in font color black
                System.out.print(BLACK+daysNames[i]+RESET+"\t");
            }
        }
        //holds the index of the first January
        int sum = setFirstJan();
        //calculate the total number of days from first january to the month before the calfing month
        for (int i = getMonth() - 2; i >= 0; i--) {
                sum += daysPerMonth[i];
        }
        //find the index of the first day of the calfing month
        int indexOftheFirstDayOfTheMonth = sum % 7;// this will either be 0 or 1, or 2...or 6
        if(getMonth()==1){
            //if month is January then the index is given by the setFirstJan()
            indexOftheFirstDayOfTheMonth = setFirstJan();
            //print the days in the previous month--> which is December of the previous year
            for (int j = 31 - (indexOftheFirstDayOfTheMonth-1); j <= 31; j++)
            {
                System.out.print(LIGHT_GRAY + j + "\t" + RESET);//days of the previous month in light_gray color
            }
        }else if(getMonth()>1&&getMonth()<12){
            //print the days in the previous month-->which can either be January,February,...October of the current year
            for (int j = daysPerMonth[getMonth() - 2] - (indexOftheFirstDayOfTheMonth-1); j <= daysPerMonth[getMonth() - 2]; j++)
            {
                System.out.print(LIGHT_GRAY + j + "\t" + RESET);//days of the previous month in light_gray color
            }
        }else if(getMonth()==12){
            //this part execute when the year is decremented by one
            //if the month is December then we have to start from the index of first January of that year
            int index = setFirstJan();//Assign index of first January
            //calculate the total number of days from first january upto November
            for (int i = getMonth() - 2; i >= 0; i--) {
                index += daysPerMonth[i];
            }
            indexOftheFirstDayOfTheMonth = index%7;
            //print the days in the previous month-->which is November
            for (int j = daysPerMonth[getMonth() - 2] - (indexOftheFirstDayOfTheMonth-1); j <= daysPerMonth[getMonth() - 2]; j++)
            {
                System.out.print(LIGHT_GRAY + j + "\t" + RESET);//days of the previous month in light_gray color
            }
        }
        //prints out the days of the current month
        for (int k = 1; k <= daysPerMonth[getMonth() - 1]; k++) {
            if(k>1){
                indexOftheFirstDayOfTheMonth = (indexOftheFirstDayOfTheMonth+1)%7;
            }
            //display Sunday in red and move to the next week
            if (indexOftheFirstDayOfTheMonth==6) {
                System.out.print(RED + k + "\n" + RESET);
            }
            //display Saturday in blue and move to Sunday
            else if (indexOftheFirstDayOfTheMonth==5) {
                    System.out.print(BLUE + k + "\t" + RESET);
            }
            //display the rest of the days of the week in black
            else
            {
                System.out.print(BLACK + k + "\t" + RESET);
            }
        }
        //Find the index of the first day of the subsequent month
        indexOftheFirstDayOfTheMonth = (indexOftheFirstDayOfTheMonth+1)%7;
        //count to help us print the days of the first two weeks of the subsequent month
        int count = 1;
        //prints out the days of the subsequent month
        for (int k = 1; k <= daysPerMonth[getMonth() - 1]; k++) {
            if(k>1){
                indexOftheFirstDayOfTheMonth = (indexOftheFirstDayOfTheMonth+1)%7;
            }
            //checks if it is the end of the week
            if (indexOftheFirstDayOfTheMonth==6) {
                //display the last day of the  week of the subsequent month week
                System.out.print(LIGHT_GRAY + k + RESET+"\n");//print the day in light gray
                //update the value of count if it is the end of the wek
                count+=1;
                if(count>2||indexOftheFirstDayOfTheMonth<5){
                    break;//it stops after printing the first two weeks of the subsequent month
                    //or if the index of the first day of the subsequent month is less than 5
                }
            } else {
                //display the days of the subsequent month in light gray
                System.out.print(LIGHT_GRAY + k + "\t" + RESET);
            }
        }
    }

    //Question VIII
    //now lets test if our program is working
    public  class ECDTest{
        public static void main(String[] args) {
            ECD ecd = new ECD();
            ecd.setYear(2022);//set serving year
            ecd.setMonth(4);//set serving month
            ecd.setDay(1);//set serving day
            ecd.calfing();//display the serving date and calfing date respectively
            ecd.calendar();//this is the line which was missing-->displays the calendar of the calfing month
            //display calendar for the two previous months
            for(int m = 1; m<3;m++){
                int updateMonth = (ecd.getMonth());
                //if the calfing month is not January or December
                //then display the previous month
                if(updateMonth<=12&&updateMonth>1){
                        ecd.setYear(ecd.getYear());
                        ecd.setMonth(ecd.getMonth()-1);
                        ecd.calendar();
                }//check if the calfing month was January then reduce the year by one and set month to December
                else if(updateMonth==1){
                    ecd.setYear(ecd.getYear()-1);
                    ecd.isLeap();
                    ecd.setFirstJan();
                    ecd.setMonth(12);
                    ecd.calendar();
                }
            }
        }
    }
}
