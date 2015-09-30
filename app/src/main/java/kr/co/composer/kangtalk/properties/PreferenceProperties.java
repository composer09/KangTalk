package kr.co.composer.kangtalk.properties;


public class PreferenceProperties {
	private static String ip = "10.0.12.120";

	public static final String IP_ADDRESS_SERVER = "http://"+ip+":50000";
	public static final String IP_ADDRESS_SOCKET = "http://"+ip+":50001";
//	public static final String IP_ADDRESS_SERVER = "http://192.168.219.124:50000";
//	public static final String IP_ADDRESS_SOCKET = "http://192.168.219.124:50001";
	public static final String REMEMBER_ME = "auto_login_cookie";
	public static final String SESSION_ID = "login_session_id";
}