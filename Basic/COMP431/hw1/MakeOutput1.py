import sys

sys.stdout.write("220 COMP 431 FTP server ready.\r\n")

# USER/PASS
sys.stdout.write("user anonymous\r\n")
sys.stdout.write("331 Guest access OK, send password.\r\n")  
sys.stdout.write("PASS word\n")   
sys.stdout.write("501 Syntax error in parameter.\r\n")

sys.stdout.write("USER anonymous\r\n")
sys.stdout.write("331 Guest access OK, send password.\r\n")  
sys.stdout.write("PASS \r\n")
sys.stdout.write("501 Syntax error in parameter.\r\n")     

sys.stdout.write("UsEr anonymous\n")
sys.stdout.write("501 Syntax error in parameter.\r\n")
sys.stdout.write("USERCS anonymous\r\n")                     
sys.stdout.write("500 Syntax error, command unrecognized.\r\n")     
sys.stdout.write("PASS password\r\n")        
sys.stdout.write("530 Not logged in.\r\n")      

sys.stdout.write("USER anonymous\r\n")    
sys.stdout.write("331 Guest access OK, send password.\r\n")         
sys.stdout.write("PASS password\r\n")                      
sys.stdout.write("230 Guest login OK.\r\n")                         

# TYPE
sys.stdout.write("TYPE A\r\n")                  # Type A (command valid)
sys.stdout.write("200 Type set to A.\r\n")

sys.stdout.write("type I\r\n")                  # Type I (command valid)
sys.stdout.write("200 Type set to I.\r\n")

sys.stdout.write("type B\r\n")                  # Unknown type (command invalid)
sys.stdout.write("501 Syntax error in parameter.\r\n")

# SYST
sys.stdout.write("SYST\r\n")                    # SYST (command valid)
sys.stdout.write("215 UNIX Type: L8.\r\n")

sys.stdout.write("syST\r\n")                    # Command valid
sys.stdout.write("215 UNIX Type: L8.\r\n")

sys.stdout.write("SYST \r\n")                   # Space after syst (command invalid)
sys.stdout.write("501 Syntax error in parameter.\r\n")

# NOOP
sys.stdout.write("NOOP\r\n")                    # Command valid
sys.stdout.write("200 Command OK.\r\n")

sys.stdout.write("NOOP \r\n")                   # Command invalid
sys.stdout.write("501 Syntax error in parameter.\r\n")

sys.stdout.write("noop\r\n")                    # Command valid
sys.stdout.write("200 Command OK.\r\n")

# PORT
sys.stdout.write("PORT 1,2,3,4,5\r\n")          # Missing a port number (command invalid)
sys.stdout.write("501 Syntax error in parameter.\r\n")

sys.stdout.write("PORT 1, 2, 3, 4, 5, 6\r\n")   # Extra spaces (command invalid)
sys.stdout.write("501 Syntax error in parameter.\r\n")

sys.stdout.write("PORT 1,2,3,4,5,6\r\n")        # Command valid
sys.stdout.write("200 Port command successful (1.2.3.4,1286).\r\n")

# RETR
sys.stdout.write("RETR MakeInput1.py\r\n")         # Command valid
sys.stdout.write("150 File status okay.\r\n")
sys.stdout.write("250 Requested file action completed.\r\n")

sys.stdout.write("RETR MakeInput2.py\r\n")    # Command valid, but isn't preceded by a new valid port
sys.stdout.write("503 Bad sequence of commands.\r\n")

# QUIT
sys.stdout.write("quit\n")                      # Command invalid
sys.stdout.write("501 Syntax error in parameter.\r\n")

sys.stdout.write("QUIT\r\n")                    # Command valid
sys.stdout.write("200 Command OK.\r\n")
