import sys

sys.stdout.write(f"USER anonymous\r\n")
sys.stdout.write(f"user anonymous\r\n")
sys.stdout.write(f"QUIT\r\n")
sys.stdout.write(f"USER\r\n")   # error
sys.stdout.write(f"type I\r\n") # error
