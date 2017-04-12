package mode;

public class myDate {

	public int year;
	public int month;
	public int day;
	public int hour;
	public int minite;
	public int second;

	public myDate(String date){
		this.toFormat(date);
	}
	
	public myDate(String date, String chinese){
		String[] Temp;
		String[] dateTemp;
		String[] timeTemp;
		Temp = date.split(" ");
		dateTemp = Temp[0].split("-");
		timeTemp = Temp[1].split(":");

		year = Integer.parseInt(dateTemp[0]);
		month = Integer.parseInt(dateTemp[1]);
		day = Integer.parseInt(dateTemp[2]);
		
		hour = Integer.parseInt(timeTemp[0]);
		minite = Integer.parseInt(timeTemp[1]);
		second = Integer.parseInt(timeTemp[2]);
	}
	
	public void toFormat(String date) {		
		String[] dateTemp;
		String[] timeTemp;
		dateTemp = date.split(" ");
		timeTemp = dateTemp[3].split(":");

		year = Integer.parseInt(dateTemp[5]);
		month = this.month_StringToNumber(dateTemp[1]);
		day = Integer.parseInt(dateTemp[2]);
		
		hour = Integer.parseInt(timeTemp[0]);
		minite = Integer.parseInt(timeTemp[1]);
		second = Integer.parseInt(timeTemp[2]);
	}
	
	/**
	 * 
	 * @param date
	 * @return -1表示时间发生早，1表示时间发生晚，0表示时间相等
	 */
	public int compare(myDate date){
		int isBigger = -1;
		if(this.year > date.year){
			isBigger = 1;
		}
		else if(this.year < date.year){
			isBigger = -1;
		}
		else{
			if(this.month > date.month){
				isBigger = 1;
			}
			else if(this.month < date.month){
				isBigger = -1;
			}
			else{
				if(this.day > date.day){
					isBigger = 1;
				}
				else if(this.day < date.day){
					isBigger = -1;
				}
				else{
					if(this.hour > date.hour){
						isBigger = 1;
					}
					else if(this.hour < date.hour){
						isBigger = -1;
					}
					else{
						if(this.minite > date.minite){
							isBigger = 1;
						}
						else if(this.minite < date.minite){
							isBigger = -1;
						}
						else{
							if(this.second > date.second){
								isBigger = 1;
							}
							else if(this.second < date.second){
								isBigger = -1;
							}
							else{
								isBigger = 0;
							}
						}
					}
				}
			}
		}
		return isBigger;
	}

	public String toString(){
		return year+"-"+month+"-"+day+" "+hour+":"+minite+":"+second;		
	}
	
	public int month_StringToNumber(String number) {
		int month = 0;
		switch (number) {
		case "Jan":
			month = 1;
			break;
		case "Feb":
			month = 2;
			break;
		case "Mar":
			month = 3;
			break;
		case "Apr":
			month = 4;
			break;
		case "May":
			month = 5;
			break;
		case "Jun":
			month = 6;
			break;
		case "Jul":
			month = 7;
			break;
		case "Aug":
			month = 8;
			break;
		case "Sep":
			month = 9;
			break;
		case "Oct":
			month = 10;
			break;
		case "Nov":
			month = 11;
			break;
		case "Dec":
			month = 12;
			break;
		}
		return month;
	}

//	public static void main(String[] args){
//		System.out.println(new myDate("2016-3-19 3:00:00", "chinese"));
//	}
}
