/**
 *
 * @param {number} a
 * @param {number} b
 * @returns {string} 'a + b = (a + b)'
 *
 * example: sumToString(3, 4)
 * returns: '3 + 4 = 7'
 * see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Template_literals
 */
export function sumToString(a, b) {
    return `${a} + ${b} = ${(a + b)}`;
}

console.log(`${sumToString(3,4)}`);

/**
 *
 * @param {number} startNumber
 * @param {number} endNumber
 * @returns {number[]}
 *
 * example: getIncreasingArray(3, 7)
 * returns: [ 3, 4, 5, 6, 7 ]
 *
 */
export function getIncreasingArray(startNumber, endNumber) {
    let number = [];
    let length = endNumber - startNumber;
    let fromTo = startNumber;
    for(let i = 0; i < length + 1; i++){
        number.push(fromTo);
        fromTo++;
    }
    return  number;
}

console.log(`${getIncreasingArray(3,7)}`);

/**
 *
 * @param {number[]} numbers
 * @return {{min: number, max: number}}
 * see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Spread_syntax
 * and https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math
 */
export function maxAndMin(numbers) {
    let min = numbers[0];
    let max = numbers[0];
    for(let i = 0; i < numbers.length; i++){
        if(numbers[i] < min){
            min = numbers[i];
        } else if(numbers[i] > max){
            max = numbers[i];
        }
    }
    return `${min}, ${max}`;
}
console.log(`${maxAndMin([-10,500,2,3,-1,-2])}`);

/**
 *
 * @param array - An array of any primitive type
 * @returns {object} Object where the keys are the values that were passed in
 * and the value was the number of times it occurred.
 *
 * example: countArray([3, 6, 3, 2, 2, 3, 'some', 'hello', 'some', [1, 2]])
 * returns: {'2': 2, '3': 3, '6': 1, some: 2, hello: 1, '1,2': 1}
 *
 */
export function countArray(array) {
    let valueArray = [];
    for(let i = 0; i < array.length; i++){
        let repeat = 1;
        for(let j = i + 1; j < array.length; j++){
            if(array[i] == array[j]){
                array.splice(j, 1)
                repeat += 1;
            }
        }
        valueArray.push(repeat);
    }
    let object = {
        [array] : valueArray
    };
    return object;
}
console.log(countArray([3, 6, 3, 2, 2, 3, 'some', 'hello', 'some', [1, 2]]))