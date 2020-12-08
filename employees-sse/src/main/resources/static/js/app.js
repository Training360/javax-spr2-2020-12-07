window.onload = function() {
    // const evtSource = new EventSource("api/sse");
    // evtSource.addEventListener("message",
    //     function(event) {
    //         console.log(event.data);
    //         print(event.data);
    //     });

    const evtSource = new EventSource("api/events");
    evtSource.addEventListener("message",
        function(event) {
            console.log(event);
            print(JSON.parse(event.data).text);
        });
}

function print(message) {
    let div = document.querySelector("#message-div");
    let p = document.createElement("p");
    p.innerHTML = message;
    div.appendChild(p);
}
