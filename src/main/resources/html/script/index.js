let possible = ['This is a page hosted using Spigot or Bungeecord', 'It even supports scripts!', 'How awesome is this?!', 'Make sure to join my discord :D'];
let current = 0;
let removalLength = 0;
let flash = true;

function updateMessage() {
    let finalMessage = `${possible[current].substring(0, possible[current].length - removalLength)}${flash ? "|" : "&nbsp;"}`;
    document.getElementById("description").innerHTML = finalMessage;
}

function addChar() {
    removalLength--;
    flash = true;
    updateMessage();
    if (removalLength != 0) setTimeout(() => addChar(), 100);
    else setTimeout(() => removeChar(), 2000);
}

function removeChar() {
    removalLength++;
    flash = true;
    updateMessage();
    if (removalLength >= possible[current].length) {
        current++
        if (current > possible.length - 1) current = 0;
        removalLength = possible[current].length;
        setTimeout(() => addChar(), 500);
    } else setTimeout(() => removeChar(), 100);
}

setInterval(() => {
    flash = !flash;
    updateMessage();
}, 400)

setTimeout(() => removeChar(), 2000);
