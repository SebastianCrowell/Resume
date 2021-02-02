import sys

sys.stdout.write(f"USEr anonymous person\r\n")
sys.stdout.write(f"SYST\r\n")             # error
sys.stdout.write(f"PASS foobar\r\n")      # error
sys.stdout.write(f"user updated starter code\r\n")
sys.stdout.write(f"pASS valid password this is\r\n")
sys.stdout.write(f"TYPE AI\r\n")          # error
sys.stdout.write(f"noop hello world\r\n") # error
sys.stdout.write(f"PORT 152,2,131,205,31,144\r\n")
sys.stdout.write(f"RETR MakeInput4.py\r\n")  
sys.stdout.write(f"RETR MakeInput4.py\r\n")  # error
sys.stdout.write(f"QUIT\r\n")
