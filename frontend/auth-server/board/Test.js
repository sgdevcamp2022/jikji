import {Link, useLocation} from "react-router-dom";
import axios from "axios";
import {useEffect} from "react";
import {useNotification} from "../action/useNotification";

function Test() {
    const location = useLocation();
    let eventSource;
    useEffect(() => {
        let subscribeUrl = "http://localhost:8080/sub";
        const permission = Notification.requestPermission();
        const accessToken = localStorage.getItem("accessToken");
        eventSource = new EventSource(subscribeUrl + "?token=" + accessToken);
    }, [])

    const ButtonHandler = (event) => {
        event.preventDefault()
        axios.get("/refreshToken/validation", {
            headers: {
                'content-type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem("refreshToken")
            }
        })
            .then(res => {
                console.log(res.data)
            })
            .catch(error => console.log(error))
    }

    const trigger =()=>{
        axios.post("/add")
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
        <form onSubmit={ButtonHandler}>
            <h1>{location.state}</h1>
            <h1>테스트입니다.</h1>
            <button formAction={""}>테스트</button>
        </form>
        <button onClick={trigger}>notification</button>
        <Link to="/updatePassword">
            <button>비밀번호 변경</button>
        </Link>
    </div>
}

export default Test;