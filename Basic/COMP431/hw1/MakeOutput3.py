import sys

sys.stdout.write(f"220 COMP 431 FTP server ready.\r\n")
sys.stdout.write(f"USER anonymous\r\n")
sys.stdout.write(f"331 Guest access OK, send password.\r\n")
sys.stdout.write(f"user anonymous\r\n")
sys.stdout.write(f"331 Guest access OK, send password.\r\n")
sys.stdout.write(f"QUIT\r\n")
sys.stdout.write(f"200 Command OK.\r\n")
