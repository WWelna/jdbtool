/* Copyright (C) 2021 William Welna (wwelna@occultusterra.com)
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/

package com.occultusterra.jdbtool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class SQLDriver {
	
	private String dbuser;
	private String dbpass;
	private String db;
	private String sqltype;
	private String esc="";
	
	private Connection c;
	
	public SQLDriver(String sqltype, String user, String pass, String database) throws SQLException {
		this.dbuser = user;
		this.dbpass = pass;
		this.db = database;
		this.sqltype = sqltype;
		this.c = DriverManager.getConnection("jdbc:"+this.sqltype+"://localhost/"+this.db, this.dbuser, this.dbpass);
		if(sqltype.equals("postgresql"))
			this.esc = "\"";
		else if(sqltype.equals("mysql"))
			this.esc = "`";
	}
	
	public List<String> mapTables() throws SQLException {
		final String[] TYPES = {"TABLE"};
		List<String> ret = new ArrayList<>();
		ResultSet rs = this.c.getMetaData().getTables(null, null, "%", TYPES);
		while(rs.next())
			ret.add(rs.getString("TABLE_NAME"));
		rs.close();
		return ret;
	}
	
	public List<JSONObject> mapSQL(String table) throws SQLException {
		List<JSONObject> ret = new ArrayList<>();
		PreparedStatement stmt = this.c.prepareStatement("SELECT * FROM "+this.esc+table+this.esc);
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsmeta = rs.getMetaData();
		int columns = rsmeta.getColumnCount();
		while(rs.next()) {
			JSONObject j = new JSONObject();
			for(int x=1; x <= columns; ++x)
				j.put(rsmeta.getColumnName(x), rs.getObject(x));
			ret.add(j);
		}
		stmt.close();
		return ret;
	}
	
	public void mapSQL2File(String filename, String table) throws SQLException, IOException {
		File h = new File(filename);
		PreparedStatement stmt = this.c.prepareStatement("SELECT * FROM "+this.esc+table+this.esc);
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData rsmeta = rs.getMetaData();
		int columns = rsmeta.getColumnCount();
		try(FileOutputStream f = new FileOutputStream(h, true)) {
			while(rs.next()) {
				JSONObject j = new JSONObject();
				for(int x=1; x <= columns; ++x)
					j.put(rsmeta.getColumnName(x), rs.getObject(x));
				j.put("__DBEXPORT__", table);
				String s = j.toString()+"\n";
				f.write(s.getBytes());
			}
		}
		stmt.close();
	}
	
	public void SQL2File(String filename) throws SQLException, IOException {
		for(String e:mapTables()) {
    		System.out.println("DOING -> "+e);
    		mapSQL2File(filename, e);
    	}
	}
	
	public void close() throws SQLException {
		this.c.close();
	}
}
