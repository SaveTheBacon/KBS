from pracer.racer_client import RacerClient
import random


def main():
    racer_client = RacerClient('127.0.0.1', 8088)
    racer_client.connect()
    racer_client.full_reset()
    result = racer_client.racer_read_file_('\"D:/Facultate/An4/Sem2/KBS/RacerFiles/ontho.racer\"')
    print(result.value)

    for _ in range (15):
        lines = open('viruses.txt').read().splitlines()
        virus_name = random.choice(lines)
        racer_client.instance_m(name= virus_name, concept= "Virus")
    print(racer_client.concept_instances_m(concept_term="Virus"))
    racer_client.disconnect()


if __name__ == '__main__':
    main()
