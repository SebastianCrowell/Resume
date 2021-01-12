import mpg_data from "./data/mpg_data.js";

/*
mpg_data is imported for you but that is for testing purposes only. All of the functions should use
a car_data param that is supplied as the first parameter.

As you write these functions notice how they could possibly be chained together to solve more complicated
queries.
 */

/**
 * @param {array} car_data - an instance of mpg_data that should be used for filtering.
 * @param minHorsepower {number}
 * @param minTorque {number}
 *
 * @return {array} An array of car objects with horsepower >= minHorsePower and torque >= minTorque
 * sorted by horsepower in descending order.
 *
 */
export function searchHighPower(car_data, minHorsepower, minTorque) {
    let horseTorque = []
    horseTorque = car_data
        .filter(p => p.horsepower >= minHorsepower)
        .filter(p => p.torque >= minTorque)
        .sort((a,b) => b.horsepower - a.horsepower)
    return horseTorque
}
//console.log(searchHighPower(mpg_data, 200, 500) )

/**
 * @param {array} car_data
 * @param minCity
 * @param minHighway
 *
 *
 * @return {array} An array of car objects with highway_mpg >= minHighway and city_mpg >= minCity
 * sorted by highway_mpg in descending order
 *
 */
export function searchMpg(car_data, minCity, minHighway) {
    let cityhigh = []
    cityhigh = car_data
        .filter(p => p.city_mpg >= minCity)
        .filter(p => p.highway_mpg >= minHighway)
        .sort((a,b) => b.highway_mpg - a.highway_mpg)
    return cityhigh
}
//console.log(searchMpg(mpg_data, 30, 20))


/**
 * Find all cars where 'id' contains the search term below.
 * Sort the results so that if the term appears earlier in the string
 * it will appear earlier in the list. Make sure searching and sorting ignores case.
 * @param car_data
 * @param searchTerm A string to that is used for searching
 * @returns {[]} array of cars
 */
export function searchName(car_data, searchTerm) {
    let name = []
    name = car_data
        .filter(p => p.id === name)
        name.sort(function(a,b){
            if(a.id < b.id){
                return -1;
            } else if (a.id > b.id){
                return 1;
            }
            return 0;
        });
    return name
}


/**
 * Find all cars made in the years asked for.
 * Sort the results by year in descending order.
 *
 * @param car_data
 * @param {number[]} years - array of years to be included in the results e.g. [2010, 2012]
 * @returns {[]} an array of car objects
 */
export function searchByYear(car_data, years) {
    let something = []
    something = car_data
        .filter(p => p.years === years)
        name.sort((a,b) => b.years - a.years)
        return something
}
