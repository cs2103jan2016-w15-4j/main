package dooyit.parser;

public class DateTime {
	private static final int UNINITIALIZED = -1;
	
	private String date;	// 08/02/2016
	private String timeStr24H;		// 1300
	private String timeStr12H;		// 1 pm, 2 am, 3.30 am
	private String day;		// Mon
	private int dd;			// 8
	private int mm;			// Feb (Months will all be in short form for now)
	private int yy;			// 2016 	
	private int timeInt;
	private String[] months = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	private String[] daysInWeek = new String[] {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};
	
	public DateTime(int[] date, String day, int time) {
		this.dd = date[0];
		this.mm = date[1];
		this.yy = date[2];
		this.timeInt = time;
		this.timeStr24H = convertTimeIntTo24hString(time);
		this.timeStr12H = convertTimeIntTo12hString(time);
		this.day = day;
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}
	
	//Converts the 24h time int to 12h String format
	private String convertTimeIntTo12hString(int time2) {
		String temp;
		if(time2 == 0) {
			temp = "12 am";
		} else if(time2 < 100 && time2 > 0) {
			temp = "12." + time2 + " am";
		} else if(time2 > 100 && time2 < 1200) {
			if(time2 % 100 == 0) {		//12 am etc
				temp = time2 / 100 + " am";
			} else {					//12.30 am etc
				temp = time2 / 100 + "." + time2 % 100 + " am";
			}
		} else {
			if(time2 % 100 == 0) {		//12 pm etc
				temp = (time2 - 1200) / 100 + " pm";
			} else {					//12.30 pm etc
				temp = (time2 - 1200) / 100 + "." + time2 % 100 + " pm";
			}
		}
		return temp;
	}
	
	//Converts the 24h time int to 24h String format
	private String convertTimeIntTo24hString(int time2) {
		String temp;
		if(time2 == 0) {
			temp = "0000";
		} else if(time2 < 100 && time2 > 0) {
			temp = "00" + time2;
		} else if(time2 > 100 && time2 < 1000) {
			temp = "0" + time2;
		} else {
			temp = String.valueOf(time2);
		}
		return temp;
	}

	public DateTime(int[] date, String day) {		
		this.dd = date[0];
		this.mm = date[1];
		this.yy = date[2];
		this.timeStr24H = String.valueOf(-1);
		this.timeStr12H = String.valueOf(-1);
		this.day = day;
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}
	
	public DateTime(int dd, int mm, int yy, String day, String time) {		
		this.dd = dd;
		this.mm = mm;
		this.yy = yy;
		this.timeStr24H = time;
		this.timeStr12H = convertTimeIntTo12hString(Integer.parseInt(time));
		this.day = day;
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}
	
	public DateTime(String[] split, String day, int time2) {
		int[] date = new int[]{Integer.parseInt(split[0]),
							   Integer.parseInt(split[1]),
							   Integer.parseInt(split[2])};
		this.dd = date[0];
		this.mm = date[1];
		this.yy = date[2];
		this.timeStr24H = convertTimeIntTo24hString(time2);
		this.timeStr12H = convertTimeIntTo12hString(time2);
		this.day = day;
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}

	public String getDay() {
		return this.day;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getTime24hStr() {
		return this.timeStr24H;
	}
	
	public String getTime12hStr() {
		return this.timeStr12H;
	}
	
	public int getTimeInt() {
		return this.timeInt;
	}
	
	public int getDD() {
		return this.dd;
	}
	
	public int getMM() {
		return this.mm;
	}
	
	public int getYY() {
		return this.yy;
	}
	
	public String toString() {
		String ans = this.date + " " + this.day + " " + this.timeStr24H + " " + this.timeStr12H;
		return ans;
	}
	
	public boolean hasTime() {
		return this.getTimeInt() != UNINITIALIZED;
	}
	
	public boolean isTheSameDateAs(DateTime obj) {
		return this.getDD() == obj.getDD() && this.getMM() == obj.getMM() && this.getYY() == obj.getYY();
	}
	
	public String[] convertToSavableStrings(){
		String[] strings = new String[5];
		
		strings[0] = String.valueOf(dd);
		strings[1] = String.valueOf(mm);
		strings[2] = String.valueOf(yy);
		strings[3] = day;
		strings[4] = timeStr24H;
		
		return strings;
	}
	
}
