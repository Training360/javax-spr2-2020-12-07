window.onload = function() {
    emp();

    document.querySelector("#message-button").onclick = function() {
        let name = document.querySelector("#message-input").value;
        fetch('http://localhost:8080/api/employees', {method: 'POST', headers: {
                'Content-Type': 'application/json'},
            body: JSON.stringify({'name': name})
        }).then(response => response.json())
            .then(data => emp());
    };

}

function emp() {
    fetch('http://localhost:8080/api/employees')
        .then(response => response.json())
        .then(employees => print(employees));

}

function print(employees) {
    let ul = document.querySelector("#employees-ul");
    ul.innerHTML = "";
    for (employee of employees) {
        let p = document.createElement("li");
        p.innerHTML = employee.name;
        ul.appendChild(p);
    }
}