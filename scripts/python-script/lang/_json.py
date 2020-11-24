import json

jsonFilePath = '/tmp/numbers.json'
numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9]

with open(jsonFilePath, 'w') as file:
    json.dump(numbers, file)

with open(jsonFilePath) as file:
    print(file.read())

with open(jsonFilePath) as file:
    numbers = json.load(file)

print("numbers[0]", numbers[0])
