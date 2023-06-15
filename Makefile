# ASerever.javaとClient.javaとfunc.javaをコンパイルする
all:
	javac AServer.java
	javac Client.java
	javac func.java
clean:
	rm -f *.class
	rm -f *_log.txt
fclean:
	rm -f *.class
	rm -f *_log.txt
	rm -f *.db