import csv
import matplotlib.pyplot as plt
import sys

def load_data_from_csv(file_name):
    data = []
    with open(file_name, newline='') as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            data.append(float(row[0]))
    return data

def graph(file_name_with_opti, file_name_without_opti):
    tab_with_opti = load_data_from_csv(file_name_with_opti)
    tab_without_opti = load_data_from_csv(file_name_without_opti)

    plt.plot(range(1, len(tab_with_opti) + 1), tab_with_opti, label='Avec optimisation')
    plt.plot(range(1, len(tab_without_opti) + 1), tab_without_opti, label='Sans optimisation')
    plt.xlabel('Nombre de corbeaux')
    plt.ylabel('Pourcentage de victoire')
    plt.title(f'Pourcentage de victoire en fonction du nombre de corbeaux')
    plt.legend()
    plt.savefig('graph.png')
    plt.close()

def main():
    if len(sys.argv) != 3:
        print("Usage: python3 stat.py fichier_avec_opti.csv fichier_sans_opti.csv")
        return

    file_name_with_opti = sys.argv[1]
    file_name_without_opti = sys.argv[2]

    graph(file_name_with_opti, file_name_without_opti)

if __name__ == "__main__":
    main()
