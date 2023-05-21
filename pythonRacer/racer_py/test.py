import socket

'''
    Modify below values for your configuration
    Racer TCP Python Client
'''

RACER_IP = '192.168.100.15'
RACER_PORT = 8088
RACER_FILE_PATH = "\"D:/family.racer\""


def parse_racer_message(msg):
    message_array = msg.split(' ')
    status_message = message_array[0]
    status_code = message_array[1]
    print('status message: ' + status_message)
    print(status_code)
    for i in range(2, len(message_array)):
        print(message_array[i])


racer_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

racer_server = (RACER_IP, RACER_PORT)

racer_socket.connect(racer_server)

try:
    message = '(full-reset)\n'
    print('message: ' + message)
    racer_socket.send(message.encode())
    parse_racer_message(racer_socket.recv(256).decode())

    message = "(racer-read-file " + RACER_FILE_PATH + ")\n"
    print('message: ' + message)
    racer_socket.send(message.encode())
    parse_racer_message(racer_socket.recv(270).decode())

    message = '(all-atomic-concepts)\n'
    print('message: ' + message)
    racer_socket.send(message.encode())
    parse_racer_message(racer_socket.recv(256).decode())

    racer_socket.close()

except Exception as e:
    print(e)
