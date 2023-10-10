let ws = null;
//var url = "ws://localhost:8080/echo-ws/echo";
let url = "ws://localhost:8080/echo";

function setConnected(connected) {
  document.getElementById('connect').disabled = connected;
  document.getElementById('disconnect').disabled = !connected;
  document.getElementById('echo').disabled = !connected;
}

function connect() {
  ws = new WebSocket(url);

  ws.onopen = function () {
    setConnected(true);
  };

  ws.onmessage = function (event) {
    log(event.data);
  };
  
  ws.onclose = function (event) {
    setConnected(false);
    log('Info: Closing Connection.');
  };
}

function disconnect() {
  if (ws != null) {
    ws.close();
    ws = null;
  }
  setConnected(false);
}

function echo() {
  if (ws != null) {
    const message = document.getElementById('message').value;
    log('Sent: ' + message);
    ws.send(message);
  } else {
    alert('connection not established, please connect.');
  }
}

function log(message) {
  const console = document.getElementById('logging');
  const p = document.createElement('p');
  p.appendChild(document.createTextNode(message));
  console.appendChild(p);
  while (console.childNodes.length > 12) {
    console.removeChild(console.firstChild);
  }
  console.scrollTop = console.scrollHeight;
}
