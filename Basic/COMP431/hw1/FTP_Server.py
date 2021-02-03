###################################
#                                 #
#     John Moore - COMP 431       #
#   Starter server code for HW1   #
#          Version 1.2            #
###################################

import os
import sys
import shutil

# Define important ASCII character decimal representations
# These are helpful for defining various command grammars  
# Use ord(char) to get decimal ascii code for char
cr = ord('\r')  # = 13
lf = ord('\n')  # = 10
crlf_vals = [cr, lf]

# Define known server commands (case insensitive). Add to this as commands are added
command_list = ["USER", "QUIT", "PASS", "TYPE", "SYST", "NOOP", "PORT", "RETR"]

# Manage valid response messages for every command
valid_responses = {
    "RETR" : "150 File status okay.\r\n",
    "USER" : "331 Guest access OK, send password.\r\n",
    "QUIT" : "200 Command OK.\r\n",
    "NOOP" : "200 Command OK.\r\n",
    "SYST" : "215 UNIX Type: L8.\r\n",
    "PASS" : "230 Guest login OK.\r\n",
    "RETR" : "250 Requested file action completed.\r\n",
}

##############################################################################################
#                                                                                            # 
#     This function is intended to manage the command processing loop.  # 
#     The general idea is to loop over the input stream, identify which 
#     command # was entered, and then delegate the command-processing to the 
#     appropriate function.  #
#                                                                                            #
##############################################################################################
login = 0
def read_commands():
    # FTP service always begins with "220 COMP 431 FTP server ready.\r\n"
    sys.stdout.write("220 COMP 431 FTP server ready.\r\n")

    # Keep track of the expected commands, initially only "USER" and "QUIT" are valid commands
    expected_commands = ["USER", "QUIT"]
    for command in sys.stdin:       # Iterate over lines from input stream until EOF is found
        if login == -1:
            break
        # Echo the line of input
        sys.stdout.write(command)

        # Split command into its tokens and parse relevant command
        tokens = command.split()    # Assume tokens are delimited by <SP>, <CR>, <LF>, or <CRLF>
        # print(tokens[1])

        # Check to make sure there are tokens in the line, and assign command_name
        command_name = tokens[0].upper() if len(tokens) > 0 else "UNKNOWN"       # Commands are case-insensitive
        # Check first token in list to see if it matches any valid commands
        # sys.stdout.write(command_name)
        if command_name in command_list and not command[0].isspace():
            if command_name in expected_commands:
                #############################################################
	                #  This is intended to delegate command processing to       #
                #  the appropriate helper function. Helper function parses  #
                #  command, performs any necessary work, and returns        #
                #  updated list of expected commands                        #
                #############################################################
                if command_name == "USER":         
                    result, expected_commands = parse_user(tokens)

                if command_name == "PASS":         
                    result, expected_commands = parse_pass(tokens)

                if command_name == "QUIT":         
                    result, parse_quit()

                if command_name == "SYST":         
                    result, expected_commands = parse_syst(command)

                if command_name == "NOOP":         
                    result, expected_commands = parse_noop(command)

                if command_name == "TYPE":         
                    result, expected_commands = parse_type(tokens)

                if command_name == "PORT":         
                    result, expected_commands = parse_port(tokens)

                if command_name == "RETR":         
                    result, expected_commands = parse_retr(tokens)
                ##################################################
                #  After command processing, the following code  #
                #  prints the appropriate response message       #
                ##################################################
                if result != "ok":
                    sys.stdout.write(result)
                else:
                    if ord(command[-1]) == lf and ord(command[-2]) == cr:       # The ord(char) function gives decimal ascii code of char
                        sys.stdout.write(valid_responses[command_name])
                    else:
                        sys.stdout.write("501 Syntax error in parameter.\r\n")
                        ######################################################
                        #  Update expected_commands list if incorrect CRLF   #
                        #  changes the possible commands that can come next  #     
                        ######################################################
                        if command_name == "USER":
                            if login != 2:
                                globals()['login'] = 0;
                            expected_commands = ["USER", "PASS", "QUIT"]
                        if command_name == "PASS":
                            if login != 2:
                                globals()['login'] = 0;
                            expected_commands = ["USER", "QUIT"]
                        if command_name == "QUIT":
                            if login != 2:
                                globals()['login'] = 0;
                            expected_commands = ["USER", "QUIT"]
                        if command_name == "SYST":
                            if login != 2:
                                globals()['login'] = 0;
                            expected_commands = ["USER", "QUIT"]
                        if command_name == "NOOP":
                            if login != 2:
                                globals()['login'] = 0;
                            expected_commands = ["USER", "QUIT"]
                        if command_name == "TYPE":
                            if login != 2:
                                globals()['login'] = 0;
                            expected_commands = ["USER", "QUIT"]
                        if command_name == "PORT":
                            if login != 2:
                                globals()['login'] = 0;
                            expected_commands = ["USER", "QUIT"]
                        if command_name == "RETR":
                            if login != 2:
                                globals()['login'] = 0;
                            expected_commands = ["USER", "QUIT"]
            else:
                # Out of order command received
                if login != 2:
                    globals()['login'] = 0;
                    sys.stdout.write("530 Not logged in.\r\n")
                else:
                    sys.stdout.write("503 Bad sequence of commands.\r\n")
        else:
            # No valid command was input
            sys.stdout.write("500 Syntax error, command unrecognized.\r\n")

################################################################################
#                                                                              # 
#     Parse the USER command to check if tokens adhere to grammar              #
#     The "tokens" parameter is a list of the elements of the command          #
#     separated by whitespace. The return value indicates if the command       #
#     is valid or not, as well as the next list of valid commands.             #
#                                                                              #
################################################################################
def parse_user(tokens):
    # Check to make sure there is at least one token after "USER"
    if globals()['login'] != 2:
        globals()['login'] = 0;
    if len(tokens) == 1:
        return "501 Syntax error in parameter.\r\n", ["USER", "QUIT"]
    else:
        # Iterate through remaining tokens and check that no invalid usernames are entered
        for token in tokens[1:]:
            for char in token:
                if ord(char) > 127 or ord(char) in crlf_vals:     # Byte values > 127 along with <CRLF> are not valid for usernames
                    return "501 Syntax error in parameter.\r\n", ["USER", "QUIT"]
    globals()['login'] = 1;
    return "ok", ["USER", "PASS", "QUIT"]      # If the function makes it here, the input adheres to the grammar for this command


def parse_pass(tokens):
    # Check to make sure there is similar to USER, at least one token after "PASS"
    # print(login)
    if len(tokens) == 1:
        globals()['login'] = 0;
        return "501 Syntax error in parameter.\r\n", ["USER", "QUIT"]
    else:
        # Iterate tokens to check that a valid password is entered
        for token in tokens[1:]:
            for char in token:
                if ord(char) > 127 or ord(char) in crlf_vals:	 # Byte vals like above
                    return "501 Syntax error in parameter.\r\n", ["USER", "QUIT"]
    if login >= 1:
       globals()['login'] = 2;
    else:
       return "530 Not logged in.\r\n", ["USER", "QUIT"]
    return "ok", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]	# Everything was correct (need the other things available after correct pass as well)

def parse_quit():
    # Stop all reading of commands
    globals()['login'] = -1;
    return "ok";

def parse_syst(command):
    # Documentation lacking to create more advanced output
    if len(command) != 6:
        return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    if command[4] != '\r':
        return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    if command[5] != '\n':
        return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    return "ok", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]

def parse_noop(command):
    # Documentation lacking to create more advanced output
    if len(command) != 6:
        return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    if command[4] != '\r':
        return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    if command[5] != '\n':
        return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    return "ok", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]

def parse_type(tokens):
    # Check to see if A or I
    # print(tokens[1])
    if len(tokens) == 1:
        return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    else:
        # Check the token for A or I
        for token in tokens[1:]:
            for char in token:
                if ord(char) > 127 or ord(char) in crlf_vals: # Byte char check
                    return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    # immutable nature of python makes it difficult to return the character to print
    if tokens[1].upper() == "A":
        return "200 Type set to A.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    if tokens[1].upper() == "I":
        return "200 Type set to I.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    # type_char = tokens[1]
    return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]

def parse_port(tokens):
    # Check to see the number of ints in tokens
    split_token = tokens[1].split(',')
    simple_bool = True
    for num in split_token:
    #   print(num)
    #   print(num.isdigit())
       if not num.isdigit():
           simple_bool = False
    if simple_bool == False:
       return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    # print(simple_bool)
    # print(split_token.isdigit())
    # print(split_token)
    if len(tokens) == 1:
       return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    else:
       # Check the tokens
       for token in tokens[1:]:
           for char in token:
               if ord(char) > 127 or ord(char) in crlf_vals: # Byte char check
                   return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
               if len(tokens) > 2:
                   return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
               if len(split_token) < 6:
                   return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
               #if not tokens[1].isdigit()
               #    return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]
    converter = str(int(split_token[4]) * 256 + int(split_token[5]))
    return "200 Port command successful (%s.%s.%s.%s,%s).\r\n" %(split_token[0], split_token[1], split_token[2], split_token[3], converter), ["QUIT", "PORT", "SYST", "NOOP", "TYPE", "RETR"]

def parse_retr(tokens):
    # Check path?
    # print(tokens)
    counter = 1
    if len(tokens) == 1:
       return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE", "RETR"]
    else:
       # Check token
       for token in tokens[1:]:
           for char in token:
               if ord(char) > 127 or ord(char) in crlf_vals: # Byte char check
                   return "501 Syntax error in parameter.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE", "RETR"]
    token_path = tokens[1]
    if token_path[0] == "/":
       file_path = token_path[1:]
       if not os.path.isfile(file_path):
       #os.path.isfile() or path.exists()
          return "550 File not found or access denied.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE", "RETR"]
       else:
          sys.stdout.write("150 File status okay.\r\n");
       #check for os.path.isfile() in the retr folder
       if os.path.isfile('retr_files/file%d' %(counter)):
          counter += 1
       shutil.copyfile('%s' %(file_path), 'retr_files/%s' %(file_path))
       os.rename('retr_files/%s' %(file_path), 'retr_files/file%d' %(counter))
    else:
       file_path = token_path
       if not os.path.isfile(file_path):
       #os.path.isfile() or path.exists()
          return "550 File not found or access denied.\r\n", ["QUIT", "PORT", "SYST", "NOOP", "TYPE", "RETR"]
       else:
          sys.stdout.write("150 File status okay.\r\n");
       #check for os.path.isfile() in the retr folder
       if os.path.isfile('retr_files/file%d' %(counter)):
          counter += 1
       shutil.copyfile('%s' %(file_path), 'retr_files/%s' %(file_path))
       os.rename('retr_files/%s' %(file_path), 'retr_files/file%d' %(counter))
    #print(file_path)
    #print(os.path.isfile(file_path))
    return "ok", ["QUIT", "PORT", "SYST", "NOOP", "TYPE"]

read_commands()
