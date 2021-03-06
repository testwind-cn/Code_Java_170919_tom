/**
 * 
 */
package com.wj.fin.wjutil;

import java.math.BigDecimal;

/**
 * @author DEV
 *
 */
public class Tools {
	public static double round_half_up(double data, int digit) {
		BigDecimal bg = new BigDecimal(data);
        double new_round = bg.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
		return new_round;
	}
	public static double round_up(double data, int digit) {
		BigDecimal bg = new BigDecimal(data);
        double new_round = bg.setScale(digit, BigDecimal.ROUND_UP).doubleValue();
		return new_round;
	}

}
