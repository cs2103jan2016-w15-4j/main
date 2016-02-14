
public class DateTime {
	String date;	// 08/02/2016
	String time;		// 1300
	String day;		// Mon
	int dd;			// 8
	int mm;			// Feb (Months will all be in short form for now)
	int yy;			// 2016 	
	String[] months = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	String[] daysInWeek = new String[] {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};
	
	public DateTime(int[] date, String day, int time) {
		this.dd = date[0];
		this.mm = date[1];
		this.yy = date[2];
		this.time = convertTimeToString(time);
		this.day = day;
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}
	
	private String convertTimeToString(int time2) {
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
		this.time = String.valueOf(-1);
		this.day = day;
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}
	
	
	public DateTime(String[] split, String day, int time2) {
		// System.out.println("hello");
		int[] date = new int[]{Integer.parseInt(split[0]),
							   Integer.parseInt(split[1]),
							   Integer.parseInt(split[2])};
		this.dd = date[0];
		this.mm = date[1];
		this.yy = date[2];
		this.time = convertTimeToString(time2);
		this.day = day;
		this.date = this.dd + " " + months[this.mm - 1] + " " + this.yy;
	}

	public String getDay() {
		return this.day;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public String toString() {
		String ans = this.date + " " + this.day + " " + this.time;
		return ans;
	}
}
