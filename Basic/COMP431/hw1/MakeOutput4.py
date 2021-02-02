import sys

sys.stdout.write(f"220 COMP 431 FTP server ready.\r\n")
sys.stdout.write(f"USEr anonymous person\r\n")
sys.stdout.write(f"331 Guest access OK, send password.\r\n")
sys.stdout.write(f"SYST\r\n")             # error
sys.stdout.write(f"530 Not logged in.\r\n")
sys.stdout.write(f"PASS foobar\r\n")      # error
sys.stdout.write(f"530 Not logged in.\r\n")
sys.stdout.write(f"user updated starter code\r\n")
sys.stdout.write(f"331 Guest access OK, send password.\r\n")
sys.stdout.write(f"pASS valid password this is\r\n")
sys.stdout.write(f"230 Guest login OK.\r\n")
sys.stdout.write(f"TYPE AI\r\n")          # error
sys.stdout.write(f"501 Syntax error in parameter.\r\n")
sys.stdout.write(f"noop hello world\r\n") # error
sys.stdout.write(f"501 Syntax error in parameter.\r\n")
sys.stdout.write(f"PORT 152,2,131,205,31,144\r\n")
sys.stdout.write(f"200 Port command successful (152.2.131.205,8080).\r\n")
sys.stdout.write(f"RETR MakeInput4.py\r\n")
sys.stdout.write(f"150 File status okay.\r\n")
sys.stdout.write(f"250 Requested file action completed.\r\n")
sys.stdout.write(f"RETR MakeInput4.py\r\n")  # error
sys.stdout.write(f"503 Bad sequence of commands.\r\n")
sys.stdout.write(f"QUIT\r\n")
sys.stdout.write(f"200 Command OK.\r\n")
