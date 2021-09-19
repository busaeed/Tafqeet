import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tafqeet {
	
	private static String riyalSaudi = "ريال سعودي";
	private static String riyalaanSaudiaan = "ريالان سعوديان اثنان";
	private static String riyalatSaudi = "ريالات سعودية";
	private static String riyalanSaudian = "ريالاً سعودياً";
	
	private static String minus = "سالب";
	private static String and = "و";
	
	private static String[] ones = {"صفر", "واحد", "اثنان", "ثلاثة", "اربعة", "خمسة", "ستة", "سبعة", "ثمانية", "تسعة"};
	private static String[] tens = {"", "عشرة", "عشرون", "ثلاثون", "اربعون", "خمسون", "ستون", "سبعون", "ثمانون", "تسعون"};
	private static String[] hundreds = {"", "مائة", "مائتان", "ثلاثمائة", "اربعمائة", "خمسمائة", "ستمائة", "سبعمائة", "ثمانمائة", "تسعمائة"};
	
	private static String eleven = "أحد عشر";
	private static String twelve = "إثنا عشر";
	private static String ashar = "عشر";
	
	private static String m2ta = "مائتا";
	
	private static String alf = "ألف";
	private static String alfa = "ألفا";
	private static String alfaan = "ألفان";
	private static String alaaf = "آلاف";
	private static String alfan = "ألفاً";
	
	private static String million = "مليون";
	private static String milliona = "مليونا";
	private static String millionaan = "مليونان";
	private static String malayeen = "ملايين";
	private static String millionan = "مليوناً";
	
	private static String milliar = "مليار";
	private static String millaira = "مليارا";
	private static String millairaan = "ملياران";
	private static String milliarat = "مليارات";
	private static String milliaran = "ملياراً";
	
	private static String trillion = "تريليون";
	private static String trilliona = "تريليونا";
	private static String trillionaan = "تريليونان";
	private static String trillionat = "تريليونات";
	private static String trillionan = "تريليوناً";
	
	private static String oneHalalah = "واحدة";
	private static String halalataan = "هللتان";
	private static String halalat = "هللات";
	private static String halalah = "هللة";
	
	private static String[] halalahOnes = {"", "احدى", "اثنتان", "ثلاث", "اربع", "خمس", "ست", "سبع", "ثمان", "تسع"};
	
	private static String tenHalalat = "عشر";
	private static String elevenHalalah = "احدى";
	private static String twelveHalalah = "اثنتا";
	
	public static String convert(BigDecimal number) {
		number = number.setScale(2, RoundingMode.CEILING);
		if (number.compareTo(new BigDecimal("1000000000000000")) != -1 || number.compareTo(new BigDecimal("-1000000000000000")) != 1) {
			throw new IllegalArgumentException("Number is out of allowed range. The allowed range is between -999999999999999.99 to 999999999999999.99");
		}
		long integerPart = number.longValue();
		int fractionPart = number.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).intValue();
		String result = "";
		result += convertIntegerPart(integerPart);
		if (fractionPart >= 1 && fractionPart <= 99) {
			result += " " + and + " " + convertFractionPart(fractionPart);
		}
		return result;
	}
	
	private static String convertIntegerPart(long integerPart) {
		if (integerPart < 0) { //Minus
			return minus + " " + convertIntegerPart(Math.abs(integerPart));
		} else if (integerPart >= 1 && integerPart <= 9) { //Ones
			return convertOnes(integerPart);
		} else if (integerPart >= 10 && integerPart <= 99) { //Tens
			return convertTens(integerPart);
		} else if (integerPart >= 100 && integerPart <= 999) { //Hundreds
			return convertHundreds(integerPart);
		} else if (integerPart >= 1000 && integerPart <= 999999) { //Thousands
			return convertThousands(integerPart);
		} else if (integerPart >= 1000000 && integerPart <= 999999999) { //Millions
			return convertMillions(integerPart);
		} else if (integerPart >= 1000000000 && integerPart <= 999999999999L) { //Billions
			return convertBillions(integerPart);
		} else if (integerPart >= 1000000000000L && integerPart <= 999999999999999L) { //Trillions
			return convertTrillions(integerPart);
		}
		return null;
	}
	
	private static String convertOnes(long integerPart) {
		if (integerPart == 0) { //Zero
			return ones[0] + " " + riyalSaudi;
		} else if (integerPart == 1) { //1
			return riyalSaudi + " " + ones[1];
		} else if (integerPart == 2) { //2
			return riyalaanSaudiaan;
		} else if (integerPart >= 3 && integerPart <= 9) { //3-9
			return ones[(int) integerPart] + " " + riyalatSaudi;
		}
		return null;
	}
	
	private static String convertTens(long integerPart) {
		if (integerPart == 10) { //10
			return tens[1] + " " + riyalatSaudi;
		} else if (integerPart >= 11 && integerPart <= 99) { //11-99
			return convertRawTens((int) integerPart) + " " + riyalanSaudian;
		}
		return null;
	}
	
	private static String convertHundreds(long integerPart) {
		if (integerPart == 200) {
			return m2ta + " " + riyalSaudi;
		} else if (integerPart >= 100 && integerPart <= 900 && integerPart%100 == 0) { //100,200,300,...
			return hundreds[(int) (integerPart/100)] + " " + riyalSaudi;
		} else if (integerPart >= 101 && integerPart <= 999) { //101-999
			return hundreds[(int) (integerPart/100)] + " " + and + " " + convertIntegerPart(integerPart%100);
		}
		return null;
	}
	
	private static String convertThousands(long integerPart) {
		if (integerPart >= 1000 && integerPart <= 9999) { //1000-9999
			return convertOnesThousands(integerPart);
		} else if (integerPart >= 10000 && integerPart <= 99999) { //10000-99999
			return convertTensThousands(integerPart);
		} else if (integerPart >= 100000 && integerPart <= 999999) { //100000-999999
			return convertHundredsThousands(integerPart);
		}
		return null;
	}
	
	private static String convertOnesThousands(long integerPart) {
		if (integerPart == 1000) { //1000
			return alf + " " + riyalSaudi;
		} else if (integerPart >= 1001 && integerPart <= 1999) { //1001-1999
			return alf + " " + and + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart == 2000) { //2000
			return alfa + " " + riyalSaudi;
		} else if (integerPart >= 2001 && integerPart <= 2999) { //2001-2999
			return alfaan + " " + and + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart >= 3000 && integerPart <= 9000 && integerPart%1000 == 0) { //3000,4000,5000,...
			return ones[(int) (integerPart/1000)] + " " + alaaf + " " + riyalSaudi;
		} else if (integerPart == 10000) { //10000
			return tens[1] + " " + alaaf + " " + riyalSaudi;
		} else if (integerPart >= 3001 && integerPart <= 9999) { //3001-9999
			return ones[(int) (integerPart/1000)] + " " + alaaf + " " + and + " " + convertIntegerPart(integerPart%1000);
		}
		return null;
	}
	
	private static String convertTensThousands(long integerPart) {
		if (integerPart == 10000) { //10000
			return tens[1] + " " + alaaf + " " + riyalSaudi;
		} else if (integerPart >= 3001 && integerPart <= 9999) { //3001-9999
			return ones[(int) (integerPart/1000)] + " " + alaaf + " " + and + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart == 10000) {
			return tens[1] + " " + alaaf + " " + riyalSaudi;
		} else if (integerPart >= 10001 && integerPart <= 10999) { //10001-10999
			return tens[1] + " " + alaaf + " " + and + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart == 11000) { //11000
			return eleven + " " + alf + " " + riyalSaudi;
		} else if (integerPart == 12000) { //12000
			return twelve + " " + alf + " " + riyalSaudi;
		} else if (integerPart >= 11001 && integerPart <= 11999) { //11001-11999
			return eleven + " " + alfan + " " + and + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart >= 12001 && integerPart <= 12999) { //12001-12999
			return twelve + " " + alfan + " " + and + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart >= 13000 && integerPart <= 19000 && integerPart%1000 == 0) { //13000,14000,15000,...
			return convertRawTens((int) (integerPart/1000)) + " " + alf + " " + riyalSaudi;
		} else if (integerPart >= 13001 && integerPart <= 19999) { //13001-19999
			return convertRawTens((int) (integerPart/1000)) + " " + alfan + " " + and + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart >= 20000 && integerPart <= 90000 && integerPart%10000 == 0) { //20000-90000
			return tens[(int) (integerPart/1000/10)] + " " + alf + " " + riyalSaudi;
		} else if (integerPart >= 21000 && integerPart <= 99000 && integerPart%1000 == 0) { //21000-99000
			return convertRawTens((int) (integerPart/1000)) + " " + alf + " " + riyalSaudi;
		} else if (integerPart >= 20001 && integerPart <= 99999) { //20001-99999
			return convertRawTens((int) (integerPart/1000)) + " " + alfan + " " + and + " " + convertIntegerPart(integerPart%1000);
		}
		return null;
	}
	
	private static String convertHundredsThousands(long integerPart) {
		if (integerPart == 200000) { //200000
			return m2ta + " " + alf + " " + riyalSaudi;
		} else if (integerPart >= 200001 && integerPart <= 200999) { //200001-200999
			return m2ta + " " + alfan + " " + and + " " + convertIntegerPart(integerPart%100000);
		} else if (integerPart >= 100000 && integerPart <= 900000 && integerPart%100000 == 0) { //100000,300000,400000,...
			return hundreds[(int) (integerPart/100000)] + " " + alf + " " + riyalSaudi;
		} else if (integerPart >= 100001 && integerPart <= 900999 && integerPart%100000 < 1000) { //100001-900999
			return hundreds[(int) (integerPart/100000)] + " " + alf + " " + and + " " + convertIntegerPart(integerPart%100000);
		} else if (integerPart >= 101000 && integerPart <= 999000 && (integerPart%100000)%1000 == 0) { //101000-999000
			return hundreds[(int) (integerPart/100000)] + " " + and + " " + convertIntegerPart(integerPart%100000);
		} else if (integerPart >= 100001 && integerPart <= 999999) { //100001-999999
			return hundreds[(int) (integerPart/100000)] + " " + and + " " + convertIntegerPart(integerPart%100000);
		}
		return null;
	}
	
	private static String convertMillions(long integerPart) {
		if (integerPart >= 1000000 && integerPart <= 9999999) { //1000000
			return convertOnesMillions(integerPart);
		} else if (integerPart >= 10000000 && integerPart <= 99999999) {
			return convertTensMillions(integerPart);
		} else if (integerPart >= 100000000 && integerPart <= 999999999) {
			return convertHundredsMillions(integerPart);
		}
		return null;
	}
	
	private static String convertOnesMillions(long integerPart) {
		if (integerPart == 1000000) { //1000000
			return million + " " + riyalSaudi;
		} else if (integerPart >= 1000001 && integerPart <= 1999999) { //1000001-1999999
			return million + " " + and + " " + convertIntegerPart(integerPart%1000000);
		} else if (integerPart == 2000000) { //2000000
			return milliona + " " + riyalSaudi;
		} else if (integerPart >= 2000001 && integerPart <= 2999999) { //2000001-2999999
			return millionaan + " " + and + " " + convertIntegerPart(integerPart%1000000);
		} else if (integerPart >= 3000000 && integerPart <= 9000000 && integerPart%1000000 == 0) { //3000000-9000000
			return ones[(int) (integerPart/1000000)] + " " + malayeen + " " + riyalSaudi;
		} else if (integerPart >= 3000001 && integerPart <= 9999999) { //3000001-9999999
			return ones[(int) (integerPart/1000000)] + " " + malayeen + " " + and + " " + convertIntegerPart(integerPart%1000000);
		}
		return null;
	}
	
	private static String convertTensMillions(long integerPart) {
		if (integerPart == 10000000) { //10000000
			return tens[1] + " " + malayeen + " " + riyalSaudi;
		} else if (integerPart >= 10000001 && integerPart <= 10999999) { //10000001-10999999
			return tens[1] + " " + malayeen + " " + and + " " + convertIntegerPart(integerPart%10000000);
		} else if (integerPart == 11000000) { //11000000
			return eleven + " " + million + " " + riyalSaudi;
		} else if (integerPart >= 11000001 && integerPart <= 11999999) { //11000001-11999999
			return eleven + " " + millionan + " " + and + " " + convertIntegerPart(integerPart%11000000);
		} else if (integerPart == 12000000) {  //12000000
			return twelve + " " + million + " " + riyalSaudi;
		} else if (integerPart >= 12000001 && integerPart <= 12999999) { //12000001-12999999
			return twelve + " " + millionan + " " + and + " " + convertIntegerPart(integerPart%12000000);
		} else if (integerPart >= 13000000 && integerPart <= 19000000 && integerPart%1000000 == 0) { //13000000-19000000
			return convertRawTens((int) (integerPart/1000000)) + " " + million + " " + riyalSaudi;
		} else if (integerPart >= 13000001 && integerPart <= 19999999) { //13000001-19999999
			return convertRawTens((int) (integerPart/1000000)) + " " + millionan + " " + and + " " + convertIntegerPart(integerPart%1000000);
		} else if (integerPart >= 20000000 && integerPart <= 90000000 && integerPart%10000000 == 0) { //2000000-9000000
			return convertRawTens((int) (integerPart/1000000)) + " " + million + " " + riyalSaudi;
		} else if (integerPart >= 20000001 && integerPart <= 99999999) { //20000001-99999999
			return convertRawTens((int) (integerPart/1000000)) + " " + millionan + " " + and + " " + convertIntegerPart(integerPart%1000000);
		}
		return null;
	}
	
	private static String convertHundredsMillions(long integerPart) {
		if (integerPart == 200000000) { //200000000
			return m2ta + " " + million + " " + riyalSaudi;
		} else if (integerPart >= 200000001 && integerPart <= 200999999) { //200000001-200999999
			return m2ta + " " + millionan + " " + and + " " + convertIntegerPart(integerPart%100000000);
		} else if (integerPart >= 100000000 && integerPart <= 900000000 && integerPart%100000000 == 0) { //100000000,300000000,400000000,...
			return hundreds[(int) (integerPart/100000000)] + " " + million + " " + riyalSaudi;
		} else if (integerPart >= 100000001 && integerPart <= 900999999 && integerPart%100000000 < 1000000) { //100000001-900999999
			return hundreds[(int) (integerPart/100000000)] + " " + million + " " + and + " " + convertIntegerPart(integerPart%100000000);
		} else if (integerPart >= 101000000 && integerPart <= 999000000 && (integerPart%100000000)%1000000 == 0) { //101000000-999000000
			return hundreds[(int) (integerPart/100000000)] + " " + and + " " + convertIntegerPart(integerPart%100000000);
		} else if (integerPart >= 100000001 && integerPart <= 999999999) { //100000001-999999999
			return hundreds[(int) (integerPart/100000000)] + " " + and + " " + convertIntegerPart(integerPart%100000000);
		}
		return null;
	}
	
	private static String convertBillions(long integerPart) {
		if (integerPart >= 1000000000 && integerPart <= 9999999999L) {
			return convertOnesBillions(integerPart);
		} else if (integerPart >= 10000000000L && integerPart <= 99999999999999L) {
			return convertTensBillions(integerPart);
		} else if (integerPart >= 100000000000L && integerPart <= 999999999999L) {
			return convertHundredsBillions(integerPart);
		}
		return null;
	}
	
	private static String convertOnesBillions(long integerPart) {
		if (integerPart == 1000000000) { //1000000000
			return milliar + " " + riyalSaudi;
		} else if (integerPart >= 1000000001 && integerPart <= 1999999999) {
			return milliar + " " + and + " " + convertIntegerPart(integerPart%1000000000);
		} else if (integerPart == 2000000000) {
			return millaira + " " + riyalSaudi;
		} else if (integerPart >= 2000000001 && integerPart <= 2999999999L) {
			return millairaan + " " + and + " " + convertIntegerPart(integerPart%2000000000);
		} else if (integerPart >= 3000000000L && integerPart <= 9000000000L && integerPart%1000000000 == 0) {
			return ones[(int) (integerPart/1000000000)] + " " + milliarat + " " + riyalSaudi;
		} else if (integerPart >= 3000000001L && integerPart <= 9999999999L) {
			return ones[(int) (integerPart/1000000000)] + " " + milliarat + " " + and + " " + convertIntegerPart(integerPart%1000000000);
		}
		return null;
	}
	
	private static String convertTensBillions(long integerPart) {
		if (integerPart == 10000000000L) {
			return tens[1] + " " + milliarat + " " + riyalSaudi;
		} else if (integerPart >= 10000000001L && integerPart <= 10999999999L) {
			return tens[1] + " " + milliarat + " " + and + " " + convertIntegerPart(integerPart%10000000000L);
		} else if (integerPart == 11000000000L) {
			return eleven + " " + milliar + " " + riyalSaudi;
		} else if (integerPart >= 11000000001L && integerPart <= 11999999999L) {
			return eleven + " " + milliaran + " " + and + " " + convertIntegerPart(integerPart%11000000000L);
		} else if (integerPart == 12000000000L) {
			return eleven + " " + milliar + " " + riyalSaudi;
		} else if (integerPart >= 12000000001L && integerPart <= 12999999999L) {
			return twelve + " " + milliaran + " " + and + " " + convertIntegerPart(integerPart%12000000000L);
		} else if (integerPart >= 13000000000L && integerPart <= 19000000000L && integerPart%1000000000L == 0) {
			return convertRawTens((int) (integerPart/1000000000)) + " " + milliar + " " + riyalSaudi;
		} else if (integerPart >= 13000000001L && integerPart <= 19999999999L) {
			return convertRawTens((int) (integerPart/1000000000)) + " " + milliaran + " " + and + " " + convertIntegerPart(integerPart%1000000000);
		} else if (integerPart >= 20000000000L && integerPart <= 90000000000L && integerPart%10000000000L == 0) {
			return convertRawTens((int) (integerPart/1000000000)) + " " + milliar + " " + riyalSaudi;
		} else if (integerPart >= 20000000001L && integerPart <= 99999999999L) {
			return convertRawTens((int) (integerPart/1000000000)) + " " + milliaran + " " + and + " " + convertIntegerPart(integerPart%1000000000);
		}
		return null;
	}
	
	private static String convertHundredsBillions(long integerPart) {
		if (integerPart == 200000000000L) {
			return m2ta + " " + milliar + " " + riyalSaudi;
		} else if (integerPart >= 200000000001L && integerPart <= 200999999999L) {
			return m2ta + " " + milliaran + " " + and + " " + convertIntegerPart(integerPart%100000000000L);
		} else if (integerPart >= 100000000000L && integerPart <= 900000000000L && integerPart%100000000000L == 0) {
			return hundreds[(int) (integerPart/100000000000L)] + " " + milliar + " " + riyalSaudi;
		} else if (integerPart >= 100000000001L && integerPart <= 900999999999L && integerPart%100000000000L < 1000000000) {
			return hundreds[(int) (integerPart/100000000000L)] + " " + milliar + " " + and + " " + convertIntegerPart(integerPart%100000000000L);
		} else if (integerPart >= 101000000000L && integerPart <= 999000000000L && (integerPart%100000000000L)%1000000000 == 0) {
			return hundreds[(int) (integerPart/100000000000L)] + " " + and + " " + convertIntegerPart(integerPart%100000000000L);
		} else if (integerPart >= 100000000001L && integerPart <= 999999999999L) {
			return hundreds[(int) (integerPart/100000000000L)] + " " + and + " " + convertIntegerPart(integerPart%100000000000L);
		}
		return null;
	}
	
	private static String convertTrillions(long integerPart) {
		if (integerPart >= 1000000000000L && integerPart <= 9999999999999L) { //1000000000000
			return convertOnesTrillions(integerPart);
		} else if (integerPart >= 10000000000000L && integerPart <= 99999999999999L) {
			return convertTensTrillions(integerPart);
		} else if (integerPart >= 100000000000000L && integerPart <= 999999999999999L) {
			return convertHundredsTrillions(integerPart);
		}
		return null;
	}
	
	private static String convertOnesTrillions(long integerPart) {
		if (integerPart == 1000000000000L) { //1000000000000
			return trillion + " " + riyalSaudi;
		} else if (integerPart >= 1000000000001L && integerPart <= 1999999999999L) {
			return trillion + " " + and + " " + convertIntegerPart(integerPart%1000000000000L);
		} else if (integerPart == 2000000000000L) {
			return trilliona + " " + riyalSaudi;
		} else if (integerPart >= 2000000000001L && integerPart <= 2999999999999L) {
			return trillionaan + " " + and + " " + convertIntegerPart(integerPart%2000000000);
		} else if (integerPart >= 3000000000000L && integerPart <= 9000000000000L && integerPart%1000000000000L == 0) {
			return ones[(int) (integerPart/1000000000000L)] + " " + trillionat + " " + riyalSaudi;
		} else if (integerPart >= 3000000000001L && integerPart <= 9999999999999L) {
			return ones[(int) (integerPart/1000000000000L)] + " " + trillionat + " " + and + " " + convertIntegerPart(integerPart%1000000000000L);
		}
		return null;
	}
	
	private static String convertTensTrillions(long integerPart) {
		if (integerPart == 10000000000000L) {
			return tens[1] + " " + trillionat + " " + riyalSaudi;
		} else if (integerPart >= 10000000000001L && integerPart <= 10999999999999L) {
			return tens[1] + " " + trillionat + " " + and + " " + convertIntegerPart(integerPart%10000000000000L);
		} else if (integerPart == 11000000000000L) {
			return eleven + " " + trillion + " " + riyalSaudi;
		} else if (integerPart >= 11000000000001L && integerPart <= 11999999999999L) {
			return eleven + " " + trillionan + " " + and + " " + convertIntegerPart(integerPart%11000000000000L);
		} else if (integerPart == 12000000000000L) {
			return eleven + " " + trillion + " " + riyalSaudi;
		} else if (integerPart >= 12000000000001L && integerPart <= 12999999999999L) {
			return twelve + " " + trillionan + " " + and + " " + convertIntegerPart(integerPart%12000000000000L);
		} else if (integerPart >= 13000000000000L && integerPart <= 19000000000000L && integerPart%1000000000000L == 0) {
			return convertRawTens((int) (integerPart/1000000000000L)) + " " + trillion + " " + riyalSaudi;
		} else if (integerPart >= 13000000000001L && integerPart <= 19999999999999L) {
			return convertRawTens((int) (integerPart/1000000000000L)) + " " + trillionan + " " + and + " " + convertIntegerPart(integerPart%1000000000000L);
		} else if (integerPart >= 20000000000000L && integerPart <= 90000000000000L && integerPart%10000000000000L == 0) {
			return convertRawTens((int) (integerPart/1000000000000L)) + " " + trillion + " " + riyalSaudi;
		} else if (integerPart >= 20000000000001L && integerPart <= 99999999999999L) {
			return convertRawTens((int) (integerPart/1000000000000L)) + " " + trillionan + " " + and + " " + convertIntegerPart(integerPart%1000000000000L);
		}
		return null;
	}
	
	private static String convertHundredsTrillions(long integerPart) {
		if (integerPart == 200000000000000L) {
			return m2ta + " " + trillion + " " + riyalSaudi;
		} else if (integerPart >= 200000000000001L && integerPart <= 200999999999999L) {
			return m2ta + " " + trillionan + " " + and + " " + convertIntegerPart(integerPart%100000000000L);
		} else if (integerPart >= 100000000000000L && integerPart <= 900000000000000L && integerPart%100000000000000L == 0) {
			return hundreds[(int) (integerPart/100000000000000L)] + " " + trillion + " " + riyalSaudi;
		} else if (integerPart >= 100000000000001L && integerPart <= 900999999999999L && integerPart%100000000000000L < 1000000000000L) {
			return hundreds[(int) (integerPart/100000000000000L)] + " " + trillion + " " + and + " " + convertIntegerPart(integerPart%100000000000000L);
		} else if (integerPart >= 101000000000000L && integerPart <= 999000000000000L && (integerPart%100000000000000L)%1000000000000L == 0) {
			return hundreds[(int) (integerPart/100000000000000L)] + " " + and + " " + convertIntegerPart(integerPart%100000000000000L);
		} else if (integerPart >= 100000000000001L && integerPart <= 999999999999999L) {
			return hundreds[(int) (integerPart/100000000000000L)] + " " + and + " " + convertIntegerPart(integerPart%100000000000000L);
		}
		return null;
	}
	
	private static String convertRawTens(int number) {
		if (number == 11) {
			return eleven;
		} else if (number == 12) {
			return twelve;
		} else if (number >= 13 && number <= 19) {
			return ones[number%10] + " " + ashar;
		} else if (number >= 20 && number <= 90 && number%10 == 0) {
			return tens[number/10];
		} else if (number >= 21 && number <= 99) {
			return ones[number%10] + " " + and + " " + tens[number/10];
		}
		return null;
	}
	
	private static String convertFractionPart(int fractionPart) {
		if (fractionPart == 1) {
			return halalah + " " + oneHalalah;
		} else if (fractionPart == 2) {
			return halalataan + " " + halalahOnes[2];
		} else if (fractionPart >= 3 && fractionPart <= 9) {
			return halalahOnes[fractionPart] + " " + halalat;
		} else if (fractionPart == 10) {
			return tenHalalat + " " + halalat;
		} else if (fractionPart == 11) {
			return elevenHalalah + " " + tens[1] + " " + halalah;
		} else if (fractionPart == 12) {
			return twelveHalalah + " " + tens[1] + " " + halalah;
		} else if (fractionPart >= 13 && fractionPart <= 19) {
			return halalahOnes[fractionPart%10] + " " + tens[1] + " " + halalah;
		} else if (fractionPart >= 20 && fractionPart <= 90 && fractionPart%10 == 0) {
			return tens[fractionPart/10] + " " + halalah;
		} else if (fractionPart >= 21 && fractionPart <= 99) {
			return halalahOnes[fractionPart%10] + " " + and + " " + tens[fractionPart/10] + " " + halalah;
		}
		return null;
	}

}
