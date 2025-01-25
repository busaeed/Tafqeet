# Tafqeet
A Java utility class to convert a number to its word representation in Arabic as a Saudi currency string along with the fraction part in compliance with the grammar rules of the language.

It's very easy to use all you have to do is to include the class into your project and then you can invoke the method like the following:

String tafqeet = Tafqeet.convert(new BigDecimal("32786.95"));

System.out.println(tafqeet);

The output will be:

فقط اثنان و ثلاثون ألفاً و سبعمائة و ستة و ثمانون ريالاً سعودياً و خمس و تسعون هللة لا غير
