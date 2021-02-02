import sys

# USER/PASS
sys.stdout.write("user anonymous\r\n")          # Test lowercase command (command valid)
sys.stdout.write("PASS word\n")                 # Test CRLF (command invalid)
sys.stdout.write("USER anonymous\r\n")          # Test uppercase command (command valid)
sys.stdout.write("PASS \r\n")                   # Test missing parameter (command invalid)
sys.stdout.write("UsEr anonymous\n")            # Test CRLF (command invalid)
sys.stdout.write("USERCS anonymous\r\n")        # Test command without space preceding parameter (command invalid)
sys.stdout.write("PASS password\r\n")           # Test pass when invalid user was previously input (command invalid)
sys.stdout.write("USER anonymous\r\n")          # Base functionality (command valid)
sys.stdout.write("PASS password\r\n")           # Base functionality (command valid)

# TYPE
sys.stdout.write("TYPE A\r\n")                  # Type A (command valid)
sys.stdout.write("type I\r\n")                  # Type I (command valid)
sys.stdout.write("type B\r\n")                  # Unknown type (command invalid)

# SYST
sys.stdout.write("SYST\r\n")                    # SYST (command valid)
sys.stdout.write("syST\r\n")                    # Command valid
sys.stdout.write("SYST \r\n")                   # Space after syst (command invalid)

# NOOP
sys.stdout.write("NOOP\r\n")                    # Command valid
sys.stdout.write("NOOP \r\n")                   # Command invalid
sys.stdout.write("noop\r\n")                    # Command valid

# PORT
sys.stdout.write("PORT 1,2,3,4,5\r\n")          # Missing a port number
sys.stdout.write("PORT 1, 2, 3, 4, 5, 6\r\n")   # Extra spaces (command invalid)
sys.stdout.write("PORT 1,2,3,4,5,6\r\n")        # Command valid

# RETR
sys.stdout.write("RETR MakeInput1.py\r\n")        # Command valid
sys.stdout.write("RETR MakeInput2.py\r\n")        # Command valid

# QUIT
sys.stdout.write("quit\n")                      # Command invalid
sys.stdout.write("QUIT\r\n")                    # Command valid
