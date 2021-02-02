import sys

sys.stdout.write(f"USER anonymous\r\n")
sys.stdout.write(f"PASS foobar\r\n")
sys.stdout.write(f"syST\r\n")
sys.stdout.write(f"type I\r\n")
sys.stdout.write(f"NOOP\r\n")
sys.stdout.write(f"PORT 152,2,131,205,31,144\r\n")
sys.stdout.write(f"RETR MakeOutput1.py\r\n") 
sys.stdout.write(f"QUIT\r\n")
