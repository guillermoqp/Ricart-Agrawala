Ricart-Agrawala
===============

Aplicación del algoritmo de exclusión mutua de Ricart - Agrawala , Sistemas Distribuidos


ALGORITMO DISTRIBUIDO DE EXCLUSION MUTUA: Ricart - Agrawala
==============================

Aplicación del algoritmo de exclusión mutua de Ricart - Agrawala .

Miembros del grupo :
Garcia Valle Juniors
Quintanilla Paredes Guillermo
Santillan Plaza Jessica
Vela Fernandez Jesus

SISTEMAS OPERATIVOS 2 
DOCENTE: Ing. Anthony Gomez Morales

Escuela de Informatica - Fac. Ciencias Fisicas y Matematica - Universidad Nacional de Trujillo

Para probar nuestra aplicación del algoritmo de exclusión mutua Distribuido de Ricart - Agrawala tendrás que poner  Driver.java y RicartAgrawala.java en el directorio que se utilizará. 

A continuación, utilice 4 terminales nodos de la red:

Driver toma 1 como argumento, el número de nodo. 
Es importante que los argumentos se hagan como abajo en  las máquinas (nodos) .

En nodo 1 , ejecutar desde la consola de windows o GNU/Linux:

				> javac Driver.java
				> javac RicartAgrawala.java
				> java Driver 1

En el nodo 2: 
		> java Driver 2

En el nodo 3 : 
		> java Driver 3

En el nodo 4 : 
		> java Driver 4


Después de que los nodos hayan terminado la comunicación se puede matar/terminar a los procesos con ctrl + c . 

Cada terminal debe tener un registro de la producción que se remite a ellas. 
Se habrá creado el archivo  "CriticalSectionOutput.txt" .

Para verificar la exclusión mutua , examinar la " CriticalSectionOutput.txt" 
Archivo Para verificar la competencia examinar el resultado en la ventana de terminal.

================= REVISAR LA PARTE DEO CODIGO EN : Driver.java ====================

			if(nodeNum == 1)// IP DEL NODO 1
			{
				//Clear the file
				BufferedWriter clearWrite = new BufferedWriter(new FileWriter("CriticalSectionOutput.txt"));
				clearWrite.write("\n");
				clearWrite.close();

				System.out.println("Nodo 1 aqui:");
				ss1 = new ServerSocket(4461); //ServerSocket para net02
				ss2 = new ServerSocket(4462); //ServerSocket para net03
				ss3 = new ServerSocket(4463); //ServerSocket para net04
				s1 = ss1.accept();
				s2 = ss2.accept();
				s3 = ss3.accept();
			}
			else if(nodeNum == 2)// AKI SE COPIA LA IP DEL NODO 1
			{
				System.out.println("Nodo 2 aqui:");
				s1 = new Socket("192.168.1.36", 4461); //ClientSocket para net01
				ss2 = new ServerSocket(4462); //ServerSocket para net03
				ss3 = new ServerSocket(4463); //ServerSocket para net04

				s2 = ss2.accept();
				s3 = ss3.accept();
			}
			else if(nodeNum == 3)// AKI SE COPIA LA IP DEL NODO 1 Y NODO 2
			{
				System.out.println("Nodo 3 aqui:");
				s1 = new Socket("192.168.1.36", 4462); //ClientSocket para net01
				s2 = new Socket("192.168.1.76", 4462); //ClientSocket para net02
				ss3 = new ServerSocket(4463); //ServerSocket para net04

				s3 = ss3.accept();
			}
			else
			{
				System.out.println("Nodo 4 aqui:");// AKI SE COPIA LA IP DEL NODO 1,2 Y 3
				s1 = new Socket("192.168.1.36", 4463);
				s2 = new Socket("192.168.1.76", 4463);
				s3 = new Socket("192.168.1.82", 4463);
			}
