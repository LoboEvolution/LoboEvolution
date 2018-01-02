package org.loboevolution.html.test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class Header {

	public static void main(String[] args) throws IOException {
		URL obj = new URL("http://mkyong.com");
		URLConnection conn = obj.openConnection();

		// get all headers
		Map<String, List<String>> map = conn.getHeaderFields();
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			// if("Content-Type".equals(entry.getKey())){
			for (int i = 0; i < entry.getValue().size(); i++) {
				System.out.println(entry.getValue().get(i));
			}
			// }
		}
	}

}
