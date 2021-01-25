# get helper tools
import matplotlib.pyplot as plt
from math import sin, cos, sqrt, tan, pi

# define variables
# set constants needed for the modelling
g =                     # acceleration of gravity in m/s²
k =                     # spring constant in N/m (must measure)
angle =                 # launch angle in degrees from horizontal
theta = angle*pi/180    # convert launch angle to radians  
m =                     # mass of the ball in kg (must measure)
dy =                    # spring launcher displacement in meters (must measure)  

# calculate launch speed from warm-up
v = 

# calculate 2-D projectile dynamics
vx = v*cos(theta)   # initial horz speed
vy = v*sin(theta)   # initial vert speed
x = 0.0             # initial horz position
y = 0.0             # initial vert position
t = 0.0             # initial time in sec
dt = 0.01           # time increment in sec

# this while loop iterates over time to calculate the speed and position
# it will run untl the ball comes back down to y-position = 0
while y >= 0:
    vy = vy - g*dt      # vert kinematic equation
    vx = vx             # horz kinematic equation
    y += vy*dt          # += means to add the RHS to the LHS and replace the LHS with the new value
    x += vx*dt          # += means to add the RHS to the LHS and replace the LHS with the new value 
    t += dt             # += means to add the RHS to the LHS and replace the LHS with the new value
    plt.plot(t, y, 'b.') # plot y vs t in blue dots    
    plt.plot(t, x, 'r.') # plot x vs t in red dots

# solution using position vs time instead of speed vs time
# reset initial values then insert code here

# plot the data calculated in the while loop
plt.ylim(0,3)
plt.xlim(0,2)
plt.title("balls y position vs time")
plt.xlabel("Time t (sec)")
plt.ylabel("Position y (m)")
plt.show()
