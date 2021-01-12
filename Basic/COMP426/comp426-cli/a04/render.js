/**
 * Course: COMP 426
 * Assignment: a04
 * Author: Sebastian Crowell
 *
 * This script uses jQuery to build an HTML page with content taken from the
 * data defined in data.js.
 */



/**
 * Given a hero object (see data.js), this function generates a "card" showing
 *     the hero's name, information, and colors.
 * @param hero  A hero object (see data.js)
 */
// let hero = {
//     id: 1,
//     first: "Tony",
//     last: "Stark",
//     name: "Iron Man",
//     img: "icons/ironman.png",
//     color: "#931815",
//     backgroundColor: "#f39c11",
//     subtitle: "The Armored Avenger",
//     description: "When billionaire industrialist Tony Stark dons his sophisticated armor, he becomes a living high-tech weapon. Tony uses his ultra modern creation alongside the Avengers fighting for the greater good.",
//     firstSeen: new Date(1963, 3),
// }
export const renderHeroCard = function(hero) {
    // TODO: Generate HTML elements to represent the hero
    // TODO: Return these elements as a string, HTMLElement, or jQuery object

    // Example: return `<div>${hero.name}</div>`;
 
        return `
        <div style="background-color:${hero.backgroundColor}">
        <img src="${hero.img}"></img>
        <span>${hero.subtitle}</span>
        </div>
        <p> 
        <div style="background-color:${hero.color}">
        <p>${hero.name}</p>
        <p>${hero.firstSeen}</p>
        <p>${hero.first}</p>
        <div>${hero.last}</div>
        <div>${hero.description}</div>
        </div>
        </p>
        <button>edit</button>
        `;

};
//console.log(renderHeroCard(hero))  

/**
 * Given a hero object, this function generates a <form> which allows the
 *     user to edit the fields of the hero. The form inputs should be
 *     pre-populated with the initial values of the hero.
 * @param hero  The hero object to edit (see data.js)
 */
export const renderHeroEditForm = function(hero) {
    // TODO: Generate HTML elements to represent the hero edit form
    // TODO: Return these elements as a string, HTMLElement, or jQuery object
    // Example: return `<form>${hero.name}</form>`;

    return `
    <form> 
    <button type="submit" value="Submit">save</button>
    <button type="cancel" value="Cancel">cancel</button>
    <input value=${hero.name}></input>
    <input value=${hero.first}></input>
    <input value="${hero.firstSeen}"></input>
    <input value=${hero.last}></input>
    <textarea>${hero.description}</textare>
    </form>
    `;
    //$(input).appendTo(".content")
    //maybe use heroicData.push({changes})
};
//console.log(renderHeroEditForm(hero))


/**
 * Given an array of hero objects, this function converts the data into HTML and
 *     loads it into the DOM.
 * @param heroes  An array of hero objects to load (see data.js)
 */
export const loadHeroesIntoDOM = function(heroes) {
    // Grab a jQuery reference to the root HTML element
    const $root = $('#root');

    // TODO: Generate the heroes using renderHeroCard()
    heroes.forEach(id => {
            $root.append(renderHeroCard(id))
    });
    // TODO: Append the hero cards to the $root element

    // Pick a hero from the list at random
    const randomHero = heroes[Math.floor(Math.random() * heroes.length)];

    // TODO: Generate the hero edit form using renderHeroEditForm()
    $root.append(renderHeroEditForm(randomHero))

    // TODO: Append the hero edit form to the $root element
};



/**
 * Use jQuery to execute the loadHeroesIntoDOM function after the page loads
 */
$(function() {
    loadHeroesIntoDOM(heroicData);
});
