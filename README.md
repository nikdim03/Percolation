# Percolation Simulation in Kotlin

This project is a Kotlin implementation of percolation simulation using the modified Dijkstra algorithm. The goal of the simulation is to find the most optimal path from the top to the bottom while trying to hit as few white squares as possible. The path can only go from top to bottom and to the sides. The project uses Java Swing library to visualize the path and allows choosing filling percentages of black squares and the grid size.

## Usage
To open and run the project, follow these steps:
- Download or clone the project
```
git clone https://github.com/nikdim03/Percolation.git
```
- Open the project in IntelliJ IDEA or your preferred IDE
- Choose filling percentages and the grid size
- Run the `Main.kt` file
- The program will generate a field with black and white squares and display the most optimal path from top to bottom in the Java Swing window

## Dependencies
This project was tested with the following dependencies:

- Kotlin 1.8.20
- Openjdk 18.0.2.1

## License
This project is licensed under the MIT License. Contributions are welcome and appreciated.
