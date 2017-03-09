package uianimationdemo.com.example.daysnews.utils;


import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


public class SystemUtils {
	private static SystemUtils systemUtils;
	private Context context;
	private TelephonyManager telManager;
	private ConnectivityManager connManager;
	private LocationManager locationManager;
	private String position;

	private SystemUtils(Context context) {
		this.context = context;
		telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		locationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}

	public static SystemUtils getInstance(Context context) {
		if (systemUtils == null) {
			systemUtils = new SystemUtils(context);
		}
		return systemUtils;
	}
	/**判断网络是否连接*/
	public boolean isNetConn() {
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}
	/**获取sim卡的类型*/
	public String simType(){
		String simOperator = telManager.getSimOperator();
		String type = "";
		if ("46000".equals(simOperator)) {
			type = "移动";
		} else if ("46002".equals(simOperator)) {
			type = "移动";
		} else if ("46001".equals(simOperator)) {
			type = "联通";
		} else if ("46003".equals(simOperator)) {
			type = "电信";
		}
		return type;
	}

	/**
	 * 获取手机IMEI 号码
	 * @return IMEI
	 */
	public String getIMEI(){
		return telManager.getDeviceId();
	}

	public String getPosition(){
		return this.position;
	}
	
	/**
	 * 获取手机的IMEI值ֵ
	 */
	
	public static String getIMEI(Context context){
		TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
}
