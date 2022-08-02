from random import randint

n = 1000
m = int(input())
k = 1000

f = open("mapa.txt", "w+")
f.write(str(n) + " " + str(m) + "\n")
for i in range(m):
    x = randint(1, n)
    y = randint(1, n)
    while (x == y):
        y = randint(1, n)

    f.write(str(x) + " " + str(y) + " " + str(randint(1, n)) + "\n")

f.write(str(k))
for i in range(k):
    init = randint(0, n)
    f.write(str(randint(1, n)) + " " + str(init) + " " + str(randint(init, n)) + "\n")

f.write(str(randint(1, n)) + " " + str(randint(1, n)))