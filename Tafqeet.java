import java.math.BigDecimal;

public class Tafqeet {
	
	private static final int CURRENCY_SCALE = 2;
	
	private static final String SINGULAR_CURRENCY_NAME = "ريال سعودي";
	private static final String DUAL_CURRENCY_NAME = "ريالان سعوديان اثنان";
	private static final String PLURAL_CURRENCY_NAME = "ريالات سعودية";
	private static final String ACCUSATIVE_CURRENCY_NAME = "ريالاً سعودياً";
	
	private static final String MINUS = "سالب";
	private static final String AND = "و";
	
	private static final String[] ONES = {"صفر", "واحد", "اثنان", "ثلاثة", "اربعة", "خمسة", "ستة", "سبعة", "ثمانية", "تسعة"};
	private static final String[] TENS = {null, "عشرة", "عشرون", "ثلاثون", "اربعون", "خمسون", "ستون", "سبعون", "ثمانون", "تسعون"};
	private static final String[] HUNDREDS = {null, "مائة", "مائتان", "ثلاثمائة", "اربعمائة", "خمسمائة", "ستمائة", "سبعمائة", "ثمانمائة", "تسعمائة"};
	
	private static final String ELEVEN = "أحد عشر";
	private static final String TWELVE = "إثنا عشر";
	private static final String TEN_SUFFIX = "عشر";
	
	private static final String TWO_HUNDRED = "مائتا";
	
	private static final String SINGULAR_THOUSAND = "ألف";
	private static final String DUAL_ACCUSATIVE_GENITIVE_THOUSAND = "ألفا";
	private static final String DUAL_NOMINATIVE_THOUSAND = "ألفان";
	private static final String PLURAL_THOUSAND = "آلاف";
	private static final String SINGULAR_ACCUSATIVE_THOUSAND = "ألفاً";
	
	private static final String SINGULAR_MILLION = "مليون";
	private static final String DUAL_ACCUSATIVE_GENITIVE_MILLION = "مليونا";
	private static final String DUAL_NOMINATIVE_MILLION = "مليونان";
	private static final String PLURAL_MILLION = "ملايين";
	private static final String SINGULAR_ACCUSATIVE_MILLION = "مليوناً";
	
	private static final String SINGULAR_BILLION = "مليار";
	private static final String DUAL_ACCUSATIVE_GENITIVE_BILLION = "مليارا";
	private static final String DUAL_NOMINATIVE_BILLION = "ملياران";
	private static final String PLURAL_BILLION = "مليارات";
	private static final String SINGULAR_ACCUSATIVE_BILLION = "ملياراً";
	
	private static final String SINGULAR_TRILLION = "تريليون";
	private static final String DUAL_ACCUSATIVE_GENITIVE_TRILLION = "تريليونا";
	private static final String DUAL_NOMINATIVE_TRILLION = "تريليونان";
	private static final String PLURAL_TRILLION = "تريليونات";
	private static final String SINGULAR_ACCUSATIVE_TRILLION = "تريليوناً";
	
	private static final String SINGULAR_SUBUNIT = "واحدة";
	private static final String DUAL_SUBUNIT = "هللتان";
	private static final String PLURAL_SUBUNIT = "هللات";
	private static final String SUBUNIT = "هللة";
	
	private static final String[] SUBUNIT_ONES = {"", "احدى", "اثنتان", "ثلاث", "اربع", "خمس", "ست", "سبع", "ثمان", "تسع"};
	
	private static final String SUBUNIT_TEN = "عشر";
	private static final String SUBUNIT_ELEVEN = "احدى";
	private static final String SUBUNIT_TWELVE = "اثنتا";
	
	private static final String ONLY = "فقط";
	private static final String AND_NOTHING_ELSE = "لا غير";
	
	public static String convert(BigDecimal amount) {
		if (!validateAmount(amount)) {
			throw new IllegalArgumentException("Number is out of allowed range. The allowed range is between -999999999999999.99 to 999999999999999.99");
		}
		
		boolean isMinus = (amount.signum() == -1) ? true : false;
		long integerPart = Math.abs(amount.longValue());
		int fractionPart = Math.abs(amount.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).intValue());
		
		String result = "";
		
		result += ONLY + " ";
		
		if (isMinus) {
		    result += MINUS + " ";
		}
		
		if (integerPart == 0) {
    		if (fractionPart >= 1 && fractionPart <= 99) {
    			result += convertFractionPart(fractionPart);
    		} else {
    		    result += ONES[0] + " " + SINGULAR_CURRENCY_NAME;
    		}
		} else {
		    result += convertIntegerPart(integerPart);
    		if (fractionPart >= 1 && fractionPart <= 99) {
    			result += " " + AND + " " + convertFractionPart(fractionPart);
    		}
		}
		
		result += " " + AND_NOTHING_ELSE;
		
		return result;
	}
	
	private static boolean validateAmount(BigDecimal amount) {
	    if (amount.toString().matches("^(\\-)?([0-9]){1,15}((\\.)[0-9]{1," + CURRENCY_SCALE + "})?$")) {
	        return true;
	    }
	    return false;
	}
	
	private static String convertIntegerPart(long integerPart) {
		if (integerPart >= 1 && integerPart <= 9) { //Ones
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
		if (integerPart == 0) { //0
			return ONES[0] + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart == 1) { //1
			return SINGULAR_CURRENCY_NAME + " " + ONES[1];
		} else if (integerPart == 2) { //2
			return DUAL_CURRENCY_NAME;
		} else if (integerPart >= 3 && integerPart <= 9) { //3-9
			return ONES[(int) integerPart] + " " + PLURAL_CURRENCY_NAME;
		}
		return null;
	}
	
	private static String convertTens(long integerPart) {
		if (integerPart == 10) { //10
			return TENS[1] + " " + PLURAL_CURRENCY_NAME;
		} else if (integerPart >= 11 && integerPart <= 99) { //11-99
			return convertRawTens((int) integerPart) + " " + ACCUSATIVE_CURRENCY_NAME;
		}
		return null;
	}
	
	private static String convertHundreds(long integerPart) {
		if (integerPart == 200) {
			return TWO_HUNDRED + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 100 && integerPart <= 900 && integerPart%100 == 0) { //100,200,300,...
			return HUNDREDS[(int) (integerPart/100)] + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 101 && integerPart <= 999) { //101-999
			return HUNDREDS[(int) (integerPart/100)] + " " + AND + " " + convertIntegerPart(integerPart%100);
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
			return SINGULAR_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 1001 && integerPart <= 1999) { //1001-1999
			return SINGULAR_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart == 2000) { //2000
			return DUAL_ACCUSATIVE_GENITIVE_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 2001 && integerPart <= 2999) { //2001-2999
			return DUAL_NOMINATIVE_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart >= 3000 && integerPart <= 9000 && integerPart%1000 == 0) { //3000,4000,5000,...
			return ONES[(int) (integerPart/1000)] + " " + PLURAL_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart == 10000) { //10000
			return TENS[1] + " " + PLURAL_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 3001 && integerPart <= 9999) { //3001-9999
			return ONES[(int) (integerPart/1000)] + " " + PLURAL_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%1000);
		}
		return null;
	}
	
	private static String convertTensThousands(long integerPart) {
		if (integerPart == 10000) { //10000
			return TENS[1] + " " + PLURAL_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 3001 && integerPart <= 9999) { //3001-9999
			return ONES[(int) (integerPart/1000)] + " " + PLURAL_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart == 10000) {
			return TENS[1] + " " + PLURAL_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 10001 && integerPart <= 10999) { //10001-10999
			return TENS[1] + " " + PLURAL_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart == 11000) { //11000
			return ELEVEN + " " + SINGULAR_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart == 12000) { //12000
			return TWELVE + " " + SINGULAR_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 11001 && integerPart <= 11999) { //11001-11999
			return ELEVEN + " " + SINGULAR_ACCUSATIVE_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart >= 12001 && integerPart <= 12999) { //12001-12999
			return TWELVE + " " + SINGULAR_ACCUSATIVE_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart >= 13000 && integerPart <= 19000 && integerPart%1000 == 0) { //13000,14000,15000,...
			return convertRawTens((int) (integerPart/1000)) + " " + SINGULAR_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 13001 && integerPart <= 19999) { //13001-19999
			return convertRawTens((int) (integerPart/1000)) + " " + SINGULAR_ACCUSATIVE_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%1000);
		} else if (integerPart >= 20000 && integerPart <= 90000 && integerPart%10000 == 0) { //20000-90000
			return TENS[(int) (integerPart/1000/10)] + " " + SINGULAR_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 21000 && integerPart <= 99000 && integerPart%1000 == 0) { //21000-99000
			return convertRawTens((int) (integerPart/1000)) + " " + SINGULAR_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 20001 && integerPart <= 99999) { //20001-99999
			return convertRawTens((int) (integerPart/1000)) + " " + SINGULAR_ACCUSATIVE_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%1000);
		}
		return null;
	}
	
	private static String convertHundredsThousands(long integerPart) {
		if (integerPart == 200000) { //200000
			return TWO_HUNDRED + " " + SINGULAR_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 200001 && integerPart <= 200999) { //200001-200999
			return TWO_HUNDRED + " " + SINGULAR_ACCUSATIVE_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%100000);
		} else if (integerPart >= 100000 && integerPart <= 900000 && integerPart%100000 == 0) { //100000,300000,400000,...
			return HUNDREDS[(int) (integerPart/100000)] + " " + SINGULAR_THOUSAND + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 100001 && integerPart <= 900999 && integerPart%100000 < 1000) { //100001-900999
			return HUNDREDS[(int) (integerPart/100000)] + " " + SINGULAR_THOUSAND + " " + AND + " " + convertIntegerPart(integerPart%100000);
		} else if (integerPart >= 101000 && integerPart <= 999000 && (integerPart%100000)%1000 == 0) { //101000-999000
			return HUNDREDS[(int) (integerPart/100000)] + " " + AND + " " + convertIntegerPart(integerPart%100000);
		} else if (integerPart >= 100001 && integerPart <= 999999) { //100001-999999
			return HUNDREDS[(int) (integerPart/100000)] + " " + AND + " " + convertIntegerPart(integerPart%100000);
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
			return SINGULAR_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 1000001 && integerPart <= 1999999) { //1000001-1999999
			return SINGULAR_MILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000);
		} else if (integerPart == 2000000) { //2000000
			return DUAL_ACCUSATIVE_GENITIVE_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 2000001 && integerPart <= 2999999) { //2000001-2999999
			return DUAL_NOMINATIVE_MILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000);
		} else if (integerPart >= 3000000 && integerPart <= 9000000 && integerPart%1000000 == 0) { //3000000-9000000
			return ONES[(int) (integerPart/1000000)] + " " + PLURAL_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 3000001 && integerPart <= 9999999) { //3000001-9999999
			return ONES[(int) (integerPart/1000000)] + " " + PLURAL_MILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000);
		}
		return null;
	}
	
	private static String convertTensMillions(long integerPart) {
		if (integerPart == 10000000) { //10000000
			return TENS[1] + " " + PLURAL_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 10000001 && integerPart <= 10999999) { //10000001-10999999
			return TENS[1] + " " + PLURAL_MILLION + " " + AND + " " + convertIntegerPart(integerPart%10000000);
		} else if (integerPart == 11000000) { //11000000
			return ELEVEN + " " + SINGULAR_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 11000001 && integerPart <= 11999999) { //11000001-11999999
			return ELEVEN + " " + SINGULAR_ACCUSATIVE_MILLION + " " + AND + " " + convertIntegerPart(integerPart%11000000);
		} else if (integerPart == 12000000) {  //12000000
			return TWELVE + " " + SINGULAR_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 12000001 && integerPart <= 12999999) { //12000001-12999999
			return TWELVE + " " + SINGULAR_ACCUSATIVE_MILLION + " " + AND + " " + convertIntegerPart(integerPart%12000000);
		} else if (integerPart >= 13000000 && integerPart <= 19000000 && integerPart%1000000 == 0) { //13000000-19000000
			return convertRawTens((int) (integerPart/1000000)) + " " + SINGULAR_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 13000001 && integerPart <= 19999999) { //13000001-19999999
			return convertRawTens((int) (integerPart/1000000)) + " " + SINGULAR_ACCUSATIVE_MILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000);
		} else if (integerPart >= 20000000 && integerPart <= 99000000 && integerPart%1000000 == 0) { //2000000-9000000
			return convertRawTens((int) (integerPart/1000000)) + " " + SINGULAR_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 20000001 && integerPart <= 99999999) { //20000001-99999999
			return convertRawTens((int) (integerPart/1000000)) + " " + SINGULAR_ACCUSATIVE_MILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000);
		}
		return null;
	}
	
	private static String convertHundredsMillions(long integerPart) {
		if (integerPart == 200000000) { //200000000
			return TWO_HUNDRED + " " + SINGULAR_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 200000001 && integerPart <= 200999999) { //200000001-200999999
			return TWO_HUNDRED + " " + SINGULAR_ACCUSATIVE_MILLION + " " + AND + " " + convertIntegerPart(integerPart%100000000);
		} else if (integerPart >= 100000000 && integerPart <= 900000000 && integerPart%100000000 == 0) { //100000000,300000000,400000000,...
			return HUNDREDS[(int) (integerPart/100000000)] + " " + SINGULAR_MILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 100000001 && integerPart <= 900999999 && integerPart%100000000 < 1000000) { //100000001-900999999
			return HUNDREDS[(int) (integerPart/100000000)] + " " + SINGULAR_MILLION + " " + AND + " " + convertIntegerPart(integerPart%100000000);
		} else if (integerPart >= 101000000 && integerPart <= 999000000 && (integerPart%100000000)%1000000 == 0) { //101000000-999000000
			return HUNDREDS[(int) (integerPart/100000000)] + " " + AND + " " + convertIntegerPart(integerPart%100000000);
		} else if (integerPart >= 100000001 && integerPart <= 999999999) { //100000001-999999999
			return HUNDREDS[(int) (integerPart/100000000)] + " " + AND + " " + convertIntegerPart(integerPart%100000000);
		}
		return null;
	}
	
	private static String convertBillions(long integerPart) {
		if (integerPart >= 1000000000 && integerPart <= 9999999999L) {
			return convertOnesBillions(integerPart);
		} else if (integerPart >= 10000000000L && integerPart <= 99999999999L) {
			return convertTensBillions(integerPart);
		} else if (integerPart >= 100000000000L && integerPart <= 999999999999L) {
			return convertHundredsBillions(integerPart);
		}
		return null;
	}
	
	private static String convertOnesBillions(long integerPart) {
		if (integerPart == 1000000000) { //1000000000
			return SINGULAR_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 1000000001 && integerPart <= 1999999999) {
			return SINGULAR_BILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000000);
		} else if (integerPart == 2000000000) {
			return DUAL_ACCUSATIVE_GENITIVE_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 2000000001 && integerPart <= 2999999999L) {
			return DUAL_NOMINATIVE_BILLION + " " + AND + " " + convertIntegerPart(integerPart%2000000000);
		} else if (integerPart >= 3000000000L && integerPart <= 9000000000L && integerPart%1000000000 == 0) {
			return ONES[(int) (integerPart/1000000000)] + " " + PLURAL_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 3000000001L && integerPart <= 9999999999L) {
			return ONES[(int) (integerPart/1000000000)] + " " + PLURAL_BILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000000);
		}
		return null;
	}
	
	private static String convertTensBillions(long integerPart) {
		if (integerPart == 10000000000L) {
			return TENS[1] + " " + PLURAL_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 10000000001L && integerPart <= 10999999999L) {
			return TENS[1] + " " + PLURAL_BILLION + " " + AND + " " + convertIntegerPart(integerPart%10000000000L);
		} else if (integerPart == 11000000000L) {
			return ELEVEN + " " + SINGULAR_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 11000000001L && integerPart <= 11999999999L) {
			return ELEVEN + " " + SINGULAR_ACCUSATIVE_BILLION + " " + AND + " " + convertIntegerPart(integerPart%11000000000L);
		} else if (integerPart == 12000000000L) {
			return TWELVE + " " + SINGULAR_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 12000000001L && integerPart <= 12999999999L) {
			return TWELVE + " " + SINGULAR_ACCUSATIVE_BILLION + " " + AND + " " + convertIntegerPart(integerPart%12000000000L);
		} else if (integerPart >= 13000000000L && integerPart <= 19000000000L && integerPart%1000000000L == 0) {
			return convertRawTens((int) (integerPart/1000000000)) + " " + SINGULAR_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 13000000001L && integerPart <= 19999999999L) {
			return convertRawTens((int) (integerPart/1000000000)) + " " + SINGULAR_ACCUSATIVE_BILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000000);
		} else if (integerPart >= 20000000000L && integerPart <= 99000000000L && integerPart%1000000000L == 0) {
			return convertRawTens((int) (integerPart/1000000000)) + " " + SINGULAR_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 20000000001L && integerPart <= 99999999999L) {
			return convertRawTens((int) (integerPart/1000000000)) + " " + SINGULAR_ACCUSATIVE_BILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000000);
		}
		return null;
	}
	
	private static String convertHundredsBillions(long integerPart) {
		if (integerPart == 200000000000L) {
			return TWO_HUNDRED + " " + SINGULAR_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 200000000001L && integerPart <= 200999999999L) {
			return TWO_HUNDRED + " " + SINGULAR_ACCUSATIVE_BILLION + " " + AND + " " + convertIntegerPart(integerPart%100000000000L);
		} else if (integerPart >= 100000000000L && integerPart <= 900000000000L && integerPart%100000000000L == 0) {
			return HUNDREDS[(int) (integerPart/100000000000L)] + " " + SINGULAR_BILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 100000000001L && integerPart <= 900999999999L && integerPart%100000000000L < 1000000000) {
			return HUNDREDS[(int) (integerPart/100000000000L)] + " " + SINGULAR_BILLION + " " + AND + " " + convertIntegerPart(integerPart%100000000000L);
		} else if (integerPart >= 101000000000L && integerPart <= 999000000000L && (integerPart%100000000000L)%1000000000 == 0) {
			return HUNDREDS[(int) (integerPart/100000000000L)] + " " + AND + " " + convertIntegerPart(integerPart%100000000000L);
		} else if (integerPart >= 100000000001L && integerPart <= 999999999999L) {
			return HUNDREDS[(int) (integerPart/100000000000L)] + " " + AND + " " + convertIntegerPart(integerPart%100000000000L);
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
			return SINGULAR_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 1000000000001L && integerPart <= 1999999999999L) {
			return SINGULAR_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000000000L);
		} else if (integerPart == 2000000000000L) {
			return DUAL_ACCUSATIVE_GENITIVE_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 2000000000001L && integerPart <= 2999999999999L) {
			return DUAL_NOMINATIVE_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%2000000000000L);
		} else if (integerPart >= 3000000000000L && integerPart <= 9000000000000L && integerPart%1000000000000L == 0) {
			return ONES[(int) (integerPart/1000000000000L)] + " " + PLURAL_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 3000000000001L && integerPart <= 9999999999999L) {
			return ONES[(int) (integerPart/1000000000000L)] + " " + PLURAL_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000000000L);
		}
		return null;
	}
	
	private static String convertTensTrillions(long integerPart) {
		if (integerPart == 10000000000000L) {
			return TENS[1] + " " + PLURAL_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 10000000000001L && integerPart <= 10999999999999L) {
			return TENS[1] + " " + PLURAL_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%10000000000000L);
		} else if (integerPart == 11000000000000L) {
			return ELEVEN + " " + SINGULAR_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 11000000000001L && integerPart <= 11999999999999L) {
			return ELEVEN + " " + SINGULAR_ACCUSATIVE_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%11000000000000L);
		} else if (integerPart == 12000000000000L) {
			return TWELVE + " " + SINGULAR_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 12000000000001L && integerPart <= 12999999999999L) {
			return TWELVE + " " + SINGULAR_ACCUSATIVE_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%12000000000000L);
		} else if (integerPart >= 13000000000000L && integerPart <= 19000000000000L && integerPart%1000000000000L == 0) {
			return convertRawTens((int) (integerPart/1000000000000L)) + " " + SINGULAR_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 13000000000001L && integerPart <= 19999999999999L) {
			return convertRawTens((int) (integerPart/1000000000000L)) + " " + SINGULAR_ACCUSATIVE_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000000000L);
		} else if (integerPart >= 20000000000000L && integerPart <= 99000000000000L && integerPart%1000000000000L == 0) {
			return convertRawTens((int) (integerPart/1000000000000L)) + " " + SINGULAR_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 20000000000001L && integerPart <= 99999999999999L) {
			return convertRawTens((int) (integerPart/1000000000000L)) + " " + SINGULAR_ACCUSATIVE_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%1000000000000L);
		}
		return null;
	}
	
	private static String convertHundredsTrillions(long integerPart) {
		if (integerPart == 200000000000000L) {
			return TWO_HUNDRED + " " + SINGULAR_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 200000000000001L && integerPart <= 200999999999999L) {
			return TWO_HUNDRED + " " + SINGULAR_ACCUSATIVE_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%100000000000L);
		} else if (integerPart >= 100000000000000L && integerPart <= 900000000000000L && integerPart%100000000000000L == 0) {
			return HUNDREDS[(int) (integerPart/100000000000000L)] + " " + SINGULAR_TRILLION + " " + SINGULAR_CURRENCY_NAME;
		} else if (integerPart >= 100000000000001L && integerPart <= 900999999999999L && integerPart%100000000000000L < 1000000000000L) {
			return HUNDREDS[(int) (integerPart/100000000000000L)] + " " + SINGULAR_TRILLION + " " + AND + " " + convertIntegerPart(integerPart%100000000000000L);
		} else if (integerPart >= 101000000000000L && integerPart <= 999000000000000L && (integerPart%100000000000000L)%1000000000000L == 0) {
			return HUNDREDS[(int) (integerPart/100000000000000L)] + " " + AND + " " + convertIntegerPart(integerPart%100000000000000L);
		} else if (integerPart >= 100000000000001L && integerPart <= 999999999999999L) {
			return HUNDREDS[(int) (integerPart/100000000000000L)] + " " + AND + " " + convertIntegerPart(integerPart%100000000000000L);
		}
		return null;
	}
	
	private static String convertRawTens(int number) {
		if (number == 11) {
			return ELEVEN;
		} else if (number == 12) {
			return TWELVE;
		} else if (number >= 13 && number <= 19) {
			return ONES[number%10] + " " + TEN_SUFFIX;
		} else if (number >= 20 && number <= 90 && number%10 == 0) {
			return TENS[number/10];
		} else if (number >= 21 && number <= 99) {
			return ONES[number%10] + " " + AND + " " + TENS[number/10];
		}
		return null;
	}
	
	private static String convertFractionPart(int fractionPart) {
		if (fractionPart == 1) {
			return SUBUNIT + " " + SINGULAR_SUBUNIT;
		} else if (fractionPart == 2) {
			return DUAL_SUBUNIT + " " + SUBUNIT_ONES[2];
		} else if (fractionPart >= 3 && fractionPart <= 9) {
			return SUBUNIT_ONES[fractionPart] + " " + PLURAL_SUBUNIT;
		} else if (fractionPart == 10) {
			return SUBUNIT_TEN + " " + PLURAL_SUBUNIT;
		} else if (fractionPart == 11) {
			return SUBUNIT_ELEVEN + " " + TENS[1] + " " + SUBUNIT;
		} else if (fractionPart == 12) {
			return SUBUNIT_TWELVE + " " + TENS[1] + " " + SUBUNIT;
		} else if (fractionPart >= 13 && fractionPart <= 19) {
			return SUBUNIT_ONES[fractionPart%10] + " " + TENS[1] + " " + SUBUNIT;
		} else if (fractionPart >= 20 && fractionPart <= 90 && fractionPart%10 == 0) {
			return TENS[fractionPart/10] + " " + SUBUNIT;
		} else if (fractionPart >= 21 && fractionPart <= 99) {
			return SUBUNIT_ONES[fractionPart%10] + " " + AND + " " + TENS[fractionPart/10] + " " + SUBUNIT;
		}
		return null;
	}

}
