const host = "localhost:8080";





const stompClient = new StompJs.Client({
    brokerURL: `ws://${host}/secured/room`
});

let userName;
getUser();

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
    });
    stompClient.subscribe('/topic/participiants', (participiants) => {
        showEnemies(JSON.parse(participiants.body));
    });
    stompClient.subscribe(`/user/${userName}/queue/punch`, function (punch) {
        showHealth(JSON.parse(punch.body));
    })
    stompClient.subscribe(`/user/${userName}/queue/participiants`, function (participiants) {
        showGreeting(JSON.parse(participiants.body).filter(item => item !== userName) + " are playing");
        showEnemies(JSON.parse(participiants.body));
    })
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendPunch() {
    stompClient.publish({
        destination: "/app/punch",
        body: JSON.stringify({'name': $("#enemy-select").val()})
    });
}

function getUser() {
    fetch(`http://${host}/api/v1/users`, {
        headers: {
            'Accept': 'application/json'
        }})
        .then(response => response.text())
        .then(text => showUserInfo(JSON.parse(text)));
}

function showEnemies(enemies) {
    let enemiesList = $("#enemy-select");
    enemiesList.empty();
    for (let enemy of enemies) {
        if (enemy === userName) continue;
        enemiesList.append("<option value='" + enemy+ "'>" + enemy + "</option>");
    }
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showHealth(punch) {
    if (punch === 100) disconnect();
    $("#health").text(punch);
}

function showUserInfo(message) {
    userName = message.username;
    $("#user-info").append("Username:<br>" + message.username + "<br>Health:</br>" + "<p id='health'>"+message.health + "</p>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendPunch());
});