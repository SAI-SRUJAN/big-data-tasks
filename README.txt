***BASH***

To run the Bash Scripts:
	- We first download the dataset using the wget command as mentioned in the file.
 	- And then for the commands mentioned in the report we can directly run it the terminal.
	- To run the script files(mongo-insert.sh, sql-insert.sh, update-country.sh)
		- We have to give the execution permission for the user for these files
			chmod u+x file-name.sh  (file-name as mentioned above)
		- We can then execute it using the command ./file-name.sh (file-name as mentioned above)
	- To view the ouput we use less command : less file-name 
		-The file name where the output is saved is mentioned in the pdf


***HADOOP***

To run the Map Reduce Scripts:
	- We first download the dataset using the wget command as mentioned in the file.
	- We then upload the dataset into the hdfs using the command:
		hdfs dfs -copyFromLocal hadoop.csv /dataset	
	- We then need to compile the scripts first using the commands
		- export HADOOP_CLASSPATH=/usr/lib/jvm/java-1.8.0-openjdk-amd64/lib/tools.jar
		- hadoop com.sun.tools.javac.Main file-name.java
	- Create a jar
		jar cf wc.jar file-name*.class
	- Then execute the scripts(HarbourRoutes.java, HarbourRoutes2.java, HarbourRoutes3.java, HarbourRoutes4.java):
		hadoop jar wc.jar file-name /dataset /output (file-name as mentioned above e.g:HarbourRoutes4) 
		- To view the ouput:
			hdfs dfs -cat /output/part-r-00000
	- To execute the script(HarbourRoutes5.java) we use this command as it has two map reduce operations and we save the output to two files:
		hadoop jar wc.jar HarbourRoutes5.java /dataset /output / output1
		- To view the ouput:
			hdfs dfs -cat /output/part-r-00000
			hdfs dfs -cat /output1/part-r-00000


***SPARK***

- We first download the dataset using the wget command as mentioned in the file.
- Save the commands in a file with .scala extension
- Run the scala file with :load (path to file) and all the commands will be executed in order
- The commands are mentioned in spark.txt file attached in folder.

***GRAPHX***

- We first download the dataset using the wget command as mentioned in the file.
- Save the commands in a file with .scala extension
- Run the scala file with :load (path to file) and all the commands will be executed in order
- The commands are mentioned in graphx.txt file attached in folder.


