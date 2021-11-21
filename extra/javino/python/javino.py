import sys
import serial
OP=sys.argv[1]
PORT=sys.argv[2]
MSG=sys.argv[3]
try:
	comm = serial.Serial(PORT, '9600', timeout=.1)
	comm.open
	comm.isOpen
	if(OP=='command' or OP=='send'):
		comm.write(bytes(MSG, 'utf-8'))
	if(OP=='request'):
		comm.write(bytes(MSG, 'utf-8'))
		print (comm.readline())
	if(OP=='listen'):
		print (comm.readline())
	comm.close
except:
	print ("Error connecting on "+PORT)
	sys.exit(1)
