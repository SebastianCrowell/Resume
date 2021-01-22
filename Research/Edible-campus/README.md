# Edible Gardens Mapp

Worked on by Josh Jiang and Sebastian Crowell.

Edible Campus is a program of the North Carolina Botanical Garden that creates working landscapes across the UNC Chapel Hill campus to facilitate student engagement in topics of food and agriculture sustainability.

This project is an interactive UI that maps each garden in google maps and contains information about gardens, plants, and recipes. The project is built with ReactJS and deployed on Carolina Cloudapps (Redhat Openshift) at http://react-ui-dept-botanicalgarden.cloudapps.unc.edu

Coverage from UNC Chapel Hill website: https://www.unc.edu/posts/2019/07/19/new-edible-campus-interactive-map-guides-foragers-to-whats-in-season/

## How to use 
 0. Download node.js https://nodejs.org/en/
 1. clone the repo in a new folder and install dependencies 
    ``` 
    git clone https://github.com/joshjiang/edible-gardens-map.git .
    npm install
    ```
 2. Ask Dr. Pozefsky for the access to the gitlabs repo with the API credentials for https://ediblecampusapi-dept-botanicalgarden.cloudapps.unc.edu/ and add them to a REACT_APP_EDIBLE_GARDENS_API_CREDENTIALS environment variable in your shell or deployment configuration
 3. run the dev server 
    ``` 
    npm start
    ```
