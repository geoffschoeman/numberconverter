# Number Converter

A utility for converting numbers into English text.

## Getting Started

### Prerequisites
* Git
* Java 8 or higher
* Maven 3.5 or higher

### Quick Start

##### Clone the project
```
git clone http://github.com/geoffschoeman/numberconverter.git
```
##### Build
```
cd NumberConverter
mvn package
```
##### Run
```  
java -jar target\numberconverter-1.0.0-SNAPSHOT.war
```  

##### How to use
There are three ways to use the number conversion tool:  

##### 1. Webpage  
Open a web browser and navigate to http://localhost:8080/ 

##### 2. Web request  
Use curl or a similar command line HTTP utility  
`curl http://localhost:8080/convert/[Number to convert]`  

Example:
```
http://localhost:8080/convert/567894
Five hundred and sixty seven thousand eight hundred and ninety four
```

##### 3. Startup arguments  
Pass one or more numbers as startup parameters. The converted values will print to the logs.  

Example:
```
java -jar target\numberconverter-1.0.0-SNAPSHOT.war 1234567 89101112
: One million two hundred and thirty four thousand five hundred and sixty seven
: Eighty nine million one hundred and one thousand one hundred and twelve
```

