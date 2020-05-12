package org.loboevolution.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.loboevolution.common.Strings;

/**
 * <p>InputStore class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class InputStore {
	
	/**
	 * <p>autocomplete.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> autocomplete(String value) {
        List<String> autoList = new ArrayList<String>();
    	try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
			PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INPUT)) {
			pstmt.setString(1, "%"+value+"%");
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					autoList.add(rs.getString(1));
				}
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return autoList;
    }	

	/**
	 * <p>insertLogin.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 * @param type a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @param navigationEnabled a boolean.
	 */
	public static void insertLogin(String id, String name, String type, String value, boolean navigationEnabled) {
		if (navigationEnabled) {
			try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
					PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_INPUT)) {
				String nameValue = type;
				if (!Strings.isBlank(id)) {
					nameValue = id;
				} else if (!Strings.isBlank(name)) {
					nameValue = name;
				}

				pstmt.setString(1, nameValue);
				pstmt.setString(2, value);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <p>deleteInput.</p>
	 */
	public static void deleteInput() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_INPUT)) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
