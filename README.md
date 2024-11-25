XML Parser Program
=================

Description:
------------
This XML Parser program validates XML documents for proper tag formatting and nesting. 
It checks for matching opening and closing tags, proper nesting order, and reports any 
formatting errors found in the XML document.

System Requirements:
------------------
- Java Runtime Environment (JRE) version 8 or higher
- Command-line interface/terminal

Installation:
------------
1. Ensure Java is installed on your system:
   - Open a terminal/command prompt
   - Type: java -version

2. Place the Parser.jar file in any directory of your choice

Usage Instructions:
-----------------
1. Open a terminal/command prompt

2. Navigate to the directory containing Parser.jar:
   Windows: cd path\to\Parser.jar
   macOS/Linux: cd path/to/Parser.jar

3. Run the parser with your XML file:
   java -jar Parser.jar your_xml_file.xml

   Example:
   java -jar Parser.jar test.xml

4. The program will analyze the XML file and output results to the console:
   - If the XML is well-formed, it will display: "XML document is well-formed."
   - If errors are found, it will list each error with its line number and description

Example Output:
-------------
Well-formed XML:
$ java -jar Parser.jar good.xml
XML document is well-formed.

XML with errors:
$ java -jar Parser.jar bad.xml
XML document contains errors:
Line 5: Unmatched closing tag: </wrongTag>
Line 8: Unclosed tag: <openTag>


