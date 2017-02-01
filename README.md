jmxbf - A brute force program to test weak accounts configured to access a JMX Registry
 
Released as open source by NCC Group Plc - http://www.nccgroup.com/

Developed by Daniele Costa, daniele.costa@nccgroup.trust
http://www.github.com/nccgroup/jmxbf
Released under AGPL see LICENSE for more information

$>Usage: 
java -jar jmxbf.jar
	-h,--host <arg>              The JMX server IP address.
	-p,--port <arg>              The JMX server listening port.
	-pf,--passwords-file <arg>   File including the passwords, one per line.
    -uf,--usernames-file <arg>   File including the usernames, one per line.

Example:
   
$>java –jar jmxbf.jar –h 192.168.20.1 –p 1099 –uf usernames.txt –pf passwords.txt
		
Some samp;e output below:

```
$>java –jar jmxbf.jar –h 192.168.20.1 –p 1099 –uf usernames.txt –pf passwords.txt

Auth failed!!!

Auth failed!!!

Auth failed!!!

. . . 

Auth failed!!!

Auth failed!!!


###SUCCESS### - We got a valid connection for: control:supersecretpwd

Found some valid credentials - continuing brute force
....

###SUCCESS### - We got a valid connection for: monitor:monitor


Found some valid credentials - continuing brute force
Auth failed!!!

Auth failed!!!

Auth failed!!!

Auth failed!!!

. . . 

Auth failed!!!

Auth failed!!!

Auth failed!!!

The following valid credentials were found:


control:supersecretpwd

monitor:monitor
```