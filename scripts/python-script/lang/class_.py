# Python中的类
class Dog:
    # self自动传递
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def sit(self):
        print(self.name.title(), "is now sitting")

    def roll_over(self):
        print(self.name.title(), "rolled over")


my_dog = Dog("a", 3)
my_dog.sit()

print("dog age", my_dog.age)


class Car:
    def __init__(self, name, model, year):
        self.name = name
        self.model = model
        self.year = year


# 类的继承
class ElectricCar(Car):
    def __init__(self, name, model, year):
        super().__init__(name, model, year)


my_car = ElectricCar("a", "b", 10)
