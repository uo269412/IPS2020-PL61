package sprint1.business.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.portable.ApplicationException;

public class Util {
	
	/** 
	 * Convierte fecha repesentada como un string iso a fecha java (para conversion de entradas de tipo fecha)
	 */
	public static Date isoStringToDate(String isoDateString) {
		try {
		return new SimpleDateFormat("yyyy-MM-dd").parse(isoDateString);
		} catch (ParseException e) {
			throw new RuntimeException("Formato ISO incorrecto para fecha: "+isoDateString);
		}
	}
	/** 
	 * Convierte fecha java a un string formato iso (para display o uso en sql) 
	 */
	public static String dateToIsoString(Date javaDate) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(javaDate);
	}

}
