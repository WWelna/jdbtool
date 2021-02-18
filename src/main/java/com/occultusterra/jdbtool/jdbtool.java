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

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class jdbtool {
	
    public static void main( String[] args ) throws Exception {
    	ArgumentParser parser = ArgumentParsers.newFor("jdbtool").build().defaultHelp(true).epilog("-I Like Turtles-");
    	parser.addArgument("--out", "-o").help("Output JSON File");
    	parser.addArgument("--dbtype", "-t").help("MariaDB, PostgreSQL, or SQLite");
    	parser.addArgument("--dbuser", "-u").help("DB Username (MariaDB, PostgreSQL)");
    	parser.addArgument("--dbpass", "-p").help("DB Password, default to none (MariaDB, PostgreSQL)");
    	parser.addArgument("--db", "-d").help("DB");
    	try {
    		Namespace n = parser.parseArgs(args);
    		if(n.get("dbtype") == null) {
    			parser.printHelp();
    			System.err.println("\nMissing Database Type");
    			System.exit(-1);
    		}
    		if(n.get("dbtype").equals("MariaDB")) {
    			if(n.get("dbuser") == null || n.get("db") == null || n.get("out") == null) {
    				parser.printHelp();
    				System.err.println("\nMissing Arguments");
    				System.exit(-1);
    			}
    			String pass = "";
    			if(n.get("dbpass") != null)
    				pass = n.get("dbpass");
    			SQLDriver db = new SQLDriver("mysql", n.get("dbuser"), pass, n.get("db"));
    			db.SQL2File(n.get("out"));
    			db.close();
    		} else if(n.get("dbtype").equals("PostgreSQL")) {
    			if(n.get("dbuser") == null || n.get("db") == null || n.get("out") == null) {
    				parser.printHelp();
    				System.err.println("\nMissing Arguments");
    				System.exit(-1);
    			}
    			String pass = "";
    			if(n.get("dbpass") != null)
    				pass = n.get("dbpass");
    			SQLDriver db = new SQLDriver("postgresql", n.get("dbuser"), pass, n.get("db"));
    			db.SQL2File(n.get("out"));
    			db.close();
    			
    		} else if(n.get("dbtype").equals("SQLite")) {
    			if(n.get("db") == null || n.get("out") == null) {
    				parser.printHelp();
    				System.err.println("\nMissing Arguments");
    				System.exit(-1);
    			}
    			SQLiteDriver db = new SQLiteDriver(n.getString("db"));
    			db.SQL2File(n.get("out"));
    			db.close();
    		}
    	} catch (ArgumentParserException e) {
    		parser.handleError(e);
    		System.exit(-1);
    	}
    }
}
