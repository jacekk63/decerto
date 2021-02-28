# decerto

Simply program that connecting data from different sources and types, using different aggregation methods and strategy how to connect two elements.

Used Java, Spring and Reflections.
Verified compatability with Java 1.8 and 11.

Upload sources from GitHub using:

	git clone https://github.com/jacekk63/decerto.git

To compile open Command Line window (preferably GitBash), go to project root foder.
Then compile sources using Maven command:

	mvn clean install

Result jar is placed in target folder.

	cd target/

there should be file like that:
	decerto-0.0.1-SNAPSHOT.jar

To execute jar use commad line like that:

	java -jar decerto-0.0.1-SNAPSHOT.jar min=1 REST bleble elements=20 max=30 dataSources=REST,JAVA,EMPTY,RANDOMLEN strategy=INTSUM method=BASE_METHOD

or that:

	java -jar decerto-0.0.1-SNAPSHOT.jar min=5 REST bleble elements=10 max=20 dataSources=REST,JAVA,EMPTY,RANDOMLEN,STRING strategy=INTSUM,INTMULTIPLY

Program excecuted without arguments will display only introduction.

	java -jar decerto-0.0.1-SNAPSHOT.jar

As there is defined only one aggregation method BASE_METHOD, it can be ommited in arguments.
Data Sources and Strategy have to be provided in arguments.
Program can use several Aggregation Methods, Data Sources and Strategies. All of them must be defined in command-line arguments like shown above.
Arguments order is not important.

Methods, Strategies and Data Sources are instantiated by Reflection (each level in system dependent order as it wasn't specific in requirements).
Program arguments are separated by Space character. Unknown arguments will be ignored.
Program arguments will be passed to Methods, Strategies and Data Sources.
Each Method, Strategy and Data Source can use own specific command-line parameters.
In future is possible to create new Aggregation Mathod, Strategy and Data Source without modify program itself.
Each Aggregation Mathod class implements MethodIfc and must be placed in decerto.methods.dynamic package.
Each Strategy class implements ConnectStrategyIfc and must be placed in decerto.strategy.dynamic package.
Each Data Source class implements IfcDataSource and must be placed in decerto.datasources.dynamic package.
Note IfcDataSource, ConnectStrategyIfc and MethodIfc extends CommonDynamicIfc.

Example program arguments:

	java -jar decerto-0.0.1-SNAPSHOT.jar min=5 max=20 elements=10 dataSources=REST,JAVA,FILE,RANDOMLEN strategy=INTSUM method=BASE_METHOD

This argument set will generate data from Data Sources with identifiers: REST,JAVA,FILE,RANDOMLEN
All data will be connected by strategy INTSUM (elements from different Data Sources will be summed if possible).
All data will be connected by BASE_METHOD.
Data Sources that generates random int values (now JavaDataSource and RestDataSource) will produce values from 5 to 20 end returns 10 elements (values from command-line arguments).

RandomLengthDataSource (identifier RANDOMLEN) will produce values from 1 to 10 end returns up to 30 elements (hardcoded).
StringJavaDataSource (identifier STRING) will produce Strings each represents letter and will return up to 10 elements (hardcoded).
EmptyDataSource (identifier EMPTY) will produce empty Data Source.

Defined following strategies:
SUM that will connect two values by string concatenation,
INTSUM that will sum two values as int values if possible,
INTMULTIPLY that will multiply two values as int values if possible.

Defined following method:
BASE_METHOD that will produce one Data Result,
each element from Data Source will be connected to corresponding element from different Data Sources,
if there is more than two Data Sources then elements from each will be connected to corresponding element from previous result.

Program display results in command line console from which it was executed.

Example:

	java -jar decerto-0.0.1-SNAPSHOT.jar min=5 REST bleble elements=10 max=20 dataSources=REST,JAVA,EMPTY,RANDOMLEN,STRING strategy=INTMULTIPLY
  
can produce output like that:

###################################################################################################
######Applying BASE_METHOD method######

###Applying INTMULTIPLY strategy###

Connecting data from INTERNAL and RANDOMLEN data sources.
First data source INTERNAL
Second data source RANDOMLEN       9 8 5 3 3 8 3 7 7
Result data:                       9 8 5 3 3 8 3 7 7

Connecting data from INTERNAL and JAVA data sources.
First data source INTERNAL         9 8 5 3 3 8 3 7 7
Second data source JAVA            17 12 7 15 18 10 16 17 19 8
Result data:                       153 96 35 45 54 80 48 119 133 8

Connecting data from INTERNAL and REST data sources.
First data source INTERNAL         153 96 35 45 54 80 48 119 133 8
Second data source REST            8 10 15 13 8 9 14 7 14 17
Result data:                       1224 960 525 585 432 720 672 833 1862 136

Connecting data from INTERNAL and STRING data sources.
First data source INTERNAL         1224 960 525 585 432 720 672 833 1862 136
Second data source STRING          O C
Result data:                       1224O 960C 525 585 432 720 672 833 1862 136

Connecting data from INTERNAL and EMPTY data sources.
First data source INTERNAL         1224O 960C 525 585 432 720 672 833 1862 136
Second data source EMPTY
Result data:                       1224O 960C 525 585 432 720 672 833 1862 136

Program finished.
###################################################################################################

As we can see Base Agregation method is used as well as INTMULTIPLY strategy.
Afer each Data Source connection program displays partially results.
Pay attention that elements that are integers are connected as applied strategy defines. In this case are multiply. 
Note that one of the Data Source is STRING. This Data Source provide letters. As we cannot multiply int with letters such elements are simply concatenated.
There is one Data Source that produce no elements. Program works as designed ignoring such Data Source.
So far math operations are executed on int elements. Be aware to provide such data values and operation that will produce results fits int limit. 
Program can connect Data Sources with different length.

