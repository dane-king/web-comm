let stompClient = null;

const getElement = (id) => document.getElementById(id);

const sendMessage=()=>stompClient.send("/app/chat", {}, JSON.stringify({ 'from': from.value, 'text': getElement('chat').value }))
const sendCommand=()=>stompClient.send("/app/command", {}, JSON.stringify({ 'from': from.value, 'text': getElement('command').value }))

const setConnected=(connected) => {
	getElement('connect').disabled = connected;
	getElement('disconnect').disabled = !connected;
	getElement('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
	getElement('chat_response').innerHTML = '';
	getElement('private_request').innerHTML = '';
    getElement('private_response').innerHTML = '';

}

const setupButtons = () => {
	const from = getElement('from');

	getElement('sendMessage').removeEventListener('click', sendMessage);
	getElement('sendCommand').removeEventListener('click', sendCommand);

	getElement('sendMessage').addEventListener('click', sendMessage)
	getElement('sendCommand').addEventListener('click', sendCommand)
}

function connect() {
	setupButtons();

	const socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);

	stompClient.connect({}, function (frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/messages', showMessageOutput('chat_response'));
		stompClient.subscribe('/user/queue/request', showMessageOutput('private_request'));
		stompClient.subscribe('/user/queue/response', showMessageOutput('private_response'));
	});
}

const disconnect= () => {

	if (stompClient != null) {
		stompClient.disconnect();
	}

	setConnected(false);
	console.log("Disconnected");
}
const showMessageOutput = (destination) => (messageOutput) => {
	messageOutput = JSON.parse(messageOutput.body);
	const response = getElement(destination);
	const p = document.createElement('p');
	p.style.wordWrap = 'break-word';
	p.appendChild(document.createTextNode(messageOutput.from + ": " + messageOutput.text + " (" + messageOutput.time + ")"));
	response.appendChild(p);
}

