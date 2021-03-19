# Particle Collider
## A collision simulator

This is a university group project from Object Oriented Programming class.
It uses JPanel to draw all the objects. It has very few dependencies. We programmed all the physics on our own.

## How it works

User dictates the amount of Particles that are randomly spawned with random velocities on the board along with the randomly spawned Obstacles.

Simulation then starts, the rules are:

    1. All objects realistically collide with each other
    2. The collisions are elastic 
    3. On each "Particle -> Obstacle" collision Particle radius is reduced by 1
    4. Obstacles can only grow to a certain size
   
The simulation ends when one of these conditions is true:

    1. All particles disappeared
    2. All obstacles reached maximum size
    3. The time exceedes two minutes
   
After the simulation a .txt file with simulation statistics is generated.

## How it looks
<img src="particleCollider.gif" width=100% height=100%/>

## How to improve it

I am very happy with how this project turned out and I consider it finished but there are many ways it could get better. 

For example adding mass to the particles would only take a couple lines of code. We chose not to include it to simplify the project a bit. The goal was to use good OOP principles and not to make the most realistic physics simulator.
