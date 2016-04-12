
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.List;

public class AppTicketService {

	public static final String TICKET_KEY = "BXTicketKey123!@#";

	public static void main(String[] args) {
	/*	try {
			System.out.println(getCode("0123699170367145986394629201"));
System.out.println(DesT.decrypt(URLDecoder.decode("WGpXEGZs6hL4YC8UV6JK3Y%2Fq564lf2yqniswqgEl41Y%3D", "utf-8"),TICKET_KEY));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		File file = new File("123456.txt");
		try {
			int index = 0;
			BufferedReader dr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String name = null;
			while ((name = dr.readLine()) != null) {
				name = name.trim();
				String content = getCode(name);
				QRCodeUtil.main(name+".png", content);
				System.out.println(index++);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getCode(String name) throws Exception {
		String encrypt = DesT.encrypt(name, TICKET_KEY);
		String urlStr = URLEncoder.encode(encrypt, "utf-8");
		return urlStr;
	}

}
