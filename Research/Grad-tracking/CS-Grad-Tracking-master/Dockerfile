
#Define node version to pull
#test others using docker ignore or commenting out a connection stuff
FROM node:latest

# Directory to hold app code
WORKDIR /CS-Grad-Tracking

#Dupe the dependency file
COPY package.json /CS-Grad-Tracking

#Install from package
RUN npm install

#Bundle into docker image
COPY . .

#Exposed port
EXPOSE 8080:8080

#Attempt a run of the system
#RUN npm run devAsAdmin

#Commands to run
CMD [ "npm", "test", "mongo://mongo:27017/cs_grad_data", "-n", "0.0.0.0"]

