import {variance} from "./data/stats_helpers.js";

/**
 * Gets the sum of an array of numbers.
 * @param array
 * @returns {*}
 * see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array
 * prototype functions. Very useful
 */
export function getSum(array) {
    let sum = array.reduce((acc, val) => {
        return acc + val
    });
    return sum;
}
console.log(getSum([1, 2, 3, 4, 5, 6]))

/**
 * Calculates the median of an array of numbers.
 * @param {number[]} array
 * @returns {number|*}
 *
 * example:
 * let array = [3,2,5,6,2,7,4,2,7,5];
 * console.log(getMedian(array)); // 4.5
 */
export function getMedian(array) {
    if(array.length % 2 === 0){
        return ((array[array.length/2 - 1] + array[array.length/2])/2);
    } else {
        return array[Math.floor(array.length/2)];
    }
}
let array = [3,2,5,6,2,7,4,2,7,5];
console.log(getMedian(array))
/**
 * Calculates statistics (see below) on an array of numbers.
 * Look at the stats_helper.js file. It does variance which is used to calculate std deviation.
 * @param {number[]} array
 * @returns {{min: *, median: *, max: *, variance: *, mean: *, length: *, sum: *, standard_deviation: *}}
 *
 * example:
 * getStatistics([3,2,4,5,5,5,2,6,7])
 * {
  length: 9,
  sum: 39,
  mean: 4.333333333333333,
  median: 5,
  min: 2,
  max: 7,
  variance: 2.6666666666666665,
  standard_deviation: 1.632993161855452
 }
 */
export function getStatistics(array) {
    let sum = array.reduce((acc, val) => {
        return acc + val
    });
    let length = array.length;
    let median = 0;
    if(length % 2 === 0){
        median = ((array[length/2 - 1] + array[length/2])/2);
    } else {
        median = array[Math.floor(length/2)];
    }
    let mean = sum/length;
    let max = array[0];
    for(let i = 1; i < length; ++i){
        if(array[i] > max){
            max = array[i];
        }
    }
    let min = array[0];
    for(let j = 1; j < length; ++j){
        if(array[j] < min){
            min = array[j];
        }
    }
    let obj = {
        length : length,
        sum : sum,
        mean : mean,
        median : median,
        min : min,
        max : max,
        variance : variance(array, mean),
        standard_deviation : Math.sqrt(variance(array, mean))
    };
    return obj
}

console.log(getStatistics([1,3,12,15,41,54]));