import {Link, useLocation} from "react-router-dom";
import {useEffect} from "react";
import axios from "axios";
function Test() {
    const location = useLocation();
    let eventSource;
    useEffect(() => {
        let subscribeUrl = "http://localhost:8080/notification/subscribe";
        const permission = Notification.requestPermission();
        const accessToken = localStorage.getItem("email");
        eventSource = new EventSource(subscribeUrl + "?token=" + accessToken);
        axios.get("/s")
            .then(event=>{
                console.log(event.data)
            })
            .catch(err => console.log(err))
    }, [])



    const trigger =()=>{
        axios.post("http://localhost:8080/notification/add")
            .then(event=>{
                console.log(event)
                console.log(location.state)
            })
            .catch(error=>console.error(error))
        console.log(location.state)
        eventSource.addEventListener("add", function (event) {
            let message = event.data;
            new Notification("test", {body:new Date()});
        })
    }

    return <div>
        <button onClick={trigger}>notification</button>
    </div>
}

export default Test;