package metricsPackage;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Comparator;
/**
 * Utility comparator for generic type T, useful for sorting algorithms.
 * <br> This allows to compare (and, for instance, to sort) numerically (integer, float, etc.) or alphabetically generic types. 
 * <br> Generalisation is thus guaranteed.
 *
 * @param <T> Generic type that is compared.
 */
public class TComparator<T> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		// TODO Auto-generated method stub
		if(isNum(o1) && isNum(o2)) {
			Number _o1, _o2;
			try {
				_o1 = NumberFormat.getInstance().parse(o1.toString());
				_o2 = NumberFormat.getInstance().parse(o2.toString());
				if (_o1==_o2) return 0;
				else if (_o1.doubleValue()>_o2.doubleValue()) return 1;
				else return -1;
			} catch (ParseException e) {
//				e.printStackTrace();
				return -1;
			}
		}
		else
			return o1.toString().compareTo(o2.toString());
	}
	
	/**
	 * Assess if a given element is numeric (integer, float, ...).
	 * @param elem Generic type T.
	 * @return True if <b>elem</b> is a number, otherwise False.
	 */
	private boolean isNum(T elem) {
		if(elem.toString().matches("-?\\d+(\\.\\d+)?")) return true;
		return false;
	}

}

