# jdbtool
Export tool for SQL Databases (Maria, PostgreSQL, & SQLite) via JSON

## Usage Instructions
```
usage: jdbtool [-h] [--out OUT] [--dbtype DBTYPE] [--dbuser DBUSER]
               [--dbpass DBPASS] [--db DB]

named arguments:
  -h, --help             show this help message and exit
  --out OUT, -o OUT      Output JSON File
  --dbtype DBTYPE, -t DBTYPE
                         MariaDB, PostgreSQL, or SQLite
  --dbuser DBUSER, -u DBUSER
                         DB Username (MariaDB, PostgreSQL)
  --dbpass DBPASS, -p DBPASS
                         DB Password, default to none (MariaDB, PostgreSQL)
  --db DB, -d DB         DB

-I Like Turtles-
```

## Release Notes
Useful tool for importing data from SQL Databases into Elastic Search and other similar needs.

## License
 
Copyright (C) 2021 William Welna (wwelna@occultusterra.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
