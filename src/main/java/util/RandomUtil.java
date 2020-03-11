package util;


/**
 * 
 *@class_name：RandomUtil
 *@comments:生成六位随机字符串工具类
 *@param:
 *@return: 
 *@author:冯书逸
 *@createtime:2019年9月6日
 */

public class RandomUtil {
	public static void main(String[] args) {
		// 控制台打印
		System.out.println(RandomUtil.getRandom());
	}

	//  生成6位数随机验证码
	public static String getRandom() {
		String[] letters = new String[] {
				"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m",
				"A","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M",
				"0","1","2","3","4","5","6","7","8","9"};
		String code ="";
		for (int i = 0; i < 6; i++) {
			code = code + letters[(int)Math.floor(Math.random()*letters.length)];
		}
		return code;
	}
}
