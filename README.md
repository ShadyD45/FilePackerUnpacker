# File Packer and Unpacker
  - This project serves as a tool to pack multiple files into a single file.
  - The generated packed file can then later be used to retrieve original files. 
  	Both of the above tasks can be called as packing and unpacking activity.

# About
  - This tool is used to perform packing and unpacking activity for multiple types of files.
  - In case of Packing activity we maintain one file which contains metadata and data of
  	multiple files from specified directory.
  - In case of Unpacking activity we extract all data from packed files and according to its
    metadata we retreive all the original files.
  - During the packing process all the data from files is first encrypted and then packed together
    along with it's checksum to ensure that data is untampered and another unauthorized person can't
	access it.
  - While unpacking the tool first checks if the checksum is correct and the file is untampered.
  	By using the metadata of packed files, it retrieves the original files with their data intact. 

# Build the project:
  1) First clone the repository on your system.
  2) Make sure you have Java installed. It will run on most of the Java versions, Earlier or Latest.
  	 The tool was developed using Java(TM) SE Runtime Environment (build 13.0.1+9).  
  3) Open terminal in the project directory and compile the Main.java file using below command. 
 	 ```
	 	javac Main.java
	 ```
  4) Once you compile the Main.java file, Java compiler will automatically compile all other needed classes in the package.
  5) Run the tool using using below command.
  	 ```
	 	java Main
	 ```
