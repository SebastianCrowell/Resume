import mpg_data from "./data/mpg_data.js";
import {getStatistics} from "./medium_1.js";

/*
This section can be done by using the array prototype functions.
https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array
see under the methods section
*/


/**
 * This object contains data that has to do with every car in the `mpg_data` object.
 *
 *
 * @param {allCarStats.avgMpg} Average miles per gallon on the highway and in the city. keys `city` and `highway`
 *
 * @param {allCarStats.allYearStats} The result of calling `getStatistics` from medium_1.js on
 * the years the cars were made.
 *
 * @param {allCarStats.ratioHybrids} ratio of cars that are hybrids
 */
export const allCarStats = {
    avgMpg : {
        city: mpg_data.map(p => (p.city_mpg)).reduce((acc, val) => {
            return (acc + val)/mpg_data.map(p => (p.city_mpg)).length
        }, []),
        highway: mpg_data.map(p => (p.highway_mpg)).reduce((acc, val) => {
            return (acc + val)/mpg_data.map(p => (p.highway_mpg)).length
        }, []),
    },
    allYearStats: getStatistics(mpg_data.map(p => (p.year))),
    ratioHybrids: mpg_data
        .map(p => (p.hybrid))
        .reduce((acc, val) => {
            if(val == true) {
                acc.push(val)
            } return acc;
        }, []).length/mpg_data.length
};
console.log(allCarStats)
/**
 * HINT: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/reduce
 *
 * @param {moreStats.makerHybrids} Array of objects where keys are the `make` of the car and
 * a list of `hybrids` available (their `id` string). Don't show car makes with 0 hybrids. Sort by the number of hybrids
 * in descending order.
 *
 *[{
 *     "make": "Buick",
 *     "hybrids": [
 *       "2012 Buick Lacrosse Convenience Group",
 *       "2012 Buick Lacrosse Leather Group",
 *       "2012 Buick Lacrosse Premium I Group",
 *       "2012 Buick Lacrosse"
 *     ]
 *   },
 *{
 *     "make": "BMW",
 *     "hybrids": [
 *       "2011 BMW ActiveHybrid 750i Sedan",
 *       "2011 BMW ActiveHybrid 750Li Sedan"
 *     ]
 *}]
 *
 *
 *
 *
 * @param {moreStats.avgMpgByYearAndHybrid} Object where keys are years and each year
 * an object with keys for `hybrid` and `notHybrid`. The hybrid and notHybrid
 * should be an object with keys for `highway` and `city` average mpg.
 *
 * Only years in the data should be keys.
 *
 * {
 *     2020: {
 *         hybrid: {
 *             city: average city mpg,
 *             highway: average highway mpg
 *         },
 *         notHybrid: {
 *             city: average city mpg,
 *             highway: average highway mpg
 *         }
 *     },
 *     2021: {
 *         hybrid: {
 *             city: average city mpg,
 *             highway: average highway mpg
 *         },
 *         notHybrid: {
 *             city: average city mpg,
 *             highway: average highway mpg
 *         }
 *     },
 *
 * }
 */
export const moreStats = {
    makerHybrids: mpg_data
        .filter(p => p.hybrid == true)
        .reduce((acc, val) => {
            if(!acc.map(v => v.make).includes(val.make)){
                 let hybrids =  mpg_data
                     .filter(p => p.hybrid == true)
                     .filter(p => p.make === val.make)
                     .map(p => p.id)
                     .reduce((accu, model) => {
                         if(!accu.includes(model)) accu.push(model);
                         return accu;
                     }, []);
                let objmake = {
                    make: val.make,
                    hybrids
                };
                acc.push(objmake);
                acc.sort(function(a,b){
                    if(a.make < b.make){
                        return -1;
                    } else if (a.make > b.make){
                        return 1;
                    }
                    return 0;
                });
            }
            return acc
        }, []),
    avgMpgByYearAndHybrid: mpg_data
        .reduce((acc, val) => {
            if(!acc[val.year]){
                let hybridsum = {
                    city: mpg_data
                        .filter(p => p.year === val.year)
                        .filter(p => p.hybrid == true)
                        .map(p => p.city_mpg)
                        .reduce((accu, hybridcar) => {
                            return Number(accu) + Number(hybridcar)
                        }, []),
                        highway: mpg_data
                        .filter(p => p.year === val.year)
                        .filter(p => p.hybrid == true)
                        .map(p => p.highway_mpg)
                        .reduce((accu, hybridcar) => {
                            return Number(accu) + Number(hybridcar)
                        }, []),
                    }   
                
                let nothybridsum = {
                    city: mpg_data
                    .filter(p => p.year === val.year)
                    .filter(p => p.hybrid == false)
                    .map(p => p.city_mpg)
                    .reduce((accu, nothybridcar) => {
                        return Number(accu) + Number(nothybridcar)
                    }, []),
                highway: mpg_data
                    .filter(p => p.year === val.year)
                    .filter(p => p.hybrid == false)
                    .map(p => p.highway_mpg)
                    .reduce((accu, nothybridcar) => {
                        return Number(accu) + Number(nothybridcar)
                    }, []),
                }
                let hybridaveragecity = hybridsum.city / mpg_data.filter(p => p.hybrid == true).filter(p => p.year === val.year).length
                let hybridaveragehighway = hybridsum.highway / mpg_data.filter(p => p.hybrid == true).filter(p => p.year === val.year).length

                let nothybridaveragecity = nothybridsum.city / mpg_data.filter(p => p.hybrid == false).filter(p => p.year === val.year).length
                let nothybridaveragehighway = nothybridsum.highway / mpg_data.filter(p => p.hybrid == false).filter(p => p.year === val.year).length

                let hybrid = {
                    city: hybridaveragecity,
                    highway: hybridaveragehighway
                }
                let notHybrid = {
                    city: nothybridaveragecity,
                    highway: nothybridaveragehighway
                }

                let objmake = {
                    hybrid,
                    notHybrid
                };
                acc[val.year] = objmake
            }
            return acc
        }, {})
};
console.log(moreStats)