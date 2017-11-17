/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var key = {
  37: 'left',
  38: 'up',
  39: 'right',
  40: 'down',
  65: 'a',
  66: 'b'
};

var kc = ['up', 'up', 'down', 'down', 'left', 'right', 'left', 'right', 'b', 'a'];

// a variable to remember the 'position' the user has reached so far.
var kcpos = 0;

// add keydown event listener
document.addEventListener('keydown', function(e) {
  // get the value of the key code from the key map
  var key = key[e.keyCode];
  // get the value of the required key from the konami code
  var reqk = kc[kcpos];

  // compare the key with the required key
  if (key == reqk) {

    // move to the next key in the konami code sequence
    kcpos++;

    // if the last key is reached, activate cheats
    if (kcpos == kc.length)
      a();
  } else
    kcpos = 0;
});

function a() {
  alert("conoces el código (:");
}