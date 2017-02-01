/*
 * jmxbf - A brute force program to test weak accounts configured to access a JMX Registry
 * 
 * Released as open source by NCC Group Plc - http://www.nccgroup.com/
 *
 *  Developed by Daniele Costa, daniele [dot] costa [at] nccgroup [dot] trust
 *  http://www.github.com/nccgroup/jmxbf
 *  Released under AGPL see LICENSE for more information
 * 
 *    First version - 25/07/2014 - v0.1alpha
 *    
 *    Last update - 31/01/2017 - v0.1beta
 * 
 *Usage: jmxbf
 *		-h,--host <arg>              The JMX server IP address.
 *		-p,--port <arg>              The JMX server listening port.
 *		-pf,--passwords-file <arg>   File including the passwords, one per line.
 *		-uf,--usernames-file <arg>   File including the usernames, one per line.
 *
 *
 * This file may be used under the terms of the GNU General Public License 
 * version 2.0 as published by the Free Software Foundation:
 *   http://www.gnu.org/licenses/gpl-2.0.html
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.management.MalformedObjectNameException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.FileReader;
import java.io.BufferedReader;
import org.apache.commons.cli.*;

 
public class jmxbf {
 
    
 
 
    public static void main(String[] args) throws IOException, MalformedObjectNameException {
        
    	   	
    	String HOST="";
		String PORT="";
		String usersFile="";
		String pwdFile="";
		
		CommandLine cmd = getParsedCommandLine(args);
        
        if (cmd!=null) {
        	
        		HOST=cmd.getOptionValue("host");
        		PORT=cmd.getOptionValue("port");
        		usersFile=cmd.getOptionValue("usernames-file");
        		pwdFile=cmd.getOptionValue("passwords-file");
        	
		
        } else {
        	
        	System.exit(1);
        }

        
        	
		String finalResults="";
		
		BufferedReader users = new BufferedReader(new FileReader(usersFile)); 
		BufferedReader pwds = new BufferedReader(new FileReader(pwdFile));
				
		
		
		
		JMXServiceURL url =
				new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+HOST + ":" + PORT+"/jmxrmi");
            //new JMXServiceURL("service:jmx:remoting-jmx://" + HOST + ":" + PORT);
         
        
		String user = null;
		boolean found=false;
		while ((user = users.readLine()) != null) {
			String pwd=null;
			while ((pwd = pwds.readLine()) != null) {
				//System.out.println(user+":"+pwd);
				
				Map<String, String[]> env = new HashMap<>();
				String[] credentials = {user, pwd};
				env.put(JMXConnector.CREDENTIALS, credentials);
				try {
				
					JMXConnector jmxConnector = JMXConnectorFactory.connect(url, env);
					
					System.out.println();
					System.out.println();
					System.out.println();
					System.out.println("[+] ###SUCCESS### - We got a valid connection for: "+user+":"+pwd+"\r\n\r\n");		
					finalResults=finalResults+"\n"+user+":"+pwd;
					jmxConnector.close();
					found=true;
					break;
				
				} catch (java.lang.SecurityException e) {
					System.out.println("Auth failed!!!\r\n");
				
				}
			}
			if(found)  {
				System.out.println("Found some valid credentials - continuing brute force");
				found=false;
				
			} 
			//closing and reopening pwds
			pwds.close();
			pwds = new BufferedReader(new FileReader(pwdFile));
				
			
			
			}
		
			users.close();
			//print final results
			if (finalResults.length()!=0) {
				System.out.println("The following valid credentials were found:\n");
				System.out.println(finalResults);
			}
			
		}

	
    
    
    /**
	 * @param args
	 * @return
	 */
	private static CommandLine getParsedCommandLine(String[] args) {
		Options options = new Options();

        Option host = new Option("h", "host", true, "The JMX server IP address.");
        host.setRequired(true);
        options.addOption(host);

        Option port = new Option("p", "port", true, "The JMX server listening port.");
        port.setRequired(true);
        options.addOption(port);
        
        Option usernames = new Option("uf", "usernames-file", true, "File including the usernames, one per line.");
        usernames.setRequired(true);
        options.addOption(usernames);
        
        Option passwords = new Option("pf", "passwords-file", true, "File including the passwords, one per line.");
        passwords.setRequired(true);
        options.addOption(passwords);
        
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("jmxbf", options);
            return null;
            //System.exit(1)

        }
		return cmd;
	}
		
		
        
    }
 
